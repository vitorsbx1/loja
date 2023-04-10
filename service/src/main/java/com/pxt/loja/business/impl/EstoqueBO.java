package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.persistence.dao.EstoqueDAO;

@Stateless
public class EstoqueBO {

	@EJB
	private EstoqueDAO etqDAO;

	public List<Estoque> buscarPorExemplo(Estoque estoque) throws PersistenceException {
		return etqDAO.buscarPorExemplo(estoque);
	}
	
	public Estoque buscarProdutoCodigo(Integer codigoProduto) throws PersistenceException{
		return etqDAO.buscarProdutoCodigo(codigoProduto);
	}

	public Estoque salvarEstoque(Estoque estoque) throws TransactionException{
		return etqDAO.salvarEstoque(estoque);
	}
}
