package Moves.SpecialMoves;
import ru.ifmo.se.pokemon.*;

public class Psychic extends SpecialMove {
    public Psychic(){
        super(Type.PSYCHIC, 90, 100);
    }

    // Acid наносит урон и с вероятностью 10%
    // снижает SpecialDefense цели на одну ступень.

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.1) {
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }

    @Override
    protected String describe(){
        return "использует Phychic";
    }
}
