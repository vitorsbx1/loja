package com.pxt.loja.domain;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Calendar;
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

	private Integer codigo;
	
	private String nome;
	
	private String cpfcnpj;

	private Date dataNascimento;
	
	private Boolean indicadorAtivo = true;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLI_SEQ")
	@SequenceGenerator(name = "CLI_SEQ", sequenceName = "VITORSB.LJCLIENTE_SEQ", initialValue = 1, allocationSize = 1)
	@Column(name = "CODCLI")
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "DESNOM")
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

	@Column(name = "CPFCNPJ")
	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		if(cpfcnpj != null){
			cpfcnpj = cpfcnpj.replaceAll("[.()'/-]","").replace(" ", "");
		}
		this.cpfcnpj = cpfcnpj;
	}

	@Column(name = "DATNAS")
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Column(name = "INDATV")
	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}
	
	public static boolean validarCPF(String cpf) {
	    if (cpf == null || cpf.length() != 11) {
	        return false;
	    }
	    
	    // Verifica se todos os caracteres são dígitos
	    for (int i = 0; i < 11; i++) {
	        if (!Character.isDigit(cpf.charAt(i))) {
	            return false;
	        }
	    }
	    
	    // Verifica se os dígitos verificadores estão corretos
	    int digito1 = Character.getNumericValue(cpf.charAt(9));
	    int digito2 = Character.getNumericValue(cpf.charAt(10));
	    
	    int soma = 0;
	    for (int i = 0; i < 9; i++) {
	        soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
	    }
	    
	    int resultado1 = soma % 11;
	    if (resultado1 < 2) {
	        resultado1 = 0;
	    } else {
	        resultado1 = 11 - resultado1;
	    }
	    
	    if (digito1 != resultado1) {
	        return false;
	    }
	    
	    soma = 0;
	    for (int i = 0; i < 10; i++) {
	        soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
	    }
	    
	    int resultado2 = soma % 11;
	    if (resultado2 < 2) {
	        resultado2 = 0;
	    } else {
	        resultado2 = 11 - resultado2;
	    }
	    
	    return digito2 == resultado2;
	}
	
	public boolean validaCaracteresCPF(String cpf) {    
	    for (int i = 0; i < cpf.length(); i++) {
	        if (cpf.charAt(i) != cpf.charAt(0)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static boolean validarCNPJ(String cnpj) {
	    if (cnpj == null || cnpj.length() != 14) {
	        return false;
	    }
	    
	    // Verifica se todos os caracteres são dígitos
	    for (int i = 0; i < 14; i++) {
	        if (!Character.isDigit(cnpj.charAt(i))) {
	            return false;
	        }
	    }
	    
	    // Verifica se os dígitos verificadores estão corretos
	    int[] digitosCNPJ = new int[14];
	    for (int i = 0; i < 14; i++) {
	        digitosCNPJ[i] = Character.getNumericValue(cnpj.charAt(i));
	    }
	    
	    int soma = digitosCNPJ[0] * 5 + digitosCNPJ[1] * 4 + digitosCNPJ[2] * 3 + digitosCNPJ[3] * 2 +
	               digitosCNPJ[4] * 9 + digitosCNPJ[5] * 8 + digitosCNPJ[6] * 7 + digitosCNPJ[7] * 6 +
	               digitosCNPJ[8] * 5 + digitosCNPJ[9] * 4 + digitosCNPJ[10] * 3 + digitosCNPJ[11] * 2;
	    
	    int resultado1 = soma % 11;
	    if (resultado1 < 2) {
	        resultado1 = 0;
	    } else {
	        resultado1 = 11 - resultado1;
	    }
	    
	    if (digitosCNPJ[12] != resultado1) {
	        return false;
	    }
	    
	    soma = digitosCNPJ[0] * 6 + digitosCNPJ[1] * 5 + digitosCNPJ[2] * 4 + digitosCNPJ[3] * 3 +
	           digitosCNPJ[4] * 2 + digitosCNPJ[5] * 9 + digitosCNPJ[6] * 8 + digitosCNPJ[7] * 7 +
	           digitosCNPJ[8] * 6 + digitosCNPJ[9] * 5 + digitosCNPJ[10] * 4 + digitosCNPJ[11] * 3 +
	           digitosCNPJ[12] * 2;
	    
	    int resultado2 = soma % 11;
	    if (resultado2 < 2) {
	        resultado2 = 0;
	    } else {
	        resultado2 = 11 - resultado2;
	    }
	    
	    return digitosCNPJ[13] == resultado2;
	}
	
	public boolean validaCaracteresCNPJ(String cnpj) {    
	    for (int i = 0; i < cnpj.length(); i++) {
	        if (cnpj.charAt(i) != cnpj.charAt(0)) {
	            return true;
	        }
	    }
	    return false;
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
		 
	public static boolean retornaIdadeEmAno(Date dataNascimento, Date dataAtualDate){
		Calendar dataNascimentoCalendar = Calendar.getInstance();
		dataNascimentoCalendar.setTime(dataNascimento);
		Calendar dataAtualCalendar = Calendar.getInstance();
		dataAtualCalendar.setTime(dataAtualDate);
		
		int anos = dataAtualCalendar.get(Calendar.YEAR) - dataNascimentoCalendar.get(Calendar.YEAR);
		
        if (dataAtualCalendar.get(Calendar.MONTH) < dataNascimentoCalendar.get(Calendar.MONTH) ||
            (dataAtualCalendar.get(Calendar.MONTH) == dataNascimentoCalendar.get(Calendar.MONTH) &&
             dataAtualCalendar.get(Calendar.DAY_OF_MONTH) < dataNascimentoCalendar.get(Calendar.DAY_OF_MONTH))) {
            anos--;
        }
        return anos >= 18;
	}
}
