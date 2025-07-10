import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * The main dashboard for administrators.
 * It provides tools to manage users and support tickets using a tabbed interface.
 */
public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Tabbed Pane for Different Admin Controls ---
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- View Users Panel ---
        JPanel viewUsersPanel = new JPanel(new BorderLayout());
        String[] userColumns = {"Username", "Password (Hidden)", "Role"};
        DefaultTableModel userTableModel = new DefaultTableModel(userColumns, 0);
        JTable usersTable = new JTable(userTableModel);
        loadUsers(userTableModel);
        viewUsersPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        tabbedPane.addTab("Manage Users", viewUsersPanel);

        // --- View Tickets Panel ---
        JPanel viewTicketsPanel = new JPanel(new BorderLayout());
        String[] ticketColumns = {"Email", "Subject", "Description", "Status"};
        DefaultTableModel ticketTableModel = new DefaultTableModel(ticketColumns, 0);
        JTable ticketsTable = new JTable(ticketTableModel);
        loadTickets(ticketTableModel);
        viewTicketsPanel.add(new JScrollPane(ticketsTable), BorderLayout.CENTER);
        tabbedPane.addTab("Manage Support Tickets", viewTicketsPanel);

        add(tabbedPane);

        // --- Logout Button ---
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginScreen().setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }

    /**
     * Loads user data from the DataManager and populates the user table.
     * @param model The table model to populate.
     */
    private void loadUsers(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        List<String[]> users = DataManager.loadAllUsers();
        for (String[] user : users) {
            if (user.length == 3) {
                // For security, do not display the actual password
                model.addRow(new Object[]{user[0], "********", user[2]});
            }
        }
    }

    /**
     * Loads ticket data from the DataManager and populates the ticket table.
     * @param model The table model to populate.
     */
    private void loadTickets(DefaultTableModel model) {
        model.setRowCount(0); // Clear existing data
        List<String[]> tickets = DataManager.loadAllTickets();
        for (String[] ticket : tickets) {
            model.addRow(ticket);
        }
    }
}