package com.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.MarcaBO;
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
	
	@EJB
	private MarcaBO marcaBO;

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
	
	
	protected void valida() throws ValidationException {
		if(getDomain().getDescricao() == null || getDomain().getDescricao().isEmpty()){
			throw new ValidationException("A Descrição é um campo obrigatório");
		}
	}
	
	
	@Override
	public void salvar(ActionEvent arg0) {
		try{
			if(getEstadoCrud() == CrudState.ST_INSERT){
				valida();
				marcaBO.salvarMarca(getDomain());
				addToList(getDomain());
				msgInfo("Salvo com sucesso!");
			}
			if(getEstadoCrud() == CrudState.ST_EDIT){
				valida();
				marcaBO.salvarMarca(getDomain());
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
		}
	}
}
