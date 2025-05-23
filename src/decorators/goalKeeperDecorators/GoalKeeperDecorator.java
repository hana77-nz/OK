package decorators.goalKeeperDecorators;

import strategies.StrategyClass;

public class GoalKeeperDecorator implements GoalKeeperInterface{
    protected GoalKeeperInterface decoratedGoalKeeper;
    public GoalKeeperDecorator(GoalKeeperInterface goalKeeperCard) {
        this.decoratedGoalKeeper = goalKeeperCard;
    }
    @Override
    public String getName() {
        return decoratedGoalKeeper.getName();
    }

    @Override
    public String getNationality() {
        return decoratedGoalKeeper.getNationality();
    }

    @Override
    public int getDivingAbility() {
        return decoratedGoalKeeper.getDivingAbility();
    }

    @Override
    public int getHandlingAbility() {
        return decoratedGoalKeeper.getHandlingAbility();
    }

    @Override
    public int getReflex() {
        return decoratedGoalKeeper.getReflex();
    }

    @Override
    public int getPosition() {
        return decoratedGoalKeeper.getPosition();
    }

    @Override
    public int getKickingAbility() {
return decoratedGoalKeeper.getKickingAbility();
    }

    @Override
    public int getSpeed() {
        return decoratedGoalKeeper.getSpeed();
    }

    @Override
    public int getPrice() {
        return decoratedGoalKeeper.getPrice();
    }

    @Override
    public void setPlayStyle(StrategyClass playStyle) {
        decoratedGoalKeeper.setPlayStyle(playStyle);
    }

    @Override
    public StrategyClass getStrategy() {
        return decoratedGoalKeeper.getStrategy();
    }

    @Override
    public int calculatePower() {
        return decoratedGoalKeeper.calculatePower();
    }

    @Override
    public void setPlayStyleName(String playStyleName) {
        decoratedGoalKeeper.setPlayStyleName(playStyleName);
    }

    @Override
    public String getPlayStyleName() {
        return decoratedGoalKeeper.getPlayStyleName();
    }

}
