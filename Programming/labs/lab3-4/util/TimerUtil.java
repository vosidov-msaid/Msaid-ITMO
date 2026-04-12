package util;

public class TimerUtil {

    public static void delay() {
        delay(1000);
    }
    
    public static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
