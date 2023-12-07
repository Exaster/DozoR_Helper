package Result;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Final extends JFrame {
    public static final long serialVersionUID = 1L;
    public static final String CONFIG_FILE = "settings.txt";

    // Configuration variables
    public static int[][] AddPosition;
    public static int[][] MenuStartPosition;
    public static int[][] MenuEndPosition;
    public static int[][] Delay_duration;
    public static int[][] check_1_pos;
    public static int[][] check_2_pos;
    public static int[][] check_3_pos;
    public static int[][] check_4_pos;
    public static int[][] option_menu;
    public static int[][] option_select;
    public static int[][] option_field;
    public static int[][] item;
    public static int[][] event_button;
    public static int[][] event_choose_button;
    public static int[][] event_del_button;
    public static int[][] shed_start;
    public static int[][] shed_end;
    private JTextField textField;
    private int[][][] time1;
    private int[][][] time2;
    private int[][][] time3;
    
    // Robot instance for simulating mouse and keyboard actions
    public static Robot bot;

    public Final(int size) {
        // Load configuration and initialize Robot
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            createDefaultConfigFile();
        } else {
            loadConfigData();
        }
        try {
            bot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // Initialize the data arrays
        time1 = new int[size][size][size];
        time2 = new int[size][size][size];
        time3 = new int[size][size][size];

        // JFrame setup
        setTitle("Input Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create and configure UI elements
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300, 30));
       
        JButton applyButton = new JButton("Час");
        JButton addButton = new JButton("Додати");
        JButton clearButton = new JButton("Очистити");
        JButton delButton = new JButton("Рейси");
        JButton plusFiveButton = new JButton("+5");
        // Apply dark gray background color
        UIManager.put("TextField.background", new Color(50, 50, 50));
        textField.setForeground(Color.CYAN);
        textField.setFont(new Font("Arial", Font.BOLD, 18));
        textField.setBackground(Color.BLACK);

        // Configure button colors
        textField.setForeground(Color.CYAN);
        textField.setBackground(Color.DARK_GRAY);
        addButton.setBackground(new Color(50, 50, 50));
        addButton.setForeground(Color.CYAN);
        applyButton.setBackground(new Color(50, 50, 50));
        applyButton.setForeground(Color.CYAN);
        clearButton.setBackground(new Color(50, 50, 50));
        clearButton.setForeground(Color.CYAN);
        delButton.setBackground(new Color(50, 50, 50));
        delButton.setForeground(Color.CYAN);
        plusFiveButton.setBackground(new Color(50, 50, 50));
        plusFiveButton.setForeground(Color.CYAN);
        
        clearButton.addActionListener(e -> textField.setText(""));

        delButton.addActionListener(e -> {
        	mix(AddPosition,AddPosition);
        	moveFromTo( shed_start, shed_end);
        	
        });
       
        // Add ActionListener to the Add button
        addButton.addActionListener(e -> {
            mix(AddPosition,AddPosition);
            Delay();
            moveFromTo(MenuStartPosition, MenuEndPosition);
            mix(check_1_pos, check_2_pos, check_3_pos, check_4_pos, option_menu, option_field, option_field);
            try {
                paste();
            } catch (AWTException a) {
                a.printStackTrace();
            }
            Delay();
            mix(item,item);
        });


        // Set GridBagConstraints for UI elements
        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = 0;
        textFieldConstraints.gridwidth = 3;
        textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        textFieldConstraints.weightx = 1.0;
        textFieldConstraints.insets = new Insets(3, 3, 3, 3);

     // First row
        GridBagConstraints applyButtonConstraints = new GridBagConstraints();
        applyButtonConstraints.gridx = 0;
        applyButtonConstraints.gridy = 1;
        applyButtonConstraints.gridwidth = 1;
        applyButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        applyButtonConstraints.weightx = 0.5;
        applyButtonConstraints.insets = new Insets(3, 3, 3, 3);  // Decrease the bottom inset

        GridBagConstraints addButtonConstraints = new GridBagConstraints();
        addButtonConstraints.gridx = 1;
        addButtonConstraints.gridy = 1;
        addButtonConstraints.gridwidth = 1;
        addButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        addButtonConstraints.weightx = 0.5;
        addButtonConstraints.insets = new Insets(3, 3, 3, 3);  // Decrease the bottom inset

        
        GridBagConstraints clearButtonConstraints = new GridBagConstraints();
        clearButtonConstraints.gridx = 0;
        clearButtonConstraints.gridy = 2;  // Place clearButton in the second row
        clearButtonConstraints.gridwidth = 1;
        clearButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        clearButtonConstraints.weightx = 0.5;
        clearButtonConstraints.insets = new Insets(3, 3, 3, 3);  // Decrease the top inset

        GridBagConstraints delButtonConstraints = new GridBagConstraints();
        delButtonConstraints.gridx = 1;
        delButtonConstraints.gridy = 2;  // Place delButton in the second row
        delButtonConstraints.gridwidth = 1;
        delButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        delButtonConstraints.weightx = 0.5;
        delButtonConstraints.insets = new Insets(3, 3, 3, 3);  // Decrease the top inset
        
        


        

        // Add components to the JFrame
        add(textField, textFieldConstraints);
        add(applyButton, applyButtonConstraints);
        add(addButton, addButtonConstraints);
        add(clearButton, clearButtonConstraints);
        add(delButton, delButtonConstraints);
      
        


        // Add ActionListener for the Apply button
        applyButton.addActionListener(new ApplyButtonListener());
        

        // Start a thread to watch for changes in the config file
        Thread configFileThread = new Thread(() -> watchConfigFile(configFile));
        configFileThread.start();

        // JFrame configuration
        getContentPane().setBackground(Color.DARK_GRAY);
        setTitle("Dozor Helper");
        setSize(195, 180);
        setVisible(true);
        setAlwaysOnTop(true);

        // Close the application when the window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Erasing Thread to free RAM");
                System.exit(0);
            }
        });
    }

    // Helper function to simulate a paste action
    public static void paste() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }


    private class ApplyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = textField.getText();
            String[] parts = input.split("[.,\\s]+");
            if (parts.length == 6) { // Check for 6 input values (hours and minutes)
                try {
                    int xHours = Integer.parseInt(parts[0]);
                    int xMinutes = Integer.parseInt(parts[1]);
                    int xSeconds = Integer.parseInt(parts[2]);

                    int yHours = Integer.parseInt(parts[3]);
                    int yMinutes = Integer.parseInt(parts[4]);
                    int ySeconds = Integer.parseInt(parts[5]);

                    // Calculate the differences
                    int zHours = yHours - xHours;
                    int zMinutes = yMinutes - xMinutes;
                    int zSeconds = ySeconds - xSeconds;

                    // Check for negative values in minutes and seconds and adjust if needed
                    if (zMinutes < 0) {
                        zHours -= 1;
                        zMinutes += 60;
                    }
                    if (zSeconds < 0) {
                        zMinutes -= 1;
                        zSeconds += 60;
                    }

                    // Check if the indices are within the bounds of the arrays
                    if (zHours >= 0 && zHours < time1.length && zMinutes >= 0 && zMinutes < time1[0].length && zSeconds >= 0 && zSeconds < time1[0][0].length) {
                        time1[zHours][zMinutes][zSeconds] = 1;
                        time2[zHours][zMinutes][zSeconds] = 2;
                        time3[zHours][zMinutes][zSeconds] = 3;

                        // Calculate indices for the additional functionality
                        int x1 = xHours;
                        int y1 = xMinutes;
                        int z1 = xSeconds;

                        int x2 = yHours;
                        int y2 = yMinutes;
                        int z2 = ySeconds;

                        int x3 = zHours;
                        int y3 = zMinutes;
                        int z3 = zSeconds;

                        
                        if (x1 < time1.length && y1 < time1[0].length && z1 < time1[0][0].length &&
                                x2 < time2.length && y2 < time2[0].length && z2 < time2[0][0].length &&
                                x3 < time3.length && y3 < time3[0].length && z3 < time3[0][0].length) {
                            
                            time1[x1][y1][z1] = 1;
                            time2[x2][y2][z2] = 2;
                            time3[x1][y1][z1] = 3;
                            
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
                }catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            if (parts.length == 2 || parts.length==3 ) { // Check for 6 input values (hours and minutes)
                try {
                    int xHours = Integer.parseInt(parts[0]);
                    int xMinutes = Integer.parseInt(parts[1]);
                    int xSeconds = 0;

                    int yHours = xHours;
                    int yMinutes = xMinutes;
                    int ySeconds = xSeconds;

                    // Calculate the differences
                    int zHours = yHours - xHours;
                    int zMinutes = yMinutes - xMinutes;
                    int zSeconds = 5;

                    // Check for negative values in minutes and seconds and adjust if needed
                    if (zMinutes < 0) {
                        zHours -= 1;
                        zMinutes += 60;
                    }
                    if (zSeconds < 0) {
                        zMinutes -= 1;
                        zSeconds += 60;
                    }

                    // Check if the indices are within the bounds of the arrays
                    if (zHours >= 0 && zHours < time1.length && zMinutes >= 0 && zMinutes < time1[0].length && zSeconds >= 0 && zSeconds < time1[0][0].length) {
                        time1[zHours][zMinutes][zSeconds] = 1;
                        time2[zHours][zMinutes][zSeconds] = 2;
                        time3[zHours][zMinutes][zSeconds] = 3;

                        // Calculate indices for the additional functionality
                        int x1 = xHours;
                        int y1 = xMinutes;
                        int z1 = xSeconds;

                        int x2 = yHours;
                        int y2 = yMinutes;
                        int z2 = ySeconds+5;

                        int x3 = zHours;
                        int y3 = zMinutes;
                        int z3 = zSeconds;

                        // Check if the new indices are within bounds
                        if (x1 < time1.length && y1 < time1[0].length && z1 < time1[0][0].length &&
                                x2 < time2.length && y2 < time2[0].length && z2 < time2[0][0].length &&
                                x3 < time3.length && y3 < time3[0].length && z3 < time3[0][0].length) {
                            // Update the arrays and perform the clicks
                            time1[x1][y1][z1] = 1;
                            time2[x2][y2][z2] = 2;
                            time3[x1][y1][z1] = 3;
                            
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
                }catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
                }
                }
            if (parts.length == 9) { // Check for 6 input values (hours and minutes)
                try {
                	moveFromTo(MenuStartPosition, MenuEndPosition);
                    
                    //Delay();
                    //mix(item,item);
                    int xHours = Integer.parseInt(parts[0]);
                    int xMinutes = Integer.parseInt(parts[1]);
                    int xSeconds = Integer.parseInt(parts[2]);

                    int yHours = Integer.parseInt(parts[3]);
                    int yMinutes = Integer.parseInt(parts[4]);
                    int ySeconds = Integer.parseInt(parts[5]);

                    // Calculate the differences
                    int zHours = Integer.parseInt(parts[6]);
                    int zMinutes = Integer.parseInt(parts[7]);
                    int zSeconds = Integer.parseInt(parts[8]);

                    // Check for negative values in minutes and seconds and adjust if needed
                    if (zMinutes < 0) {
                        zHours -= 1;
                        zMinutes += 60;
                    }
                    if (zSeconds < 0) {
                        zMinutes -= 1;
                        zSeconds += 60;
                    }

                    // Check if the indices are within the bounds of the arrays
                    if (zHours >= 0 && zHours < time1.length && zMinutes >= 0 && zMinutes < time1[0].length && zSeconds >= 0 && zSeconds < time1[0][0].length) {
                        time1[zHours][zMinutes][zSeconds] = 1;
                        time2[zHours][zMinutes][zSeconds] = 2;
                        time3[zHours][zMinutes][zSeconds] = 3;

                        // Calculate indices for the additional functionality
                        int x1 = xHours;
                        int y1 = xMinutes;
                        int z1 = xSeconds;

                        int x2 = yHours;
                        int y2 = yMinutes;
                        int z2 = ySeconds;

                        int x3 = zHours;
                        int y3 = zMinutes;
                        int z3 = zSeconds;

                        
                        if (x1 < time1.length && y1 < time1[0].length && z1 < time1[0][0].length &&
                                x2 < time2.length && y2 < time2[0].length && z2 < time2[0][0].length &&
                                x3 < time3.length && y3 < time3[0].length && z3 < time3[0][0].length) {
                            
                            time1[x1][y1][z1] = 1;
                            time2[x2][y2][z2] = 2;
                            time3[x1][y1][z1] = 3;
                            
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
                }catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        }
        
        
        
    }




 


    // Helper function to simulate mouse wheel scrolling
    public void roll(int scrollAmount, int x, int y) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        robot.delay(100);

        for (int i = 0; i < Math.abs(scrollAmount); i++) {
            robot.mouseWheel((scrollAmount < 0) ? -1 : 1);
            robot.mouseMove(x, y);
            robot.delay(5);
        }
    }
    
  
    // Helper function to perform a mix of mouse clicks and text pasting
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

    // Helper function to move the mouse from start coordinates to end coordinates
    public static void moveFromTo(int[][] startCoords, int[][] endCoords) {
        try {
            Robot robot = new Robot();
            int startX = startCoords[0][0];
            int startY = startCoords[0][1];
            int endX = endCoords[0][0];
            int endY = endCoords[0][1];
            robot.mouseMove(startX, startY);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            Delay();
            robot.mouseMove(endX, endY);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // Helper function to create a default configuration file
    private static void createDefaultConfigFile() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILE);
            String[] defaultData = {
                "535,170 // Default data for AddPosition",
                "506,155 // Default data for Menu start position",
                "145,70 // Default data for Menu end position",
                "100,0 // Default data for elay",
                "270,295 // Default data for checkbox 1",
                "270,315 // Default data for checkbox 2",
                "270,335 // Default data for checkbox 3",
                "270,350 // Default data for checkbox 4",
                "215,110 // Default data for option menu",
                "215,135 // Default data for option select",
                "215,135 // Default data for option field",
                "215,145 // Default data for option item",
                "650,20 // Default data for Event button",
                "150,80 // Default data for Event choose button",
                "160,60 // Default data for Event delete button",
                "627,303 // Default data for Shedule start position",
                "158,118 // Default data for Shedule end position",
            };
            for (String line : defaultData) {
                writer.write(line + "\n");
            }
            System.out.println("Default data written to the file.");
            writer.close();
            loadConfigData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper function to load configuration data from the file
    public static void loadConfigData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
            String line;
            int AddPositionRow = 0;
            int MenuStartPositionRow = 0;
            int MenuEndPositionRow = 0;
            int check_1_posRow = 0;
            int check_2_posRow = 0;
            int check_3_posRow = 0;
            int check_4_posRow = 0;
            int option_menuRow = 0;
            int option_selectRow = 0;
            int option_fieldRow = 0;
            int itemRow = 0;
            int DelayRow = 0;
            int event_buttonRow = 0;
            int event_choose_buttonRow = 0;
            int event_del_buttonRow = 0;
            int shed_startRow = 0;
            int shed_endRow = 0;
            
            while ((line = reader.readLine()) != null) {
                line = line.split("//")[0].trim();
                String[] values = line.split(",");
                if (values.length != 2) {
                    continue;
                }
                int x = Integer.parseInt(values[0].trim());
                int y = Integer.parseInt(values[1].trim());
                if (AddPositionRow < 1) {
                    AddPosition = new int[1][2];
                    AddPosition[0][0] = x;
                    AddPosition[0][1] = y;
                    AddPositionRow++;
                } else if (MenuStartPositionRow < 1) {
                    MenuStartPosition = new int[1][2];
                    MenuStartPosition[0][0] = x;
                    MenuStartPosition[0][1] = y;
                    MenuStartPositionRow++;
                } else if (MenuEndPositionRow < 1) {
                    MenuEndPosition = new int[1][2];
                    MenuEndPosition[0][0] = x;
                    MenuEndPosition[0][1] = y;
                    MenuEndPositionRow++;
                } else if (DelayRow < 1) {
                    Delay_duration = new int[1][2];
                    Delay_duration[0][0] = x;
                    Delay_duration[0][1] = y;
                    DelayRow++;
                } else if (check_1_posRow < 1) {
                    check_1_pos = new int[1][2];
                    check_1_pos[0][0] = x;
                    check_1_pos[0][1] = y;
                    check_1_posRow++;
                } else if (check_2_posRow < 1) {
                    check_2_pos = new int[1][2];
                    check_2_pos[0][0] = x;
                    check_2_pos[0][1] = y;
                    check_2_posRow++;
                } else if (check_3_posRow < 1) {
                    check_3_pos = new int[1][2];
                    check_3_pos[0][0] = x;
                    check_3_pos[0][1] = y;
                    check_3_posRow++;
                } else if (check_4_posRow < 1) {
                    check_4_pos = new int[1][2];
                    check_4_pos[0][0] = x;
                    check_4_pos[0][1] = y;
                    check_4_posRow++;
                } else if (option_menuRow < 1) {
                    option_menu = new int[1][2];
                    option_menu[0][0] = x;
                    option_menu[0][1] = y;
                    option_menuRow++;
                } else if (option_selectRow < 1) {
                    option_select = new int[1][2];
                    option_select[0][0] = x;
                    option_select[0][1] = y;
                    option_selectRow++;
                } else if (option_fieldRow < 1) {
                    option_field = new int[1][2];
                    option_field[0][0] = x;
                    option_field[0][1] = y;
                    option_fieldRow++;
                } else if (itemRow < 1) {
                    item = new int[1][2];
                    item[0][0] = x;
                    item[0][1] = y;
                    itemRow++;
                } else if (event_buttonRow < 1) {
                	event_button = new int[1][2];
                	event_button[0][0] = x;
                	event_button[0][1] = y;
                	event_buttonRow++;
                } else if (event_choose_buttonRow < 1) {
                	event_choose_button = new int[1][2];
                	event_choose_button[0][0] = x;
                	event_choose_button[0][1] = y;
                	event_choose_buttonRow++;
                } else if (event_del_buttonRow < 1) {
                	event_del_button = new int[1][2];
                	event_del_button[0][0] = x;
                	event_del_button[0][1] = y;
                	event_del_buttonRow++;
                } else if ( shed_startRow< 1) {
                	shed_start = new int[1][2];
                	shed_start[0][0] = x;
                	shed_start[0][1] = y;
                	shed_startRow++;
                } else if (shed_endRow < 1) {
                	shed_end = new int[1][2];
                	shed_end[0][0] = x;
                	shed_end[0][1] = y;
                	shed_endRow++;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper function to watch changes in the config file
    public static void watchConfigFile(File configFile) {
        try {
            Path configPath = configFile.toPath().toAbsolutePath().getParent();
            WatchService watchService = configPath.getFileSystem().newWatchService();
            configPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        if (event.context().toString().equals(CONFIG_FILE)) {
                            LocalDateTime timestamp = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String formattedTimestamp = timestamp.format(formatter);
                            System.out.println("Configuration file changed " + formattedTimestamp + "  Debug");
                            loadConfigData();
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Helper function for introducing a delay
    public static void Delay() {
        int time = Delay_duration[0][0];
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Helper function to simulate a mouse click at a specific position
    public static void clickOnScreen(int x, int y) {
        Delay();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Final app = new Final(100);
            app.setVisible(true);
        });
    }
}
// не знаю для чого, але вся робота через файли, переробив програму колишню для гри, зараз надішлю