package apipana.io.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @Test
    void testDepositIncreasesBalance() {
        TransactionService transactionService = new TransactionService();
        String iban = "ES0000001";
        long initialBalance = 0;
        long depositFee = 100;
        transactionService.deposit(iban, depositFee);
        double balanceAfterDeposit = transactionService.getBalance(iban);
        assertEquals(initialBalance + depositFee, balanceAfterDeposit);
    }
}