package com.project1hour.api.core.domain.bungae.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import org.locationtech.jts.geom.Point;

@Embeddable
public record Location(
        @Column(columnDefinition = "point")
        Point location,

        @Lob
        @Column(columnDefinition = "text")
        String locationInfo
) {
}
