package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.persistence.dao.FornecedorDAO;

@Stateless
public class FornecedorBO {

	@EJB
	private FornecedorDAO fornecedorDAO;
	
	
	public void salvarObjeto(Fornecedor fornecedor) throws TransactionException{
		fornecedorDAO.salvarFornecedor(fornecedor);
	}
	
	public List<Fornecedor> buscar(Fornecedor fornecedor) throws PersistenceException{
		return fornecedorDAO.buscar(fornecedor);
	}
	
}
