package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Cliente;
import com.pxt.loja.persistence.dao.ClienteDAO;

@Stateless
public class ClienteBO {

	@EJB
	private ClienteDAO clienteDAO;
	
	
	public void salvarCliente(Cliente cliente) throws TransactionException{
		clienteDAO.salvarCliente(cliente);
	}
	
	public List<Cliente> buscar(Cliente cliente) throws PersistenceException{
		return clienteDAO.buscar(cliente);
	}
	
}
