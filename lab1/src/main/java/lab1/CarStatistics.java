package lab1;

import domain.Car;
import domain.CarStatus;

import java.util.EnumMap;
import java.util.Map;

public class CarStatistics {

    private Map<CarStatus, Long> statusCounts;
    private long totalMileage;
    private int carCount;

    public CarStatistics() {
        statusCounts = new EnumMap<>(CarStatus.class);

        for (CarStatus status : CarStatus.values()) {
            statusCounts.put(status, 0L);
        }

        totalMileage = 0;
        carCount = 0;
    }

    public void add(Car car) {

        CarStatus status = car.getStatus();

        statusCounts.put(
                status,
                statusCounts.get(status) + 1
        );

        totalMileage += car.getMileage();

        carCount++;
    }

    public double getAverageMileage() {

        if (carCount == 0) {
            return 0;
        }

        return (double) totalMileage / carCount;
    }

    public long getStatusCount(CarStatus status) {
        return statusCounts.get(status);
    }

    public void setStatusCount(CarStatus status, long count) {
        statusCounts.put(status, count);
    }

    public void addMileage(long mileage) {
        totalMileage += mileage;
    }

    public void addCarCount(int count) {
        carCount += count;
    }

    public long getTotalMileage() {
        return totalMileage;
    }

    public int getCarCount() {
        return carCount;
    }

    public Map<CarStatus, Long> getStatusCounts() {
        return statusCounts;
    }
}