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
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "position")
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_details_id")
    private server.models.PersonalDetail personalDetails;

    @OneToMany(mappedBy = "employee")
    private Set<CardId> cards = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<server.models.Report> reports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<server.models.User> users = new LinkedHashSet<>();

}