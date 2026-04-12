// Класс описывает человека
class Person {
    // Поля
    String name;
    int age;

    // Конструктор
    Person(String n, int a) {
        name = n;
        age = a;
    }

    // Метод
    void sayHello() {
        System.out.println("Hi! My name is " + name + ", I'm " + age + " years old.");
    }
}

public class Test {
    public static void main(String[] args) {
        // Создание и инициализация объектов
        Person p1 = new Person("Anna", 25);
        Person p2 = new Person("Igor", 30);

        // Вызов методов
        p1.sayHello();
        p2.sayHello();
    }
}
