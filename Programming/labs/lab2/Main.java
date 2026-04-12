
// public - доступен из любого места
// protected - доступен внутри класса, пакета и в наследниках
// default (package-private) - Доступен только внутри одного файла/пакета
// private - доступен только внутри класса
class BankAccount { 
    // public — доступен из любого места
    public String username;

    // private - доступен только внутри класса
    private double balance;

    public void getUsername() {
        System.out.println("I'm " + username);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void getBalance() {
        // доступ через public метод
        System.out.println("Your balance: " + balance);
    }
}

// Default. Без модификатора доступа. Доступен только внутри одного файла/пакета
class PackagePrivateExample {
    String message = "Inside this file";

    void showMessage() {
        System.out.println(message);
    }
}

// protected — доступен внутри класса, пакета и в наследниках
class ParentAccount {
    protected double bonus = 500;

    protected void showBonus() {
        System.out.println("Protected bonus: " + bonus);
    }
}

// Дочерний класс наследует protected поля и методы
class ChildAccount extends ParentAccount {
    void useBonus() {
        System.out.println("Child class can access protected field: " + bonus);
        showBonus();
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.username = "Ivan Ivanovich";
        bankAccount.getUsername();
        bankAccount.deposit(1000);
        bankAccount.getBalance();
        
        System.out.println("\n=== Example package-private ===");
        PackagePrivateExample pkg = new PackagePrivateExample();
        // доступен, потому что в том же файле
        pkg.showMessage();

        System.out.println("\n=== Example protected ===");
        ChildAccount child = new ChildAccount();
        // наследник имеет доступ к protected полям и методам
        child.useBonus();

    }
}