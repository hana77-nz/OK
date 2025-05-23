package decorators.goalKeeperDecorators;

public class HeroGoalKeeperCardDecorator extends GoalKeeperDecorator{
    public HeroGoalKeeperCardDecorator(GoalKeeperInterface goalKeeperCard) {
        super(goalKeeperCard);
    }
    @Override
    public int getDivingAbility() {
        return Math.min(99, decoratedGoalKeeper.getDivingAbility() + 4);
    }

    @Override
    public int getPosition() {
        return Math.min(99, decoratedGoalKeeper.getPosition() + 1);
    }

    @Override
    public int getHandlingAbility() {
        return Math.min(99, decoratedGoalKeeper.getHandlingAbility() + 2);
    }

    @Override
    public int getKickingAbility() {
        return Math.min(99, decoratedGoalKeeper.getKickingAbility() + 1);
    }
    @Override
    public int getReflex() {
        return Math.min(99, decoratedGoalKeeper.getReflex() + 2);
    }
    @Override
    public int getSpeed() {
        return Math.min(99, decoratedGoalKeeper.getSpeed() + 2);
    }
}
