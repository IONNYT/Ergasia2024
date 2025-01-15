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

    // Fetch all registrations
    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    // Fetch a specific registration by ID
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Optional<Registration> registration = registrationService.getRegistrationById(id);
        return registration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch approved registrations
    @GetMapping("/approved")
    public ResponseEntity<List<Registration>> getApprovedRegistrations() {
        List<Registration> approvedRegistrations = registrationService.getApprovedRegistrations();
        return ResponseEntity.ok(approvedRegistrations);
    }

    // Fetch registrations by landlord ID
    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<Registration>> getRegistrationsByLandlordId(@PathVariable Long landlordId) {
        List<Registration> registrations = registrationService.getRegistrationsByLandlordId(landlordId);
        return ResponseEntity.ok(registrations);
    }

    // Create a new registration
    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration registration) {
        Registration savedRegistration = registrationService.saveRegistration(registration);
        return ResponseEntity.status(201).body(savedRegistration);
    }

    // Delete a registration by ID
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
