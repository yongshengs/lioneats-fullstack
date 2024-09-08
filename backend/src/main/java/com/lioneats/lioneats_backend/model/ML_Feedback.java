package com.lioneats.lioneats_backend.model;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ML_Feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ML_Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_location", nullable = false)
    private String imageLocation;

    @Column(name = "ML_result", nullable = false)
    private String ml_result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_detail_id", nullable = false)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private DishDetail userDish;

    @Column(name = "remarks")
    private String remarks;
}
