import javax.swing.SwingUtilities;

/**
 * The main entry point for the Gym Membership and Service Application.
 * This class is responsible for starting the application by showing the login screen.
 */
public class MainApp {

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure the GUI is created on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            // Create and display the login screen.
            new LoginScreen().setVisible(true);
        });
    }
}
