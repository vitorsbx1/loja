package com.loja.gui.bean.cadastros;

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

import com.pxt.loja.business.impl.ProdutoBO;
import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean extends CrudController<Produto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Produto domain;

	private SearchFieldController<Marca> searchMarca;
	
	private SearchFieldController<Fornecedor> searchFornecedor;

	@EJB
	private PersistenceService persistenceService;

	@EJB
	private ProdutoBO produtoBO;
	
	
	@Override
	public Produto getDomain() {
		if (domain == null) {
			domain = new Produto();
		}
		return domain;
	}

	@Override
	public void setDomain(Produto domain) {
		this.domain = domain;
	}
	
	public List<Float> getNumeracoes() {
		List<Float> numeracoesTenis = Arrays.asList(34f, 35f, 36f, 37f, 38f,
				39f, 40f, 41.5f, 42f, 43f, 44f);
		return numeracoesTenis;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	public SearchFieldController<Marca> getSearchMarca() {
		if (searchMarca == null) {
			searchMarca = new SearchFieldController<Marca>(getPersistenceService()) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				public void setObject(Marca marca) {
					getDomain().setMarcaNaoNulo(marca);
				}
				
				@Override
				public Marca getObject() {
					return getDomain().getMarcaNaoNulo();
				}
				
				@Override
				public void buscar() throws Exception {
					setResultList((List<Marca>) persistenceService.findByExample(((Marca) getSearchObject())));
				}
			};
		}
		return searchMarca;
	}
	
	public SearchFieldController<Fornecedor> getSearchFornecedor() {
		if (searchFornecedor == null) {
			searchFornecedor = new SearchFieldController<Fornecedor>(
					getPersistenceService()) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				public void setObject(Fornecedor fornecedor) {
					getDomain().setFornecedorNaoNulo(fornecedor);
				}
				
				@Override
				public Fornecedor getObject() {
					return getDomain().getFornecedorNaoNulo();
				}
				
				@Override
				public void buscar() throws Exception {
					setResultList((List<Fornecedor>) persistenceService.findByExample(((Fornecedor) getSearchObject())));
				}
				
				@Override
				public void limpar() {
					super.limpar();
				}
			};
		}
		return searchFornecedor;
	}

	protected void valida() throws ValidationException, PersistenceException{
		if (getDomain().getDescricao() == null || getDomain().getDescricao().isEmpty()) {
			throw new ValidationException("A Descrição é um campo obrigatório");
		}
		if (getDomain().getTamanho() == null) {
			throw new ValidationException("O tamanho é um campo obrigatório");
		}
		if (getDomain().getMarca() == null) {
			throw new ValidationException("A Marca é um campo obrigatório");
		}
		if (getDomain().getFornecedor() == null) {
			throw new ValidationException("O Fornecedor é um campo obrigatório");
		}
		if (getDomain().getModelo() == null || getDomain().getModelo().isEmpty()) {
			throw new ValidationException("O modelo é um campo obrigatório");
		}
		if (getDomain().getCategoria() == null || getDomain().getCategoria().isEmpty()) {
			throw new ValidationException("A categoria é um campo obrigatório");
		}
		if (getDomain().getCor() == null
				|| getDomain().getCor().isEmpty()) {
			throw new ValidationException("A cor é um campo obrigatório");
		}
		if (getDomain().getValor() == null) {
			throw new ValidationException("O valor é um campo obrigatório");
		}
		if (produtoBO.produtoDuplicadoExists(getDomain().getDescricao(), getDomain().getMarca(), getDomain().getFornecedor(), getDomain().getTamanho())){
			throw new ValidationException("O produto inserido já encontra-se cadastrado.");
		}
	}
	
	@Override
	protected void buscar() throws TransactionException {
		try{
			setListagem(produtoBO.buscar(getDomain()));
			if(getListagem().isEmpty()){
				msgWarn("Nenhum produto encontrado");
			}
		}catch(PersistenceException e){
			e.printStackTrace();
			msgError(e, e.getMessage());
		}
	}

	@Override
	public void salvar(ActionEvent arg0) {
		try{
			if(getEstadoCrud() == CrudState.ST_INSERT){
				valida();
				produtoBO.salvarProduto(getDomain());
				addToList(getDomain());
				msgInfo("Salvo com sucesso!");
			}
			if(getEstadoCrud() == CrudState.ST_EDIT){
				valida();
				produtoBO.salvarProduto(getDomain());
				getListagem().clear();
				addToList(getDomain());
				msgInfo("Alterado com sucesso!");
			}			
			this.configuraEstado(CrudState.ST_DEFAULT);
		}
		catch(TransactionException e){
			msgWarn(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			msgWarn(e.getMessage());
		} catch (PersistenceException e) {
			e.printStackTrace();
			 msgError(e, e.getMessage());
		}
	}
}
