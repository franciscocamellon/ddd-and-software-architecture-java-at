package com.camelloncase.almoxarifado.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ordens_separacao")
public class OrdemSeparacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private StatusSeparacao status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "ordem_separacao_itens",
            joinColumns = @JoinColumn(name = "ordem_separacao_id")
    )
    private List<ItemSeparacao> itens = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected OrdemSeparacao() {
    }

    public OrdemSeparacao(Long pedidoId, List<ItemSeparacao> itens) {
        if (pedidoId == null) {
            throw new IllegalArgumentException("pedidoId não pode ser nulo");
        }
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Ordem de separação deve ter ao menos 1 item");
        }
        this.pedidoId = pedidoId;
        this.itens = new ArrayList<>(itens);
        this.status = StatusSeparacao.CRIADA;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public StatusSeparacao getStatus() {
        return status;
    }

    public List<ItemSeparacao> getItens() {
        return new ArrayList<>(itens);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void iniciarPreparacao() {
        if (this.status != StatusSeparacao.CRIADA) {
            throw new IllegalStateException("Só é possível iniciar preparação quando CRIADA");
        }
        this.status = StatusSeparacao.EM_PREPARACAO;
        this.updatedAt = Instant.now();
    }

    public void marcarProntaParaDespacho() {
        if (this.status != StatusSeparacao.EM_PREPARACAO) {
            throw new IllegalStateException("Só é possível finalizar separação quando EM_PREPARACAO");
        }
        this.status = StatusSeparacao.PRONTA_PARA_DESPACHO;
        this.updatedAt = Instant.now();
    }

    public void cancelar() {
        if (this.status == StatusSeparacao.PRONTA_PARA_DESPACHO) {
            throw new IllegalStateException("Não é possível cancelar após estar pronta para despacho");
        }
        this.status = StatusSeparacao.CANCELADA;
        this.updatedAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdemSeparacao that = (OrdemSeparacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
