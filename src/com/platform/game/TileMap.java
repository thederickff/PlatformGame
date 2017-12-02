/* Copyright (c) 2017, Derick Felix */
package com.platform.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @name TileMap
 * @author derickfelix
 * @date Dec 2, 2017
 */
public class TileMap {

    private int xOffset;
    private int yOffset;

    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;
    
    private BufferedImage tileSet;
    private Tile[][] tiles;
    
    private int minX;
    private int minY;
    private int maxX = 0;
    private int maxY = 0;

    public TileMap(String s, int tileSize) {
        this.tileSize = tileSize;

        try (BufferedReader br = new BufferedReader(new FileReader(s))) {
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];
            
            minX = Game.WIDTH - mapWidth * tileSize;
            minY = Game.HEIGHT - mapHeight * tileSize;
                    
            String delimiters = "\\s+";
            
            for (int row = 0; row < mapHeight; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for (int col = 0; col < mapWidth; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (IOException e) {
            System.out.printf("IOException error: %s\n", e);
        }
    }
    
    public void loadTiles(String s) {
        try {
            this.tileSet = ImageIO.read(getClass().getResource(s));
            int numberTilesAcross = (tileSet.getWidth() + 1) / (tileSize + 1);
            
            tiles = new Tile[2][numberTilesAcross];
            
            BufferedImage subImage;
            
            for (int col = 0; col < numberTilesAcross; col ++) {
                subImage = tileSet.getSubimage(col * tileSize + col, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subImage, false);
                subImage = tileSet.getSubimage(col * tileSize + col, tileSize + 1, tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, true);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    public int getxOffset() { return xOffset; }
    public int getyOffset() { return yOffset; } 
    public int getColTile(int x) { return x / tileSize; }
    public int getRowTile(int y) { return y / tileSize; }
    public int getTileSize() { return tileSize; }
    public int getTile(int row, int col) { return map[row][col]; }
    public boolean isBlocked(int row, int col) {
        int rc = map[row][col];
        int r = rc / tiles[0].length;
        int c = rc % tiles[0].length;
        return tiles[r][c].isBlocked();
    }
    public void setxOffset(int xOffset) { 
        this.xOffset = xOffset; 
        
        if (this.xOffset < minX) {
            this.xOffset = minX;
        }
        if (this.xOffset > maxX) {
            this.xOffset = maxX;
        }
    }
    public void setyOffset(int yOffset) { 
        this.yOffset = yOffset;
        if (this.yOffset < minY) {
            this.yOffset = minY;
        }
        
        if (this.yOffset > maxY) {
            this.yOffset = maxY;
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    public void update() {
        
    }
    
    public void draw(Graphics2D g) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int rc = map[row][col];
                
                int r = rc / tiles[0].length;
                int c = rc % tiles[0].length;
                
                g.drawImage(tiles[r][c].getImage(), xOffset + col * tileSize, yOffset + row * tileSize, null);
            }
        }
    }
}
