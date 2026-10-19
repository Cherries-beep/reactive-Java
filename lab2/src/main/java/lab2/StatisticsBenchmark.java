package lab2;

import domain.Rental;
import domain.RentalGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(2)
@Threads(1)
@State(Scope.Benchmark)
public class StatisticsBenchmark {

    @Param({"1", "10", "100", "1000", "5000", "25000"})
    public int size;

    @Param({"0", "1"})
    public long delayMillis;

    private List<Rental> rentals;

    @Setup(Level.Trial)
    public void setup() {
        rentals = RentalGenerator.generate(size, 42);
    }

    @State(Scope.Benchmark)
    public static class ParallelismState {
        @Param({"15", "100"})
        public int parallelism;
    }

    @State(Scope.Benchmark)
    public static class PoolState {
        @Param({"15", "100"})
        public int parallelism;

        ForkJoinPool pool;

        @Setup(Level.Trial)
        public void setup() {
            pool = new ForkJoinPool(parallelism);
        }

        @TearDown(Level.Trial)
        public void tearDown() {
            pool.close();
        }
    }

    @Benchmark
    public StatisticsResult sequential() {
        return Statistics.sequential(rentals, delayMillis);
    }

    @Benchmark
    public StatisticsResult parallel() {
        return Statistics.parallel(rentals, delayMillis);
    }

    @Benchmark
    public StatisticsResult customPool(PoolState poolState) {
        return Statistics.forkJoin(rentals, delayMillis, poolState.pool);
    }
}
