package TankGame;

import java.io.*;
import java.util.Vector;

/**
 * @projectName: TankGame
 * @package: TankGame
 * @className: Recorder
 * @author: LI SIR
 * @description: 该类用于记录相关信息，和文件交互
 * @date: 2025/1/14 16:30
 * @version: 1.0
 */
public class Recorder {
    // 记录我方击毁敌人坦克数量
    private static int allEnemyTankNum = 0;
    // 定义 IO 对象，准备写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\myRecord.txt";
    // 定义 Vector 指向 MyPanel 对象的敌人坦克 Vector
    private static Vector<EnemyTank> enemyTanks = null;
    // 定义一个 Node 的 Vector，用于保存敌方坦克信息
    private static Vector<Node> nodes = new Vector<>();

    public static String getRecordFile() {
        return recordFile;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            // 循环读取文件中的坦克信息
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return nodes;
    }

    /**
     * 游戏退出时将 allEnemyTankNum 保存到 recordFile
     * 保存敌人坦克的坐标和方向
     */
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "");
            bw.newLine();
            // 遍历敌方坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                // 取出敌方坦克
                EnemyTank enemyTank = enemyTanks.get(i);

                // 判断是否存活
                if (enemyTank.isLive) {
                    // 保存该 enemyTank 信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
