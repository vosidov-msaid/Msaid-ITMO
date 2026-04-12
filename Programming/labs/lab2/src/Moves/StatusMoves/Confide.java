package Moves.StatusMoves;
import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove {
    public Confide(){
        super(Type.NORMAL, 0, 0);
    }

    // Confide снижает Special Attack цели на одну ступень

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }

    @Override
    protected String describe(){
        return "делится секретом (использует Confide)";
    }
}
