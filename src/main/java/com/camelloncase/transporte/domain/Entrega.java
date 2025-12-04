package com.camelloncase.transporte.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "entregas")
public class Entrega implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long pedidoId;

    @Embedded
    private EnderecoEntrega enderecoDestino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private StatusEntrega status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Entrega() {
    }

    public Entrega(Long pedidoId, EnderecoEntrega enderecoDestino) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("pedidoId não pode ser nulo");
        }
        if (enderecoDestino == null) {
            throw new IllegalArgumentException("enderecoDestino não pode ser nulo");
        }
        this.pedidoId = pedidoId;
        this.enderecoDestino = enderecoDestino;
        this.status = StatusEntrega.CRIADA;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public EnderecoEntrega getEnderecoDestino() {
        return enderecoDestino;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void iniciarTransporte() {
        if (this.status != StatusEntrega.CRIADA) {
            throw new IllegalStateException("Só é possível iniciar transporte quando CRIADA");
        }
        this.status = StatusEntrega.EM_TRANSITO;
        this.updatedAt = Instant.now();
    }

    public void confirmarEntrega() {
        if (this.status != StatusEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Só é possível confirmar entrega quando EM_TRANSITO");
        }
        this.status = StatusEntrega.ENTREGUE;
        this.updatedAt = Instant.now();
    }

    public void registrarDevolucao() {
        if (this.status != StatusEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Só é possível registrar devolução quando EM_TRANSITO");
        }
        this.status = StatusEntrega.DEVOLVIDA;
        this.updatedAt = Instant.now();
    }

    public void marcarExtraviada() {
        if (this.status != StatusEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Só é possível marcar extravio quando EM_TRANSITO");
        }
        this.status = StatusEntrega.EXTRAVIADA;
        this.updatedAt = Instant.now();
    }

    public void cancelar() {
        if (this.status != StatusEntrega.CRIADA) {
            throw new IllegalStateException("Só é possível cancelar quando CRIADA");
        }
        this.status = StatusEntrega.CANCELADA;
        this.updatedAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrega entrega = (Entrega) o;
        return Objects.equals(id, entrega.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
