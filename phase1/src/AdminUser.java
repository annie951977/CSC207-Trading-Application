import java.util.UUID;

/**
 * Represents an administrative user in the trading system.
 */

public class AdminUser {
    private String username;
    private String password;
    private UUID adminId = UUID.randomUUID();

    /**
     * Constructs an instance of an Admin User based on Strings of username and password.
     */
    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for username as a String
     * @return username as a String
     */
    public String getUsername() { return username; }

    /**
     * Getter for password as a String
     * @return password as a String
     */
    public String getPassword() { return password; }

    /**
     * Getter for admin id as a UUID
     * @return admin id as a UUID
     */
    public UUID getAdminId() { return adminId; }

    /**
     * Represents the current AdminUser by their username and userId
     * @return the username and adminid separated by a comma
     */
    public String toString() { return username + ", " + adminId; }


}
