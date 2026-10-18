package domain;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String fullName;
    private final String phoneNumber;
    private final LocalDate birthDate;
    private final String driverLicenseNumber;

    public User(UUID id, String fullName, String phoneNumber,
                LocalDate birthDate, String driverLicenseNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }
}
