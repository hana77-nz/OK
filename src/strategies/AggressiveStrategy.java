package strategies;

import models.Team;

public class AggressiveStrategy implements PlayStyle {
    @Override
    public int calculatePower(int power) {
        switch (power) {
            case 1:
                return (Team.cb.getShootingAbility() + Team.cb.getPace() + Team.cb.getDribblingAbility()) / 3;
            case 2:
                return (Team.st.getShootingAbility() + Team.st.getPace() + Team.st.getDribblingAbility()) / 3;
        }
        return 0;
    }
}
