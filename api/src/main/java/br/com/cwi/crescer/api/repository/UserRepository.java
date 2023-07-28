package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Page<User> findByEmailNot(String email, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.username = :search OR u.email = :search AND u.id <> :userId")
    Page<User> findByNameOrEmail(Pageable pageable, String search, Long userId);

}
