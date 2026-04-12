package Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

public class StoneEdge extends PhysicalMove {
    public StoneEdge() {
        super(Type.ROCK, 100, 80);
    }

    // Stone Edge наносит урон и имеет увеличенный
    // коэффициент критического удара (1/8 вместо 1/24).

    @Override
    protected double calcCriticalHit(Pokemon a, Pokemon d) {
        return 3.0 * super.calcCriticalHit(a, d);
    }

    @Override
    protected String describe(){
        return "использует Stone Edge";
    }
}
