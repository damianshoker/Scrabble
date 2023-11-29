package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Control.AddAIplayer;
import Control.AddPlayer;

public class Mainmenu extends JPanel{
	
	private Image backgroundImage;



	 public static void main(String[] args) {

	    	JFrame frame = new JFrame("Scrabble Game");
	    	Mainmenu contents = new Mainmenu();
	    	frame.setContentPane(contents);
	    	frame.pack();
	    	frame.setResizable(false);
	    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        frame.setVisible(true);







	 }

	 public Mainmenu() {
		 
		 try {
	            backgroundImage = ImageIO.read(getClass().getResource("/scrabble_board.jpg"));
	            int newWidth = 1476;
	            int newHeight = 900;
	            backgroundImage = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));



		 JButton singleplayButton = new JButton("Single Play");
		 singleplayButton.setBackground(Color.GRAY);
		 singleplayButton.addActionListener((ActionListener) new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  removeAll();
			        
			        backgroundImage = null;
			        repaint();
		        AddPlayer addplayer = new AddPlayer();
		        add(addplayer);
		        revalidate();
		        repaint();
		      }
		    });

		 JButton AIplayButton = new JButton("AI Play");
		 AIplayButton.setBackground(Color.GRAY);
		 AIplayButton.addActionListener((ActionListener) new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  removeAll();
			        
			        backgroundImage = null;
			        repaint();
		    	  AddAIplayer addaiplayer = new AddAIplayer();
			       add(addaiplayer);
			       revalidate();
			       repaint();
		      }
		    });

		 JButton OnlineplayButton = new JButton("Online");
		 OnlineplayButton.setBackground(Color.GRAY);
		 OnlineplayButton.addActionListener((ActionListener) new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        //
		      }
		    });
		 
		 JButton helpButton = new JButton("Help");
	        helpButton.setBackground(Color.GRAY);
	        helpButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JFrame helpFrame = new JFrame("Scrabble Rules");
	                
	                helpFrame.setSize(500, 600);
	                helpFrame.setResizable(false);
	                helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                JTextArea rulesTextArea = new JTextArea();
	                rulesTextArea.setText("Scrabble Rules:\n\n"
	                        + "1. Setup: Each player draws seven tiles from the tile bag. The game board should be empty at the start.\n"
	                        + "2. First word: The first player forms a word using their tiles and places it horizontally or vertically on the board, making sure that one of the letters is on the central square. The player then calculates the word's score and records it on the score sheet. After that, the player draws new tiles from the bag to replace the ones used.\n"
	                        + "3. Subsequent turns: Players take turns forming new words using their tiles. Each new word must be connected to at least one existing word on the board by sharing a common letter. New words can be formed horizontally or vertically but not diagonally. Players cannot rearrange tiles already on the board.\n"
	                        + "4. Scoring: Points are awarded based on the value of the tiles used in a word, with each tile having a designated point value. Bonus points are earned by placing tiles on special squares, such as double or triple letter scores and double or triple word scores.\n"
	                        + "5. Exchanging tiles: Instead of forming a word, a player can choose to exchange a tile for a new one from the bag. This counts as a turn.\n"
	                        + "6. End of the game: The game ends when all tiles have been drawn and one player uses their last tile, or when no player can form a new word. The player with the highest score wins.\n\n"
	                        + "Note: Different scrabble games may have different rules..");

	                rulesTextArea.setEditable(false);
	                rulesTextArea.setLineWrap(true);
	                rulesTextArea.setWrapStyleWord(true);
	                helpFrame.add(rulesTextArea);

	                helpFrame.setVisible(true);
	            }
	        });

	        


		 JButton QUITButton = new JButton("Quit");
		 QUITButton.setBackground(Color.GRAY);
		 QUITButton.addActionListener((ActionListener) new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  System.exit(0);
		      }
		    });

		 JLabel titleLabel = new JLabel("SCRABBLE");
		 titleLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 72)); // Increase the font size
		 titleLabel.setForeground(Color.white); // Set a custom text color
		 titleLabel.setHorizontalAlignment(JLabel.CENTER);


		    add(titleLabel);
		    add(Box.createRigidArea(new Dimension(300, 200)));
		    add(singleplayButton);
		    add(Box.createRigidArea(new Dimension(0, 20)));
		    add(AIplayButton);
		    add(Box.createRigidArea(new Dimension(0, 20)));
		    add(OnlineplayButton);
		    add(Box.createRigidArea(new Dimension(0, 20)));
		    add(QUITButton);
		    add(Box.createRigidArea(new Dimension(0, 20)));
	        add(helpButton);





}
	 
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (backgroundImage != null) {
	            g.drawImage(backgroundImage, 0, 0, this);
	        }
	    }

}

