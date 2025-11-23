package com.camelloncase.api;

import com.camelloncase.application.PetAdoptionService;
import com.camelloncase.commands.CriarPetCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/pets")
public class PetCommandController {

    private final PetAdoptionService petAdoptionService;
	private final CommandGateway commandGateway;

	public PetCommandController(PetAdoptionService petAdoptionService,
								CommandGateway commandGateway) {
		this.petAdoptionService = petAdoptionService;
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public CompletableFuture<ResponseEntity<String>> criarPet(
			@RequestParam("nome") String nome) {

		String petId = UUID.randomUUID().toString();

		return commandGateway.send(new CriarPetCommand(petId, nome))
				.thenApply(result ->
						ResponseEntity.ok("Pet criado com id=" + petId + ", nome=" + nome));
	}

    @PostMapping("/{id}/adotar")
    public CompletableFuture<ResponseEntity<String>> adotarPet(
            @PathVariable("id") String petId,
            @RequestParam("ownerId") String ownerId) {

        return petAdoptionService.adotar(petId, ownerId)
                .thenApply(ResponseEntity::ok);
    }
}
