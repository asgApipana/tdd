package apipana.io.tdd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionService {
    public static String WITHDRAW = "withdraw";
    public static String DEPOSIT = "deposit";

    HashMap<String, Double> accounts;
    ArrayList<BankTransaction> transactions;

    public TransactionService() {
        this.accounts = HashMap.newHashMap(1024);
        this.transactions = new ArrayList<BankTransaction>();
    }

    public boolean withdraw(String IBAN, double amount) {
        double balance = accounts.get(IBAN);
        if (balance >= amount) {
            double newBalance = balance - amount;
            BankTransaction newWithdraw = new BankTransaction(IBAN, LocalDateTime.now(),
                    amount, newBalance, WITHDRAW);
            transactions.add(newWithdraw);
            accounts.put(IBAN, newBalance);
            return true;
        }

        throw new RuntimeException("Insufficient funds");
    }

    public void deposit(String IBAN, double amount) {
        // Register user if it doesnt exist
        if (!accounts.containsKey(IBAN)) {
            accounts.put(IBAN, 0.00);
        }

        double oldBalance = accounts.get(IBAN);
        double newBalance = oldBalance + amount;
        BankTransaction newWithdraw = new BankTransaction(IBAN, LocalDateTime.now(),
                amount, newBalance, DEPOSIT);
        transactions.add(newWithdraw);
        accounts.put(IBAN, newBalance);
    }

    public List<BankTransaction> getAccountStatements(String IBAN) {
        ArrayList<BankTransaction> accountStatements = new ArrayList<BankTransaction>();
        for(BankTransaction transaction : transactions){
            if(transaction.getIBAN().equals(IBAN)){
                accountStatements.add(transaction);
            }
        }
        return accountStatements;
    }

    public double getBalance(String IBAN) {
        return accounts.get(IBAN);
    }
}
