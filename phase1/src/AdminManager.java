import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Create and manage instances of AdminUser.
 */
public class AdminManager {
    private List<AdminUser> allAdmins = new ArrayList<>();

    /**
     * Creates a new AdminUser with given username and password.
     * @param username user's account name identifier
     * @param password user's account password
     */
    public void addAdmin(String username, String password) {
        AdminUser admin = new AdminUser(username, password);
        allAdmins.add(admin);
        // return admin; ?
    }

    /**
     * Retrieves an AdminUser with specified ID. Returns null otherwise.
     * @param adminId The AdminUser's account id.
     */
    public AdminUser getAdmin(UUID adminId) {
        AdminUser desiredAdmin = null;
        for (AdminUser admin : allAdmins) {
            if ((admin.getAdminId().equals(adminId))) {
                desiredAdmin = admin;
            }
        }
        return desiredAdmin;
    }

}
