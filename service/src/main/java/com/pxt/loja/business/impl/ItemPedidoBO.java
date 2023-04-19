package com.pxt.loja.business.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.ItemPedido;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.persistence.dao.ItemPedidoDAO;
@SuppressWarnings("all")
@Stateless
public class ItemPedidoBO {

	@EJB
	private ItemPedidoDAO itemPedidoDAO;
	
	@EJB
	private PersistenceService persistenceService;

	public void salvarItemPedido(ItemPedido itemPedido) throws PersistenceException, TransactionException{
		itemPedidoDAO.saveOrUpdate(itemPedido);
	}
	
	public List<ItemPedido> buscarItemPedido(Produto produto, Long pedido) throws PersistenceException{
		return itemPedidoDAO.buscarItemPedido(produto, pedido);
	}
}	