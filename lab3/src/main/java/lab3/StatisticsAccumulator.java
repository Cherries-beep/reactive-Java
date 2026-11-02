package lab3;

import domain.Rental;
import lab2.StatisticsResult;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final class StatisticsAccumulator {
    private final Map<UUID, Long> counts = new HashMap<>();
    private final DoubleSummaryStatistics prices = new DoubleSummaryStatistics();

    void add(Rental rental) {
        add(rental.getCarId(), rental.getPrice());
    }

    void add(RentalData rental) {
        add(rental.carId(), rental.price());
    }

    private void add(UUID carId, double price) {
        counts.merge(carId, 1L, Long::sum);
        prices.accept(price);
    }

    StatisticsResult result() {
        return new StatisticsResult(Map.copyOf(counts), prices.getAverage());
    }

    record RentalData(UUID carId, double price) {
    }
}
