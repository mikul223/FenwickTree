package main.java;

public class FenwickTree{
    private int[] tree;
    private int size;


    //Построение дерева из массива с проверкой на 0 и исключений. Элементы добавляются с помощью метода update
    public void build(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Массив пуст :(");
        }

        this.size = arr.length;
        this.tree = new int[size + 1];

        for (int i = 0; i < size; i++) {
            update(i, arr[i]);
        }
    }

    //Метод обновления элемента.  Добавление дельты к элементу с заданным индексом, проверка индекса
    public void update(int index, int delta) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Индекс " + index + " выходит за пределы [0, " + (size-1) + "]");
        }

        int i = index + 1;   // Переход к 1-индексации
        while (i <= size) {
            tree[i] += delta;
            i += i & -i;    // Двигаемся вверх по дереву. "i & -i" - выделяет младший значащий бит числа
        }
    }


    //Метод вычисления префиксной суммы от 0 до индекса, проверка индекса
    public int prefixSum(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Индекс " + index + " выходит за пределы [0, " + (size-1) + "]");
        }

        int sum = 0;
        int i = index + 1;
        while (i > 0) {
            sum += tree[i];
            i -= i & -i; // Двигаемся вниз по дереву
        }
        return sum;
    }

    //Метод вычисления суммы на отрезке, проверка границ отрезка
    public int rangeSum(int left, int right) {
        if (left < 0 || right >= size || left > right) {
            throw new IllegalArgumentException("Неверный диапазон: [" + left + ", " + right + "]");
        }

        if (left == 0) {
            return prefixSum(right);
        }
        return prefixSum(right) - prefixSum(left - 1);
    }

    //Метод вычисления среднего арифметического на отрезке, проверка границ отрезка
    public double rangeAverage(int left, int right) {
        if (left < 0 || right >= size || left > right) {
            throw new IllegalArgumentException("Неверный диапазон: [" + left + ", " + right + "]");
        }

        int sum = rangeSum(left, right);
        int count = right - left + 1;

        return (double) sum / count;
    }

    //Метод подсчета количества инверсий
    public int countInversions(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int maxElement = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxElement) {
                maxElement = arr[i];
            }
        }

        // Временное дерево Фенвика для инверсий
        FenwickTree invTree = new FenwickTree();
        invTree.size = maxElement + 1;
        invTree.tree = new int[invTree.size + 1];

        int inversions = 0;

        // Проходим по массиву в обратном порядке
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] > 0) {
                inversions += invTree.prefixSum(arr[i] - 1);
            }
            invTree.update(arr[i], 1);
        }

        return inversions;
    }




    //Формирование строки с массивом
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FenwickTree[");
        for (int i = 1; i <= size; i++) {
            sb.append(tree[i]);
            if (i < size) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}