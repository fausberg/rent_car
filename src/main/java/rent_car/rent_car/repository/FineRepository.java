package rent_car.rent_car.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rent_car.rent_car.domain.Fine;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
}