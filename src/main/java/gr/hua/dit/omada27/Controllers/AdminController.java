package gr.hua.dit.omada27.Controllers;

import gr.hua.dit.omada27.Entities.*;
import gr.hua.dit.omada27.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Fetch all admins
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // Fetch a specific admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch an admin by username
    @GetMapping("/username/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        Admin admin = adminService.getAdminByUsername(username);
        return ResponseEntity.ok(admin);
    }

    // Create a new admin
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.status(201).body(savedAdmin);
    }

    // Delete an admin by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // Verify a renter by ID
    @PutMapping("/verifyRenter/{renterId}")
    public ResponseEntity<Void> verifyRenter(@PathVariable Long renterId) {
        try {
            adminService.verifyRenter(renterId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all unverified renters
    @GetMapping("/unverifiedRenters")
    public ResponseEntity<List<Renter>> getUnverifiedRenters() {
        List<Renter> unverifiedRenters = adminService.getUnverifiedRenters();
        return ResponseEntity.ok(unverifiedRenters);
    }

    // Get all pending registrations
    @GetMapping("/pendingRegistrations")
    public ResponseEntity<List<Registration>> getPendingRegistrations() {
        List<Registration> pendingRegistrations = adminService.getPendingRegistrations();
        return ResponseEntity.ok(pendingRegistrations);
    }

    // Approve a registration by ID
    @PutMapping("/approveRegistration/{registrationId}")
    public ResponseEntity<Void> approveRegistration(@PathVariable Long registrationId) {
        try {
            adminService.approveRegistration(registrationId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Reject a registration by ID
    @PutMapping("/rejectRegistration/{registrationId}")
    public ResponseEntity<Void> rejectRegistration(@PathVariable Long registrationId) {
        try {
            adminService.rejectRegistration(registrationId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete a renter by ID
    @DeleteMapping("/deleteRenter/{renterId}")
    public ResponseEntity<Void> deleteRenter(@PathVariable Long renterId) {
        try {
            adminService.deleteRenter(renterId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a landlord by ID
    @DeleteMapping("/deleteLandlord/{landlordId}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable Long landlordId) {
        try {
            adminService.deleteLandlord(landlordId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a registration by ID
    @DeleteMapping("/deleteRegistration/{registrationId}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long registrationId) {
        try {
            adminService.deleteRegistration(registrationId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
