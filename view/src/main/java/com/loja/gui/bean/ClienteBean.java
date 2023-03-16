package com.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.exception.CrudException;

import com.pxt.loja.domain.Cliente;

@ManagedBean
@ViewScoped
public class ClienteBean extends CrudController<Cliente>{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private Cliente domain;
	@EJB
	private PersistenceService persistenceService;	
	
	@Override
	public Cliente getDomain() {
		if(domain == null){
			domain = new Cliente();
		}
		return domain;
	}

	@Override
	public void setDomain(Cliente domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	
	@Override
	protected void antesSalvar() throws CrudException {
		if(getDomain().getNome() == null || getDomain().getNome().isEmpty()){
			throw new CrudException("O nome é um campo obrigatório");
		}if(getDomain().getCpfcnpj() == null || getDomain().getCpfcnpj().isEmpty()){
			throw new CrudException("O CPF/CNPJ é um campo obrigatório");
		}
		super.antesSalvar();
	}
}
