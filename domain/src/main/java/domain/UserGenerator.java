package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UserGenerator {

    private static final Random RANDOM = new Random();
    private static final String[] MALE_FIRST_NAMES = {
            "Александр", "Дмитрий", "Иван", "Михаил", "Алексей"
    };
    private static final String[] MALE_LAST_NAMES = {
            "Иванов", "Петров", "Соколов", "Смирнов", "Кузнецов"
    };
    private static final String[] FEMALE_FIRST_NAMES = {
            "Мария", "Анна", "Елена", "Ольга", "Екатерина"
    };
    private static final String[] FEMALE_LAST_NAMES = {
            "Иванова", "Петрова", "Соколова", "Смирнова", "Кузнецова"
    };

    public static List<User> generate(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(generate());
        }
        return users;
    }

    public static User generate() {
        boolean male = RANDOM.nextBoolean();
        String[] firstNames = male ? MALE_FIRST_NAMES : FEMALE_FIRST_NAMES;
        String[] lastNames = male ? MALE_LAST_NAMES : FEMALE_LAST_NAMES;
        String fullName = firstNames[RANDOM.nextInt(firstNames.length)]
                + " " + lastNames[RANDOM.nextInt(lastNames.length)];

        // Синтетические данные для лабораторных работ.
        return new User(
                UUID.randomUUID(),
                fullName,
                "+79" + randomDigits(9),
                LocalDate.now().minusYears(21 + RANDOM.nextInt(45))
                        .minusDays(RANDOM.nextInt(365)),
                randomDigits(10)
        );
    }

    private static String randomDigits(int count) {
        StringBuilder digits = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            digits.append(RANDOM.nextInt(10));
        }
        return digits.toString();
    }
}
