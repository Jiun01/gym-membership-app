import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * The main dashboard for administrators.
 * It provides tools to manage users and support tickets using a tabbed interface.
 */
public class AdminDashboard extends JFrame {

    private DefaultTableModel userTableModel;
    private JTable usersTable;
    private DefaultTableModel ticketTableModel;
    private JTable ticketsTable;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Tabbed Pane for Different Admin Controls ---
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Manage Users Panel ---
        JPanel manageUsersPanel = new JPanel(new BorderLayout());
        String[] userColumns = {"Username", "Role"};
        userTableModel = new DefaultTableModel(userColumns, 0);
        usersTable = new JTable(userTableModel);
        loadUsers(userTableModel);
        manageUsersPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);

        JPanel userButtonsPanel = new JPanel();
        JButton addUserButton = new JButton("Add User");
        JButton editUserButton = new JButton("Edit User");
        JButton deleteUserButton = new JButton("Delete User");
        userButtonsPanel.add(addUserButton);
        userButtonsPanel.add(editUserButton);
        userButtonsPanel.add(deleteUserButton);
        manageUsersPanel.add(userButtonsPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Manage Users", manageUsersPanel);

        // --- Manage Tickets Panel ---
        JPanel manageTicketsPanel = new JPanel(new BorderLayout());
        String[] ticketColumns = {"Email", "Subject", "Description", "Status"};
        ticketTableModel = new DefaultTableModel(ticketColumns, 0);
        ticketsTable = new JTable(ticketTableModel);
        loadTickets(ticketTableModel);
        manageTicketsPanel.add(new JScrollPane(ticketsTable), BorderLayout.CENTER);

        JPanel ticketButtonsPanel = new JPanel();
        JButton updateTicketStatusButton = new JButton("Update Status");
        ticketButtonsPanel.add(updateTicketStatusButton);
        manageTicketsPanel.add(ticketButtonsPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Manage Support Tickets", manageTicketsPanel);

        add(tabbedPane);

        // --- Logout Button ---
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);

        // --- Action Listeners for Buttons ---
        addUserButton.addActionListener(e -> addUser());
        editUserButton.addActionListener(e -> editUser());
        deleteUserButton.addActionListener(e -> deleteUser());
        updateTicketStatusButton.addActionListener(e -> updateTicketStatus());
    }

    private void loadUsers(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        List<String[]> users = DataManager.loadAllUsers();
        for (String[] user : users) {
            // We only show username and role, not the password hash and salt
            if (user.length >= 4) {
                model.addRow(new Object[]{user[0], user[3]});
            }
        }
    }

    private void loadTickets(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        List<String[]> tickets = DataManager.loadAllTickets();
        for (String[] ticket : tickets) {
            model.addRow(ticket);
        }
    }

    private void addUser() {
        // Create a dialog for adding a new user
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField roleField = new JTextField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField,
            "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (DataManager.saveUser(username, password, role)) {
                JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers(userTableModel); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = (String) userTableModel.getValueAt(selectedRow, 0);
        String currentRole = (String) userTableModel.getValueAt(selectedRow, 1);

        String newRole = JOptionPane.showInputDialog(this, "Enter new role for " + username + ":", currentRole);

        if (newRole != null && !newRole.trim().isEmpty() && !newRole.equals(currentRole)) {
            if (DataManager.updateUserRole(username, newRole)) {
                JOptionPane.showMessageDialog(this, "User role updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers(userTableModel); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update user role.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = (String) userTableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + username + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (DataManager.deleteUser(username)) {
                JOptionPane.showMessageDialog(this, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers(userTableModel); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTicketStatus() {
        int selectedRow = ticketsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a ticket to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String email = (String) ticketTableModel.getValueAt(selectedRow, 0);
        String subject = (String) ticketTableModel.getValueAt(selectedRow, 1);
        String description = (String) ticketTableModel.getValueAt(selectedRow, 2);

        String[] statuses = {"Open", "In Progress", "Closed"};
        String newStatus = (String) JOptionPane.showInputDialog(this, "Select new status for the ticket:", 
                "Update Ticket Status", JOptionPane.QUESTION_MESSAGE, null, statuses, statuses[0]);

        if (newStatus != null) {
            if (DataManager.updateTicketStatus(email, subject, description, newStatus)) {
                JOptionPane.showMessageDialog(this, "Ticket status updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTickets(ticketTableModel); // Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update ticket status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}