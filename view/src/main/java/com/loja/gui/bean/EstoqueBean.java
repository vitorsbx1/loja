package com.loja.gui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.business.impl.EstoqueBO;
import com.pxt.loja.business.impl.MovimentacaoBO;
import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class EstoqueBean extends CrudController<Estoque> {

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

	
	@Override
	public Estoque getDomain() {
		if (domain == null) {
			domain = new Estoque();
		}
		return domain;
	}

	@Override
	public void setDomain(Estoque domain) {
		this.domain = domain;
	}

	@Override
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
	protected void buscar(){
		
		try{
			List<Estoque> listaEstoque = estoqueBO.buscarPorExemplo(getDomain());
			setListagem(listaEstoque);
		
		}catch(PersistenceException e){
			msgError(e, e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void salvar(ActionEvent event) {
		try {
			estoqueBO.salvarEstoque(getDomain());
			this.addToList(getDomain());
			this.configuraEstado(CrudState.ST_DEFAULT);
			msgInfo("Registro salvo com sucesso!");
		} catch (TransactionException e) {
			msgError(e, e.getMessage());
		}
	}
}
