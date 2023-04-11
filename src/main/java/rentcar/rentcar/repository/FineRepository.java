package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.Fine;

import java.util.ArrayList;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM fines WHERE user_id = :id")
    ArrayList<Fine> getFinesByUserId(int id);
}