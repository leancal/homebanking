package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Set<Transaction> findByDateBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

}

