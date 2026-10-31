package lab3;

import domain.Rental;
import domain.User;
import domain.UserGenerator;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class RentalFlow {
    private RentalFlow() {
    }

    public static Flowable<Rental> generate(int count, long seed, Scheduler scheduler) {
        return Flowable.<Rental, State>generate(() -> new State(count, seed), (state, emitter) -> {
            if (state.index == count) {
                emitter.onComplete();
            } else {
                emitter.onNext(state.next());
                if (state.index == count) {
                    emitter.onComplete();
                }
            }
            return state;
        }).subscribeOn(scheduler);
    }

    private static final class State {
        private final Random random;
        private final List<UUID> carIds;
        private final List<User> users;
        private int index;

        private State(int count, long seed) {
            random = new Random(seed);
            int carCount = Math.min(count, 100);
            carIds = new ArrayList<>(carCount);
            for (int i = 0; i < carCount; i++) {
                carIds.add(new UUID(random.nextLong(), random.nextLong()));
            }
            users = UserGenerator.generate(Math.min(count, 100));
        }

        private Rental next() {
            int carCount = carIds.size();
            LocalDateTime start = LocalDateTime.of(2026, 1, 1, 0, 0)
                    .plusDays(index / carCount);
            int durationMinutes = 30 + random.nextInt(180);
            Rental rental = new Rental(
                    users.get(random.nextInt(users.size())),
                    carIds.get(index % carCount),
                    start,
                    start.plusMinutes(durationMinutes),
                    300 + random.nextInt(1701),
                    random.nextInt(durationMinutes + 1)
            );
            index++;
            return rental;
        }
    }
}
