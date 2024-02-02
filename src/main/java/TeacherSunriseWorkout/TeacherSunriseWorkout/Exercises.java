package TeacherSunriseWorkout.TeacherSunriseWorkout;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Exercises extends JFrame {
	// String url="C:\\Users\\01957\\Downloads/";
	   FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	   // Get the desktop directory
	   java.io.File desktopDir = fileSystemView.getHomeDirectory();
	   // Create a subdirectory named "Etiketten"
	   String url = desktopDir.getAbsolutePath() + "/file";
	   java.io.File subdirectory = new java.io.File(url);
String exname;
    private Timer timer;int time=0;
    private int secondsElapsed = 0;
    private JLabel title2; // Assuming you have a JLabel to display the timer
String username;
public Exercises() {
	
}
    public Exercises(String namee,int times,String usernamee) {
    	exname=namee;
    	time=times;username=usernamee;
         System.out.println(time);
        // Set the layout manager for the main frame
        setLayout(new GridLayout(4, 1));

        // First part - Title
        JLabel titleLabel = new JLabel(exname + " Workout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        // Second part - Two titles vertically
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(3, 1));

        JLabel title1 = new JLabel("Timer", SwingConstants.CENTER);
        title1.setFont(new Font("Arial", Font.BOLD, 24));
        title2 = new JLabel("00:00:00", SwingConstants.CENTER);
        title2.setFont(new Font("Arial", Font.BOLD, 24));
        secondPanel.add(title1);
        secondPanel.add(title2);
        JLabel gg = new JLabel("Instructions");
        gg.setFont(new Font("Arial", Font.BOLD, 18));
        secondPanel.add(gg);
        add(secondPanel);

        // Third part - Text area with 4 or 5 sentences
        JTextArea textArea = new JTextArea("-> Perform (" + exname + ") continuously\n" +
                "-> Focus on form and breathing\n" +
                "-> Stop when the timer runs out\n");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        add(new JScrollPane(textArea));

        // Fourth part - Two buttons horizontally
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(new FlowLayout());

        JButton startTimeButton = new JButton("Start Time");
        JButton finishWorkoutButton = new JButton("Finish Workout");

        // Attach action listeners to the buttons
        startTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(time>0)
            	{
            		 startTimer();
            	}
               
            }
        });

        finishWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // stopTimer();
            	 HomePage HomePage;
				try {
					HomePage = new HomePage(username);
					HomePage.setVisible(true);
	                 dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                 
            }
        });

        fourthPanel.add(startTimeButton);
        fourthPanel.add(finishWorkoutButton);
        add(fourthPanel);

        // Set frame properties
        setTitle("Workout App");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void startTimer() {
        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    secondsElapsed++;
                    updateTimerDisplay();
                    if (secondsElapsed >= (time*60)) { // Stop timer after 2 minutes (120 seconds)
                        stopTimer();
                        try {
							showCompletionAlert();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    }
                }
            });
            timer.start();
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void updateTimerDisplay() {
        title2.setText(secondsToTimeFormat(secondsElapsed));
    }

    private void showCompletionAlert() throws IOException {
    	// save  name , time ,"OK", currentdate to csv file
    	
        JOptionPane.showMessageDialog(this, "Successfully completed!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        saveToCsvFile(exname, time, "OK");
        
    }
    private void saveToCsvFile(String name, int secondsElapsed, String status) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        // Specify the path to your CSV file
        String filePath = url+"/exercise.csv";
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            // Check if the row already exists
            boolean rowExists = false;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length == 5 &&
                	    rowData[0].trim().equalsIgnoreCase(name.trim())&&
                	    rowData[4].trim().equalsIgnoreCase(username.trim()) &&
                	    rowData[3].trim().equalsIgnoreCase(currentDate.trim())) {
                	    rowExists = true;
                	    break;
                	}

            }

            // If the row doesn't exist, append new data to the CSV file
            if (!rowExists) {
                writer.write(name + "," + secondsElapsed + "," + status + "," + currentDate + "," + username);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data to CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public  ArrayList<Data> readCsvFilealldata() throws IOException {
       	String filePath = url+"/exercise.csv";
       	File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
       	String rr="0";
           ArrayList<Data> info = new ArrayList<>();
          // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          // String currentDate = dateFormat.format(new Date());
           try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
               String line;
               while ((line = reader.readLine()) != null) {
                   String[] rowData = line.split(",");
                   if (rowData.length == 5) {
                       Data data = new Data(rowData[0], rowData[1], rowData[2], rowData[3],rowData[4]);
                       info.add(data);
                      
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();
           }

           return info;
       }
    

    private String secondsToTimeFormat(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

   
}
