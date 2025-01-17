package gr.hua.dit.omada27.Services;

import gr.hua.dit.omada27.Entities.*;
import gr.hua.dit.omada27.Repositories.RegistrationRepository;
import gr.hua.dit.omada27.Repositories.RenterRepository;
import gr.hua.dit.omada27.Repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RenterService {

    // declaration του RenterDepository ως dependency
    private final RenterRepository renterRepository;
    private final RegistrationRepository registrationRepository;
    private final RequestRepository requestRepository;

    // dependency injection βάσει constructor
    @Autowired
    public RenterService(RenterRepository renterRepository,
                         RegistrationRepository registrationRepository,
                         RequestRepository requestRepository) {
        this.renterRepository = renterRepository;
        this.registrationRepository = registrationRepository;
        this.requestRepository = requestRepository;
    }

    // βασικές μέθοδοι CRUD
    public List<Renter> getAllRenters() {
        return renterRepository.findAll();
    }

    public Optional<Renter> getRenterById(Long id) {
        return renterRepository.findById(id);
    }

    public Renter getRenterByUsername(String username) {
        return renterRepository.findByUsername(username);
    }

    public Renter saveRenter(Renter renter) {
        // validateRenter(renter);
        return renterRepository.save(renter);
    }

    public void deleteRenter(Long id) {
        renterRepository.deleteById(id);
    }


    // αναζήτηση βάσει πόλης, τύπου κατοικίας, τιμής και approval status
    public List<Registration> searchApartments(String city, ApartmentType type, double minPrice, double maxPrice) {
        return registrationRepository.findByCityAndTypeAndPriceBetweenAndApprovalStatus(
                city, type, minPrice, maxPrice, ApprovalStatus.ACCEPTED
        );
    }

    // αναζήτηση βάσει τύπου κατοικίας, τιμής και approval status
    public List<Registration> searchByTypeAndPrice(ApartmentType type, double minPrice, double maxPrice) {
        return registrationRepository.findByTypeAndPriceBetweenAndApprovalStatus(
                type, minPrice, maxPrice, ApprovalStatus.ACCEPTED
        );
    }

    // αναζήτηση βάσει πόλης, τιμής και approval status
    public List<Registration> searchByCityAndPrice(String city, double minPrice, double maxPrice) {
        return registrationRepository.findByCityAndPriceBetweenAndApprovalStatus(
                city, minPrice, maxPrice, ApprovalStatus.ACCEPTED
        );
    }

    // καταχώριση αίτησης
    public Request submitRequest(Long renterId, Long registrationId) {
        Optional<Renter> renter = renterRepository.findById(renterId);
        Optional<Registration> registration = registrationRepository.findById(registrationId);

        if (renter.isPresent() && registration.isPresent()) {
            Request newRequest = new Request();
            newRequest.setRenter(renter.get());
            newRequest.setRegistration(registration.get());
            newRequest.setStatus(ApprovalStatus.PENDING);
            newRequest.setCreatedDate(LocalDateTime.now());

            return requestRepository.save(newRequest);
        } else {
            throw new IllegalArgumentException("Invalid renter ID or registration ID.");
        }
    }

    // ανάκτηση αιτημάτων για τον ενικοιαστή
    public List<Request> getRequestsByRenter(Long renterId) {
        return requestRepository.findByRenterId(renterId);
    }
}
