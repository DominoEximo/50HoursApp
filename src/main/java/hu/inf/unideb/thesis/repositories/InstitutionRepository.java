package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.Institution;
import hu.inf.unideb.thesis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends PagingAndSortingRepository<Institution, Long>, JpaRepository<Institution, Long> {

    List<Institution> findByLocation(String location);

    Institution findByName(String name);

}
