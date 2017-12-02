/* Copyright (c) 2017, Derick Felix */

package com.platform.game;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @name Player
 * @author derickfelix
 * @date Dec 2, 2017
 */
public class Player {

    private double x;
    private double y;
    private double dx;
    private double dy;
    
    private final int width;
    private final int height;
    
    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;
    
    private final double moveSpeed;
    private final double maxSpeed;
    private final double maxFallingSpeed;
    private final double stopSpeed;
    private final double jumpStart;
    private final double gravity;
    
    private final TileMap tileMap;
    
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;
    
    public Player(TileMap tileMap) {
        this.tileMap = tileMap;
        this.width = 20;
        this.height = 20;
        
        this.moveSpeed = 0.3;
        this.maxSpeed = 4.2;
        this.maxFallingSpeed = 12;
        this.stopSpeed = 0.30;
        this.jumpStart = -11.0;
        this.gravity = 0.64;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void setLeft(boolean left) { this.left = left; }
    public void setRight(boolean right) { this.right = right; }
    public void setJumping() { if (!falling) {this.jumping = true;} }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    ////////////////////////////////////////////////////////////////////////////
    
    public void update() {
        // determine next position
        leftOrRight();
        upOrDown();
        // check collisions
        checkCollisions();
        // move the map
        tileMap.setxOffset((int) (Game.WIDTH / 2 - this.x));
        tileMap.setyOffset((int) (Game.HEIGHT / 2 - this.y));
    }
    
    public void draw(Graphics2D g) {
        int xOffset = tileMap.getxOffset();
        int yOffset = tileMap.getyOffset();
        
        g.setColor(Color.red);
        g.fillRect((int) (xOffset + x - width / 2), (int) (yOffset + y - height / 2), width, height);
    }
    
    private void leftOrRight() {
        if (left) {
            this.dx -= moveSpeed;
            if (dx < -maxSpeed) {
               this.dx = -maxSpeed; 
            }
        } 
        else if(right) {
            this.dx += moveSpeed;
            if(dx > maxSpeed) {
                this.dx = maxSpeed;
            }
        } 
        else {
            if (dx > 0) {
                this.dx -= stopSpeed;
                if (dx < 0) {
                    this.dx = 0;
                }
            } 
            else if (dx < 0) {
                this.dx += stopSpeed;
                if (dx > 0) {
                    this.dx = 0;
                }
            }
        }
    }
    
    private void upOrDown() {
        if (jumping) {
            this.dy = jumpStart;
            this.falling = true;
            this.jumping = false;
        }
        
        if (falling) {
            this.dy += gravity;
            if (dy > maxFallingSpeed) {
                this.dy = maxFallingSpeed;
            }
        } else {
            this.dy = 0;
        }
    }
    
    private void checkCollisions() {
        int currentCol = this.tileMap.getColTile((int) x);
        int currentRow = this.tileMap.getRowTile((int) y);
        
        double toX = x + dx;
        double toY = y + dy;
        double tempX = x;
        double tempY = y;
        
        // dy
        calculateCorners(x, toY);
        if (this.dy < 0) {
            if (topLeft || topRight) {
                this.dy = 0;
                tempY = (currentRow * tileMap.getTileSize()) + (height / 2);
            } else {
                tempY += dy;
            }
        }
        if (this.dy > 0) {
            if (bottomLeft || bottomRight) {
                this.dy = 0;
                this.falling = false;
                tempY = (currentRow + 1) * tileMap.getTileSize() - (height / 2);
            } else {
                tempY += this.dy;
            }
        }
        
        // dx
        calculateCorners(toX, y);
        if (dx < 0) {
            if (topLeft || bottomLeft) {
                this.dx = 0;
                tempX = currentCol * tileMap.getTileSize() + width / 2;
            } else {
                tempX += this.dx;
            }
        }
        
        if (dx > 0) {
            if (topRight || bottomRight) {
                this.dx = 0;
                tempX = (currentCol + 1) * tileMap.getTileSize() - width / 2;
            } else { 
                tempX += this.dx;
            }
        }
        
        if (!falling) {
            calculateCorners(x, y + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
        
        this.x = tempX;
        this.y = tempY;
    }
    
    private void calculateCorners(double x, double y) {
        int leftTile = this.tileMap.getColTile((int) (x - width / 2));
        int rightTile = this.tileMap.getColTile((int) (x + width / 2) - 1);
        int topTile = this.tileMap.getRowTile((int) (y - height / 2));
        int bottomTile = this.tileMap.getRowTile((int) (y + height / 2) - 1);
        
        this.topLeft = tileMap.getTile(topTile, leftTile) == 0;
        this.topRight = tileMap.getTile(topTile, rightTile) == 0;
        this.bottomLeft = tileMap.getTile(bottomTile, leftTile) == 0;
        this.bottomRight = tileMap.getTile(bottomTile, rightTile) == 0;
    }
}
