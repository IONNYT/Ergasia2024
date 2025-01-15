package gr.hua.dit.omada27.Entities;

import jakarta.persistence.*;

// Η κλάση Renter έχει τα attributes και τις μεθόδους για τους ενοικιαστές ακινήτων
// Είναι καταχωρημένη ως entity και εμφανίζεται σαν πίνακας στη βάση δεδομένων 
@Entity
public class Renter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus; // UNVERIFIED, VERIFIED

    // άδειος constructor για JPA
    protected Renter() {
    }

    // Constructor για τα απαιτούμενα πεδία (με εξαίρεση το id)
    public Renter(String firstName, String lastName, String username, String password, String email, VerificationStatus verificationStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.verificationStatus = VerificationStatus.UNVERIFIED;
    }

    // Getters και setters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
