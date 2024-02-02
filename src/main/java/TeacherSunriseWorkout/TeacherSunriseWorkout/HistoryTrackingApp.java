package TeacherSunriseWorkout.TeacherSunriseWorkout;
import org.jdesktop.swingx.JXDatePicker;

import TeacherSunriseWorkout.TeacherSunriseWorkout.Data;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
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
import java.util.Calendar;
import java.util.Date;

public class HistoryTrackingApp extends JFrame {
	Data Data;
	// String url="C:\\Users\\01957\\Downloads/";
	   FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	   // Get the desktop directory
	   java.io.File desktopDir = fileSystemView.getHomeDirectory();
	   // Create a subdirectory named "Etiketten"
	   String url = desktopDir.getAbsolutePath() + "/file";
	   java.io.File subdirectory = new java.io.File(url);
    private JTable table;int co=0;
    private DefaultTableModel tableModel;String status,tim;
String username;
    public HistoryTrackingApp(String name) throws IOException {
    	username=name;
        // Set the layout manager for the main frame
        setLayout(new GridLayout(3, 1));

        // First part - Title
        JLabel titleLabel = new JLabel("View History & Track", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // Second part - Date picker and table
        JPanel secondPanel = new JPanel(new GridLayout(2, 1));

        // Date picker
        JPanel datePickerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel dateLabel = new JLabel("Select Date: ");
        JXDatePicker datePicker = new JXDatePicker();

        // Set the date to the current date
        datePicker.setDate(Calendar.getInstance().getTime());

        // Add a DateSelectionListener to update the table data
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					updateTableData(datePicker.getDate());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        datePickerPanel.add(dateLabel);
        datePickerPanel.add(datePicker);
        secondPanel.add(datePickerPanel);

        // Table
        
        // Fetch and add new data based on the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(Calendar.getInstance().getTime());

        // Example: Add some dummy data based on the selected date
        // You should replace this with your logic to fetch and add actual data
  
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Exercise Name");
        model.addColumn("Completed");
        model.addColumn("Duration (minutes)");
         String filePath = url + "/exercisename.csv";
        
        ArrayList<Data> dataName= readCsvFileExerciseName(filePath);
        ArrayList<Data> datah= readCsvFile(formattedDate);
        String[] exercises = new String[dataName.size()];
      
        	dataName.forEach(e->{
        		co=0;status="";tim="";
        		datah.forEach(f->{
        			
            		
        			if(e.getExercisename().equals(f.getExercisename()))
        			{
        				co=1;
        				status=f.getStatus();
        				tim=f.getDuration();
        			}
            		
            	});
        		
        		if(co==1)
        		{
        			model.addRow(new Object[]{e.getExercisename(), status,tim});
        		}
        		else
        		{
        			model.addRow(new Object[]{e.getExercisename(), "NO","0"});
        		}
        	});
     
        
 
        
       
       // tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
       
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(400, 400));
        secondPanel.add(tableScrollPane);

        add(secondPanel);

        // Third part - Buttons
        JPanel thirdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back to Dashboard");
        JButton resetButton = new JButton("Reset");
        thirdPanel.add(backButton);
        thirdPanel.add(resetButton);
        add(thirdPanel);

        // Button actions
        backButton.addActionListener(new ActionListener() {
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

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	 File fileToDelete = new File(url+"/file.csv");
                 if (fileToDelete.exists()) {
                     fileToDelete.delete();
                 }
                 //exercisename
               fileToDelete = new File(url+"/exercise.csv");
                 if (fileToDelete.exists()) {
                     fileToDelete.delete();
                 }
                 fileToDelete = new File(url+"/exercisename.csv");
                 if (fileToDelete.exists()) {
                     fileToDelete.delete();
                 }
                // Add your logic for reset action
                JOptionPane.showMessageDialog(null, "Reset action performed");
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

        // Set frame properties
        setTitle("History & Tracking");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void updateTableData(Date selectedDate) throws IOException {
        // Clear existing table data
        

        // Fetch and add new data based on the selected date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);
          String filePath = url + "/exercisename.csv";
        
        ArrayList<Data> dataName= readCsvFileExerciseName(filePath);
        ArrayList<Data> datah= readCsvFile(formattedDate);
       
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        dataName.forEach(e->{
    		co=0;status="";tim="";
    		datah.forEach(f->{
    			
        		
    			if(e.getExercisename().equals(f.getExercisename()))
    			{
    				co=1;
    				status=f.getDuration();
    				tim=f.getDuration();
    			}
        		
        	});
    		
    		if(co==1)
    		{
    			model.addRow(new Object[]{e.getExercisename(), status,tim});
    		}
    		else
    		{
    			model.addRow(new Object[]{e.getExercisename(), "NO","0"});
    		}
    	});
 
    

        // Example: Add some dummy data based on the selected date
        // You should replace this with your logic to fetch and add actual data
       
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

    private  ArrayList<Data> readCsvFile(String currentDate) throws IOException {
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
                   // Check if the date matches the currentDate
                  // if (currentDate.equals(data.getDate())) {
                  
                   if ((currentDate.equals( rowData[3].trim()) && username.equals(rowData[4].trim()))) {
                	   info.add(data);
                      
                   }
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

       return info;
   }
    
   
}
