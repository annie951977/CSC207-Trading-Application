import java.lang.reflect.Array;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;


/**
 * Represents the Transaction History of User by storing information about completed transactions
 */

public class TransactionHistory{
    private ArrayList <Transaction> oneWayTransactions;
    private ArrayList <Transaction> twoWayTransactions;
    private HashMap<User, Integer> usersNumTradeTimes;
    private HashMap<LocalDate, Transaction> dateToTransaction; // decide if this is really needed
    private int numItemsLended = 0;
    private int numItemsBorrowed = 0;

    /**
     * Constructs an instance of TransactionHistory with an empty ArrayList transactions and an empty Hashtable usersNumTradeTimes
     * The most recent Transaction is added to the end of the list transactions
     */

    public TransactionHistory(){
        oneWayTransactions = new ArrayList<>();
        twoWayTransactions = new ArrayList<>();
        usersNumTradeTimes = new HashMap<>();
        dateToTransaction = new HashMap<>();
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
     * Getter for all the transactions in TransactionHistory
     * @return an array list of all transactions, regardless of type
     */
    public ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(oneWayTransactions);
        allTransactions.addAll(twoWayTransactions);
        return allTransactions;
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
        return oneWayTransactions.size() + twoWayTransactions.size();
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

    /**
     * adds a Transaction to the Transaction History
     * @param transaction the transaction being added to the history
     */
    public void setTransactionHistory(Transaction transaction) {
        if (transaction.isOneWay()) {
            oneWayTransactions.add(transaction);
        } else {
            twoWayTransactions.add(transaction);
        }
    }

    /**
     * Returns a String Representation of User's Transaction History
     * @return a String of User's transaction History
     */
//    public String toString(){
//        StringBuilder newString = new StringBuilder("Your Transaction History:" + "/n");
//       for (int i = 0; i < oneWayTransactions.size();) {
//           if(oneWayTransactions.get(i).isPerm()){
//               newString.append(oneWayTransactions.get(i).getFirstMeeting().getDate().toString());
//               newString.append(",");
//               newString.append(oneWayTransactions.get(i).getItem().getName());
//               newString.append("/n");
//           }
//           if(!oneWayTransactions.get(i).isPerm()) {
//               newString.append(oneWayTransactions.get(i).getSecondMeeting().getDate().toString());
//               newString.append(",");
//               newString.append(oneWayTransactions.get(i).getItem().getName());
//               newString.append("/n");
//           }
//           i++;
//        }
//        for (int i = 0; i < twoWayTransactions.size();) {
//            if(twoWayTransactions.get(i).isPerm()){
//                newString.append(twoWayTransactions.get(i).getFirstMeeting().getDate().toString());
//                newString.append(",");
//                newString.append(twoWayTransactions.get(i).getItem1().getName());
//                newString.append(",");
//                newString.append(twoWayTransactions.get(i).getItem2().getName());
//                newString.append("/n");
//            }
//            if(!twoWayTransactions.get(i).isPerm()) {
//                newString.append(twoWayTransactions.get(i).getSecondMeeting().getDate().toString());
//                newString.append(",");
//                newString.append(twoWayTransactions.get(i).getItem1().getName());
//                newString.append(",");
//                newString.append(twoWayTransactions.get(i).getItem2().getName());
//                newString.append("/n");
//            }
//            i++;
//        }
//       return newString.toString();
//    }

//    /**
//     * returns the number of transactions made in the last calendar week
//     * @return number of transactions in a week
//     */
//   public int numTransactionsInWeek(){
//        int numTransactions = 0;
//        ArrayList<Transaction> allTransactions = getAllTransactions();
////        ZoneId k = ZoneId.of("America/Montreal");
////        LocalDate today = LocalDate.now(k); alternative way of doing it; saving just in case
//        Calendar currentCal = Calendar.getInstance();
//        int week = currentCal.get(Calendar.WEEK_OF_YEAR);
//        int year = currentCal.get(Calendar.YEAR);
//        Calendar targetCal = Calendar.getInstance();
//        int targetWeek;
//        int targetYear;
//        for (int i = 0; i < allTransactions.size();){
//            LocalDate date;
//            if(allTransactions.get(i).isPerm()){
//                date = allTransactions.get(i).getFirstMeeting().getDate();
//            } else {
//                date = allTransactions.get(i).getSecondMeeting().getDate(); //TODO: reconsider design of getSecondMeeting
//            }
//            Date setDate = java.sql.Date.valueOf(date);
//            targetCal.setTime(setDate);
//            targetWeek = targetCal.get(Calendar.WEEK_OF_YEAR);
//            targetYear = targetCal.get(Calendar.YEAR);
//            if(targetWeek == week && targetYear == year){
//                numTransactions ++;
//            }
//            i++;
//        }
//       // main idea of this code from: https://stackoverflow.com/questions/10313797/how-to-check-a-day-is-in-the-current-week-in-java
//        return numTransactions;
//   }
}
