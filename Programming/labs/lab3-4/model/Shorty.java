package model;

import util.TimerUtil;

public class Shorty extends Character implements Respondent {
    private boolean sawOtherShorty;

    public Shorty(String name, SleepStatus state) {
        super(name, state);
        this.sawOtherShorty = false;
    }

    public void walkCity() {
        System.out.println(name + " гуляет по городу.");
        TimerUtil.delay();
    }

    @Override
    public String respond(String question) {
        return sawOtherShorty ? "Видел что-то подозрительное!" : "Нет, я спал всю ночь";
    }

    public void askOther(Respondent other) {
        if (this.equals(other)) {
            throw new exceptions.ErrorAskYourselfException();
        }
        String answer = other.respond("Не видал ли ты ночью чего-нибудь подозрительного?");
        System.out.println(name + " спрашивает " + other.getName() + ":");
        TimerUtil.delay();
        System.out.println("- \"Не видал ли ты ночью чего-нибудь подозрительного?\"");
        TimerUtil.delay();
        System.out.println(other.getName() + " ответил:");
        TimerUtil.delay();
        System.out.println("- \"" + answer + "\"");
        TimerUtil.delay();
    }

    @Override
    public boolean sawSomething() {
        return sawOtherShorty;
    }

    @Override
    public void doAction() {
        walkCity();
    }

}