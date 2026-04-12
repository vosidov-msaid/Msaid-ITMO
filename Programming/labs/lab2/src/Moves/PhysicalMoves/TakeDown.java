package Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

public class TakeDown extends PhysicalMove {
    public TakeDown() {
        super(Type.NORMAL, 90, 85);
    }

    // TakeDown получает лишь 1/4 от нанесённого урона при отдаче

    @Override
    protected void applySelfDamage(Pokemon att, double damage) {
        double recoil = damage / 4.0;
        att.setMod(Stat.HP, (int)(-recoil));
    }

    @Override
    protected String describe(){
        return "использует Take Down";
    }
}
