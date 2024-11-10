package com.airline.swingui;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        // Set up the main frame properties
        setTitle("Skyfall Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add the main panel to the frame
        add(createMainPanel(), BorderLayout.CENTER);
        setLocationRelativeTo(null);  // Center the window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setVisible(true);  // Show the admin dashboard window
        });
    }

    private static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton editUserDataButton = new JButton("Edit Existing User Data");
        JButton viewCustomerInfoButton = new JButton("View Customer Info");
        JButton viewFlightsButton = new JButton("View Flights");

        editUserDataButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewCustomerInfoButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewFlightsButton.setFont(new Font("Arial", Font.BOLD, 14));

        editUserDataButton.addActionListener(e -> openEditUserDataWindow());
        viewCustomerInfoButton.addActionListener(e -> openCustomerDataWindow());
        viewFlightsButton.addActionListener(e -> new ViewFlightsWindow());

        mainPanel.add(editUserDataButton);
        mainPanel.add(viewCustomerInfoButton);
        mainPanel.add(viewFlightsButton);

        return mainPanel;
    }

    private static void openEditUserDataWindow() {
        JFrame editFrame = new JFrame("Edit User Data");
        editFrame.setSize(600, 400);
        editFrame.setLayout(new BorderLayout());

        List<String[]> userData = fetchUserDataFromFile("booking_confirmation.txt");
        String[] columnNames = {"Flight Number", "Departure", "Arrival", "Date", "Time", "Price"};
        JTable table = new JTable(userData.toArray(new String[0][]), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        editFrame.add(scrollPane, BorderLayout.CENTER);
        editFrame.setVisible(true);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static List<String[]> fetchUserDataFromFile(String filePath) {
        List<String[]> userData = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Flight Number:")) {
                    String flightNumber = line.split(": ")[1].trim();
                    String departure = reader.readLine().split(": ")[1].trim();
                    String arrival = reader.readLine().split(": ")[1].trim();
                    String date = reader.readLine().split(": ")[1].trim();
                    String time = reader.readLine().split(": ")[1].trim();
                    String price = reader.readLine().split(": ")[1].trim();
                    
                    userData.add(new String[] {flightNumber, departure, arrival, date, time, price});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return userData;
    }

    private static void openCustomerDataWindow() {
        JFrame customerFrame = new JFrame("Customer Details");
        customerFrame.setSize(600, 400);
        customerFrame.setLayout(new BorderLayout());

        List<String[]> customerData = fetchCustomerDataFromFile("user.txt");

        String[] columnNames = {"Customer Name", "Contact Info"};
        JTable table = new JTable(customerData.toArray(new String[0][]), columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        customerFrame.add(scrollPane, BorderLayout.CENTER);
        customerFrame.setVisible(true);
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static List<String[]> fetchCustomerDataFromFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming each line contains "Name, Email" format
                String[] user = line.split(", ");
                if (user.length == 2) {
                    data.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    static class ViewFlightsWindow {
        JFrame flightFrame;

        ViewFlightsWindow() {
            flightFrame = new JFrame("View Flights");
            flightFrame.setSize(600, 400);
            flightFrame.setLayout(new BorderLayout());

            String[] columnNames = {"Flight Number", "Departure", "Arrival", "Date", "Time", "Price"};
            List<String[]> flightData = fetchFlightDataFromFile("booking_confirmation.txt");

            JTable table = new JTable(flightData.toArray(new String[0][]), columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            flightFrame.add(scrollPane, BorderLayout.CENTER);
            flightFrame.setVisible(true);
            flightFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private static List<String[]> fetchFlightDataFromFile(String filePath) {
        List<String[]> flightData = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\t");
                if (columns.length >= 6) { 
                    flightData.add(new String[] {
                        columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return flightData;
    }
}
