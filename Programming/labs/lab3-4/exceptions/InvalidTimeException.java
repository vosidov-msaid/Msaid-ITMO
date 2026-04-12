package exceptions;

public class InvalidTimeException extends RuntimeException {
    @Override
    public String getMessage() { 
        return "Ошибка: Некорректное время."; 
    }
}
