package com.loja.gui.bean.cadastros;

import java.util.Calendar;
import java.util.Date;

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

import com.pxt.loja.business.impl.ClienteBO;
import com.pxt.loja.domain.Cliente;

@SuppressWarnings("all")
@ManagedBean
@ViewScoped
public class ClienteBean extends CrudController<Cliente> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cliente domain;
	
	@EJB
	private PersistenceService persistenceService;
	
	@EJB
	private ClienteBO clienteBO;

	private Calendar dataAtual = Calendar.getInstance();
	
	private Date dataAtualDate = dataAtual.getTime();
	

	@Override
	public Cliente getDomain() {
		if (domain == null) {
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
	
	protected void valida() throws ValidationException, PersistenceException {
		if (getDomain().getNome() == null || getDomain().getNome().isEmpty()) {
			throw new ValidationException("O Nome � um campo obrigat�rio");
		}
		if (getDomain().getCpfcnpj() == null || getDomain().getCpfcnpj().isEmpty()) {
			throw new ValidationException("O CPF/CNPJ � um campo obrigat�rio");
		}
		if (getDomain().getDataNascimento() == null) {
			throw new ValidationException("A Data de Nascimento � um campo obrigat�rio");
		}
		if (getDomain().getDataNascimento().compareTo(dataAtualDate) > 0) {
			throw new ValidationException("Data selecionada inv�lida, data maior que dia atual");
		}
		if(!getDomain().retornaIdadeEmAno(getDomain().getDataNascimento(), dataAtualDate)){
			throw new ValidationException("Cadastro n�o permitido para menores de 18 anos.");
		}
		if(getDomain().getCpfcnpj().length() == 11){
			if(!getDomain().validaCaracteresCPF(getDomain().getCpfcnpj())){
				throw new ValidationException("CPF inv�lido! Corrigir e cadastrar novamente.");
			}
			if(!getDomain().validarCPF(getDomain().getCpfcnpj())){
				throw new ValidationException("CPF inv�lido! Corrigir e cadastrar novamente.");
			}
		}
		if(getDomain().getCpfcnpj().length() == 14){
			if(!getDomain().validaCaracteresCNPJ(getDomain().getCpfcnpj())){
				throw new ValidationException("CNPJ inv�lido! Corrigir e cadastrar novamente.");
			}
			if(!getDomain().validarCNPJ(getDomain().getCpfcnpj())){
				throw new ValidationException("CNPJ inv�lido! Corrigir e cadastrar novamente.");
			}
		}
		if(getDomain().getCpfcnpj().length() != 11 && getDomain().getCpfcnpj().length() != 14){
			throw new ValidationException("CPF/CNPJ inv�lido! Inserir 11 n�meros para CPF ou 14 n�meros para CNPJ.");
		}

	}
	
	@Override
	protected void buscar() throws TransactionException {
		try{
			setListagem(clienteBO.buscar(getDomain()));
			if(getListagem().isEmpty()){
				msgWarn("Nenhum Cliente encontrado na Pesquisa!");
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
				clienteBO.salvarCliente(getDomain());
				addToList(getDomain());
				msgInfo("Salvo com sucesso!");
			}
			if(getEstadoCrud() == CrudState.ST_EDIT){
				valida();
				clienteBO.salvarCliente(getDomain());
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
		}
	}
}
