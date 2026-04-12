package model;

import java.time.*;

public class SpaceMarine implements Comparable<SpaceMarine> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double health; //Поле не может быть null, Значение поля должно быть больше 0
    private AstartesCategory category; //Поле не может быть null
    private Weapon weaponType; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine(Long id, String name, Coordinates coordinates, LocalDate creationDate, Double health, AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        if (id == null || id <= 0) { throw new IllegalArgumentException("id must be greater than 0"); }
        if (name == null || name.isEmpty()) { throw new IllegalArgumentException("name cannot be null or empty"); }
        if (coordinates == null) { throw new IllegalArgumentException("coordinates cannot be null"); }
        if (creationDate == null) { throw new IllegalArgumentException("creationDate cannot be null"); }
        if (health == null || health <= 0) { throw new IllegalArgumentException("health must be greater than 0"); }
        if (category == null) { throw new IllegalArgumentException("category cannot be null"); }
        if (meleeWeapon == null) { throw new IllegalArgumentException("meleeWeapon cannot be null"); }
        if (chapter == null) { throw new IllegalArgumentException("chapter cannot be null"); }
        
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDate getCreationDate() { return creationDate; }
    public Double getHealth() { return health; }
    public AstartesCategory getCategory() { return category; }
    public Weapon getWeaponType() { return weaponType; }
    public MeleeWeapon getMeleeWeapon() { return meleeWeapon; }
    public Chapter getChapter() { return chapter; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
    public void setHealth(Double health) { this.health = health; }
    public void setCategory(AstartesCategory category) { this.category = category; }
    public void setWeaponType(Weapon weaponType) { this.weaponType = weaponType; }
    public void setMeleeWeapon(MeleeWeapon meleeWeapon) { this.meleeWeapon = meleeWeapon; }
    public void setChapter(Chapter chapter) { this.chapter = chapter; }


    @Override
    public int compareTo(SpaceMarine other) {
        return this.name.compareToIgnoreCase(other.name);
    }
    
    @Override
    public String toString() {
        return "SpaceMarine{" + 
                "id=" + id + 
                ", name='" + name + "'" + 
                ", coordinates=" + coordinates + 
                ", creationDate=" + creationDate + 
                ", health=" + health + 
                ", category=" + category + 
                ", weaponType=" + weaponType + 
                ", meleeWeapon=" + meleeWeapon + 
                ", chapter=" + chapter + 
            "}"; 
    }
}