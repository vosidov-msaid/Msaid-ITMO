import model.*;
import util.TimerUtil;

import java.io.IOException;

public class Main {
    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        ConfigChecker.Config cfg = ConfigChecker.loadConfig();
        if (cfg == null) return;

        String cityName = cfg.cityName;
        String[] shortyNames = cfg.shortyNames;

        City city = new City(cityName);

        String[] names = shortyNames;
        for (String n : names) {
            SleepStatus state = SleepStatus.SLEEPING;
            city.addCharacter(new Shorty(n, state));
        }

        clear();
        
        System.out.println("---Ночь в городе " + cityName + "---");
        TimerUtil.delay();
        System.out.println("Все коротышки спят.\n");
        TimerUtil.delay();

        System.out.println("---Утро начинается---");
        TimerUtil.delay();
        city.changeTime(TimeOfDay.MORNING);
        TimerUtil.delay();

        city.startDialog();
        
        city.summarize();
    }
}