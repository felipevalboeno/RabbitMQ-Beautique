package br.com.beautique.api.service;

import br.com.beautique.api.dtos.BeautyProcedureDTO;
import br.com.beautique.api.dtos.CustomerDTO;
import org.springframework.stereotype.Service;

@Service
public interface BeautyProcedureService {
    //assinatura do metodo
    BeautyProcedureDTO create(BeautyProcedureDTO beautyProcedureDTO);
    void delete(Long id);

    BeautyProcedureDTO update(BeautyProcedureDTO beautyProcedureDTO);
}
