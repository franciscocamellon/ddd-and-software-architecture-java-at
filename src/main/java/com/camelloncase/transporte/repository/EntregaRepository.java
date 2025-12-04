package com.camelloncase.transporte.repository;

import com.camelloncase.transporte.domain.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntregaRepository extends JpaRepository<Entrega, UUID> {
    Optional<Entrega> findByPedidoId(Long pedidoId);
}
