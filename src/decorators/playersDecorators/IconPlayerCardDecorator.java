package decorators.playersDecorators;

public class IconPlayerCardDecorator extends PlayerDecorator {
    public IconPlayerCardDecorator(PlayerInterface playerCard) {
        super(playerCard);
    }
    @Override
    public int getShootingAbility() {
        return Math.min(99, decoratedPlayer.getShootingAbility() + 5);
    }

    @Override
    public int getPace() {
        return Math.min(99, decoratedPlayer.getPace() + 3);
    }

    @Override
    public int getDribblingAbility() {
        return Math.min(99, decoratedPlayer.getDribblingAbility() + 2);
    }

    @Override
    public int getPassingAbility() {
        return Math.min(99, decoratedPlayer.getPassingAbility() + 2);
    }

    @Override
    public int getDefendingAbility() {
        return Math.min(99, decoratedPlayer.getDefendingAbility() + 5);
    }
}


