package org.energycompany.entity;

import jakarta.persistence.*;
import lombok.*;
import org.energycompany.entity.enums.AmountUnit;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "consumption")
@Table(name = "consumption")
public class Consumption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "metering_point_id", nullable = false)
    private UUID meteringPointId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "amount_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private AmountUnit amount_unit;

    @Column(name = "consumption_time", nullable = false)
    private Instant consumptionTime;
}
