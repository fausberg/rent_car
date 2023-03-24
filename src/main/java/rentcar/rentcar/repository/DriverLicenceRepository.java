package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.DriverLicence;

@Repository
public interface DriverLicenceRepository extends JpaRepository<DriverLicence, Integer> {
}