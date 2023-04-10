package com.loja.gui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.SearchController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.business.impl.EstoqueBO;
import com.pxt.loja.business.impl.MovimentacaoBO;
import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class EstoqueBean extends SearchController<Estoque> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersistenceService persistenceService;

	@EJB
	private EstoqueBO estoqueBO;

	@EJB
	private MovimentacaoBO movimentacaoBO;
	
	private Estoque domain;

	private SearchFieldController<Produto> searchProduto;

	
	
	public Estoque getDomain() {
		if (domain == null) {
			domain = new Estoque();
		}
		return domain;
	}

	
	public void setDomain(Estoque domain) {
		this.domain = domain;
	}

	
	public PersistenceService getPersistenceService() {
		return persistenceService;
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
					getDomain().setProdutoNaoNulo(produto);
				}

				@Override
				public Produto getObject() {
					return getDomain().getProdutoNaoNulo();
				}

				@Override
				public void buscar() throws Exception {
					setResultList((List<Produto>) persistenceService.findByExample(((Produto) getSearchObject())));
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
	protected void limpar() {
		searchProduto.limpar();
		super.limpar();
	}
	
	@Override
	protected void busca(){	
		try{
			List<Estoque> listaEstoque = estoqueBO.buscarPorExemplo(getDomain());
			setListagem(listaEstoque);
			if(getListagem().isEmpty()){
				msgInfo("Nenhum registro encontrado.");;
			}
		}catch(PersistenceException e){
			msgError(e, e.getMessage());
			e.printStackTrace();
		}
	}


}
