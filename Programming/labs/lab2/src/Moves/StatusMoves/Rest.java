package Moves.StatusMoves;
import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest(){
        super(Type.PSYCHIC, 0, 0);
    }

    // Пользователь спит 2 хода, но полностью исцеляется
    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().condition(Status.SLEEP).turns(2);
        p.restore();
        p.setCondition(e);
    }

    @Override
    protected String describe(){
        return "использует Rest и засыпает, полностью восстанавливая HP";
    }
}
