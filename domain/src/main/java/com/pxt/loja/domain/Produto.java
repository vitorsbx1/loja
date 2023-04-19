package com.pxt.loja.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "VITORSB.TLJPRODUTO")
public class Produto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String descricao;

	private Float tamanho;

	private Marca marca;

	private Fornecedor fornecedor;
	
	private String modelo;

	private String categoria;

	private String cor;

	private Boolean indicadorAtivo = true;

	private BigDecimal valor;

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_SEQ")
	@SequenceGenerator(name = "PRO_SEQ", sequenceName = "VITORSB.LJPRODUTO_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODPRO")
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "DESPRO")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		if(descricao != null){
			descricao = descricao.trim(); // remove espaços em branco extras no início e no final
			descricao  = Normalizer.normalize(descricao, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			descricao = descricao.replaceAll("\\p{M}", "");
			descricao = descricao.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ªº]", "").trim();
	    }
	    this.descricao = descricao;
	}
	
	@Column(name = "TAMPRO")
	public Float getTamanho() {
		return tamanho;
	}

	public void setTamanho(Float tamanho) {
		this.tamanho = tamanho;
	}

	@ManyToOne(targetEntity = Marca.class)
	// Vários produtos para 1 Marca
	@JoinColumn(name = "CODMRC", referencedColumnName = "CODMRC")
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
	    this.marca = marca;
	}

	@Transient
	public Marca getMarcaNaoNulo() {
		if (this.marca == null) {
			return new Marca();
		}
		return getMarca();
	}

	public void setMarcaNaoNulo(Marca marca) {
		this.marca = marca;
	}

	@ManyToOne(targetEntity = Fornecedor.class)
	// Vários produtos para 1 Fornecedor
	@JoinColumn(name = "CODFRN", referencedColumnName = "CODFRN")
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Transient
	public Fornecedor getFornecedorNaoNulo() {
		if (this.fornecedor == null) {
			return new Fornecedor();
		}
		return getFornecedor();
	}

	public void setFornecedorNaoNulo(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Column(name = "MDLPRO")
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		if(modelo != null){
			modelo = modelo.trim(); // remove espaços em branco extras no início e no final
			modelo  = Normalizer.normalize(modelo, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");  // Remove os acentos "á" to "a"
			modelo = modelo.replaceAll("\\p{M}", ""); // Remove qualquer coisa que não seja letra ou numero"
			modelo = modelo.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ªº]", "").trim(); //remove tudo o que não for uma letra ou espaço em branco.
	    }
	    this.modelo = modelo;
	}

	@Column(name = "CTGPRO")
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		if(categoria != null){
			categoria = categoria.trim(); // remove espaços em branco extras no início e no final
			categoria  = Normalizer.normalize(categoria, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");  // Remove os acentos "á" to "a"
			categoria = categoria.replaceAll("\\p{M}", ""); // Remove qualquer coisa que não seja letra ou numero"
			categoria = categoria.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit} ªº]", "").trim(); //remove tudo o que não for uma letra ou espaço em branco.
	    }
	    this.categoria = categoria;
	}

	@Column(name = "CORPRO")
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	@Column(name = "INDATV")
	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}

	@Column(name = "VLRPRO")
	@Min(value = 0, message = "O valor deve ser positivo!")
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result
				+ ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result
				+ ((cor == null) ? 0 : cor.hashCode());
		result = prime
				* result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result
				+ ((indicadorAtivo == null) ? 0 : indicadorAtivo.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result
				+ ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result
				+ ((tamanho == null) ? 0 : tamanho.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (tamanho != other.tamanho)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [codigo=" + codigo
				+ ", descricao=" + descricao
				+ ", tamanho=" + tamanho + ", marca=" + marca
				+ ", fornecedor=" + fornecedor + ", modelo="
				+ modelo + ", categoria=" + categoria
				+ ", cor=" + cor + ", indicadorAtivo="
				+ indicadorAtivo + ", valor=" + valor + "]";
	}

}
