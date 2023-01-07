package com.example.observability.infrastructure.persistence.repository;

import com.example.observability.infrastructure.persistence.entity.MontadoraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MontadoraRepository extends JpaRepository<MontadoraEntity, Long> {

}
