package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Produto;

@Stateless
public class EstoqueDAO extends LOJAHibernateDAO<Estoque, Produto>{

	@SuppressWarnings("unchecked")
	public List<Estoque> buscarPorExemplo(Estoque estoque) {
		Criteria criteria = getSession().createCriteria(Estoque.class);
		
		if(estoque.getProduto() != null && estoque.getProduto().getCodigoProduto() != null){
			criteria.add(Restrictions.eq("produto.codigoProduto", estoque.getProduto().getCodigoProduto()));
		}
		
		return criteria.list();
		
	}

}
