package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.Fine;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
}