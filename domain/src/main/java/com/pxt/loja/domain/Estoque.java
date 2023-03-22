package com.pxt.loja.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VITORSB.TLJESTOQUE")
public class Estoque implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne// Um estoque para um Produto
	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
	private Produto produto;
	
	@Column(name = "QDEPRO")
	private Integer quantidadeProduto = 0;
	
	@Column(name = "QDERSV")
	private Integer quantidadeReserva;
	
	@Column(name = "QDERCB")
	private Integer quantidadeRecebimento;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Produto getProdutoNaoNulo() {
		if(this.produto == null){
			return new Produto();
		}
		return getProduto();
	}
	
	public void setProdutoNaoNulo(Produto produto) {
		this.produto = produto;
	}
	

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public Integer getQuantidadeReserva() {
		return quantidadeReserva;
	}

	public void setQuantidadeReserva(Integer quantidadeReserva) {
		this.quantidadeReserva = quantidadeReserva;
	}

	public Integer getQuantidadeRecebimento() {
		return quantidadeRecebimento;
	}

	public void setQuantidadeRecebimento(Integer quantidadeRecebimento) {
		this.quantidadeRecebimento = quantidadeRecebimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime
				* result
				+ ((quantidadeProduto == null) ? 0 : quantidadeProduto
						.hashCode());
		result = prime
				* result
				+ ((quantidadeRecebimento == null) ? 0 : quantidadeRecebimento
						.hashCode());
		result = prime
				* result
				+ ((quantidadeReserva == null) ? 0 : quantidadeReserva
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidadeProduto == null) {
			if (other.quantidadeProduto != null)
				return false;
		} else if (!quantidadeProduto.equals(other.quantidadeProduto))
			return false;
		if (quantidadeRecebimento == null) {
			if (other.quantidadeRecebimento != null)
				return false;
		} else if (!quantidadeRecebimento.equals(other.quantidadeRecebimento))
			return false;
		if (quantidadeReserva == null) {
			if (other.quantidadeReserva != null)
				return false;
		} else if (!quantidadeReserva.equals(other.quantidadeReserva))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estoque [produto=" + produto + ", quantidadeProduto="
				+ quantidadeProduto + ", quantidadeReserva="
				+ quantidadeReserva + ", quantidadeRecebimento="
				+ quantidadeRecebimento + "]";
	}
	
	
	

}
