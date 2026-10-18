package lab2;

import domain.Rental;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Statistics {

    public static StatisticsResult sequential(List<Rental> rentals, long delayMillis) {
        return rentals.stream().collect(Collectors.teeing(
                Collectors.groupingBy(
                        rental -> rental.getCarId(delayMillis),
                        Collectors.counting()),
                Collectors.averagingDouble(Rental::getPrice),
                StatisticsResult::new
        ));
    }

    public static StatisticsResult parallel(List<Rental> rentals, long delayMillis) {
        return rentals.parallelStream().collect(Collectors.teeing(
                Collectors.groupingByConcurrent(
                        rental -> rental.getCarId(delayMillis),
                        Collectors.counting()),
                Collectors.averagingDouble(Rental::getPrice),
                StatisticsResult::new
        ));
    }

    public static StatisticsResult forkJoin(List<Rental> rentals, long delayMillis, ForkJoinPool pool) {
        return pool.submit(() -> parallel(rentals, delayMillis)).join();
    }
}
