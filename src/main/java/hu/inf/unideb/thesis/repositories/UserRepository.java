package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long>, JpaRepository<User, Long> {

    User findByName(String name);

}
