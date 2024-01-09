package Main;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Utils {
	private static Timer timer;
	public static int initialX;
	public static int initialY;	
	
	public static void paste() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
	public static void clickOnScreen(int x, int y) {
        
        Program.bot.mouseMove(x, y);
        Program.bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Program.bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
	public static void mix(Object... instructions) {
        for (Object instruction : instructions) {
            if (instruction instanceof int[][]) {
                int[][] coords = (int[][]) instruction;
                for (int[] coord : coords) {
                    int x = coord[0];
                    int y = coord[1];
                    clickOnScreen(x, y);
                }
            }
        }
    }
	   public static void moveFromTo(int[][] startCoords, int[][] endCoords) {
	        try {
	            Robot robot = new Robot();
	            int startX = startCoords[0][0];
	            int startY = startCoords[0][1];
	            int endX = endCoords[0][0];
	            int endY = endCoords[0][1];
	            robot.mouseMove(startX, startY);
	            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	            delay();
	            robot.mouseMove(endX, endY);
	            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	        } catch (AWTException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void delay() {
	        int time = Program.delay_duration[0][0];
	        try {
	            Thread.sleep(time);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }	 

	    public static class ApplyButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String input = Program.textField.getText();
	            String[] parts = input.split("[.,\\s]+");

	            if (parts.length == 6) {
	                performCalculation(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
	            } else if (parts.length == 2 || parts.length == 3) {
	                performCalculation(parts[0], parts[1], "0", parts[0], parts[1], "5");
	            } else if (parts.length == 4) {
	                Utils.moveFromTo(Program.menu_start_pos, Program.menu_end_pos);
	                performCalculation(parts[0], parts[1], "0", parts[2], parts[3], "0");
	            } else if (parts.length == 9) {
	            	 Utils.moveFromTo(Program.menu_start_pos, Program.menu_end_pos);
	                performCalculation(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
	                performCalculation(parts[3], parts[4], parts[5], parts[6], parts[7], parts[8]);
	            }
	        }

	        private void performCalculation(String xHoursStr, String xMinutesStr, String xSecondsStr,
	                                        String yHoursStr, String yMinutesStr, String ySecondsStr) {
	            try {
	                int xHours = Integer.parseInt(xHoursStr);
	                int xMinutes = Integer.parseInt(xMinutesStr);
	                int xSeconds = Integer.parseInt(xSecondsStr);

	                int yHours = Integer.parseInt(yHoursStr);
	                int yMinutes = Integer.parseInt(yMinutesStr);
	                int ySeconds = Integer.parseInt(ySecondsStr);

	                int zHours = yHours - xHours;
	                int zMinutes = yMinutes - xMinutes;
	                int zSeconds = ySeconds - xSeconds;

	                if (zMinutes < 0) {
	                    zHours -= 1;
	                    zMinutes += 60;
	                }
	                if (zSeconds < 0) {
	                    zMinutes -= 1;
	                    zSeconds += 60;
	                }

	                if (zHours >= 0 && zHours < Program.time1.length && zMinutes >= 0 && zMinutes < Program.time1[0].length && zSeconds >= 0 && zSeconds < Program.time1[0][0].length) {
	                	Program.time1[zHours][zMinutes][zSeconds] = 1;
	                    Program.time2[zHours][zMinutes][zSeconds] = 2;
	                    Program.time3[zHours][zMinutes][zSeconds] = 3;

	                    int x1 = xHours;
	                    int y1 = xMinutes;
	                    int z1 = xSeconds;

	                    int x2 = yHours;
	                    int y2 = yMinutes;
	                    int z2 = ySeconds;

	                    int x3 = zHours;
	                    int y3 = zMinutes;
	                    int z3 = zSeconds;

	                    if (x1 < Program.time1.length && y1 < Program.time1[0].length && z1 < Program.time1[0][0].length &&
	                            x2 < Program.time2.length && y2 < Program.time2[0].length && z2 < Program.time2[0][0].length &&
	                            x3 < Program.time3.length && y3 < Program.time3[0].length && z3 < Program.time3[0][0].length) {

	                    	Program.time1[x1][y1][z1] = 1;
	                    	Program.time2[x2][y2][z2] = 2;
	                    	Program. time3[x1][y1][z1] = 3;

	                        roll(-x1, 140, 160);
	                        roll(-y1, 180, 160);
	                        roll(-z1, 220, 160);
	                        roll(-x2, 140, 190);
	                        roll(-y2, 180, 190);
	                        roll(-z2, 220, 190);
	                        roll(-x3, 140, 230);
	                        roll(-y3, 180, 230);
	                        roll(-z3, 220, 230);

	                        System.out.println("Data changed: time1[" + x1 + "][" + y1 + "][" + z1 + "] and time2[" + x2 + "][" + y2 + "][" + z2 + "]" + " and time3[" + x3 + "][" + y3 + "][" + z3 + "]");
	                    }
	                }
	            } catch (AWTException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }

	    public static void roll(int scrollAmount, int x, int y) throws AWTException {
	        Robot robot = new Robot();
	        robot.mouseMove(x, y);
	        if(Program.delay_duration[0][0]<=200) {
	        	 robot.delay(100);
	        	 System.out.println("delay is less than 200");
	        }else {
	        	 robot.delay((Program.delay_duration[0][0])/2);
	        	 System.out.println("delay is bigger than 200");
	        }
	       

	        for (int i = 0; i < Math.abs(scrollAmount); i++) {
	            robot.mouseWheel((scrollAmount < 0) ? -1 : 1);
	            robot.mouseMove(x, y);
	            robot.delay(5);
	        }
	    }
	    
	    public static void setCoords(int lineNumber, String newText) {
	    	
		
			JFrame frame = new JFrame("Coordinates");
				
			frame.setUndecorated(true);
			frame.setSize(250, 100);
			frame.setLocationRelativeTo(null);
			frame.getContentPane().setBackground(Color.DARK_GRAY);
			JLabel label = new JLabel("Coordinates: X=0, Y=0");
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Arial", Font.BOLD, 16));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
			frame.add(label);
			frame.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						updateConfig(lineNumber, newText);
					}
				}
			});
			timer = new Timer(30, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int x = MouseInfo.getPointerInfo().getLocation().x;
					int y = MouseInfo.getPointerInfo().getLocation().y;
					label.setText("<html>Coordinates: X=" + x + ", Y=" + y + "<br>Changing: " + newText
							+ "<br>Enter to apply " + "</html>");
				}
			});
			JButton closeButton = new JButton();
			closeButton.setBackground(Color.RED);
			closeButton.setPreferredSize(new Dimension(10, 10));
			closeButton.setBorder(null);
			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.setOpaque(false);
			buttonPanel.add(closeButton);
			frame.add(buttonPanel, BorderLayout.SOUTH);

			frame.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					initialX = e.getX();
					initialY = e.getY();
				}
			});
			frame.addMouseMotionListener(new MouseAdapter() {
				public void mouseDragged(MouseEvent e) {
					int currentX = frame.getLocation().x + e.getX() - initialX;
					int currentY = frame.getLocation().y + e.getY() - initialY;
					frame.setLocation(currentX, currentY);
				}
			});
			frame.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					timer.stop();
				}
			});
			frame.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						updateConfig(lineNumber, newText);
						frame.dispose();
					}
				}
			});
			timer.start();
			frame.setAlwaysOnTop(true);
			frame.setVisible(true);
			frame.requestFocus();
		}
	    
	    public static void updateConfig(int lineNumber, String newText) {
			try {
				File configFile = new File("settings.txt");
				ArrayList<String> lines = new ArrayList<>();

				try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
					String line;
					while ((line = reader.readLine()) != null) {
						lines.add(line);
					}
				}
				if (lineNumber >= 1 && lineNumber <= lines.size()) {
					int cursorX = MouseInfo.getPointerInfo().getLocation().x;
					int cursorY = MouseInfo.getPointerInfo().getLocation().y;

					String updatedLine = String.format("%d,%d // changed `%s`", cursorX, cursorY, newText);
					lines.set(lineNumber - 1, updatedLine);
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
						for (String updated : lines) {
							writer.write(updated);
							writer.newLine();
						}
					}
				} else {
					System.out.println("Invalid line number.");
				}
			} catch (IOException ex) {
				System.err.println("An error occurred: " + ex.getMessage());
			}
		}	
	    
	    public static void startTimer() {
	        // Start the timer only once when the button is pressed
	        if (!Program.timer.isRunning()) {
	            Program.timer.start();
	        }
	    }
}
