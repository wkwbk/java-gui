package TankGame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @projectName: JavaGUI
 * @package: TankGame
 * @className: TankGame
 * @author: LI SIR
 * @description: 游戏主窗口
 * @date: 2024/12/1 11:27
 * @version: 1.0
 */
public class TankGame extends JFrame {
    MyPanel myPanel;

    public static void main(String[] args) {
        new TankGame();
    }

    public TankGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入选择：\n1. 新游戏\n2. 继续上局");
        String key = scanner.next();

        myPanel = new MyPanel(key);

        Thread thread = new Thread(myPanel);
        thread.start();

        this.add(myPanel);
        this.addKeyListener(myPanel);
        this.setSize(1290, 787);
        // 在屏幕中居中显示
        this.setLocationRelativeTo(null);
        // 关闭后退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}

