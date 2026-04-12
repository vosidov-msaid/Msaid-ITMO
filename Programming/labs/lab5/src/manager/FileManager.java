package manager;

import model.*;
import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class FileManager {
    private final String filename;
    
    public FileManager(String filename) {
        this.filename = filename;
    }

    public List<SpaceMarine> readFromFile() {
        List<SpaceMarine> list = new ArrayList<>();
        Set<Long> dbIds = new HashSet<>();
        File f = new File(filename);
        if(!f.exists()){
            System.err.println("File not found: " + filename);
            System.exit(1);
        }
        else if(!f.canRead()){
            System.err.println("Cannot read file: " + filename);
            System.exit(1);
        }

        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(filename))) {
            BufferedReader br = new BufferedReader(reader);
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null){
                lineNum++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("id")){
                    continue;
                }
                try {
                    SpaceMarine sm = parseLine(line);
                    if (sm != null){
                        if (dbIds.contains(sm.getId())) {
                            System.out.println("[Class: FileManager] Skipping line: " + lineNum + ": Duplicate ID " + sm.getId());
                            continue;
                        }
                        dbIds.add(sm.getId());
                        list.add(sm);
                    }
                }
                catch (Exception e) {
                    System.out.println("[Class: FileManager] Skipping line: " + lineNum + ": " + e.getMessage());
                }
            }
        } 
        catch (IOException e) {
            System.out.println("[Class: FileManager] IO error reading file: " + e.getMessage());
        }

        return list;
    }

    public void writeToFile(List<SpaceMarine> spaceMarines) {
        File f = new File(filename);
        if(!f.exists()){
            System.out.println("File not found: " + filename);
            return;
        }
        else if(!f.canWrite()) {
            System.out.println("Cannot write to file: " + filename);
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, false))) {
            for(SpaceMarine sm : spaceMarines) {
                pw.println(toCSV(sm));
            }
            System.out.println("Collection saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("IO error writing to file: " + e.getMessage());
        }
    }

    private SpaceMarine parseLine(String line) {
        String[] rows = line.split(",", -1);
        if (rows.length < 13){
            throw new IllegalArgumentException("Expected 13 columns, got " + rows.length);
        }

        Long id = Long.parseLong(rows[0].trim());
        String name = rows[1].trim();
        float x = Float.parseFloat(rows[2].trim());
        float y = Float.parseFloat(rows[3].trim());
        LocalDate creationDate = LocalDate.parse(rows[4].trim());
        double health = Double.parseDouble(rows[5].trim());
        AstartesCategory category = AstartesCategory.valueOf(rows[6].trim());
        Weapon weapon = rows[7].trim().isEmpty() ? null : Weapon.valueOf(rows[7].trim());
        MeleeWeapon melee = MeleeWeapon.valueOf(rows[8].trim());
        String chName = rows[9].trim();
        String chParent = rows[10].trim().isEmpty() ? null : rows[10].trim();
        int chCount = Integer.parseInt(rows[11].trim());
        String chWorld = rows[12].trim();

        Coordinates coords = new Coordinates(x, y);
        Chapter chapter = new Chapter(chName, chParent, chCount, chWorld);
        return new SpaceMarine(id, name, coords, creationDate, health, category, weapon, melee, chapter);
    }

    public String toCSV(SpaceMarine sm) {
        return sm.getId() + "," +
                sm.getName() + "," +
                sm.getCoordinates().getX() + "," +
                sm.getCoordinates().getY() + "," +
                sm.getCreationDate() + "," +
                sm.getHealth() + "," +
                sm.getCategory() + "," +
                (sm.getWeaponType() != null ? sm.getWeaponType() : "") + "," +
                sm.getMeleeWeapon() + "," +
                sm.getChapter().getName() + "," +
                (sm.getChapter().getParentLegion() != null ? sm.getChapter().getParentLegion() : "") + "," +
                sm.getChapter().getMarinesCount() + "," +
                sm.getChapter().getWorld();
    }
    
}