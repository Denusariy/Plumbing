package ru.denusariy.plumbing.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "plumber")
@Data
@NoArgsConstructor
public class Plumber {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "plumber")
    private List<House> houses;

    public Plumber(String name) {
        this.name = name;
    }
}
