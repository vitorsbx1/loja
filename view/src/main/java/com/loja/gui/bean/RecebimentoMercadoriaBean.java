package com.loja.gui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;

import com.pxt.loja.business.impl.EstoqueBO;
import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class RecebimentoMercadoriaBean extends CrudController<Estoque> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersistenceService persistenceService;
	
	@EJB
	private EstoqueBO estoqueBO;

	private Estoque domain;

	private MovimentacaoEstoque movimentacaoEstoque;

	private SearchFieldController<Produto> searchProduto;
	
	@Override
	public Estoque getDomain() {
		if (domain == null) {
			domain = new Estoque();
		}
		return domain;
	}



	public MovimentacaoEstoque getMovimentacaoEstoque() {
		if (movimentacaoEstoque == null) {
			movimentacaoEstoque = new MovimentacaoEstoque();
		}
		return movimentacaoEstoque;
	}
	
	public void setMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) {
		this.movimentacaoEstoque = movimentacaoEstoque;

	}

	
	@Override
	public void setDomain(Estoque domain) {
		this.domain = domain;

	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	protected void limparCampos() {
		super.limparCampos();
	}

	
	public SearchFieldController<Produto> getSearchProduto() {
		if (searchProduto == null) {
			searchProduto = new SearchFieldController<Produto>(getPersistenceService()) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void setObject(Produto produto) {
					getDomain().setProdutoNaoNulo(produto);				}

				@Override
				public Produto getObject() {
					return getDomain().getProdutoNaoNulo();
				}
				
				@Override
				public void buscar() throws Exception {
					setResultList((List<Produto>)persistenceService.findByExample(((Produto)getSearchObject())));
				}
				
				@Override
				public void limpar() {
					super.limpar();
				}

			};
		}
		return this.searchProduto;
	}
	
	
	@Override
	protected void buscar() throws TransactionException {
		setListagem(estoqueBO.buscarPorExemplo(getDomain()));
	}
}
