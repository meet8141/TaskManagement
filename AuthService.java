import java.sql.*;
import java.util.Scanner;

public class AuthService {
    private Scanner sc = new Scanner(System.in);
    private Encryption_Decryption cipher = new Encryption_Decryption(); // Using your existing encryption class
    private int key = 29093;
    // Add this method in AuthService
    private boolean isValidPassword(String password) {
        if (password.length() < 6) return false;
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasUpper && hasDigit && hasSpecial;
    }
    // === User Sign-Up ===
    public boolean signUp( Connection conn) {
        try  {
            boolean done = false;
            while (!done){
                System.out.println("=== Sign Up ===");
                System.out.print("Enter username: ");
                String username = sc.nextLine();
                String checkname="select username from users where username=?";
                PreparedStatement ps=conn.prepareStatement(checkname);
                ps.setString(1,username);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    System.out.println("Username already exists. Please choose a different username.");

                }
                else{
                System.out.print("Enter email: ");
                String email = sc.nextLine();
                    if (!email.contains("@")&&( !email.endsWith(".com")||!email.endsWith(".org")||!email.endsWith(".in"))) {
                    System.out.println("Please enter a valid email address (must end with @ and .com).");
                    continue;
                     }
                System.out.print("Enter password: ");
                String password = sc.nextLine();
                 if (isValidPassword(password)){
                     String encryptedPassword = cipher.encrypt(password,key); // 🔐 Encrypt password

                     String query = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
                     PreparedStatement stmt = conn.prepareStatement(query);
                     stmt.setString(1, username);
                     stmt.setString(2, email);
                     stmt.setString(3, encryptedPassword);

                     int rows = stmt.executeUpdate();
                     return rows > 0;
                    }
                    else {
                     System.out.println("Password must be at least 6 characters.");
                     System.out.println("1 uppercase letter[A-Z].");
                     System.out.println("1 special character[e.g @].");
                     System.out.println("1 digit[0-9].");
                   }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false ;
    }

    // === User Login ===
    public User login(Connection conn ) {
        try  {
            System.out.println("=== Login ===");
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String inputPassword = sc.nextLine();

            String query = "SELECT * FROM Users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("password");
                String decryptedPassword = cipher.decrypt(encryptedPassword,key);

                if (decryptedPassword.equals(inputPassword)) {
                    return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"));
                } else {
                    System.out.println("Incorrect password.");
                    System.out.print("Do you want to reset your password? (Y/N): ");
                    String choice = sc.nextLine().toLowerCase();
                    if (choice.equals("y")) {
                        if (resetPasswordByEmail()) {
                            System.out.println("Password reset successful. Please log in again.");
                        }
                    }
                }
            } else {
                System.out.println("User not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // === Forgot Password (Reset via Email) ===
    public boolean resetPasswordByEmail() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Enter your registered email: ");
            String email = sc.nextLine();

            String checkQuery = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.print("Enter your new password: ");
                String newPassword = sc.nextLine();
                String encrypted = cipher.encrypt(newPassword,key);

                String updateQuery = "UPDATE Users SET password = ? WHERE email = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, encrypted);
                updateStmt.setString(2, email);
                int rows = updateStmt.executeUpdate();

                return rows > 0;
            } else {
                System.out.println("Email not found in the system.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
