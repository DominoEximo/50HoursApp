package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType,Long>,PagingAndSortingRepository<JobType,Long>{

    JobType findByName(String name);
}
