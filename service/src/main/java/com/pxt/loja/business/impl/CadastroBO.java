package com.pxt.loja.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.CadastroDAO;


@Stateless
public class CadastroBO {
	
	@EJB
	private CadastroDAO cadastroDAO;
	
	
	public CadastroBO(){}
	
	
	
}
