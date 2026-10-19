package domain;

import java.time.LocalDateTime;
import java.util.UUID;

// Факт завершённой аренды. Время, стоимость и расстояние относятся к одной поездке.
public class Rental {

    private final UUID id = UUID.randomUUID();
    private final User user;
    private final UUID carId;
    private final int distanceKm;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final double price;

    public Rental(
            User user,
            UUID carId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            double price,
            int distanceKm
    ) {
        this.user = user;
        this.carId = carId;
        this.distanceKm = distanceKm;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public UUID getCarId() {
        return carId;
    }

    public UUID getCarId(long delayMillis) {
        if (delayMillis > 0) {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                throw new IllegalStateException("Rental retrieval interrupted", e);
            }
        }
        return carId;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getPrice() {
        return price;
    }
}
