import lab01.example.model.SimpleBankAccount;
import org.junit.jupiter.api.BeforeEach;

/**
 * The test suite for testing the SimpleBankAccount implementation
 */
class SimpleBankAccountTest extends AbstractBankAccountTest {

    @BeforeEach
    void beforeEach() {
        super.beforeEach();
        bankAccount = new SimpleBankAccount(accountHolder, 0);
    }

}
