package com.camelloncase.events;

import java.time.Instant;

public class PetCriado extends BaseEvent<String> implements DomainEvent {

    public final String type;
    public final String nome;
    public final Instant when;

    public PetCriado(String id, String nome) {
        super(id);
		this.type = this.type();
        this.nome = nome;
        this.when = Instant.now();
    }

    @Override
	public String type() {
		return "PetCriado";
	}

    @Override
	public Instant occurredOn() {
		return when;
	}
}
