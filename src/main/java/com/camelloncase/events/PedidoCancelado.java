package com.camelloncase.events;

import java.time.Instant;

public class PedidoCancelado extends BaseEvent<Long> implements DomainEvent {

    public final String type;
    public final Instant when;

    public PedidoCancelado(Long pedidoId) {
        super(pedidoId);
        this.type = type();
        this.when = Instant.now();
    }

    @Override
    public String type() {
        return "PedidoCancelado";
    }

    @Override
    public Instant occurredOn() {
        return when;
    }
}
