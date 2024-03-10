package hu.inf.unideb.thesis.repositories;

import hu.inf.unideb.thesis.entity.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends PagingAndSortingRepository<Contract, Long>, JpaRepository<Contract, Long> {
}
