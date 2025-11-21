package main.java;

public class FenwickTree<T extends Number>{
    private long[] tree;
    private int size;

    private static final int INDEX_OFFSET = 1;
    private static final int EMPTY_ARRAY = 0;
    private static final int MIN_INDEX = 0;
    private static final int INVERSION_TREE = 2;

    //Коллекция для хранения истории операций
    private DynamicArray<String> operationHistory;

    public FenwickTree() {
        this.operationHistory = new DynamicArray<>();
    }

    //Построение дерева из массива с проверкой на 0 и исключений. Элементы добавляются с помощью метода update
    public void build(T[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Массив пуст!");
        }

        this.size = arr.length;
        this.tree = new long[size + INDEX_OFFSET]; // 1-индексация

        long[] prefix = new long[size + INDEX_OFFSET];
        for (int i = 0; i < size; i++) {
            prefix[i + INDEX_OFFSET] = prefix[i + INDEX_OFFSET - 1] + arr[i].longValue();
        }
        for (int i = INDEX_OFFSET; i <= size; i++) {
            int lsb = i & -i;
            tree[i] = prefix[i] - prefix[i - lsb];
        }
        operationHistory.add("Построено дерево из массива размером " + size);
    }

    //Метод обновления элемента.  Добавление дельты к элементу с заданным индексом, проверка индекса
    public void update(int index, T delta) {
        if (index < MIN_INDEX || index >= size) {
            throw new IllegalArgumentException("Индекс " + index + " выходит за пределы [" + MIN_INDEX + ", " + (size-1) + "]");
        }
        long deltaValue = delta.longValue();
        int i = index + INDEX_OFFSET;   // Переход к 1-индексации
        while (i <= size) {
            tree[i] += deltaValue;
            i += i & -i;    // Двигаемся вверх по дереву. "i & -i" - выделяет младший значащий бит числа
        }
        if (deltaValue < 0){
            operationHistory.add("Элемент с индексом " + index + " уменьшен на " + -1*deltaValue);
        }
        if (deltaValue > 0){
            operationHistory.add("Элемент с индексом " + index + " увеличен на " + delta);
        }

    }


    //Метод вычисления префиксной суммы от 0 до индекса, проверка индекса
    public long prefixSum(int index) {
        if (index < MIN_INDEX || index >= size) {
            throw new IllegalArgumentException("Индекс " + index + " выходит за пределы [" + MIN_INDEX + ", " + (size-1) + "]");
        }

        long sum = 0;
        int i = index + INDEX_OFFSET;
        while (i > 0) {
            sum += tree[i];
            i -= i & -i; // Двигаемся вниз по дереву
        }

        return sum;

    }

    //Метод вычисления суммы на отрезке, проверка границ отрезка
    public long rangeSum(int left, int right) {
        if (left < MIN_INDEX || right >= size || left > right) {
            throw new IllegalArgumentException("Неверный диапазон: [" + left + ", " + right + "]");
        }
        if (left == MIN_INDEX) {
            return prefixSum(right);
        } else {
            return prefixSum(right) - prefixSum(left - 1);
        }

    }

    //Метод вычисления среднего арифметического на отрезке, проверка границ отрезка
    public double rangeAverage(int left, int right) {
        if (left < MIN_INDEX || right >= size || left > right) {
            throw new IllegalArgumentException("Неверный диапазон: [" + left + ", " + right + "]");
        }

        long sum = rangeSum(left, right);
        int count = right - left + 1;
        double average = (double) sum / count;

        operationHistory.add("Вычислено среднее арифметическое на отрезке [" + left + ".." + right + "] = " + average );
        return average;
    }

    //Метод подсчета количества инверсий
    public <U extends Number> int countInversions(U[] arr) {
        if (arr == null || arr.length == EMPTY_ARRAY) {
            operationHistory.add("Найдено инверсий: 0");
            return 0;
        }
        long minElement = arr[0].longValue();
        long maxElement = arr[0].longValue();
        for (U num : arr) {
            long value = num.longValue();
            if (value > maxElement) {
                maxElement = value;
            }
            if (value < minElement) {
                minElement = value;
            }
        }

        //Сдвиг для устранения отрицательных значений
        long shift;
        if (minElement < 0) {
            shift = -minElement + 1;
        } else {
            shift = 0;
        }
        int treeSize = (int)(maxElement + shift) + INVERSION_TREE;

        // Временное дерево Фенвика для инверсий
        FenwickTree<Integer> invTree = new FenwickTree<>();
        Integer[] zeroArray = new Integer[treeSize];
        for (int i = 0; i < treeSize; i++) {
            zeroArray[i] = 0;
        }
        invTree.build(zeroArray);

        int inversions = 0;

        // Проходим по массиву в обратном порядке
        for (int i = arr.length - 1; i >= 0; i--) {
            int value = (int)(arr[i].longValue() + shift); // Применяем сдвиг
            if (value < MIN_INDEX) {
                continue;
            }
            int treeIndex = value + INDEX_OFFSET;
            if (treeIndex >= treeSize) {
                treeIndex = treeSize - 1; // Ограничиваем максимальным индексом
            }
            if (value > MIN_INDEX) {
                inversions += (int)invTree.prefixSum(value - 1);
            }
            invTree.update(treeIndex, 1);
        }
        operationHistory.add("Найдено инверсий: " + inversions );
        return inversions;
    }

    //Получение истории операций
    public DynamicArray<String> getOperationHistory() {
        return operationHistory;
    }


    //Формирование строки с массивом
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = INDEX_OFFSET; i <= size; i++) {
            sb.append(tree[i]);
            if (i < size) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}