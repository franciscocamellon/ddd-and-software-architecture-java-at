package com.camelloncase.domain;

import com.camelloncase.commands.AdotarPetCommand;
import com.camelloncase.commands.CriarPetCommand;
import com.camelloncase.events.PetAdotado;
import com.camelloncase.events.PetCriado;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Entity
@Aggregate
public class Pet {

	@Id
	@AggregateIdentifier
	private String id;
	private String nome;
	private String status;
	private String ownerId; // referência ao agregado owner

	protected Pet() { }

	@CommandHandler
	public Pet(CriarPetCommand cmd) {
		AggregateLifecycle.apply(new PetCriado(cmd.id, cmd.nome));
	}

	@CommandHandler
	public void handle(AdotarPetCommand cmd) {
		if ("ADOPTED".equals(this.status)) {
			throw new IllegalStateException("Pet já adotado.");
		}
		if (cmd.ownerId == null || cmd.ownerId.isBlank()) {
			throw new IllegalArgumentException("ownerId obrigatório.");
		}
		AggregateLifecycle.apply(new PetAdotado(cmd.id, cmd.ownerId));
	}

	@EventSourcingHandler
	public void on(PetCriado evt) {
		this.id = evt.id;
		this.nome = evt.nome;
		this.status = "AVAILABLE";
	}

	@EventSourcingHandler
	public void on(PetAdotado evt) {
		this.ownerId = evt.ownerId;
		this.status = "ADOPTED";
	}
}

