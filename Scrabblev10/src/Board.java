import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

class Board extends JPanel {
	
	 private Tile[][] tiles;
	
	

        public Board() {
        	setPreferredSize(new Dimension(602, 602));
            tiles = new Tile[15][15];
            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    tiles[row][col] = new Tile(' ', row, col);
                }
            }
        }

        public void paintComponent(Graphics g) {

        	super.paintComponent(g);
        	
        	int tileSize = 40;

            

            
        	for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                	int x = col * tileSize;
                    int y = row * tileSize;

                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, tileSize, tileSize);
                    g.setColor(Color.GRAY);
                    g.drawRect(x, y, tileSize, tileSize);
                    if ((row == 0 && col == 0)  
                            || ((row == 7 && col == 0)) || ((row == 0 && col == 7)) 
                            || ((row == 14 && col == 14)) || ((row == 14 && col == 0)) 
                            || ((row == 0 && col == 14)) || ((row == 14 && col == 7)) 
                            || ((row == 7 && col == 14)) 
                             ){
                        g.setColor(new Color(255, 0, 0, 120)); 
                        g.fillRect(x+1, y+1, tileSize, tileSize);
                    }
                    if ((row == 1 && col == 1 ) || ((row == 2 && col == 2))
                    		|| ((row == 3 && col == 3)) || ((row == 4 && col == 4)) 
                    		|| ((row == 10 && col == 10)) || ((row == 13 && col == 13)) 
                    		|| ((row == 12 && col == 12)) || ((row == 11 && col == 11)) 
                    		|| ((row == 13 && col == 1)) || ((row == 12 && col == 2)) 
                    		|| ((row == 11 && col == 3)) || ((row == 10 && col == 4)) 
                    		|| ((col == 13 && row == 1)) || ((col == 12 && row == 2)) 
                    		|| ((col == 11 && row == 3)) || ((col == 10 && row == 4)) 
                    		
                    ){
                        g.setColor(new Color(255, 192, 203, 120)); 
                        g.fillRect(x+1, y+1, tileSize, tileSize);
                    }
                    if ((row == 1 && col == 5 ) || ((row == 1 && col == 9))
                    		|| ((row == 5 && col == 5)) || ((row == 5 && col == 9)) 
                    		|| ((row == 9 && col == 5)) || ((row == 9 && col == 9)) 
                    		|| ((row == 13 && col == 5)) || ((row == 13 && col == 9)) 
                    		|| ((row == 5 && col == 1)) || ((row == 9 && col == 1)) 
                    		|| ((row == 5 && col == 13)) || ((row == 9 && col == 13)) 
                    	
                    		
                    ){
                   

                    g.setColor(new Color(0, 0, 255, 120)); 
                    g.fillRect(x+1, y+1, tileSize, tileSize);
                }
                    if ((row == 3 && col == 0 ) || ((row == 11 && col == 0))
                    		|| ((row == 2 && col == 6)) || ((row == 2 && col == 8)) 
                    		|| ((row == 3 && col == 0)) || ((row == 7 && col == 3)) 
                    		|| ((row == 8 && col == 2)) || ((row == 6 && col == 2)) 
                    		|| ((row == 6 && col == 6)) || ((row == 6 && col == 8)) 
                    		|| ((row == 6 && col == 12)) || ((row == 7 && col == 3)) 
                    		|| ((row == 2 && col == 6)) || ((row == 2 && col == 8)) 
                    		|| ((row == 3 && col == 7)) || ((row == 3 && col == 14)) 
                    		
                    		|| ((row == 7 && col == 11)) || ((row == 6 && col == 12)) 
                    		|| ((row == 8 && col == 12)) || ((row == 8 && col == 6)) 
                    		|| ((row == 8 && col == 8)) || ((row == 11 && col == 0)) 
                    		|| ((row == 11 && col == 7)) || ((row == 11 && col == 14)) 
                    		|| ((row == 12 && col == 6)) || ((row == 12 && col == 8)) 
                    		|| ((row == 14 && col == 3)) || ((row == 0 && col == 3)) 
                    		|| ((row == 14 && col == 11)) || ((row == 0 && col == 11)) 
       
                    ){
                  
                    g.setColor(new Color(173, 216, 230, 120)); 
                    g.fillRect(x+1, y+1, tileSize, tileSize);
                    
                }
                    if ((row == 7 && col == 7)){
                    	
                        g.setColor(new Color(255, 255, 0, 120)); 
                        g.fillRect(x+1, y+1, tileSize, tileSize);
                    }
                    }
                }
        
            
            
            
            

        }
        public Collection<Tile> getTiles() {
            List<Tile> result = new ArrayList<>();
            for (int row = 0; row < 15; row++) {
                for (int col = 0; col < 15; col++) {
                    Tile tile = tiles[row][col];
                    if (tile != null) {
                        result.add(tile);
                    }
                }
            }
            return result;
        }
        
        public void placeTile(Tile tile) {
            int row = tile.getRow();
            int col = tile.getCol();
            tiles[row][col] = tile;
        }
        
        public Tile getTileAt(int row, int col) {
            if (row >= 0 && row < 15 && col >= 0 && col < 15) {
                return tiles[row][col];
            } else {
                return null;
            }
        }
        
        public void removeTile(Tile tile) {
            tiles[tile.getRow()][tile.getCol()] = null;
        }
        public boolean hasTileAbove(int row, int col) {
            return getTileAt(row , col - 1) != null;
        }

        public boolean hasTileBelow(int row, int col) {
            return getTileAt(row, col + 1) != null;
        }

        public boolean hasTileToLeft(int row, int col) {
            return getTileAt(row - 1 , col ) != null;
        }

        public boolean hasTileToRight(int row, int col) {
            return getTileAt(row + 1, col ) != null;
        }
        
       
   

		

        
        
        
} 