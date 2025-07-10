import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GymDynamicApp {

    public static void main(String[] args) {
        // Initialize the main frame for the application
        JFrame frame = new JFrame("Gym Fitness Tracker");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout for the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 255)); // Light background color

        // Logo (smaller size)
        String imagePath = "C:/Users/Laptop House/Desktop/java/logo.jpg";  // Your image path
        ImageIcon logoIcon = new ImageIcon(imagePath);
        Image resizedLogo = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Resize logo
        JLabel logoLabel = new JLabel(new ImageIcon(resizedLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("Welcome to Your Gym Fitness Tracker");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(51, 51, 153));  // Dark blue for title
        mainPanel.add(titleLabel);

        // Ask user for their fitness goals
        JLabel goalLabel = new JLabel("What is your goal?");
        goalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        goalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(goalLabel);

        String[] options = {"Lose Weight", "Gain Muscle", "Maintain Weight"};
        JComboBox<String> goalComboBox = new JComboBox<>(options);
        goalComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        goalComboBox.setMaximumSize(new Dimension(200, 30));
        mainPanel.add(goalComboBox);

        // Input for current weight and height
        JTextField weightField = new JTextField("Enter your current weight (kg)");
        weightField.setMaximumSize(new Dimension(250, 30));
        weightField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(weightField);

        JTextField heightField = new JTextField("Enter your height (cm)");
        heightField.setMaximumSize(new Dimension(250, 30));
        heightField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(heightField);

        // Submit button to proceed
        JButton submitButton = new JButton("Submit and Get Recommendations");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(173, 216, 230)); // Light blue color for button
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(submitButton);

        // Result panel for displaying recommendations
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(new Color(245, 245, 245)); // Light grey background

        JLabel resultLabel = new JLabel("Your Personalized Recommendations:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(0, 102, 204));  // Dark blue for result
        resultPanel.add(resultLabel);

        JTextArea recommendationArea = new JTextArea(6, 30);
        recommendationArea.setFont(new Font("Arial", Font.PLAIN, 14));
        recommendationArea.setEditable(false);
        recommendationArea.setBackground(new Color(245, 245, 245));
        recommendationArea.setForeground(new Color(51, 51, 51));
        resultPanel.add(recommendationArea);

        // Initially hide the result panel
        resultPanel.setVisible(false);

        // Add panels to the frame
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);

        // Action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input data
                String goal = (String) goalComboBox.getSelectedItem();
                double weight = Double.parseDouble(weightField.getText().trim());
                double height = Double.parseDouble(heightField.getText().trim());

                // Calculate BMI and give recommendations
                double bmi = weight / (height / 100) / (height / 100);
                StringBuilder recommendations = new StringBuilder();

                recommendations.append("Your BMI is: ").append(String.format("%.2f", bmi)).append("\n");

                // Display customized recommendations based on goal
                if (goal.equals("Lose Weight")) {
                    recommendations.append("Recommended weight loss: 0.5-1 kg per week.\n")
                            .append("Suggested duration: 3-6 months.\n")
                            .append("Diet plan: Focus on a calorie deficit with 1,500-1,800 kcal/day.\n")
                            .append("Exercise: 4-5 days per week with cardio and strength training.\n");
                } else if (goal.equals("Gain Muscle")) {
                    recommendations.append("Recommended muscle gain: 0.2-0.5 kg per month.\n")
                            .append("Suggested duration: 6-12 months.\n")
                            .append("Diet plan: Focus on high-protein intake (2,000-2,500 kcal/day).\n")
                            .append("Exercise: Focus on strength training with 3-4 days per week.\n");
                } else if (goal.equals("Maintain Weight")) {
                    recommendations.append("Maintain your current weight by staying within your calorie maintenance.\n")
                            .append("Diet plan: 2,000-2,200 kcal/day.\n")
                            .append("Exercise: 3-4 days per week with moderate activity.\n");
                }

                // Show the result panel with recommendations
                resultPanel.setVisible(true);

                // Display recommendations in the text area
                recommendationArea.setText(recommendations.toString());
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
