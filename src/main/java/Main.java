package main.java;

public class Main {
    public static void main(String[] args) {
        //Тестовый массив
        int[] testArray = {1, 3, 5, 7, 9, 11};
        FenwickTree fenwick = new FenwickTree();
        fenwick.build(testArray);

        //Функционал

        System.out.println("Структура дерева: " + fenwick.toString());
        System.out.println("Префиксная сумма [0..3]: " + fenwick.prefixSum(3));
        System.out.println("Сумма на отрезке [1..4]: " + fenwick.rangeSum(1, 4));

        //После обновления
        fenwick.update(2, 2); // Добавляем 2 к элементу с индексом 2
        System.out.println("После обновления - Префиксная сумма [0..3]: " + fenwick.prefixSum(3));
        System.out.println("После обновления - Сумма на отрезке [1..4]: " + fenwick.rangeSum(1, 4));




        // Граничные случаи
        try {
            fenwick.update(-1, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Корректно перехвачен неверный индекс: " + e.getMessage());
        }

        try {
            fenwick.rangeSum(3, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Корректно перехвачен неверный диапазон: " + e.getMessage());
        }
    }
}