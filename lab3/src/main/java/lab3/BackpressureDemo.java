package lab3;

import io.reactivex.rxjava3.schedulers.Schedulers;
import lab2.StatisticsResult;

public final class BackpressureDemo {
    private BackpressureDemo() {
    }

    public static void main(String[] args) {
        int count = args.length == 0 ? 150_000 : Integer.parseInt(args[0]);
        StatisticsSubscriber subscriber = new StatisticsSubscriber(256);
        long start = System.nanoTime();
        RentalFlow.generate(count, 42, Schedulers.computation()).subscribe(subscriber);
        StatisticsResult result = subscriber.result().join();
        long processed = result.rentalCounts().values().stream().mapToLong(Long::longValue).sum();
        if (processed != count) {
            throw new IllegalStateException("Expected " + count + " rentals, got " + processed);
        }
        System.out.printf("rentals=%d cars=%d averagePrice=%.2f timeMs=%.3f%n",
                processed, result.rentalCounts().size(), result.averagePrice(),
                (System.nanoTime() - start) / 1_000_000.0);
    }
}
