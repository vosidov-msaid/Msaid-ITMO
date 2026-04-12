package command;

import io.InputReader;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SpaceMarineBuilder {
    private InputReader reader;
    private final boolean interactive;

    public SpaceMarineBuilder(InputReader reader) {
        this.reader = reader;
        this.interactive = reader.isInteractive();
    }

    public SpaceMarine build(long id) throws IOException {
        String name = readNonEmpty("Enter name: ");
        Float x = readFloat("Enter coordinate X: ", null, null);
        Float y = readFloat("Enter coordinate Y (> -44): ", -44f, null);
        Double health = readDouble("Enter health: ", 0.0, null);
        AstartesCategory category = readEnum("Enter category: ", AstartesCategory.class, false);
        Weapon weapon = readEnum("Enter weapon (weaponType, Enter = null): ", Weapon.class, true);
        MeleeWeapon melee = readEnum("Enter melee weapon (meleeWeapon): ", MeleeWeapon.class, false);
        String chName = readNonEmpty("Enter chapter name (chapter.name): ");
        String chParent = readNullable("Enter parent legion (chapter.parentLegion, Enter = null): ");
        Integer chCount = readInt("Enter chapter size (1 - 1000): ", 1, 1000);
        String chWorld = readNonEmpty("Enter world of the chapter (chapter.world): ");

        Coordinates coords = new Coordinates(x, y);
        Chapter chapter = new Chapter(chName, chParent, chCount, chWorld);
        return new SpaceMarine(id, name, coords, LocalDate.now(), health,
                category, weapon, melee, chapter);
    }

    // Field Readers
    private String readNonEmpty(String line) throws IOException {
        String t = read_line(line);
        if(t != null && !t.trim().isEmpty()) {
            return t.trim();
        }
        System.out.println("Value cannot be empty. Input again.");
        return readNonEmpty(line);
    }

    private String readNullable(String line) throws IOException {
        while(true) {
            String t = read_line(line);
            if(t == null || t.trim().isEmpty()) {
                return null;
            }
            else {
                return t.trim();
            }
        }
        
    }

    private Float readFloat(String line, Float min, Float max) throws IOException {
        String t = read_line(line);

        try {
            float value = Float.parseFloat(t.trim());
            if(min != null && value < min) {
                System.out.println("Value must be >= " + min);
                return readFloat(line, min, max);
            }
            if(max != null && value >= max) {
                System.out.println("Value must be <" + max);
                return readFloat(line, min, max);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Send float number");
        }
        return readFloat(line, min, max);
    }

    private Double readDouble(String line, Double min, Double max) throws IOException {
        String t = read_line(line);

        try {
            double value = Double.parseDouble(t.trim());
            if(min != null && value <= min) {
                System.out.println("Value must be > " + min);
                return readDouble(line, min, max);
            }
            if(max != null && value >= max) {
                System.out.println("Value must be <" + max);
                return readDouble(line, min, max);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Send double number");
        }
        return readDouble(line, min, max);
    }

    private Integer readInt(String line, int min, int max) throws IOException {
        String t = read_line(line);
        try {
            int value = Integer.parseInt(t.trim());
            if(value < min || value > max) {
                System.out.println("Value must be from " + min + " to " + max);
                return readInt(line, min, max);
            }
            return value;
        } catch(NumberFormatException e) {
            System.out.println("Send int number");
        }
        return readInt(line, min, max);
    }
    private <E extends Enum<E>> E readEnum(String line, Class<E> type, boolean nullable) throws IOException {
        E[] constants = type.getEnumConstants();
        if(interactive) {
            System.out.print("Available values: ");
            for(E c : constants) { 
                System.out.print(c.name() + " ");
            }
            if(nullable) {
                System.out.print("(Enter = null)");
            }
            System.out.println();
        }

        String t = read_line(line);
        if(nullable && (t == null || t.trim().isEmpty())) {
            return null;
        }

        try {
            return Enum.valueOf(type, t.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect value. Available: " + Arrays.toString(constants));
        }
        return readEnum(line, type, nullable);
    }

    private String read_line(String msg) throws IOException {
        if(interactive) {
            System.out.print(msg);
        }

        return reader.readLine();
    }
}