package manager;

import model.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CollectionManager {
    private final LinkedList<SpaceMarine> collection = new LinkedList<>();
    private final FileManager fileManager;
    private LocalDateTime initDate;
    private long nextId = 1;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void loadFromFile() {
        List<SpaceMarine> list = fileManager.readFromFile();
        collection.clear();
        collection.addAll(list);
        if (collection.isEmpty()) {
            nextId = 1;
        } else {
            Long lastId = collection.getLast().getId();
            nextId = (lastId == null ? 1 : lastId + 1);
        }
        initDate = LocalDateTime.now();

        System.out.println("Loaded " + collection.size() + " elements(s) from file.");
    }

    public boolean removeById(long id) {
        return collection.removeIf(sm -> sm.getId() == id);
    }

    public void clear() {
        collection.clear();
    }

    public boolean addIfMax(SpaceMarine sm) {
        if (collection.isEmpty() || sm.compareTo(Collections.max(collection)) > 0) {
            collection.add(sm);
            return true;
        }
        return false;
    }

    public boolean addIfMin(SpaceMarine sm) {
        if (collection.isEmpty() || sm.compareTo(Collections.min(collection)) < 0) {
            collection.add(sm);
            return true;
        }
        return false;
    }

    public void save() {
        fileManager.writeToFile(collection);
    }

    public long generateId() {
        return nextId++;
    }

    public void add(SpaceMarine sm) {
        collection.add(sm);
    }

    public boolean updateById(long id, SpaceMarine updated) {
        for(SpaceMarine sm : collection) {
            if(sm.getId() == id) {
                sm.setName(updated.getName());
                sm.setCoordinates(updated.getCoordinates());
                sm.setHealth(updated.getHealth());
                sm.setCategory(updated.getCategory());
                sm.setWeaponType(updated.getWeaponType());
                sm.setMeleeWeapon(updated.getMeleeWeapon());
                sm.setChapter(updated.getChapter());
                return true;
            }
        }
        return false;
    }

    public LinkedList<SpaceMarine> getCollection() {
        return collection;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void shuffle() {
        Collections.shuffle(collection);
    }

    public long countByMeleeWeapon(MeleeWeapon mw) {
        return collection.stream().filter(sm -> sm.getMeleeWeapon() == mw).count();
    }

    public List<SpaceMarine> getSortedAscending() {
        List<SpaceMarine> sorted = new ArrayList<>(collection);
        Collections.sort(sorted);
        return sorted;
    }

    public List<Double> getHealthDescending() {
        List<Double> healths = new ArrayList<>();
        collection.forEach(sm -> healths.add(sm.getHealth()));
        healths.sort(Collections.reverseOrder());
        return healths;
    }

    public boolean idExists(long id) {
        return collection.stream().anyMatch(sm -> sm.getId() == id);
    }
}