package com.camelloncase.domain;

import com.camelloncase.domain.vo.ValorMonetario;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pedidos")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantidade;

	@Embedded
	private ValorMonetario totalItem;

	private Long idProduto;

	@JoinColumn(name = "PEDIDO_ID", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;

	public ItemPedido() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public ValorMonetario getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(ValorMonetario totalItem) {
		this.totalItem = totalItem;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public String toString() {
		return "ItemPedido{" +
				"id=" + id +
				", quantidade=" + quantidade +
				", totalItem=" + totalItem +
				", idProduto=" + idProduto +
				", pedido=" + pedido +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final ItemPedido itemPedido = (ItemPedido) o;
		return Objects.equals(id, itemPedido.id);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(id);
		return hash;
	}
}
