package com.pxt.loja.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Embeddable
public class ItemID implements Serializable {
	
	private static final long serialVersionUID = -3806355357587826941L;

	private Pedido pedido;

	private Produto produto;

	/**
	 * retorna o pedido do item
	 * @return
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "NUMPED", referencedColumnName = "NUMPED")
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * responsavel por retorno do produto
	 * @return
	 */

	@OneToOne
	@JoinColumn(name = "CODITE", referencedColumnName = "CODPRO")
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
