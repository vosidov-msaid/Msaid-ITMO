package Moves.StatusMoves;
import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam(){
        super(Type.NORMAL, 0, 0);
    }

    // Double Team повышает Evasiveness на одну ступень
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, +1);
    }

    @Override
    protected String describe(){
        return "распыляется (использует Double Team)";
    }
}
