package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rentcar.rentcar.domain.Car;

import java.util.ArrayList;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM cars WHERE booking = false")
    ArrayList<Car> getCars();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cars SET booking = true WHERE id = :id")
    void bookCar(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE cars SET booking = false WHERE id = :id")
    void removeTheBookingFromTheCar(Integer id);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM cars WHERE number = :number")
    Car getCarByNumber(String number);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM cars WHERE number = :number")
    void deleteCarByNumber(String number);
}

