package com.camelloncase.events;

import java.time.Instant;

public class PedidoDespachado extends BaseEvent<Long> implements DomainEvent {

    public final String type;
    public final Instant when;

    public final String logradouro;
    public final String numero;
    public final String cidade;
    public final String uf;
    public final String cep;

    public PedidoDespachado(Long pedidoId, String logradouro, String numero, String cidade, String uf, String cep) {
        super(pedidoId);
        this.type = type();
        this.when = Instant.now();
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    @Override
    public String type() {
        return "PedidoDespachado";
    }

    @Override
    public Instant occurredOn() {
        return when;
    }
}
