package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Files {
	public static void loadConfigData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Program.CONFIG_FILE));

			Program.add_pos = loadArray(reader, "add_pos", 1);
			Program.menu_start_pos = loadArray(reader, "menu_start_pos", 1);
			Program.menu_end_pos = loadArray(reader, "menu_end_pos", 1);
			Program.delay_duration = loadArray(reader, "delay_duration", 1);
			Program.check_1_pos = loadArray(reader, "check_1_pos", 1);
			Program.check_2_pos = loadArray(reader, "check_2_pos", 1);
			Program.check_3_pos = loadArray(reader, "check_3_pos", 1);
			Program.check_4_pos = loadArray(reader, "check_4_pos", 1);
			Program.option_menu = loadArray(reader, "option_menu", 1);
			Program.option_select = loadArray(reader, "option_select", 1);
			Program.option_field = loadArray(reader, "option_field", 1);
			Program.item = loadArray(reader, "item", 1);
			Program.event_button = loadArray(reader, "event_button", 1);
			Program.event_choose_button = loadArray(reader, "event_choose_button", 1);
			Program.event_del_button = loadArray(reader, "event_del_button", 1);
			Program.shed_start = loadArray(reader, "shed_start", 1);
			Program.shed_end = loadArray(reader, "shed_end", 1);

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void watchConfigFile(File configFile) {
		try {
			Path configPath = configFile.toPath().toAbsolutePath().getParent();
			WatchService watchService = configPath.getFileSystem().newWatchService();
			configPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				WatchKey key = watchService.take();
				for (WatchEvent<?> event : key.pollEvents()) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						if (event.context().toString().equals(Program.CONFIG_FILE)) {
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

	private static int[][] loadArray(BufferedReader reader, String arrayName, int expectedSize) throws IOException {
		int[][] array = new int[expectedSize][2];
		for (int i = 0; i < expectedSize; i++) {
			String line = reader.readLine();
			if (line != null) {
				line = line.split("//")[0].trim();
				String[] values = line.split(",");
				if (values.length == 2) {
					int x = Integer.parseInt(values[0].trim());
					int y = Integer.parseInt(values[1].trim());
					array[i][0] = x;
					array[i][1] = y;
				}
			}
		}
		return array;
	}

	public static void createDefaultConfigFile() {
		try {
			FileWriter writer = new FileWriter(Program.CONFIG_FILE);
			String[] defaultData = { 
					"410,180 // Default data for AddPosition",
					"506,155 // Default data for Menu start position", 
					"145,70 // Default data for Menu end position",
					"100,0 // Default data for delay", 
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
					"158,118 // Default data for Shedule end position", };
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
	 public static void processInputFile() throws IOException {
	        File inputFile = new File("input.txt");

	        // Check if the file exists, and create it if not
	        if (!inputFile.exists()) {
	            createInputFile();
	        }

	        // Open the file for reading
	        //Program.fileReader = new BufferedReader(new FileReader(inputFile));
	        Program.fileReader = new BufferedReader(new FileReader(inputFile, StandardCharsets.UTF_8));

	    }
	 public static void createInputFile() throws IOException {
	        // Create the "input.txt" file with some initial content
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt"))) {
	            bw.write(" ");
	            // Add more lines as needed
	        }
	    }


}
