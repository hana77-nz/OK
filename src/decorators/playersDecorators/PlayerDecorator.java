package decorators.playersDecorators;
import strategies.StrategyClass;

    public class PlayerDecorator implements PlayerInterface{
        protected PlayerInterface decoratedPlayer;

        public PlayerDecorator(PlayerInterface playerCard) {
            this.decoratedPlayer = playerCard;
        }

        @Override
        public String getName() {
            return decoratedPlayer.getName();
        }

        @Override
        public String getNationality() {
            return decoratedPlayer.getNationality();
        }

        @Override
        public int getShootingAbility() {
            return decoratedPlayer.getShootingAbility();
        }

        @Override
        public int getPace() {
            return decoratedPlayer.getPace();
        }

        @Override
        public int getDribblingAbility() {
            return decoratedPlayer.getDribblingAbility();
        }

        @Override
        public int getPhysicalAbility() {
            return decoratedPlayer.getPhysicalAbility();
        }

        @Override
        public int getPassingAbility() {
            return decoratedPlayer.getPassingAbility();
        }

        @Override
        public int getDefendingAbility() {
            return decoratedPlayer.getDefendingAbility();
        }

        @Override
        public int getPrice() {
            return decoratedPlayer.getPrice();
        }

        @Override
        public void setPlayStyle(StrategyClass playStyle) {
            decoratedPlayer.setPlayStyle(playStyle);
        }

        @Override
        public StrategyClass getStrategy() {
            return decoratedPlayer.getStrategy();
        }

        @Override
        public int calculatePower() {
            return decoratedPlayer.calculatePower();
        }

        @Override
        public void setPlayStyleName(String playStyleName) {
            decoratedPlayer.setPlayStyleName(playStyleName);
        }

        @Override
        public String getPlayStyleName() {
            return decoratedPlayer.getPlayStyleName();
        }

}
