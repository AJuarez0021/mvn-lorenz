package com.work.graphics.lorenz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author linux
 */
public class Lorenz extends JPanel {

    private static final int MAX_ITER = 50000;
    private final double xmin;
    private final double xmax;
    private final double ymin;
    private final double ymax;

    private double x, y, z;
    private int width;
    private int height;

    public Lorenz() {
        this.xmin = -25;
        this.xmax = 25;
        this.ymin = 0;
        this.ymax = 50;
        this.width = getWidth();
        this.height = getHeight();
    }

    private void update(double dt) {
        double xnew = x + dx(x, y, z) * dt;
        double ynew = y + dy(x, y, z) * dt;
        double znew = z + dz(x, y, z) * dt;
        x = xnew;
        y = ynew;
        z = znew;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        clearDevice(g2d);
        draw(g2d);
    }

    public void draw(Graphics2D g2d) {
        lorenz(g2d, 0.0, 20.00, 25.0, Color.YELLOW);
        lorenz(g2d, 0.0, 20.01, 25.0, Color.BLUE);
        lorenz(g2d, 0.0, 20.02, 25.0, Color.RED);

    }

    private void lorenz(Graphics2D g2d, double x1, double y1, double z1, Color color) {
        this.width = getWidth();
        this.height = getHeight();

        double dt = 0.001;
        this.x = x1;
        this.y = y1;
        this.z = z1;
       
        for (int i = 0; i < MAX_ITER; i++) {
            update(dt);
            setPixel(g2d, x, z, color);
        }
    }

    private double dx(double x, double y, double z) {
        return -10 * (x - y);
    }

    private double dy(double x, double y, double z) {
        return -x * z + 28 * x - y;
    }

    private double dz(double x, double y, double z) {
        return x * y - 8 * z / 3;
    }

    private void setPixel(Graphics2D g2d, double x, double y, Color color) {
        double xs = width * (x - xmin) / (xmax - xmin);
        double ys = height * (ymax - y) / (ymax - ymin);
        float scaledPenRadius = (float) (0.003 * 512);
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(xs - scaledPenRadius / 2, ys - scaledPenRadius / 2,
                scaledPenRadius, scaledPenRadius));
    }

    private void clearDevice(Graphics2D g2d) {
        g2d.setBackground(Color.BLACK);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public static void createWin(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.add(new Lorenz());
        frame.setVisible(true);
        setLocationCenter(frame);
    }

    private static void setLocationCenter(JFrame frame) {
        setLocationMove(frame, 0, 0);
    }

    private static void setLocationMove(JFrame frame, int moveWidth, int moveHeight) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frameSize.width = frameSize.width > screenSize.width ? screenSize.width : frameSize.width;
        frameSize.height = frameSize.height > screenSize.height ? screenSize.height : frameSize.height;
        frame.setLocation((screenSize.width - frameSize.width) / 2 + moveWidth, (screenSize.height - frameSize.height) / 2 + moveHeight);
    }
}
