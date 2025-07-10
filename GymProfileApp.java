import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GymProfileApp {

    public static void main(String[] args) {
        // Initialize the main frame for the application
        JFrame frame = new JFrame("Create Your Profile");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout and panel for the profile form
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255)); // Light background color

        // Create form title
        JLabel titleLabel = new JLabel("Create Your Gym Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(51, 51, 153));  // Dark blue for title
        panel.add(titleLabel);

        // Add profile image next to form fields
        String imagePath = "C:/Users/Laptop House/Desktop/java/logo.jpg";  // Your image path
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(imageLabel);
        }

        // Name input
        JTextField nameField = new JTextField("Enter your name");
        nameField.setMaximumSize(new Dimension(250, 30));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setForeground(new Color(51, 51, 51)); // Text color
        panel.add(Box.createVerticalStrut(10));
        panel.add(nameField);

        // Age input
        JTextField ageField = new JTextField("Enter your age");
        ageField.setMaximumSize(new Dimension(250, 30));
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        ageField.setForeground(new Color(51, 51, 51));
        panel.add(Box.createVerticalStrut(10));
        panel.add(ageField);

        // Gender input
        JTextField genderField = new JTextField("Enter your gender");
        genderField.setMaximumSize(new Dimension(250, 30));
        genderField.setFont(new Font("Arial", Font.PLAIN, 14));
        genderField.setForeground(new Color(51, 51, 51));
        panel.add(Box.createVerticalStrut(10));
        panel.add(genderField);

        // Email input
        JTextField emailField = new JTextField("Enter your email");
        emailField.setMaximumSize(new Dimension(250, 30));
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setForeground(new Color(51, 51, 51));
        panel.add(Box.createVerticalStrut(10));
        panel.add(emailField);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(173, 216, 230)); // Light blue color for button
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);

        // Result panel for showing the congratulations message, profile details, and logo
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(new Color(245, 245, 245)); // Light grey background

        // Congratulations label
        JLabel resultLabel = new JLabel("Congratulations! Your profile is ready.");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(0, 102, 204));  // Dark blue for result
        resultPanel.add(resultLabel);

        // Profile details display
        JTextArea profileDetails = new JTextArea(4, 30);
        profileDetails.setFont(new Font("Arial", Font.PLAIN, 14));
        profileDetails.setEditable(false);
        profileDetails.setBackground(new Color(245, 245, 245));
        profileDetails.setForeground(new Color(51, 51, 51));
        resultPanel.add(profileDetails);

        // Add profile image to result panel as well
        if (imgFile.exists()) {
            ImageIcon resultImageIcon = new ImageIcon(imagePath);
            JLabel resultImageLabel = new JLabel(resultImageIcon);
            resultImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultPanel.add(resultImageLabel);
        }

        // Initially, hide the result panel
        resultPanel.setVisible(false);

        // Add panels to the frame
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);

        // Action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the user input data
                String name = nameField.getText();
                String age = ageField.getText();
                String gender = genderField.getText();
                String email = emailField.getText();

                // Hide the profile creation form
                panel.setVisible(false);

                // Show the result panel with congratulations message, profile details, and image
                resultPanel.setVisible(true);

                // Display profile details in the result panel
                String profileInfo = "Name: " + name + "\n" +
                        "Age: " + age + "\n" +
                        "Gender: " + gender + "\n" +
                        "Email: " + email;
                profileDetails.setText(profileInfo);
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
