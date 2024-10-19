import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirlineLoginUI extends JFrame {

    public AirlineLoginUI() {
        setTitle("Airline Ticket Login");
        setSize(400, 700); // Larger window size for more space
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        // Disable maximizing the window
        setResizable(false);

        // Load images (using getClass().getResource() for classpath access)
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/assets/airplane_background.jpg"));
        ImageIcon userIcon = new ImageIcon(getClass().getResource("/assets/user_icon.png"));
        ImageIcon lockIcon = new ImageIcon(getClass().getResource("/assets/lock_icon.png"));
        ImageIcon instagramIcon = new ImageIcon(getClass().getResource("/assets/instagram_icon.png"));
        ImageIcon facebookIcon = new ImageIcon(getClass().getResource("/assets/facebook_icon.png"));
        ImageIcon twitterIcon = new ImageIcon(getClass().getResource("/assets/twitter_icon.png"));
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/assets/airline_logo.png"));

        // Background image: airplane or cloud-themed
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

        // Airline-themed panel (ticket-like appearance)
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255, 180)); // Semi-transparent white
        mainPanel.setBounds(40, 180, 320, 400);
        mainPanel.setLayout(null);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        backgroundPanel.add(mainPanel);

        // Load and scale the logo
        Image logoImage = logoIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImage));

        // Center the logo
        int logoWidth = logo.getPreferredSize().width;
        int logoHeight = logo.getPreferredSize().height;
        int x = (backgroundPanel.getWidth() - logoWidth) / 2;
        int y = 30; // Fixed vertical position; adjust as needed

        logo.setBounds(x, y, logoWidth, logoHeight);
        backgroundPanel.add(logo);

        // Welcome title
        JLabel welcomeLabel = new JLabel("Welcome Aboard!", JLabel.CENTER);
        welcomeLabel.setBounds(50, 70, 300, 50);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        backgroundPanel.add(welcomeLabel);

        // User icon & field
        Image scaledUserIcon = userIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel userLabel = new JLabel(new ImageIcon(scaledUserIcon));
        userLabel.setBounds(20, 40, 20, 20);
        mainPanel.add(userLabel);

        JTextField userField = new JTextField("User name / Email");
        userField.setBounds(50, 40, 230, 25);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setForeground(Color.GRAY);
        userField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        mainPanel.add(userField);

        // Password icon & field
        Image scaledLockIcon = lockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel passLabel = new JLabel(new ImageIcon(scaledLockIcon));
        passLabel.setBounds(20, 90, 20, 20);
        mainPanel.add(passLabel);

        JPasswordField passField = new JPasswordField("Password");
        passField.setBounds(50, 90, 230, 25);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setForeground(Color.GRAY);
        passField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        mainPanel.add(passField);

        // "Log In" Button with hover effect
        JButton loginButton = new JButton("LOG IN");
        loginButton.setBounds(40, 150, 230, 40);
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder());

        // Hover effect for button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 123, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(33, 150, 243));
            }
        });

        // Action listener for the LOG IN button to redirect to Flight Booking UI
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirect to Flight Booking UI
                FlightBookingUI flightBookingUI = new FlightBookingUI();
                flightBookingUI.setVisible(true);
                dispose(); // Close the current login window
            }
        });

        mainPanel.add(loginButton);

        // Forgot password link
        JLabel forgotPassLabel = new JLabel("Forgot Password?");
        forgotPassLabel.setBounds(100, 210, 150, 25);
        forgotPassLabel.setForeground(Color.BLUE);
        forgotPassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(forgotPassLabel);

        // Social media logins (with icons)
        JLabel socialLabel = new JLabel("Or log in with:");
        socialLabel.setBounds(105, 250, 150, 25);
        socialLabel.setForeground(Color.GRAY);
        mainPanel.add(socialLabel);

        // Social media icons (scaled properly)
        Image scaledInstagramIcon = instagramIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel instagramLabel = new JLabel(new ImageIcon(scaledInstagramIcon));
        instagramLabel.setBounds(60, 280, 30, 30);
        mainPanel.add(instagramLabel);

        Image scaledFacebookIcon = facebookIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel facebookLabel = new JLabel(new ImageIcon(scaledFacebookIcon));
        facebookLabel.setBounds(130, 280, 30, 30);
        mainPanel.add(facebookLabel);

        Image scaledTwitterIcon = twitterIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel twitterLabel = new JLabel(new ImageIcon(scaledTwitterIcon));
        twitterLabel.setBounds(200, 280, 30, 30);
        mainPanel.add(twitterLabel);

        // Footer (terms, policies)
        JLabel footer = new JLabel("<html><center><br>By logging in, you agree to our<br>Terms and Privacy Policy.</center></html>");
        footer.setBounds(70, 600, 260, 40);
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("Arial", Font.PLAIN, 12));
        backgroundPanel.add(footer);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AirlineLoginUI ui = new AirlineLoginUI();
            ui.setVisible(true);
        });
    }
}
