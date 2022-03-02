package lab01.example.model;

public class SimpleBankAccountWithAtm extends AbstractBankAccount {

    private final FeeHandler feeHandler;

    public SimpleBankAccountWithAtm(AccountHolder holder, double balance, FeeHandler feeHandler) {
        super(holder, balance);
        this.feeHandler = feeHandler;
    }

    @Override
    public void deposit(int userID, double amount) {
        if (checkUser(userID)) {
            super.deposit(userID, this.handleAmount(amount, this.feeHandler, "-"));
        }
    }

    @Override
    public void withdraw(int userID, double amount) {
        if (checkUser(userID) && isWithdrawAllowed(amount)) {
            super.withdraw(userID, this.handleAmount(amount, this.feeHandler, "+"));
        }
    }

    public FeeHandler getFeeHandler() {
        return this.feeHandler;
    }

    private double handleAmount(double amount, FeeHandler fee, String symbol) {
        switch (symbol) {
            case "+": return amount + fee.feeHandler();
            case "-": return amount - fee.feeHandler();
            default: return amount;
        }
    }


}
