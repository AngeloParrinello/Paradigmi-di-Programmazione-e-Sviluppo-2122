package lab01.tdd;

public class EqualsStrategyFactory extends InputNumberStrategyFactory {

    public EqualsStrategyFactory(int inputNumber) {
        super(inputNumber);
    }

    @Override
    public SelectStrategy createStrategy() {
        return new EqualsStrategy(super.getInputNumber());
    }
}
