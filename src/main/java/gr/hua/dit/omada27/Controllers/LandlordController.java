package gr.hua.dit.omada27.Controllers;

import gr.hua.dit.omada27.Entities.*;
import gr.hua.dit.omada27.Services.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/landlords")
public class LandlordController {

    private final LandlordService landlordService;

    @Autowired
    public LandlordController(LandlordService landlordService) {
        this.landlordService = landlordService;
    }

    // Ανάκτηση όλων των ιδιοκτητών, χρησιμοποιείται απο διαχειριστές
    @GetMapping
    public ResponseEntity<List<Landlord>> getAllLandlords() {
        List<Landlord> landlords = landlordService.findAll();
        return ResponseEntity.ok(landlords);
    }

    // ανάκτηση ιδιοκτήτη βάσει ID, χρησιμοποιείται απο διαχειριστές
    @GetMapping("/{id}")
    public ResponseEntity<Landlord> getLandlordById(@PathVariable Long id) {
        Optional<Landlord> landlord = landlordService.findById(id);
        return landlord.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ανάκτηση ιδιοκτήτη βάσει username, χρησιμοποιείται από διαχειριστές
    @GetMapping("/username/{username}")
    public ResponseEntity<Landlord> getLandlordByUsername(@PathVariable String username) {
        Landlord landlord = landlordService.findByUsername(username);
        return ResponseEntity.ok(landlord);
    }

    // δημιουργία προφίλ ιδιοκτήτη, χρησιμοποιείται από ιδιοκτήτες
    @PostMapping
    public ResponseEntity<Landlord> createLandlord(@RequestBody Landlord landlord) {
        Landlord savedLandlord = landlordService.saveLandlord(landlord);
        return ResponseEntity.status(201).body(savedLandlord);
    }

    // διαγραφή ιδιοκτήτη βάσει ID, χρησιμοποιείται απο διαχειριστές
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable Long id) {
        landlordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ανάκτηση καταχωρίσεων ενός ιδιοκτήτη, χρησιμοποιείται απο ιδιοκτήτες και διαχειριστές
    @GetMapping("/{id}/registrations")
    public ResponseEntity<List<Registration>> getRegistrationsForLandlord(@PathVariable Long id) {
        try {
            List<Registration> registrations = landlordService.getRegistrationsForLandlord(id);
            return ResponseEntity.ok(registrations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // καταχώριση ακινήτου, χρησιμοποιείται από ιδιοκτήτες
    @PostMapping("/{id}/registerApartment")
    public ResponseEntity<Registration> registerApartment(
            @PathVariable Long id,
            @RequestParam String city,
            @RequestParam String street,
            @RequestParam String zip,
            @RequestParam ApartmentType type,
            @RequestParam double price,
            @RequestParam String description) {
        try {
            Registration registration = landlordService.registerApartment(id, city, street, zip, type, price, description);
            return ResponseEntity.status(201).body(registration);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ανάκτηση αιτημάτων προς ιδιοκτήτη βάσει status, χρησιμοποιείται από ιδιοκτήτες (και ενοικιαστές)
    @GetMapping("/{id}/requests")
    public ResponseEntity<List<Request>> getRequestsByLandlordAndStatus(
            @PathVariable Long id,
            @RequestParam ApprovalStatus status) {
        List<Request> requests = landlordService.getRequestsByLandlordAndStatus(id, status);
        return ResponseEntity.ok(requests);
    }

    // αλλαγή request status, χρησιμοποιείται από ιδιοκτήτες
    @PutMapping("/requests/{requestId}")
    public ResponseEntity<Request> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam ApprovalStatus status) {
        try {
            Request updatedRequest = landlordService.updateRequestStatus(requestId, status);
            return ResponseEntity.ok(updatedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

