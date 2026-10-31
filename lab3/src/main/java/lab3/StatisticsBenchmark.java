package lab3;

import domain.Rental;
import domain.RentalGenerator;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lab2.Statistics;
import lab2.StatisticsResult;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@State(Scope.Benchmark)
public class StatisticsBenchmark {
    @Param({"500", "2500"})
    public int size;

    @Param({"64", "128"})
    public int parallelism;

    private List<Rental> rentals;
    private ForkJoinPool pool;
    private ExecutorService executor;
    private Scheduler scheduler;

    @Setup
    public void setup() {
        rentals = RentalGenerator.generate(size, 42);
        pool = new ForkJoinPool(parallelism);
        executor = Executors.newFixedThreadPool(parallelism);
        scheduler = Schedulers.from(executor);
    }

    @TearDown
    public void tearDown() {
        scheduler.shutdown();
        executor.shutdown();
        pool.close();
    }

    @Benchmark
    public StatisticsResult parallelStream() {
        return Statistics.forkJoin(rentals, 1, pool);
    }

    @Benchmark
    public StatisticsResult observable() {
        return ReactiveStatistics.calculate(rentals, 1, scheduler, parallelism);
    }
}
