package top.academy;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class EOForkJoinPool {

    // Вспомогательная задача: обрабатывает один чанк
    private static class PartitionTask extends RecursiveTask<List<List<Integer>>> {
        private final List<Integer> chunk;

        PartitionTask(List<Integer> chunk) {
            this.chunk = chunk;
        }

        @Override
        protected List<List<Integer>> compute() {
            List<Integer> evens = new ArrayList<>();
            List<Integer> odds = new ArrayList<>();

            // Предварительная оценка размера (опционально)
            int expectedSize = Math.max(chunk.size() / 2, 1);
            evens = new ArrayList<>(expectedSize);
            odds = new ArrayList<>(expectedSize);

            for (int x : chunk) {
                if ((x & 1) == 0) {
                    evens.add(x);
                } else {
                    odds.add(x);
                }
            }

            // Возвращаем пару: [evens, odds]
            return Arrays.asList(evens, odds);
        }
    }

    // Главная задача: координирует разбиение и сборку
    private static class SortTask extends RecursiveTask<List<Integer>> {
        private final List<Integer> data;

        SortTask(List<Integer> data) {
            this.data = data;
        }

        @Override
        protected List<Integer> compute() {
            int n = data.size();
            if (n == 0) {
                return List.of();
            }

            int nThreads = Runtime.getRuntime().availableProcessors();
            int chunkSize = (n + nThreads - 1) / nThreads; // ceil division

            List<PartitionTask> tasks = new ArrayList<>(nThreads);

            // Создаём и запускаем задачи
            for (int i = 0; i < nThreads; i++) {
                int start = i * chunkSize;
                if (start >= n) break;
                int end = Math.min(start + chunkSize, n);
                PartitionTask task = new PartitionTask(data.subList(start, end));
                tasks.add(task);
                task.fork();
            }

            // Собираем все чётные и нечётные
            List<Integer> allEvens = new ArrayList<>();
            List<Integer> allOdds = new ArrayList<>();

            for (PartitionTask task : tasks) {
                List<List<Integer>> result = task.join();
                allEvens.addAll(result.get(0));
                allOdds.addAll(result.get(1));
            }

            // Глобальная сортировка
            allEvens.sort(null); // natural order (ascending)
            allOdds.sort(Collections.reverseOrder()); // descending

            // Объединение
            List<Integer> finalResult = new ArrayList<>(allEvens.size() + allOdds.size());
            finalResult.addAll(allEvens);
            finalResult.addAll(allOdds);

            return finalResult;
        }
    }

    // Публичный метод для вызова
    public static List<Integer> sort(List<Integer> data) {
        if (data.isEmpty()) {
            return List.of();
        }
        return ForkJoinPool.commonPool().invoke(new SortTask(data));
    }
}