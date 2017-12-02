/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.game;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author derickfelix
 */
public class Game {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 450;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Platformer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel());
        window.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}
