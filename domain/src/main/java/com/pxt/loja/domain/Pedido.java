package com.pxt.loja.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name = "VITORSB.TLJPEDIDO")
public class Pedido implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer numero;
	
	private Cliente cliente;

	private Date dataPedido;

	private StatusPedido status;
	
	private List<ItemPedido> listaItens = new ArrayList<>();
	
	
	@Id
	@Column(name="NUMPED")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NUMPED_SEQ")
	@SequenceGenerator(name = "NUMPED_SEQ", sequenceName = "VITORSB.LJITEPEDIDO_SEQ", initialValue = 1, allocationSize = 1)
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	// Vários pedidos para 1 Cliente
	@ManyToOne(targetEntity = Cliente.class)
	@JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI")
	public Cliente getCliente() {
		return cliente;
	}
	
	@Transient
	public Cliente getClienteNaoNulo() {
		if(cliente == null){
			return new Cliente();
		}
		return this.cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Column(name = "DATPED")
	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	@Transient
	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
		
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="id.pedido")
	@Transient
	public List<ItemPedido> getListaItens() {
		return listaItens;
	}
	
	public void setListaItens(List<ItemPedido> listaItens) {
		this.listaItens = listaItens;
	}
	
	public void removerItem(ItemPedido item) {
		getListaItens().remove(item);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((dataPedido == null) ? 0 : dataPedido.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Pedido other = (Pedido) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataPedido == null) {
			if (other.dataPedido != null)
				return false;
		} else if (!dataPedido.equals(other.dataPedido))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [numPed=" + numero + ", cliente=" + cliente
				+ ", dataPedido=" + dataPedido + "]";
	}
	
}
