import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;


public class DataManager {

    private static final String USERS_FILE = "users.csv";
    private static final String TICKETS_FILE = "tickets.csv";
    private static final String PROFILES_FILE = "profiles.csv"; // For member profiles
    private static final String GOALS_FILE = "goals.csv";       // For member goals

    // --- Password Hashing Methods ---
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


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

    public static boolean updateUserRole(String username, String newRole) {
        List<String[]> users = loadAllUsers();
        boolean userFound = false;
        for (String[] user : users) {
            if (user.length > 0 && user[0].equals(username)) {
                user[3] = newRole; // Update the role
                userFound = true;
                break;
            }
        }

        if (userFound) {
            try (FileWriter fw = new FileWriter(USERS_FILE, false); // Overwrite the file
                 PrintWriter pw = new PrintWriter(fw)) {
                for (String[] user : users) {
                    pw.println(String.join(",", user));
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false; // User not found
        }
    }

    public static boolean deleteUser(String username) {
        List<String[]> users = loadAllUsers();
        List<String[]> updatedUsers = new ArrayList<>();
        boolean userFound = false;
        for (String[] user : users) {
            if (user.length > 0 && user[0].equals(username)) {
                userFound = true;
            } else {
                updatedUsers.add(user);
            }
        }

        if (userFound) {
            try (FileWriter fw = new FileWriter(USERS_FILE, false); // Overwrite the file
                 PrintWriter pw = new PrintWriter(fw)) {
                for (String[] user : updatedUsers) {
                    pw.println(String.join(",", user));
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false; // User not found
        }
    }

    public static boolean updateTicketStatus(String email, String subject, String description, String newStatus) {
        List<String[]> tickets = loadAllTickets();
        boolean ticketFound = false;
        for (String[] ticket : tickets) {
            if (ticket.length >= 4 && ticket[0].equals(email) && ticket[1].equals(subject) && ticket[2].equals(description)) {
                ticket[3] = newStatus; // Update the status
                ticketFound = true;
                break;
            }
        }

        if (ticketFound) {
            try (FileWriter fw = new FileWriter(TICKETS_FILE, false); // Overwrite the file
                 PrintWriter pw = new PrintWriter(fw)) {
                for (String[] ticket : tickets) {
                    pw.println(String.join(",", ticket));
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false; // Ticket not found
        }
    }

    // --- Member Data Persistence ---

    public static void saveProfile(String username, String name, String age, String gender) {
        List<String[]> allProfiles = new ArrayList<>();
        boolean profileFound = false;

        // Read all existing profiles, excluding the one for the current username
        try (BufferedReader br = new BufferedReader(new FileReader(PROFILES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(username)) {
                    profileFound = true; // Mark that we found the profile
                } else {
                    allProfiles.add(values);
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }

        // Add the new (or updated) profile
        allProfiles.add(new String[]{username, name, age, gender});

        // Write all profiles back to the file, overwriting it
        try (FileWriter fw = new FileWriter(PROFILES_FILE, false); // Overwrite the file
             PrintWriter pw = new PrintWriter(fw)) {
            for (String[] profile : allProfiles) {
                pw.println(String.join(",", profile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGoals(String username, String[] dailyGoals, String[] weeklyGoals) {
        List<String[]> allGoals = new ArrayList<>();
        boolean goalsFound = false;

        // Read all existing goals, excluding the one for the current username
        try (BufferedReader br = new BufferedReader(new FileReader(GOALS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(username)) {
                    goalsFound = true; // Mark that we found the goals
                } else {
                    allGoals.add(values);
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }

        // Add the new (or updated) goals
        String daily = String.join(";", dailyGoals);
        String weekly = String.join(";", weeklyGoals);
        allGoals.add(new String[]{username, daily, weekly});

        // Write all goals back to the file, overwriting it
        try (FileWriter fw = new FileWriter(GOALS_FILE, false); // Overwrite the file
             PrintWriter pw = new PrintWriter(fw)) {
            for (String[] goal : allGoals) {
                pw.println(String.join(",", goal));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] loadGoals(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(GOALS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(username)) {
                    String[] loadedDaily = values[1].split(";");
                    String[] loadedWeekly = values[2].split(";");

                    String[] daily = new String[5]; // Expected 5 daily goals
                    String[] weekly = new String[4]; // Expected 4 weekly goals

                    System.arraycopy(loadedDaily, 0, daily, 0, Math.min(loadedDaily.length, daily.length));
                    System.arraycopy(loadedWeekly, 0, weekly, 0, Math.min(loadedWeekly.length, weekly.length));

                    // Fill remaining with empty strings if loaded data is shorter
                    for (int i = loadedDaily.length; i < daily.length; i++) daily[i] = "";
                    for (int i = loadedWeekly.length; i < weekly.length; i++) weekly[i] = "";

                    return new String[][]{daily, weekly};
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return null; // Goals not found
    }

    public static String[] loadProfile(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(PROFILES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > 0 && values[0].equals(username)) {
                    return values;
                }
            }
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException)) {
                e.printStackTrace();
            }
        }
        return null; // Profile not found
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