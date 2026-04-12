package lab1;

public class Lab1 {

    public static double calc1(double x) {
        return Math.cos(Math.log(Math.pow(Math.cos(x), 2)));
    }
    public static double calc2(double x) {
        return Math.pow(0.5 / (Math.asin(Math.cos(x)) - 2.0/3.0), 3);
    }
    public static double calc3(double x) {
        return Math.tan(Math.pow(0.25 / (Math.atan(Math.sin(x)) - 3.0), Math.pow(x, 3)));
    }

    public static void main(String[] args) {
        System.out.println("Лабораторная работа #1");
        System.out.println("Вариант: 32982");
        System.out.println();

        // 1. Создать одномерный массив w типа long. Заполнить его чётными числами от 2 до 22 включительно в порядке возрастания.
        long[] w = new long[11];
        for (int i = 0; i < w.length; i++) {
            w[i] = 2 + (i * 2);
        }
        System.out.println("Массив w (long):");
        for (int i = 0; i < w.length; i++) {
            System.out.print(w[i] + " ");
        }
        System.out.println();
        System.out.println();

        // 2. Создать одномерный массив x типа double. Заполнить его 16-ю случайными числами в диапазоне от -3.0 до 6.0.
        double[] x = new double[16];
        System.out.println("Массив x (double):");
        for (int i = 0; i < x.length; i++) {
            x[i] = -3.0 + Math.random() * 9.0;
            System.out.printf("%.5f ", x[i]);
        }
        System.out.println();
        System.out.println();

        // 3. Создать двумерный массив w1 размером 11x16. Вычислить элементы по формуле.
        double[][] w1 = new double[11][16];
        System.out.println("Результирующая матрица W:");
        System.out.println();

        for (int i = 0; i < w1.length; i++) {
            for (int j = 0; j < w1[i].length; j++) {
                if (w[i] == 2) {
                    w1[i][j] = calc1(x[j]);
                }
                else if (w[i] == 6 || w[i] == 10 || w[i] == 18 || w[i] == 20 || w[i] == 22) {
                    w1[i][j] = calc2(x[j]);
                }
                else {
                    w1[i][j] = calc3(x[j]);
                }
                System.out.printf(" %.5s", w1[i][j]);
            }
            System.out.println();
        }
    }
}
