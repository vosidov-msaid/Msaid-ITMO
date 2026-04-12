package model;

import java.util.*;

public abstract class Character{
    protected String name;
    protected SleepStatus state;

    public Character(String name, SleepStatus state){
        this.name = name;
        this.state = state;
    }

    public String getName(){
        return name;
    }

    public SleepStatus getState() {
        return state;
    }

    public void wakeUp() {
        state = SleepStatus.AWAKE;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof Character)) return false;
        Character c = (Character) obj;
        return Objects.equals(name, c.name) && state == c.state;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, state);
    }

    @Override
    public String toString(){
        return Objects.toString(name) + " (" + Objects.toString(state) + ")";
    }

    public abstract void doAction();
}