package com.pxt.loja.domain;

import java.io.Serializable;
import java.text.Normalizer;

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
	
	private Integer codigo;
	
	private String descricao;

	private Boolean indicadorAtivo = true;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MRC_SEQ")
	@SequenceGenerator(name = "MRC_SEQ", sequenceName = "VITORSB.LJMARCA_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODMRC")
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "DESMRC")
	public String getDescricao() {
	    return descricao;
	}

	public void setDescricao(String descricao) {
		if(descricao != null){
			descricao = descricao.trim(); // remove espaços em branco extras no início e no final
			descricao  = Normalizer.normalize(descricao, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");  // Remove os acentos "á" to "a"
			descricao = descricao.replaceAll("\\p{M}", ""); // Remove qualquer coisa que não seja letra ou numero"
			descricao = descricao.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ªº]", "").trim(); //remove tudo o que não for uma letra ou espaço em branco.
	    }
	    this.descricao = descricao;
	}

	@Column(name = "INDATV")
	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}

	@Override
	public String toString() {
		String retorno = null;
		if(codigo != null && descricao != null){
			retorno = codigo + " - " + descricao;
		}
		return retorno;
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
