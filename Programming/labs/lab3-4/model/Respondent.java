package model;

public interface Respondent {
    String respond(String question);
    boolean sawSomething();
    String getName();
}