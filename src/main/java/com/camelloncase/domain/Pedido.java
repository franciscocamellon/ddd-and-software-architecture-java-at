package com.camelloncase.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataPedido;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> itensPedidos = new ArrayList<>();

	private Long idCliente;

	@Embedded
	private ValorMonetario valorMonetario;

	private PedidoStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public List<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}

	public void setItensPedidos(List<ItemPedido> itensPedidos) {
		this.itensPedidos = itensPedidos;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public ValorMonetario getValorMonetario() {
		return valorMonetario;
	}

	public void setValorMonetario(ValorMonetario valorMonetario) {
		this.valorMonetario = valorMonetario;
	}

	@Override
	public String toString() {
		return "Pedido{" +
				"id=" + id +
				", dataPedido=" + dataPedido +
				", itensPedidos=" + itensPedidos +
				", idCliente=" + idCliente +
				", valorMonetario=" + valorMonetario +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		final Pedido pedido = (Pedido) o;
		return Objects.equals(id, pedido.id);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(id);
		return hash;
	}

	public enum PedidoStatus {
		NOVO, FECHADO, CANCELADO, ENVIADO
	}

	public void adicionarItem(Long idProduto, int quantidade) {
		if (Objects.isNull(idProduto)) {
			throw new IllegalArgumentException("Produto inválido!");
		}
		if (quantidade <= 0) {
			throw new IllegalArgumentException("A quantidade precisa ser positiva");
		}
		if (this.status != PedidoStatus.NOVO) {
			throw new IllegalArgumentException("Não é possível inserir itens em um pedido em andamento!");
		}
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPedido(this);
		itemPedido.setIdProduto(idProduto);
		itemPedido.setQuantidade(quantidade);
		if (this.itensPedidos == null) {
			this.itensPedidos = new ArrayList<>();
		}
		this.itensPedidos.add(itemPedido);

	}

	public void fecharPedido() {
		if (this.status != PedidoStatus.NOVO) {
			throw new IllegalArgumentException("Não é possível fechar um pedido que não é novo!");
		}
		if (this.itensPedidos == null || this.itensPedidos.isEmpty()) {
			throw new IllegalArgumentException("Não é possível fechar um pedido vazio!");
		}
		this.status = PedidoStatus.FECHADO;
		//DomainEvents.publish(new pedidoFechadoEvent(this.id));
	}

	public void cancelarPedido() {
		if (this.status != PedidoStatus.FECHADO) {
			throw new IllegalArgumentException("Não é possível fechar um pedido que não esteja fechado!");
		}
		this.status = PedidoStatus.CANCELADO;
		//DomainEvents.publish(new pedidoCanceladoEvent(this.id));
	}

	public void enviarPedido() {
		if (this.status != PedidoStatus.FECHADO) {
			throw new IllegalArgumentException("Não é possível enviar um pedido que não esteja fechado!");
		}
		this.status = PedidoStatus.ENVIADO;
		//DomainEvents.publish(new pedidoEnviadoEvent(this.id));
	}
}
