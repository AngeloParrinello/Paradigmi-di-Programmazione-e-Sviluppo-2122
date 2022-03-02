package lab01.tdd;

public abstract class InputNumberStrategyFactory implements StrategyFactory {

    private final int inputNumber;

    public InputNumberStrategyFactory(int inputNumber) {
        this.inputNumber = inputNumber;
    }

    public int getInputNumber() {
        return this.inputNumber;
    }


}
