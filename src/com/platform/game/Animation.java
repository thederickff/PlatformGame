/* Copyright (c) 2017, Derick Felix */

package com.platform.game;

import java.awt.image.BufferedImage;

/**
 * @name Animation
 * @author derickfelix
 * @date Dec 2, 2017
 */
public class Animation {

    
    private BufferedImage[] frames;
    private int currentFrame;
    
    private long startTime;
    private long delay;
    
    public Animation() {
        
    }
    
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        if (currentFrame >= frames.length) {
            currentFrame = 0;
        }
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public void update() {
        if (delay == -1) {
            return;
        }
        
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length) {
            currentFrame = 0;
        }
    }
    
    public BufferedImage getImage() {
        return frames[currentFrame];
    }
}
