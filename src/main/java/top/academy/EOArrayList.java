package top.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EOArrayList {

    public static List<Integer> sort(List<Integer> numbers) {
        // Создание списков для четных и нечетных чисел
        List<Integer> evenNumbers = new ArrayList<>(numbers);
        List<Integer> oddNumbers = new ArrayList<>(numbers);

        // Удаление нечетных чисел из evenNumbers и четных из oddNumbers
        evenNumbers.removeIf(n -> n % 2 != 0);
        oddNumbers.removeIf(n -> n % 2 == 0);

        // Сортировка четных по возрастанию
        Collections.sort(evenNumbers);
        // Сортировка нечетных по убыванию
        oddNumbers.sort(Comparator.reverseOrder());

        // добавление отсортированных чисел
        List<Integer> Numbers = new ArrayList<>(evenNumbers);
        Numbers.addAll(evenNumbers);

        return Numbers;
    }
}