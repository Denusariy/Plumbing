package ru.denusariy.plumbing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "house")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "address")
    private String address;
    @ManyToOne
    @JoinColumn(name = "plumber_id", referencedColumnName = "id")
    private Plumber plumber;

    public House(String address) {
        this.address = address;
    }
}
