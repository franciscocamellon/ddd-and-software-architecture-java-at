package com.camelloncase.transporte.messaging;

import com.camelloncase.events.PedidoDespachado;
import com.camelloncase.events.PedidoExtraviado;
import com.camelloncase.events.PedidoRecebido;
import com.camelloncase.events.PedidoRejeitado;
import com.camelloncase.transporte.domain.EnderecoEntrega;
import com.camelloncase.transporte.domain.Entrega;
import com.camelloncase.transporte.repository.EntregaRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("transporte")
public class TransportePedidosEventsService {

    private final EntregaRepository entregaRepository;

    public TransportePedidosEventsService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    @EventHandler
    public void on(PedidoDespachado event) {
        if (entregaRepository.findByPedidoId(event.id).isPresent()) {
            return;
        }

        var endereco = new EnderecoEntrega(
                event.logradouro,
                event.numero,
                event.cidade,
                event.uf,
                event.cep
        );

        Entrega entrega = new Entrega(event.id, endereco);
        entrega.iniciarTransporte();
        entregaRepository.save(entrega);
    }

    @EventHandler
    public void on(PedidoRecebido event) {
        entregaRepository.findByPedidoId(event.id).ifPresent(entrega -> {
            entrega.confirmarEntrega();
            entregaRepository.save(entrega);
        });
    }

    @EventHandler
    public void on(PedidoRejeitado event) {
        entregaRepository.findByPedidoId(event.id).ifPresent(entrega -> {
            entrega.registrarDevolucao();
            entregaRepository.save(entrega);
        });
    }

    @EventHandler
    public void on(PedidoExtraviado event) {
        entregaRepository.findByPedidoId(event.id).ifPresent(entrega -> {
            entrega.marcarExtraviada();
            entregaRepository.save(entrega);
        });
    }
}
