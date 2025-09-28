package top.academy;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class EOForkRecursiveTask extends RecursiveTask<List<List<Integer>>> {

    private final List<Integer> chunk;

    public EOForkRecursiveTask(List<Integer> chunk) {
        this.chunk = chunk;
    }

    @Override
    protected List<List<Integer>> compute() {
        // chunk -> [evens, odds]
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();

        for (int x : chunk) {
            if ((x & 1) == 0) {
                evens.add(x);
            } else {
                odds.add(x);
            }
        }

        return Arrays.asList(evens, odds);
    }

    public static List<Integer> sort(List<Integer> data) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        int size = data.size();
        if (size == 0) return List.of();

        // Разбиваем на nThreads чанков
        List<EOForkRecursiveTask> tasks = new ArrayList<>(nThreads);
        int chunkSize = (size + nThreads - 1) / nThreads; // ceil division

        for (int i = 0; i < nThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, size);
            if (start >= size) break;
            EOForkRecursiveTask task = new EOForkRecursiveTask(data.subList(start, end));
            tasks.add(task);
            task.fork();
        }

        // Собираем результаты
        List<Integer> allEvens = new ArrayList<>();
        List<Integer> allOdds = new ArrayList<>();

        for (EOForkRecursiveTask task : tasks) {
            List<List<Integer>> pair = task.join();
            allEvens.addAll(pair.get(0));
            allOdds.addAll(pair.get(1));
        }

        // Глобальная сортировка
        allEvens.sort(null); // по возрастанию
        allOdds.sort(Collections.reverseOrder()); // по убыванию

        // Объединение
        List<Integer> result = new ArrayList<>(allEvens);
        result.addAll(allOdds);
        return result;
    }
}