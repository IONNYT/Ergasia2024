package gr.hua.dit.omada27.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // το ID του ακινήτου

    @ManyToOne
    @JoinColumn(name = "landlord_id") // ξένο κλειδί για πρόσβαση στα στοιχεία του ιδιοκτήτη
    private Landlord landlord;

    private String city;
    private String street;
    private String zip;
    @Enumerated(EnumType.STRING)
    private ApartmentType type;
    private double price;
    @Column(nullable = false) // Οι καταχωρίσεις είναι μη αποδεκτές μέχρι να λάβουν έγκριση
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;  // η καταχώριση εμφανίζεται σε αναζήτηση μόνο αν το πεδίο ειναι true;
    private String description;  // σύντομη περιγραφή του διαμερίσματος
    private LocalDateTime registrationDate;  // ημερομηνία καταχώρισης

    // άδειος constructor για το JPA
    public Registration() {
    }

    public Registration(String city, String street, String zip, ApartmentType type, double price, ApprovalStatus ApprovalStatus, String description, LocalDateTime registrationDate) {
        this.city = city;
        this.street = street;
        this.zip = zip;
        this.type = type;
        this.price = price;
        this.approvalStatus = gr.hua.dit.omada27.Entities.ApprovalStatus.PENDING;
        this.description = description;
        this.registrationDate = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public ApartmentType getType() {
        return type;
    }

    public void setType(ApartmentType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}

