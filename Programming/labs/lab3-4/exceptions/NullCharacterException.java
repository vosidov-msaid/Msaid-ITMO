package exceptions;

public class NullCharacterException extends RuntimeException {
    @Override
    public String getMessage() { 
        return "Ошибка: Персонаж не может быть null."; 
    }
}
