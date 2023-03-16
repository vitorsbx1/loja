package com.pxt.loja.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "VITORSB.TLJCLIENTE")
public class Cliente implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLI_SEQ")
	@SequenceGenerator(name = "CLI_SEQ", sequenceName = "VITORSB.LJCLIENTE_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODCLI")
	private Long codigo;
	
	@Column(name = "DESNOM")
	private String nome;
	
	@Column(name = "CPFCNPJ")
	private String cpfcnpj;

	
	@Column(name = "DATNAS")
	private Date dataNascimento;
	
	@Column(name = "INDATV")
	private Boolean indicadorAtivo = true;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((cpfcnpj == null) ? 0 : cpfcnpj.hashCode());
		result = prime * result
				+ ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result
				+ ((indicadorAtivo == null) ? 0 : indicadorAtivo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cliente other = (Cliente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (cpfcnpj == null) {
			if (other.cpfcnpj != null)
				return false;
		} else if (!cpfcnpj.equals(other.cpfcnpj))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (indicadorAtivo == null) {
			if (other.indicadorAtivo != null)
				return false;
		} else if (!indicadorAtivo.equals(other.indicadorAtivo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [codigo=" + codigo + ", nome=" + nome + ", cpfcnpj="
				+ cpfcnpj + ", dataNascimento=" + dataNascimento
				+ ", indicadorAtivo=" + indicadorAtivo + "]";
	}
	

	
	
	
}
