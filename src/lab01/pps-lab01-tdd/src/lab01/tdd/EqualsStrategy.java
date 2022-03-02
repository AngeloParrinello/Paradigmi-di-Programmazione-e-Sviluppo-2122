package lab01.tdd;

public class EqualsStrategy extends InputNumberStrategy {


    public EqualsStrategy(int inputNumber) {
        super(inputNumber);
    }

    @Override
    public boolean apply(int element) {
        return element == super.getInputNumber();
    }
}
