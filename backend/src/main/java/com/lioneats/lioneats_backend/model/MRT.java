package com.lioneats.lioneats_backend.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import java.util.List;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MRT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private double latitude;
    private double longitude;

    @ElementCollection
    @CollectionTable(name = "mrt_lines", joinColumns = @JoinColumn(name = "mrt_id"))
    @Column(name = "line")
    private List<String> lines;

    public MRT() {}

    public MRT(String name, double latitude, double longitude, List<String> lines) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lines = lines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

}




