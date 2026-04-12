package Moves.StatusMoves;
import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger(){
        super(Type.NORMAL, 0, 85);
    }

    // Swagger сбивает цель с толку и повышает ее атаку на две ступени
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
        p.setMod(Stat.ATTACK, +2);
    }

    @Override
    protected String describe(){
        return "вызывает Swagger: выбивает из колеи и резко поднимает атаку противника";
    }
}
