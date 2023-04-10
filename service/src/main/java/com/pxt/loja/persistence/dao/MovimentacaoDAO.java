package com.pxt.loja.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pxt.framework.commons.util.DateHelper;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.domain.TipoOperacao;


@SuppressWarnings("all")
@Stateless
public class MovimentacaoDAO extends LOJAHibernateDAO<MovimentacaoEstoque, Produto>{
	
	public List<Estoque> buscarPorExemplo(Estoque estoque) throws PersistenceException{
		
		try{
		Criteria criteria = getSession().createCriteria(Estoque.class);
		
		if(estoque.getProduto() != null && estoque.getProduto().getCodigoProduto() != null){
			criteria.add(Restrictions.eq("produto.codigoProduto", estoque.getProduto().getCodigoProduto()));
		}
		return criteria.list();
		 
		}catch(Exception e){
		  throw new PersistenceException("Erro ao buscar Estoque do Produto");
		}
	}
	
	public List<MovimentacaoEstoque> buscarRelatorioMovimentacao(Date dataInicial, Date dataFinal, TipoOperacao tipoOperacao, Produto produto) throws PersistenceException{
		try {
			Criteria criteria = getSession().createCriteria(MovimentacaoEstoque.class);
			
			if(dataInicial != null && dataFinal != null){
				criteria.add(Restrictions.ge("dataMovimentacao", DateHelper.addDays(dataFinal, 0, true)));
				criteria.add(Restrictions.lt("dataMovimentacao", DateHelper.addDays(dataFinal, 1, true)));
			}
			if(tipoOperacao != null){
				criteria.add(Restrictions.eq("tipoOperacao", tipoOperacao));
			}
			if(produto != null){
				criteria.add(Restrictions.eq("produto.codigoProduto", produto.getCodigoProduto()));
			}
			return criteria.list();
			
		} catch (Exception e) {
			throw new PersistenceException("Erro ao buscar lista de Movimentações", e);
		}
	}

}
