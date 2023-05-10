package rentcar.rentcar.mappers;

import org.springframework.stereotype.Component;
import rentcar.rentcar.domain.Car;
import rentcar.rentcar.domain.DTO.CarDTO;

@Component
public class CarMapper {

    public Car mapCarDTOtoCar (CarDTO carDTO) {
        Car car = new Car();
        car.setColor(carDTO.getColor());
        car.setPrice(carDTO.getPrice());
        car.setModel(carDTO.getModel());
        car.setManufacturer(carDTO.getManufacturer());
        car.setBooking(false);
        car.setNumber(carDTO.getNumber());
        return car;
    }

    public CarDTO mapCarToCarDTO (Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setColor(car.getColor());
        carDTO.setPrice(car.getPrice());
        carDTO.setModel(car.getModel());
        carDTO.setManufacturer(car.getManufacturer());
        carDTO.setBooking(car.isBooking());
        carDTO.setNumber(car.getNumber());
        return carDTO;
    }
}
