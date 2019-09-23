package cz.stu.core;

import cz.stu.utils.ResourceReader;

import javax.swing.*;
import java.awt.*;

public class Display {
    private String title;
    private Dimension size;
    private JFrame frame;
    private Canvas canvas;

    public Display(String title, Dimension size) {
        this.title = title;
        this.size = size;

        createDisplay();
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setIconImage(ResourceReader.loadImage("sprites/icon.png"));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(size));
        canvas.setMaximumSize(new Dimension(size));
        canvas.setMinimumSize(new Dimension(size));
        canvas.setFocusable(true);

        frame.add(canvas);
        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
