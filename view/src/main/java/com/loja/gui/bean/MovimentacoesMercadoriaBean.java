package com.loja.gui.bean;

import java.util.Arrays;
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
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.MovimentacaoBO;
import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.domain.TipoOperacao;

@ManagedBean
@ViewScoped
public class MovimentacoesMercadoriaBean extends CrudController<MovimentacaoEstoque> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersistenceService persistenceService;
	
	@EJB
	private MovimentacaoBO movimentacaoBO;
	
	private MovimentacaoEstoque domain;

	private SearchFieldController<Produto> searchProduto;
	
	private List<TipoOperacao> tipoOperacoes;


	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	@Override
	public MovimentacaoEstoque getDomain() {
		if (domain == null) {
			domain = new MovimentacaoEstoque();
		}
		return domain;
	}
	
	@Override
	public void setDomain(MovimentacaoEstoque domain) {
		this.domain = domain;
	}
	
	public SearchFieldController<Produto> getSearchProduto() {
		if (searchProduto == null) {
			searchProduto = new SearchFieldController<Produto>(
					getPersistenceService()) {

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
	
	public List<TipoOperacao> getTipoOperacoes() {
		if (tipoOperacoes == null) {
			tipoOperacoes = Arrays.asList(TipoOperacao.values());
		}
		return tipoOperacoes;
	}

	protected void validar() throws ValidationException, PersistenceException {
		
		if(getDomain().getProduto() == null){
			throw new ValidationException("O Produto é um campo obrigatório");
		}
		if(getDomain().getQuantidadeMovimentacao() == null){
			throw new ValidationException("Quantidade é um campo obrigatório");
		}
		if(getDomain().getTipoOperacao() == null){
			throw new ValidationException("Operações é um campo obrigatório");
		}
	}
	
	@Override
	public void salvar(ActionEvent arg0) {
		try{
			this.validar();
			movimentacaoBO.salvarMovimentacaoEstoque(getDomain());
			this.addToList(getDomain());
			this.configuraEstado(CrudState.ST_DEFAULT);
			msgInfo("Salvo com sucesso!");
		}catch(PersistenceException | TransactionException e){
			msgError(e, e.getMessage());
		}catch(ValidationException e){
			e.printStackTrace();
			msgWarn(e.getMessage());
		}
	}

}
