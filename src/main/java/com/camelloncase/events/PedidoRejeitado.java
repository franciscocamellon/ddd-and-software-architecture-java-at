package com.camelloncase.events;

import java.time.Instant;

public class PedidoRejeitado extends BaseEvent<Long> implements DomainEvent {

    public final String type;
    public final Instant when;

    public PedidoRejeitado(Long pedidoId) {
        super(pedidoId);
        this.type = type();
        this.when = Instant.now();
    }

    @Override
    public String type() {
        return "PedidoRejeitado";
    }

    @Override
    public Instant occurredOn() {
        return when;
    }
}
