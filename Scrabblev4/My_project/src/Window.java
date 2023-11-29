import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;

public class Window extends JPanel    {
    Board board; 
    
    int startX;
    int startY;  
    int newturnscore=0;   
    private List<JLabel> playerNameScoreLabels = new ArrayList<>();
    Map<String, Integer> playerScores = new HashMap<>();
    
    private List<JLabel> rackTileLabels = new ArrayList<>();
    
    private ScrabbleTile scrabbleTile = new ScrabbleTile();

    JLabel message; 
    JLabel message1; 
    JLabel message2; 
    JLabel message3;
    JLabel message5;
    JLabel message6;    
    int currentPlayerIndex = 0;
    List<String> playerNames;
    List<Tile> placedTiles = new ArrayList<>();    
    Map<String, List<Character>>  playerTiles = new HashMap<>();    
    private ScrabbleDictionary scrabbleDictionary;      
    List<JLabel> tileLabels = new ArrayList<>();
    List<Tile> permanentPlacedTiles = new ArrayList<>();
 
    public Window() {
    	String filePath = "src/Scrabble.txt";
    	scrabbleDictionary = new ScrabbleDictionary(filePath);
    	playerNames = AddPlayer.getNames();
        setLayout(null);  
        setBackground( Color.WHITE); 
        setBorder( BorderFactory.createEtchedBorder() ); 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize); //[width=1440,height=900]
        
        message = new JLabel("SKIP"); 
        message.setForeground(Color.black ); 
        message.setFont(new Font("Serif", Font.BOLD, 14));
        add(message);
        
        System.out.println(ScrabbleTile.getTileBagSize());
        
        message1 = new JLabel("Pass");
        message1.setForeground(Color.black );
        message1.setFont(new Font("Serif", Font.BOLD, 14));
        add(message1);
        
        message2 = new JLabel("Next");
        message2.setForeground(Color.black ); 
        message2.setFont(new Font("Serif", Font.BOLD, 14));
        add(message2);
        
        List<String> names = AddPlayer.getNames();
        
        
        int y = 470;
        for (String name : names) {
            JLabel message4 = new JLabel(name);
            message4.setForeground(Color.BLACK);
            message4.setFont(new Font("Serif", Font.BOLD, 14));
            message4.setBounds(1200, y, 380, 30);
            add(message4);
            
            JLabel playerScoreLabel = new JLabel("0");
            playerScoreLabel.setForeground(Color.BLACK);
            playerScoreLabel.setFont(new Font("Serif", Font.BOLD, 14));
            playerScoreLabel.setBounds(1300, y, 380, 30);
            add(playerScoreLabel);
            playerNameScoreLabels.add(playerScoreLabel);
            
            playerScores.put(name, 0);

            List<Character> allocatedTiles = scrabbleTile.allocateTiles();
//            System.out.println(allocatedTiles);     
            playerTiles.put(name, allocatedTiles);
            System.out.println(playerTiles);
            y += 30;
        }
        List<Character> playerTilesList = playerTiles.get(playerNames.get(currentPlayerIndex));
        int x = 520;
        for (char tile : playerTilesList) {
            ImageIcon tileImaget = Tileimage.getTileImage(tile);
            JLabel labelt = new JLabel(tileImaget);
            add(labelt);
            labelt.setBounds(x, 670, 40, 40);
            int originx = x;
            int originy = 670;
            x += 50;
            labelt.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                     startX = e.getX();
                     startY = e.getY();
                }
                public void mouseReleased(MouseEvent e) {
                	int tileX = labelt.getX();
                    int tileY = labelt.getY();
                    int boardX = tileX - 420;
                    int boardY = tileY - 10;
                    int col = boardY / 40;
                    int row = boardX / 40;
                    if (col < 0 || col > 14 || row < 0 || row > 14) { 
                        placedTiles.removeIf(t -> t.getLetter() == tile); 
                        labelt.setLocation(originx, originy);
                        return;
                    }
                    Tile newTile = new Tile(tile, row, col);
                    
                    List<Tile> tilesOnBoard = new ArrayList<>();
                    for (Tile t : board.getTiles()) {
                        if (!placedTiles.contains(t)) {
                            tilesOnBoard.add(t);
                        }
                    }
                    
                    Tile oldTile = board.getTileAt(row, col);
                    if (oldTile != null) {
                        board.removeTile(oldTile);
                        placedTiles.remove(oldTile);
                        tilesOnBoard.remove(oldTile);
                    }
                    
                    Tile previousTile = placedTiles.stream().filter(t -> t.getLetter() == tile).findFirst().orElse(null);
                    if (previousTile != null) {
                        board.removeTile(previousTile);
                        placedTiles.remove(previousTile);
                        tilesOnBoard.remove(previousTile);
                    }
 
                    placedTiles.add(newTile);             
                    board.placeTile(newTile);
                    tilesOnBoard.add(newTile);
                    
                    System.out.println("Tile " + tile + " was moved to row " + row + ", col, " + col + " with value "+ ScrabbleTile.letterValues.get(tile));

                    labelt.setLocation(row * 40 + board.getX(), col * 40 + board.getY());
                }
                
                });
            labelt.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                	
                    int newX = e.getX() + labelt.getX() - startX;
                    int newY = e.getY() + labelt.getY() - startY;
                    labelt.setLocation(newX, newY);
                }
            });
           
            tileLabels.add(labelt);
        }
