# Запуск бенчмарков

Требуется JDK 21. Команды выполняются из корня проекта.

Все бенчмарки со всеми параметрами из аннотаций:

```bash
./gradlew :lab2:run
```

Все три варианта для 1000 аренд без задержки:

```bash
./gradlew :lab2:run --args="lab2.StatisticsBenchmark -p size=1000 -p delayMillis=0"
```

Только последовательный Stream с задержкой 1 мс на аренду:

```bash
./gradlew :lab2:run --args="lab2.StatisticsBenchmark.sequential -p size=1000 -p delayMillis=1"
```

В IntelliJ IDEA можно запустить Gradle-задачу `lab2 → Tasks → application → run`.
Итоговая сводка JMH показывает среднее время обработки всей коллекции в `ms/op`.
