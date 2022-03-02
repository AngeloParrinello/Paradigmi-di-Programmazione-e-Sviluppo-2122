import lab01.example.model.SimpleBankAccountWithAtm;
import org.junit.jupiter.api.BeforeEach;

class SimpleBankAccountWithAtmTest extends AbstractBankAccountTest {

    @BeforeEach
    void beforeEach() {
        super.beforeEach();
        feeHandler = () -> 1;
        bankAccount = new SimpleBankAccountWithAtm(accountHolder, 0, feeHandler);
    }

}