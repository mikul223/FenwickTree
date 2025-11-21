//git push origin main

package main.java;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

public class Main {

    private static final int EXIT_CODE = -1;
    private static final int MIN_ARRAY= 1;
    private static final int MIN_INDEX = 0;
    private static final int RANDOM_MIN = -100;
    private static final int RANDOM_RANGE = 201; //100+100+1

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FenwickTree<Integer> fenwick = new FenwickTree<>();
        Integer[] currentArray = null;
        Random random = new Random();

        System.out.print("Введите количество элементов массива: ");
        int n = 0;
        while (true) {
            try {
                n = scanner.nextInt();
                if (n < MIN_ARRAY) {
                    System.out.print("Количество элементов должно быть натуральным числом! Введите снова: ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Ошибка: введите целое число! Введите снова: ");
                scanner.next();
            }
        }

        Integer[] array = new Integer[n];

        System.out.println("Введите элементы массива");
        for (int i = MIN_INDEX; i < n; i++) {
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


            int choice = MIN_INDEX;
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
                            for (int i = MIN_INDEX; i < currentArray.length; i++) {
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
                                System.out.print("Введите индекс (" + MIN_INDEX + "-" + (n-1) + ") или -1 для отмены операции: ");
                                int prefixIndex = scanner.nextInt();
                                if (prefixIndex == EXIT_CODE) break;
                                System.out.println("Префиксная сумма [" + MIN_INDEX + ".." + prefixIndex + "]: " + fenwick.prefixSum(prefixIndex));
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
                        System.out.print("Введите левую границу (" + MIN_INDEX + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                        int left = MIN_INDEX;
                        while (true) {
                            try {
                                left = scanner.nextInt();
                                if (left == EXIT_CODE) break;
                                if (left < MIN_INDEX || left >= n) {
                                    System.out.println("Ошибка: левая граница должна быть в диапазоне [" + MIN_INDEX + ", " + (n-1) + "]");
                                    System.out.print("Введите левую границу (" + MIN_INDEX + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: левая граница должна быть целым числом!");
                                scanner.next();
                                System.out.print("Введите левую границу (" + MIN_INDEX +"-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                            }
                        }
                        if (left == EXIT_CODE) break;


                        int right = MIN_INDEX;
                        while (true){
                            try{
                                System.out.print("Введите правую границу (" + left + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                                right = scanner.nextInt();
                                if (right == EXIT_CODE) break;
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
                        if (right == EXIT_CODE) break;
                        System.out.println("Сумма на отрезке [" + left + ".." + right + "]: " + fenwick.rangeSum(left, right));
                        break;

                    case 5:

                        int updateIndex = MIN_INDEX;
                        while (true){
                            try{
                                System.out.print("Введите индекс элемента для обновления (" + MIN_INDEX + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции:  ");
                                updateIndex = scanner.nextInt();
                                if (updateIndex == EXIT_CODE) break;
                                if (updateIndex < MIN_INDEX || updateIndex >= n) {
                                    System.out.println("Ошибка: индекс должен быть в диапазоне [" + MIN_INDEX + ", " + (n-1) + "]!");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (updateIndex == EXIT_CODE) break;

                        int delta = MIN_INDEX;
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
                        int newValue = MIN_INDEX;
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

                        int insertIndex = MIN_INDEX;
                        while (true) {
                            try {
                                System.out.print("Введите индекс для вставки (" + MIN_INDEX + "-" + currentArray.length + ") или " + EXIT_CODE + " для отмены операции: ");
                                insertIndex = scanner.nextInt();
                                if (insertIndex == EXIT_CODE) break;
                                if (insertIndex < MIN_INDEX || insertIndex > currentArray.length) {
                                    System.out.println("Ошибка: индекс должен быть в диапазоне [" + MIN_INDEX + ", " + currentArray.length + "]!");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (insertIndex == EXIT_CODE) break;

                        //Новый массив, на элемент больше
                        Integer[] newArray = new Integer[currentArray.length + 1];

                        // Копируем элементы до индекса вставки, вставляем новый элемент, копируем со сдвигом оставшееся
                        for (int i = MIN_INDEX; i < insertIndex; i++) {
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
                        if (currentArray.length == MIN_INDEX) {
                            System.out.println("Массив пуст!");
                            break;
                        }

                        int removeIndex = MIN_INDEX;
                        while (true) {
                            try {
                                System.out.print("Введите индекс элемента для удаления (" + MIN_INDEX + "-" + (currentArray.length-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                                removeIndex = scanner.nextInt();
                                if (removeIndex == EXIT_CODE) break;
                                if (removeIndex < MIN_INDEX || removeIndex >= currentArray.length) {
                                    System.out.println("Ошибка: индекс должен быть в диапазоне [" + MIN_INDEX + ", " + (currentArray.length-1) + "]!");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: индекс должен быть целым числом!");
                                scanner.next();
                            }
                        }
                        if (removeIndex == EXIT_CODE) break;

                        // Новый массив на элемент меньше
                        Integer[] reducedArray = new Integer[currentArray.length - 1];

                        for (int i = MIN_INDEX, j = MIN_INDEX; i < currentArray.length; i++) {
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
                        System.out.print("Введите левую границу (" + MIN_INDEX + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                        int aLeft = MIN_INDEX;
                        while (true) {
                            try {
                                aLeft = scanner.nextInt();
                                if (aLeft == EXIT_CODE) break;
                                if (aLeft < MIN_INDEX || aLeft >= n) {
                                    System.out.println("Ошибка: левая граница должна быть в диапазоне [" + MIN_INDEX + ", " + (n-1) + "]");
                                    System.out.print("Введите левую границу (" + MIN_INDEX + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Ошибка: левая граница должна быть целым числом!");
                                System.out.print("Введите левую границу (" + MIN_INDEX + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                                scanner.next();
                            }
                        }
                        if (aLeft == EXIT_CODE) break;

                        int aRight = MIN_INDEX;
                        while (true){
                            try{
                                System.out.print("Введите правую границу (" + aLeft + "-" + (n-1) + ") или " + EXIT_CODE + " для отмены операции: ");
                                aRight = scanner.nextInt();
                                if (aRight == EXIT_CODE) break;
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
                        if (aRight == EXIT_CODE) break;

                        double average = fenwick.rangeAverage(aLeft, aRight);
                        System.out.printf("Среднее арифметическое на отрезке [" + aLeft + ".." + aRight + "]: %.2f\n", average);
                        break;

                    case 9:

                        int inversions = fenwick.countInversions(currentArray);
                        System.out.println("Количество инверсий в массиве: " + inversions);
                        break;

                    case 10:
                        System.out.print("Введите количество элементов в случайном массиве: ");
                        int rSize = MIN_INDEX;
                        while (true) {
                            try {
                                rSize = scanner.nextInt();
                                if (rSize <= MIN_ARRAY) {
                                    System.out.print("Количество элементов должно быть натуральным числом! Введите снова: ");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.print("Ошибка: введите целое число! Введите снова: ");
                                scanner.next();
                            }

                        }
                        Integer[] rArray = new Integer[rSize];
                        for (int i = MIN_INDEX; i < rSize; i++) {
                            rArray[i] = random.nextInt(RANDOM_RANGE) + RANDOM_MIN;
                        }

                        fenwick.build(rArray);
                        currentArray = rArray.clone();
                        n = currentArray.length;

                        System.out.print("Создан случайный массив: [");
                        for (int i = MIN_INDEX; i < currentArray.length; i++) {
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


