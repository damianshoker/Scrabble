package Control;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

import GUI.Window;

public class AddPlayer extends JPanel implements ActionListener {
	
	private Image backgroundImage;
private int counter = 0;
private static List<JTextField> textFields = new ArrayList<JTextField>();
private JButton addButton, submitButton, resetButton;

private JLabel label;


public AddPlayer() {
	try {
        backgroundImage = ImageIO.read(getClass().getResource("/scrabble_board.jpg"));
    } catch (IOException e) {
        e.printStackTrace();
    }
    setLayout(new FlowLayout());
    label = new JLabel("Enter Names");
    label.setForeground(Color.white);
    add(label);
    addButton = new JButton("+");
    addButton.addActionListener(this);
    add(addButton);
    submitButton = new JButton("Submit");
    submitButton.addActionListener(this);
    add(submitButton);
    setSize(500, 200);
    resetButton = new JButton("Reset");
    resetButton.addActionListener(this);
    
    add(resetButton);

}


@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
}

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addButton) {
        if (counter < 4) {
            JTextField textField = new JTextField(10);
            textFields.add(textField);
            add(textField);
            counter++;
            if (counter == 4) {
                addButton.setEnabled(false);
            }
            revalidate();
            repaint();
        }
    } else if (e.getSource() == submitButton) {
    	List<String> names = getNames();

    	List<String> uniqueNames = new ArrayList<String>();
    	for (String name : names) {
    	    if (name.isEmpty()) {
    	        JOptionPane.showMessageDialog(this, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
    	        return;
    	    } else if (uniqueNames.contains(name)) {
    	        JOptionPane.showMessageDialog(this, "Please enter unique names", "Error", JOptionPane.ERROR_MESSAGE);
    	        return;
    	    } else {
    	        uniqueNames.add(name);

    	    }
    	}
    	if (uniqueNames.size() < 2) {
    	    JOptionPane.showMessageDialog(this, "Please enter at least 2 names", "Error", JOptionPane.ERROR_MESSAGE);
    	} else {

	        removeAll();
	        
	        backgroundImage = null;
	        repaint();
	        
	        Window window = new Window();
	        add(window);
	        revalidate();
	        repaint();
        }
    }
    if (e.getSource() == resetButton) {
        resetForm();
    }
}

private void resetForm() {
    for (JTextField textField : textFields) {
        remove(textField);
    }
    textFields.clear();
    counter = 0;
    addButton.setEnabled(true);
    revalidate();
    repaint();
}


public static List<String> getNames() {
    List<String> names = new ArrayList<String>();
    for (JTextField textField : textFields) {
        names.add(textField.getText());
    }
    return names;


}

}