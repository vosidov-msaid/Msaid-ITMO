import ru.ifmo.se.pokemon.*;
import Pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();
        
        Pokemon bellsprout = new Bellsprout("Bellsprout", 1);
        Pokemon diancie = new Diancie("Diancie", 1);
        Pokemon bunnelby = new Bunnelby("Bunnelby", 1);
        Pokemon weepinbell = new Weepinbell("Weepinbell", 1);
        Pokemon victreebel = new Victreebel("Victreebel", 1);
        Pokemon diggersby = new Diggersby("Diggersby", 1);
        
        battle.addAlly(bellsprout);
        battle.addAlly(diancie);
        battle.addAlly(bunnelby);
        battle.addFoe(weepinbell);
        battle.addFoe(victreebel);
        battle.addFoe(diggersby);
        
        battle.go();
    }
}
