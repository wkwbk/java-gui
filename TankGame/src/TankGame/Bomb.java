package TankGame;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: Bomb
 * @author: LI SIR
 * @description: 炸弹类
 * @date: 2024/12/11 16:50
 * @version: 1.0
 */
public class Bomb {
    // 炸弹的坐标
    int x, y;
    // 炸弹的生命周期
    int life = 30;
    boolean isLive = true;

    // 构造方法
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 减少生命值
    public  void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
