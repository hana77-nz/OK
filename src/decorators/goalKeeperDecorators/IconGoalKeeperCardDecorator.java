package decorators.goalKeeperDecorators;

public class IconGoalKeeperCardDecorator extends GoalKeeperDecorator{
    public IconGoalKeeperCardDecorator(GoalKeeperInterface goalKeeperCard) {
        super(goalKeeperCard);
    }

    @Override
    public int getDivingAbility() {
        return Math.min(99, decoratedGoalKeeper.getDivingAbility() + 5);
    }

    @Override
    public int getKickingAbility() {
        return Math.min(99, decoratedGoalKeeper.getKickingAbility() + 2);
    }

    @Override
    public int getSpeed() {
        return Math.min(99, decoratedGoalKeeper.getSpeed() + 5);
    }

    @Override
    public int getReflex() {
        return Math.min(99, decoratedGoalKeeper.getReflex() + 2);
    }
    @Override
    public int getHandlingAbility() {
        return Math.min(99, decoratedGoalKeeper.getHandlingAbility() + 3);
    }
}


