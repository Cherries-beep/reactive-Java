import java.time.LocalDateTime;
import java.util.List;

public class Car {

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