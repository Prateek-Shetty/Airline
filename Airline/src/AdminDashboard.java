import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new BackgroundPanel());
        setVisible(true);
    }

    static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel() {
            backgroundImage = new ImageIcon(getClass().getResource("/assets/Flight-into-space.jpg")).getImage();
            setLayout(new BorderLayout());

            add(createHeaderPanel(), BorderLayout.NORTH);
            add(createButtonPanel(), BorderLayout.CENTER);
        }

        private JPanel createHeaderPanel() {
            JPanel headerPanel = new JPanel();
            headerPanel.setOpaque(false);
            JLabel titleLabel = new JLabel("Admin Dashboard");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
            titleLabel.setForeground(new Color(255, 215, 0));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            headerPanel.add(titleLabel);
            return headerPanel;
        }

        private JPanel createButtonPanel() {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

            // Buttons with new styles
            JButton flightsButton = createStyledButton("Flights Info", Color.CYAN);
            JButton customerDataButton = createStyledButton("Customer Data", new Color(173, 216, 230));
            JButton manageButton = createStyledButton("Manage", new Color(50, 205, 50));
            JButton settingsButton = createStyledButton("Settings", new Color(255, 99, 71));

            flightsButton.addActionListener(e -> new NewWindow());
            customerDataButton.addActionListener(e -> openCustomerDataWindow());
            manageButton.addActionListener(e -> new EditFlightInfoWindow());
            settingsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings not yet implemented!"));

            buttonPanel.add(flightsButton);
            buttonPanel.add(customerDataButton);
            buttonPanel.add(manageButton);
            buttonPanel.add(settingsButton);
            return buttonPanel;
        }

        private JButton createStyledButton(String text, Color color) {
            JButton button = new JButton(text);
            button.setBackground(color.darker());
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Add hover effect
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(color.brighter());
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(color.darker());
                }
            });
            return button;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static void openCustomerDataWindow() {
        JFrame frame2 = new JFrame("Customer Details");
        frame2.setSize(500, 400);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame2.setLayout(new BorderLayout());

        String[][] data = {
                {"Moksh", "87685422", "5224", "Chennai"},
                {"Jolie", "4742525", "9865", "Amsterdam"},
        };
        String[] columnNames = {"CustomerName", "ContactInfo", "BookingID", "FlightDestination"};
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame2.add(scrollPane, BorderLayout.CENTER);

        frame2.setVisible(true);
    }

    static class EditFlightInfoWindow {
        JFrame frame;
        JTextField flightNameField, arrivalTimeField, departureTimeField;
        JButton saveButton;

        EditFlightInfoWindow() {
            frame = new JFrame("Edit Flight Info");
            frame.setSize(450, 300);
            frame.setLayout(new GridLayout(4, 2, 10, 10));

            frame.add(new JLabel("Flight Name:"));
            flightNameField = new JTextField();
            frame.add(flightNameField);

            frame.add(new JLabel("Arrival Time:"));
            arrivalTimeField = new JTextField();
            frame.add(arrivalTimeField);

            frame.add(new JLabel("Departure Time:"));
            departureTimeField = new JTextField();
            frame.add(departureTimeField);

            saveButton = new JButton("Save Changes");
            saveButton.setBackground(new Color(60, 179, 113));
            saveButton.setForeground(Color.WHITE);
            saveButton.setFont(new Font("Arial", Font.BOLD, 14));
            saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            saveButton.addActionListener(new SaveButtonListener());
            frame.add(saveButton);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        static class SaveButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Flight Information Saved!");
            }
        }
    }

    static class NewWindow {
        JFrame frames;
        JButton closeButton;

        NewWindow() {
            frames = new JFrame("Flights Info");
            frames.setSize(500, 400);
            frames.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frames.setLayout(new BorderLayout());

            String[][] data = {
                    {"1E345", "AirIndia", "13:00", "14:15", "Chennai", "28"},
                    {"1E346", "IndiGo", "14:00", "15:15", "Lucknow", "38"},
                    {"1E347", "SpiceJet", "15:00", "16:15", "Delhi", "8"},
                    {"1E348", "Vistara", "16:00", "17:15", "Mumbai", "2"},
                    {"1E349", "GoAir", "17:00", "18:15", "Udaipur", "45"},
            };
            String[] columnNames = {
                    "Flight Number", "Flight Name", "Arrival Time", "Departure Time", "Destination", "Available Seats"
            };
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            frames.add(scrollPane, BorderLayout.CENTER);

            closeButton = new JButton("Close");
            closeButton.setBackground(new Color(139, 0, 0));
            closeButton.setForeground(Color.WHITE);
            closeButton.setFont(new Font("Arial", Font.BOLD, 14));
            closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            closeButton.addActionListener(e -> frames.dispose());
            frames.add(closeButton, BorderLayout.SOUTH);

            frames.setVisible(true);
        }
    }
}
