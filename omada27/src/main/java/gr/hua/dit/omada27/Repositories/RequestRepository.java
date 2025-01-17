package gr.hua.dit.omada27.Repositories;

import gr.hua.dit.omada27.Entities.Request;
import gr.hua.dit.omada27.Entities.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // επιστροφή αιτημάτων συγκεκριμένου ενικοιαστή
    List<Request> findByRenterId(Long renterId);

    // επιστροφή αιτήσεων για συγκεκριμένη καταχώριση
    List<Request> findByRegistrationId(Long registrationId);

    // επιστροφή αιτημάτων status (π.χ. PENDING, APPROVED)
    List<Request> findByStatus(ApprovalStatus approvalStatus);

    // βρισκει αιτήματα βασει status αλλα για συγκεκριμένο landlord
    @Query("SELECT r FROM Request r WHERE r.registration.landlord.id = :landlordId AND r.status = :approvalStatus")
    List<Request> findByLandlordIdAndApprovalStatus(@Param("landlordId") Long landlordId, @Param("approvalStatus") ApprovalStatus approvalStatus);


    @Query("SELECT r FROM Request r JOIN FETCH r.renter JOIN FETCH r.registration WHERE r.status = :status")
    List<Request> findRequestsWithDetailsByStatus(ApprovalStatus status);

}
