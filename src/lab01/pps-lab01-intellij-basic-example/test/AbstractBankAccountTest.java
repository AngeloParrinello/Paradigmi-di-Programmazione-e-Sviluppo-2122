import lab01.example.model.AccountHolder;
import lab01.example.model.BankAccount;
import lab01.example.model.FeeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract public class AbstractBankAccountTest {

    protected AccountHolder accountHolder;
    protected BankAccount bankAccount;
    private final int DEPOSIT = 100;
    private final int WITHDRAW = 70;
    private final int WRONG_USER = 2;
    protected FeeHandler feeHandler = () -> 0;


    @BeforeEach
    void beforeEach() {
        accountHolder = new AccountHolder("Mario", "Rossi", 1);
    }

    @Test
    void testInitialBalance() {
        int INITIAL_BALANCE = 0;
        assertEquals(INITIAL_BALANCE, bankAccount.getBalance());
    }

    @Test
    void testDeposit() {
        bankAccount.deposit(accountHolder.getId(), DEPOSIT);
        assertEquals(DEPOSIT - feeHandler.feeHandler(), bankAccount.getBalance());
    }

    @Test
    void testWrongDeposit() {
        bankAccount.deposit(accountHolder.getId(), DEPOSIT);
        int WRONG_AMOUNT = 50;
        bankAccount.deposit(WRONG_USER, WRONG_AMOUNT);
        assertEquals(DEPOSIT - feeHandler.feeHandler(), bankAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        bankAccount.deposit(accountHolder.getId(), DEPOSIT);
        bankAccount.withdraw(accountHolder.getId(), WITHDRAW);
        assertEquals(DEPOSIT - WITHDRAW - 2 * feeHandler.feeHandler(), bankAccount.getBalance());
    }

    @Test
    void testWrongWithdraw() {
        bankAccount.deposit(accountHolder.getId(), DEPOSIT);
        bankAccount.withdraw(WRONG_USER, WITHDRAW);
        assertEquals((DEPOSIT - feeHandler.feeHandler()), bankAccount.getBalance());
    }

}
