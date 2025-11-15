package com.camelloncase.events;

import java.time.Instant;

public interface DomainEvent {
    String type();
    Instant occurredOn();
}
