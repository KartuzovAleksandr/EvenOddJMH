package top.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EOList {

    public static List<Integer> sort(List<Integer> numbers) {
        // Создание списков для четных и нечетных чисел
        List<Integer> evenNumbers = new ArrayList<>(numbers);
        List<Integer> oddNumbers = new ArrayList<>(numbers);
//         removeIf очень медленный, поможет параллелизм
//         Удаление нечетных чисел из evenNumbers и четных из oddNumbers
//         в 2 потока
        var filterEven = CompletableFuture.runAsync(() -> evenNumbers.removeIf(n -> (n & 1) != 0));
        var filterOdd  = CompletableFuture.runAsync(() -> oddNumbers.removeIf(n -> (n & 1) == 0));
        CompletableFuture.allOf(filterEven, filterOdd).join(); // ← ОБЯЗАТЕЛЬНО!
//         сортировка в 2 потока
        var evenFut = CompletableFuture.runAsync(() -> evenNumbers.sort(null));
        var oddFut  = CompletableFuture.runAsync(() -> oddNumbers.sort(Collections.reverseOrder()));
        CompletableFuture.allOf(evenFut, oddFut).join();

        // добавление отсортированных чисел нечетных к четным
        // так быстрее чем делать новый список
        evenNumbers.addAll(oddNumbers);
        return evenNumbers;
    }
}