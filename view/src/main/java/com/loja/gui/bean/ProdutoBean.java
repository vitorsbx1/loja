package com.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.exception.CrudException;

import com.pxt.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean extends CrudController<Produto>{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private Produto domain;
	@EJB
	private PersistenceService persistenceService;	
	
	@Override
	public Produto getDomain() {
		if(domain == null){
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
		if(getDomain().getDescricaoProduto() == null || getDomain().getDescricaoProduto().isEmpty()){
			throw new CrudException("A descrição é um campo obrigatório");
		}
		super.antesSalvar();
	}
}
