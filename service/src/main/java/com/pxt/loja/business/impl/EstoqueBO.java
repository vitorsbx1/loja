package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.persistence.dao.EstoqueDAO;

@Stateless
public class EstoqueBO {

	@EJB
	private EstoqueDAO etqDAO;
	
	
	public List<Estoque> buscarPorExemplo(Estoque estoque){
		return etqDAO.buscarPorExemplo(estoque);
	}
	
}
