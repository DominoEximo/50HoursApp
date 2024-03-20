package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long>, JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role")
    Page<User> findByRolesName(@Param("role") String role, Pageable pageable);
}
