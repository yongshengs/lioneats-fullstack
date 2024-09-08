package com.lioneats.lioneats_backend.model;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Circle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mrt_id")
    private MRT mrt;

    private int radius;

    @OneToMany(mappedBy = "circle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Shop> shops;

    public Circle() {}

    public Circle(MRT mrt, int radius) {
        this.mrt = mrt;
        this.radius = radius;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MRT getMrt() {
        return mrt;
    }

    public void setMrt(MRT mrt) {
        this.mrt = mrt;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public double getLatitude() {
        return mrt != null ? mrt.getLatitude() : 0;
    }

    public double getLongitude() {
        return mrt != null ? mrt.getLongitude() : 0;
    }
}
