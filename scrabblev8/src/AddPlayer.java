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

public class AddPlayer extends JPanel implements ActionListener {
private int counter = 0;
private static List<JTextField> textFields = new ArrayList<JTextField>();
private JButton addButton, submitButton;
private JLabel label;


public AddPlayer() {
    setLayout(new FlowLayout());
    label = new JLabel("Enter Names");
    add(label);
    addButton = new JButton("+");
    addButton.addActionListener(this);
    add(addButton);
    submitButton = new JButton("Submit");
    submitButton.addActionListener(this);
    add(submitButton);
    setSize(500, 200);
    
    
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
	        Window window = new Window();
	        add(window);
	        revalidate();
	        repaint();
        }
    }
}

public static List<String> getNames() {
    List<String> names = new ArrayList<String>();
    for (JTextField textField : textFields) {
        names.add(textField.getText());
    }
    return names;
    
    
}

}