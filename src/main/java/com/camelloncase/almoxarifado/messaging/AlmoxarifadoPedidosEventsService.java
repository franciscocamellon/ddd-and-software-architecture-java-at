package com.camelloncase.almoxarifado.messaging;

import com.camelloncase.almoxarifado.domain.ItemSeparacao;
import com.camelloncase.almoxarifado.domain.OrdemSeparacao;
import com.camelloncase.almoxarifado.repository.OrdemSeparacaoRepository;
import com.camelloncase.events.PedidoCancelado;
import com.camelloncase.events.PedidoEnviado;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@ProcessingGroup("almoxarifado")
public class AlmoxarifadoPedidosEventsService {

    private final OrdemSeparacaoRepository ordemSeparacaoRepository;

    public AlmoxarifadoPedidosEventsService(OrdemSeparacaoRepository ordemSeparacaoRepository) {
        this.ordemSeparacaoRepository = ordemSeparacaoRepository;
    }

    @EventHandler
    public void on(PedidoEnviado event) {

        if (ordemSeparacaoRepository.findByPedidoId(event.id).isPresent()) {
            return;
        }

        var itens = event.itens.stream()
                .map(i -> new ItemSeparacao(i.produtoId, i.quantidade))
                .collect(Collectors.toList());

        ordemSeparacaoRepository.save(new OrdemSeparacao(event.id, itens));
    }

    @EventHandler
    public void on(PedidoCancelado event) {
        ordemSeparacaoRepository.findByPedidoId(event.id)
                .ifPresent(ordem -> {
                    ordem.cancelar();
                    ordemSeparacaoRepository.save(ordem);
                });
    }
}
