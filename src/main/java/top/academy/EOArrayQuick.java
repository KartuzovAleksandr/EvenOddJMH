package top.academy;

import java.util.Arrays;

public class EOArrayQuick {

    public static int[] sort(int[] arr) {
        int n = arr.length;
        int[] evens = new int[n];
        int[] odds = new int[n];
        int evenCount = 0, oddCount = 0;

        for (int num : arr) {
            if (num % 2 == 0) {
                evens[evenCount++] = num;
            } else {
                odds[oddCount++] = num;
            }
        }

        // Уменьшаем размер массивов
        evens = Arrays.copyOf(evens, evenCount);
        odds = Arrays.copyOf(odds, oddCount);

        // Сортировка четных по возрастанию
        quicksort(evens, 0, evens.length - 1, false); // по возрастанию
        // Сортировка нечетных по убыванию
        quicksort(odds, 0, odds.length - 1, true);   // по убыванию

        // Объединение
        int[] result = new int[n];
        int index = 0;
        for (int i = 0; i < evenCount; i++) {
            result[index++] = evens[i];
        }
        for (int i = 0; i < oddCount; i++) {
            result[index++] = odds[i];
        }

        return result;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    // Универсальная быстрая сортировка
    public static void quicksort(int[] arr, int left, int right, boolean desc) {
        if (left >= right) return;

        int pivot = arr[(left + right) / 2];
        int i = left, j = right;

        while (i <= j) {
            if (!desc) {
                while (arr[i] < pivot) i++;
                while (arr[j] > pivot) j--;
            } else {
                while (arr[i] > pivot) i++;
                while (arr[j] < pivot) j--;
            }

            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        quicksort(arr, left, j, desc);
        quicksort(arr, i, right, desc);
    }
}