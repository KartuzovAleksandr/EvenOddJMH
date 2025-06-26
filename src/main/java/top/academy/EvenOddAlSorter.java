package top.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvenOddAlSorter {

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

        // Очистка исходного списка и добавление отсортированных чисел
        numbers.clear();
        numbers.addAll(evenNumbers);
        numbers.addAll(oddNumbers);

        return numbers;
    }
}