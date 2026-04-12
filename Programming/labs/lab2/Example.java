public class Example {
    // static final — общая константа для класса
    static final String COMPANY_NAME = "OpenAI";

    // static — общее для всех объектов, можно изменить
    static int count = 0;

    // final — константа, нельзя изменить
    final double PI = 3.14159;


    Example() {
        // увеличивается при каждом создании объекта
        count++; 
    }

    // static-метод (вызов без объекта)
    static void showCount() {
        System.out.println("Object created: " + count);
    }

    // final-метод — нельзя переопределить
    final void showInfo() {
        System.out.println("Company: " + COMPANY_NAME);
        System.out.println("Number p: " + PI);
    }

    public static void main(String[] args) {
        Example ex1 = new Example();
        Example ex2 = new Example();

        // вызов static-метода без объекта
        Example.showCount(); 

        // вызов обычного (final) метода
        ex1.showInfo(); 
    }
}
