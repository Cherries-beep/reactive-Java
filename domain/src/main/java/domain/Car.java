package domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Car {

    private final UUID id = UUID.randomUUID();
    private int mileage;
    private String model;
    private LocalDateTime lastMaintenance;
    private CarStatus status;
    private CarInfo info;
    private List<Rental> rentals;
    private Location location;

    public Car(
            int mileage,
            String model,
            LocalDateTime lastMaintenance,
            CarStatus status,
            CarInfo info,
            List<Rental> rentals,
            Location location
    ) {
        this.mileage = mileage;
        this.model = model;
        this.lastMaintenance = lastMaintenance;
        this.status = status;
        this.info = info;
        this.rentals = rentals;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public int getMileage() {
        return mileage;
    }

    public String getModel() {
        return model;
    }

    public LocalDateTime getLastMaintenance() {
        return lastMaintenance;
    }

    public CarStatus getStatus() {
        return status;
    }

    public CarInfo getInfo() {
        return info;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public Location getLocation() {
        return location;
    }
}
