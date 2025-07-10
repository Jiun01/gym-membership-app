import javax.swing.*;
import java.awt.*;

/**
 * The main dashboard for members after they log in.
 * It uses a tabbed pane to provide access to all member features.
 */
public class MemberDashboard extends JFrame {

    /**
     * Constructor for the MemberDashboard.
     * @param username The username of the logged-in member, used for data persistence.
     */
    public MemberDashboard(String username) {
        setTitle("Member Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Feature Panels ---
        // The username is passed to the panels that need it for saving data.
        tabbedPane.addTab("My Profile", new ProfilePanel(username));
        tabbedPane.addTab("Fitness Tracker", new FitnessTrackerPanel());
        tabbedPane.addTab("My Goals", new GoalsPanel(username));
        tabbedPane.addTab("New Support Ticket", new CreateTicketPanel());

        // --- Chatbot Panel ---
        JPanel chatbotPanel = new JPanel(new BorderLayout(5, 5));
        chatbotPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextArea chatArea = new JTextArea("Chatbot: Hello " + username + "! How can I help you today?");
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        JTextField messageField = new JTextField("Type your message here...");
        JButton sendButton = new JButton("Send");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatbotPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatbotPanel.add(inputPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Chatbot", chatbotPanel);

        // --- Privacy Policy Panel ---
        tabbedPane.addTab("Privacy & Policy", new PrivacyPolicyPanel());

        add(tabbedPane, BorderLayout.CENTER);

        // --- Logout Button ---
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }
}