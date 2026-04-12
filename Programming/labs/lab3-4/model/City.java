package model;
import java.util.*;

public class City {
    private String name;
    private TimeOfDay timeOfDay;
    private List<Character> characters;
    private List<Question> history;
    private int questionCounter;

    public City(String name) {
        this.name = name;
        this.timeOfDay = TimeOfDay.NIGHT;
        this.characters = new ArrayList<>();
        this.history = new ArrayList<>();
        this.questionCounter = 0;
    }

    public void addCharacter(Character c){
        if (c == null) {
            throw new exceptions.NullCharacterException();
        }
        characters.add(c);
    }

    public void changeTime(TimeOfDay time){
        if (time == null) {
            throw new exceptions.InvalidTimeException();
        }
        this.timeOfDay = time;
        if (time == TimeOfDay.MORNING) {
            wakeUpCharacters();
            System.out.println("Все коротышки проснулись.\n");
        }
    }

    private void wakeUpCharacters(){
        for (Character c : characters) {
            c.wakeUp();
        }
    }

    public void startDialog() {
        for (Character asker : characters) {
            if(asker.getState() == SleepStatus.AWAKE && asker instanceof Respondent){
                asker.doAction();

                List<Character> rand_characters = new ArrayList<>(characters);
                Collections.shuffle(rand_characters);
                
                for (Character answerer : rand_characters) {
                    if (!asker.equals(answerer) && answerer instanceof Respondent) {
                        try {
                            ((Shorty) asker).askOther((Respondent) answerer);
                            questionCounter++;
                            Question q = new Question(asker, answerer, "Не видал ли ты ночью чего-нибудь подозрительного?", ((Respondent) answerer).respond("Нет, я спал всю ночь"));
                            history.add(q);
                            System.out.println();
                        } catch (exceptions.ErrorAskYourselfException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }

        }
    }

    public void summarize(){
        System.out.println("---Итог---");
        System.out.println("Общий диалог: " + questionCounter);

        System.out.println("Никто ничего не видал и не слыхал.");
        System.out.println("Так все расспросы ни к чему и не привели.");
    }
}