package lab1;

import domain.Car;
import domain.CarGenerator;
import domain.CarStatus;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class Statistics {

    private static volatile Map<CarStatus, Long> statusCounts;
    private static volatile double averageMileage;

    public static void calculateWithLoop(List<Car> cars) {

        CarStatistics statistics = new CarStatistics();
        for (Car car : cars) {
            statistics.add(car);
        }
        statusCounts = statistics.getStatusCounts();
        averageMileage = statistics.getAverageMileage();
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

                
        double averageMileage =
        cars.stream()
                .mapToInt(Car::getMileage)
                .average()
                .orElse(0);

        Statistics.statusCounts = statusCounts;
        Statistics.averageMileage = averageMileage;
    }


    public static void calculateWithOwnCollector(List<Car> cars) {

        CarStatistics statistics = cars.stream()
                .collect(Collector.of(
                        CarStatistics::new,
                        CarStatistics::add,
                        (first, second) -> {

                            for (CarStatus status : CarStatus.values()) {

                                long count =
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

        statusCounts = statistics.getStatusCounts();
        averageMileage = statistics.getAverageMileage();
    }
   
    public static void measureTime(int size) {

        List<Car> cars = CarGenerator.generate(size);

        for (int i = 0; i < 20; i++) {
            calculateWithLoop(cars);
            calculateWithStream(cars);
            calculateWithOwnCollector(cars);
        }

        int repeats = 20;
        long totalLoop = 0;
        long totalStream = 0;
        long totalCollector = 0;

        for (int i = 0; i < repeats; i++) {
            switch (i % 3) {
                case 0:
                    totalLoop      += time(cars, Statistics::calculateWithLoop);
                    totalStream    += time(cars, Statistics::calculateWithStream);
                    totalCollector += time(cars, Statistics::calculateWithOwnCollector);
                    break;
                case 1:
                    totalStream    += time(cars, Statistics::calculateWithStream);
                    totalCollector += time(cars, Statistics::calculateWithOwnCollector);
                    totalLoop      += time(cars, Statistics::calculateWithLoop);
                    break;
                case 2:
                    totalCollector += time(cars, Statistics::calculateWithOwnCollector);
                    totalLoop      += time(cars, Statistics::calculateWithLoop);
                    totalStream    += time(cars, Statistics::calculateWithStream);
                    break;
            }
        }

        System.out.println("Время цикла: "
                + totalLoop      / 1_000_000.0 / repeats + " мс");
        System.out.println("Время Stream API: "
                + totalStream    / 1_000_000.0 / repeats + " мс");
        System.out.println("Время собственного Collector: "
                + totalCollector / 1_000_000.0 / repeats + " мс");
    }

    private static long time(List<Car> cars, Consumer<List<Car>> method) {
        long start = System.nanoTime();
        method.accept(cars);
        return System.nanoTime() - start;
    }
}