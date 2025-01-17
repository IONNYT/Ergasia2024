package gr.hua.dit.omada27.Controllers;

import gr.hua.dit.omada27.Entities.Request;
import gr.hua.dit.omada27.Entities.ApprovalStatus;
import gr.hua.dit.omada27.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // ανάκτηση όλων των αιτήσεων
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    // ανάκτηση αιτήματος βάσει ID
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Optional<Request> request = requestService.getRequestById(id);
        return request.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ανάκτηση αιτήσεων βάσει ID ενικοιαστή
    @GetMapping("/renter/{renterId}")
    public ResponseEntity<List<Request>> getRequestsByRenterId(@PathVariable Long renterId) {
        List<Request> requests = requestService.getRequestsByRenterId(renterId);
        return ResponseEntity.ok(requests);
    }

    // ανάκτηση αιτήσεων βάσει status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        try {
            List<Request> requests = requestService.getRequestsByStatus(status);
            return ResponseEntity.ok(requests);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ανάκτηση αιτήσεων βάσει ID ιδιοκτήτη και status
    @GetMapping("/landlord/{landlordId}/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByLandlordAndStatus(
            @PathVariable Long landlordId,
            @PathVariable String status) {
        try {
            ApprovalStatus approvalStatus = ApprovalStatus.valueOf(status.toUpperCase());
            List<Request> requests = requestService.getRequestsByLandlordAndStatus(landlordId, approvalStatus);
            return ResponseEntity.ok(requests);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Create a new request
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request savedRequest = requestService.saveRequest(request);
        return ResponseEntity.status(201).body(savedRequest);
    }

    // διαγραφή αίτησης βάσει ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        try {
            requestService.deleteRequest(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
