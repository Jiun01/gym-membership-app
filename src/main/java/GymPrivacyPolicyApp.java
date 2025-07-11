import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GymPrivacyPolicyApp {

    public static void main(String[] args) {
        // Initialize the main frame for the application
        JFrame frame = new JFrame("Privacy & Policy");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout and panel for content display
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 255));

        // Create text area for policy content
        JTextArea policyText = new JTextArea();
        policyText.setFont(new Font("Arial", Font.PLAIN, 14));
        policyText.setText("Privacy & Policy\n\nWelcome to our Gym Membership & Service App.\nWe are committed to protecting your personal information and your right to privacy.\nThis Privacy Policy explains how we collect, use, store, and protect your data...\n\n");
        policyText.setWrapStyleWord(true);
        policyText.setLineWrap(true);
        policyText.setEditable(false);
        policyText.setBackground(new Color(240, 240, 255));

        // Scrollable area for the content
        JScrollPane scrollPane = new JScrollPane(policyText);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create buttons for different sections of the privacy policy
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setBackground(new Color(240, 240, 255));

        JButton infoButton = new JButton("What Information We Collect");
        infoButton.setBackground(new Color(173, 216, 230)); // Light blue color
        infoButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton useButton = new JButton("How We Use Your Information");
        useButton.setBackground(new Color(173, 216, 230));
        useButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton securityButton = new JButton("Data Storage & Security");
        securityButton.setBackground(new Color(173, 216, 230));
        securityButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(infoButton);
        buttonPanel.add(useButton);
        buttonPanel.add(securityButton);

        panel.add(buttonPanel, BorderLayout.WEST);

        // Add panel to the frame
        frame.setLayout(new BorderLayout());
        frame.add(panel);

        // Implement button functionality
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                policyText.setText("1. What Information We Collect:\n\n" +
                        "We may collect the following types of personal data:\n" +
                        "- Full name and contact details (email, phone number)\n" +
                        "- Date of birth, gender, height, weight (for profile and workout goals)\n" +
                        "- Login credentials (securely stored in hashed-salted format)\n" +
                        "- Membership history, subscription status\n" +
                        "- App activity logs: login, session duration logs\n" +
                        "- Workout logs (time, frequency)\n" +
                        "- Activity logs: steps, workouts, calorie calculations, goal tracking\n");
            }
        });

        useButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                policyText.setText("2. How We Use Your Information:\n\n" +
                        "Your data is used to:\n" +
                        "- Manage your membership and account profile\n" +
                        "- Provide customized responses and goal tracking\n" +
                        "- Track your fitness performance and generate insights\n" +
                        "- Provide recommendations (e.g. workout suggestions)\n" +
                        "- Improve app performance and features\n");
            }
        });

        securityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                policyText.setText("3. Data Storage & Security:\n\n" +
                        "All user data is stored locally on your device with secure file storage services.\n" +
                        "Sensitive information (like login credentials) is encrypted using industry-standard methods (e.g. SHA-256).\n" +
                        "We also use multi-factor authentication where available to handle data security and unauthorized access.\n");
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
