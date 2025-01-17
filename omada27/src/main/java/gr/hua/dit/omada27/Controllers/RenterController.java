package gr.hua.dit.omada27.Controllers;

import gr.hua.dit.omada27.Entities.*;
import gr.hua.dit.omada27.Services.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/renters")
public class RenterController {

    private final RenterService renterService;

    @Autowired
    public RenterController(RenterService renterService) {
        this.renterService = renterService;
    }

    // ανάκτηση όλων των ενικοιαστών, χρησιμοποιείται από ενικοιαστές
    @GetMapping
    public ResponseEntity<List<Renter>> getAllRenters() {
        List<Renter> renters = renterService.getAllRenters();
        return ResponseEntity.ok(renters);
    }

    // ανάκτηση ενικοιαστή βάσει ID, για admin
    @GetMapping("/{id}")
    public ResponseEntity<Renter> getRenterById(@PathVariable Long id) {
        Optional<Renter> renter = renterService.getRenterById(id);
        return renter.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ανάκτηση ενικοιαστή βάσει username, για admin
    @GetMapping("/username/{username}")
    public ResponseEntity<Renter> getRenterByUsername(@PathVariable String username) {
        Renter renter = renterService.getRenterByUsername(username);
        return ResponseEntity.ok(renter);
    }

    // δημιουργία νέου ενικοιαστή
    @PostMapping
    public ResponseEntity<Renter> createRenter(@RequestBody Renter renter) {
        Renter savedRenter = renterService.saveRenter(renter);
        return ResponseEntity.status(201).body(savedRenter);
    }

    // διαγραφή ενικοιαστή βάσει ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRenter(@PathVariable Long id) {
        renterService.deleteRenter(id);
        return ResponseEntity.noContent().build();
    }

    // αναζήτησης ακινήτου βάσει πόλης, τύπου, και τιμής
    @GetMapping("/search")
    public ResponseEntity<List<Registration>> searchApartments(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) ApartmentType type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Registration> results;

        if (city != null && type != null) {
            results = renterService.searchApartments(city, type, minPrice != null ? minPrice : 0, maxPrice != null ? maxPrice : Double.MAX_VALUE);
        } else if (city != null) {
            results = renterService.searchByCityAndPrice(city, minPrice != null ? minPrice : 0, maxPrice != null ? maxPrice : Double.MAX_VALUE);
        } else if (type != null) {
            results = renterService.searchByTypeAndPrice(type, minPrice != null ? minPrice : 0, maxPrice != null ? maxPrice : Double.MAX_VALUE);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(results);
    }

    // υποβολή αίτησης ενικοίασης
    @PostMapping("/{renterId}/request/{registrationId}")
    public ResponseEntity<Request> submitRequest(@PathVariable Long renterId, @PathVariable Long registrationId) {
        try {
            Request request = renterService.submitRequest(renterId, registrationId);
            return ResponseEntity.status(201).body(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ανάκτηση όλων των αιτήσεων ενός ενικοιαστή
    @GetMapping("/{id}/requests")
    public ResponseEntity<List<Request>> getRequestsByRenter(@PathVariable Long id) {
        List<Request> requests = renterService.getRequestsByRenter(id);
        return ResponseEntity.ok(requests);
    }
}
