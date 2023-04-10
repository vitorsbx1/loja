package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Marca;
import com.pxt.loja.persistence.dao.MarcaDAO;

@Stateless
public class MarcaBO {

	@EJB
	private MarcaDAO marcaDAO;
	
	public void salvarMarca(Marca marca) throws TransactionException{
		marcaDAO.salvarMarca(marca);
	}
	
	public List<Marca> buscar(Marca marca) throws PersistenceException{
		return marcaDAO.buscar(marca);
	}
}
