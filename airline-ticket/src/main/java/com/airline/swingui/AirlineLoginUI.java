package com.airline.swingui;

import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirlineLoginUI extends JFrame {

    // MongoDB variables - # MongoDB collection setup
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

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

        Image logoImage = logoIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImage));

        int logoWidth = logo.getPreferredSize().width;
        int logoHeight = logo.getPreferredSize().height;
        int x = (backgroundPanel.getWidth() - logoWidth) / 2;
        int y = 30;

        logo.setBounds(x, y, logoWidth, logoHeight);
        backgroundPanel.add(logo);

        JLabel welcomeLabel = new JLabel("Welcome Aboard!", JLabel.CENTER);
        welcomeLabel.setBounds(50, 70, 300, 50);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        backgroundPanel.add(welcomeLabel);

        // Input Fields Start Here
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

        // MongoDB connection initialization - # MongoDB initialization
        mongoClient = MongoClients.create("mongodb://localhost:27017/airlineDB");  // Connect to MongoDB locally
        database = mongoClient.getDatabase("airlineDB");  // Access "airlineDB" database
        collection = database.getCollection("users");  // Access the "users" collection

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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = userField.getText().trim();
                String password = new String(passField.getPassword());

                if (name.equals("Admin") && email.equals("admin@gmail.com") && password.equals("admin123")) {
                    AdminDashboard adminDashboard = new AdminDashboard();
                    adminDashboard.setVisible(true);
                } else {
                    // Save user data to MongoDB - # Save data to MongoDB
                    saveUserToMongo(name, email);
                    FlightBookingUI flightBookingUI = new FlightBookingUI();
                    flightBookingUI.setVisible(true);
                }
                dispose();
            }
        });

        mainPanel.add(loginButton);

        JLabel footer = new JLabel(
                "<html><center><br>By logging in, you agree to our<br>Terms and Privacy Policy.</center></html>");
        footer.setBounds(70, 600, 260, 40);
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        backgroundPanel.add(footer);
    }

    // Method to save user data to MongoDB - # MongoDB insert method
    private static void saveUserToMongo(String name, String email) {
        // Create a new MongoDB document with the name and email
        Document userDocument = new Document("name", name)
                                    .append("email", email);

        // Insert the document into the "users" collection
        collection.insertOne(userDocument);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineLoginUI ui = new AirlineLoginUI();
            ui.setVisible(true);
        });
    }
}
