package decorators.goalKeeperDecorators;

import models.GoalKeeperCard;
import decorators.goalKeeperDecorators.GoalKeeperDecorator;
import decorators.goalKeeperDecorators.GoalKeeperInterface;

public class GoldGoalKeeperCardDecorator extends GoalKeeperDecorator {

    public GoldGoalKeeperCardDecorator(GoalKeeperInterface goalKeeperCard) {
        super(goalKeeperCard);
    }

    @Override
    public int getDivingAbility() {
        return Math.min(99, decoratedGoalKeeper.getDivingAbility() + 3);
    }

    @Override
    public int getKickingAbility() {
        return Math.min(99, decoratedGoalKeeper.getKickingAbility() + 3);
    }

    @Override
    public int getReflex() {
        return Math.min(99, decoratedGoalKeeper.getReflex() + 3);
    }

    @Override
    public int getPosition() {
        return Math.min(99, decoratedGoalKeeper.getPosition() + 4);
    }

    @Override
    public int getSpeed() {
        return Math.min(99, decoratedGoalKeeper.getSpeed() + 1);
    }
}
