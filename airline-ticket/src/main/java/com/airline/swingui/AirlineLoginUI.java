package com.airline.swingui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AirlineLoginUI extends JFrame {

    public AirlineLoginUI() {
        setTitle("Airline Ticket Login");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/images/airplane_background.jpg"));
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/images/airline_logo.png"));

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setBounds(0, 0, 400, 700);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255, 180));
        mainPanel.setBounds(40, 180, 320, 400);
        mainPanel.setLayout(null);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        backgroundPanel.add(mainPanel);

        // Logo
        Image logoImage = logoIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImage));
        logo.setBounds((backgroundPanel.getWidth() - logo.getPreferredSize().width) / 2, 30, logo.getPreferredSize().width, logo.getPreferredSize().height);
        backgroundPanel.add(logo);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome Aboard!", JLabel.CENTER);
        welcomeLabel.setBounds(50, 70, 300, 50);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        backgroundPanel.add(welcomeLabel);

        // Input Labels
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 40, 60, 25);
        mainPanel.add(nameLabel);
        
        JTextField nameField = new JTextField();
        nameField.setBounds(80, 40, 200, 25);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        mainPanel.add(nameField);

        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(20, 90, 60, 25);
        mainPanel.add(userLabel);
        
        JTextField userField = new JTextField();
        userField.setBounds(80, 90, 200, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        mainPanel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 140, 80, 25);
        mainPanel.add(passLabel);
        
        JPasswordField passField = new JPasswordField();
        passField.setBounds(80, 140, 200, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        mainPanel.add(passField);

        // Login Button
        JButton loginButton = new JButton("LOG IN");
        loginButton.setBounds(40, 200, 230, 40);
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder());

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 123, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(33, 150, 243));
            }
        });

        loginButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = userField.getText().trim();
            String password = new String(passField.getPassword());

            // Admin login check
            if (name.equals("Admin") && email.equals("admin@gmail.com") && password.equals("admin123")) {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.setVisible(true);
            } else {
                // Save user data to user.txt file
                saveUserToFile(name, email);
                FlightBookingUI flightBookingUI = new FlightBookingUI();
                flightBookingUI.setVisible(true);
            }
            dispose();
        });

        mainPanel.add(loginButton);

        // Footer
        JLabel footer = new JLabel("<html><center><br>By logging in, you agree to our<br>Terms and Privacy Policy.</center></html>");
        footer.setBounds(70, 600, 260, 40);
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        backgroundPanel.add(footer);
    }

    // Method to save user data to a file
    private static void saveUserToFile(String name, String email) {
        try {
            File file = new File("user.txt");
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)); // Append mode
            writer.write(name + ", " + email);
            writer.newLine(); // Add a new line after each entry
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineLoginUI ui = new AirlineLoginUI();
            ui.setVisible(true);
        });
    }
}
