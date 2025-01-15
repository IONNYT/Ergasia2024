package gr.hua.dit.omada27.Repositories;

import gr.hua.dit.omada27.Entities.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {

    // επιστροφή ιδιοκτήτη βάσει ID
    Landlord findByUsername(String username);
}

