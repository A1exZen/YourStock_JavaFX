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
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private server.models.Supplier supplier;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "material")
    private Set<server.models.MaterialStock> materialStocks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "material")
    private Set<server.models.ReportMaterial> reportMaterials = new LinkedHashSet<>();

}