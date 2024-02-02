package TeacherSunriseWorkout.TeacherSunriseWorkout;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import TeacherSunriseWorkout.TeacherSunriseWorkout.Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Class representing the HomePage of the application, extends JFrame
public class HomePage extends JFrame {
    Data Data;
    // Username of the logged-in user
    String username;
    
    // File system view to handle file-related operations
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    
    // Get the desktop directory
    java.io.File desktopDir = fileSystemView.getHomeDirectory();
    
    // Create a subdirectory named "file"
    String url = desktopDir.getAbsolutePath() + "/file";
    java.io.File subdirectory = new java.io.File(url);
int co=0;
    // Constructor for the HomePage class
    public HomePage(String usernamee) throws IOException {
        username = usernamee;

        // Set up the frame
        setTitle("Home Page");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Welcome panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome (" + username + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size if needed
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        // Options panel
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcOptions = new GridBagConstraints();
        gbcOptions.insets = new Insets(5, 5, 5, 5);
        String filePath = url + "/exercisename.csv";
        
        ArrayList<Data> data= readCsvFileExerciseName(filePath);
        String[] exercises = new String[data.size()];
        for(int i=0;i<data.size();i++)
        {
        	exercises[i]=data.get(i).getExercisename();
        }
       

        
        String[] options = {"Manage Exercises", "View History & Track", "LogOut"};

        JComboBox<String> exercisesDropdown = new JComboBox<>(exercises);
        exercisesDropdown.setPreferredSize(new Dimension(200, 30));
        JComboBox<String> optionsDropdown = new JComboBox<>(options);
        optionsDropdown.setPreferredSize(new Dimension(200, 30));

        // Add action listeners for the dropdown lists
        ActionListener dropdownActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> clickedDropdown = (JComboBox<String>) e.getSource();
                String selectedOption = (String) clickedDropdown.getSelectedItem();

                if (selectedOption.equals("Manage Exercises")) {
                    ManageyourExercises ManageyourExercises = new ManageyourExercises(username);
                    ManageyourExercises.setVisible(true);
                    dispose();
                }
                else if(selectedOption.equals("LogOut"))
                {
                	LoginView LoginView;
					try {
						LoginView = new LoginView();
						LoginView.setVisible(true);
	                	dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                }
                else if (selectedOption.equals("View History & Track")) {
                    HistoryTrackingApp HistoryTrackingApp;
                    try {
                        HistoryTrackingApp = new HistoryTrackingApp(username);
                        HistoryTrackingApp.setVisible(true);
                        dispose();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }  else {
                	co=0;
                	 ArrayList<Data> data;
					try {
						data = readCsvFileExerciseName(filePath);
						 data.forEach(f->{
	                		 if(selectedOption.equals(f.getExercisename())&&username.equals(f.getUsername()))
	                		 {
	                			 co=Integer.parseInt(f.getDuration());
	                		 }
	                	 });
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                    Exercises Exercises = new Exercises(selectedOption,co, username);
                    Exercises.setVisible(true);
                    dispose();
                }
            }
        };

        exercisesDropdown.addActionListener(dropdownActionListener);
        optionsDropdown.addActionListener(dropdownActionListener);

        // Add components to the options panel
        gbcOptions.gridx = 0;
        gbcOptions.gridy = 0;
        optionsPanel.add(new JLabel("Exercises:"), gbcOptions);

        gbcOptions.gridx = 1;
        gbcOptions.gridy = 0;
        optionsPanel.add(exercisesDropdown, gbcOptions);

        gbcOptions.gridx = 0;
        gbcOptions.gridy = 1;
        optionsPanel.add(new JLabel("Options:"), gbcOptions);

        gbcOptions.gridx = 1;
        gbcOptions.gridy = 1;
        optionsPanel.add(optionsDropdown, gbcOptions);

        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }
    private ArrayList<Data> readCsvFileExerciseName(String filePath) throws IOException {
    	File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    	
        ArrayList<Data> info = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length == 3) {
                    Data = new Data(rowData[0], rowData[1], rowData[2]);
                    if (username.equals(Data.getUsername())) {
                        info.add(Data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

   
   
}
