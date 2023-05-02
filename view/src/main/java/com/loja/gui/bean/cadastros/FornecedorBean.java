package com.loja.gui.bean.cadastros;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.FornecedorBO;
import com.pxt.loja.domain.Fornecedor;

@SuppressWarnings("all")
@ManagedBean
@ViewScoped
public class FornecedorBean extends CrudController<Fornecedor>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Fornecedor domain;
	
	@EJB
	private PersistenceService persistenceService;
	
	@EJB
	private FornecedorBO fornecedorBO;
	
	
	@Override
	public Fornecedor getDomain() {
		if(domain == null){
			domain = new Fornecedor();
		}
		return domain;
	}

	@Override
	public void setDomain(Fornecedor domain) {
		this.domain = domain;
		
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	protected void valida() throws ValidationException, PersistenceException {
		
		if(getDomain().getNome() == null || getDomain().getNome().isEmpty()){
			throw new ValidationException("O Nome é um campo obrigatório");
		}
		if(getDomain().getCnpj().length() != 14){
			throw new ValidationException("CNPJ inválido! Necessário inserir 14 números para CNPJ");
		}
		if(!getDomain().validaCaracteresCNPJ(getDomain().getCnpj())){
			throw new ValidationException("CNPJ inválido! Corrigir e cadastrar novamente.");
		}
		if(!getDomain().validarCNPJ(getDomain().getCnpj())){
			throw new ValidationException("CNPJ inválido! Corrigir e cadastrar novamente.");
		}
	}

	@Override
	protected void buscar() throws TransactionException {
		try{
		
			setListagem(fornecedorBO.buscar(getDomain()));
			if(getListagem().isEmpty()){
				msgWarn("Nenhum Fornecedor encontrado na pesquisa!");
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
				fornecedorBO.salvarObjeto(getDomain());
				addToList(getDomain());
				msgInfo("Salvo com sucesso!");
			}
			if(getEstadoCrud() == CrudState.ST_EDIT){
				valida();
				fornecedorBO.salvarObjeto(getDomain());
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
		catch(PersistenceException e){
			e.printStackTrace();
		}
	}

}
