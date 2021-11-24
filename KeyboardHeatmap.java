import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.io.File;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {
    private Image keyboard;
    final char[][] ch2D = CharacterFrequency.ch2D;
    CharacterFrequency charFreq;

    public Surface() {
        // Constantly checks for a valid file until one is found or user quits
        while (true) {
            System.out.print("Please enter the file you would like to scan: ");
            Scanner scanner = new Scanner(System.in);
            String fileName = scanner.nextLine();
            if (new File(fileName).exists()) {
                // make new CharacterFrequency object
                charFreq = new CharacterFrequency(fileName);
                System.out.println("File Found. Click the Java icon on your taskbar to see the heatmap.");
                scanner.close();
                break;
            } else {
                System.out.println("File not found. Try again or CTRL + C to exit.");
                System.out.println();
            }

        }
        loadImage();
        setSurfaceSize();
    }

    private void loadImage() {
        keyboard = new ImageIcon("keyboard.png").getImage();
    }

    private void setSurfaceSize() {
        Dimension d = new Dimension();
        d.width = keyboard.getWidth(null);
        d.height = keyboard.getHeight(null);
        setPreferredSize(d);
    }

    private void colorHeatmap(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(keyboard, 0, 0, null);

        int xPosition = 14; // x position of the first key
        int yPosition = 20; // y position of the first key
        int width = 72; // width of each key
        int height = 68; // height of each key
        float alpha = 6 * 0.1f; // transparency of the key

        int min = charFreq.getMinFrequency();
        int max = charFreq.getMaxFrequency();
        for (int i = 0; i < ch2D.length; i++) {
            for (int j = 0; j < ch2D[i].length / 2; j++) {
                // ch2D[2 * j] and ch2D[2 * j + 1] are the two characters in a single key
                int val = charFreq.getKeyFrequency(ch2D[i][j * 2], ch2D[i][j * 2 + 1]);
                g2d.setColor(calculateColor(val, min, max));
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                if (i == 0) { // first row (top row)
                    g2d.fillRect(xPosition, yPosition, width, height);
                    xPosition += 100;
                    if (j == ch2D[i].length / 2 - 1) {
                        // move to next row
                        xPosition = 158;
                        yPosition = 118;
                    }
                }
                if (i == 1) { // second row (qwerty...)
                    g2d.fillRect(xPosition, yPosition, width, height);
                    xPosition += 100;
                    if (j == ch2D[i].length / 2 - 1) {
                        xPosition = 180;
                        yPosition = 216;
                    }
                }
                if (i == 2) { // third row (asdfg...)
                    g2d.fillRect(xPosition, yPosition, width, height);
                    xPosition += 100;
                    if (j == ch2D[i].length / 2 - 1) {
                        xPosition = 200;
                        yPosition = 314;
                    }
                }
                if (i == 3) { // forth row (zxcvb...)
                    g2d.fillRect(xPosition, yPosition, width, height);
                    xPosition += 100;
                }
            }
        }
    }

    public Color calculateColor(int value, int minimum, int maximum) {
        double percent = ((value - minimum) * 100) / (maximum - minimum);
        Color[] colors = { new Color(255, 255, 217), new Color(237, 248, 177), new Color(199, 233, 180),
                new Color(127, 205, 187), new Color(66, 182, 196), new Color(29, 145, 192), new Color(33, 95, 168),
                new Color(37, 52, 148), new Color(7, 30, 88) };
        int index = (int) Math.floor(percent / 100 * (colors.length - 1));
        return colors[index];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        colorHeatmap(g);
    }
}

public class KeyboardHeatmap extends JFrame {
    public KeyboardHeatmap() {

        initUI();
    }

    private void initUI() {

        add(new Surface());

        pack();

        setTitle("Keyboard Heatmap");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                KeyboardHeatmap ex = new KeyboardHeatmap();
                ex.setVisible(true);
            }
        });
    }
}
