package com.camelloncase.events;

import java.time.Instant;

public class PetAdotado extends BaseEvent<String> implements DomainEvent {

	public final String type;
    public final String ownerId;
    public final Instant when;

    public PetAdotado(String id, String ownerId) {
        super(id);
		this.type = this.type();
        this.ownerId = ownerId;
        this.when = Instant.now();
    }

    @Override
	public String type() {
		return "PetAdotado";
	}

    @Override
	public Instant occurredOn() {
		return when;
	}
}
