/* Copyright (c) 2017, Derick Felix */
package com.platform.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public TileMap(String s, int tileSize) {
        this.tileSize = tileSize;

        try (BufferedReader br = new BufferedReader(new FileReader(s))) {
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];
            
            String delimiters = " ";
            
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
    
    public void update() {
        
    }
    
    public void draw(Graphics2D g) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int rc = map[row][col];
                
                if (rc == 0) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
                
                g.fillRect(xOffset + col * tileSize, yOffset + row * tileSize, tileSize, tileSize);
            }
        }
    }
}
