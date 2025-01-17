package gr.hua.dit.omada27.Controllers;

import gr.hua.dit.omada27.Entities.ApprovalStatus;
import gr.hua.dit.omada27.Entities.Registration;
import gr.hua.dit.omada27.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    // ανάκτηση καταχώρησης βάσει ID
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Optional<Registration> registration = registrationService.getRegistrationById(id);
        return registration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ανάκτηση APPROVED καταχωρήσεων
    @GetMapping("/approved")
    public ResponseEntity<List<Registration>> getApprovedRegistrations() {
        List<Registration> approvedRegistrations = registrationService.getApprovedRegistrations();
        return ResponseEntity.ok(approvedRegistrations);
    }

    // ανάκτηση καταχωρήσεων βάσει ID ιδιοκτήτη
    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<Registration>> getRegistrationsByLandlordId(@PathVariable Long landlordId) {
        List<Registration> registrations = registrationService.getRegistrationsByLandlordId(landlordId);
        return ResponseEntity.ok(registrations);
    }

    // δημιουργία νέας καταχώρησης
    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration registration) {
        Registration savedRegistration = registrationService.saveRegistration(registration);
        return ResponseEntity.status(201).body(savedRegistration);
    }

    // διαγραφή καταχώρησης βάσει ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        try {
            registrationService.deleteRegistration(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
