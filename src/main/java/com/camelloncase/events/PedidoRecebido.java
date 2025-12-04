package com.camelloncase.events;

import java.time.Instant;

public class PedidoRecebido extends BaseEvent<Long> implements DomainEvent {

    public final String type;
    public final Instant when;

    public PedidoRecebido(Long pedidoId) {
        super(pedidoId);
        this.type = type();
        this.when = Instant.now();
    }

    @Override
    public String type() {
        return "PedidoRecebido";
    }

    @Override
    public Instant occurredOn() {
        return when;
    }
}
