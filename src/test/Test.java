package test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Test {

    private static JTextField textField = new JTextField(); // Initialize textField
    private static JButton addButton; // Declare addButton at class level
    private static Timer timer; // Timer for scheduling updates
    private static BufferedReader fileReader;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout()); // Set the layout manager

        addButton = new JButton("ADD");
        addButton.addActionListener(e -> {
            System.out.print("ADD pressed\n");
            func(); // Call the function after the button click
            startTimer();
        });

        // Assuming you have a JButton and want to associate the functionality with it
        JButton yourButton = new JButton("Your Button");

        // Add an ActionListener to the button
        yourButton.addActionListener(e -> {
            try {
                processInputFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        textField.setPreferredSize(new Dimension(200, 25));
        frame.add(textField); // Use the class-level textField
        frame.add(yourButton);
        frame.add(addButton);
        frame.pack(); // Pack the components
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Set up the timer to update the text field every 1000 milliseconds (1 second)
        int delay = 1000;
        timer = new Timer(delay, evt -> updateTextField());
        timer.setRepeats(true);
    }

    private static void startTimer() {
        // Start the timer only once when the button is pressed
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    private static void processInputFile() throws IOException {
        File inputFile = new File("input.txt");

        // Check if the file exists, and create it if not
        if (!inputFile.exists()) {
            createInputFile();
        }

        // Open the file for reading
        fileReader = new BufferedReader(new FileReader(inputFile));
    }

    private static void createInputFile() throws IOException {
        // Create the "input.txt" file with some initial content
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt"))) {
            bw.write("Line 1\n");
            bw.write("Line 2\n");
            bw.write("Line 3\n");
            // Add more lines as needed
        }
    }

    private static void func() {
        System.out.print("func Called \n \n");

        // You can perform additional logic after the button click here
    }

    private static void updateTextField() {
        try {
            // Read the next line from the file and update the text field
            String line;
            if ((line = fileReader.readLine()) != null) {
                textField.setText(line);
                System.out.println("Line is " + line);
            } else {
                // Stop the timer when there are no more lines to read
                timer.stop();
                // Close the file reader
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

