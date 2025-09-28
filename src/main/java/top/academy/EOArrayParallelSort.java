package top.academy;
import java.util.Arrays;

public class EOArrayParallelSort {

    public static int[] sort(int[] arr) {
        int n = arr.length;
        int[] evens = new int[n];
        int[] odds = new int[n];
        int evenCount = 0, oddCount = 0;

        for (int num : arr) {
            if ((num & 1) == 0) { // это быстрее чем num %2 == 0
                evens[evenCount++] = num;
            } else {
                odds[oddCount++] = num;
            }
        }
        // Сортировка четных по возрастанию
        Arrays.parallelSort(evens, 0, evenCount);
        // Сортировка нечетных по убыванию
        Arrays.parallelSort(odds, 0, oddCount); // здесь не получается reverseOrder()
        // Заполняем результат: сначала чётные, потом нечётные (в обратном порядке)
        int[] result = new int[n];
        System.arraycopy(evens, 0, result, 0, evenCount);
        for (int i = 0; i < oddCount; i++) {
            result[evenCount + i] = odds[oddCount - 1 - i];
        }
        return result;
    }
}