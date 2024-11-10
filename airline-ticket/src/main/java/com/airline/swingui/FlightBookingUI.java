package com.airline.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;



public class FlightBookingUI extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultTableModel flightsModel;
    private JTable flightsTable;
    private JComboBox<String> departureField;
    private JComboBox<String> arrivalField;
    private JTextArea confirmationArea;
    private JButton datePickerButton;
    private JComboBox<String> timeComboBox;
    private JLabel dateLabel;
    private Calendar selectedDate = Calendar.getInstance();

    private final Map<String, double[]> airportCoordinates;

    public FlightBookingUI() {
        setTitle("Flight Booking System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize airport coordinates for distance calculation
        airportCoordinates = initAirportCoordinates();

        // Set up background image
        JLabel backgroundLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = loadImage("/assets/bookingbg.jpg");
                if (backgroundIcon != null) {
                    Image bgImage = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(),
                            Image.SCALE_SMOOTH);
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundLabel.setLayout(new BorderLayout());

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new OverlayLayout(contentPane));
        contentPane.add(backgroundLabel);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.setBackground(new Color(255, 255, 255, 200));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));
        tabbedPane.setForeground(new Color(50, 50, 50));

        JPanel bookingPanel = createBookingPanel();
        tabbedPane.addTab("Book Flight", bookingPanel);

        JPanel confirmationPanel = createConfirmationPanel();
        tabbedPane.addTab("Booking Confirmation", confirmationPanel);

        contentPane.add(tabbedPane);
        setContentPane(contentPane);
        setVisible(true);
    }

    private Map<String, double[]> initAirportCoordinates() {
        Map<String, double[]> coordinates = new HashMap<>();
        coordinates.put("Bangalore Kempegowda (IN)", new double[]{12.9716, 77.5946});
        coordinates.put("Delhi (IN)", new double[]{28.7041, 77.1025});
        coordinates.put("Bangkok (TH)", new double[]{13.7563, 100.5018});
        coordinates.put("London Heathrow (GB)", new double[]{51.4700, -0.4543});
        coordinates.put("New York JFK (US)", new double[]{40.6413, -73.7781});
        coordinates.put("Paris Charles de Gaulle (FR)", new double[]{49.0097, 2.5479});
        // Add other airports as needed
        return coordinates;
    }

    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel(new GridBagLayout());
        bookingPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel departureLabel = new JLabel("Departure City:");
        String[] airports = airportCoordinates.keySet().toArray(new String[0]);
        departureField = new JComboBox<>(airports);
        departureField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        bookingPanel.add(departureLabel, gbc);
        gbc.gridx = 1;
        bookingPanel.add(departureField, gbc);

        JLabel arrivalLabel = new JLabel("Arrival City:");
        arrivalField = new JComboBox<>(airports);
        arrivalField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        bookingPanel.add(arrivalLabel, gbc);
        gbc.gridx = 1;
        bookingPanel.add(arrivalField, gbc);

        dateLabel = new JLabel("Select Date:");
        datePickerButton = createRoundedButton("Pick Date");
        datePickerButton.addActionListener(e -> showDateTimePicker());
        gbc.gridx = 0;
        gbc.gridy = 2;
        bookingPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        bookingPanel.add(datePickerButton, gbc);

        JLabel timeLabel = new JLabel("Select Time:");
        timeComboBox = new JComboBox<>(new String[]{"Select Time", "09:00 AM", "12:00 PM", "03:00 PM"});
        gbc.gridx = 0;
        gbc.gridy = 3;
        bookingPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        bookingPanel.add(timeComboBox, gbc);

        JButton searchFlightsButton = createRoundedButton("Search Flights");
        searchFlightsButton.addActionListener(e -> searchFlights());
        gbc.gridx = 1;
        gbc.gridy = 4;
        bookingPanel.add(searchFlightsButton, gbc);

        flightsModel = new DefaultTableModel(new String[]{"Flight Number", "Departure", "Arrival", "Price"}, 0);
        flightsTable = new JTable(flightsModel);
        flightsTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(flightsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        bookingPanel.add(scrollPane, gbc);

        JButton bookFlightButton = createRoundedButton("Book Flight");
        bookFlightButton.addActionListener(e -> bookFlight());
        gbc.gridy = 6;
        bookingPanel.add(bookFlightButton, gbc);

        return bookingPanel;
    }

    private JPanel createConfirmationPanel() {
        JPanel confirmationPanel = new JPanel(new BorderLayout());
        confirmationPanel.setBackground(new Color(255, 255, 255, 200));

        confirmationArea = new JTextArea();
        confirmationArea.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(confirmationArea);
        confirmationPanel.add(scrollPane, BorderLayout.CENTER);

        return confirmationPanel;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    /**
     * 
     */
    private void showDateTimePicker() {
        // Create Date Picker Model
        UtilDateModel model = new UtilDateModel();
        model.setDate(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        model.setSelected(true);
    
        // Properties for date picker (optional customization)
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
    
        // Initialize date panel and date picker
        JDatePanelImpl datePanel = new JDatePanelImpl();
        Component datePicker = new FlightBookingUI();
    
        // Create Time ComboBox with predefined times
        JComboBox<String> timeComboBox = new JComboBox<>(new String[] {
            "09:00 AM", "12:00 PM", "03:00 PM", "06:00 PM", "09:00 PM"
        });
        timeComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        timeComboBox.setForeground(new Color(50, 50, 50));
        timeComboBox.setBackground(new Color(255, 255, 255));
    
        // Panel for date and time selection
        JPanel dateTimePanel = new JPanel(new BorderLayout());
        dateTimePanel.add(datePicker, BorderLayout.NORTH);
        dateTimePanel.add(timeComboBox, BorderLayout.SOUTH);
    
        // Show the date and time picker in a dialog
        int result = JOptionPane.showConfirmDialog(this, dateTimePanel, "Select Date and Time", JOptionPane.OK_CANCEL_OPTION);
    
        // Handle the selected date and time
        if (result == JOptionPane.OK_OPTION) {
            java.util.Date selectedDateValue = (java.util.Date) datePicker.getModel().getValue();
            String selectedTime = (String) timeComboBox.getSelectedItem();
    
            // Combine the date and time selections
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDateValue);
            
            // Convert selected time into hours and minutes
            String[] timeParts = selectedTime.split(" ");
            String[] hourMinute = timeParts[0].split(":");
            int hour = Integer.parseInt(hourMinute[0]);
            int minute = Integer.parseInt(hourMinute[1]);
            
            if (timeParts[1].equals("PM") && hour < 12) {
                hour += 12;  // Convert PM to 24-hour format
            } else if (timeParts[1].equals("AM") && hour == 12) {
                hour = 0;  // Midnight case
            }
    
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
    
            // Update the selected date and time label
            selectedDate.setTime(calendar.getTime());
            dateLabel.setText("Selected Date and Time: " + selectedDate.get(Calendar.YEAR) + "-"
                    + (selectedDate.get(Calendar.MONTH) + 1) + "-" + selectedDate.get(Calendar.DAY_OF_MONTH)
                    + " " + String.format("%02d:%02d", hour, minute));
        }
    }
    
    

    private void searchFlights() {
        String departure = (String) departureField.getSelectedItem();
        String arrival = (String) arrivalField.getSelectedItem();
        @SuppressWarnings("unused")
        String time = (String) timeComboBox.getSelectedItem();

        String flightNumber = generateFlightNumber(departure, arrival);
        String flightPrice = calculatePrice(departure, arrival);

        flightsModel.addRow(new Object[]{flightNumber, departure, arrival, flightPrice});
    }

    private String generateFlightNumber(String departure, String arrival) {
        return "AI" + (int) (Math.random() * 1000) + "-" + departure.substring(0, 3) + arrival.substring(0, 3);
    }

    private String calculatePrice(String departure, String arrival) {
        int distance = calculateDistance(departure, arrival);
        int basePrice = 300;
        int price = basePrice + (distance / 100) * 10;
        return "$" + price;
    }

    private int calculateDistance(String departure, String arrival) {
        double[] departureCoords = airportCoordinates.get(departure);
        double[] arrivalCoords = airportCoordinates.get(arrival);

        if (departureCoords == null || arrivalCoords == null) {
            return 0;
        }

        double lat1 = Math.toRadians(departureCoords[0]);
        double lon1 = Math.toRadians(departureCoords[1]);
        double lat2 = Math.toRadians(arrivalCoords[0]);
        double lon2 = Math.toRadians(arrivalCoords[1]);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double earthRadiusKm = 6371;
        return (int) (earthRadiusKm * c);
    }

    private void bookFlight() {
        int selectedRow = flightsTable.getSelectedRow();

        if (selectedRow != -1) {
            String flightNumber = (String) flightsModel.getValueAt(selectedRow, 0);
            String departure = (String) flightsModel.getValueAt(selectedRow, 1);
            String arrival = (String) flightsModel.getValueAt(selectedRow, 2);
            String price = (String) flightsModel.getValueAt(selectedRow, 3);
            String time = (String) timeComboBox.getSelectedItem();

            confirmationArea.setText("Booking Confirmation:\n\nFlight Number: " + flightNumber + "\nDeparture: " +
                    departure + "\nArrival: " + arrival + "\nPrice: " + price + "\nTime: " + time + "\nDate: " +
                    (selectedDate.get(Calendar.MONTH) + 1) + "/" + selectedDate.get(Calendar.DAY_OF_MONTH) + "/" +
                    selectedDate.get(Calendar.YEAR));
            tabbedPane.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a flight to book.");
        }
    }

    private ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FlightBookingUI::new);
    }
}
