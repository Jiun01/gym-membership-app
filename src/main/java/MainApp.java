import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure the GUI is created on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            // Create and display the login screen.
            new LoginScreen().setVisible(true);
        });
    }
}
