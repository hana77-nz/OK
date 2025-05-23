package decorators.goalKeeperDecorators;

public class BronzeGoalKeeperCardDecorator extends GoalKeeperDecorator{
    public BronzeGoalKeeperCardDecorator(GoalKeeperInterface goalKeeperCard) {
        super(goalKeeperCard);
    }
    @Override
    public int getSpeed() {
        return Math.min(99, decoratedGoalKeeper.getSpeed() + 2);
    }

    @Override
    public int getHandlingAbility() {
        return Math.min(99, decoratedGoalKeeper.getHandlingAbility() + 1);
    }

    @Override
    public int getReflex() {
        return Math.min(99, decoratedGoalKeeper.getReflex() + 3);
    }

}
