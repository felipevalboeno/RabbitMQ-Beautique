package br.com.beautique.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;
    private LocalDateTime dateTime;
    private Boolean appointmentsOpen;

    private Long customer;
    private Long beautyProcedure;
}
