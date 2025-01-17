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


    // δημιουργία admin
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.status(201).body(savedAdmin);
    }


    // επιβεβαίωση ενικοιαστή βάσει ID
    @PutMapping("/verifyRenter/{renterId}")
    public ResponseEntity<Void> verifyRenter(@PathVariable Long renterId) {
        try {
            adminService.verifyRenter(renterId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ανάκτηση μη-επιβεβαιωμένων ενικοιαστών
    @GetMapping("/unverifiedRenters")
    public ResponseEntity<List<Renter>> getUnverifiedRenters() {
        List<Renter> unverifiedRenters = adminService.getUnverifiedRenters();
        return ResponseEntity.ok(unverifiedRenters);
    }

    // ανάκτηση PENDING αιτήσεων
    @GetMapping("/pendingRegistrations")
    public ResponseEntity<List<Registration>> getPendingRegistrations() {
        List<Registration> pendingRegistrations = adminService.getPendingRegistrations();
        return ResponseEntity.ok(pendingRegistrations);
    }

    // αποδοχή καταχωρήσεων βάσει ID
    @PutMapping("/approveRegistration/{registrationId}")
    public ResponseEntity<Void> approveRegistration(@PathVariable Long registrationId) {
        try {
            adminService.approveRegistration(registrationId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // απόρριψη καταχώρησης βάσει ID
    @PutMapping("/rejectRegistration/{registrationId}")
    public ResponseEntity<Void> rejectRegistration(@PathVariable Long registrationId) {
        try {
            adminService.rejectRegistration(registrationId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // διαγραφή ενοικιαστή βάσει ID
    @DeleteMapping("/deleteRenter/{renterId}")
    public ResponseEntity<Void> deleteRenter(@PathVariable Long renterId) {
        try {
            adminService.deleteRenter(renterId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // διαγραφή ιδιοκτήτη ID
    @DeleteMapping("/deleteLandlord/{landlordId}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable Long landlordId) {
        try {
            adminService.deleteLandlord(landlordId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // διαγραφή καταχώρησης βάσει ID
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
