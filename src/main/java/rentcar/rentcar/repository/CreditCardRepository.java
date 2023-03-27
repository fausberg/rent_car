package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.CreditCard;

import java.util.ArrayList;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM credit_cards WHERE user_id = :id")
    ArrayList<CreditCard> getCreditCardsByUserId(int id);
}
