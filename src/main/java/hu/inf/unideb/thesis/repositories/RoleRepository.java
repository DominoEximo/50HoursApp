package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role,Long>, JpaRepository<Role, Long> {

    Role findByName(String name);
}
