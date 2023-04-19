package com.pxt.loja.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

@Entity
@Table(name = "VITORSB.TLJESTOQUE")
public class Estoque implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Produto produto;
	
	private Integer quantidadeProduto;
	
	private Integer quantidadeRecebimento;

	
	@Id
	@OneToOne// Um estoque para um Produto
	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	@Transient
	public Produto getProdutoNaoNulo() {
		if(this.produto == null){
			return new Produto();
		}
		return getProduto();
	}
	
	public void setProdutoNaoNulo(Produto produto) {
		this.produto = produto;
	}
	
	@Column(name = "QDEPRO")
	@Min(value = 0, message = "A quantidade deve ser positivo!")
	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	@Column(name = "QDERCB")
	@Min(value = 0, message = "A quantidade deve ser positivo!")
	public Integer getQuantidadeRecebimento() {
		return quantidadeRecebimento;
	}

	public void setQuantidadeRecebimento(Integer quantidadeRecebimento) {
		this.quantidadeRecebimento = quantidadeRecebimento;
	}

	@Override
	public String toString() {
		return "Estoque [produto=" + produto + ", quantidadeProduto="
				+ quantidadeProduto + ", quantidadeRecebimento="
				+ quantidadeRecebimento + "]";
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
		return true;
	}

}
