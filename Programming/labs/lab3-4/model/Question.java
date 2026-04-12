package model;

public record Question(
    Character asker,
    Character asked,
    String question,
    String answer
) {}