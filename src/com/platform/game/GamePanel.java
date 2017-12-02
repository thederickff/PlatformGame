/* Copyright (c) 2017, Derick Felix */
package com.platform.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @name GamePanel
 * @author derickfelix
 * @date Dec 2, 2017
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private final int FPS = 60;
    private final int targetTime = 1000 / FPS;
    
    private TileMap tilemap;
    private Player player;

    public GamePanel() {
        super();
        initComponents();
    }

    private void initComponents() {
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
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
        this.player = new Player(tilemap);
        this.player.setX(50);
        this.player.setY(50);
    }
    ////////////////////////////////////////////////////////////////////////////
    
    private void update() {
        Toolkit.getDefaultToolkit().sync();
        this.tilemap.update();
        this.player.update();
    }
    
    private void render() {
        this.tilemap.draw(g);
        this.player.draw(g);
    }
    
    private void draw() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_A) this.player.setLeft(true);
        if (code == KeyEvent.VK_D) this.player.setRight(true);
        if (code == KeyEvent.VK_SPACE) this.player.setJumping();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_A) this.player.setLeft(false);
        if (code == KeyEvent.VK_D) this.player.setRight(false);
    }
    
}
