package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import pxt.framework.business.PersistenceService;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.ItemPedido;
import com.pxt.loja.domain.Produto;
@SuppressWarnings("all")
@Stateless
public class ItemPedidoDAO extends GenericDAO{

	@EJB
	private PersistenceService persistenceService;
	
	public List<ItemPedido> buscarItemPedido(Produto produto, Long pedido) throws PersistenceException{
		Criteria cri = getSession().createCriteria(ItemPedido.class,"ite");
		cri.createAlias("ite.produto", "prd");
		cri.createCriteria("pedido", "ped", JoinType.INNER_JOIN);
		if (produto != null && produto.getCodigo() != null){
			cri.add(Restrictions.eq("ite.produto", produto));
		}
		if(pedido != null){
			cri.add(Restrictions.eq("ped.numPed", pedido));
		}
		return cri.list();
	}
}
