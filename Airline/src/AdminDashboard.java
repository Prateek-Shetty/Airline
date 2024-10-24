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
        frame.setVisible(true);
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            // Load the background image
            backgroundImage = new ImageIcon("C:\\Users\\hp\\Documents\\GitHub\\Airline\\Airline\\src\\assets\\Flight-into-space.jpg").getImage(); // Change the path to your image
            setLayout(null); 
            addButtons();
        }

        private void addButtons() {
            JButton welcomeButton = new JButton("Welcome to Admin Page");
            JButton flightsButton = new JButton("Flights Info");
            JButton customerDataButton = new JButton("Customer Data");
            JButton manageButton = new JButton("Manage");

            // Set button colors
            welcomeButton.setBackground(Color.BLUE);
            welcomeButton.setForeground(Color.WHITE);
            flightsButton.setBackground(Color.GREEN);
            flightsButton.setForeground(Color.BLACK);
            customerDataButton.setBackground(Color.ORANGE);
            customerDataButton.setForeground(Color.BLACK);
            manageButton.setBackground(Color.RED);
            manageButton.setForeground(Color.WHITE);

            // Set bounds for buttons
            welcomeButton.setBounds(150, 0, 250, 30);
            flightsButton.setBounds(0, 200, 150, 30);
            customerDataButton.setBounds(200, 200, 150, 30);
            manageButton.setBounds(400, 200, 150, 30);

            flightsButton.addActionListener(e -> new NewWindow());
            customerDataButton.addActionListener(e -> openCustomerDataWindow());
            manageButton.addActionListener(e -> new EditFlightInfoWindow());

            add(welcomeButton);
            add(flightsButton);
            add(customerDataButton);
            add(manageButton);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static void openCustomerDataWindow() {
        JFrame frame2 = new JFrame();
        frame2.setSize(400, 400);
        frame2.setTitle("Customer Details");
        frame2.setVisible(true);

        String[][] data = {
            {"Moksh", "87685422", "5224", "Chennai"},
            {"Jolie", "4742525", "9865", "Amsterdam"},
            // Add more data as needed
        };

        String[] columnNames = {"CustomerName", "ContactInfo", "BookingID", "FlightDestination"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 300, 100);
        frame2.add(scrollPane, BorderLayout.CENTER);
    }

    // New class for editing flight information
    static class EditFlightInfoWindow {
        JFrame frame;
        JTextField flightNameField, arrivalTimeField, departureTimeField;
        JButton saveButton;

        EditFlightInfoWindow() {
            frame = new JFrame("Edit Flight Info");
            frame.setSize(400, 300);
            frame.setLayout(new GridLayout(4, 2));

            frame.add(new JLabel("Flight Name:"));
            flightNameField = new JTextField();
            frame.add(flightNameField);

            frame.add(new JLabel("Arrival Time:"));
            arrivalTimeField = new JTextField();
            frame.add(arrivalTimeField);

            frame.add(new JLabel("Departure Time:"));
            departureTimeField = new JTextField();
            frame.add(departureTimeField);

            saveButton = new JButton("Save Changes");
            saveButton.addActionListener(new SaveButtonListener());
            frame.add(saveButton);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        // Listener for the save button
        static class SaveButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle save action
                // Close the editing window after saving
                // Implement your saving logic here
            }
        }
    }

    
    
    static class NewWindow {
        JFrame frames = new JFrame();
        JButton closeButton;

        NewWindow() {
            frames.setSize(400, 400);
            frames.setTitle("Flights Info");
            frames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frames.setLayout(new BorderLayout());

            // Create flight data
            String[][] data = {
                {"1E345", "AirIndia", "13:00", "14:15", "Chennai", "28"},
                {"1E346", "IndiGo", "14:00", "15:15", "Lucknow", "38"},
                {"1E347", "SpiceJet", "15:00", "16:15", "Delhi", "8"},
                {"1E348", "Vistara", "16:00", "17:15", "Mumbai", "2"},
                {"1E349", "GoAir", "17:00", "18:15", "Udaipur", "45"},
                // Add more flight data as needed
            };

            String[] columnNames = {
                "Flight Number", "Flight Name", "Arrival Time", "Departure Time", "Destination", "Available Seats"
            };

            // Create the JTable
            JTable table = new JTable(data, columnNames);

            // Create a JScrollPane and add the table to it
            JScrollPane scrollPane = new JScrollPane(table);
            frames.add(scrollPane, BorderLayout.CENTER);

            // Create a close button
            closeButton = new JButton("Close");
            closeButton.setBackground(Color.RED);
            closeButton.setForeground(Color.WHITE);
            closeButton.addActionListener(e -> frames.dispose());

            // Add the close button to the bottom of the frame
            frames.add(closeButton, BorderLayout.SOUTH);

            // Make the frame visible
            frames.setVisible(true);
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
	}
}