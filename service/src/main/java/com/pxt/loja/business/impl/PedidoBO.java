package com.pxt.loja.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.ItemPedido;
import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.Pedido;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.domain.StatusPedido;
import com.pxt.loja.domain.TipoOperacao;
import com.pxt.loja.persistence.dao.PedidoDAO;
@SuppressWarnings("all")
@Stateless
public class PedidoBO {

	@EJB
	private PedidoDAO pedidoDAO;
	
	@EJB
	private ItemPedidoBO itemPedidoBO;
	
	@EJB
	private EstoqueBO estoqueBO;
	
	@EJB
	private MovimentacaoBO movimentacaoBO;
	
	@Inject
	private EntityManager entityManager;
	
	private static List<ItemPedido> listaItensPedido = new ArrayList<>();
	
	
	public List<Produto> buscarProdutoEstoqueDiponivel() throws PersistenceException{
		return pedidoDAO.buscarProdutoEstoqueDiponivel();
	}
	
	public List<Produto> buscarProdutoEstoqueDiponivelExists() throws PersistenceException{
		return pedidoDAO.buscarProdutoEstoqueDiponivelExists();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvarPedido(Pedido pedido) throws PersistenceException{
		try{
			Pedido novoPedido = new Pedido();
			Date data = new Date();
				
			novoPedido.setDataPedido(data);
			novoPedido.setCliente(pedido.getCliente());
			novoPedido.setStatus(StatusPedido.EM_ANDAMENTO);
			
			pedidoDAO.save(novoPedido);
			
			for(ItemPedido item : pedido.getListaItens()){
				item.setPedido(novoPedido);
				itemPedidoBO.salvarItemPedido(item);
				
				MovimentacaoEstoque novaMovimentacao = new MovimentacaoEstoque();
				novaMovimentacao.setData(data);
				novaMovimentacao.setProduto(item.getProdutoNaoNulo());
				novaMovimentacao.setQuantidade(item.getQuantidade());
				novaMovimentacao.setTipoOperacao(TipoOperacao.VENDIDO);
				 	
				movimentacaoBO.salvarMovimentacaoEstoque(novaMovimentacao);
				
			}
			
		}catch(Exception e){
			throw new PersistenceException("Erro ao salvar pedido", e);
		}
	}
}
