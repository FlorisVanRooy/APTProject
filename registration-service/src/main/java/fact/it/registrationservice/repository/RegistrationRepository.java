package fact.it.registrationservice.repository;

import fact.it.registrationservice.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, String> {
    Optional<Registration> findByRegistrationCode(String registrationCode);
}
