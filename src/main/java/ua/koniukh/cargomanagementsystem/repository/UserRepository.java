package ua.koniukh.cargomanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.koniukh.cargomanagementsystem.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
