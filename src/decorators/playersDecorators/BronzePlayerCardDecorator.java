package decorators.playersDecorators;

public class BronzePlayerCardDecorator extends PlayerDecorator{
    public BronzePlayerCardDecorator(PlayerInterface decoratedPlayer) {
        super(decoratedPlayer);
    }
    public int getPace() {
        return Math.min(99, decoratedPlayer.getPace() + 1);
    }

    @Override
    public int getDribblingAbility() {
        return Math.min(99, decoratedPlayer.getDribblingAbility() + 3);
    }

    @Override
    public int getDefendingAbility() {
        return Math.min(99, decoratedPlayer.getDefendingAbility() + 2);
    }
}

