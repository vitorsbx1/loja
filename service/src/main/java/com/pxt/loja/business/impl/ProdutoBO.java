package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.persistence.dao.ProdutoDAO;

@Stateless
public class ProdutoBO {

	@EJB
	private ProdutoDAO produtoDAO;
	
	public void salvarProduto(Produto produto) throws TransactionException{
		produtoDAO.salvarProduto(produto);
	}
	
	public List<Produto> buscar(Produto produto) throws PersistenceException{
		return produtoDAO.buscar(produto);
	}
	
	public Boolean produtoDuplicadoExists(String descricao, Marca marca, Fornecedor fornecedor, Float tamanho) throws PersistenceException{
		return produtoDAO.produtoDuplicadoExists(descricao, marca, fornecedor, tamanho);
	}
}

