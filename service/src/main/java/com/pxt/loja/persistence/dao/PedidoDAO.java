package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import pxt.framework.business.PersistenceService;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Produto;

@SuppressWarnings("all")
@Stateless
public class PedidoDAO extends GenericDAO{
	
	@EJB
	private PersistenceService persistenceService;
	
	//METODO COM SUBSELECT BASICO
	public List<Produto> buscarProdutoEstoqueDiponivel() throws PersistenceException{
		try{
		//Criar subselect para a tabela VITORSB.TLJESTOQUE
		DetachedCriteria subSelect = DetachedCriteria.forClass(Estoque.class, "estoque");
		subSelect.add(Restrictions.gt("quantidadeProduto", 0));
		subSelect.setProjection(Projections.property("produto.codigo"));
		
		//Criar o Select principal para a tabela VITORSB.TLJPRODUTO
		Criteria criteria = getSession().createCriteria(Produto.class, "produto");
		criteria.add(Subqueries.propertyIn("codigo", subSelect));
		
		//Executar
		List<Produto> listaProdutos = criteria.list();
		return listaProdutos;
		
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar produtos disponíveis!", e);
		}
	}
	
	//METODO COM SUBSELECT E EXISTS
	public List<Produto> buscarProdutoEstoqueDiponivelExists() throws PersistenceException{
		try{
		//Criar subselect com exists para a tabela VITORSB.TLJESTOQUE
		DetachedCriteria subSelect = DetachedCriteria.forClass(Estoque.class, "estoque");
		subSelect.add(Restrictions.gt("quantidadeProduto", 0));
		subSelect.add(Restrictions.eqProperty("produto.codigo", "pr.codigo"));
		subSelect.setProjection(Projections.property("produto.codigo"));
		
		//Criar o Select principal para a tabela VITORSB.TLJPRODUTO
		Criteria criteria = getSession().createCriteria(Produto.class, "pr");
		criteria.add(Subqueries.exists(subSelect));
		
		//Executar
		List<Produto> listaProdutos = criteria.list();
		return listaProdutos;
		
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar produtos disponíveis!", e);
		}
	}

}
