package TankGame;

import java.util.Vector;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: EnemyTank
 * @author: LI SIR
 * @description: 敌方坦克
 * @date: 2024/12/4 20:07
 * @version: 1.0
 */
public class EnemyTank extends Tank implements Runnable {
    // 定义子弹容器
    Vector<Shot> shots = new Vector<>();
    // 坦克容器
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    /**
     * 判断当前坦克是否与其他坦克碰撞或重叠
     *
     * @return
     */
    public boolean isTouchEnemyTank() {
        // 判断当前坦克方向
        switch (getDirect()) {
            case 0: // 向上
                // 让当前坦克和其他所有坦克进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 对方坦克方向是上或下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 40
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (getX() + 40 >= enemyTank.getX()
                                    && getX() + 40 <= enemyTank.getX() + 40
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 对方坦克方向是左或右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 60
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (getX() + 40 >= enemyTank.getX()
                                    && getX() + 40 <= enemyTank.getX() + 60
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: // 向右
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 对方坦克方向是上或下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (getX() + 60 >= enemyTank.getX()
                                    && getX() + 60 <= enemyTank.getX() + 40
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (getX() + 60 >= enemyTank.getX()
                                    && getX() + 60 <= enemyTank.getX() + 40
                                    && getY() + 40 >= enemyTank.getY()
                                    && getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 对方坦克方向是左或右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (getX() + 60 >= enemyTank.getX()
                                    && getX() + 60 <= enemyTank.getX() + 60
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (getX() + 60 >= enemyTank.getX()
                                    && getX() + 60 <= enemyTank.getX() + 60
                                    && getY() + 40 >= enemyTank.getY()
                                    && getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: // 向下
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 对方坦克方向是上或下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 40
                                    && getY() + 60 >= enemyTank.getY()
                                    && getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (getX() + 40 >= enemyTank.getX()
                                    && getX() + 40 <= enemyTank.getX() + 40
                                    && getY() + 60 >= enemyTank.getY()
                                    && getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 对方坦克方向是左或右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 60
                                    && getY() + 60 >= enemyTank.getY()
                                    && getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (getX() + 40 >= enemyTank.getX()
                                    && getX() + 40 <= enemyTank.getX() + 60
                                    && getY() + 60 >= enemyTank.getY()
                                    && getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: // 向左
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        // 对方坦克方向是上或下
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 40
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 40
                                    && getY() + 40 >= enemyTank.getY()
                                    && getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        // 对方坦克方向是左或右
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 60
                                    && getY() >= enemyTank.getY()
                                    && getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (getX() >= enemyTank.getX()
                                    && getX() <= enemyTank.getX() + 60
                                    && getY() + 40 >= enemyTank.getY()
                                    && getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (isLive) {
            // 判断敌方坦克子弹数量，如果没有则添加一个子弹
            if (shots.isEmpty()) {
                Shot shot = null;
                // 判断敌方坦克方向
                switch (getDirect()) {
                    case 0: // 向上
                        shot = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1: // 向右
                        shot = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2: // 向下
                        shot = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3: // 向左
                        shot = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                // 将创建的子弹放入子弹容器
                shots.add(shot);
                // 启动子弹线程
                new Thread(shot).start();
            }
            // 根据坦克方向来继续移动
            switch (getDirect()) {
                case 0: // 向上
                    // 让坦克保持一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1: // 向右
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2: // 向下
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3: // 向左
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }

            // 随机改变坦克方向
            setDirect((int) (Math.random() * 4));
        }
    }
}
