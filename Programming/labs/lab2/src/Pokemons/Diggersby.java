package Pokemons;
import ru.ifmo.se.pokemon.*;

import Moves.PhysicalMoves.BrutalSwing;

public class Diggersby extends Bunnelby {
    public Diggersby(String name, int level){
        super(name,level);
        setStats(85, 56, 77, 50, 77, 78);
        setType(Type.NORMAL, Type.GROUND);
        addMove(new BrutalSwing());
    }
}