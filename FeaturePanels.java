import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// --- ProfilePanel.java (from GymProfileApp) ---
class ProfilePanel extends JPanel {
    public ProfilePanel(String username) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createTitledBorder("Your Gym Profile"));
        setBackground(new Color(240, 240, 255));

        // Load profile data
        String[] profileData = DataManager.loadProfile(username);

        // Create JLabels to display profile information
        JLabel nameLabel = new JLabel("Name: ");
        JLabel ageLabel = new JLabel("Age: ");
        JLabel genderLabel = new JLabel("Gender: ");

        if (profileData != null) {
            nameLabel.setText("Name: " + profileData[1]);
            ageLabel.setText("Age: " + profileData[2]);
            genderLabel.setText("Gender: " + profileData[3]);
        } else {
            nameLabel.setText("Name: Not set");
            ageLabel.setText("Age: Not set");
            genderLabel.setText("Gender: Not set");
        }

        // Add labels to the panel
        add(nameLabel);
        add(ageLabel);
        add(genderLabel);
        add(Box.createVerticalGlue()); // Pushes content to the top
    }
}


// --- GoalsPanel.java (from GymGoalsApp) ---
class GoalsPanel extends JPanel {
    public GoalsPanel(String username) { // Username is now passed to the constructor
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Daily & Weekly Goals"));
        setBackground(new Color(240, 240, 255));

        // --- Data Structures ---
        JTextField[] dailyGoalInputs = new JTextField[3];
        JTextField[] weeklyGoalInputs = new JTextField[2];

        // --- UI Panels ---
        JPanel dailyPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        dailyPanel.setBorder(BorderFactory.createTitledBorder("Daily Goals"));
        dailyPanel.setBackground(Color.WHITE);

        JPanel weeklyPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        weeklyPanel.setBorder(BorderFactory.createTitledBorder("Weekly Goals"));
        weeklyPanel.setBackground(Color.WHITE);

        // --- Daily Goals Fields ---
        dailyGoalInputs[0] = new JTextField(10);
        dailyGoalInputs[1] = new JTextField(10);
        dailyGoalInputs[2] = new JTextField(10);

        dailyPanel.add(new JLabel("Workout Duration (30 mins):"));
        dailyPanel.add(dailyGoalInputs[0]);
        dailyPanel.add(new JLabel("Steps Taken (10,000):"));
        dailyPanel.add(dailyGoalInputs[1]);
        dailyPanel.add(new JLabel("Calories Burned (400 kcal):"));
        dailyPanel.add(dailyGoalInputs[2]);

        // --- Weekly Goals Fields ---
        weeklyGoalInputs[0] = new JTextField(10);
        weeklyGoalInputs[1] = new JTextField(10);

        weeklyPanel.add(new JLabel("Consistency (4 workouts):"));
        weeklyPanel.add(weeklyGoalInputs[0]);
        weeklyPanel.add(new JLabel("Weekly Calories (2,500 kcal):"));
        weeklyPanel.add(weeklyGoalInputs[1]);

        add(dailyPanel);
        add(weeklyPanel);

        // --- Save Button ---
        JButton saveButton = new JButton("Save Progress");
        add(saveButton);

        saveButton.addActionListener(e -> {
            String[] dailyData = {
                dailyGoalInputs[0].getText(),
                dailyGoalInputs[1].getText(),
                dailyGoalInputs[2].getText()
            };
            String[] weeklyData = {
                weeklyGoalInputs[0].getText(),
                weeklyGoalInputs[1].getText()
            };
            DataManager.saveGoals(username, dailyData, weeklyData);
            JOptionPane.showMessageDialog(this, "Goals saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}

// --- FitnessTrackerPanel.java (from FitnessTrackerApp) ---
class FitnessTrackerPanel extends JPanel {
    public FitnessTrackerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Fitness Tracker & Calculator"));
        setBackground(new Color(240, 240, 255));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        add(new JLabel("What is your goal?"));
        JComboBox<String> goalComboBox = new JComboBox<>(new String[]{"Lose Weight", "Gain Muscle", "Maintain Weight"});
        goalComboBox.setMaximumSize(new Dimension(200, 30));
        add(goalComboBox);

        add(Box.createVerticalStrut(10));
        add(new JLabel("Current Weight (kg):"));
        JTextField weightField = new JTextField(10);
        weightField.setMaximumSize(new Dimension(200, 30));
        add(weightField);

        add(Box.createVerticalStrut(10));
        add(new JLabel("Height (cm):"));
        JTextField heightField = new JTextField(10);
        heightField.setMaximumSize(new Dimension(200, 30));
        add(heightField);

        add(Box.createVerticalStrut(20));
        JButton submitButton = new JButton("Submit and Get Recommendations");
        add(submitButton);

        JTextArea recommendationArea = new JTextArea(6, 30);
        recommendationArea.setEditable(false);
        add(new JScrollPane(recommendationArea));

        submitButton.addActionListener(e -> {
            String weightText = weightField.getText();
            String heightText = heightField.getText();
            try {
                Double.parseDouble(weightText);
                Double.parseDouble(heightText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for weight and height.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String goal = (String) goalComboBox.getSelectedItem();
            StringBuilder recommendations = new StringBuilder();
            if ("Lose Weight".equals(goal)) {
                recommendations.append("Recommended: 0.5-1 kg loss per week.\n");
                recommendations.append("Diet: Calorie deficit (1,500-1,800 kcal/day).\n");
            } else if ("Gain Muscle".equals(goal)) {
                recommendations.append("Recommended: 0.2-0.5 kg gain per month.\n");
                recommendations.append("Diet: High-protein (2,000-2,500 kcal/day).\n");
            } else {
                recommendations.append("Maintain current weight.\n");
                recommendations.append("Diet: 2,000-2,200 kcal/day).\n");
            }
            recommendationArea.setText(recommendations.toString());
        });
    }
}

// --- CreateTicketPanel.java ---
class CreateTicketPanel extends JPanel {
    public CreateTicketPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Create New Support Ticket"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Form Fields
        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Your Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; JTextField emailField = new JTextField(30); add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; JComboBox<String> subjectBox = new JComboBox<>(new String[]{"General Inquiry", "Billing Issue", "Technical Support"}); add(subjectBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; JTextArea descArea = new JTextArea(5, 30); add(new JScrollPane(descArea), gbc);

        gbc.gridx = 1; gbc.gridy = 3; JCheckBox termsCheck = new JCheckBox("I accept the terms."); add(termsCheck, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        JButton submitButton = new JButton("Submit Ticket");
        add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            if (emailField.getText().isEmpty() || descArea.getText().isEmpty() || !termsCheck.isSelected()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields and accept the terms.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataManager.saveTicket(emailField.getText(), (String) subjectBox.getSelectedItem(), descArea.getText());
            JOptionPane.showMessageDialog(this, "Ticket submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Clear fields
            emailField.setText("");
            descArea.setText("");
            termsCheck.setSelected(false);
        });
    }
}

// --- PrivacyPolicyPanel.java (from GymPrivacyPolicyApp) ---
class PrivacyPolicyPanel extends JPanel {
    public PrivacyPolicyPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Privacy & Policy"));
        JTextArea policyText = new JTextArea(loadPrivacyPolicy());
        policyText.setWrapStyleWord(true);
        policyText.setLineWrap(true);
        policyText.setEditable(false);
        policyText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(policyText), BorderLayout.CENTER);
    }

    private String loadPrivacyPolicy() {
        StringBuilder policy = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("privacy_policy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                policy.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to load privacy policy.";
        }
        return policy.toString();
    }
}