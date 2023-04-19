package com.pxt.loja.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.persistence.dao.EstoqueDAO;

@Stateless
public class EstoqueBO {

	@EJB
	private EstoqueDAO estoqueDAO;

	@EJB
	private ProdutoBO produtoBO;

	public List<Estoque> buscar(Estoque estoque) throws PersistenceException {
		return estoqueDAO.buscarPorExemplo(estoque);
	}

	public Estoque buscarProdutoCodigo(Integer codigoProduto)
			throws PersistenceException {
		return estoqueDAO.buscarProdutoCodigo(codigoProduto);
	}

	public Estoque salvarEstoque(Estoque estoque) throws TransactionException {
		return estoqueDAO.salvarEstoque(estoque);
	}

	public List<Estoque> buscar(Estoque estoque, Marca marca,Fornecedor fornecedor) throws PersistenceException {
		List<Produto> produtosEncontrados = new ArrayList<>();

		if (marca != null || fornecedor != null) {
			Produto produtoBusca = new Produto();
			produtoBusca.setMarca(marca);
			produtoBusca.setFornecedor(fornecedor);

			produtosEncontrados = produtoBO.buscar(produtoBusca);

			if (produtosEncontrados.isEmpty()) {
				throw new PersistenceException("Nenhum produto encontrado no estoque!");
			}
		}
		return estoqueDAO.buscar(estoque, produtosEncontrados);
	}

}
