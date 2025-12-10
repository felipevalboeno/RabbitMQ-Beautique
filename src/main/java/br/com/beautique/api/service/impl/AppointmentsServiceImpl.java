package br.com.beautique.api.service.impl;

import br.com.beautique.api.dtos.AppointmentDTO;
import br.com.beautique.api.entities.AppointmentsEntity;
import br.com.beautique.api.entities.BeautyProceduresEntity;
import br.com.beautique.api.entities.CustomerEntity;
import br.com.beautique.api.repository.AppointmentRepository;
import br.com.beautique.api.repository.BeautyProdecureRepository;
import br.com.beautique.api.repository.CustomerRepository;
import br.com.beautique.api.service.AppointmentsService;
import br.com.beautique.api.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BeautyProdecureRepository beautyProdecureRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final ConvertUtil<AppointmentsEntity, AppointmentDTO> convertUtil = new ConvertUtil<>(AppointmentsEntity.class, AppointmentDTO.class);


    @Override
    public AppointmentDTO create(AppointmentDTO appointmentsDTO) {
            AppointmentsEntity appointmentsEntity = convertUtil.convertToSource(appointmentsDTO);
            AppointmentsEntity newAppointmentsEntity = appointmentRepository.save(appointmentsEntity);
            return  convertUtil.convertToTarget(newAppointmentsEntity);

    }

    @Override
    public AppointmentDTO update(AppointmentDTO appointmentDTO) {
        Optional<AppointmentsEntity> currentAppointment = appointmentRepository.findById(appointmentDTO.getId());
        if(currentAppointment.isEmpty()){
            throw  new RuntimeException("Appointment not found");
        }
        //converte o DTO pra uma entity e armazena no appointmentsEntity
        AppointmentsEntity appointmentsEntity = convertUtil.convertToSource(appointmentDTO);
        //persiste o valor do created at, pois ele não é enviado, pegando ele do currentAppointment e setando no appointmentsEntity
        appointmentsEntity.setCreatedAt(currentAppointment.get().getCreatedAt());
        //salva no banco
        AppointmentsEntity updatedAppointmentEntity = appointmentRepository.save(appointmentsEntity);

        return convertUtil.convertToTarget(updatedAppointmentEntity);
    }

    @Override
    public void deleteById(Long id) {
        AppointmentsEntity appointmentsEntity =  appointmentRepository.findById(id)
                .orElseThrow(()  -> new RuntimeException("Appointment not found"));
        appointmentRepository.delete(appointmentsEntity);

    }

    @Override
    public AppointmentDTO setCustomerToAppointment(AppointmentDTO appointmentDTO) {
        CustomerEntity customerEntity = findCustomerById(appointmentDTO.getCustomer());
        BeautyProceduresEntity beautyProceduresEntity = findBeautyProceduresById(appointmentDTO.getBeautyProcedure());
        AppointmentsEntity appointmentsEntity = findAppointmentById(appointmentDTO.getId());
        appointmentsEntity.setCustomer(customerEntity);
        appointmentsEntity.setBeautyProcedure(beautyProceduresEntity);
        appointmentsEntity.setAppointmentOpen(false);

        AppointmentsEntity updatedAppointmentEntity = appointmentRepository.save(appointmentsEntity);


        return buildAppointmentDTO(updatedAppointmentEntity);
    }

    private AppointmentsEntity findAppointmentById(Long id){
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

    }

    private BeautyProceduresEntity findBeautyProceduresById(Long id){
        return beautyProdecureRepository.findById(id).orElseThrow(() -> new RuntimeException("Beauty procedure not found"));
    }

    private CustomerEntity findCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found0"));
    }

    private AppointmentDTO buildAppointmentDTO(AppointmentsEntity appointmentsEntity){
            return AppointmentDTO.builder()
                    .id(appointmentsEntity.getId())
                    .beautyProcedure(appointmentsEntity.getBeautyProcedure().getId())
                    .dateTime(appointmentsEntity.getDateTime())
                    .appointmentsOpen(appointmentsEntity.getAppointmentOpen())
                    .customer(appointmentsEntity.getCustomer().getId())
                    .build();
    }
}
