//git push origin main

package main.java;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FenwickTree fenwick = new FenwickTree();
        int[] currentArray = null;

        System.out.print("Введите количество элементов массива: ");
        int n = scanner.nextInt();

        int[] array = new int[n];

        System.out.println("Введите элементы массива:");
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        currentArray = array.clone();
        fenwick.build(array);

        fenwick.build(array);

        boolean running = true;
        while (running) {
            System.out.println("\nДоступные операции:");
            System.out.println("1 - Вывести текущий массив");
            System.out.println("2 - Вывести структуру дерева");
            System.out.println("3 - Вычислить префиксную сумму");
            System.out.println("4 - Вычислить сумму на отрезке");
            System.out.println("5 - Обновить элемент");
            System.out.println("0 - Выход");
            System.out.print("Выберите операцию: ");

            int choice = scanner.nextInt();
            try {
                switch (choice) {

                    case 1:
                        if (currentArray != null) {
                            System.out.print("Текущий массив: [");
                            for (int i = 0; i < currentArray.length; i++) {
                                System.out.print(currentArray[i]);
                                if (i < currentArray.length - 1) System.out.print(", ");
                            }
                            System.out.println("]");
                        } else {
                            System.out.println("Массив не инициализирован");
                        }
                        break;
                    case 2:
                        System.out.println("Структура дерева: " + fenwick.toString());
                        break;

                    case 3:
                        System.out.print("Введите индекс (0-" + (n-1) + "): ");
                        int prefixIndex = scanner.nextInt();
                        System.out.println("Префиксная сумма [0.." + prefixIndex + "]: " + fenwick.prefixSum(prefixIndex));
                        break;

                    case 4:
                        System.out.print("Введите левую границу (0-" + (n-1) + "): ");
                        int left = scanner.nextInt();
                        System.out.print("Введите правую границу (" + left + "-" + (n-1) + "): ");
                        int right = scanner.nextInt();
                        System.out.println("Сумма на отрезке [" + left + ".." + right + "]: " + fenwick.rangeSum(left, right));
                        break;

                    case 5:
                        System.out.print("Введите индекс элемента для обновления (0-" + (n-1) + "): ");
                        int updateIndex = scanner.nextInt();
                        System.out.print("Введите значение дельты: ");
                        int delta = scanner.nextInt();
                        fenwick.update(updateIndex, delta);
                        System.out.println("Элемент обновлен");
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        System.out.println("Неверный выбор операции");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }
}


