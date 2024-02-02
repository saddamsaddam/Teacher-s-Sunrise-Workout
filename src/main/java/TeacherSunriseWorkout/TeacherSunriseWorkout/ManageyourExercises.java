package TeacherSunriseWorkout.TeacherSunriseWorkout;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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

// Class representing the ManageyourExercises window
public class ManageyourExercises extends JFrame {
	Data Data;
    // Components for the UI
    private JComboBox<String> exerciseComboBox;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label1, label2;
    private JButton addButton, editButton;
    private JTable table;

    // File-related variables
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    java.io.File desktopDir = fileSystemView.getHomeDirectory();
    String url = desktopDir.getAbsolutePath() + "/file";
    java.io.File subdirectory = new java.io.File(url);

    // Username of the logged-in user
    String username;

    // Constructor for the ManageyourExercises class
    public ManageyourExercises(String name) {
        username = name;

        // Set up the JFrame
        setTitle("Manage Your Exercises");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        createTable();
        createBottomPanel();

        // Set layout for the main frame
        setLayout(new BorderLayout());

        // Add components to the frame
        add(createTopPanel(), BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.CENTER);
    }

    // Method to create the top panel with title and table
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Manage your exercises", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Add the title label to the top panel
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(450, 400));
        topPanel.add(scrollPane, BorderLayout.CENTER);

        return topPanel;
    }

    // Method to create the table
    private void createTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Exercise Name)");
        model.addColumn("Duration(Minute)");
        // add previous 
        String filePath = url + "/exercisename.csv";
       
        ArrayList<Data> data= readCsvFileExerciseName(filePath);
        data.forEach(e->{
        	
        	model.addRow(new Object[]{e.getExercisename(), e.getDuration()});
        });
       // 

        table = new JTable(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // Method to create the bottom panel with text fields, labels, and buttons
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout());

        // ComboBox and labels
       
        textField2 = new JTextField(10);
        textField1 = new JTextField(10);
        label1 = new JLabel("Select Exercise Name:");
        label2 = new JLabel("Exercise Duration (Minutes):");

        // Add ComboBox and labels to the bottom panel
        bottomPanel.add(label1);
        bottomPanel.add(textField1);
        bottomPanel.add(label2);
        bottomPanel.add(textField2);

        // Add and Edit buttons
        addButton = new JButton("Add/Edit");
        editButton = new JButton("HomePage");

        // Add action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddButton();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        // Add buttons to the bottom panel
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(new JLabel("                                   ."));

        return bottomPanel;
    }

    // Method to handle the logic when the Add/Edit button is clicked
    private void handleAddButton() {
        String selectedExercise =textField1.getText();;
        String duration = textField2.getText();

        // Validate input
        if (duration.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter exercise duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();


       
        
       // model.setRowCount(0);

        model.addRow(new Object[]{textField1.getText(),  textField2.getText()});
        saveToCsvFile(textField1.getText(),  textField2.getText());
        
        textField1.setText("");
        textField2.setText("");
    }

    // Method to save data to the CSV file
    private void saveToCsvFile(String data1, String data2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            String filePath = url + "/exercisename.csv";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            ArrayList<Data> info = readCsvFile(data1,filePath);

            File fileToDelete = new File(filePath);
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }

            if (!data1.equals("") || !data2.equals("")) {
                info.add(new Data(data1, data2, username));
                writeCsvFile(info, filePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data to CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private ArrayList<Data> readCsvFileExerciseName(String filePath) {
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

    // Method to read data from the CSV file
    private ArrayList<Data> readCsvFile(String data1,String filePath) {
        ArrayList<Data> info = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length == 3) {
                    Data = new Data(rowData[0], rowData[1], rowData[2]);
                    if (!data1.equals(Data.getExercisename()) && username.equals(Data.getUsername())) {
                        info.add(Data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }

    // Method to write data to the CSV file
    private void writeCsvFile(ArrayList<Data> info, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            info.forEach(e -> {
                try {
                    writer.write(e.getExercisename() + "," + e.getDuration() + "," + e.getUsername());
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    
}
