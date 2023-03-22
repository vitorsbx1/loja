package com.pxt.loja.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.domain.Produto;
import com.pxt.loja.persistence.dao.RecebimentoMercadoriaDAO;


@Stateless
public class RecebimentoMercadoriaBO {
	
	@EJB
	private RecebimentoMercadoriaDAO recebimentoMercadoriaDAO;
	
	public RecebimentoMercadoriaBO(){}
	
	public Produto inserirQuantidade(Produto produto){
		return produto;
	}
}
