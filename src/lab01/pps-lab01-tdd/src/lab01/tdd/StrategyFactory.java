package lab01.tdd;

@FunctionalInterface
public interface StrategyFactory {

    SelectStrategy createStrategy();

}
