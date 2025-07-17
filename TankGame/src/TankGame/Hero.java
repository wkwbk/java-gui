package TankGame;

import java.util.Vector;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: Hero
 * @author: LI SIR
 * @description: 我方坦克
 * @date: 2024/12/1 11:26
 * @version: 1.0
 */
public class Hero extends Tank {
    // 定义一个 Shot 对象，表示一个射击
    Shot shot = null;
    // 定义子弹容器
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    // 根据当前 Hero 对象的位置和方向创建 Shot 对象
    public void shotEnemyTank() {
        // 限制子弹数量不得超过5
        if (shots.size() == 5) {
            return;
        }
        // 根据我方坦克的位置和方向创建子弹对象
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
}
