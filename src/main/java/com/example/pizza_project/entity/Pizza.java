package com.example.pizza_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PIZZA")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PIZZA_NAME", length = 25, nullable = false, unique = false)
    private String name;
    @Column(name = "PIZZA_SIZE", length = 10, nullable = false, unique = false)
    private String size;

    @Column(name = "KEY_INGREDIENTS", length = 50, nullable = false, unique = false)
    private String ingredients;

    @Column(name = "PIZZA_PRICE", columnDefinition = " Decimal(10,2) default '0.00' ", nullable = false)
    private BigDecimal price;

    public Pizza(String name, String size, String key_ingredients, BigDecimal price, Cafe cafe) {
        this.name = name;
        this.size = size;
        this.ingredients = key_ingredients;
        this.price = price;
        this.cafe = cafe;
    }

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.MERGE
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cafe_id", nullable = false)
//@JsonIgnore
    @JsonBackReference
    private Cafe cafe;

    public Pizza save(Pizza pizza) {
        return pizza;
    }

}
