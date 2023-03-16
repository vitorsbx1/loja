package com.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.exception.CrudException;

import com.pxt.loja.domain.Marca;


@ManagedBean
@ViewScoped
public class MarcaBean extends CrudController<Marca>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Marca domain;
	@EJB
	private PersistenceService persistenceService;

	@Override
	public Marca getDomain() {
		if(domain == null){
			domain = new Marca();
		}
		return domain;
	}

	@Override
	public void setDomain(Marca domain) {
		this.domain = domain;
		
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	@Override
	protected void antesSalvar() throws CrudException {
		if(getDomain().getDescricao() == null || getDomain().getDescricao().isEmpty()){
			throw new CrudException("A descrição é um campo obrigatório");
		}
		super.antesSalvar();
	}

}
