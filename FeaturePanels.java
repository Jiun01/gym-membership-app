import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// --- ProfilePanel.java (from GymProfileApp) ---
class ProfilePanel extends JPanel {
    public ProfilePanel(String username) { // Username is now passed to the constructor
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createTitledBorder("Create Your Gym Profile"));
        setBackground(new Color(240, 240, 255));

        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField genderField = new JTextField(20);
        JButton saveButton = new JButton("Save Profile");

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Gender:"));
        add(genderField);
        add(Box.createVerticalStrut(10));
        add(saveButton);

        saveButton.addActionListener(e -> {
            DataManager.saveProfile(
                    username,
                    nameField.getText(),
                    ageField.getText(),
                    genderField.getText()
            );
            JOptionPane.showMessageDialog(this, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}


// --- GoalsPanel.java (from GymGoalsApp) ---
class GoalsPanel extends JPanel {
    public GoalsPanel(String username) { // Username is now passed to the constructor
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Daily & Weekly Goals"));
        setBackground(new Color(240, 240, 255));

        // Daily Goals
        JPanel dailyPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        dailyPanel.setBorder(BorderFactory.createTitledBorder("Daily Goals"));
        dailyPanel.setBackground(Color.WHITE);
        dailyPanel.add(new JLabel("Workout Duration (30 mins):"));
        dailyPanel.add(new JTextField(10));
        dailyPanel.add(new JLabel("Steps Taken (10,000):"));
        dailyPanel.add(new JTextField(10));
        dailyPanel.add(new JLabel("Calories Burned (400 kcal):"));
        dailyPanel.add(new JTextField(10));
        add(dailyPanel);

        // Weekly Goals
        JPanel weeklyPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        weeklyPanel.setBorder(BorderFactory.createTitledBorder("Weekly Goals"));
        weeklyPanel.setBackground(Color.WHITE);
        weeklyPanel.add(new JLabel("Consistency (4 workouts):"));
        weeklyPanel.add(new JTextField(10));
        weeklyPanel.add(new JLabel("Weekly Calories (2,500 kcal):"));
        weeklyPanel.add(new JTextField(10));
        add(weeklyPanel);

        JTextField[] dailyGoalInputs = new JTextField[3];
        // ... (code to create daily goal fields)

        JTextField[] weeklyGoalInputs = new JTextField[2];
        // ... (code to create weekly goal fields)

        JButton saveButton = new JButton("Save Progress");
        add(saveButton);

        saveButton.addActionListener(e -> {
            String[] dailyData = { dailyGoalInputs[0].getText(), dailyGoalInputs[1].getText(), dailyGoalInputs[2].getText() };
            String[] weeklyData = { weeklyGoalInputs[0].getText(), weeklyGoalInputs[1].getText() };
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
                recommendations.append("Diet: 2,000-2,200 kcal/day.\n");
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
        JTextArea policyText = new JTextArea(
                "Privacy & Policy\n\n" +
                        "Welcome to our Gym Membership & Service App.\n" +
                        "We are committed to protecting your personal information and your right to privacy.\n\n" +
                        "1. What Information We Collect:\n" +
                        "- Full name, contact details, login credentials, and workout logs.\n\n" +
                        "2. How We Use Your Information:\n" +
                        "- To manage your membership, track fitness performance, and improve our services.\n\n" +
                        "3. Data Storage & Security:\n" +
                        "- All user data is stored locally on your device in CSV files."
        );
        policyText.setWrapStyleWord(true);
        policyText.setLineWrap(true);
        policyText.setEditable(false);
        policyText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(policyText), BorderLayout.CENTER);
    }
}