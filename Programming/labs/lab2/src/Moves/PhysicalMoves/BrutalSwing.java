package Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

public class BrutalSwing extends PhysicalMove {
    public BrutalSwing(){
        super(Type.DARK, 60, 100);
    }

    // яростно размахивает своим телом,
    // нанося урон всему, что находится поблизости

    // Наносит обычный урон без дополнительных эффектов.

    @Override
    protected String describe(){
        return "использует Brutal Swing";
    }
}
