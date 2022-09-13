package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);

}
//repositorio es una clase que se encarga de guardar los datos en la base de datos

