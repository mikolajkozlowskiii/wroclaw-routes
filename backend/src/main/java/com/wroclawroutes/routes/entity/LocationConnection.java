package com.wroclawroutes.routes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Entity
@Table(	name = "locations_connections",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_start_end_locations",
                        columnNames = {"start_location_id", "end_location_id"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LocationConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_location_id")
    private Location startLocation;
    @NotNull
    @JoinColumn(name = "end_location_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Location endLocation;
    private Integer distanceInMeters;
    private Integer timeOnFootInSec;

    @PrePersist
    @PreUpdate
    private void validateStartEndLocations() {
        if (startLocation != null && startLocation.equals(endLocation)) {
            throw new IllegalArgumentException("startLocation cannot be the same as endLocation");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationConnection that = (LocationConnection) o;
        return Objects.equals(startLocation, that.startLocation) && Objects.equals(endLocation, that.endLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startLocation, endLocation);
    }
}






