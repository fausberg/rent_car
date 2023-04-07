package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rentcar.rentcar.domain.DriverLicence;

@Repository
public interface DriverLicenceRepository extends JpaRepository<DriverLicence, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM  driver_licences WHERE user_id = :userId")
    public void deleteDriverLicenceByUserId(int userId);

    @Query(nativeQuery = true, value = "SELECT * FROM driver_licences WHERE user_id = :userId")
    public DriverLicence getDriverLicenceByUserID(int userId);
}