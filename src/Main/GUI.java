package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
	
	public static void updateTextField()  {
	    try {
	        String line;
	        if ((line = Program.fileReader.readLine()) != null) {
	            if (isValidLine(line)) {
	            	Program.textField.setText(line);
	                Program.addButton.doClick();
	                Thread.sleep(1500);
	                Program.applyButton.doClick();
	                Utils.clickOnScreen(405, 470);
	                Thread.sleep(1500);
	                System.out.println("Line is " + line);
	            } else {
	                // Skip the invalid line, copy it to the buffer, or handle as needed
	                System.out.println("Skipping invalid line: " + line);
	                copyToClipboard(line);
	            }
	        } else {
	            Program.timer.stop();
	            Program.fileReader.close();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void copyToClipboard(String text) {
	    StringSelection selection = new StringSelection(text);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}

	private static boolean isValidLine(String line) {
	    // Check if the line contains only numbers, commas, dots, or empty spaces
	    return line.matches("[0-9,\\s.]+");
	}
}
