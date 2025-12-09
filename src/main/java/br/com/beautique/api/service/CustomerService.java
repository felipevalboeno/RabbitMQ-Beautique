package br.com.beautique.api.service;

import br.com.beautique.api.dtos.CustomerDTO;
import br.com.beautique.api.entities.CustomerEntity;

public interface CustomerService {

    CustomerDTO create(CustomerDTO customerEntity);
    void delete(Long id);
}
