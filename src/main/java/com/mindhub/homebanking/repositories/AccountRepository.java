package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {


    Account findByNumber(String number);//Account findByNumber(String number) es un metodo que se encuentra en la clase JpaRepository
                                        //findByNumber es un metodo de la interfaz JpaRepository que busca un objeto de tipo Account por el numero de cuenta.
    Account findByAccountType(String accountType);
}


