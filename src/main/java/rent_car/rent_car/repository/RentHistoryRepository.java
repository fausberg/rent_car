package rent_car.rent_car.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rent_car.rent_car.domain.RentHistory;

import javax.persistence.Query;
import java.util.ArrayList;

@Repository
public interface RentHistoryRepository extends JpaRepository<RentHistory, Integer> {
}