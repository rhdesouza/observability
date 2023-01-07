package com.example.observability.infrastructure.persistence.repository;

import com.example.observability.infrastructure.persistence.entity.LocacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocacaoRepository extends JpaRepository<LocacaoEntity, Long> {

}
