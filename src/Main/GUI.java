package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicCheckBoxUI;

public class GUI {
	public static JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		UIManager.put("TextField.background", new Color(50, 50, 50));
		button.setForeground(Color.CYAN);
		button.setBackground(new Color(50, 50, 50));
		return button;
	}
	public static JCheckBox createStyledCheckbox(String label) {
        JCheckBox checkbox = new JCheckBox(label);
        checkbox.setForeground(Color.CYAN);
        checkbox.setBackground(Color.DARK_GRAY);
        checkbox.setBorderPaintedFlat(true);
        checkbox.setFocusPainted(false);
      
        checkbox.setUI(new BasicCheckBoxUI() {
            @Override
            protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
                // Customize the appearance of the checkbox square
                g.setColor(Color.CYAN); // Set the color you want for the square
                g.fillRect(iconRect.x, iconRect.y, iconRect.width, iconRect.height);
            }
        });

        return checkbox;
    }

	public static GridBagConstraints createButtonConstraints(int gridx, int gridy) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridx;
		constraints.gridy = gridy;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.insets = new Insets(3, 3, 3, 3); // Default insets
		return constraints;
	}
	
	 public static void updateTextField() {
	        try {
	            // Read the next line from the file and update the text field
	            String line;
	            if ((line = Program.fileReader.readLine()) != null) {
	            	Program.textField.setText(line);
	                Program.addButton.doClick();
	                try {
	                    Thread.sleep(1500); // Sleep for 3 seconds (3000 milliseconds)
	                } catch (InterruptedException e) {
	                    e.printStackTrace(); // Handle the InterruptedException if necessary
	                }
	                Program.applyButton.doClick();

	                Utils.clickOnScreen(405, 470);
	                Utils.delay();
	                try {
	                    Thread.sleep(1500); // Sleep for 3 seconds (3000 milliseconds)
	                } catch (InterruptedException e) {
	                    e.printStackTrace(); // Handle the InterruptedException if necessary
	                }
	                System.out.println("Line is " + line);
	            } else {
	                // Stop the timer when there are no more lines to read
	            	Program.timer.stop();
	                // Close the file reader
	            	Program.fileReader.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
