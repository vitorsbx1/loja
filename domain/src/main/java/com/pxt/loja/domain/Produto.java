package com.pxt.loja.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "VITORSB.TLJPRODUTO")
public class Produto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_SEQ")
	@SequenceGenerator(name = "PRO_SEQ", sequenceName = "VITORSB.LJPRODUTO_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODPRO")
	private Long codigoProduto;
	
	@Column(name = "DESPRO")
	private String descricaoProduto;
	
	@Column(name = "TAMPRO")
	private Double tamanhoProduto;
	
	@Column(name = "MRCPRO")
	private String marcaProduto;
	
	@Column(name = "MDLPRO")
	private String modeloProduto;
	
	@Column(name = "CTGPRO")
	private String categoriaProduto;
	
	@Column(name = "CORPRO")
	private String corProduto;

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

	public String getMarcaProduto() {
		return marcaProduto;
	}

	public void setMarcaProduto(String marcaProduto) {
		this.marcaProduto = marcaProduto;
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
				+ ((marcaProduto == null) ? 0 : marcaProduto.hashCode());
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
		if (marcaProduto == null) {
			if (other.marcaProduto != null)
				return false;
		} else if (!marcaProduto.equals(other.marcaProduto))
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
				+ ", tamanhoProduto=" + tamanhoProduto + ", marcaProduto="
				+ marcaProduto + ", modeloProduto=" + modeloProduto
				+ ", categoriaProduto=" + categoriaProduto + ", corProduto="
				+ corProduto + "]";
	}

	
	
	
	
	
}
