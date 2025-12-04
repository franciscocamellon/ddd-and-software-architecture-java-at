package com.camelloncase.almoxarifado.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ItemSeparacao {

    private Long produtoId;
    private int quantidade;

    protected ItemSeparacao() {
    }

    public ItemSeparacao(Long produtoId, int quantidade) {
        if (produtoId == null) {
            throw new IllegalArgumentException("produtoId não pode ser nulo");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser maior que zero");
        }
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemSeparacao that = (ItemSeparacao) o;
        return quantidade == that.quantidade && Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produtoId, quantidade);
    }
}
