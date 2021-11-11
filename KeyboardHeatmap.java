import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {

    private Image keyboard;
    static CharacterFrequency characterFrequency;
    char[][] ch2D = CharacterFrequency.ch2D;

    public Surface() {
        // Constantly checks for a valid file until one is found or user quits
        while (true) {
            System.out.print("Please enter the file you would like to scan: ");
            Scanner sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            if (new File(fileName).exists()) {
                // make new CharacterFrequency object
                characterFrequency = new CharacterFrequency(fileName);
                System.out.println("File Found. Click the Java icon on your taskbar to see the heatmap.");
                sc.close();
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

    private void colorHeatmap(Graphics g) throws IOException {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(keyboard, 0, 0, null);

        int xPosition = 14; // x position of the first key
        int yPosition = 20; // y position of the first key
        int width = 72; // width of each key
        int height = 68; // height of each key
        float alpha = 5 * 0.1f; // transparency of the key

        for (int i = 0; i < ch2D.length; i++) {
            for (int j = 0; j < ch2D[i].length / 2; j++) {
                // ch2D[2 * j] and ch2D[2 * j + 1] are the two characters in a single key
                int val = characterFrequency.getSingleFrequency(ch2D[i][2 * j])
                        + characterFrequency.getSingleFrequency(ch2D[i][2 * j + 1]);
                g2d.setColor(calculateColor(val, characterFrequency.getMinKeyFrequency(),
                        characterFrequency.getMaxKeyFrequency()));
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

    // Retuns a color based on the value of the key
    // I couldn't figure out a better way to do this
    public Color calculateColor(int value, int minimum, int maximum) {
        double percent = ((value - minimum) * 100) / (maximum - minimum);
        if (percent >= 94.44444444444444) {
            return new Color(124, 10, 1);
        } else if (94.44444444444444 > percent && percent >= 88.88888888888889) {
            return new Color(208, 26, 15);
        } else if (88.88888888888889 > percent && percent >= 83.33333333333333) {
            return new Color(253, 57, 0);
        } else if (83.33333333333333 > percent && percent >= 77.77777777777777) {
            return new Color(252, 185, 19);
        } else if (77.77777777777777 > percent && percent >= 72.22222222222221) {
            return new Color(254, 251, 1);
        } else if (72.22222222222221 > percent && percent >= 66.66666666666666) {
            return new Color(255, 251, 94);
        } else if (66.66666666666666 > percent && percent >= 61.1111111111111) {
            return new Color(206, 251, 2);
        } else if (61.1111111111111 > percent && percent >= 55.55555555555554) {
            return new Color(135, 250, 0);
        } else if (55.55555555555554 > percent && percent >= 49.999999999999986) {
            return new Color(58, 249, 1);
        } else if (49.999999999999986 > percent && percent >= 44.44444444444443) {
            return new Color(0, 255, 0);
        } else if (44.44444444444443 > percent && percent >= 38.88888888888887) {
            return new Color(0, 229, 75);
        } else if (38.88888888888887 > percent && percent >= 33.333333333333314) {
            return new Color(0, 212, 176);
        } else if (33.333333333333314 > percent && percent >= 27.777777777777757) {
            return new Color(0, 183, 216);
        } else if (27.777777777777757 > percent && percent >= 22.2222222222222) {
            return new Color(24, 138, 240);
        } else if (22.2222222222222 > percent && percent >= 16.666666666666643) {
            return new Color(20, 108, 246);
        } else if (16.666666666666643 > percent && percent >= 11.111111111111088) {
            return new Color(0, 82, 255);
        } else if (11.111111111111088 > percent && percent >= 5.555555555555532) {
            return new Color(0, 0, 255);
        } else if (5.555555555555532 > percent && percent > 0.0) {
            return new Color(7, 0, 196);
        } else {
            return new Color(0, 0, 0);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
            colorHeatmap(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
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