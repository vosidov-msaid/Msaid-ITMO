package exceptions;

public class ErrorAskYourselfException extends RuntimeException {
    @Override
    public String getMessage() { 
        return "Ошибка: Нельзя спрашивать самого себя!"; 
    }
}
