package decorators.playersDecorators;

import strategies.StrategyClass;

public interface PlayerInterface {
    String getName();
    String getNationality();
    int getPrice();
    int getShootingAbility();
    int getPace();
    int getDribblingAbility();
    int getPhysicalAbility();
    int getPassingAbility();
    int getDefendingAbility();
    StrategyClass getStrategy();
    int calculatePower();
    String getPlayStyleName();

    void setPlayStyle(StrategyClass playStyle);
    void setPlayStyleName(String playStyleName);

}
