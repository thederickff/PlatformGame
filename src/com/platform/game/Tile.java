/* Copyright (c) 2017, Derick Felix */

package com.platform.game;

import java.awt.image.BufferedImage;

/**
 * @name Tile
 * @author derickfelix
 * @date Dec 2, 2017
 */
public class Tile {

    private BufferedImage image;
    private boolean blocked;
    
    public Tile(BufferedImage image, boolean blocked) {
        this.image = image;
        this.blocked = blocked;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    
    
    
}
