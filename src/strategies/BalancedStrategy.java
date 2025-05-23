package strategies;

import models.Team;

public class BalancedStrategy implements PlayStyle{
    @Override
    public int calculatePower(int power) {
        switch (power) {
            case 0:
                return (Team.gk.getDivingAbility() + Team.gk.getHandlingAbility() + Team.gk.getReflex()
                        + Team.gk.getPosition() + Team.gk.getKickingAbility() + Team.gk.getSpeed()) / 6;
            case 1:
                return (Team.cb.getPhysicalAbility() + Team.cb.getPassingAbility() + Team.cb.getDefendingAbility()
                        + Team.cb.getShootingAbility() + Team.cb.getPace() + Team.cb.getDribblingAbility()) / 6;
            case 2:
                return (Team.st.getPhysicalAbility() + Team.st.getPassingAbility() + Team.st.getDefendingAbility()
                        + Team.st.getShootingAbility() + Team.st.getPace() + Team.st.getDribblingAbility()) / 6;

        }
        return 0;
    }
}
