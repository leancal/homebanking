package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {



}

