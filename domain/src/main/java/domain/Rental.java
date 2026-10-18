package domain;

import java.time.LocalDateTime;

public class Rental {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;

    public Rental( // конструктор
            LocalDateTime startTime,
            LocalDateTime endTime,
            double price
    ) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
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