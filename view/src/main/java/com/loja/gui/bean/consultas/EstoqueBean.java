package com.loja.gui.bean.consultas;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.SearchController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.EstoqueBO;
import com.pxt.loja.business.impl.MovimentacaoBO;
import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
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
	
	private Marca marca;
	
	private Fornecedor fornecedor;

	private SearchFieldController<Produto> searchProduto;

	private SearchFieldController<Marca> searchMarca;
	
	private SearchFieldController<Fornecedor> searchFornecedor;
	
	
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
	
	public SearchFieldController<Marca> getSearchMarca() {
		if (searchMarca == null) {
			searchMarca = new SearchFieldController<Marca>(getPersistenceService()) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				public void setObject(Marca marca) {
					setMarcaNaoNulo(marca);;
				}
				
				@Override
				public Marca getObject() {
					return getMarcaNaoNulo();
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
					setFornecedorNaoNulo(fornecedor);
				}
				
				@Override
				public Fornecedor getObject() {
					return getFornecedorNaoNulo();
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
	
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
	    this.marca = marca;
	}

	public Marca getMarcaNaoNulo() {
		if (this.marca == null) {
			return new Marca();
		}
		return getMarca();
	}

	public void setMarcaNaoNulo(Marca marca) {
		this.marca = marca;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Fornecedor getFornecedorNaoNulo() {
		if (this.fornecedor == null) {
			return new Fornecedor();
		}
		return getFornecedor();
	}

	public void setFornecedorNaoNulo(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	protected void limpar() {
		searchProduto.limpar();
		searchMarca.limpar();
		searchFornecedor.limpar();
		getListagem().clear();
	}
	
	protected void validarCampos() throws ValidationException{
		if(getDomain().getProduto() == null && getMarca() == null && getFornecedor() == null){
			throw new ValidationException("Informe ao menos um campo para consulta");
		}
	}
	

	
	@Override
	protected void busca(){	
		try{
			validarCampos();
			List<Estoque> listaEstoque = estoqueBO.buscar(getDomain(),getMarca(),getFornecedor());
			setListagem(listaEstoque);
			if(getListagem().isEmpty()){
				msgInfo("Nenhuma informação de estoque encontrado.");;
			}
		}catch(ValidationException e){
			e.printStackTrace();
			msgWarn(e.getMessage());
		}
		catch(PersistenceException e){
			msgError(e, e.getMessage());
			e.printStackTrace();
			getListagem().clear();
		}
	}

}
