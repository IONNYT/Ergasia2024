package gr.hua.dit.omada27.Repositories;

import gr.hua.dit.omada27.Entities.Renter;
import gr.hua.dit.omada27.Entities.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {

    // ανάκτηση ενικοιαστή μέσω email (χρήσιμο για login ή account recovery)
    Renter findByEmail(String email);

    // ανάκτηση ενικοιαστή βάσει username
    Renter findByUsername(String username);

    List<Renter> findByVerificationStatus(VerificationStatus verificationStatus);


}
