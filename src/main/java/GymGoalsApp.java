import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GymGoalsApp {

    public static void main(String[] args) {
        // Initialize the main frame for the application
        JFrame frame = new JFrame("Gym Daily & Weekly Goals");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout and panel for goals screen
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 255)); // Light background color

        // Title of the page
        JLabel titleLabel = new JLabel("Your Gym Goals");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(51, 51, 153));  // Dark blue for title
        panel.add(titleLabel);

        // Logo
        String imagePath = "C:/Users/Laptop House/Desktop/java/logo.jpg";  // Your image path
        ImageIcon logoIcon = new ImageIcon(imagePath);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        // Daily Goals Section
        JPanel dailyGoalsPanel = new JPanel();
        dailyGoalsPanel.setLayout(new GridLayout(5, 1));  // Grid for daily goals
        dailyGoalsPanel.setBackground(new Color(255, 255, 255));

        String[] dailyGoals = {
                "🏋️‍♂️ Workout Duration (30 minutes)",
                "👣 Steps Taken (10,000 steps)",
                "🔥 Calories Burned (400 kcal)",
                "💧 Hydration (2 liters of water)",
                "🍽️ Nutrition (1,800–2,200 kcal)"
        };

        // Array to store user input for daily goals
        JTextField[] dailyGoalInputs = new JTextField[5];

        for (int i = 0; i < dailyGoals.length; i++) {
            JLabel goalLabel = new JLabel(dailyGoals[i]);
            goalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            dailyGoalsPanel.add(goalLabel);

            dailyGoalInputs[i] = new JTextField();
            dailyGoalInputs[i].setFont(new Font("Arial", Font.PLAIN, 14));
            dailyGoalsPanel.add(dailyGoalInputs[i]);
        }

        panel.add(dailyGoalsPanel);

        // Weekly Goals Section
        JPanel weeklyGoalsPanel = new JPanel();
        weeklyGoalsPanel.setLayout(new GridLayout(4, 1));  // Grid for weekly goals
        weeklyGoalsPanel.setBackground(new Color(255, 255, 255));

        String[] weeklyGoals = {
                "🗓️ Consistency (4 workouts)",
                "🔥 Weekly Calories (2,500–3,000 kcal)",
                "✅ Challenge Streak (5 days in a row)",
                "📊 Progress Tracking (Update body weight)"
        };

        // Array to store user input for weekly goals
        JTextField[] weeklyGoalInputs = new JTextField[4];

        for (int i = 0; i < weeklyGoals.length; i++) {
            JLabel goalLabel = new JLabel(weeklyGoals[i]);
            goalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            weeklyGoalsPanel.add(goalLabel);

            weeklyGoalInputs[i] = new JTextField();
            weeklyGoalInputs[i].setFont(new Font("Arial", Font.PLAIN, 14));
            weeklyGoalsPanel.add(weeklyGoalInputs[i]);
        }

        panel.add(weeklyGoalsPanel);

        // Button to submit and display progress
        JButton submitButton = new JButton("Submit Progress");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(173, 216, 230)); // Light blue color for button
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);

        // Result panel for showing the goals completion status
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(new Color(245, 245, 245)); // Light grey background

        JLabel resultLabel = new JLabel("Goals Progress:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(0, 102, 204));  // Dark blue for result
        resultPanel.add(resultLabel);

        JTextArea progressDetails = new JTextArea(8, 30);
        progressDetails.setFont(new Font("Arial", Font.PLAIN, 14));
        progressDetails.setEditable(false);
        progressDetails.setBackground(new Color(245, 245, 245));
        progressDetails.setForeground(new Color(51, 51, 51));
        resultPanel.add(progressDetails);

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
                // Gather daily goals input
                StringBuilder dailyGoalsProgress = new StringBuilder("Daily Goals Progress:\n");
                for (int i = 0; i < dailyGoalInputs.length; i++) {
                    dailyGoalsProgress.append(dailyGoals[i]).append(": ").append(dailyGoalInputs[i].getText()).append("\n");
                }

                // Gather weekly goals input
                StringBuilder weeklyGoalsProgress = new StringBuilder("\nWeekly Goals Progress:\n");
                for (int i = 0; i < weeklyGoalInputs.length; i++) {
                    weeklyGoalsProgress.append(weeklyGoals[i]).append(": ").append(weeklyGoalInputs[i].getText()).append("\n");
                }

                // Show the result panel with goals progress details
                resultPanel.setVisible(true);

                // Display the progress details
                progressDetails.setText(dailyGoalsProgress.toString() + weeklyGoalsProgress.toString());
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
