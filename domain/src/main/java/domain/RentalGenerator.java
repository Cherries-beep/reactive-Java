package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RentalGenerator {

    public static List<Rental> generate(int count) {
        return generate(count, new Random().nextLong());
    }

    public static List<Rental> generate(int count, long seed) {
        List<Rental> rentals = new ArrayList<>(count);
        if (count == 0) {
            return rentals;
        }
        Random random = new Random(seed);
        int carCount = Math.min(count, 100);
        List<UUID> carIds = new ArrayList<>(carCount);
        for (int i = 0; i < carCount; i++) {
            carIds.add(new UUID(random.nextLong(), random.nextLong()));
        }
        List<User> users = UserGenerator.generate(Math.min(count, 100));
        LocalDateTime firstDay = LocalDateTime.now().minusDays(count / carCount + 2L);
        for (int i = 0; i < count; i++) {
            LocalDateTime start = firstDay.plusDays(i / carCount);
            int durationMinutes = 30 + random.nextInt(180);
            rentals.add(new Rental(
                    users.get(random.nextInt(users.size())),
                    carIds.get(i % carCount),
                    start,
                    start.plusMinutes(durationMinutes),
                    300 + random.nextInt(1701),
                    random.nextInt(durationMinutes + 1)
            ));
        }
        return rentals;
    }
}
