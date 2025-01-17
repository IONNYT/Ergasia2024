package gr.hua.dit.omada27.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


// Η κλάση Landlord έχει τα attributes και τις μεθόδους για τους ιδιοκτήτες ακινήτων
// Είναι καταχωρημένη ως entity και εμφανίζεται σαν πίνακας στη βάση δεδομένων
@Entity
public class Landlord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    // προσωπική λίστα καταχωρίσεων του κάθε ιδιοκτήτη
    @OneToMany(mappedBy = "landlord")
    private List<Registration> registrations = new ArrayList<>();


    // άδειος constructor για JPA
    protected Landlord() {
    }

    // Constructor για τα απαιτούμενα πεδία (με εξαίρεση το id)
    public Landlord(String firstName, String lastName, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
}

