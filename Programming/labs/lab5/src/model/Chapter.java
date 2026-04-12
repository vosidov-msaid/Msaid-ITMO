package model;

public class Chapter {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private Integer marinesCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле не может быть null

    public Chapter(String name, String parentLegion, Integer marinesCount, String world) {
        if (name == null || name.isEmpty()) { throw new IllegalArgumentException("name cannot be null or empty"); }
        if (marinesCount == null || marinesCount <= 0 || marinesCount > 1000) { throw new IllegalArgumentException("marinesCount must be greater than 0 and less than or equal to 1000"); }
        if (world == null) { throw new IllegalArgumentException("world cannot be null"); }
        
        this.name = name;
        this.parentLegion = parentLegion;
        this.marinesCount = marinesCount;
        this.world = world;
    }

    public String getName() { return name; }
    public String getParentLegion() { return parentLegion; }
    public Integer getMarinesCount() { return marinesCount; }
    public String getWorld() { return world; }

    public void setName(String name) { this.name = name; }
    public void setParentLegion(String parentLegion) { this.parentLegion = parentLegion; }
    public void setMarinesCount(Integer marinesCount) { this.marinesCount = marinesCount; }
    public void setWorld(String world) { this.world = world; }

    @Override
    public String toString() {
        return "Chapter{name='" + name + "', parentLegion='" + parentLegion
                + "', marinesCount=" + marinesCount + ", world='" + world + "'}";
    }
}