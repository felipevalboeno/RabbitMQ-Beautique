package br.com.beautique.api.service;

import br.com.beautique.api.dtos.AppointmentDTO;

public interface AppointmentsService {

    AppointmentDTO create(AppointmentDTO appointmentsDTO);
    AppointmentDTO update(AppointmentDTO appointmentDTO);
    void deleteById(Long id);
    AppointmentDTO setCustomerToAppointment(AppointmentDTO appointmentDTO);

}
