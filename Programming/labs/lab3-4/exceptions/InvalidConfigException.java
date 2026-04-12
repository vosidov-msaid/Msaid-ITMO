package exceptions;

public class InvalidConfigException extends RuntimeException {
    private String parameter;

    public InvalidConfigException(String parameter) {
        this.parameter = parameter;
    }
    @Override
    public String getMessage() { 
        return "Ошибка: Некорректный параметр - " + parameter; 
    }
}
