package alessia.LastProjectGestioneEventi.repositories;

import alessia.LastProjectGestioneEventi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO extends JpaRepository<User, UUID> {
    Optional<User> findByeMail(String eMail);
}
