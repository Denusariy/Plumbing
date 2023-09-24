package ru.denusariy.plumbing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.denusariy.plumbing.domain.entity.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
