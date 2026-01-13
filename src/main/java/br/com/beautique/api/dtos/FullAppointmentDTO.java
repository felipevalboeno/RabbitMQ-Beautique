package br.com.beautique.api.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FullAppointmentDTO {
    

    private Long id;
    private LocalDateTime dateTime;
    private Boolean appointmentOpen;

    private CustomerDTO customer;
    private BeautyProcedureDTO beautyProcedure;
}
