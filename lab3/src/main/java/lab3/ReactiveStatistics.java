package lab3;

import domain.Rental;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Scheduler;
import lab2.StatisticsResult;

import java.util.List;

public final class ReactiveStatistics {
    private ReactiveStatistics() {
    }

    public static StatisticsResult calculate(List<Rental> rentals, long delayMillis,
                                             Scheduler scheduler, int parallelism) {
        return Observable.fromIterable(rentals)
                .flatMap(rental -> Single.fromCallable(() ->
                        new StatisticsAccumulator.RentalData(
                                rental.getCarId(delayMillis), rental.getPrice()))
                        .subscribeOn(scheduler).toObservable(), false, parallelism)
                .collect(StatisticsAccumulator::new, StatisticsAccumulator::add)
                .map(StatisticsAccumulator::result)
                .blockingGet();
    }
}
