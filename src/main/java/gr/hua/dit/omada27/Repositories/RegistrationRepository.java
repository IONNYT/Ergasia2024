package gr.hua.dit.omada27.Repositories;

import gr.hua.dit.omada27.Entities.ApartmentType;
import gr.hua.dit.omada27.Entities.ApprovalStatus;
import gr.hua.dit.omada27.Entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// η κλάση Registration και Request έχουν περισσότερες repository μεθόδους
// διότι οι άλλες οντότητες επικοινωνούν μεταξύ τους μέσω των καταχωρήσεων και αιτήσεων
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Επιστροφή λίστας καταχωρήσεων ενός συγκεκριμένου ενικοιαστή
    List<Registration> findByLandlordId(Long landlordId);

    // επιστροφή όλων των καταχωρίσεων μιας συγκεκριμένης κατηγορίας ακινήτων (για ενικοιαστές)
    List<Registration> findByType(ApartmentType type);


    // Αναζήτηση καταχωρήσεων βάσει τοποθεσίας (για ενικοιαστές)
    @Query("SELECT r FROM Registration r WHERE LOWER(r.city) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Registration> findByCityContainingIgnoreCase(String location);


    // προβολή εγκεκριμένων καταχωρίσεων (για προβολή στους ενικοιαστές)
    List<Registration> findByApprovalStatus(ApprovalStatus approvalStatus);


    // μέθοδοι για φιλτράρισμα αναζήτησης
    List<Registration> findByCityAndTypeAndPriceBetweenAndApprovalStatus(
            String city,
            ApartmentType type,
            double minPrice,
            double maxPrice,
            ApprovalStatus approvalStatus
    );

    List<Registration> findByTypeAndPriceBetweenAndApprovalStatus(
            ApartmentType type,
            double minPrice,
            double maxPrice,
            ApprovalStatus approvalStatus
    );

    List<Registration> findByCityAndPriceBetweenAndApprovalStatus(
            String city,
            double minPrice,
            double maxPrice,
            ApprovalStatus approvalStatus
    );



}
