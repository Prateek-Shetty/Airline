import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminDashboard implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminDashboard::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BackgroundPanel());
        frame.setJMenuBar(createMenuBar()); // Set the menu bar
        frame.add(createBottomMenuBar(), BorderLayout.SOUTH); // Add the bottom menu bar
        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.ORANGE);
        menuBar.setPreferredSize(new Dimension(600, 40));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        menuPanel.setBackground(Color.ORANGE);

        JLabel airlineLabel = new JLabel("Indian Airlines @1953");
        airlineLabel.setForeground(Color.black);
        airlineLabel.setFont(new Font("Arial", Font.BOLD, 8));

        menuPanel.add(airlineLabel);
        menuPanel.add(Box.createHorizontalGlue());

        JMenuItem customerDataMenuItem = new JMenuItem("Customer Data");
        JMenuItem manageMenuItem = new JMenuItem("Manage");
        JMenuItem flightsMenuItem = new JMenuItem("Flights Info");

        // Set background colors, remove borders, and set opaque for menu items
        customizeMenuItem(customerDataMenuItem);
        customizeMenuItem(manageMenuItem);
        customizeMenuItem(flightsMenuItem);

        customerDataMenuItem.addActionListener(e -> openCustomerDataWindow());
        manageMenuItem.addActionListener(e -> new EditFlightInfoWindow());
        flightsMenuItem.addActionListener(e -> new NewWindow());

        menuPanel.add(customerDataMenuItem);
        menuPanel.add(Box.createHorizontalStrut(20));
        menuPanel.add(manageMenuItem);
        menuPanel.add(Box.createHorizontalStrut(20));
        menuPanel.add(flightsMenuItem);

        menuBar.add(menuPanel);

        return menuBar;
    }

    private static void customizeMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(Color.ORANGE);
        menuItem.setForeground(Color.black);
        menuItem.setFont(new Font("Arial", Font.BOLD, 14));
        menuItem.setOpaque(true);
        menuItem.setBorder(null); // Remove the border
    }

    private static JPanel createBottomMenuBar() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        bottomPanel.setBackground(Color.GREEN);

        JButton bottomButton1 = new JButton("@Indian Airlines");
        bottomButton1.setBackground(Color.GREEN);
        bottomButton1.setForeground(Color.black);
        bottomButton1.setBorderPainted(false);

        JPanel button2Panel = new JPanel();
        button2Panel.setLayout(new BorderLayout());
        button2Panel.setBackground(Color.BLUE);

        JButton bottomButton2 = new JButton("All rights reserved with Government of India");
        bottomButton2.setBackground(Color.GREEN);
        bottomButton2.setForeground(Color.black);
        bottomButton2.setBorderPainted(false);

        button2Panel.add(bottomButton2, BorderLayout.NORTH);

        bottomPanel.add(bottomButton1);
        bottomPanel.add(button2Panel);

        return bottomPanel;
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            backgroundImage = new ImageIcon("C:\\Users\\hp\\Documents\\GitHub\\Airline\\Airline\\src\\assets\\image.png").getImage();
            setLayout(null);
            addButtons();
        }

        private void addButtons() {
            JButton welcomeButton = new JButton("Welcome to Admin Page");
            welcomeButton.setBackground(Color.WHITE);
            welcomeButton.setForeground(Color.BLACK);
            welcomeButton.setBounds(150, 20, 250, 30);
            add(welcomeButton);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static void openCustomerDataWindow() {
        JFrame frame2 = new JFrame();
        frame2.setSize(800, 400);
        frame2.setTitle("Customer Details");
        frame2.setLayout(new BorderLayout());

        // Create the menu bar and bottom buttons
        frame2.setJMenuBar(createMenuBar()); // Set the same menu bar
        frame2.add(createBottomMenuBar(), BorderLayout.SOUTH); // Add the bottom menu bar

        // Create the table with customer data
        String[][] data = {
            {"Moksh Rana", "moksh45rana@gmail.com", "5221", "Chennai","Bangalore"},
            {"Jolie Fernandes", "Jolie@gmail.com", "5222", "Bengaluru","Abu Dhabi"},
            {"Aarav Gupta","AaravGupta@gmail.com","5223","Raipur","Ahemdabad"},
            {"Sneha Chauhan","Sneha@gmail.com","5224","Udaipur","Mysore"},
            {"Rohan Rao","Rao@gmail.com","5225","Surat","Patna"},
            {"Vikram Joshi","VikranJos@gmail.com","5226","Patna","Delhi"},
            {"Karan Mehta","Mehtavik@gmail.com","5227","Lucknow","Jammu"},
            {"Mohd Hamza","mohd@gmail.com","5228","Kochi","Jaipur"},
            {"Meera Choudhary","meeraj@gmail.com","5229","Guwahati","Vadodara"},
            {"Ramkrishna C","ramak@gmail.com","5230","Hyderabad","Chennai"}
             
            // Add more data as needed
        };

        String[] columnNames = {"Customer Name", "Contact Info", "Booking ID", "Flight Station","Flight Destination"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // Center the table in the frame
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        frame2.add(centerPanel, BorderLayout.CENTER);

        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // New class for editing flight information
    static class EditFlightInfoWindow {
        JFrame frame;
        JTextField flightNameField, arrivalTimeField, departureTimeField;
        JButton saveButton;

        EditFlightInfoWindow() {
            frame = new JFrame("Edit Flight Info");
            frame.setSize(800, 300);
            frame.setLayout(new BorderLayout());

            // Create the menu bar and bottom buttons
            frame.setJMenuBar(createMenuBar()); // Set the same menu bar
            frame.add(createBottomMenuBar(), BorderLayout.SOUTH); // Add the bottom menu bar

            JPanel inputPanel = new JPanel(new GridLayout(4, 2));
            frame.add(inputPanel, BorderLayout.CENTER);

            inputPanel.add(new JLabel("Flight Name:"));
            flightNameField = new JTextField();
            inputPanel.add(flightNameField);

            inputPanel.add(new JLabel("Arrival Time:"));
            arrivalTimeField = new JTextField();
            inputPanel.add(arrivalTimeField);

            inputPanel.add(new JLabel("Departure Time:"));
            departureTimeField = new JTextField();
            inputPanel.add(departureTimeField);

            saveButton = new JButton("Save Changes");
            saveButton.addActionListener(new SaveButtonListener());
            inputPanel.add(saveButton);

            // Create a close button
            JButton closeButton = new JButton("Close");
            closeButton.setBackground(Color.RED);
            closeButton.setForeground(Color.WHITE);
            closeButton.addActionListener(e -> frame.dispose());
            inputPanel.add(closeButton);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        // Listener for the save button
        static class SaveButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save action
                // Implement your saving logic here
                System.out.println("Flight information saved.");
            }
        }
    }

    // New class for displaying flight information
    static class NewWindow {
        JFrame frames = new JFrame();

        NewWindow() {
            frames.setSize(800, 400);
            frames.setTitle("Flights Info");
            frames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frames.setLayout(new BorderLayout());

            // Create the menu bar and bottom buttons
            frames.setJMenuBar(createMenuBar()); // Set the same menu bar
            frames.add(createBottomMenuBar(), BorderLayout.SOUTH); // Add the bottom menu bar

            // Create flight data
            String[][] data = {
                {"1E345", "AirIndia", "13:00", "14:15","Bengaluru","Abu Dhabi","28"},
                {"1E346", "IndiGo", "14:00", "15:15","Lucknow","Jammu", "38"},
                {"1E347", "SpiceJet", "15:00", "16:15","Raipur","Ahemdabad","8"},
                {"1E348", "Vistara", "16:00", "17:15","Udaipur","Mysore", "2"},
                {"1E349", "GoAir", "17:00", "18:15","Surat","Patna","45"},
                {"1E345", "AirIndia", "18:00", "20:15","Patna","Delhi","28"},
                {"1E346", "IndiGo", "2:00", "4:15","Lucknow","Jammu", "38"},
                {"1E347", "SpiceJet", "5:00", "6:15","Kochi","Jaipur","8"},
                {"1E348", "Vistara", "6:00", "7:15","Guwahati","Vadodara", "2"},
                {"1E349", "GoAir", "1:00", "3:15","Hyderabad","Chennai","45"}
               
            };

            String[] columnNames = {
                "Flight Number", "Flight Name", "Arrival Time", "Departure Time","Start","Destination", "Available Seats"};

            // Create the JTable
            JTable table = new JTable(data, columnNames);

            // Create a JScrollPane and add the table to it
            JScrollPane scrollPane = new JScrollPane(table);
            frames.add(scrollPane, BorderLayout.CENTER);

                       // Make the frame visible
                       frames.setVisible(true);
                   }
               }
           
               @Override
               public void actionPerformed(ActionEvent e) {
                   // This method is not used in this implementation
                   throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
               }
           }
