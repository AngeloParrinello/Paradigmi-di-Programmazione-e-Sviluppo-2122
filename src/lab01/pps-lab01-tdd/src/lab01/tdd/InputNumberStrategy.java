package lab01.tdd;

public abstract class InputNumberStrategy implements SelectStrategy {

    private final int inputNumber;

    protected InputNumberStrategy(int inputNumber) {
        this.inputNumber = inputNumber;
    }

    public int getInputNumber() {
        return this.inputNumber;
    }
}
