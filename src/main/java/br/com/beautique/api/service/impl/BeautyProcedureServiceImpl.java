package br.com.beautique.api.service.impl;

import br.com.beautique.api.dtos.BeautyProcedureDTO;
import br.com.beautique.api.dtos.CustomerDTO;
import br.com.beautique.api.entities.BeautyProceduresEntity;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.repository.BeautyProdecureRepository;
import br.com.beautique.api.service.BeautyProcedureService;
import br.com.beautique.api.service.BrokerService;
import br.com.beautique.api.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeautyProcedureServiceImpl implements BeautyProcedureService {

    @Autowired
    private BeautyProdecureRepository beautyProdecureRepository;

    private final ConvertUtil<BeautyProceduresEntity, BeautyProcedureDTO>  convertUtil = new ConvertUtil<>(BeautyProceduresEntity.class, BeautyProcedureDTO.class);

    @Autowired
    private BrokerService brokerService;

    @Override
    public BeautyProcedureDTO create(BeautyProcedureDTO beautyProcedureDTO) {
        BeautyProceduresEntity beautyProceduresEntity = convertUtil.convertToSource(beautyProcedureDTO);
        BeautyProceduresEntity newBeautyProceduresEntity = beautyProdecureRepository.save(beautyProceduresEntity);
        sendBeautyProceduresToQueue(newBeautyProceduresEntity);
        return convertUtil.convertToTarget(newBeautyProceduresEntity);
    }

    @Override
    public void delete(Long id) {
        Optional<BeautyProceduresEntity> beautyProceduresEntityOptional = beautyProdecureRepository.findById(id);
        if(beautyProceduresEntityOptional.isEmpty()){
            throw new RuntimeException("Beauty procedure not found");
        }
        beautyProdecureRepository.deleteById(id);
    }

    @Override
    public BeautyProcedureDTO update(BeautyProcedureDTO beautyProcedureDTO) {

        Optional<BeautyProceduresEntity> beautyProceduresEntityOptional = beautyProdecureRepository.findById(beautyProcedureDTO.getId());

        if(beautyProceduresEntityOptional.isEmpty()){
            throw  new RuntimeException("Beauty procedure not found");
        }

        BeautyProceduresEntity beautyProceduresEntity = convertUtil.convertToSource(beautyProcedureDTO);

        beautyProceduresEntity.setAppointments(beautyProceduresEntityOptional.get().getAppointments());

        beautyProceduresEntity.setCreatedAt(beautyProceduresEntityOptional.get().getCreatedAt());

        BeautyProceduresEntity updatedBeautyProceduresEntity = beautyProdecureRepository.save(beautyProceduresEntity);

        sendBeautyProceduresToQueue(updatedBeautyProceduresEntity);


        return  convertUtil.convertToTarget(updatedBeautyProceduresEntity);
    }

private void sendBeautyProceduresToQueue(BeautyProceduresEntity beautyProceduresEntity) {
        // Implementation for sending beauty procedure data to RabbitMQ queue
        
        BeautyProcedureDTO beautyProcedureDTO = BeautyProcedureDTO.builder()
                .id(beautyProceduresEntity.getId())
                .name(beautyProceduresEntity.getName())
                .description(beautyProceduresEntity.getDescription())
                .price(beautyProceduresEntity.getPrice())
                .build();

                brokerService.send("beautyProcedures", beautyProcedureDTO);
    
    }


}


