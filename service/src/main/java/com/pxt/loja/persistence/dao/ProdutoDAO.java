package com.pxt.loja.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;

@SuppressWarnings("all")
@Stateless
public class ProdutoDAO extends LOJAHibernateDAO<Produto, Integer>{

	@EJB
	private PersistenceService persistenceService;

	public void salvarProduto(Produto produto) throws TransactionException {
		try{
			persistenceService.saveOrUpdate(produto);
		}catch(Exception e){
			if(e instanceof SQLException){
				throw new TransactionException("Produto já cadastrado.", e);
			}
			throw new TransactionException("Erro ao salvar Produto",e);
		}
	}

	public List<Produto> buscar(Produto produto) throws PersistenceException{
		try{
			Criteria criteria = getSession().createCriteria(Produto.class);
			
			if(produto != null){
				if(produto.getCodigoProduto() != null){
					criteria.add(Restrictions.eq("codigoProduto", produto.getCodigoProduto()));
				}
				if(produto.getDescricaoProduto() != null && !produto.getDescricaoProduto().isEmpty()){
					criteria.add(Restrictions.like("descricaoProduto", produto.getDescricaoProduto(), MatchMode.ANYWHERE));
				}
			}			
			return criteria.list();
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar o Produto", e);
		}
		
	}
	
}
