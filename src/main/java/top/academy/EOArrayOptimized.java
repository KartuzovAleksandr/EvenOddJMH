package top.academy;

import java.util.Arrays;

public class EOArrayOptimized {
    public static int[] sort(int[] arr) {
        // Шаг 1: подсчитываем количество чётных и нечётных
        int evenCount = 0;
        for (int num : arr) {
            if ((num & 1) == 0) evenCount++;
        }
        int oddCount = arr.length - evenCount;

        // Шаг 2: выделяем точные массивы
        int[] evens = new int[evenCount];
        int[] odds = new int[oddCount];

        // Шаг 3: один проход — заполняем оба массива
        int ei = 0, oi = 0;
        for (int num : arr) {
            if ((num & 1) == 0) {
                evens[ei++] = num;
            } else {
                odds[oi++] = num;
            }
        }

        // Шаг 4: сортируем
        Arrays.sort(evens); // возрастание
        Arrays.sort(odds);  // сначала по возрастанию

        // Шаг 5: копируем в результат: чётные + нечётные (в обратном порядке)
        int[] result = new int[arr.length];
        System.arraycopy(evens, 0, result, 0, evenCount);

        // Копируем нечётные в обратном порядке (без создания нового массива!)
        for (int i = 0; i < oddCount; i++) {
            result[evenCount + i] = odds[oddCount - 1 - i];
        }

        return result;
    }
}
