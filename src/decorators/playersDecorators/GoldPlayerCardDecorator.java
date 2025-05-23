package decorators.playersDecorators;

public class GoldPlayerCardDecorator extends PlayerDecorator{
    public GoldPlayerCardDecorator(PlayerInterface playerCard) {
        super(playerCard);
    }
    @Override
    public int getShootingAbility() {
        return Math.min(99, decoratedPlayer.getShootingAbility() + 3);
    }

    @Override
    public int getPassingAbility() {
        return Math.min(99, decoratedPlayer.getPassingAbility() + 3);
    }

    @Override
    public int getDribblingAbility() {
        return Math.min(99, decoratedPlayer.getDribblingAbility() + 3);
    }

    @Override
    public int getPhysicalAbility() {
        return Math.min(99, decoratedPlayer.getPhysicalAbility() + 4);
    }
    @Override
    public int getDefendingAbility() {
        return Math.min(99, decoratedPlayer.getDefendingAbility() + 1);
    }
}
