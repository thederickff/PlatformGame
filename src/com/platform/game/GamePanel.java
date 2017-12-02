/* Copyright (c) 2017, Derick Felix */
package com.platform.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @name GamePanel
 * @author derickfelix
 * @date Dec 2, 2017
 */
public class GamePanel extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private final int FPS = 30;
    private final int targetTime = 1000 / FPS;
    
    private TileMap tilemap;

    public GamePanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        init();
        
        long startTime;
        long urdTime;
        long waitTime;
        
        while (running) {
            startTime = System.nanoTime();
            update();
            render();
            draw();
            
            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            try {
                if (waitTime > 0) 
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                System.out.printf("An error occured: %s\n", e);
            }
        }
    }

    public void init() {
        this.running = true;
        this.image = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        this.tilemap = new TileMap(getClass().getResource("/com/platform/game/resources/map.txt").getFile(), 32);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    private void update() {
        //Toolkit.getDefaultToolkit().sync();
        this.tilemap.update();
    }
    
    private void render() {
        this.tilemap.draw(g);
    }
    
    private void draw() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
    
}
