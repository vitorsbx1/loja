//package com.pxt.loja.domain;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name = "VITORSB.TLJMOVIMENTACAOETQ")
//public class MovimentacaoEstoque implements Serializable {
//	
//	@Id
//	@Column(name = "CODMOV")
//	private Long codigoMovimentacao;
//
//	@Column(name = "DATMOV")
//	private Date dataMovimentacao;
//	
//	@ManyToOne(targetEntity = Produto.class) // Varias movimentacoes para um Produto
//	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
//	private Produto produto;
//	
//	@Column(name = "QDEMOV")
//	private Integer quantidadeMovimentacao;
//	
//	@Column(name = "DESOPE")
//	@Enumerated(EnumType.STRING)
//	private TipoOperacao tipoOperacao;
//
//	public Long getCodigoMovimentacao() {
//		return codigoMovimentacao;
//	}
//
//	public void setCodigoMovimentacao(Long codigoMovimentacao) {
//		this.codigoMovimentacao = codigoMovimentacao;
//	}
//
//	public Date getDataMovimentacao() {
//		return dataMovimentacao;
//	}
//
//	public void setDataMovimentacao(Date dataMovimentacao) {
//		this.dataMovimentacao = dataMovimentacao;
//	}
//
//	public Produto getProduto() {
//		return produto;
//	}
//
//	public void setProduto(Produto produto) {
//		this.produto = produto;
//	}
//
//	public Integer getQuantidadeMovimentacao() {
//		return quantidadeMovimentacao;
//	}
//
//	public void setQuantidadeMovimentacao(Integer quantidadeMovimentacao) {
//		this.quantidadeMovimentacao = quantidadeMovimentacao;
//	}
//
//	public TipoOperacao getTipoOperacao() {
//		return tipoOperacao;
//	}
//
//	public void setTipoOperacao(TipoOperacao tipoOperacao) {
//		this.tipoOperacao = tipoOperacao;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime
//				* result
//				+ ((codigoMovimentacao == null) ? 0 : codigoMovimentacao
//						.hashCode());
//		result = prime
//				* result
//				+ ((dataMovimentacao == null) ? 0 : dataMovimentacao.hashCode());
//		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
//		result = prime
//				* result
//				+ ((quantidadeMovimentacao == null) ? 0
//						: quantidadeMovimentacao.hashCode());
//		result = prime * result
//				+ ((tipoOperacao == null) ? 0 : tipoOperacao.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		MovimentacaoEstoque other = (MovimentacaoEstoque) obj;
//		if (codigoMovimentacao == null) {
//			if (other.codigoMovimentacao != null)
//				return false;
//		} else if (!codigoMovimentacao.equals(other.codigoMovimentacao))
//			return false;
//		if (dataMovimentacao == null) {
//			if (other.dataMovimentacao != null)
//				return false;
//		} else if (!dataMovimentacao.equals(other.dataMovimentacao))
//			return false;
//		if (produto == null) {
//			if (other.produto != null)
//				return false;
//		} else if (!produto.equals(other.produto))
//			return false;
//		if (quantidadeMovimentacao == null) {
//			if (other.quantidadeMovimentacao != null)
//				return false;
//		} else if (!quantidadeMovimentacao.equals(other.quantidadeMovimentacao))
//			return false;
//		if (tipoOperacao != other.tipoOperacao)
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		return "MovimentacaoEstoque [codigoMovimentacao=" + codigoMovimentacao
//				+ ", dataMovimentacao=" + dataMovimentacao + ", produto="
//				+ produto + ", quantidadeMovimentacao="
//				+ quantidadeMovimentacao + ", tipoOperacao=" + tipoOperacao
//				+ "]";
//	}
//	
//	
//	
//}
