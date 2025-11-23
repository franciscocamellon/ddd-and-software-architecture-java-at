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
	private String ownerId;

	protected Pet() { }

	@CommandHandler
	public Pet(CriarPetCommand criarPetCommand) {
		AggregateLifecycle.apply(new PetCriado(criarPetCommand.id, criarPetCommand.nome));
	}

	@CommandHandler
	public void handle(AdotarPetCommand adotarPetCommand) {
		if ("ADOPTED".equals(this.status)) {
			throw new IllegalStateException("Pet já adotado.");
		}
		if (adotarPetCommand.ownerId == null || adotarPetCommand.ownerId.isBlank()) {
			throw new IllegalArgumentException("ownerId obrigatório.");
		}
		AggregateLifecycle.apply(new PetAdotado(adotarPetCommand.id, adotarPetCommand.ownerId));
	}

	@EventSourcingHandler
	public void on(PetCriado petCriadoEvent) {
		this.id = petCriadoEvent.id;
		this.nome = petCriadoEvent.nome;
		this.status = "AVAILABLE";
	}

	@EventSourcingHandler
	public void on(PetAdotado petAdotadoEvent) {
		this.ownerId = petAdotadoEvent.ownerId;
		this.status = "ADOPTED";
	}
}