//        List<Character> Tiles = scrabbleTile.getCurrentTileBag();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
        message5 = new JLabel("Score " + newturnscore);
        message5.setForeground(Color.black );
        message5.setFont(new Font("Serif", Font.BOLD, 14));
        add(message5);

        board = new Board();
        add(board);
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 670, 380, 30);
        add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	newturnscore = 0;
//              System.out.println("Tile " + tile.getLetter() + " at row " + tile.getRow() + ", col " + tile.getCol());
                int turnscore = 0;
                for (Tile tile : placedTiles) {
                    Integer x = ScrabbleTile.letterValues.get(tile.getLetter());
                    if (x != null) {
                        turnscore += x.intValue();
                    }
                }
                
                
                System.out.println(turnscore);
                System.out.println(playerTiles);
                newturnscore = turnscore;
                message5.setText("Score " + newturnscore);
                add(message5);
                revalidate();
                repaint();
                
                if (!areTilesNextToEachOther(placedTiles)) {
                    JOptionPane.showMessageDialog(Window.this, "Placed tiles are not next to each other");
                    return;
                }
                
                String placedLetters = placedTiles.stream()
                        .map(t -> String.valueOf(t.getLetter()))
                        .collect(Collectors.joining());
                
                if (scrabbleDictionary.isWordInDictionary(placedLetters)) {
                    System.out.println("Legal word: " + placedLetters);
                    updatePlayerScore(newturnscore);
                    
                    String currentPlayerName = playerNames.get(currentPlayerIndex);
                    List<Character> currentPlayerTiles = playerTiles.get(currentPlayerName);
                    for (Tile placedTile : placedTiles) {
                        currentPlayerTiles.remove(Character.valueOf(placedTile.getLetter()));
                        if (scrabbleTile.hasMoreTiles()) {
                            currentPlayerTiles.add(scrabbleTile.getNewTile());
                        }
                    }
                    playerTiles.put(currentPlayerName, currentPlayerTiles);
                    // Add your other conditions and actions for a successful submission
                    permanentPlacedTiles.addAll(placedTiles);
                    // Clear the placedTiles list after a successful submission
                    placedTiles.clear();
                    switchToNextPlayer();
                } else {
                    JOptionPane.showMessageDialog(Window.this, "The word " + placedLetters + " is not a legal word");
                }
            }
        });

        JLabel red = new JLabel("This is a 3X word!");
        red.setForeground(new Color(255, 0, 0, 120));
        red.setFont(new Font("Serif", Font.BOLD, 21));
        add(red);
        red.setBounds(120, 70, 380, 30);
        
        JLabel lblue = new JLabel("This is a 2X letter!");
        lblue.setForeground(new Color(173, 216, 230, 120));
        lblue.setFont(new Font("Serif", Font.BOLD, 21));
        add(lblue);
        lblue.setBounds(120, 110, 380, 30);
        
        JLabel pink = new JLabel("This is a 2X word!");
        pink.setForeground(new Color(255, 192, 203, 120));
        pink.setFont(new Font("Serif", Font.BOLD, 21));
        add(pink);
        pink.setBounds(120, 150, 380, 30);
        
        JLabel dblue = new JLabel("This is a 3X letter!");
        dblue.setForeground(new Color(0, 0, 255, 120));
        dblue.setFont(new Font("Serif", Font.BOLD, 21));
        add(dblue);
        dblue.setBounds(120, 190, 380, 30);
        
        JLabel yellow = new JLabel("This is a 2X letter!");
        yellow.setForeground(new Color(255, 255, 0, 120));
        yellow.setFont(new Font("Serif", Font.BOLD, 21));
        add(yellow);
        yellow.setBounds(120, 230, 380, 30);
  
        message.setBounds(120, 370, 380, 30);
        message1.setBounds(120, 470, 380, 30);
        message2.setBounds(120, 570, 380, 30);
        message5.setBounds(1200, 70, 380, 30);
        board.setBounds(450,20,602,602);
        
        playerNameScoreLabels.get(currentPlayerIndex).setFont(new Font("Serif", Font.BOLD, 14));

 
    }
  
    private void switchToNextPlayer() {
        List<Character> currentPlayerTiles = playerTiles.get(playerNames.get(currentPlayerIndex));

        // Remove the previous player's tiles from the window
        removePlayerTilesFromWindow();
        rackTileLabels.clear();

        
        System.out.println(ScrabbleTile.getTileBagSize());
        
        // Remove old tile labels
        for (JLabel label : tileLabels) {
            remove(label);
        }
        tileLabels.clear();

        
        

        // Update playerTiles with new tiles
        playerTiles.put(playerNames.get(currentPlayerIndex), currentPlayerTiles);
        System.out.println(playerTiles);

        // Add permanent placed tiles
        for (Tile tile : permanentPlacedTiles) {
            addPermanentTileToWindow(tile);
        }

        // Add the new player's tiles to the window
        JLabel previousPlayerLabel = playerNameScoreLabels.get(currentPlayerIndex);
        previousPlayerLabel.setFont(new Font("Serif", Font.ITALIC, 14));

        currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size();
        List<Character> nextPlayerTilesList = playerTiles.get(playerNames.get(currentPlayerIndex));
        addPlayerTilesToWindow(nextPlayerTilesList);

        JLabel currentPlayerLabel = playerNameScoreLabels.get(currentPlayerIndex);
        currentPlayerLabel.setFont(new Font("Serif", Font.BOLD, 14));

        revalidate();
        repaint();
    }
    private void addPlayerTilesToWindow(List<Character> playerTilesList) {
    	
    	
        int x = 520;
        
        for (char tile : playerTilesList) {
            ImageIcon tileImage = Tileimage.getTileImage(tile);
            JLabel labelt = new JLabel(tileImage);
            rackTileLabels.add(labelt);
            add(labelt);
            setComponentZOrder(labelt, 0);
            labelt.setBounds(x, 670, 40, 40);
            int originX = x;
            int originY = 670;
            x += 50;
            
            labelt.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                     startX = e.getX();
                     startY = e.getY();
                }
                public void mouseReleased(MouseEvent e) {
                	int tileX = labelt.getX();
                    int tileY = labelt.getY();
                    int boardX = tileX - 420;
                    int boardY = tileY - 10;
                    int col = boardY / 40;
                    int row = boardX / 40;
                    if (col < 0 || col > 14 || row < 0 || row > 14) { 
                        placedTiles.removeIf(t -> t.getLetter() == tile); 
                        labelt.setLocation(originX, originY);
                        return;
                    }
                    Tile newTile = new Tile(tile, row, col);
                    List<Tile> tilesOnBoard = new ArrayList<>();
                    for (Tile t : board.getTiles()) {
                        if (!placedTiles.contains(t)) {
                            tilesOnBoard.add(t);
                        }
                    }
                    Tile oldTile = board.getTileAt(row, col);
                    if (oldTile != null) {
                        board.removeTile(oldTile);
                        placedTiles.remove(oldTile);
                        tilesOnBoard.remove(oldTile);
                    }
                    Tile previousTile = placedTiles.stream().filter(t -> t.getLetter() == tile).findFirst().orElse(null);
                    if (previousTile != null) {
                        board.removeTile(previousTile);
                        placedTiles.remove(previousTile);
                        tilesOnBoard.remove(previousTile);
                    }
                    placedTiles.add(newTile);             
                    board.placeTile(newTile);
                    tilesOnBoard.add(newTile);
                    System.out.println("Tile " + tile + " was moved to row " + row + ", col, " + col + " with value "+ ScrabbleTile.letterValues.get(tile));
                    labelt.setLocation(row * 40 + board.getX(), col * 40 + board.getY());
                    
                    
                }
                });
            labelt.addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    int newX = e.getX() + labelt.getX() - startX;
                    int newY = e.getY() + labelt.getY() - startY;
                    labelt.setLocation(newX, newY);
                }
            });
            tileLabels.add(labelt);
        }
    }
    private void removePlayerTilesFromWindow() {
        Iterator<JLabel> iterator = rackTileLabels.iterator();
        while (iterator.hasNext()) {
            JLabel label = iterator.next();
            if (label.getParent() == this) {
                remove(label);
                iterator.remove();
            }
        }
    }
    private void updatePlayerScore(int score) {
        String currentPlayerName = playerNames.get(currentPlayerIndex);
        int currentScore = playerScores.get(currentPlayerName);
        int newScore = currentScore + score;

        playerScores.put(currentPlayerName, newScore);
        playerNameScoreLabels.get(currentPlayerIndex).setText(Integer.toString(newScore));
    }
    public boolean areTilesNextToEachOther(List<Tile> placedTiles) {
        if (placedTiles.isEmpty()) {
            return false;
        }
        placedTiles.sort(Comparator.comparing(Tile::getRow).thenComparing(Tile::getCol));
        boolean sameRow = true;
        boolean sameCol = true;
        int initialRow = placedTiles.get(0).getRow();
        int initialCol = placedTiles.get(0).getCol();
        for (int i = 1; i < placedTiles.size(); i++) {
            Tile prevTile = placedTiles.get(i - 1);
            Tile currTile = placedTiles.get(i);
            int rowDiff = Math.abs(currTile.getRow() - prevTile.getRow());
            int colDiff = Math.abs(currTile.getCol() - prevTile.getCol());
            if (rowDiff > 1 || colDiff > 1 || (rowDiff == 1 && colDiff == 1)) {
                return false;
            }
            if (currTile.getRow() != initialRow) {
                sameRow = false;
            }
            if (currTile.getCol() != initialCol) {
                sameCol = false;
            }
        }
        return sameRow || sameCol;
    }
    private void disableTileEvents(JLabel label) {
        for (MouseListener mouseListener : label.getMouseListeners()) {
            label.removeMouseListener(mouseListener);
        }
        for (MouseMotionListener mouseMotionListener : label.getMouseMotionListeners()) {
            label.removeMouseMotionListener(mouseMotionListener);
        }
    }
    
    private void addPermanentTileToWindow(Tile tile) {
        char letter = tile.getLetter();
        ImageIcon tileImage = Tileimage.getTileImage(letter);
        JLabel labelt = new JLabel(tileImage);
        add(labelt);
        setComponentZOrder(labelt, 0);
        int row = tile.getRow();
        int col = tile.getCol();
        labelt.setBounds(row * 40 + board.getX(), col * 40 + board.getY(), 40, 40);
        tileLabels.add(labelt);
        disableTileEvents(labelt);
    }
}
