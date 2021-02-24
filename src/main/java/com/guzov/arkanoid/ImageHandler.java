package com.guzov.arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHandler {
    public static void TakeScrrenShot(JFrame jframe) throws InterruptedException {
        Thread.sleep(2000);
        BufferedImage img = new BufferedImage(jframe.getWidth(), jframe.getHeight(), BufferedImage.TYPE_INT_RGB);
        jframe.paint(img.getGraphics());

        JFrame screenshotFrame = new JFrame();
        screenshotFrame.setTitle("stained_image");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        screenshotFrame.setSize(220,399);
        screenshotFrame.setSize(img.getWidth(), img.getHeight());
        JLabel label=new JLabel();
        label.setIcon(new ImageIcon(img));
        screenshotFrame.getContentPane().add(label, BorderLayout.CENTER);
        screenshotFrame.setLocationRelativeTo(null);
        screenshotFrame.pack();
        screenshotFrame.setVisible(true);

    }

}
