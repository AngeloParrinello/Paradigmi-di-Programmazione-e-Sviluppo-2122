package lab01.tdd;

public class EvenStrategyFactory implements StrategyFactory {

    @Override
    public SelectStrategy createStrategy() {
        return new EvenStrategy();
    }
}
