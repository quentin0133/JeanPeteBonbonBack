package fr.dawan.jeankasskouille.business.component.user;

import fr.dawan.jeankasskouille.business.component.user.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    @Query("""
            SELECT DISTINCT u
            FROM User u
            LEFT JOIN u.roles r
            WHERE
                    (:firstname IS NULL OR u.firstName LIKE %:firstname%) AND
                    (:lastname IS NULL OR u.lastName LIKE %:lastname%) AND
                    (:email IS NULL OR u.email = :email) AND
                    (:roles IS NULL OR r = :role)""")
    Page<User> findBySearch(
            String firstname,
            String lastname,
            String email,
            Role role,
            Pageable pageable);
}
