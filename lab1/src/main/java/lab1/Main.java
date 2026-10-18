package lab1;

import domain.Car;
import domain.CarGenerator;

import java.util.List;
public class Main {

    public static void main(String[] args) {

//        List<Car> cars = CarGenerator.generate(100000);
//
//        System.out.println("Количество машин: " + cars.size());
//
//        for (Car car : cars) {
//            System.out.println(
//                    car.getModel()
//                            + " | пробег: " + car.getMileage()
//                            + " | статус: " + car.getStatus()
//            );
//        }
//        Statistics.calculateWithLoop(cars);
//        Statistics.calculateWithStream(cars);
//        Statistics.calculateWithOwnCollector(cars);
        System.out.println("\n=== 5000 машин ===");
        Statistics.measureTime(5000);

        System.out.println("\n=== 50000 машин ===");
        Statistics.measureTime(50000);

        System.out.println("\n=== 250000 машин ===");
        Statistics.measureTime(250000);
    }
}