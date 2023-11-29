import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mainmenu extends JPanel{
	
	
	
	 public static void main(String[] args) {
	    	
	    	JFrame frame = new JFrame("Fit to Screen");
	    	Mainmenu contents = new Mainmenu();
	    	frame.setContentPane(contents);
	    	frame.pack();
	    	frame.setResizable(false);
	    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        frame.setVisible(true);
	        
	        
	    	 
	    	 
	    	 
	    	 
	      
	 }
	 
	 public Mainmenu() {
		 
		 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		 
		 
	 
		 JButton singleplayButton = new JButton("Single Play");
		 singleplayButton.setBackground(Color.GRAY);
		 singleplayButton.addActionListener((ActionListener) new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        removeAll();
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
		        //
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
	    
		 
		 JButton QUITButton = new JButton("Quit");
		 QUITButton.setBackground(Color.GRAY);
		 QUITButton.addActionListener((ActionListener) new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		    	  System.exit(0);
		      }
		    });
		 
		 JLabel titleLabel = new JLabel("SCRABBLE");
		    titleLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 36));
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
	    
	    
	    
	    
	  
}
}

