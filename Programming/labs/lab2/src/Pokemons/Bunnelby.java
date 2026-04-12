package Pokemons;
import ru.ifmo.se.pokemon.*;

import Moves.StatusMoves.Agility;
import Moves.PhysicalMoves.TakeDown;
import Moves.StatusMoves.Leer;

public class Bunnelby extends Pokemon {
    public Bunnelby(String name, int level){
        super(name,level);
        setStats(38, 36, 38, 32, 36, 57);
        setType(Type.NORMAL);
        setMove(new Agility(), new TakeDown(), new Leer());
    }
}