package com.loja.gui.bean.operacoes;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;

import com.pxt.loja.domain.Cliente;
import com.pxt.loja.domain.Pedido;



@ManagedBean
@ViewScoped
public class PedidoBean  extends CrudController<Pedido>{

	private static final long serialVersionUID = -5819059111110666637L;

	@EJB
	private PersistenceService persistenceService;
	
	private Pedido domain;
	
	private SearchFieldController<Cliente> searchCliente;
	
	@Override
	public Pedido getDomain() {
		if(domain == null) {
			domain = new Pedido();
		}
		return domain;
	}

	@Override
	public void setDomain(Pedido domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	
	public SearchFieldController<Cliente> getSearchCliente() {
		if (searchCliente == null) {
			searchCliente = new SearchFieldController<Cliente>(this.persistenceService, Cliente.class) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				public void setObject(Cliente cliente) {
					getDomain().setCliente(cliente);
				}
				
				@Override
				public Cliente getObject() {
					return getDomain().getClienteNaoNulo();
				}
				
				@Override
				public void buscar() throws Exception {
					setResultList((List<Cliente>) persistenceService.findByExample(((Cliente) getSearchObject())));
				}
			};
		}
		return searchCliente;
	}
	
	

}
