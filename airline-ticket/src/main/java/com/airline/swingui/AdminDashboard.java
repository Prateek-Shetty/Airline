package com.airline.swingui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JFrame {

    private static final String[] FLIGHT_COLUMNS = { "Flight Number", "Departure", "Arrival", "Date", "Time", "Price" };

    public AdminDashboard() {
        setTitle("Skyfall Admin Dashboard");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createSidebarPanel(), BorderLayout.WEST); // Sidebar added
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setVisible(true);
        });
    }

    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Title Label styling
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(new Color(0, 51, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Description Label styling
        JLabel descriptionLabel = new JLabel("Manage flight and customer data seamlessly.", SwingConstants.LEFT);
        descriptionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        descriptionLabel.setForeground(new Color(220, 220, 220));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // Profile icon placeholder on the right side
        JLabel profileIcon = new JLabel(new ImageIcon(
                new ImageIcon("D:\\Airline\\Airline\\airline-ticket\\target\\classes\\images\\airline_logo.png")
                        .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        profileIcon.setBorder(new EmptyBorder(0, 0, 0, 20));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(profileIcon);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(descriptionLabel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private static JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(4, 1, 0, 15)); // Adjusted to 4 rows to remove one button
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel menuLabel = new JLabel("MENU", JLabel.CENTER);
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton flightsButton = createStyledButton("Flights");
        JButton analyticsButton = createStyledButton("Analytics");
        JButton settingsButton = createStyledButton("Settings");

        sidebar.add(menuLabel);
        sidebar.add(flightsButton);
        sidebar.add(analyticsButton);
        sidebar.add(settingsButton); // Removed the customersButton

        return sidebar;
    }

    private static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 15));
        buttonPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        buttonPanel.setBackground(Color.WHITE);

        JButton editUserDataButton = createStyledButton("Edit Existing User Data");
        JButton viewFlightsButton = createStyledButton("View Flights");

        editUserDataButton.addActionListener(e -> openEditUserDataWindow());
        viewFlightsButton.addActionListener(e -> new ViewFlightsWindow());

        buttonPanel.add(editUserDataButton);
        buttonPanel.add(viewFlightsButton);

        // Analytics panel display
        JPanel analyticsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        analyticsPanel.setBackground(new Color(245, 245, 245));

        JLabel totalFlightsLabel = createAnalyticsLabel("Total Flights: 120");
        JLabel totalCustomersLabel = createAnalyticsLabel("Total Customers: 500");
        JLabel revenueLabel = createAnalyticsLabel("Revenue: $250K");

        analyticsPanel.add(totalFlightsLabel);
        analyticsPanel.add(totalCustomersLabel);
        analyticsPanel.add(revenueLabel);

        mainPanel.add(analyticsPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private static JLabel createAnalyticsLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(33, 150, 243));
        label.setOpaque(true);
        label.setBackground(new Color(235, 235, 235));
        label.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        return label;
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 136, 229));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243));
            }
        });
        return button;
    }

    private static void openEditUserDataWindow() {
        JFrame editFrame = new JFrame("Edit User Data");
        editFrame.setSize(700, 400);
        editFrame.setLayout(new BorderLayout());

        List<String[]> userData = fetchDataFromJsonFile("booking_confirmation.json", FLIGHT_COLUMNS);
        if (userData == null)
            return;

        JTable table = new JTable(userData.toArray(new String[0][]), FLIGHT_COLUMNS);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        editFrame.add(scrollPane, BorderLayout.CENTER);
        editFrame.setVisible(true);
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static List<String[]> fetchDataFromJsonFile(String filePath, String[] fields) {
        List<String[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
    
            if (!rootNode.isArray()) {
                JOptionPane.showMessageDialog(null, "Invalid JSON format. Expected an array.", "Data Error",
                        JOptionPane.ERROR_MESSAGE);
                return data;
            }
    
            for (JsonNode node : rootNode) {
                String[] rowData = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    rowData[i] = node.has(fields[i]) ? node.get(fields[i]).asText() : "N/A";
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading data from " + filePath, "File Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return data;
    }

    static class ViewFlightsWindow {
        JFrame flightFrame;

        ViewFlightsWindow() {
            flightFrame = new JFrame("View Flights");
            flightFrame.setSize(700, 400);
            flightFrame.setLayout(new BorderLayout());

            List<String[]> flightData = fetchDataFromJsonFile("booking_confirmation.json", FLIGHT_COLUMNS);
            if (flightData == null)
                return;

            JTable table = new JTable(flightData.toArray(new String[0][]), FLIGHT_COLUMNS);
            styleTable(table);

            JScrollPane scrollPane = new JScrollPane(table);
            flightFrame.add(scrollPane, BorderLayout.CENTER);
            flightFrame.setVisible(true);
            flightFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(33, 150, 243));
        table.getTableHeader().setForeground(Color.WHITE);
    }
}
