package apipana.io.tdd;

import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void testWithdrawFundsIfAvailable(){
        TransactionService transactionService = new TransactionService();
        String iban = "ES0000001";
        double initialBalance = 100;
        double withdrawAmount = 10;
        // Creating account
        transactionService.deposit(iban, initialBalance);
        transactionService.withdraw(iban, withdrawAmount);
        double balanceAfterWithDraw = transactionService.getBalance(iban);
        assertEquals(initialBalance - withdrawAmount, balanceAfterWithDraw);
    }

    @Test
    void testCannotWithdrawMoreThanAvailableBalance(){
        String iban = "ES0000001";
        double initialBalance = 100;
        double withdrawAmount = 200;

        Exception exception = assertThrows(RuntimeException.class, () -> {
            // Creating account
            TransactionService transactionService = new TransactionService();
            transactionService.deposit(iban, initialBalance);
            transactionService.withdraw(iban, withdrawAmount);
        });

        String expectedMessage = "Insufficient funds";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetTransactionLog() {
        TransactionService transactionService = new TransactionService();
        String iban = "ES0000001";
        // Creating transactions
        transactionService.deposit(iban, 1000);
        transactionService.withdraw(iban, 950);
        transactionService.deposit(iban, 200);
        List<BankTransaction> transactions = transactionService.getAccountStatements(iban);

        //Tests for each transaction
        BankTransaction first = transactions.get(0);
        assertEquals("deposit", first.getOperation());
        assertEquals(iban, first.getIBAN());
        assertEquals(1000, first.getAmount());
        assertEquals(1000, first.getBalance());

        BankTransaction second = transactions.get(1);
        assertEquals("withdraw", second.getOperation());
        assertEquals(iban, second.getIBAN());
        assertEquals(950, second.getAmount());
        assertEquals(50, second.getBalance());

        BankTransaction third = transactions.get(2);
        assertEquals("deposit", third.getOperation());
        assertEquals(iban, third.getIBAN());
        assertEquals(200, third.getAmount());
        assertEquals(250, third.getBalance());
    }
}