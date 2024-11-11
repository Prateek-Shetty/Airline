package com.airline.swingui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Skyfall Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setVisible(true);
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

        List<String[]> userData = fetchUserDataFromJsonFile("booking_confirmation.json");
        String[] columnNames = {"Flight Number", "Departure", "Arrival", "Date", "Time", "Price"};
        JTable table = new JTable(userData.toArray(new String[0][]), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        editFrame.add(scrollPane, BorderLayout.CENTER);
        editFrame.setVisible(true);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static List<String[]> fetchUserDataFromJsonFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
    
            for (JsonNode booking : rootNode) {
                String flightNumber = booking.has("Flight Number") ? booking.get("Flight Number").asText() : "N/A";
                String departure = booking.has("Departure") ? booking.get("Departure").asText() : "N/A";
                String arrival = booking.has("Arrival") ? booking.get("Arrival").asText() : "N/A";
                String date = booking.has("Date") ? booking.get("Date").asText() : "N/A";
                String time = booking.has("Time") ? booking.get("Time").asText() : "N/A";
                String price = booking.has("Price") ? booking.get("Price").asText() : "N/A";
    
                data.add(new String[]{flightNumber, departure, arrival, date, time, price});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return data;
    }

    private static void openCustomerDataWindow() {
        JFrame customerFrame = new JFrame("Customer Details");
        customerFrame.setSize(600, 400);
        customerFrame.setLayout(new BorderLayout());

        List<String[]> customerData = fetchCustomerDataFromJsonFile("user.json");

        String[] columnNames = {"Customer Name", "Contact Info"};
        JTable table = new JTable(customerData.toArray(new String[0][]), columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        customerFrame.add(scrollPane, BorderLayout.CENTER);
        customerFrame.setVisible(true);
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static List<String[]> fetchCustomerDataFromJsonFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            for (JsonNode node : rootNode) {
                String name = node.get("name").asText();
                String email = node.get("email").asText();
                data.add(new String[]{name, email});
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
            List<String[]> flightData = fetchFlightDataFromJsonFile("booking_confirmation.json");

            JTable table = new JTable(flightData.toArray(new String[0][]), columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            flightFrame.add(scrollPane, BorderLayout.CENTER);
            flightFrame.setVisible(true);
            flightFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    public static List<String[]> fetchFlightDataFromJsonFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
    
            for (JsonNode flight : rootNode) {
                String flightNumber = flight.has("Flight Number") ? flight.get("Flight Number").asText() : "N/A";
                String departure = flight.has("Departure") ? flight.get("Departure").asText() : "N/A";
                String arrival = flight.has("Arrival") ? flight.get("Arrival").asText() : "N/A";
                String date = flight.has("Date") ? flight.get("Date").asText() : "N/A";
                String time = flight.has("Time") ? flight.get("Time").asText() : "N/A";
                String price = flight.has("Price") ? flight.get("Price").asText() : "N/A";
    
                data.add(new String[]{flightNumber, departure, arrival, date, time, price});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
                return data;
    }
}
