
package com.camelloncase.application;

import com.camelloncase.commands.AdotarPetCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.concurrent.CompletableFuture;

public class PetAdoptionService {

    private final CommandGateway commandGateway;

    public PetAdoptionService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> adotar(String petId, String ownerId) {
        return commandGateway.send(new AdotarPetCommand(petId, ownerId))
            .thenApply(x -> "Adocao solicitada para pet=" + petId + " owner=" + ownerId);
    }
}
