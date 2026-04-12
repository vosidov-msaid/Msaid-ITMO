package Pokemons;
import ru.ifmo.se.pokemon.*;

import Moves.SpecialMoves.Psychic;
import Moves.PhysicalMoves.StoneEdge;
import Moves.StatusMoves.Swagger;
import Moves.StatusMoves.Rest;

public class Diancie extends Pokemon {
    public Diancie(String name, int level){
        super(name,level);
        setStats(50, 100, 150, 100, 150, 50);
        setType(Type.ROCK, Type.FAIRY);
        setMove(new Psychic(), new StoneEdge(), new Swagger(), new Rest());
    }
}