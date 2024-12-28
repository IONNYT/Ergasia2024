package gr.hua.dit.rental.Entities;

import jakarta.persistence.*;

@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;  // το ID του ακινήτου

    @ManyToOne
    @JoinColumn(name = "landlord_id") // ξένο κλειδί για πρόσβαση στα στοιχεία του ιδιοκτήτη
    private Landlord landlord;

    private String city;
    private String street;
    private String zip;
    private String type;
    private double price;
    private boolean approved;
    private String description;


}
