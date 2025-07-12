import javax.swing.*;
import java.awt.*;

public class MemberDashboard extends JFrame {

    public MemberDashboard(String username) {
        setTitle("Member Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Feature Panels
        // The username is passed to the panels that need it for saving data.
        tabbedPane.addTab("My Profile", new ProfilePanel(username));
        tabbedPane.addTab("Fitness Tracker", new FitnessTrackerPanel());
        tabbedPane.addTab("My Goals", new GoalsPanel(username));
        tabbedPane.addTab("New Support Ticket", new CreateTicketPanel());

        // Chatbot Panel
        tabbedPane.addTab("Chatbot", new Chatbot());

        // Privacy Policy Panel
        tabbedPane.addTab("Privacy & Policy", new PrivacyPolicyPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}