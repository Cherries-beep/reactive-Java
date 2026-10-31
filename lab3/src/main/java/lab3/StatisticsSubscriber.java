package lab3;

import domain.Rental;
import lab2.StatisticsResult;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CompletableFuture;

public final class StatisticsSubscriber implements Subscriber<Rental> {
    private final int batchSize;
    private final StatisticsAccumulator accumulator = new StatisticsAccumulator();
    private final CompletableFuture<StatisticsResult> result = new CompletableFuture<>();
    private Subscription subscription;
    private int received;

    public StatisticsSubscriber(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(batchSize);
    }

    @Override
    public void onNext(Rental rental) {
        accumulator.add(rental);
        if (++received == batchSize) {
            received = 0;
            subscription.request(batchSize);
        }
    }

    @Override
    public void onError(Throwable error) {
        result.completeExceptionally(error);
    }

    @Override
    public void onComplete() {
        result.complete(accumulator.result());
    }

    public CompletableFuture<StatisticsResult> result() {
        return result;
    }
}
