package lab01.tdd;

public class MultipleOfStrategyFactory extends InputNumberStrategyFactory {

    public MultipleOfStrategyFactory(int inputNumber) {
        super(inputNumber);
    }

    @Override
    public SelectStrategy createStrategy() {
        return new MultipleOfStrategy(super.getInputNumber());
    }
}
