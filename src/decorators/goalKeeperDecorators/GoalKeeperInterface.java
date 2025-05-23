package decorators.goalKeeperDecorators;

import strategies.StrategyClass;

public interface GoalKeeperInterface {
    String getName();
    String getNationality();
    int getDivingAbility();
    int getHandlingAbility();
    int getReflex();
    int getPosition();
    int getKickingAbility();
    int getSpeed();
    int getPrice();
    String getPlayStyleName();
    StrategyClass getStrategy();
    int calculatePower();
    void setPlayStyleName(String playStyleName);
    void setPlayStyle(StrategyClass playStyle);


}
