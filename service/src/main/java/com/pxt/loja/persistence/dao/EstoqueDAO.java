package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Produto;

@Stateless
public class EstoqueDAO extends LOJAHibernateDAO<Estoque, Produto> {
	
	@EJB
	PersistenceService persistenceService;

	
	@SuppressWarnings("unchecked")
	public List<Estoque> buscarPorExemplo(Estoque estoque) throws PersistenceException {

		try {
			Criteria criteria = getSession().createCriteria(Estoque.class);

			if (estoque.getProduto() != null
					&& estoque.getProduto().getCodigoProduto() != null) {
				criteria.add(Restrictions.eq("produto.codigoProduto", estoque.getProduto().getCodigoProduto()));
			}
			return criteria.list();
		}catch (Exception e) {
			throw new PersistenceException("Erro ao buscar Estoque do Produto");
		}
	}

	public Estoque buscarProdutoCodigo(Integer codigoProduto) throws PersistenceException {

		try {
			Criteria criteria = getSession().createCriteria(Estoque.class);
			if (codigoProduto != null) {
				criteria.add(Restrictions.eq("produto.codigoProduto",codigoProduto));
			}
			return (Estoque) criteria.uniqueResult();
		}catch (Exception e) {
			throw new PersistenceException("Erro ao buscar Produto", e);
		}
	}
	
	public Estoque salvarEstoque(Estoque estoque) throws TransactionException{
		try {
			return persistenceService.saveOrUpdate(estoque);
		}catch (Exception e) {
			throw new TransactionException("Falha ao salvar Estoque", e);
		}
	}
}
