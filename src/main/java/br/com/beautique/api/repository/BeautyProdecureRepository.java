package br.com.beautique.api.repository;

import br.com.beautique.api.entities.BeautyProceduresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeautyProdecureRepository extends JpaRepository<BeautyProceduresEntity, Long> {
}
