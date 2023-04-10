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
@Table(name = "VITORSB.TLJFORNECEDOR")
public class Fornecedor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FRN_SEQ")
	@SequenceGenerator(name = "FRN_SEQ", sequenceName = "VITORSB.LJFORNECEDOR_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODFRN")
	private Integer codigo;
	
	@Column(name = "DESNOM")
	private String nome;
	
	@Column(name = "CNPJ")
	private String cnpj;

	@Column(name = "INDATV")
	private Boolean indicadorAtivo = true;

	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if(nome != null){
			nome = nome.trim(); // remove espaços em branco extras no início e no final
	        nome  = Normalizer.normalize(nome, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // Remove os acentos "á" to "a"
	        nome = nome.replaceAll("\\p{M}", ""); // Remove qualquer coisa que não seja letra ou numero"
	        nome = nome.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ªº]", "").trim(); //remove tudo o que não for uma letra ou espaço em branco.
	        nome = nome.replaceAll("\\d", ""); // Remove números
	    }
	    this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		if(cnpj != null){
			cnpj = cnpj.replaceAll("[.()'/-]","").replace(" ", "");
		}
		this.cnpj = cnpj;
	}

	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}

	@Override
	public String toString() {
		return "Fornecedor [codigo=" + codigo + ", nome=" + nome
				+ ", cpf=" + cnpj + ", indicadorAtivo=" + indicadorAtivo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result
				+ ((nome == null) ? 0 : nome.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (indicadorAtivo == null) {
			if (other.indicadorAtivo != null)
				return false;
		} else if (!indicadorAtivo.equals(other.indicadorAtivo))
			return false;
		return true;
	}


}
