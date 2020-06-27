import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMenu {
    private User currentUser;
    private AdminManager am;
    private UserManager um;
    private HashMap<Item, User> allPendingItems;

    public UserMenu(UserManager userManager, AdminManager adminManager,
                    HashMap<Item, User> pendingItems, User currentUser) {
        this.currentUser = currentUser;
        HashMap<Item, User> allPendingItems;
        am = adminManager;
        um = userManager;
    }

    public String requestAddItem(String itemName){
        return (String) "a";
    }

    /**
     * To withdraw item from user's specified list, which is either the User's wishlist or inventory.
     * @param item An item in the trading system.
     * @param listType either "wishlist" or "inventory" as a String
     */
    public void withdrawItem(Item item, String listType){
        if(listType.equals("wishlist")){
            um.removeItem(currentUser, item, "wishlist");
        }else if (listType.equals("inventory")){
            um.removeItem(currentUser,item,"inventory");
        }
    }

    /**
     * To add an given item to user's wishlist
     * @param item An item in the trading system
     */
    public void addToWishlist(Item item){
        um.addItem(currentUser, item, "wishlist");
    }

    /**
     * To return the wishlist of currUser
     * @return list of items
     */
    public List<Item> getUserWishlist(){return currentUser.getWishlist();}

    /**
     * TO return the inventory of currUser
     * @return list of items
     */
    public List<Item> getUserInventory(){return currentUser.getInventory();}

    /**
     * To return all the available items in other user's inventory.
     * @return list of items that are available in other user's inventory.
     */
    public List<Item> getAvailableItems(){
        List<Item> availableItems = new ArrayList<>();
        List<User> allUsers = um.getAllUsers();
        for (User user:allUsers) {
            if(!user.equals(currentUser)){
                availableItems.addAll(user.getInventory());
            }
        }return availableItems;
    }


    //Transaction methods
    /**
     * To change a Transaction status to canceled
     * @param transaction A transaction to be canceled and to remove transaction from tra
     */
    public void cancelTransaction(Transaction transaction){
        currentUser.getTransactionDetails().getIncomingOffers().remove(transaction);
        transaction.setStatus("cancelled");
    }
    /**
     * creates a Transaction and adds it to users
     * adds the Transaction to transaction details of both users
     * @param targetUser The User to whom currUser sends a Transaction
     */
    public void createTransaction(User targetUser){
        //TODO: method body
    }


}
