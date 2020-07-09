import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * This class manages user.
 */
public class UserManager {
    private List<User> allUsers;
    private List<User> flaggedAccounts;
    private List<User> frozenAccounts;
    private HashMap<UUID, User> idToUser;


    /**
     * Creates a list of users.
     */
    public UserManager(List<User> users, List<User> flaggedAccounts, List<User> frozenAccounts) {
        allUsers = users;
        this.flaggedAccounts = flaggedAccounts;
        this.frozenAccounts = frozenAccounts;
        this.idToUser = new HashMap<>();
    }

    /**
     * Adds a new user with given info.
     * @param username online identifier of a User
     * @param password account password
     * @return username and userId as string separated by comma.
     */
    public User addUser(String username, String password) throws InvalidUserException {
        User newUser = new User(username, password);
        if (allUsers.size() == 0) {
            allUsers.add(newUser);
            idToUser.put(newUser.getUserId(),newUser);
            return newUser;
        }
        else {
            for (User user : allUsers) {
                if (user.getUsername().equals(username) | user.getUserId().equals(newUser.getUserId())) {
                    throw new InvalidUserException();
                }
            }
        }
        allUsers.add(newUser);
        idToUser.put(newUser.getUserId(), newUser);
        return newUser;
    }

    /**
     * To retrieve a specific user by username.
     * @param username online identifier of a User
     * @return username and userId as string separated by comma
     */
    public User getUser(String username) throws InvalidUserException {
        for (User user : allUsers) {
            if ((user.getUsername().equals(username))) {
                return user;
                }
            }
        throw new InvalidUserException();
    }

    /**
     * To retrieve a specific user by userId
     * @param id UUID identifier of a User
     * @return user who has the userId id
     */
    public User getUserById(UUID id) throws InvalidUserException{
      if(idToUser.containsKey(id)){
          return idToUser.get(id);
      }  else {
          throw new InvalidUserException();
          }
    }

    /**
     * To add an item to user's specified list, which is either the User's wishlist or inventory.
     * @param user the user
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void addItem(User user, Item item, String listType){
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
        switch (thresholdType) {
            case "borrowThreshold":
                user.setBorrowThreshold(thresholdValue);
                break;
            case "weeklyThreshold":
                user.setWeeklyThreshold(thresholdValue);
                break;
            case "incompleteThreshold":
                user.setIncompleteThreshold(thresholdValue);
                break;
        }
    }

    /**
     * Changes the status of a user's account from active to frozen.
     * @param user A user in the trading system.
     */
    public void freezeAccount(User user){
        user.setStatus("frozen");
    }

    /**
     * Changes the status of a user's account from frozen to active.
     * @param user A user in the trading system.
     */
    public void unfreezeAccount(User user){
        user.setStatus("active");
    }

    /**
     * Adds a transaction to User's transaction history.
     * @param user A user in the trading system.
     * @param transaction a meetup between 2 users.
     */
    public void addToTransactionHistory(User user, Transaction transaction) {
        TransactionHistory tH = user.getTransactionHistory();
        tH.setTransactionHistory(transaction);
        user.setTransactionHistory(tH);
        updateTransactionHistoryValues(user, transaction);
    }

    /**
     * A private helper method for addToTransactionHistory that updates UserNumTradeTimes, NumItemsBorrowed, and NumItemsLended
     * @param user A user in a trading system
     * @param transaction a transaction between two Users
     */

    // consider splitting into two methods. Reasoning for having one method, user1 == user is needed for both updating the UserNumTradeTimes and NumItemsBorrowed, NumItemsLended
     private void updateTransactionHistoryValues(User user, Transaction transaction){
            TransactionHistory tH = user.getTransactionHistory();
         if (transaction.getUser1() == user.getUserId()) {
             user.getTransactionHistory().setNumItemsLended();
             User u2 = idToUser.get(transaction.getUser2());
             if (tH.getUsersNumTradeTimes().containsKey(u2)) {
                 tH.getUsersNumTradeTimes().put(u2, tH.getUsersNumTradeTimes().get(u2) + 1);
             } else {
                 tH.getUsersNumTradeTimes().put(u2, 1);
             }
             if (!transaction.isOneWay()) {
                 user.getTransactionHistory().setNumItemsBorrowed();
             }
         } else {
             user.getTransactionHistory().setNumItemsBorrowed();
             User u1 = idToUser.get(transaction.getUser1());
             if (tH.getUsersNumTradeTimes().containsKey(u1)) {
                 tH.getUsersNumTradeTimes().put(u1, tH.getUsersNumTradeTimes().get(u1) + 1);
             } else {
                 tH.getUsersNumTradeTimes().put(u1, 1);
                 if (!transaction.isOneWay()) {
                     user.getTransactionHistory().setNumItemsLended();
                 }
             }
         }
        }

    /**
     * Checks whether the input username is valid.
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

    /**
     * Returns a list of all Users in the Trading System.
     * @return all users in the system.
     */
    public List<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Returns whether this user with the given username and password is in the system or not.
     * @param username the username of the specified user
     * @param password the password of the specified user
     * @return boolean if this user account is already in the system or not.
     */
    public boolean validUser(String username, String password) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void addToFlaggedAccounts(User user){
        flaggedAccounts.add(user);
    }

    public List<User> getFlaggedAccounts() {
        return flaggedAccounts;
    }
}
