package server.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "action")
    private String action;

    @Column(name = "details")
    private String details;

    @Column(name = "report_date")
    private Instant reportDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "report")
    private Set<server.models.ReportMaterial> reportMaterials = new LinkedHashSet<>();

    @OneToMany(mappedBy = "report")
    private Set<server.models.ReportProduct> reportProducts = new LinkedHashSet<>();

}