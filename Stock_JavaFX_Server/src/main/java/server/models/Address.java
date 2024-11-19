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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "address")
    private Set<server.models.PersonalDetail> personalDetails = new LinkedHashSet<>();

}