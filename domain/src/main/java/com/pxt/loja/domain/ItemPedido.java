package com.pxt.loja.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "VITORSB.TLJITEPEDIDO")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ItemID id;
	
	private Integer quantidade;
	
	private BigDecimal valor;
	
	private BigDecimal subTotalItem;

	@EmbeddedId
	public ItemID getId() {
		return id;
	}

	public void setId(ItemID id) {
		this.id = id;
	}
	
	@Transient
	public ItemID getItemIdNaoNulo() {
		if(id == null) {
			id = new ItemID();
		}
		return id;
	}


	@Column(name = "QDEPED")
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public void adicionarQuantidade(Integer quantidade) {
		this.quantidade = this.quantidade + quantidade;
	}

	@Column(name = "VLRITE")
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Transient
	public Produto getProdutoNaoNulo() {
		return getItemIdNaoNulo().getProduto();
	}

	public void setProduto(Produto produto) {
		getItemIdNaoNulo().setProduto(produto);
	}
	
	@Transient
	public Pedido getPedidoNaoNulo() {
		return getItemIdNaoNulo().getPedido();
	}

	public void setPedido(Pedido pedido) {
		getItemIdNaoNulo().setPedido(pedido);
	}

	@Transient
	public BigDecimal getSubTotalItem() {
		return subTotalItem;
	}

	public void setSubTotalItem(BigDecimal subTotalItem) {
		this.subTotalItem = subTotalItem;
	}
	
	
	
	
}