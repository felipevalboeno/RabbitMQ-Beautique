package br.com.beautique.api.service;

import br.com.beautique.api.dtos.CustomerDTO;
import br.com.beautique.api.entities.CustomerEntity;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    CustomerDTO create(CustomerDTO customerEntity);
    void delete(Long id);

    CustomerDTO update(CustomerDTO customerDTO);
}
