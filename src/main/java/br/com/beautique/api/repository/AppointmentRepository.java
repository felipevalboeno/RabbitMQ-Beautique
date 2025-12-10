package br.com.beautique.api.repository;

import br.com.beautique.api.entities.AppointmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentsEntity, Long> {
}
