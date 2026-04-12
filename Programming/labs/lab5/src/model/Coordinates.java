package model;

public class Coordinates {
    private Float x; //Поле не может быть null
    private Float y; //Значение поля должно быть больше -44, Поле не может быть null

    public Coordinates(Float x, Float y) {
        if (x == null) { throw new IllegalArgumentException("x cannot be null"); }
        if (y == null || y <= -44) { throw new IllegalArgumentException("y must be greater than -44 and cannot be null"); }

        this.x = x;
        this.y = y;
    }

    public Float getX() { return x; }
    public Float getY() { return y; }
    public void setX(Float x) { this.x = x; }
    public void setY(Float y) { this.y = y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}