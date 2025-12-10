package br.com.beautique.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "appointments")
public class AppointmentsEntity  extends BaseEntity{

    @Column(nullable = false, updatable = true)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Boolean appointmentOpen;


    @ManyToOne(fetch = FetchType.LAZY) // relacionamento muitos pra um, só usa carga dos dados se necessário
    @JoinColumn(name = "customer_id", nullable = true)//determina a coluna de junção
    @ToString.Exclude// excluem campos dos métodos toString, equals, e hashcode
    @EqualsAndHashCode.Exclude// excluem campos dos métodos toString, equals, e hashcode do lombok
    private CustomerEntity customer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beauty_procedure_id", nullable = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BeautyProceduresEntity beautyProcedure;


}
