package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface InstitutionRepository extends PagingAndSortingRepository<Institution, Long>, JpaRepository<Institution, Long> {

    List<Institution> findByLocation(String location);

    Institution findByName(String name);

    @Query("SELECT i FROM Institution i WHERE i.type.name = :jobTypeName")
    Page<Institution> findByType(@Param("jobTypeName") String jobTypeName, Pageable pageable);

}
