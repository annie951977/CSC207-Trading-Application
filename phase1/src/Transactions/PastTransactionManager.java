package Transactions;

import java.time.LocalDate;
import java.util.*;
import java.util.UUID;
import java.util.HashMap;

import Exceptions.InvalidTransactionException;
import Users.TradingUser;
import Users.TransactionHistory;

public class PastTransactionManager extends TransactionManager{
    public PastTransactionManager(HashMap<UUID, Transaction> transactions){
        super(transactions);

    }

    public List<Transaction> generateTransactionsList(TransactionHistory transactionHistory) throws InvalidTransactionException {
        List<UUID>pastTransactionsIDs = transactionHistory.getAllTransactions();
        return getTransactionsFromIdList(pastTransactionsIDs);
    }

    public int numTransactionsInWeek(TradingUser tradingUser) throws InvalidTransactionException {
        TransactionHistory transactionHistory = tradingUser.getTransactionHistory();
        int numTransactions = 0;
        List<Transaction> allTransactions = generateTransactionsList(transactionHistory);
//        ZoneId k = ZoneId.of("America/Montreal");
//        LocalDate today = LocalDate.now(k); alternative way of doing it; saving just in case
        Calendar currentCal = Calendar.getInstance();
        int week = currentCal.get(Calendar.WEEK_OF_YEAR);
        int year = currentCal.get(Calendar.YEAR);
        Calendar targetCal = Calendar.getInstance();
        int targetWeek;
        int targetYear;
        for (int i = 0; i < allTransactions.size();){
            LocalDate date;
            Transaction currTrans = allTransactions.get(i);
            date = currTrans.getTransactionMeetings().get(currTrans.getTransactionMeetings().size() - 1).getDate();
            Date setDate = java.sql.Date.valueOf(date);
            targetCal.setTime(setDate);
            targetWeek = targetCal.get(Calendar.WEEK_OF_YEAR);
            targetYear = targetCal.get(Calendar.YEAR);
            if(targetWeek == week && targetYear == year){
                numTransactions ++;
            }
            i++;
        }
        // main idea of this code from: https://stackoverflow.com/questions/10313797/how-to-check-a-day-is-in-the-current-week-in-java
        return numTransactions;
    }

    /**
     * Returns if the tradingUser has exceeded the weekly limit of transactions
     * @param tradingUser TradingUser
     * @return boolean
     */
    public boolean weeklyThresholdExceeded(TradingUser tradingUser) throws InvalidTransactionException {
        int threshold = tradingUser.getWeeklyThreshold();
        int numberWeeklyTransactions = numTransactionsInWeek(tradingUser);
        return numberWeeklyTransactions >= threshold;
    }

}
