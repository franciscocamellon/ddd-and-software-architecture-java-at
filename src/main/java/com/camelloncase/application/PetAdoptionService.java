package com.camelloncase.application;

import com.camelloncase.commands.AdotarPetCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PetAdoptionService {

    private final CommandGateway commandGateway;

    public PetAdoptionService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
	}

    public CompletableFuture<String> adotar(String petId, String ownerId) {

        return commandGateway.send(new AdotarPetCommand(petId, ownerId))
            .thenApply(x -> "Adoção solicitada para pet=" + petId + " owner=" + ownerId);
    }
}
