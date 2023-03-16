package com.pxt.loja.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "VITORSB.TLJPRODUTO")
public class Produto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_SEQ")
	@SequenceGenerator(name = "PRO_SEQ", sequenceName = "VITORSB.LJPRODUTO_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODPRO")
	private Long codigoProduto;
	
	@Column(name = "DESPRO")
	private String descricaoProduto;
	
	@Column(name = "TAMPRO")
	private Double tamanhoProduto;
	
	@ManyToOne(targetEntity = Marca.class) // Vários produtos para 1 Marca
	@JoinColumn(name = "CODMRC", referencedColumnName = "CODMRC")
	private Marca marca;
	
	@ManyToOne(targetEntity = Fornecedor.class) // Vários produtos para 1 Fornecedor
	@JoinColumn(name = "CODFRN", referencedColumnName = "CODFRN")
	private Fornecedor fornecedor;
	
	@Column(name = "MDLPRO")
	private String modeloProduto;
	
	@Column(name = "CTGPRO")
	private String categoriaProduto;
	
	@Column(name = "CORPRO")
	private String corProduto;
	
	@Column(name = "INDATV")
	private Boolean indicadorAtivo = true;

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public Double getTamanhoProduto() {
		return tamanhoProduto;
	}

	public void setTamanhoProduto(Double tamanhoProduto) {
		this.tamanhoProduto = tamanhoProduto;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getModeloProduto() {
		return modeloProduto;
	}

	public void setModeloProduto(String modeloProduto) {
		this.modeloProduto = modeloProduto;
	}

	public String getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(String categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public String getCorProduto() {
		return corProduto;
	}

	public void setCorProduto(String corProduto) {
		this.corProduto = corProduto;
	}

	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((categoriaProduto == null) ? 0 : categoriaProduto.hashCode());
		result = prime * result
				+ ((codigoProduto == null) ? 0 : codigoProduto.hashCode());
		result = prime * result
				+ ((corProduto == null) ? 0 : corProduto.hashCode());
		result = prime
				* result
				+ ((descricaoProduto == null) ? 0 : descricaoProduto.hashCode());
		result = prime * result
				+ ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result
				+ ((indicadorAtivo == null) ? 0 : indicadorAtivo.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result
				+ ((modeloProduto == null) ? 0 : modeloProduto.hashCode());
		result = prime * result
				+ ((tamanhoProduto == null) ? 0 : tamanhoProduto.hashCode());
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
		Produto other = (Produto) obj;
		if (categoriaProduto == null) {
			if (other.categoriaProduto != null)
				return false;
		} else if (!categoriaProduto.equals(other.categoriaProduto))
			return false;
		if (codigoProduto == null) {
			if (other.codigoProduto != null)
				return false;
		} else if (!codigoProduto.equals(other.codigoProduto))
			return false;
		if (corProduto == null) {
			if (other.corProduto != null)
				return false;
		} else if (!corProduto.equals(other.corProduto))
			return false;
		if (descricaoProduto == null) {
			if (other.descricaoProduto != null)
				return false;
		} else if (!descricaoProduto.equals(other.descricaoProduto))
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (indicadorAtivo == null) {
			if (other.indicadorAtivo != null)
				return false;
		} else if (!indicadorAtivo.equals(other.indicadorAtivo))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modeloProduto == null) {
			if (other.modeloProduto != null)
				return false;
		} else if (!modeloProduto.equals(other.modeloProduto))
			return false;
		if (tamanhoProduto == null) {
			if (other.tamanhoProduto != null)
				return false;
		} else if (!tamanhoProduto.equals(other.tamanhoProduto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [codigoProduto=" + codigoProduto
				+ ", descricaoProduto=" + descricaoProduto
				+ ", tamanhoProduto=" + tamanhoProduto + ", marca=" + marca
				+ ", fornecedor=" + fornecedor + ", modeloProduto="
				+ modeloProduto + ", categoriaProduto=" + categoriaProduto
				+ ", corProduto=" + corProduto + ", indicadorAtivo="
				+ indicadorAtivo + "]";
	}


	
	
	
}
