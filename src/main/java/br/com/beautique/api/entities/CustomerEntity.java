package br.com.beautique.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CustomerEntity extends BaseEntity{

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(nullable = false, length = 100)
    private String email;


    @JsonIgnore//Ignora o campo durante serialização do json, evita expor detalhes sensíveis.
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AppointmentsEntity> appointments;
}





