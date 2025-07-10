import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

/**
 * A utility class to handle all data persistence operations.
 * Manages reading/writing to CSV files for users, tickets, and member data.
 * Implements password hashing with a salt.
 */
public class DataManager {

    private static final String USERS_FILE = "users.csv";
    private static final String TICKETS_FILE = "tickets.csv";
    private static final String PROFILES_FILE = "profiles.csv"; // For member profiles
    private static final String GOALS_FILE = "goals.csv";       // For member goals

    // --- Password Hashing Methods ---

    /**
     * Generates a cryptographically secure salt.
     * @return A salt as a Base64 encoded string.
     */
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password with a given salt using SHA-256.
     * @param password The password to hash.
     * @param salt The salt to use (Base64 encoded).
     * @return The hashed password as a Base64 encoded string.
     */
    private static String hashPassword(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            generatedPassword = Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // --- User Management ---

    /**
     * Authenticates a user by checking the provided credentials against the users file.
     * @param username The username to check.
     * @param password The password to check.
     * @return The user's role if authentication is successful, otherwise null.
     */
    public static String authenticateUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4 && values[0].equals(username)) {
                    String salt = values[1];
                    String storedHash = values[2];
                    String hashedPassword = hashPassword(password, salt);
                    if (hashedPassword != null && hashedPassword.equals(storedHash)) {
                        return values[3]; // Return the role
                    }
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return null; // Authentication failed
    }

    /**
     * Saves a new user to the users file with a salted and hashed password.
     * @param username The new user's username.
     * @param password The new user's password.
     * @param role The new user's role.
     * @return true if the user was saved successfully, false otherwise.
     */
    public static boolean saveUser(String username, String password, String role) {
        if (isUserExists(username)) {
            return false;
        }
        try {
            String salt = getSalt();
            String hashedPassword = hashPassword(password, salt);
            if (hashedPassword == null) return false;

            try (FileWriter fw = new FileWriter(USERS_FILE, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(String.join(",", username, salt, hashedPassword, role));
                return true;
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Member Data Persistence ---

    /**
     * Saves a member's profile data. Appends to profiles.csv.
     * @param username The user's username.
     * @param name The user's full name.
     * @param age The user's age.
     * @param gender The user's gender.
     */
    public static void saveProfile(String username, String name, String age, String gender) {
        try (FileWriter fw = new FileWriter(PROFILES_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(String.join(",", username, name, age, gender));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a member's goals data. Appends to goals.csv.
     * @param username The user's username.
     * @param dailyGoals An array of daily goal inputs.
     * @param weeklyGoals An array of weekly goal inputs.
     */
    public static void saveGoals(String username, String[] dailyGoals, String[] weeklyGoals) {
        try (FileWriter fw = new FileWriter(GOALS_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String daily = String.join(";", dailyGoals);
            String weekly = String.join(";", weeklyGoals);
            pw.println(String.join(",", username, daily, weekly));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Other Methods (isUserExists, saveTicket, loadAllUsers, loadAllTickets) ---
    // (These methods remain largely the same, but loadAllUsers needs to account for the new format)

    private static boolean isUserExists(String username) {
        // Implementation unchanged, but good practice to check
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File not found is okay
        }
        return false;
    }

    public static void saveTicket(String email, String subject, String description) {
        try (FileWriter fw = new FileWriter(TICKETS_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            String sanitizedDesc = description.replace(",", ";").replace("\n", " | ");
            pw.println(String.join(",", email, subject, sanitizedDesc, "Open"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> loadAllUsers() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(line.split(","));
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) e.printStackTrace();
        }
        return users;
    }

    public static List<String[]> loadAllTickets() {
        // Implementation is unchanged
        List<String[]> tickets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TICKETS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                tickets.add(line.split(","));
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) e.printStackTrace();
        }
        return tickets;
    }
}