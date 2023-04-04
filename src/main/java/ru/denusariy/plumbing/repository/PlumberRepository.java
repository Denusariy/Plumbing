package ru.denusariy.plumbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denusariy.plumbing.domain.entity.Plumber;

import java.util.Optional;

@Repository
public interface PlumberRepository extends JpaRepository<Plumber, Integer> {
    Optional<Plumber> findByNameEquals(String name);
}
