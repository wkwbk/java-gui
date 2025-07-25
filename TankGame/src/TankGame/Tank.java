package TankGame;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: Tank
 * @author: LI SIR
 * @description: 坦克类
 * @date: 2024/12/1 11:24
 * @version: 1.0
 */
public class Tank {
    private int x;
    private int y;
    private int direct;
    private int speed = 1;
    boolean isLive = true;

    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
