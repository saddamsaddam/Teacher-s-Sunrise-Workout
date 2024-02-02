package TeacherSunriseWorkout.TeacherSunriseWorkout;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// The main class representing the login view, extends JFrame
public class LoginView extends JFrame {
     boolean result=false;
     Exercises Exercises;
     RegistrationForm RegistrationForm;
    // Components for the username and password fields
    private JTextField usernameField;
    private JPasswordField passwordField;
    JButton registerButton;
    // File system view to handle file-related operations
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();

    // Get the desktop directory
    java.io.File desktopDir = fileSystemView.getHomeDirectory();

    // Create a subdirectory named "file"
    String url = desktopDir.getAbsolutePath() + "/file";
    java.io.File subdirectory = new java.io.File(url);

    // Constructor for the LoginView class
    public LoginView() throws IOException {
    	// insert registration 3 users if not exist
    	 RegistrationForm =new RegistrationForm();
    	 ArrayList<Registration> userdata=	RegistrationForm.loadRegistrationDataFromFile();
    	 if(userdata.size()<=0)
    	 {
    		 RegistrationForm.save3UserToFile("PYP1","PYP1@gmail.com","4ourHealth","50","23","45");
    		 RegistrationForm.save3UserToFile("PYP2","PYP2@gmail.com","4ourHealth","110","253","46");
    		 RegistrationForm.save3UserToFile("PYP3","PYP3@gmail.com","4ourHealth","122","233","45");
    		 
    	 }
    	
    	
    	//	Read past exercises from CSV file when you start APPLICATION!
    	 Exercises=new Exercises();
    	ArrayList<Data> exercisedata= Exercises.readCsvFilealldata();
    	exercisedata.forEach(e->{
    		System.out.println(e.getUsername() +"  "+e.getExercisename()+"  "+e.getDuration()+"  "+e.getStatus()+"  "+e.getDate());
    		
    	});

        // Check if the subdirectory exists, if not, create it
        if (!subdirectory.exists()) {
            boolean created = subdirectory.mkdirs();
            if (!created) {
                System.err.println("Failed to create the 'file' subdirectory.");
                return;
            }
        }

        // Set up the frame
        setTitle("Login Page");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom panel with background image
        JPanel mainPanel = new ImagePanel(new ImageIcon(LoginView.class.getResource("xy1.jpg")).getImage());
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add a JLabel for the text at the top-center
        JLabel topTextLabel = new JLabel("Teacher’s Sunrise Workout");
        topTextLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size if needed
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across the whole width
        gbc.anchor = GridBagConstraints.PAGE_START; // Align to the top
        mainPanel.add(topTextLabel, gbc);

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        registerButton = new JButton(" Register ");
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get entered username and password
                String enteredUsername = usernameField.getText();
                char[] enteredPasswordChars = passwordField.getPassword();
                String enteredPassword = new String(enteredPasswordChars);

                // Check against predefined usernames and password
                if (isValidLogin(enteredUsername, enteredPassword)) {
                    // If login is valid, open the HomePage and close the current window
                    HomePage HomePage;
    				try {
    					HomePage = new HomePage(enteredUsername);
    					HomePage.setVisible(true);
    	                 dispose();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                } else {
                    // Display an error message if login fails
                    JOptionPane.showMessageDialog(LoginView.this, "Login failed. Please try again.");
                }
            }
        });
         registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login validation logic here
            	// Open the RegistrationForm when the register button is clicked
                RegistrationForm registrationForm = new RegistrationForm();
                registrationForm.setVisible(true);
                dispose();
            }
        });
        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(loginButton, gbc);

        // Add the main panel to the frame
       // add(mainPanel);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        JPanel top=new  JPanel(new BorderLayout());
        top.setBackground(Color.YELLOW);
        top.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10)); // Set top and right margin
        
        top.add(registerButton, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    // Custom JPanel with background image
    private static class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Method to validate login credentials
    private boolean isValidLogin(String username, String password) {
    	RegistrationForm RegistrationForm=new RegistrationForm();
    	ArrayList<Registration> data=RegistrationForm.loadRegistrationDataFromFile();
    	result=false;
    	data.forEach(e->{
    		if (e.getName().equals(username) && e.getPassword().equals(password)) {
   			 result=true;
   		 }
    		
    	});
       
        return result;
    }
   
    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
					new LoginView().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
}
