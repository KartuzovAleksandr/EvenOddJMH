## Тестирование параллельных методов фильтрации и сортировки целых чисел

Выполняется выделение четных (сортировка по возрастанию) и нечетных (по убыванию)

- обычные массивы с QuickSort
- обычные массивы с ParallelSort
- коллекции ArrayList
- потоки
- параллельные потоки
- CompletableFuture
- ForkJoin
- ForkRecursiveTask
- виртуальные потоки StructuredTaskScope (preview)

Генерация 1.000.000 целых чисел от 0 до 5000

Сборка
mvn clean package

Запуск
java --enable-preview -jar target/benchmarks.jar -jvmArgs --enable-preview

Зеленой стрелочкой не будет работать preview features