package top.academy;
import java.util.Arrays;

public class EOArraySort {

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
        Arrays.sort(evens);

        // Сортировка нечетных по убыванию
        Arrays.sort(odds);
        for (int i = 0; i < odds.length / 2; i++) {
            int temp = odds[i];
            odds[i] = odds[odds.length - 1 - i];
            odds[odds.length - 1 - i] = temp;
        }

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
}