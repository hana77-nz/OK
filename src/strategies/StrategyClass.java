package strategies;

public class StrategyClass {
    private PlayStyle playStyle;
    public StrategyClass(PlayStyle playStyle) {
        this.playStyle = playStyle;
    }

    public PlayStyle getPlayStyle() {
        return playStyle;
    }
}

