package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Program extends JFrame {
    public static final long serialVersionUID = 1L;
    public static final String CONFIG_FILE = "settings.txt";

    public static final int array_size = 1;
    public static int[][] add_pos;
    public static int[][] menu_start_pos;
    public static int[][] menu_end_pos;
    public static int[][] delay_duration;
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

    public static JTextField textField;

    public static int[][][] time1;
    public static int[][][] time2;
    public static int[][][] time3;

    public static Robot bot;

    public static boolean secondOption = false;
    public static boolean auto = false;

    static JButton applyButton;
    static JButton addButton;
    public JCheckBox secondOptionCheckbox;
    public static Timer timer; // Timer for scheduling updates
    public static BufferedReader fileReader;

    public Program(int size) {
        secondOptionCheckbox = GUI.createStyledCheckbox("Alt");
        GridBagConstraints checkboxConstraints = GUI.createButtonConstraints(0, 4);

        secondOptionCheckbox.addActionListener(e -> {
            secondOption = secondOptionCheckbox.isSelected();
            System.out.println("Checkbox state is: " + secondOption);
        });

        File configFile = new File(CONFIG_FILE);
        Thread configFileThread = new Thread(() -> Files.watchConfigFile(configFile));

        if (!configFile.exists()) {
            Files.createDefaultConfigFile();
        } else {
            Files.loadConfigData();
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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create and configure UI elements
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300, 30));

        textField.setForeground(Color.CYAN);
        textField.setBackground(Color.DARK_GRAY);

        applyButton = GUI.createStyledButton("Час");
        addButton = GUI.createStyledButton("Додати");

        JButton clearButton = GUI.createStyledButton("Очистити");
        JButton railsButton = GUI.createStyledButton("Рейси");
        JButton setButton = GUI.createStyledButton("Set");
        JButton autoButton = GUI.createStyledButton("Auto");
        

        GridBagConstraints textFieldConstraints = GUI.createButtonConstraints(0, 0);
        textFieldConstraints.gridwidth = 3;
        textFieldConstraints.weightx = 1.0;

        GridBagConstraints applyButtonConstraints = GUI.createButtonConstraints(0, 1);
        GridBagConstraints addButtonConstraints = GUI.createButtonConstraints(1, 1);
        GridBagConstraints clearButtonConstraints = GUI.createButtonConstraints(0, 2);
        GridBagConstraints setButtonContraints = GUI.createButtonConstraints(1, 2);
        GridBagConstraints autoButtonContraints = GUI.createButtonConstraints(0, 3);
        GridBagConstraints railsButtonContraints = GUI.createButtonConstraints(1, 3);
        setButton.addActionListener(e -> Utils.setCoords(1, "Додати"));
        clearButton.addActionListener(e -> textField.setText(""));
        railsButton.addActionListener(e -> {
            Utils.mix(add_pos, add_pos);
            Utils.delay();
            Utils.moveFromTo(shed_start, shed_end);
        });

        addButton.addActionListener(e -> {
        	
            Utils.mix(add_pos, add_pos);
            Utils.delay();
            Utils.moveFromTo(menu_start_pos, menu_end_pos);
            Utils.delay();
            Utils.mix(check_1_pos, check_2_pos, check_3_pos, check_4_pos, option_menu, option_field, option_field);
            Utils.delay();
      
            try {
                Utils.paste();
            } catch (AWTException a) {
                a.printStackTrace();
            }
         
            if (secondOption == true) {
            	Utils.delay();
                Utils.clickOnScreen(215, 163);
                
            }else {
                
                Utils.mix(item, item);
                Utils.delay();
                Utils.mix(item, item);
            }          

        });

        applyButton.addActionListener(new Utils.ApplyButtonListener());
        add(secondOptionCheckbox, checkboxConstraints);
        add(textField, textFieldConstraints);
        add(applyButton, applyButtonConstraints);
        add(addButton, addButtonConstraints);
        add(clearButton, clearButtonConstraints);
        add(setButton, setButtonContraints);
        add(autoButton, autoButtonContraints);
        add(railsButton,railsButtonContraints);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Erasing Thread to free RAM");
                System.exit(0);
            }
        });

        getContentPane().setBackground(Color.DARK_GRAY);
        setTitle("Dozor Helper");
        setSize(195, 180);
        setVisible(true);
        setAlwaysOnTop(true);
        configFileThread.start();

        JCheckBox checkBox = new JCheckBox("Switch");
        checkBox.setForeground(Color.CYAN);
        checkBox.setBackground(Color.DARK_GRAY);
        checkBox.addItemListener(e -> {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            auto=e.getStateChange() == ItemEvent.SELECTED;
            autoButton.setEnabled(!selected);
        });

        GridBagConstraints checkBoxConstraints = GUI.createButtonConstraints(1, 4);
        add(checkBox, checkBoxConstraints);

        autoButton.addActionListener(e -> {
            try {
                Files.processInputFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.print("ADD pressed\n");
            applyButton.doClick();
            // Call the function after the button click
            Utils.startTimer();
        });

        // Set up the timer to update the text field every 1000 milliseconds (1 second)
        int delay = 1000;
        timer = new Timer(delay, evt -> GUI.updateTextField());
        timer.setRepeats(true);
    }
   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Program app = new Program(100);
            app.setVisible(true);
        });
    }
}
