package command.commands;

import command.Command;
import manager.CollectionManager;
import model.MeleeWeapon;

import java.util.*;

public class CountByMeleeWeaponCommand implements Command {
    private final CollectionManager cm;

    public CountByMeleeWeaponCommand(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage: count_by_melee_weapon <WEAPON>");
            System.out.println("Values: " + Arrays.toString(MeleeWeapon.values()));
            return;
        }

        try {
            MeleeWeapon mw = MeleeWeapon.valueOf(args[0].trim().toUpperCase());
            System.out.println("Count: " + cm.countByMeleeWeapon(mw));

        } catch (IllegalArgumentException e) {
            System.out.println("Unknown melee weapon. Values: " + Arrays.toString(MeleeWeapon.values()));
        }
    }

    @Override
    public String description() {
        return "count_by_melee_weapon <weapon> — count by melee weapon";
    }
}