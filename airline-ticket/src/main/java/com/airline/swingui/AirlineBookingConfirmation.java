package com.airline.swingui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirlineBookingConfirmation extends JFrame {

    public AirlineBookingConfirmation(String details) {
        setTitle("Booking Confirmation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Create a gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(85, 170, 255),
                        getWidth(), getHeight(), new Color(0, 85, 170));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Booking Confirmed!");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        headerLabel.setForeground(new Color(255, 245, 238));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Boarding Pass with dynamic details from the JSON file
        JPanel boardingPass = createBoardingPassFromFile();
        mainPanel.add(boardingPass, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);

        JLabel footerLabel = new JLabel("Thank you for choosing our airline!");
        footerLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        footerLabel.setForeground(new Color(255, 245, 238));
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        footerPanel.add(footerLabel, BorderLayout.NORTH);

        // Book Another Flight button
        JButton bookAnotherButton = new JButton("Book Another Flight");
        bookAnotherButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        bookAnotherButton.setForeground(Color.WHITE);
        bookAnotherButton.setBackground(new Color(70, 130, 180));
        bookAnotherButton.setFocusPainted(false);
        bookAnotherButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bookAnotherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FlightBookingUI().setVisible(true); // Replace with your FlightBookingUI class
                dispose();
            }
        });

        footerPanel.add(bookAnotherButton, BorderLayout.SOUTH);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Save the details to both the admin file and the current booking file
        saveBookingDetails(details);
    }

    private JPanel createBoardingPassFromFile() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw boarding pass background
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Float(10, 10, getWidth() - 20, getHeight() - 20, 20, 20));
                g2d.setColor(new Color(220, 220, 220));
                g2d.setStroke(new BasicStroke(1));
                g2d.draw(new RoundRectangle2D.Float(10, 10, getWidth() - 20, getHeight() - 20, 20, 20));

                // Draw airline logo
                g2d.setColor(new Color(0, 85, 170));
                g2d.fill(new Ellipse2D.Float(30, 30, 60, 60));
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("SA", 45, 70);

                // Dotted line for boarding pass division
                g2d.setColor(new Color(200, 200, 200));
                Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{8, 6}, 0);
                g2d.setStroke(dashed);
                g2d.drawLine(30, getHeight() - 100, getWidth() - 30, getHeight() - 100);
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(255, 255, 255, 180));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // Read data from JSON file and display it
        String details = readBookingDetailsFromFile();
        addLabelAndValue(panel, gbc, "Booking Details:", details, 0);

        return panel;
    }

    private String readBookingDetailsFromFile() {
        StringBuilder details = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Read from current_booking.json
            File file = new File("current_booking.json");
            if (file.exists()) {
                Map<String, String> bookingDetails = mapper.readValue(file, Map.class);
                for (Map.Entry<String, String> entry : bookingDetails.entrySet()) {
                    details.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }
            } else {
                details.append("No current booking found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            details.append("Error loading booking details.");
        }

        return details.toString();
    }

    private void saveBookingDetails(String details) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert details string to HashMap for JSON compatibility
            HashMap<String, String> bookingDetails = new HashMap<>();
            for (String line : details.split("\n")) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    bookingDetails.put(parts[0], parts[1]);
                }
            }

            // Append to booking_confirmation.json
            File bookingFile = new File("booking_confirmation.json");
            List<HashMap<String, String>> bookings;

            if (bookingFile.exists()) {
                bookings = new ArrayList<>(List.of(mapper.readValue(bookingFile, HashMap[].class)));
            } else {
                bookings = new ArrayList<>();
            }
            bookings.add(bookingDetails);

            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            writer.writeValue(bookingFile, bookings);

            // Overwrite current_booking.json
            writer.writeValue(new File("current_booking.json"), bookingDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLabelAndValue(JPanel panel, GridBagConstraints gbc, String labelText, String valueText, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(70, 130, 180));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel value = new JLabel("<html>" + valueText.replace("\n", "<br>") + "</html>");
        value.setFont(new Font("SansSerif", Font.PLAIN, 14));
        value.setForeground(Color.DARK_GRAY);
        panel.add(value, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineBookingConfirmation frame = new AirlineBookingConfirmation("Your flight details here.");
            frame.setVisible(true);
        });
    }
}
