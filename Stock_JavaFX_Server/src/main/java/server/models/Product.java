package server.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price", precision = 10)
    private BigDecimal price;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<server.models.ProductStock> productStocks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<server.models.ReportProduct> reportProducts = new LinkedHashSet<>();

}