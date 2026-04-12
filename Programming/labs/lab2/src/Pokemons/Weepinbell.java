package Pokemons;
import ru.ifmo.se.pokemon.*;

import Moves.SpecialMoves.Acid;

public class Weepinbell extends Bellsprout {
    public Weepinbell(String name, int level){
        super(name,level);
        setStats(65, 90, 50, 85, 45, 55);
        // setType(Type.GRASS, Type.POISON);
        addMove(new Acid());
    }
}