package rent_car.rent_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rent_car.rent_car.domain.Car;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}

