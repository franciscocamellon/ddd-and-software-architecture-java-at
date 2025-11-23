package com.camelloncase.api;

import com.camelloncase.application.PetQueryService;
import com.camelloncase.domain.Pet;
import com.camelloncase.events.DomainEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetQueryController {

    private final PetQueryService petQueryService;

    public PetQueryController(PetQueryService petQueryService) {
        this.petQueryService = petQueryService;
    }

    @GetMapping("/{id}/eventos")
    public ResponseEntity<List<DomainEvent>> listarEventos(@PathVariable("id") String petId) {
        var eventos = petQueryService.listarEventosDoPet(petId);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> obterPet(@PathVariable("id") String petId) {
        return petQueryService.obterPetPorId(petId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
