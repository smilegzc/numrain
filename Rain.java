import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.MemoryImageSource;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

public class Rain extends JFrame implements ActionListener {
    Random rand = new Random();
    int row;
    int[] posArr;
    Toolkit kit = Toolkit.getDefaultToolkit();
    Number num;
    Dimension screenSize;

    public Rain() {
        initFrame();
    }

    private void initFrame() {
        //初始化变量
        screenSize = kit.getScreenSize();
        row = screenSize.width/10;
        num = new Number();
        posArr = new int[row + 1];
        this.add(num, BorderLayout.CENTER);

        //设置窗口全屏显示
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.setUndecorated(true);
        //隐藏鼠标光标
        Image img = kit.createImage(new MemoryImageSource(0, 0, new int[0], 0, 0));
        this.setCursor(kit.createCustomCursor(img, new Point(0, 0), null));
        //设置ESC键退出
        JRootPane rp = this.getRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        InputMap inputMap = rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rp.getActionMap().put("ESCAPE", new Key());

        this.setVisible(true);
        //初始化列头位置（以像素为单位）
        for(int i = 0; i < row +1; i++) {
            posArr[i] = rand.nextInt(screenSize.height);
        }
        //整个程序的主循环
        new Timer(80, this).start();
    }
    //监听按键动作
    private class Key extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    //由Timer触发
    public void actionPerformed(ActionEvent e) {
        num.repaint();
    
    }
    //画板
    class Number extends JPanel {

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(getFont().deriveFont(Font.BOLD));
            //将背景色绘为黑色
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, screenSize.width, screenSize.height);

            int index = 0;//列标签
            g2.setColor(Color.GREEN);
            for(int i = 0; i < row; i++) {
                //绘出列字符串
                for(int j = 0; j < 20; j ++) {
                    g2.drawString("" + rand.nextInt(2), i * 10, posArr[index] - j * 17);
                }

                posArr[index] += rand.nextInt(5) * 20;
                if(posArr[index] > 768 + 15 * 20) {
                    posArr[i] = rand.nextInt(5) * 20;
                }
                index ++;
            }
        }
    }

    public static void main(String[] args) {
        new Rain();
    }
}
