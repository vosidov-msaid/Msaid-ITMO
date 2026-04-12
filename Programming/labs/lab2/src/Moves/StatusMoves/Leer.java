package Moves.StatusMoves;
import ru.ifmo.se.pokemon.*;

public class Leer extends StatusMove {
    public Leer(){
        super(Type.NORMAL, 0, 100);
    }

    // Leer снижает Defense цели на одну ступень
    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.DEFENSE, -1);
    }

    @Override
    protected String describe(){
        return "угрожающе смотрит (использует Leer)";
    }
}
