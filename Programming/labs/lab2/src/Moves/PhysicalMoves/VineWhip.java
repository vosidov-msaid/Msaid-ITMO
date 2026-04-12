package Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

public class VineWhip extends PhysicalMove {
    public VineWhip(){
        super(Type.GRASS, 45, 100);
    }

    // Vine Whip наносит урон без дополнительного эффекта

    @Override
    protected String describe(){
        return "использует Vine Whip";
    }
}
