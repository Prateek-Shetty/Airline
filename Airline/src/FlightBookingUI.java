import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class FlightBookingUI extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultTableModel flightsModel;
    private JTable flightsTable;
    private JTextField departureField;
    private JTextField arrivalField;
    private JTextArea confirmationArea;
    private JButton datePickerButton;
    private JComboBox<String> timeComboBox;
    private Calendar selectedDate = Calendar.getInstance();

    public FlightBookingUI() {
        setTitle("Flight Booking System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //test
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
        tabbedPane.setOpaque(false); // Ensure the tabbed pane is transparent
        tabbedPane.setBackground(new Color(255, 255, 255, 200)); // Slight transparency to content
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
        bookingPanel.setOpaque(false); // Transparent background for this panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Departure Field
        JLabel departureLabel = new JLabel("Departure City:");
        departureField = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0;
        bookingPanel.add(departureLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        bookingPanel.add(departureField, gbc);

        // Arrival Field
        JLabel arrivalLabel = new JLabel("Arrival City:");
        arrivalField = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 1;
        bookingPanel.add(arrivalLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        bookingPanel.add(arrivalField, gbc);

        // Date Picker
        JLabel dateLabel = new JLabel("Select Date:");
        datePickerButton = createRoundedButton("Pick Date");
        gbc.gridx = 0; gbc.gridy = 2;
        bookingPanel.add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        bookingPanel.add(datePickerButton, gbc);
        datePickerButton.addActionListener(e -> showDatePicker());

        // Time ComboBox
        JLabel timeLabel = new JLabel("Select Time:");
        timeComboBox = new JComboBox<>(new String[]{"Select Time", "09:00 AM", "12:00 PM", "03:00 PM"});
        customizeComboBox(timeComboBox);
        gbc.gridx = 0; gbc.gridy = 3;
        bookingPanel.add(timeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        bookingPanel.add(timeComboBox, gbc);

        // Search Flights Button
        JButton searchFlightsButton = createRoundedButton("Search Flights");
        searchFlightsButton.addActionListener(e -> searchFlights());
        gbc.gridx = 1; gbc.gridy = 4;
        bookingPanel.add(searchFlightsButton, gbc);

        // Flights Table
        flightsModel = new DefaultTableModel(new String[]{"Flight Number", "Departure", "Arrival", "Price"}, 0);
        flightsTable = new JTable(flightsModel);
        flightsTable.setFillsViewportHeight(true);
        flightsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        flightsTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(flightsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        bookingPanel.add(scrollPane, gbc);

        // Book Flight Button
        JButton bookFlightButton = createRoundedButton("Book Flight");
        bookFlightButton.addActionListener(e -> bookFlight());
        gbc.gridy = 6;
        bookingPanel.add(bookFlightButton, gbc);

        return bookingPanel;
    }

    private JPanel createConfirmationPanel() {
        JPanel confirmationPanel = new JPanel(new BorderLayout());
        confirmationPanel.setBackground(new Color(255, 255, 255, 200)); // Slightly transparent

        confirmationArea = new JTextArea();
        confirmationArea.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmationArea.setEditable(false);
        confirmationArea.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(confirmationArea);
        confirmationPanel.add(scrollPane, BorderLayout.CENTER);

        return confirmationPanel;
    }

    private void customizeComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setForeground(new Color(50, 50, 50));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        comboBox.setBackground(new Color(255, 255, 255));
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243));
            }
        });

        return button;
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

    private void searchFlights() {
        // Dummy data for flights
        flightsModel.setRowCount(0); // Clear previous data
        flightsModel.addRow(new Object[]{"FL123", departureField.getText(), arrivalField.getText(), "$200"});
        flightsModel.addRow(new Object[]{"FL456", departureField.getText(), arrivalField.getText(), "$250"});
        flightsModel.addRow(new Object[]{"FL789", departureField.getText(), arrivalField.getText(), "$300"});
    }

    private void bookFlight() {
        int selectedRow = flightsTable.getSelectedRow();
        if (selectedRow != -1) {
            String flightNumber = flightsModel.getValueAt(selectedRow, 0).toString();
            String departure = flightsModel.getValueAt(selectedRow, 1).toString();
            String arrival = flightsModel.getValueAt(selectedRow, 2).toString();
            String price = flightsModel.getValueAt(selectedRow, 3).toString();
            confirmationArea.setText("Flight Number: " + flightNumber + "\nDeparture: " + departure + "\nArrival: " + arrival + "\nPrice: " + price);
            tabbedPane.setSelectedIndex(1); // Switch to Confirmation tab
        } else {
            JOptionPane.showMessageDialog(this, "Please select a flight to book.", "No Flight Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new FlightBookingUI();
    }
}
