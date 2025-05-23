package enums;

import strategies.AggressiveStrategy;
import strategies.BalancedStrategy;
import strategies.DefensiveStrategy;
import strategies.StrategyClass;

public enum Strategy {
    Balanced(new StrategyClass(new BalancedStrategy()), 3000000, "balanced"),
    Aggressive(new StrategyClass(new AggressiveStrategy()),5000000, "aggressive"),
    defensive(new StrategyClass(new DefensiveStrategy()), 4000000, "defensive"),;
    public final StrategyClass strategy;
    public final int money;
    public final String name;
    Strategy(StrategyClass strategy, int money, String name) {
        this.strategy = strategy;
        this.money = money;
        this.name = name;
    }
}