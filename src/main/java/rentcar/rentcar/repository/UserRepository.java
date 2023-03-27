package rentcar.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rentcar.rentcar.domain.User;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByLogin(String login);

    @Query(nativeQuery = true, value = "SELECT login FROM users")
    ArrayList<String> logins();
}