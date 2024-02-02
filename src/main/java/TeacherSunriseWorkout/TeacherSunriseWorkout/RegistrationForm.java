package TeacherSunriseWorkout.TeacherSunriseWorkout;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField heightField;
    private JTextField weightField;
    private JTextField ageField;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel registrationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        registrationPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField(15);
        registrationPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        registrationPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        emailField = new JTextField(15);
        registrationPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registrationPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField(15);
        registrationPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        registrationPanel.add(new JLabel("Height:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        heightField = new JTextField(15);
        registrationPanel.add(heightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        registrationPanel.add(new JLabel("Weight:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        weightField = new JTextField(15);
        registrationPanel.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        registrationPanel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        ageField = new JTextField(15);
        registrationPanel.add(ageField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Check if all fields are filled
                if (areAllFieldsFilled()) {
                    // Print or handle the registration details
                    System.out.println("Name: " + nameField.getText());
                    System.out.println("Email: " + emailField.getText());
                    System.out.println("Password: " + new String(passwordField.getPassword()));
                    System.out.println("Height: " + heightField.getText());
                    System.out.println("Weight: " + weightField.getText());
                    System.out.println("Age: " + ageField.getText());

                    // Close the registration form
                    saveToFile();
                    LoginView LoginView;
					try {
						LoginView = new LoginView();
						LoginView.setVisible(true);
	                    dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    

                    
                   
                } else {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Please fill in all fields.");
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        registrationPanel.add(submitButton, gbc);

        add(registrationPanel);
        setLocationRelativeTo(null);
    }
    
    private boolean areAllFieldsFilled() {
        return !nameField.getText().isEmpty() &&
                !emailField.getText().isEmpty() &&
                !new String(passwordField.getPassword()).isEmpty() &&
                !heightField.getText().isEmpty() &&
                !weightField.getText().isEmpty() &&
                !ageField.getText().isEmpty();
    }
    
    private void saveToFile() {
    	 FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    	    // Get the desktop directory
    	    java.io.File desktopDir = fileSystemView.getHomeDirectory();

    	    // Create a subdirectory named "file" if it doesn't exist
    	    String subdirectoryPath = desktopDir.getAbsolutePath() + "/file";
    	    java.io.File subdirectory = new java.io.File(subdirectoryPath);

    	    if (!subdirectory.exists()) {
    	        boolean created = subdirectory.mkdirs();
    	        if (!created) {
    	            JOptionPane.showMessageDialog(this, "Error creating the 'file' subdirectory.");
    	            return;
    	        }
    	    }
    	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(subdirectoryPath + "/registration.txt", true))) {
            // Concatenate all registration data with commas
            String line = String.join(",", 
                nameField.getText(),
                emailField.getText(),
                new String(passwordField.getPassword()),
                heightField.getText(),
                weightField.getText(),
                ageField.getText()
            );

            // Write the line to the file
            writer.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving registration data to file.");
        }
    }
    
    public void save3UserToFile(String name,String email, String password, String height, String weight, String age) {
   	 FileSystemView fileSystemView = FileSystemView.getFileSystemView();

   	    // Get the desktop directory
   	    java.io.File desktopDir = fileSystemView.getHomeDirectory();

   	    // Create a subdirectory named "file" if it doesn't exist
   	    String subdirectoryPath = desktopDir.getAbsolutePath() + "/file";
   	    java.io.File subdirectory = new java.io.File(subdirectoryPath);

   	    if (!subdirectory.exists()) {
   	        boolean created = subdirectory.mkdirs();
   	        if (!created) {
   	            JOptionPane.showMessageDialog(this, "Error creating the 'file' subdirectory.");
   	            return;
   	        }
   	    }
   	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(subdirectoryPath + "/registration.txt", true))) {
           // Concatenate all registration data with commas
           String line = String.join(",", 
        		   name,
        		   email,
        		   password,
        		   height,
        		   weight,
        		   age
           );

           // Write the line to the file
           writer.write(line + "\n");
       } catch (IOException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error saving registration data to file.");
       }
   }
    
    public  ArrayList<Registration> loadRegistrationDataFromFile() {
        ArrayList<Registration> registrationDataList = new ArrayList<>();
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();

	    // Get the desktop directory
	    java.io.File desktopDir = fileSystemView.getHomeDirectory();

	    // Create a subdirectory named "file" if it doesn't exist
	    String subdirectoryPath = desktopDir.getAbsolutePath() + "/file";
	    java.io.File subdirectory = new java.io.File(subdirectoryPath);

	    if (!subdirectory.exists()) {
	        boolean created = subdirectory.mkdirs();
	        
	    }
        try (BufferedReader reader = new BufferedReader(new FileReader(subdirectoryPath +"/registration.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Create a new RegistrationForm instance
                Registration registrationFormInstance = new Registration(data[0],data[1],data[2],data[3],data[4],data[5]);

                registrationDataList.add(registrationFormInstance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return registrationDataList;
    }

}
