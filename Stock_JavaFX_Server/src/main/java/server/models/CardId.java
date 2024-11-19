package server.models;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card_id")
public class CardId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private server.models.Employee employee;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "ended_date")
    private Instant endedDate;

}