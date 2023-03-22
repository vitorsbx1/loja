package com.loja.gui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.faces.exception.CrudException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.CadastroBO;
import com.pxt.loja.domain.Estoque;
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

	private SearchFieldController<Marca> searchMarca;
	private SearchFieldController<Fornecedor> searchFornecedor;

	private Produto domain;

	@EJB
	private PersistenceService persistenceService;

	@EJB
	private CadastroBO cadastroBO;

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

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	protected void antesSalvar() throws CrudException {
		if (getDomain().getDescricaoProduto() == null
				|| getDomain().getDescricaoProduto().isEmpty()) {
			throw new CrudException("A descrição é um campo obrigatório");
		}
		super.antesSalvar();

	}

	@Override
	protected void limparCampos() {
		super.limparCampos();
	}

	public SearchFieldController<Marca> getSearchMarca() {
		if (searchMarca == null) {
			searchMarca = new SearchFieldController<Marca>(
					getPersistenceService()) {

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
					setResultList((List<Marca>) persistenceService
							.findByExample(((Marca) getSearchObject())));
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
					setResultList((List<Fornecedor>) persistenceService
							.findByExample(((Fornecedor) getSearchObject())));
				}

				@Override
				public void limpar() {
					super.limpar();
				}

			};
		}
		return searchFornecedor;
	}

	@Override
	protected void salvar() throws TransactionException {
		Estoque estoque = new Estoque();
		estoque.setProduto(getDomain());
		estoque.setQuantidadeProduto(0);
		estoque.setQuantidadeRecebimento(0);
		estoque.setQuantidadeReserva(0);
		try {
			persistenceService.save(getDomain());
			persistenceService.save(estoque);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
