package lab2;

import java.util.Map;
import java.util.UUID;

public record StatisticsResult(Map<UUID, Long> rentalCounts, double averagePrice) {
}
