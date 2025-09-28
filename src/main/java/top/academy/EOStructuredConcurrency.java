package top.academy;

import java.util.*;
import java.util.concurrent.*;

public class EOStructuredConcurrency {

    public static List<Integer> sort(List<Integer> numbers) throws Exception {
        List<Integer> evens = null;
        List<Integer> odds = null;

        try (var scope = StructuredTaskScope.open()) {
            // fork() возвращает Subtask, НЕ Future!
            var evenSubtask = scope.fork(() ->
                    numbers.stream()
                            .filter(x -> (x & 1) == 0)
                            .sorted()
                            .toList()
            );

            var oddSubtask = scope.fork(() ->
                    numbers.stream()
                            .filter(x -> (x & 1) != 0)
                            .sorted(Collections.reverseOrder())
                            .toList()
            );

            scope.join(); // ждём все задачи

            // Получаем результаты через .get()
            evens = evenSubtask.get();
            odds = oddSubtask.get();
        }

        List<Integer> result = new ArrayList<>(evens.size() + odds.size());
        result.addAll(evens);
        result.addAll(odds);
        return result;
    }
}