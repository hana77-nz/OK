package decorators.playersDecorators;

public class HeroPlayerCardDecorator extends PlayerDecorator{

    public HeroPlayerCardDecorator(PlayerInterface playerCard) {
        super(playerCard);
    }
    @Override
    public int getShootingAbility() {
        return Math.min(99, decoratedPlayer.getShootingAbility() + 4);
    }

    @Override
    public int getPace() {
        return Math.min(99, decoratedPlayer.getPace() + 2);
    }

    @Override
    public int getDribblingAbility() {
        return Math.min(99, decoratedPlayer.getDribblingAbility() + 2);
    }

    @Override
    public int getPassingAbility() {
        return Math.min(99, decoratedPlayer.getPassingAbility() + 1);
    }

    @Override
    public int getDefendingAbility() {
        return Math.min(99, decoratedPlayer.getDefendingAbility() + 2);
    }
    @Override
    public int getPhysicalAbility() {
        return Math.min(99, decoratedPlayer.getPhysicalAbility() + 1);
    }
}

