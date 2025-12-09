package br.com.beautique.api.service.impl;

import br.com.beautique.api.dtos.CustomerDTO;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.repository.CustomerRepository;
import br.com.beautique.api.service.CustomerService;
import br.com.beautique.api.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CostumerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // CustomerEntity = Source, CustomerDTO = target
    private final ConvertUtil<CustomerEntity, CustomerDTO> convertUtil = new ConvertUtil<>(CustomerEntity.class, CustomerDTO.class);

    @Override
    public CustomerDTO create(CustomerDTO customerDto) {

        CustomerEntity customerEntity = convertUtil.convertToSource(customerDto);
        CustomerEntity newcustomerEntity = customerRepository.save(customerEntity);
        return convertUtil.convertToTarget(newcustomerEntity);

    }

    @Override
    public void delete(Long id) {
        //Optional é uma classe container que pode conter um valor não nulo
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(id);
        if(customerEntityOptional.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        customerRepository.delete(customerEntityOptional.get());
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) { //recebe e retorna customerDTO
        //filtrando por id usando repository e retornando um optional
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerDTO.getId());
        //se o optional vier vazio, lança exceção
        if (customerEntityOptional.isEmpty()){
            throw new RuntimeException("Customer not found");
        }
        //converte o DTO para customerEntity
        CustomerEntity customerEntity = convertUtil.convertToSource(customerDTO);

        //persistindo dados da entity
        customerEntity.setAppointments(customerEntityOptional.get().getAppointments());
        //persistindo o campo createdAt pois temos objeto novo aqui
        customerEntity.setCreatedAt(customerEntityOptional.get().getCreatedAt());

        //salvando a customerEntity e convertando o resultado pra um DTO
        return convertUtil.convertToTarget(customerRepository.save(customerEntity));
    }


}
