package com.lioneats.lioneats_backend.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public enum Gender {
        Male,
        Female,
        Other
    }

    public enum PreferredBudget {
        HIGH,
        MEDIUM,
        LOW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be less than 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    @Column(nullable = true)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Size(max = 100, message = "Country must be less than 100 characters")
    @Column(nullable = true)
    private String country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_allergy",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    private List<Allergy> allergies;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Preferred budget is required")
    @Column(nullable = false)
    private PreferredBudget preferredBudget;

    @Column(nullable = false)
    private boolean likesSpicy;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_dish_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<DishDetail> dishPreferences;
}

