import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

        JButton fillProfileButton = new JButton("Fill/Edit Profile");
        fillProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(fillProfileButton);

        fillProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // This will be handled by the parent container (MemberDashboard) or a CardLayout within this panel
                // For now, we'll just show a message or switch to an input panel
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ProfilePanel.this);
                JTabbedPane tabbedPane = (JTabbedPane) ((MemberDashboard) topFrame).getContentPane().getComponent(0);
                tabbedPane.addTab("Edit Profile", new ProfileInputPanel(username));
                tabbedPane.setSelectedComponent(tabbedPane.getComponentAt(tabbedPane.getTabCount() - 1));
            }
        });
    }
}

class ProfileInputPanel extends JPanel {
    public ProfileInputPanel(String username) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBorder(BorderFactory.createTitledBorder("Create/Edit Your Gym Profile"));
        setBackground(new Color(240, 240, 255));

        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField genderField = new JTextField(20);
        JButton saveButton = new JButton("Save Profile");

        // Load existing profile data if available
        String[] existingProfile = DataManager.loadProfile(username);
        if (existingProfile != null) {
            nameField.setText(existingProfile[1]);
            ageField.setText(existingProfile[2]);
            genderField.setText(existingProfile[3]);
        }

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Gender:"));
        add(genderField);
        add(Box.createVerticalStrut(10));
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ageText = ageField.getText();
                try {
                    Integer.parseInt(ageText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ProfileInputPanel.this, "Please enter a valid number for age.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DataManager.saveProfile(
                        username,
                        nameField.getText(),
                        ageText,
                        genderField.getText()
                );
                JOptionPane.showMessageDialog(ProfileInputPanel.this, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // After saving, switch back to the display panel and remove this input panel
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ProfileInputPanel.this);
                JTabbedPane tabbedPane = (JTabbedPane) ((MemberDashboard) topFrame).getContentPane().getComponent(0);
                int tabIndex = tabbedPane.indexOfComponent(ProfileInputPanel.this);
                if (tabIndex != -1) {
                    tabbedPane.removeTabAt(tabIndex);
                }
                // Refresh the ProfilePanel to show updated data
                int profileTabIndex = tabbedPane.indexOfTab("My Profile");
                if (profileTabIndex != -1) {
                    tabbedPane.removeTabAt(profileTabIndex);
                    tabbedPane.insertTab("My Profile", null, new ProfilePanel(username), null, profileTabIndex);
                    tabbedPane.setSelectedComponent(tabbedPane.getComponentAt(profileTabIndex));
                }
            }
        });
    }
}


// --- GoalsPanel.java (from GymGoalsApp) ---
class GoalsPanel extends JPanel {
    public GoalsPanel(String username) { // Username is now passed to the constructor
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Daily & Weekly Goals"));
        setBackground(new Color(240, 240, 255));

        // --- Data Structures ---
        JTextField[] dailyGoalTexts = new JTextField[5];
        JCheckBox[] dailyGoalChecks = new JCheckBox[5];
        JTextField[] weeklyGoalTexts = new JTextField[4];
        JCheckBox[] weeklyGoalChecks = new JCheckBox[4];

        // Load existing goals
        String[][] existingGoals = DataManager.loadGoals(username);
        String[] existingDailyData = (existingGoals != null && existingGoals.length > 0) ? existingGoals[0] : new String[5];
        String[] existingWeeklyData = (existingGoals != null && existingGoals.length > 1) ? existingGoals[1] : new String[4];

        // --- UI Panels ---
        JPanel dailyPanel = new JPanel(new GridLayout(0, 1, 10, 5)); // Changed to 1 column for better layout
        dailyPanel.setBorder(BorderFactory.createTitledBorder("Daily Goals"));
        dailyPanel.setBackground(Color.WHITE);

        JPanel weeklyPanel = new JPanel(new GridLayout(0, 1, 10, 5)); // Changed to 1 column
        weeklyPanel.setBorder(BorderFactory.createTitledBorder("Weekly Goals"));
        weeklyPanel.setBackground(Color.WHITE);

        // Helper to create a goal entry panel
        class GoalEntryPanel extends JPanel {
            JTextField textField;
            JCheckBox checkBox;

            public GoalEntryPanel(String label, String initialText, boolean initialCheck) {
                super(new FlowLayout(FlowLayout.LEFT));
                textField = new JTextField(initialText, 20);
                checkBox = new JCheckBox("Completed", initialCheck);
                add(new JLabel(label));
                add(textField);
                add(checkBox);
            }
        }

        // --- Daily Goals Fields ---
        String[] dailyLabels = {"Workout Duration (30 mins):", "Steps Taken (10,000):", "Calories Burned (400 kcal):", "Hydration (2 liters):", "Nutrition (1,800-2,200 kcal):"};
        for (int i = 0; i < dailyGoalTexts.length; i++) {
            String[] parts = existingDailyData[i].split("~", 2); // Split text and boolean
            String text = parts[0];
            boolean checked = parts.length > 1 ? Boolean.parseBoolean(parts[1]) : false;
            
            GoalEntryPanel panel = new GoalEntryPanel(dailyLabels[i], text, checked);
            dailyGoalTexts[i] = panel.textField;
            dailyGoalChecks[i] = panel.checkBox;
            dailyPanel.add(panel);
        }

        // --- Weekly Goals Fields ---
        String[] weeklyLabels = {"Consistency (4 workouts):", "Weekly Calories (2,500-3,000 kcal):", "Challenge Streak (5 days):", "Progress Tracking (Update weight/measurements):"};
        for (int i = 0; i < weeklyGoalTexts.length; i++) {
            String[] parts = existingWeeklyData[i].split("~", 2); // Split text and boolean
            String text = parts[0];
            boolean checked = parts.length > 1 ? Boolean.parseBoolean(parts[1]) : false;

            GoalEntryPanel panel = new GoalEntryPanel(weeklyLabels[i], text, checked);
            weeklyGoalTexts[i] = panel.textField;
            weeklyGoalChecks[i] = panel.checkBox;
            weeklyPanel.add(panel);
        }

        add(dailyPanel);
        add(weeklyPanel);

        // --- Save Button ---
        JButton saveButton = new JButton("Save Progress");
        add(saveButton);

        saveButton.addActionListener(e -> {
            String[] dailyData = new String[dailyGoalTexts.length];
            for (int i = 0; i < dailyGoalTexts.length; i++) {
                dailyData[i] = dailyGoalTexts[i].getText() + "~" + dailyGoalChecks[i].isSelected();
            }
            String[] weeklyData = new String[weeklyGoalTexts.length];
            for (int i = 0; i < weeklyGoalTexts.length; i++) {
                weeklyData[i] = weeklyGoalTexts[i].getText() + "~" + weeklyGoalChecks[i].isSelected();
            }
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

    public static String loadPrivacyPolicy() {
        StringBuilder policy = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(PrivacyPolicyPanel.class.getResourceAsStream("/privacy_policy.txt")))) {
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