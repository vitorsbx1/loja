package com.pxt.loja.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;


@Entity
@Table(name = "VITORSB.TLJMOVIMENTACAOETQ")
public class MovimentacaoEstoque implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long codigo;

	private Date data;
	
	private Produto produto;
	
	private Integer quantidade;
	
	private TipoOperacao tipoOperacao;

	
	@Id
	@Column(name = "CODMOV")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOVETQ_SEQ")
	@SequenceGenerator(name = "MOVETQ_SEQ", sequenceName = "VITORSB.LJMOVETQ_SEQ", initialValue = 1, allocationSize = 1)
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Column(name = "DATMOV")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@ManyToOne(targetEntity = Produto.class) // Varias movimentacoes para um Produto
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

	@Column(name = "QDEMOV")
	@Min(value = 0, message = "A quantidade deve ser positiva!")
	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

	@Column(name = "DESOPE")
	@Enumerated(EnumType.STRING)
	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codigo == null) ? 0 : codigo
						.hashCode());
		result = prime
				* result
				+ ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime
				* result
				+ ((quantidade == null) ? 0
						: quantidade.hashCode());
		result = prime * result
				+ ((tipoOperacao == null) ? 0 : tipoOperacao.hashCode());
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
		MovimentacaoEstoque other = (MovimentacaoEstoque) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (tipoOperacao != other.tipoOperacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MovimentacaoEstoque [codigo=" + codigo
				+ ", dataMovimentacao=" + data + ", produto="
				+ produto + ", quantidadeMovimentacao="
				+ quantidade + ", tipoOperacao=" + tipoOperacao
				+ "]";
	}
	
}
