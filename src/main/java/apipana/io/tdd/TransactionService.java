package apipana.io.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionService {
    HashMap<String, Double> accounts;

    public TransactionService() {
        this.accounts = HashMap.newHashMap(1024);
    }

    public boolean withdraw(String IBAN, double amount) {
        double balance = accounts.get(IBAN);
        if (balance >= amount) {
            accounts.put(IBAN, balance - amount);
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
        accounts.put(IBAN, oldBalance + amount);
    }

    public List<String> getAccountStatement() {
        return new ArrayList<String>();
    }

    public double getBalance(String IBAN) {
        return accounts.get(IBAN);
    }
}
