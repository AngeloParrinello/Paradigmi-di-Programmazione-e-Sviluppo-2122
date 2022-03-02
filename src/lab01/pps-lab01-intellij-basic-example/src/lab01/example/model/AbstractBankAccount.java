package lab01.example.model;

public abstract class AbstractBankAccount implements BankAccount {

    private final AccountHolder holder;
    private double balance;

    protected AbstractBankAccount(AccountHolder holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    @Override
    public AccountHolder getHolder() {
        return holder;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(int userID, double amount) {
        if (checkUser(userID)) {
            this.balance = this.balance + amount;
        }
    }

    @Override
    public void withdraw(int userID, double amount) {
        if (checkUser(userID) && isWithdrawAllowed(amount)) {
            this.balance = this.balance - amount;
        }
    }

    protected boolean isWithdrawAllowed(final double amount){
        return this.balance >= amount;
    }

    protected boolean checkUser(final int id) {
        return this.holder.getId() == id;
    }
}
