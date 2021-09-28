package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.koniukh.cargomanagementsystem.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {}
