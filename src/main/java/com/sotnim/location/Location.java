package com.sotnim.location;

import com.sotnim.core.domain.BaseEntity;
import com.sotnim.location.gateway.CoordinatesBean;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Audited
@Entity
@Table(name = "locations")
public class Location extends BaseEntity {

    @Setter(NONE)
    @Id
    @SequenceGenerator(name = "locationsSequence", sequenceName = "locations_seq", allocationSize = 1)
    @GeneratedValue(generator = "locationsSequence", strategy = SEQUENCE)
    @Column(name = "id")
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public void updateCoordinates(CoordinatesBean coordinates) {
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
    }
}
