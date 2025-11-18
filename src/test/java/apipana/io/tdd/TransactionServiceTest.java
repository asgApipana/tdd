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
}