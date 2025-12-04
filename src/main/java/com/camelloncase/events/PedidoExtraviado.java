package com.camelloncase.events;

import java.time.Instant;

public class PedidoExtraviado extends BaseEvent<Long> implements DomainEvent {

    public final String type;
    public final Instant when;

    public PedidoExtraviado(Long pedidoId) {
        super(pedidoId);
        this.type = type();
        this.when = Instant.now();
    }

    @Override
    public String type() {
        return "PedidoExtraviado";
    }

    @Override
    public Instant occurredOn() {
        return when;
    }
}
