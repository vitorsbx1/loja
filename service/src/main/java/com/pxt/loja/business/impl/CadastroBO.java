package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.persistence.dao.CadastroDAO;


@Stateless
public class CadastroBO {
	
	@EJB
	private CadastroDAO cadastroDAO;
	
	public CadastroBO(){}
	

	public List<Marca> buscarMarca(Marca marca) throws PersistenceException{
		return cadastroDAO.buscarMarca(marca);
	}
	
	public List<Fornecedor> buscarFornecedor(Fornecedor fornecedor) throws PersistenceException{
		return cadastroDAO.buscarFornecedor(fornecedor);
	}
}
