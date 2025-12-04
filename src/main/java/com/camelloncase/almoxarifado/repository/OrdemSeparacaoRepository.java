package com.camelloncase.almoxarifado.repository;

import com.camelloncase.almoxarifado.domain.OrdemSeparacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrdemSeparacaoRepository extends JpaRepository<OrdemSeparacao, UUID> {
    Optional<OrdemSeparacao> findByPedidoId(Long pedidoId);
}
