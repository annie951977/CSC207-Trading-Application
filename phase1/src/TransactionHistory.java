import java.util.*;
import java.time.LocalDate;


/**
 * Represents the trade history of a User
 */

public class TransactionHistory {
    private ArrayList <Transaction> oneWayTransactions;
    private ArrayList <Transaction> twoWayTransactions;
    private HashMap<User, Integer> usersNumTradeTimes;
    private HashMap<LocalDate, Transaction> dateToTransaction;
    private int numTransactions = 0;
    private int numItemsLended = 0;
    private int numItemsBorrowed = 0;
    // TODO: get week transactions --> this should be a boolean and this should call the threshold from the User
    // TODO: toString id
    // TODO: Add to transaction history method
    /**
     * Constructs an instance of TransactionHistory with an empty ArrayList transactions and an empty Hashtable usersNumTradeTimes
     * The most recent Transaction is added to the end of the list transactions
     */

    public TransactionHistory(){
        oneWayTransactions = new ArrayList<Transaction>();
        twoWayTransactions = new ArrayList<Transaction>();
        usersNumTradeTimes = new HashMap<User,Integer>();
        dateToTransaction = new HashMap<LocalDate, Transaction>();
    }
    /**
     * @return the three most recent OneWay Transactions
     */
    public ArrayList<Transaction> mostRecentOneWayTransactions(){
        if (oneWayTransactions.size() <= 3) {
           return oneWayTransactions;
        } return (ArrayList<Transaction>) oneWayTransactions.subList(oneWayTransactions.size() - 3, twoWayTransactions.size());
    }

    /**
     * @return the three most recent TwoWay Transaction
     */
    public ArrayList<Transaction> mostRecentTwoWayTransactions(){
        if(twoWayTransactions.size() <= 3){
            return twoWayTransactions;
        }
        return (ArrayList<Transaction>) twoWayTransactions.subList(twoWayTransactions.size() - 3, twoWayTransactions.size());
    }
    /**
     * @return the usernames of the User's top three trading partners
     */
    public ArrayList<String> mostTradedWithUsers(){
        HashMap<User, Integer> temp = (HashMap<User, Integer>) usersNumTradeTimes.clone();
        ArrayList<Map.Entry<User, Integer>> mostTradedWith = new ArrayList<>();
        ArrayList<String> mostTradedWithUsernames = new ArrayList<>();
        // fuck it, let's just iterate through multiple times because I don't want to deal with a linked hashmap jfc

        // if the user hasn't traded with anyone yet
        if(usersNumTradeTimes.size() == 0){
            String note = "You haven't traded with anyone yet!";
            mostTradedWithUsernames.add(note);
            return mostTradedWithUsernames;
        }
        // if user has less than or equal to 3 trades
        //// this is not necessary actually now that I think about it
        if(usersNumTradeTimes.size() <= 3){
            Set<User> users = usersNumTradeTimes.keySet();
            for (User userTemp : users) {
                mostTradedWithUsernames.add(userTemp.getUsername());
            }
            return mostTradedWithUsernames;
            }

        // if user has greater than 3 trades

        // find the three most traded users
        int j = 0;
        while(j < 3){
            Map.Entry<User, Integer> maxUser = null;
            for(Map.Entry<User, Integer> entry: temp.entrySet()){
                if(maxUser == null || entry.getValue() >= maxUser.getValue()){
                    maxUser = entry;
                }
            }
            mostTradedWith.add(maxUser);
            temp.remove(maxUser.getKey());
            j ++;
        }
        // takes each max user and gets the username, sticks it in an array list. yay.
        for(int counter = 0; counter < mostTradedWith.size();){
            String username = mostTradedWith.get(counter).getKey().getUsername();
            mostTradedWithUsernames.add(username);
        }
        return mostTradedWithUsernames;

    }
    /**
     * @return all OneWay Transactions
     */
    public ArrayList <Transaction> getOneWayTransactions(){
        return oneWayTransactions;
    }

    /**
     * @return all TwoWay Transactions
     */
    public ArrayList<Transaction> getTwoWayTransactions(){
        return twoWayTransactions;
    }

    /**
     * @return all of users and the times they have been traded with
     */
    public  HashMap<User, Integer> getUsersNumTradeTimes(){
        return usersNumTradeTimes;
    }

    /**
     * Getter for this User's number of transactions.
     * @return numTransactions as an integer.
     */
    public int getNumTransactions() {
        return numTransactions;
    }

    /**
     * Getter for numItemsLended as an int
     * @return numItemsLended as an integer
     */
    public int getNumItemsLended() { return numItemsLended; }
    /**
     * Getter for dateToTransaction
     */
    public HashMap<LocalDate,Transaction> getDateToTransaction() {return dateToTransaction;}

    /**
     * Setter for numItemsLended, increases by 1 every time it is called
     */
    public void setNumItemsLended() { this.numItemsLended++; }

    /**
     * Getter for numItemsBorrowed
     * @return numItemsBorrowed as an integer
     */
    public int getNumItemsBorrowed() { return numItemsBorrowed; }

    /**
     * Setter for numItemsBorrowed, increases by 1 every time it is called
     */
    public void setNumItemsBorrowed() { this.numItemsBorrowed++; }

}
