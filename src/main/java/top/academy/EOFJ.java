package top.academy;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class EOFJ extends RecursiveTask<List<Integer>> {

    private final List<Integer> data;

    public EOFJ(List<Integer> data) {
        this.data = data;
    }

    @Override
    protected List<Integer> compute() {
        if (data.size() <= 10_000) {
            List<Integer> evens = data.stream()
                    .filter(x -> x % 2 == 0)
                    .sorted()
                    .toList();

            List<Integer> odds = data.stream()
                    .filter(x -> x % 2 != 0)
                    .sorted(Collections.reverseOrder())
                    .toList();

            return Stream.concat(evens.stream(), odds.stream()).toList();
        }

        int mid = data.size() / 2;
        EOFJ left = new EOFJ(data.subList(0, mid));
        EOFJ right = new EOFJ(data.subList(mid, data.size()));

        left.fork();
        right.fork();

        List<Integer> leftResult = left.join();
        List<Integer> rightResult = right.join();

        return Stream.concat(leftResult.stream(), rightResult.stream()).toList();
    }

    public static List<Integer> sort(List<Integer> data) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new EOFJ(data));
    }
}