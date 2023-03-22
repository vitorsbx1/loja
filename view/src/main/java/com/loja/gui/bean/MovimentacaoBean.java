package com.loja.gui.bean;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;

import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.TipoOperacao;

@ManagedBean
@ViewScoped
public class MovimentacaoBean extends CrudController<MovimentacaoEstoque> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersistenceService persistenceService;

	private MovimentacaoEstoque domain;
	
	private List<TipoOperacao> tipoOperacoes;

	
	@Override
	public MovimentacaoEstoque getDomain() {
		if (domain == null) {
			domain = new MovimentacaoEstoque();
		}
		return domain;
	}
	

	@Override
	public void setDomain(MovimentacaoEstoque domain) {
		this.domain = domain;
	}


	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	protected void limparCampos() {
		super.limparCampos();
	}
	

	/**
	 * Obtém lista de filiais de estoque
	 * @return
	 */
	public List<TipoOperacao> getTipoOperacoes() {
		if (tipoOperacoes == null) {
			tipoOperacoes = Arrays.asList(TipoOperacao.values());
		}
		return tipoOperacoes;
	}

}
