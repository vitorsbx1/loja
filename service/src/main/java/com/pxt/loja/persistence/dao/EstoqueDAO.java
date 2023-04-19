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

@SuppressWarnings("all")
@Stateless
public class EstoqueDAO extends LOJAHibernateDAO<Estoque, Produto> {
	
	@EJB
	PersistenceService persistenceService;

	public List<Estoque> buscarPorExemplo(Estoque estoque) throws PersistenceException {
		try {
			Criteria criteria = getSession().createCriteria(Estoque.class);

			if (estoque.getProduto() != null
					&& estoque.getProduto().getCodigo() != null) {
				criteria.add(Restrictions.eq("produto.codigo", estoque.getProduto().getCodigo()));
			}
			return criteria.list();
		}catch (Exception e) {
			throw new PersistenceException("Erro ao buscar Estoque do Produto");
		}
	}

	public Estoque buscarProdutoCodigo(Integer codigo) throws PersistenceException {
		try {
			Criteria criteria = getSession().createCriteria(Estoque.class);
			if (codigo != null) {
				criteria.add(Restrictions.eq("produto.codigo",codigo));
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
	
	public List<Estoque> buscar(Estoque estoque, List<Produto> produtosEncontrados) throws PersistenceException{
		try{
			Criteria criteria = getSession().createCriteria(Estoque.class);
			
			if(estoque.getProduto() != null && estoque.getProduto().getCodigo() != null){
				criteria.add(Restrictions.eq("produto.codigo", estoque.getProduto().getCodigo()));
			}
			if(!produtosEncontrados.isEmpty()){
				criteria.add(Restrictions.in("produto", produtosEncontrados));
			}
			
			return criteria.list();
			
		}catch(Exception e){
			throw new PersistenceException("Não foi psosível buscar o estoque!" , e);
		}
	}
}
