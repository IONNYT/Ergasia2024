package gr.hua.dit.omada27.Services;

import gr.hua.dit.omada27.Entities.ApprovalStatus;
import gr.hua.dit.omada27.Entities.Registration;
import gr.hua.dit.omada27.Repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    // dependency declaration
    private final RegistrationRepository registrationRepository;

    // dependency injection βάσει constructor
    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<Registration> getRegistrationById(Long id) {
        return registrationRepository.findById(id);
    }

    public List<Registration> getApprovedRegistrations() {
        return registrationRepository.findByApprovalStatus(ApprovalStatus.ACCEPTED);
    }


    public List<Registration> getRegistrationsByLandlordId(Long landlordId) {
        return registrationRepository.findByLandlordId(landlordId);
    }

    public Registration saveRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

}
