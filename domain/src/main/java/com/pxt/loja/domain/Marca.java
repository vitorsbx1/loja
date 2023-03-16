package com.pxt.loja.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "VITORSB.TLJMARCA")
public class Marca implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MRC_SEQ")
	@SequenceGenerator(name = "MRC_SEQ", sequenceName = "VITORSB.LJMARCA_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODMRC")
	private Long codigo;
	
	@Column(name = "DESMRC")
	private String descricao;

	@Column(name = "INDATV")
	private Boolean indicadorAtivo = true;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}

	@Override
	public String toString() {
		return "Marca [codigo=" + codigo + ", descricao=" + descricao
				+ ", indicadorAtivo=" + indicadorAtivo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((indicadorAtivo == null) ? 0 : indicadorAtivo.hashCode());
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
		Marca other = (Marca) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (indicadorAtivo == null) {
			if (other.indicadorAtivo != null)
				return false;
		} else if (!indicadorAtivo.equals(other.indicadorAtivo))
			return false;
		return true;
	}
	
	
	
}
