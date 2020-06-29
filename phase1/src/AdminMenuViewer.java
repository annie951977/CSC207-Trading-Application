import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class AdminMenuViewer {
    private AdminMenu am;
    private int input;

    public AdminMenuViewer(AdminMenu adminMenu) {
        am = adminMenu;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean userInteracting = true;

        while (userInteracting) {
            System.out.println("1. Check Pending Items for Approval");
            System.out.println("2. Check Pending Users for Approval");
            System.out.println("3. Create New Admin User");
            System.out.println("4. Add New Item to a User's Wishlist/Inventory");
            System.out.println("5. Change User Threshold");
            System.out.println("6. Logout");
            System.out.println("Pick an option.");
            input = scanner.nextInt();

            if (input == 1) {
                checkPendingItems();
            } else if (input == 2) {
                checkPendingUsers();
            } else if (input == 3) {
                createAdmin();
            } else if (input == 4) {
                addItemToUser();
            } else if (input == 5) {
                changeUserThreshold();
            } else if (input == 6) {
                System.out.println("You have successfully logged out.");
                // stop the while loop
                userInteracting = false;
            } else { System.out.println("Not a valid option. Please enter a valid option."); }
        }
    }

    private void checkPendingItems() {
        Scanner scanner = new Scanner(System.in);
        Iterator<Item> itemIterator = am.getAllPendingItems().keySet().iterator();

        while (itemIterator.hasNext()) {
            System.out.println(itemIterator.next());
            System.out.println("1. Approve item for User's inventory."); // TO-DO optionally: make this print the User's username
            System.out.println("2. Decline item.");
            System.out.println("3. Go to next item.");
            input = scanner.nextInt();
            if (input == 1) {
                am.checkPendingItems(am.getAllPendingItems().get(itemIterator.next()), itemIterator.next(), true);
            }
            else if (input == 2) {
                am.checkPendingItems(am.getAllPendingItems().get(itemIterator.next()), itemIterator.next(), false);
            }
        }
    }

    private void checkPendingUsers() {
        Scanner scanner = new Scanner(System.in);
        for (User user : am.getFlaggedAccounts()) {
            System.out.println(user); // TO-DO: how can we print why this user's account has been flagged?
            System.out.println("1. Freeze account.");
            System.out.println("2. Unfreeze account.");
            System.out.println("3. Go to next user.");
            input = scanner.nextInt();
            if (input == 1) {
                am.checkPendingUsers(user, true);
            }
            else if (input == 2) {
                am.checkPendingUsers(user, false);
            }
        }
    }

    private void createAdmin() {
        if (am.currentAdmin.isFirstAdmin()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter new Administrative User's username: ");
            String username = scanner.nextLine();
            System.out.println("Please enter new Administrative User's password: ");
            String password = scanner.nextLine();
            try {
                am.createNewAdmin(username, password);
                System.out.println("New Admin User " + username + " successfully created.");
            } catch(InvalidAdminException e) {
                System.out.println("Username already taken.");
            }
        }
        else { System.out.println("Permission denied, only the first admin can create new administrative user accounts.");}
    }

    private void addItemToUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the item: ");
        String itemName = scanner.nextLine();
        Item newItem = new Item(itemName);
        System.out.println("Please enter the username of the User: ");
        String username = scanner.nextLine();
        System.out.println("Would you like to add this item to the user's wishlist or inventory?");
        String whichList = scanner.nextLine();
        try {
            if (whichList.equals("wishlist")) {
                am.addItem(username, newItem, "wishlist");
            }
            else if (whichList.equals("inventory")) {
                am.addItem(username, newItem, "inventory");
            }
            else { System.out.println("Not a valid option, you have to pick 'wishlist' or 'inventory'");}
        } catch(InvalidUserException e) {
            System.out.println("Username does not exist. Please enter an existing User's username.");
        }
    }

    private void changeUserThreshold() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the username of the User you would like to change thresholds for: ");
        String username = scanner.nextLine();
        System.out.println("Please enter which threshold ('borrow', 'weekly', or 'incomplete') that you would like to change: ");
        String whichThreshold = scanner.nextLine();
        try {
            switch (whichThreshold) {
                case "borrow": {
                    System.out.println("The current minimum number of times that this user must lend something before they can borrow/trade is: " + am.getUm().getUser(username).getBorrowThreshold());
                    System.out.println("What would you like to change the borrow threshold to?");
                    int newThreshold = scanner.nextInt();
                    am.changeThreshold(am.getUm().getUser(username), newThreshold, "borrow");
                    break;
                }
                case "weekly": {
                    System.out.println("The current maximum number of transactions that this user can participate in a week is: " + am.getUm().getUser(username).getWeeklyThreshold());
                    System.out.println("What would you like to change the weekly threshold to?");
                    int newThreshold = scanner.nextInt();
                    am.changeThreshold(am.getUm().getUser(username), newThreshold, "weekly");
                    break;
                }
                case "incomplete": {
                    System.out.println("The current maximum number of incomplete transactions before this user's account is frozen is: " + am.getUm().getUser(username).getIncompleteThreshold());
                    System.out.println("What would you like to change the incomplete threshold to?");
                    int newThreshold = scanner.nextInt();
                    am.changeThreshold(am.getUm().getUser(username), newThreshold, "incomplete");
                    break;
                }
                default:
                    System.out.println("Not a valid option, you have to pick either 'borrow', 'weekly', or 'incomplete'.");
                    break;
            }
        } catch(InvalidUserException e) {
            System.out.println("Username does not exist. Please enter an existing User's username.");
        }
    }
}
