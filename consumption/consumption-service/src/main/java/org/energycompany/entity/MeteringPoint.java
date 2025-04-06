package org.energycompany.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "metering_point")
@Table(name = "metering_point")
public class MeteringPoint extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;
}
