import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Change info in user and item entities.
 */
public class UserManager {
    private List<User> allUsers;

    /**
     * Creates a list of users.
     */
    public UserManager(List<User> users) {
        allUsers = users;
    }

    /**
     * Add a new user with given info.
     * @param username online identifier of a User
     * @param password account password
     * @return username and userId as string separated by comma.
     */
    public User addUser(String username, String password){
        User user = new User(username, password);
        allUsers.add(user);
        return user;
    }

    /**
     * To retrieve a specific user by username.
     * @param username online identifier of a User
     * @return username and userId as string separated by comma
     */
    public User getUser(String username){
        User desiredUser= new User(null,null);
        for (User user : allUsers) {
            if ((user.getUsername().equals(username))) {
                    desiredUser = user;
                }
            }
            return desiredUser;
    }


    /**
     * To add an item to user's specified list, which is either the User's wishlist or inventory.
     * @param username online identifier of a User
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void addItem(String username, Item item, String listType){
        User user = getUser(username);
        List<Item> userInventory = user.getInventory();
        List<Item> userWishlist = user.getWishlist();

        if(listType.equals("wishlist")){
            userWishlist.add(item);
        }
        else if(listType.equals("inventory")){
            userInventory.add(item);
        }

    }


    /**
     * To remove a item from user's specified list, which is either the User's wishlist or inventory.
     * @param user An user in the trading system.
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void removeItem(User user, Item item, String listType) {
        if (listType.equals("wishlist")){
            user.getWishlist().remove(item);
        }
        else if (listType.equals("inventory")){
            user.getInventory().remove(item);
        }
    }


    /**
     * To change the user's specified threshold.
     * @param user A user in the trading system.
     * @param thresholdValue new value of threshold as an int
     * @param thresholdType either "borrow", "weekly", or "incomplete" as a String
     */
    public void changeThreshold(User user, int thresholdValue, String thresholdType){

        if(thresholdType.equals("borrowThreshold")){
            user.setBorrowThreshold(thresholdValue);
        }
        else if(thresholdType.equals("weeklyThreshold")){
            user.setWeeklyThreshold(thresholdValue);
        }
        else if(thresholdType.equals("incompleteThreshold")){
            user.setIncompleteThreshold(thresholdValue);
        }
    }

    /**
     * To change the status of an user's account to frozen.
     * @param user A user in the trading system.
     */
    public void freezeAccount(User user){
        user.setStatus("frozen");
    }

    /**
     * To change the status of an user's account to active.
     * @param user A user in the trading system.
     */
    public void unfreezeAccount(User user){
        user.setStatus("active");
    }

    /**
     * Add a transaction to User's transaction history.
     * @param user A user in the trading system.
     * @param transaction a meetup between 2 users.
     */
    public void addToTransactionHistory(User user, Transaction transaction){
        TransactionHistory transactionHistory = user.getTransactionHistory();
        transactionHistory.setTransactionHistory(transaction);
        user.setTransactionHistory(transactionHistory);
    }

    /**
     * To check whether the username is valid.
     * @param username online identifier of a User
     * @return True or False as boolean
     */
    public boolean checkAvailableUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
