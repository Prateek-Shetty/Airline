package com.airline.swingui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlightBookingUI extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultTableModel flightsModel;
    private JTable flightsTable;
    private JTextArea confirmationArea;
    private JButton datePickerButton;
    private JComboBox<String> departureComboBox;
    private JComboBox<String> arrivalComboBox;
    private Calendar selectedDate = Calendar.getInstance();

    public FlightBookingUI() {
        setTitle("Flight Booking System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up background image
        JLabel backgroundLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = loadImage("/assets/bookingbg.jpg");
                if (backgroundIcon != null) {
                    Image bgImage = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundLabel.setLayout(new BorderLayout());

        // Main content
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new OverlayLayout(contentPane));
        contentPane.add(backgroundLabel);

        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.setBackground(new Color(255, 255, 255, 200));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));
        tabbedPane.setForeground(new Color(50, 50, 50));

        // Booking Tab
        JPanel bookingPanel = createBookingPanel();
        tabbedPane.addTab("Book Flight", bookingPanel);

        // Confirmation Tab
        JPanel confirmationPanel = createConfirmationPanel();
        tabbedPane.addTab("Booking Confirmation", confirmationPanel);

        // Add Tabbed Pane to Frame
        contentPane.add(tabbedPane);
        setContentPane(contentPane);
        setVisible(true);
    }

    private ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Image not found: " + e.getMessage());
            return null;
        }
    }

    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new GridBagLayout());
        bookingPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Departure Field with JComboBox
        JLabel departureLabel = new JLabel("Departure City:");
        String[] cities = {
            "Select City", "New York (US)", "Los Angeles (US)", "Chicago (US)", "San Francisco (US)",
            "Houston (US)", "Miami (US)", "Boston (US)", "Washington D.C. (US)", "Seattle (US)", "Atlanta (US)",
            "Toronto (CA)", "Vancouver (CA)", "Montreal (CA)", "London (UK)", "Berlin (DE)", "Paris (FR)",
            "Mumbai (IN)", "Delhi (IN)", "Bangalore (IN)", "Shanghai (CN)", "Tokyo (JP)", "Sydney (AU)"
        };

        departureComboBox = new JComboBox<>(cities);
        departureComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        bookingPanel.add(departureLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        bookingPanel.add(departureComboBox, gbc);

        // Arrival Field with JComboBox
        JLabel arrivalLabel = new JLabel("Arrival City:");
        arrivalComboBox = new JComboBox<>(cities);
        arrivalComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        gbc.gridx = 0;
        gbc.gridy = 1;
        bookingPanel.add(arrivalLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        bookingPanel.add(arrivalComboBox, gbc);

        // Date Picker
        JLabel dateLabel = new JLabel("Select Date:");
        datePickerButton = createRoundedButton("Pick Date");
        gbc.gridx = 0;
        gbc.gridy = 2;
        bookingPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        bookingPanel.add(datePickerButton, gbc);

        // Add ActionListener for Date Picker Button
        datePickerButton.addActionListener(e -> showDatePicker());

        add(bookingPanel);
        setVisible(true);

        // Search Flights Button
        JButton searchFlightsButton = createRoundedButton("Search Flights");
        searchFlightsButton.addActionListener(e -> searchFlights());
        gbc.gridx = 1;
        gbc.gridy = 3;
        bookingPanel.add(searchFlightsButton, gbc);

        // Flights Table
        flightsModel = new DefaultTableModel(new String[]{"Flight Number", "Departure", "Arrival", "Date", "Time", "Price"}, 0);
        flightsTable = new JTable(flightsModel);
        flightsTable.setFillsViewportHeight(true);
        flightsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        flightsTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(flightsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        bookingPanel.add(scrollPane, gbc);

        // Book Flight Button
        JButton bookFlightButton = createRoundedButton("Book Flight");
        bookFlightButton.addActionListener(e -> bookFlight());
        gbc.gridy = 5;
        bookingPanel.add(bookFlightButton, gbc);

        return bookingPanel;
    }

    private void searchFlights() {
        String departure = (String) departureComboBox.getSelectedItem();
        String arrival = (String) arrivalComboBox.getSelectedItem();
        String date = datePickerButton.getText();

        if ("Select City".equals(departure) || "Select City".equals(arrival) || "Pick Date".equals(date)) {
            JOptionPane.showMessageDialog(this, "Please select departure city, arrival city, and date.", "Incomplete Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        flightsModel.setRowCount(0);
        List<Flight> flights = fetchFlightsFromBackend(departure, arrival, date);

        for (Flight flight : flights) {
            flightsModel.addRow(new Object[]{flight.flightNumber, flight.departure, flight.arrival, flight.date, flight.time, flight.price});
        }
    }

    private List<Flight> fetchFlightsFromBackend(String departure, String arrival, String date) {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("FL123", departure, arrival, date, "09:00 AM", "$200"));
        flights.add(new Flight("FL456", departure, arrival, date, "02:00 PM", "$250"));
        flights.add(new Flight("FL789", departure, arrival, date, "07:00 PM", "$300"));
        return flights;
    }

    private void bookFlight() {
        int selectedRow = flightsTable.getSelectedRow();
        if (selectedRow != -1) {
            String flightNumber = flightsModel.getValueAt(selectedRow, 0).toString();
            String departure = flightsModel.getValueAt(selectedRow, 1).toString();
            String arrival = flightsModel.getValueAt(selectedRow, 2).toString();
            String date = flightsModel.getValueAt(selectedRow, 3).toString();
            String time = flightsModel.getValueAt(selectedRow, 4).toString();
            String price = flightsModel.getValueAt(selectedRow, 5).toString();

            String confirmationDetails = String.format("Flight Number: %s\nDeparture: %s\nArrival: %s\nDate: %s\nTime: %s\nPrice: %s", flightNumber, departure, arrival, date, time, price);
            new AirlineBookingConfirmation(confirmationDetails);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a flight to book.", "No Flight Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private JPanel createConfirmationPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Booking Confirmation Page"));
        return panel;
    }

    private void showDatePicker() {
        // Popup for selecting a date
        JDialog dateDialog = new JDialog(this, "Select Date", true);
        dateDialog.setSize(300, 300);
        dateDialog.setLayout(new BorderLayout());
        dateDialog.setLocationRelativeTo(this);

        JPanel calendarPanel = new JPanel(new GridLayout(7, 7));
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel("")); // Empty slots for days before 1st
        }

        for (int day = 1; day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
            final int selectedDay = day; // Create a new final variable
            JButton dayButton = new JButton(String.valueOf(day));
            
            dayButton.addActionListener(e -> {
                selectedDate.set(Calendar.DAY_OF_MONTH, selectedDay); // Use the final variable here
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                datePickerButton.setText(dateFormat.format(selectedDate.getTime()));
                dateDialog.dispose();
            });
            
            calendarPanel.add(dayButton);
        }

        dateDialog.add(calendarPanel, BorderLayout.CENTER);
        dateDialog.setVisible(true);
    }
    
    private static class Flight {
        String flightNumber, departure, arrival, date, time, price;

        Flight(String flightNumber, String departure, String arrival, String date, String time, String price) {
            this.flightNumber = flightNumber;
            this.departure = departure;
            this.arrival = arrival;
            this.date = date;
            this.time = time;
            this.price = price;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FlightBookingUI();
        });
    }
}
