package br.com.beautique.api.controllers;

import br.com.beautique.api.dtos.AppointmentDTO;
import br.com.beautique.api.service.AppointmentsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentsController {

    @Autowired
    private AppointmentsService appointmentsService;


    @PostMapping
    ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO appointmentDTO){
        return ResponseEntity.ok(appointmentsService.create(appointmentDTO));
    }

    @PatchMapping
    ResponseEntity<AppointmentDTO> update(@RequestBody AppointmentDTO appointmentDTO){
        return ResponseEntity.ok(appointmentsService.update(appointmentDTO));
    }

}
