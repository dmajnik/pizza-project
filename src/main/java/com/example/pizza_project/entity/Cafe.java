package com.example.pizza_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cafe")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cafe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CAFE_NAME", length = 50, nullable = false, unique = false)
    private String name;
    @Column(name = "CITY_NAME", length = 50, nullable = false, unique = false)
    private String city;
    @Column(name = "ADDRESS", length = 50, nullable = false, unique = false)
    private String address;
    @Column(name = "CAFE_EMAIL", length = 50, nullable = false, unique = false)
    private String email;
    @Column(name = "CAFE_NUMBER", length = 50, nullable = false, unique = false)
    private String phone;
    @Column(name = "OPEN_AT", length = 5, nullable = false, unique = false)
    private String open_at;
    @Column(name = "CLOSE_AT", length = 5, nullable = false, unique = false)
    private String close_at;


    public Cafe(String name, String city, String address, String email, String phone, String open_at, String close_at) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.open_at = open_at;
        this.close_at = close_at;
    }

    @OneToMany
    @OnDelete(action = OnDeleteAction.CASCADE )
    @JoinColumn(name="cafe_id")
    @JsonIgnore
    private Set<Pizza> pizza_menu = new HashSet<>();

    public Cafe save(Cafe cafe) {
        return cafe;
    }
}

