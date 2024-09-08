package com.lioneats.lioneats_backend.model;

import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "DishDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishDetail {

    @Id
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "isSpicy", nullable = false)
    private Boolean isSpicy;

    @Column(name = "ingredients", length = 300)
    private String ingredients;

    @Column(name = "history", length = 800)
    private String history;

    @Column(name = "description", length = 800)
    private String description;

    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Allergy> allergies;

    @ManyToMany(mappedBy = "dishPreferences", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToOne(mappedBy = "dishDetail", fetch = FetchType.LAZY)
    private Dish dish;

    @OneToMany(mappedBy = "userDish", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ML_Feedback> feedbacks;
}
