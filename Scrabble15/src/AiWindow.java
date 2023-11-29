import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.*;
public class AiWindow extends JPanel {
    Board board;
    int startX;
    int startY;
    int newturnscore = 0;
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
    private ScrabbleSolver scrabbleSolver;
    int currentPlayerIndex = 0;
    List<String> playerNames;
    List<Tile> placedTiles = new ArrayList<>();
    Map<String, List<Character>> playerTiles = new HashMap<>();
    private ScrabbleDictionary scrabbleDictionary;
    List<JLabel> tileLabels = new ArrayList<>();
    List<Tile> permanentPlacedTiles = new ArrayList<>();
    private Gaddag gaddag;
    private boolean hasGameEnded() {
        for (String name : playerNames) {
            List<Character> tiles = playerTiles.get(name);
            if (tiles.size() == 0) {
                return true;
            }
        }
        return false;
    }
    public AiWindow() {
    	gaddag = new Gaddag();gaddag = new Gaddag();
    	gaddag.loadWordsFromFile("src/Scrabble.txt");
    	
    	board = new Board();
    	
    	scrabbleSolver = new ScrabbleSolver(gaddag, board);
        String filePath = "src/Scrabble.txt";
        scrabbleDictionary = new ScrabbleDictionary(filePath);
        AddAIplayer addAIplayerInstance = new AddAIplayer();
        playerNames = addAIplayerInstance.getPlayerAndAICount();
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEtchedBorder());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(screenSize); // [width=1440,height=900]
        message = new JLabel("SKIP");
        message.setForeground(Color.black);
        message.setFont(new Font("Serif", Font.BOLD, 14));
        // label click event
        message.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearPlacedTiles();
                switchToNextPlayer();
            }
        });
        add(message);
        System.out.println(ScrabbleTile.getTileBagSize());
        message1 = new JLabel("Pass");
        message1.setForeground(Color.black);
        message1.setFont(new Font("Serif", Font.BOLD, 14));
        add(message1);
        message2 = new JLabel("Clear");
        message2.setForeground(Color.black);
        message2.setFont(new Font("Serif", Font.BOLD, 14));
        // click event
        message2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clearPlacedTiles();
            }
        });
        add(message2);
        List<String> names = addAIplayerInstance.getPlayerAndAICount();
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
            // System.out.println(allocatedTiles);
            playerTiles.put(name, allocatedTiles);
            System.out.println(playerTiles);
            y += 30;
        }
        while (playerNames.get(currentPlayerIndex).startsWith("AI ")) {
            currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size();
        }
        List<Character> playerTilesList = playerTiles.get(playerNames.get(currentPlayerIndex));
        int x = 520;
        for (char tile : playerTilesList) {
            int originx = x;
            int originy = 670;
            x += 50;
            addMouseListener(tile, originx, originy);
        }
        // List<Character> Tiles = scrabbleTile.getCurrentTileBag();
        message5 = new JLabel("Score " + newturnscore);
        message5.setForeground(Color.black);
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
                int turnscore = 0;
                int wordMultiplier = 1;
                for (Tile tile : placedTiles) {
                    System.out.println("Tile " + tile.getLetter() + " at row " + tile.getRow() +
                            ", col " + tile.getCol());
                    Integer x = ScrabbleTile.letterValues.get(tile.getLetter());
                    if (x != null) {
                        int letterScore = x.intValue();
                        if (tile.getTileType() == TileType.DOUBLE_LETTER || tile.getTileType() == TileType.STAR_SPIN) {
                            letterScore *= 2;
                        } else if (tile.getTileType() == TileType.TRIPLE_LETTER) {
                            letterScore *= 3;
                        } else if (tile.getTileType() == TileType.DOUBLE_WORD) {
                            wordMultiplier *= 2;
                        } else if (tile.getTileType() == TileType.TRIPLE_WORD) {
                            wordMultiplier *= 3;
                        }
                        System.out.println("Score for " + tile.getLetter() + " is " + letterScore);
                        turnscore += letterScore;
                    }
                }
                turnscore = turnscore * wordMultiplier;
                System.out.println(turnscore);
                newturnscore = turnscore;
                message5.setText("Score " + newturnscore);
                add(message5);
                revalidate();
                repaint();
                if (!areAllWordsConnected()) {
                    JOptionPane.showMessageDialog(AiWindow.this, "Placed tiles are not next to each other");
                    return;
                }
                List<String> formedWords = getFormedWords(board, placedTiles, permanentPlacedTiles);
                System.out.println("Formed word from placed tiles: " + formedWords);
                if (formedWords.stream().allMatch(word -> scrabbleDictionary.isWordInDictionary(word))) {
                    System.out.println("Legal word: " + formedWords);
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
                    if (!scrabbleTile.hasMoreTiles() && hasGameEnded()) {
                        displayTheScore();
                        System.exit(0);
                    }
                    switchToNextPlayer();
                } else {
                    JOptionPane.showMessageDialog(AiWindow.this, "The word " + formedWords + " is not a legal word");
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
        board.setBounds(450, 20, 602, 602);

        playerNameScoreLabels.get(currentPlayerIndex).setFont(new Font("Serif", Font.BOLD, 14));

    }

    private void displayTheScore() {
        JOptionPane.showMessageDialog(AiWindow.this, "Game has ended");

        for (String playerName : playerNames) {
            int playerScore = playerScores.get(playerName);
            for (Character tile : playerTiles.get(playerName)) {
                Integer tileValue = ScrabbleTile.letterValues.get(tile);
                if (tileValue != null) {
                    playerScore -= tileValue.intValue();
                }
            }
            playerScores.put(playerName, playerScore);
        }
        String finalScores = "Final scores: ";
        for (String playerName : playerNames) {
            finalScores += playerName + ": " + playerScores.get(playerName) + "\n";
        }
        JOptionPane.showMessageDialog(AiWindow.this, finalScores);
    }

    private void clearPlacedTiles() {

        for (Tile tile : placedTiles) {

            board.removeTile(tile);

            tile.resetLabelLocation();
            tile.setRowCol(-1, -1);

        }

        System.out.println();


        placedTiles.clear();

    }

    private JLabel addMouseListener(char tile, int originx, int originy) {
        Tile theTile = new Tile(tile, -1, -1, originx, originy);


        if (tile == '*') {

            theTile.getLabel().addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    String input = JOptionPane.showInputDialog(AiWindow.this, "Enter a letter");
                    if (input != null && input.length() == 1) {
                        char letter = input.charAt(0);
                        if (letter >= 'A' && letter <= 'Z') {
                            theTile.setLetter(letter);
                            // remove the listener
                            theTile.getLabel().removeMouseListener(this);
                            
                            // Update the player's tiles (new lines added)
                            String currentPlayerName = playerNames.get(currentPlayerIndex);
                            List<Character> currentPlayerTiles = playerTiles.get(currentPlayerName);
                            currentPlayerTiles.remove(Character.valueOf('*'));
                            currentPlayerTiles.add(Character.valueOf(letter));
                            playerTiles.put(currentPlayerName, currentPlayerTiles);
                        }
                    }
                }
            });
        }

        theTile.getLabel().addMouseListener(new MouseAdapter() {
            int start_pos_x, start_pos_y;
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();

                start_pos_x = theTile.getLabel().getX();
                start_pos_y = theTile.getLabel().getY();

            }

            public void mouseReleased(MouseEvent e) {
                int boardX = theTile.getLabel().getX() - 420;
                int boardY = theTile.getLabel().getY() - 10;
                int col = boardY / 40;
                int row = boardX / 40;


                if (col < 0 || col > 14 || row < 0 || row > 14) {
                    placedTiles.remove(theTile);

                    theTile.resetLabelLocation();


                    if (theTile.getCol() >= 0 && theTile.getCol() <= 14 && theTile.getRow() >= 0 && theTile.getRow() <= 14) {
                        System.out.println("start_row=" + theTile.getRow() + "; start_col=" + theTile.getCol());
                        board.removeTile(theTile.getRow(), theTile.getCol());
                        theTile.setRowCol(-1, -1);
                    }

                    return;
                }


                if (theTile.getRow() == row && theTile.getCol() == col) {
                    return;
                }

//                Tile newTile = new Tile(tile, row, col);

                List<Tile> tilesOnBoard = new ArrayList<>();
                for (Tile t : board.getTiles()) {
                    if (!placedTiles.contains(t)) {
                        tilesOnBoard.add(t);
                    }
                }

                Tile oldTile = board.getTileAt(row, col);

                if (oldTile != null ) {

                    if (oldTile.getLetter() != ' ') {
                        theTile.resetLabelLocation();
                        return;
                    }
                    board.removeTile(oldTile);
                    placedTiles.remove(oldTile);
                    tilesOnBoard.remove(oldTile);

                    oldTile.setRowCol(-1, -1);
                    oldTile.resetLabelLocation();
                }


                if (placedTiles.contains(theTile)) {
                    placedTiles.remove(theTile);
                    tilesOnBoard.remove(theTile);
                    board.removeTile(theTile);
                }

                theTile.setRowCol(row, col);
                theTile.getLabel().setLocation(row * 40 + board.getX(), col * 40 + board.getY());


                placedTiles.add(theTile);
                tilesOnBoard.add(theTile);
                board.placeTile(theTile);

                System.out.println("Tile " + tile + " was moved to row " + row + ", col, " + col + " with value "
                        + ScrabbleTile.letterValues.get(tile));
            }

        });
        theTile.getLabel().addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {

                int newX = e.getX() + theTile.getLabel().getX() - startX;
                int newY = e.getY() + theTile.getLabel().getY() - startY;
                theTile.getLabel().setLocation(newX, newY);
            }
        });


        add(theTile.getLabel());

        tileLabels.add(theTile.getLabel());

        return theTile.getLabel();
    }

    private void switchToNextPlayer() {
        List<Character> currentPlayerTiles = playerTiles.get(playerNames.get(currentPlayerIndex));

        removePlayerTilesFromWindow();
        rackTileLabels.clear();

        System.out.println(ScrabbleTile.getTileBagSize());

        for (JLabel label : tileLabels) {
            remove(label);
        }
        tileLabels.clear();

        playerTiles.put(playerNames.get(currentPlayerIndex), currentPlayerTiles);
        System.out.println(playerTiles);

        for (Tile tile : permanentPlacedTiles) {
            addPermanentTileToWindow(tile);
        }
        

        JLabel previousPlayerLabel = playerNameScoreLabels.get(currentPlayerIndex);
        previousPlayerLabel.setFont(new Font("Serif", Font.ITALIC, 14));
        


        currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size();

        // Skip AI players
        while (playerNames.get(currentPlayerIndex).startsWith("AI ")) {
            System.out.println("SKippppppppppp");
            List<AnchorSquare> anchorSquares = findAnchorSquares();
            System.out.println(anchorSquares);

            generateAIMove(); // Add this line
            currentPlayerIndex = (currentPlayerIndex + 1) % playerNames.size();
        }

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
            int originX = x;
            int originY = 670;
            x += 50;

            JLabel label = addMouseListener(tile, originX, originY);
            rackTileLabels.add(label);
            setComponentZOrder(label, 0);
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

    public boolean areAllWordsConnected() {
        if (placedTiles.isEmpty()) {
            return false;
        }

        boolean [][] visited = new boolean[15][15];

        Tile tile = placedTiles.get(0);

        depthFirstSearch(tile.getRow(), tile.getCol(), visited);


        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Tile t = board.getTileAt(i, j);
                if (t != null && !visited[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }


    private void depthFirstSearch(int rowIndex, int colIndex, boolean [][] visited) {

        if (rowIndex < 0 || rowIndex >= 15 || colIndex < 0 || colIndex >= 15) {
            return;
        }
        if (visited[rowIndex][colIndex]) {
            return;
        }

        System.out.println("visiting " + board.getTileAt(rowIndex, colIndex).getLetter() + " at " + rowIndex + ", " + colIndex );


        visited[rowIndex][colIndex] = true;

        int [][] neighbors = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

        for (int [] neighbor : neighbors) {
            int row = rowIndex + neighbor[0];
            int col = colIndex + neighbor[1];

            Tile tile = board.getTileAt(row, col);
            if (tile != null) {
                depthFirstSearch(row, col, visited);
            }
        }
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

    private Tile getAdjacentTileAbove(Board board, List<Tile> permanentPlacedTiles, Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();

        Tile adjacentTileAbove = board.hasTileAbove(row, col) ? board.getTileAt(row, col - 1) : null;
        if (adjacentTileAbove != null && permanentPlacedTiles.contains(adjacentTileAbove)) {
            return adjacentTileAbove;
        }
        return null;
    }

    private Tile getAdjacentTileToLeft(Board board, List<Tile> permanentPlacedTiles, Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();

        Tile adjacentTileToLeft = board.hasTileToLeft(row, col) ? board.getTileAt(row - 1, col) : null;
        if (adjacentTileToLeft != null && permanentPlacedTiles.contains(adjacentTileToLeft)) {
            return adjacentTileToLeft;
        }
        return null;
    }

    private Tile getAdjacentTileBelow(Board board, List<Tile> permanentPlacedTiles, Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();

        Tile adjacentTileBelow = board.hasTileBelow(row, col) ? board.getTileAt(row, col + 1) : null;
        if (adjacentTileBelow != null && permanentPlacedTiles.contains(adjacentTileBelow)) {
            return adjacentTileBelow;
        }
        return null;
    }

    private Tile getAdjacentTileToRight(Board board, List<Tile> permanentPlacedTiles, Tile tile) {
        int row = tile.getRow();
        int col = tile.getCol();

        Tile adjacentTileToRight = board.hasTileToRight(row, col) ? board.getTileAt(row + 1, col) : null;
        if (adjacentTileToRight != null && permanentPlacedTiles.contains(adjacentTileToRight)) {
            return adjacentTileToRight;
        }
        return null;
    }

    private List<String> getFormedWords(Board board, List<Tile> placedTiles, List<Tile> permanentPlacedTiles) {
        Set<String> formedWords = new HashSet<>(); // Change to Set to prevent duplicates

        for (Tile tile : placedTiles) {
            Tile adjacentTileAbove = getAdjacentTileAbove(board, permanentPlacedTiles, tile);
            Tile adjacentTileToLeft = getAdjacentTileToLeft(board, permanentPlacedTiles, tile);
            Tile adjacentTileBelow = getAdjacentTileBelow(board, permanentPlacedTiles, tile);
            Tile adjacentTileToRight = getAdjacentTileToRight(board, permanentPlacedTiles, tile);

            if (adjacentTileAbove != null || adjacentTileBelow != null) {
                StringBuilder verticalWord = new StringBuilder();

                int row = tile.getRow();
                int col = tile.getCol();
                while (board.hasTileAbove(row, col)) {
                    Tile aboveTile = board.getTileAt(row, col - 1);
                    verticalWord.insert(0, aboveTile.getLetter());
                    col--;
                }

                verticalWord.append(tile.getLetter());

                col = tile.getCol();
                while (board.hasTileBelow(row, col)) {
                    Tile belowTile = board.getTileAt(row, col + 1);
                    verticalWord.append(belowTile.getLetter());
                    col++;
                }

                String word = verticalWord.toString().replaceAll("\\s+", "");
                if (word.length() > 1 && word.contains(String.valueOf(tile.getLetter()))) {
                    formedWords.add(word);
                }
            }

            if (adjacentTileToLeft != null || adjacentTileToRight != null) {
                StringBuilder horizontalWord = new StringBuilder();

                int row = tile.getRow();
                int col = tile.getCol();
                while (board.hasTileToLeft(row, col)) {
                    Tile leftTile = board.getTileAt(row - 1, col);
                    horizontalWord.insert(0, leftTile.getLetter());
                    row--;
                }

                horizontalWord.append(tile.getLetter());

                row = tile.getRow();
                while (board.hasTileToRight(row, col)) {
                    Tile rightTile = board.getTileAt(row + 1, col);
                    horizontalWord.append(rightTile.getLetter());
                    row++;
                }

                String word = horizontalWord.toString().replaceAll("\\s+", "");
                if (word.length() > 1 && word.contains(String.valueOf(tile.getLetter()))) {
                    formedWords.add(word);
                }
            }
        }

        return new ArrayList<>(formedWords);
    }
    private List<AnchorSquare> findAnchorSquares() {
        List<AnchorSquare> anchorSquares = new ArrayList<>();
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Tile tile = board.getTileAt(row, col);
                if (tile == null || tile.getLetter() == ' ') {
                    int[][] neighbors = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
                    for (int[] neighbor : neighbors) {
                        int newRow = row + neighbor[0];
                        int newCol = col + neighbor[1];
                        if (newRow >= 0 && newRow < 15 && newCol >= 0 && newCol < 15) {
                            Tile neighborTile = board.getTileAt(newRow, newCol);
                            if (neighborTile != null && neighborTile.getLetter() != ' ') {
                                anchorSquares.add(new AnchorSquare(neighborTile.getLetter(), row, col));
                            }
                        }
                    }
                }
            }
        }
        return anchorSquares;
    }
    
    private void generateAIMove() {
        List<AnchorSquare> anchorSquares = findAnchorSquares();
        String rack = playerTiles.get(playerNames.get(currentPlayerIndex)).stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        List<String> availableMoves = new ArrayList<>();
        for (AnchorSquare anchorSquare : anchorSquares) {
            availableMoves.addAll(scrabbleSolver.findWordsFromAnchorSquare(anchorSquare, rack));
        }
        
        ScrabbleTile scrabbleTile = new ScrabbleTile();
        
        List<String> sortedMoves = scrabbleTile.sortMovesByValue(availableMoves);

        System.out.println("Available moves: " + availableMoves);
        System.out.println("Available moves sorted by value: " + sortedMoves);
        System.out.println(playerTiles);

     // Iterate through sortedMoves and try to place each word
        for (String move : sortedMoves) {
            for (AnchorSquare anchorSquare : anchorSquares) {
                // Check if the anchor letter is in the move
                int anchorIndex = move.indexOf(anchorSquare.getLetter());
                if (anchorIndex != -1) {
                    // Get the substring after the anchor letter
                    String afterAnchor = move.substring(anchorIndex + 1);

                    // Try to place the substring after the anchor letter
                    if (board.placeWord(afterAnchor, anchorSquare.getRow(), anchorSquare.getCol())) {
                        System.out.println("Placed word: " + move);

                        Direction direction = board.getWordDirection(anchorSquare.getRow(), anchorSquare.getCol());
                        for (int i = 0; i < move.length(); i++) {
                            if (i != anchorIndex) { // Only display tile image for non-anchor letters
                                int row = direction == Direction.HORIZONTAL ? anchorSquare.getRow() : anchorSquare.getRow() + (i - anchorIndex);
                                int col = direction == Direction.HORIZONTAL ? anchorSquare.getCol() + (i - anchorIndex) : anchorSquare.getCol();

                                System.out.println("Letter: " + move.charAt(i) + ", Row: " + row + ", Col: " + col);
                                displayTileImage(move.charAt(i), row, col);
                            }
                        }

                        board.printBoard();
                        return; // The word was successfully placed, so we can exit the loop
                    }
                }
            }
        }
    }
    
    
    public void displayTileImage(char letter, int row, int col) {
        ImageIcon tileImage = Tileimage.getTileImage(letter);
        JLabel labelt = new JLabel(tileImage);
        add(labelt);
        setComponentZOrder(labelt, 0);
        labelt.setBounds(row * 40 + board.getX(), col * 40 + board.getY(), 40, 40);
        tileLabels.add(labelt);
        disableTileEvents(labelt);
    }
    
    
    
    
    

}
