package gr.hua.dit.omada27.Services;

import gr.hua.dit.omada27.Entities.*;
import gr.hua.dit.omada27.Repositories.AdminRepository;
import gr.hua.dit.omada27.Repositories.LandlordRepository;
import gr.hua.dit.omada27.Repositories.RegistrationRepository;
import gr.hua.dit.omada27.Repositories.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    // dependency declaration
    private final AdminRepository adminRepository;
    private final RenterRepository renterRepository;
    private final RegistrationRepository registrationRepository;
    private final LandlordRepository landlordRepository;


    // dependency injection βάσει constructor
    @Autowired
    public AdminService(AdminRepository adminRepository, RenterRepository renterRepository, RegistrationRepository registrationRepository, LandlordRepository landlordRepository) {
        this.adminRepository = adminRepository;
        this.renterRepository = renterRepository;
        this.registrationRepository = registrationRepository;
        this.landlordRepository = landlordRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    // έγκριση ενικοιαστή μέσω ID
    public void verifyRenter(Long renterId) {
        Renter renter = renterRepository.findById(renterId)
                .orElseThrow(() -> new IllegalArgumentException("Renter with ID " + renterId + " not found"));

        if (renter.getVerificationStatus() == VerificationStatus.VERIFIED) {
            throw new IllegalStateException("Renter with ID " + renterId + " is already verified.");
        }

        renter.setVerificationStatus(VerificationStatus.VERIFIED);
        renterRepository.save(renter);
    }

    public List<Renter> getUnverifiedRenters() {
        return renterRepository.findByVerificationStatus(VerificationStatus.UNVERIFIED);
    }

    // ανάκρηση pending καταχωρήσεων
    public List<Registration> getPendingRegistrations() {
        return registrationRepository.findByApprovalStatus(ApprovalStatus.PENDING);
    }

    // έγκριση καταχώρισης
    public void approveRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration with ID " + registrationId + " not found."));

        if (registration.getApprovalStatus() == ApprovalStatus.ACCEPTED) {
            throw new IllegalStateException("Registration with ID " + registrationId + " is already approved.");
        }

        registration.setApprovalStatus(ApprovalStatus.ACCEPTED);
        registrationRepository.save(registration);
    }

    // άρνηση καταχώρισης
    public void rejectRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration with ID " + registrationId + " not found."));

        if (registration.getApprovalStatus() == ApprovalStatus.REJECTED) {
            throw new IllegalStateException("Registration with ID " + registrationId + " is already rejected.");
        }

        registration.setApprovalStatus(ApprovalStatus.REJECTED);
        registrationRepository.save(registration);
    }


    // διαγραφή ενικοιαστή μέσω ID
    public void deleteRenter(Long renterId) {
        Renter renter = renterRepository.findById(renterId)
                .orElseThrow(() -> new IllegalArgumentException("Renter with ID " + renterId + " not found."));

        renterRepository.deleteById(renterId);
    }

    // διαγραφή landlord μέσω ID
    public void deleteLandlord(Long landlordId) {
        Landlord landlord = landlordRepository.findById(landlordId)
                .orElseThrow(() -> new IllegalArgumentException("Landlord with ID " + landlordId + " not found."));

        landlordRepository.deleteById(landlordId);
    }

    // διαγραφή καταχώρισης μέσω ID
    public void deleteRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration with ID " + registrationId + " not found."));

        registrationRepository.deleteById(registrationId);
    }


}
