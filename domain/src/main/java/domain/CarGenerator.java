package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CarGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] MODELS = {
            "BMW 3 Series",
            "Kia Rio",
            "Hyundai Solaris",
            "Volkswagen Polo",
            "Skoda Octavia"
    };

    private static final String[] DISTRICTS = {
            "Petrogradsky",
            "Vasileostrovsky",
            "Moskovsky",
            "Central",
            "Primorsky"
    };

    public static List<Car> generate(int count) {

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            int mileage = RANDOM.nextInt(200_000);

            String model = MODELS[
                    RANDOM.nextInt(MODELS.length)
            ];

            LocalDateTime lastMaintenance =
                    LocalDateTime.now().minusDays(
                            RANDOM.nextInt(365)
                    );

            CarStatus status =
                    CarStatus.values()[
                            RANDOM.nextInt(CarStatus.values().length)
                    ];

            CarInfo info = new CarInfo(
                    2018 + RANDOM.nextInt(9),
                    100 + RANDOM.nextInt(151),
                    RANDOM.nextBoolean() ? "petrol" : "hybrid"
            );

            Location location = new Location(
                    "Saint Petersburg",
                    DISTRICTS[RANDOM.nextInt(DISTRICTS.length)]
            );

            List<Rental> rentals = new ArrayList<>();

            Car car = new Car(
                    mileage,
                    model,
                    lastMaintenance,
                    status,
                    info,
                    rentals,
                    location
            );

            rentals.addAll(generateRentals(car.getId(), mileage));
            cars.add(car);
        }

        return cars;
    }

    private static List<Rental> generateRentals(UUID carId, int mileage) {

        List<Rental> rentals = new ArrayList<>();

        int count = RANDOM.nextInt(6);

        int remainingMileage = mileage;
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < count; i++) {

            LocalDateTime start =
                    now.minusDays(count - i).minusMinutes(RANDOM.nextInt(60));

            LocalDateTime end =
                    start.plusMinutes(
                            30 + RANDOM.nextInt(180)
                    );

            double price =
                    300 + RANDOM.nextInt(1701);

            int distanceKm = RANDOM.nextInt(Math.min(remainingMileage, 100) + 1);
            remainingMileage -= distanceKm;

            rentals.add(
                    new Rental(UserGenerator.generate(), carId, start, end, price, distanceKm)
            );
        }

        return rentals;
    }
}
