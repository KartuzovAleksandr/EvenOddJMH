package top.academy;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.*;

public class EOStream {

    public static List<Integer> sort(List<Integer> numbers) {
        var evenFuture = CompletableFuture.supplyAsync(() ->
                numbers.stream()
                        .filter(x -> (x & 1) == 0)
                        .sorted()
                        .toList()
        );

        var oddFuture = CompletableFuture.supplyAsync(() ->
                numbers.stream()
                        .filter(x -> (x & 1) != 0)
                        .sorted(Collections.reverseOrder())
                        .toList()
        );

        var evens = evenFuture.join();
        var odds = oddFuture.join();

        List<Integer> result = new ArrayList<>(evens.size() + odds.size());
        result.addAll(evens);
        result.addAll(odds);
        return result;
        // был медленнее
//        List<Integer> evens = numbers.stream()
//                .filter(x -> (x & 1) == 0)
//                .sorted()
//                .toList();
//
//        List<Integer> odds = numbers.stream()
//                .filter(x -> (x & 1) != 0)
//                .sorted(Collections.reverseOrder())
//                .toList();
//
//        return Stream.concat(evens.stream(), odds.stream()).toList();
    }
}