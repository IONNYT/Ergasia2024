package gr.hua.dit.omada27.Services;

import gr.hua.dit.omada27.Entities.*;
import gr.hua.dit.omada27.Repositories.LandlordRepository;
import gr.hua.dit.omada27.Repositories.RegistrationRepository;
import gr.hua.dit.omada27.Repositories.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LandlordService {

    // dependency injection
    private final LandlordRepository landlordRepository;
    private final RequestRepository requestRepository;
    private final RegistrationRepository registrationRepository;

    // Constructor injection για τα repositories
    public LandlordService(LandlordRepository landlordRepository,
                           RequestRepository requestRepository,
                           RegistrationRepository registrationRepository) {
        this.landlordRepository = landlordRepository;
        this.requestRepository = requestRepository;
        this.registrationRepository = registrationRepository;
    }

    public Landlord saveLandlord(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public Optional<Landlord> findById(Long id) {
        return landlordRepository.findById(id);
    }

    public Landlord findByUsername(String username) {
        return landlordRepository.findByUsername(username);
    }

    public List<Landlord> findAll() {
        return landlordRepository.findAll();
    }

    // διαγραφή landlord μέσω id
    @Transactional
    public void deleteById(Long id) {
        if (!landlordRepository.existsById(id)) {
            throw new IllegalArgumentException("Landlord with ID " + id + " does not exist.");
        }
        landlordRepository.deleteById(id);
    }

    // ανάκτηση καταχωρίσεων landlord
    public List<Registration> getRegistrationsForLandlord(Long landlordId) {
        Landlord landlord = landlordRepository.findById(landlordId)
                .orElseThrow(() -> new IllegalArgumentException("Landlord with ID " + landlordId + " does not exist."));
        return landlord.getRegistrations();
    }


    public List<Request> getRequestsByLandlordAndStatus(Long landlordId, ApprovalStatus status) {
        return requestRepository.findByLandlordIdAndApprovalStatus(landlordId, status);
    }

    // αλλαγή request status σε ACCEPTED ή REJECTED
    @Transactional
    public Request updateRequestStatus(Long requestId, ApprovalStatus newStatus) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request with ID " + requestId + " does not exist."));

        // Update request status
        request.setStatus(newStatus);
        return requestRepository.save(request);
    }

    // μέθοδος καταχώρισης για landlord
    @Transactional
    public Registration registerApartment(Long landlordId, String city, String street, String zip,
                                          ApartmentType type, double price, String description) {

        // εύρεση landlord
        Landlord landlord = landlordRepository.findById(landlordId)
                .orElseThrow(() -> new IllegalArgumentException("Landlord with ID " + landlordId + " does not exist."));

        // δημιουργία Registration object και καταχώριση τιμών
        Registration registration = new Registration(city, street, zip, type, price, ApprovalStatus.PENDING, description, LocalDateTime.now());

        // αντιστοίχιση landlord στο registration
        registration.setLandlord(landlord);

        // αποθήκευση καταχώρισης στη βάση δεδομένων
        return registrationRepository.save(registration);
    }
}
