package TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: MyPanel
 * @author: LI SIR
 * @description: 游戏绘图区域
 * @date: 2024/12/1 11:26
 * @version: 1.0
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    // 定义我方坦克
    Hero hero;
    // 定义存放敌方坦克的容器
    Vector<EnemyTank> enemyTanks = new Vector<>();
    // 定义一个存放 Node 对象的 Vector 用于恢复坦克坐标和方向
    Vector<Node> nodes = new Vector<>();
    // 定义存放炸弹的容器，当子弹击中坦克时就加入 Bomb 对象到容器
    Vector<Bomb> bombs = new Vector<>();
    // 定义敌方坦克数量
    int enemyTankNum = 5;

    // 定义三张炸弹图片
    Image image1, image2, image3;

    // 构造方法
    public MyPanel(String key) {
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("文件不存在，只能开启新游戏");
            key = "1";
        }

        // 初始化我方坦克
        hero = new Hero(450, 690);
        // 设置我方坦克移动速度
        hero.setSpeed(10);
        // 将 enemyTanks 传给 Recorder 类的 enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        switch (key) {
            case "1":
                // 初始化敌方坦克
                for (int i = 0; i < enemyTankNum; i++) {
                    // 创建一个敌方坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    // 设置方向
                    enemyTank.setDirect(2);
                    // 启动敌方坦克线程
                    Thread thread = new Thread(enemyTank);
                    thread.start();
//            // 给敌方坦克加入一颗子弹
//            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
//            // 将子弹加入敌方坦克的子弹容器
//            enemyTank.shots.add(shot);
//            // 启动 shot 对象
//            new Thread(shot).start();
                    // 加入容器
                    enemyTanks.add(enemyTank);
                    // 将坦克容器存入坦克，以便坦克能够访问其他坦克位置
                    enemyTank.setEnemyTanks(enemyTanks);
                }
                break;
            case "2":
                // 初始化敌方坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    // 创建一个敌方坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    // 设置方向
                    enemyTank.setDirect(node.getDirect());
                    // 启动敌方坦克线程
                    Thread thread = new Thread(enemyTank);
                    thread.start();
                    // 加入容器
                    enemyTanks.add(enemyTank);
                    // 将坦克容器存入坦克，以便坦克能够访问其他坦克位置
                    enemyTank.setEnemyTanks(enemyTanks);
                }
                break;
            default:
                System.out.println("输入有误！");
        }
        // 初始化图片对象，图片放在 out 目录中
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/img/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/img/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/img/bomb_3.gif"));
    }

    public void showInfo(Graphics g) {
        // 画出玩家总成绩
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTank(1020, 60, g, 0, 0);

        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 填充矩形，颜色默认黑色
        g.fillRect(0, 0, 1000, 750);
        // 画出我方坦克
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

//        // 画出我方坦克子弹
//        if (hero.shot != null && hero.shot.isLive) {
//            g.draw3DRect(hero.shot.x, hero.shot.y, 2, 2, false);
//        }

        // 画出我方坦克子弹容器中的子弹
        if (hero != null) {
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot shot = hero.shots.get(i);
                // 如果容器中的子弹不为空且存活，就画出子弹，否则从容器中移除该子弹
                if (shot != null && shot.isLive) {
                    g.draw3DRect(shot.x, shot.y, 2, 2, false);
                } else {
                    hero.shots.remove(shot);
                }
            }
        }

        // 如果 bombs 容器中有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb bomb = bombs.get(i);
            // 根据当前这个 bomb 的 life 值画出对应的图片
            if (bomb.life > 20) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 10) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            // 让这个炸弹的生命值减少
            bomb.lifeDown();
            // 如果 bomb life 为 0，就从 bombs 容器中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        // 画出敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            // 取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);

            // 判断当前坦克是否还存活，存活就画出坦克
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);

                // 画出敌方坦克所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);

                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

        showInfo(g);
    }

    /**
     * @param x      坦克左上角x坐标
     * @param y      坦克左上角y坐标
     * @param g      画笔
     * @param direct 坦克方向（上下左右）
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 根据不同类型设置坦克颜色
        switch (type) {
            case 0: // 我方坦克
                g.setColor(Color.cyan);
                break;
            case 1: // 敌方坦克
                g.setColor(Color.yellow);
                break;
        }

        // 根据坦克的方向绘制坦克
        switch (direct) {
            case 0: // 向上
                g.fill3DRect(x, y, 10, 60, false); // 画出坦克左轮
                g.fill3DRect(x + 30, y, 10, 60, false); // 画出坦克右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // 画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1: // 向右
                g.fill3DRect(x, y, 60, 10, false); // 画出坦克左轮
                g.fill3DRect(x, y + 30, 60, 10, false); // 画出坦克右轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // 画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2: // 向下
                g.fill3DRect(x, y, 10, 60, false); // 画出坦克左轮
                g.fill3DRect(x + 30, y, 10, 60, false); // 画出坦克右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // 画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3: // 向左
                g.fill3DRect(x, y, 60, 10, false); // 画出坦克左轮
                g.fill3DRect(x, y + 30, 60, 10, false); // 画出坦克右轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // 画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    // 判断我方子弹是否击中敌方坦克
    public void hitEnemyTank() {
        // 遍历我方坦克子弹容器
        for (Shot shot : hero.shots) {
            // 判断子弹是否存活
            if (shot != null && shot.isLive) {
                // 从容器中取出敌方坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    // 判断敌方子弹是否击中我方坦克
    public void hitHero() {
        // 遍历所有敌方坦克
        for (EnemyTank enemyTank : enemyTanks) {
            // 遍历敌方坦克所有子弹
            for (Shot shot : enemyTank.shots) {
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    // 判断子弹是否击中坦克
    public void hitTank(Shot shot, Tank tank) {
        switch (tank.getDirect()) {
            case 0: // 坦克向上时
            case 2: // 坦克向下时
                if (shot.x > tank.getX() && shot.x < tank.getX() + 40 && shot.y > tank.getY() && shot.y < tank.getY() + 60) {
                    shot.isLive = false;
                    tank.isLive = false;
                    // 创建 Bomb 对象加入到 bombs 容器
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
            case 1: // 坦克向右时
            case 3: // 坦克向左时
                if (shot.x > tank.getX() && shot.x < tank.getX() + 60 && shot.y > tank.getY() && shot.y < tank.getY() + 40) {
                    shot.isLive = false;
                    tank.isLive = false;
                    // 创建 Bomb 对象加入到 bombs 容器
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                    // 将坦克从容器中删除
                    if (tank instanceof EnemyTank) {
                        enemyTanks.remove(tank);
                        Recorder.addAllEnemyTankNum();
                    }
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_J) {
//            // 判断我方坦克的子弹是否创建，如果创建了则判断子弹状态是否销毁，限制只能发射一颗子弹
//            if (hero.shot == null || !hero.shot.isLive) {
//                hero.shotEnemyTank();
//            }
            // 发射多颗子弹
            hero.shotEnemyTank();
        }

        // 让面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            hitEnemyTank();
            hitHero();
            this.repaint();
        }
    }
}
