import java.awt.Component;
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

public class AddAIplayer extends JPanel implements ActionListener {
    private int counter = 0;
    private int aiCounter = 1;
    private static List<JTextField> textFields = new ArrayList<JTextField>();
    private JButton addButton, submitButton, addAIButton;
    private JLabel label;

    public AddAIplayer() {
        setLayout(new FlowLayout());
        label = new JLabel("Enter Names");
        add(label);
        addButton = new JButton("+");
        addButton.addActionListener(this);
        add(addButton);
        addAIButton = new JButton("Add AI");
        addAIButton.addActionListener(this);
        add(addAIButton);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton);
        setSize(500, 200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton || e.getSource() == addAIButton) {
            if (counter < 4) {
                JTextField textField;
                if (e.getSource() == addButton) {
                    textField = new JTextField(10);
                } else {
                    textField = new JTextField("AI " + aiCounter, 10);
                    textField.setEditable(false);
                    aiCounter++;
                }
                textFields.add(textField);
                add(textField);
                counter++;

                if (counter == 4) {
                    addButton.setEnabled(false);
                    addAIButton.setEnabled(false);
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

            if (uniqueNames.size() < 2 || getPlayerCount() < 1 || getAICount() < 1) {
                JOptionPane.showMessageDialog(this, "Please enter at least 1 human player, 1 AI player, and 2 total names", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
            	removeAll();
    	        AiWindow aiwindow = new AiWindow();
    	        add(aiwindow);
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

    public int getPlayerCount() {
        int playerCount = 0;
        for (JTextField textField : textFields) {
            String name = textField.getText();
            if (!name.startsWith("AI ")) {
                playerCount++;
            }
        }
        return playerCount;
    }

    public int getAICount() {
        int aiCount = 0;
        for (JTextField textField : textFields) {
            String name = textField.getText();
            if (name.startsWith("AI ")) {
                aiCount++;
            }
        }
        return aiCount;
    }
    public List<String> getPlayerAndAICount() {
        List<String> names = new ArrayList<>();
        for (JTextField textField : textFields) {
            names.add(textField.getText());
        }
        return names;
    }
}