package com.camelloncase.application;

import com.camelloncase.domain.Pet;
import com.camelloncase.events.DomainEvent;
import com.camelloncase.repository.PetRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventhandling.DomainEventMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetQueryService {

    private final EventStore eventStore;
    private final PetRepository petRepository;

    public PetQueryService(EventStore eventStore, PetRepository petRepository) {
        this.eventStore = eventStore;
        this.petRepository = petRepository;
    }

    public List<DomainEvent> listarEventosDoPet(String petId) {
        return eventStore.readEvents(petId)
                .asStream()
                .map(DomainEventMessage::getPayload)
                .filter(payload -> payload instanceof DomainEvent)
                .map(payload -> (DomainEvent) payload)
                .collect(Collectors.toList());
    }

    public Optional<Pet> obterPetPorId(String petId) {
        return petRepository.findById(petId);
    }
}
