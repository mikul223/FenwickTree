//git push origin main

package main.java;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FenwickTree fenwick = new FenwickTree();
        int[] currentArray = null;
        Random random = new Random();

        System.out.print("Введите количество элементов массива: ");
        int n = 0;
        while (true) {
            try {
                n = scanner.nextInt();
                if (n <= 0) {
                    System.out.print("Количество элементов должно быть натуральным числом! Введите снова: ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Ошибка: введите целое число! Введите снова: ");
                scanner.next();
            }
        }

        int[] array = new int[n];

        System.out.println("Введите элементы массива");
        for (int i = 0; i < n; i++) {
            while (true) {
                try {
                    System.out.print("Элемент " + i + ": ");
                    array[i] = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Ошибка: введите целое число! Введите снова: ");
                    scanner.next();
                }
            }

        }

        currentArray = array.clone();
        fenwick.build(array);


        boolean running = true;
        while (running) {
            System.out.println("\nДоступные операции:");
            //System.out.println("");
            System.out.println("1 - Вывести текущий массив");
            System.out.println("2 - Вывести структуру дерева");
            System.out.println("3 - Вычислить префиксную сумму");
            System.out.println("4 - Вычислить сумму на отрезке");
            System.out.println("5 - Обновить элемент");
            System.out.println("6 - Добавить элемент");
            System.out.println("7 - Удалить элемент");
            System.out.println("8 - Найти среднее арифметическое на отрезке");
            System.out.println("9 - Найти количество инверсий в массиве");
            System.out.println("10 - Сгенерировать случайный массив");
            System.out.println("0 - Выход");
            System.out.print("Выберите операцию: ");


            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Неверный выбор операции!");
                scanner.next();
                continue;
            }


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
                            System.out.println("Массив не инициализирован!");
                        }
                        break;
                    case 2:
                        System.out.println("Структура дерева: " + fenwick.toString());
                        break;

                    case 3:
                        while (true) {
                            try {
                                System.out.print("Введите индекс (0-" + (n-1) + ") или -1 для отмены операции: ");
                                int prefixIndex = scanner.nextInt();
                                if (prefixIndex == -1) break;
                                System.out.println("Префиксная сумма [0.." + prefixIndex + "]: " + fenwick.prefixSum(prefixIndex));
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            } catch (IllegalArgumentException e) {
                                System.out.println("Ошибка: " + e.getMessage());
                        }
                        }
                        break;

                    case 4:
                        System.out.print("Введите левую границу (0-" + (n-1) + ") или -1 для отмены операции: ");
                        int left = 0;
                        while (true) {
                            try {
                                left = scanner.nextInt();
                                if (left == -1) break;
                                if (left < 0 || left >= n) {
                                    System.out.println("Ошибка: левая граница должна быть в диапазоне [0, " + (n-1) + "]");
                                    System.out.print("Введите левую границу (0-" + (n-1) + ") или -1 для отмены операции: ");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: левая граница должна быть целым числом!");
                                scanner.next();
                                System.out.print("Введите левую границу (0-" + (n-1) + ") или -1 для отмены операции: ");
                            }
                        }
                        if (left == -1) break;


                        int right = 0;
                        while (true){
                            try{
                                System.out.print("Введите правую границу (" + left + "-" + (n-1) + ") или -1 для отмены операции: ");
                                right = scanner.nextInt();
                                if (right == -1) break;
                                if (right < left || right >= n) {
                                    System.out.println("Ошибка: правая граница должна быть в диапазоне [" + left + ", " + (n-1) + "]");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: правая граница должна быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (right == -1) break;
                        System.out.println("Сумма на отрезке [" + left + ".." + right + "]: " + fenwick.rangeSum(left, right));
                        break;

                    case 5:

                        int updateIndex = 0;
                        while (true){
                            try{
                                System.out.print("Введите индекс элемента для обновления (0-" + (n-1) + ") или -1 для отмены операции:  ");
                                updateIndex = scanner.nextInt();
                                if (updateIndex == -1) break;
                                if (updateIndex < 0 || updateIndex >= n) {
                                    System.out.println("Ошибка: индекс должен быть в диапазоне [0, " + (n-1) + "]!");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (updateIndex == -1) break;

                        int delta = 0;
                        while (true) {
                            try {
                                System.out.print("Введите насколько хотите изменить (текущее значение " + currentArray[updateIndex] + "). Например +1 или -2: ");
                                delta = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: Введите целое число!");
                                scanner.next();
                            }
                        }

                        fenwick.update(updateIndex, delta);
                        currentArray[updateIndex] += delta;
                        System.out.println("Элемент обновлен");
                        break;

                    case 6:
                        int newValue = 0;
                        while (true) {
                            try {
                                System.out.print("Введите значение нового элемента: ");
                                newValue = scanner.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: введите целое число!");
                                scanner.next();
                            }
                        }

                        int insertIndex = 0;
                        while (true) {
                            try {
                                System.out.print("Введите индекс для вставки (0-" + currentArray.length + ") или -1 для отмены операции: ");
                                insertIndex = scanner.nextInt();
                                if (insertIndex == -1) break;
                                if (insertIndex < 0 || insertIndex > currentArray.length) {
                                    System.out.println("Ошибка: индекс должен быть в диапазоне [0, " + currentArray.length + "]!");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (insertIndex == -1) break;

                        //Новый массив, на элемент больше
                        int[] newArray = new int[currentArray.length + 1];

                        // Копируем элементы до индекса вставки, вставляем новый элемент, копируем со сдвигом оставшееся
                        for (int i = 0; i < insertIndex; i++) {
                            newArray[i] = currentArray[i];
                        }
                        newArray[insertIndex] = newValue;
                        for (int i = insertIndex; i < currentArray.length; i++) {
                            newArray[i + 1] = currentArray[i];
                        }

                        fenwick.build(newArray);
                        currentArray = newArray;
                        n = currentArray.length;
                        System.out.println("Элемент добавлен. Новый размер массива: " + currentArray.length);
                        break;

                    case 7:
                        if (currentArray.length == 0) {
                            System.out.println("Массив пуст!");
                            break;
                        }

                        int removeIndex = 0;
                        while (true) {
                            try {
                                System.out.print("Введите индекс элемента для удаления (0-" + (currentArray.length-1) + ") или -1 для отмены операции: ");
                                removeIndex = scanner.nextInt();
                                if (removeIndex == -1) break;
                                if (removeIndex < 0 || removeIndex >= currentArray.length) {
                                    System.out.println("Ошибка: индекс должен быть в диапазоне [0, " + (currentArray.length-1) + "]!");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (removeIndex == -1) break;

                        // Новый массив на элемент меньше
                        int[] reducedArray = new int[currentArray.length - 1];

                        for (int i = 0, j = 0; i < currentArray.length; i++) {
                            if (i != removeIndex) {
                                reducedArray[j++] = currentArray[i];
                            }
                        }

                        fenwick.build(reducedArray);
                        currentArray = reducedArray;
                        n = currentArray.length;
                        System.out.println("Элемент удален. Новый размер массива: " + currentArray.length);
                        break;

                    case 8:
                        System.out.print("Введите левую границу (0-" + (n-1) + ") или -1 для отмены операции: ");
                        int aLeft = 0;
                        while (true) {
                            try {
                                aLeft = scanner.nextInt();
                                if (aLeft == -1) break;
                                if (aLeft < 0 || aLeft >= n) {
                                    System.out.println("Ошибка: левая граница должна быть в диапазоне [0, " + (n-1) + "]");
                                    System.out.print("Введите левую границу (0-" + (n-1) + ") или -1 для отмены операции: ");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: левая граница должна быть целым числом!");
                                System.out.print("Введите левую границу (0-" + (n-1) + ") или -1 для отмены операции: ");
                                scanner.next();
                            }
                        }
                        if (aLeft == -1) break;

                        int aRight = 0;
                        while (true){
                            try{
                                System.out.print("Введите правую границу (" + aLeft + "-" + (n-1) + ") или -1 для отмены операции: ");
                                aRight = scanner.nextInt();
                                if (aRight == -1) break;
                                if (aRight < aLeft || aRight >= n) {
                                    System.out.println("Ошибка: правая граница должна быть в диапазоне [" + aLeft + ", " + (n-1) + "]");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: правая граница должна быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (aRight == -1) break;

                        double average = fenwick.rangeAverage(aLeft, aRight);
                        System.out.printf("Среднее арифметическое на отрезке [" + aLeft + ".." + aRight + "]: %.2f\n", average);
                        break;

                    case 9:

                        int inversions = fenwick.countInversions(currentArray);
                        System.out.println("Количество инверсий в массиве: " + inversions);
                        break;

                    case 10:
                        System.out.print("Введите количество элементов в случайном массиве: ");
                        int rSize = 0;
                        while (true) {
                            try {
                                rSize = scanner.nextInt();
                                if (rSize <= 0) {
                                    System.out.print("Количество элементов должно быть натуральным числом! Введите снова: ");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.print("Ошибка: введите целое число! Введите снова: ");
                                scanner.next();
                            }

                        }
                        int[] rArray = new int[rSize];
                        for (int i = 0; i < rSize; i++) {
                            rArray[i] = random.nextInt(201) - 100; // числа от -100 до 100
                        }

                        fenwick.build(rArray);
                        currentArray = rArray.clone();
                        n = currentArray.length;

                        System.out.print("Создан случайный массив: [");
                        for (int i = 0; i < currentArray.length; i++) {
                            System.out.print(currentArray[i]);
                            if (i < currentArray.length - 1) System.out.print(", ");
                        }
                        System.out.println("]");
                        break;


                    case 0:
                        running = false;
                        break;

                    default:
                        System.out.println("Неверный выбор операции!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + " !");
            }
        }

        scanner.close();
    }
}


