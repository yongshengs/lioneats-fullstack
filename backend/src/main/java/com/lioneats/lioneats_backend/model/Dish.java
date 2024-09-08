package com.lioneats.lioneats_backend.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "Dish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DishDetailName", referencedColumnName = "name", unique = true)
    private DishDetail dishDetail;

    @Column(name = "ImageUrl", length = 200)
    private String imageUrl;
}
