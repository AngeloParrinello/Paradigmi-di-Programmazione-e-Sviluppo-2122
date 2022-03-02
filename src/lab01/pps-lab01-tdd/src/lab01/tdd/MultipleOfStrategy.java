package lab01.tdd;

public class MultipleOfStrategy extends InputNumberStrategy {

    public MultipleOfStrategy(int inputNumber) {
        super(inputNumber);
    }

    @Override
    public boolean apply(int element) {
        return element % super.getInputNumber() == 0;
    }
}
