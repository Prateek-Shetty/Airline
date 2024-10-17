import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("Airline Ticket Management");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Set background gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(134, 123, 223), 0, getHeight(), new Color(173, 143, 244));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 350, 600);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        // Create the main panel to simulate the card-style look
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(30, 100, 290, 400);
        mainPanel.setLayout(null);
        mainPanel.setBorder(BorderFactory.createEmptyBorder());
        backgroundPanel.add(mainPanel);

        // User name label and text field
        JLabel userLabel = new JLabel(new ImageIcon("user_icon.png"));
        userLabel.setBounds(20, 40, 20, 20);
        mainPanel.add(userLabel);

        JTextField userField = new JTextField("User name / Email");
        userField.setBounds(50, 40, 200, 25);
        userField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        mainPanel.add(userField);

        // Password label and text field
        JLabel passLabel = new JLabel(new ImageIcon("lock_icon.png"));
        passLabel.setBounds(20, 90, 20, 20);
        mainPanel.add(passLabel);

        JPasswordField passField = new JPasswordField("Password");
        passField.setBounds(50, 90, 200, 25);
        passField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        mainPanel.add(passField);

        // Log in button
        JButton loginButton = new JButton("LOG IN NOW");
        loginButton.setBounds(40, 150, 210, 40);
        loginButton.setBackground(new Color(90, 80, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(loginButton);

        // Social media icons and labels
        JLabel socialLabel = new JLabel("log in via");
        socialLabel.setBounds(120, 230, 100, 30);
        mainPanel.add(socialLabel);

        JLabel instagramIcon = new JLabel(new ImageIcon("instagram_icon.png"));
        instagramIcon.setBounds(60, 260, 30, 30);
        mainPanel.add(instagramIcon);

        JLabel facebookIcon = new JLabel(new ImageIcon("facebook_icon.png"));
        facebookIcon.setBounds(120, 260, 30, 30);
        mainPanel.add(facebookIcon);

        JLabel twitterIcon = new JLabel(new ImageIcon("twitter_icon.png"));
        twitterIcon.setBounds(180, 260, 30, 30);
        mainPanel.add(twitterIcon);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginUI ui = new LoginUI();
            ui.setVisible(true);
        });
    }
}
