package top.academy;

import java.util.*;
import java.util.stream.*;

public class EvenOddPStreamSorter {

    public static List<Integer> sort(List<Integer> numbers) {
        List<Integer> evens = numbers.parallelStream()
                .filter(x -> x % 2 == 0)
                .sorted()
                .toList();

        List<Integer> odds = numbers.parallelStream()
                .filter(x -> x % 2 != 0)
                .sorted(Collections.reverseOrder())
                .toList();

        return Stream.concat(evens.stream(), odds.stream()).toList();
    }
}