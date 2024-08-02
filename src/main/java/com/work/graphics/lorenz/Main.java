package com.work.graphics.lorenz;

import javax.swing.SwingUtilities;

/**
 *
 * @author linux
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> Lorenz.createWin("Lorenz", 640, 480));
    }
}
