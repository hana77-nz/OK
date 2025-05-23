package strategies;

import models.Team;

public class DefensiveStrategy implements PlayStyle {
    @Override
    public int calculatePower(int power) {
        switch (power) {
            case 1:
                return (Team.cb.getPhysicalAbility() + Team.cb.getPassingAbility() + Team.cb.getDefendingAbility()) / 3;
            case 2:
                return (Team.st.getPhysicalAbility() + Team.st.getPassingAbility() + Team.st.getDefendingAbility()) / 3;

        }
        return 0;
    }
}