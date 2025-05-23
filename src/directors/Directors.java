package directors;

import builders.GoalKeeperCardBuilder;
import builders.PlayerCardBuilder;

public class Directors {
    public static GoalKeeperCardBuilder goalKeeperCardBuilder = new GoalKeeperCardBuilder();
    public static PlayerCardBuilder playerCardBuilder = new PlayerCardBuilder();
    public static void setPlayerCardBuilder(String name, String nationality, int shootingAbility, int pace, int dribblingAbility,
                                            int physicalAbility, int passingAbility, int defendingAbility) {
        playerCardBuilder.name(name).nationality(nationality).shootingAbility(shootingAbility).pace(pace).dribblingAbility(dribblingAbility)
                .physicalAbility(physicalAbility).passingAbility(passingAbility).defendingAbility(defendingAbility).build();
    }
    public static void setGoalKeeperCardBuilder(String name, String nationality, int divingAbility, int handlingAbility, int reflex,
                                                int position, int kickingAbility, int speed) {
        goalKeeperCardBuilder.name(name).nationality(nationality).divingAbility(divingAbility).handingAbility(handlingAbility).reflex(reflex)
                .position(position).kickingAbility(kickingAbility).speed(speed).build();
    }


}
