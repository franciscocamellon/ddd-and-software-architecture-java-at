package com.camelloncase.events;

import java.time.Instant;
import java.util.List;

public class PedidoEnviado extends BaseEvent<Long> implements DomainEvent {

    public final String type;
    public final Instant when;
    public final List<PedidoItem> itens;

    public PedidoEnviado(Long pedidoId, List<PedidoItem> itens) {
        super(pedidoId);
        this.type = type();
        this.when = Instant.now();
        this.itens = itens;
    }

    @Override
    public String type() {
        return "PedidoEnviado";
    }

    @Override
    public Instant occurredOn() {
        return when;
    }

    public static class PedidoItem {
        public final Long produtoId;
        public final int quantidade;

        public PedidoItem(Long produtoId, int quantidade) {
            this.produtoId = produtoId;
            this.quantidade = quantidade;
        }
    }
}
