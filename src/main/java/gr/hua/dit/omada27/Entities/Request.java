package gr.hua.dit.omada27.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false) // ξένο κλειδί για τον Renter
    private Renter renter;

    @ManyToOne
    @JoinColumn(name = "registration_id", nullable = false) // ξένο κλειδί για το ακίνητο
    private Registration registration;


    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;


    private LocalDateTime createdDate; // ημερομηνία αίτησης


    // άδειος constructor για JPA
    public Request() {
    }

    public Request(ApprovalStatus status, LocalDateTime createdDate) {
        this.status = ApprovalStatus.PENDING;
        this.createdDate = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}



