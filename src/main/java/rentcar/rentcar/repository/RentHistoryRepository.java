package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.RentHistory;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface RentHistoryRepository extends JpaRepository<RentHistory, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM rent_historyes WHERE time_start = :timeStart ")
    RentHistory getRentHistoriesByTimeStart(Timestamp timeStart);

    @Query(nativeQuery = true, value = "SELECT * FROM rent_historyes WHERE user_id = :id")
    ArrayList<RentHistory> getRentHistoriesByUserId(int id);
}