import javax.swing.*;
import java.awt.*;

/**
 * The login window for the application.
 * It allows users to enter their credentials and authenticates them.
 * Based on the user's role, it opens the appropriate dashboard.
 */
public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("Gym Application Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // --- UI Components ---
        JLabel titleLabel = new JLabel("Member & Staff Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register New Account");

        // --- Layout ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(userLabel, gbc);

        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passLabel, gbc);

        gbc.gridx = 1;
        add(passField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // --- Action Listeners ---
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            authenticateUser(username, password);
        });

        registerButton.addActionListener(e -> {
            new RegistrationScreen().setVisible(true);
        });
    }

    /**
     * Authenticates the user and opens the corresponding dashboard.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    private void authenticateUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String role = DataManager.authenticateUser(username, password);

        if (role != null) {
            // Successful login
            if ("Admin".equals(role)) {
                new AdminDashboard().setVisible(true);
            } else {
                // CORRECTED LINE: Pass the username to the MemberDashboard constructor
                new MemberDashboard(username).setVisible(true);
            }
            dispose(); // Close the login screen
        } else {
            // Failed login
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}