package TankGame;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: Shot
 * @author: LI SIR
 * @description: 射击类
 * @date: 2024/12/8 11:06
 * @version: 1.0
 */
public class Shot implements Runnable {
    int x;
    int y;
    int direct;
    int speed = 5;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (direct) {
                case 0: // 向上
                    y -= speed;
                    break;
                case 1: // 向右
                    x += speed;
                    break;
                case 2: // 向下
                    y += speed;
                    break;
                case 3: // 向左
                    x -= speed;
                    break;
            }

            // 当子弹移动到面板的边界或碰到敌人坦克时，就销毁
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
