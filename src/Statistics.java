import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

public class Statistics {

    public static void calculateWithLoop(List<Car> cars) {

        Map<CarStatus, Integer> statusCounts =
                new EnumMap<>(CarStatus.class);

        for (CarStatus status : CarStatus.values()) {
            statusCounts.put(status, 0);
        }

        long totalMileage = 0;

        for (Car car : cars) {
            CarStatus status = car.getStatus();

            statusCounts.put(
                    status,
                    statusCounts.get(status) + 1
            );

            totalMileage += car.getMileage();
        }

        double averageMileage =
                (double) totalMileage / cars.size();

        System.out.println("Статистика через цикл:");
        System.out.println("AVAILABLE: "
                + statusCounts.get(CarStatus.AVAILABLE));
        System.out.println("RENTED: "
                + statusCounts.get(CarStatus.RENTED));
        System.out.println("MAINTENANCE: "
                + statusCounts.get(CarStatus.MAINTENANCE));
        System.out.println("Средний пробег: "
                + averageMileage);
    }

    public static void calculateWithStream(List<Car> cars) {

        Map<CarStatus, Long> statusCounts =
                cars.stream()
                        .collect(
                                java.util.stream.Collectors.groupingBy(
                                        Car::getStatus,
                                        java.util.stream.Collectors.counting()
                                )
                        );

        System.out.println("Статистика через Stream API:");
        System.out.println("AVAILABLE: "
                + statusCounts.getOrDefault(CarStatus.AVAILABLE, 0L));
        System.out.println("RENTED: "
                + statusCounts.getOrDefault(CarStatus.RENTED, 0L));
        System.out.println("MAINTENANCE: "
                + statusCounts.getOrDefault(CarStatus.MAINTENANCE, 0L));

                
        double averageMileage =
        cars.stream()
                .mapToInt(Car::getMileage)
                .average()
                .orElse(0);

        System.out.println("Средний пробег: " + averageMileage);
    }


    public static void calculateWithOwnCollector(List<Car> cars) {

        CarStatistics statistics = cars.stream()
                .collect(Collector.of(
                        CarStatistics::new,
                        CarStatistics::add,
                        (first, second) -> {

                                for (CarStatus status : CarStatus.values()) {

                                int count =
                                        first.getStatusCount(status)
                                        + second.getStatusCount(status);

                                first.setStatusCount(status, count);
                                }

                                first.addMileage(
                                        second.getTotalMileage()
                                );

                                first.addCarCount(
                                        second.getCarCount()
                                );

                                return first;
                        }
                ));

        System.out.println("Статистика через собственный Collector:");

        System.out.println("AVAILABLE: "
                + statistics.getStatusCount(CarStatus.AVAILABLE));

        System.out.println("RENTED: "
                + statistics.getStatusCount(CarStatus.RENTED));

        System.out.println("MAINTENANCE: "
                + statistics.getStatusCount(CarStatus.MAINTENANCE));

        System.out.println("Средний пробег: "
                + statistics.getAverageMileage());
        }
   
    public static void measureTime(int size) {

        List<Car> cars = CarGenerator.generate(size);

        long start;
        long end;

        start = System.nanoTime();
        calculateWithLoop(cars);
        end = System.nanoTime();

        System.out.println("Время цикла: "
                + (end - start) / 1_000_000.0 + " мс");

        start = System.nanoTime();
        calculateWithStream(cars);
        end = System.nanoTime();

        System.out.println("Время Stream API: "
                + (end - start) / 1_000_000.0 + " мс");

        start = System.nanoTime();
        calculateWithOwnCollector(cars);
        end = System.nanoTime();

        System.out.println("Время собственного Collector: "
                + (end - start) / 1_000_000.0 + " мс");
        }
}