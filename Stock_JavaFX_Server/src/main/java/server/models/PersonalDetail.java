package server.models;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personal_details")
public class PersonalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "personalDetails")
    private Set<Customer> customers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "personalDetails")
    private Set<Employee> employees = new LinkedHashSet<>();

    @OneToMany(mappedBy = "personalDetails")
    private Set<server.models.Supplier> suppliers = new LinkedHashSet<>();

}