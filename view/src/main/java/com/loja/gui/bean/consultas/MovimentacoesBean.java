package com.loja.gui.bean.consultas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.SearchController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.MovimentacaoBO;
import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.domain.TipoOperacao;

@ManagedBean
@ViewScoped
public class MovimentacoesBean extends SearchController <MovimentacaoEstoque> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersistenceService persistenceService;
	
	@EJB
	private MovimentacaoBO movimentacaoBO;

	private MovimentacaoEstoque domain;
	
	private List<TipoOperacao> tipoOperacoes;
	
	private SearchFieldController<Produto> searchProduto;
	
	Date dataInicial;
	
	Date dataFinal;
	
	
	public MovimentacaoEstoque getDomain() {
		if (domain == null) {
			domain = new MovimentacaoEstoque();
		}
		return domain;
	}
	
	public void setDomain(MovimentacaoEstoque domain) {
		this.domain = domain;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	/**
	 * Obt�m lista de MOVIMENTACOES de estoque
	 * @return
	 */
	public List<TipoOperacao> getTipoOperacoes() {
		if (tipoOperacoes == null) {
			tipoOperacoes = Arrays.asList(TipoOperacao.values());
		}
		return tipoOperacoes;
	}

	public SearchFieldController<Produto> getSearchProduto() {
		if (searchProduto == null) {
			searchProduto = new SearchFieldController<Produto>(
					getPersistenceService()) {

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
	
	protected void validarIntervaloDatas() throws ValidationException{
		Long diferencaDatas = (getDataInicial().getTime() - getDataFinal().getTime()) / 86400000;
		
		if (diferencaDatas > 30){
			throw new ValidationException("Inserir um intervalo de datas menor a 30 dias.");
		}
	}
	
	protected void validarCampos() throws ValidationException{
		if(getDataInicial() == null || getDataFinal() == null){
			throw new ValidationException("Data Inicial e Data Final s�o obrigat�rios.");
		}
		if(getDataInicial().after(getDataFinal())){
			throw new ValidationException("Data Inicial maior que Data Final");
		}
		validarIntervaloDatas();
	}
	
	@Override
	protected void limpar() {
		setDataInicial(null);
		setDataFinal(null);
		getDomain().setTipoOperacao(null);
		searchProduto.limpar();
		super.limpar();
	}
	
	@Override
	protected void busca() {
		try{
			validarCampos();
			List<MovimentacaoEstoque> listaMovimentacao = movimentacaoBO.buscarRelatorioMovimentacao(getDataInicial(), getDataFinal(), getDomain().getTipoOperacao(), getDomain().getProduto());
			this.setListagem(listaMovimentacao);
			if(getListagem().isEmpty()){
				msgInfo("Nenhum registro encontrado");
			}
		}catch(PersistenceException e){
			msgError(e, e.getMessage());
		}catch(ValidationException e){
			e.printStackTrace();
			msgWarn(e.getMessage());
		}
	}

	@Override
	public void exportar() {
		System.out.println("Exportar");
		if (getListagem() == null || getListagem().isEmpty()) {
			msgInfo("N�o existe nenhum registro � ser exportado");
		} else {
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/txt");
			response.setHeader("Content-Disposition", "inline;filename=\"movimentacao.csv\"");
			try {
				ByteArrayOutputStream outputStream = movimentacaoBO.gerarCsv(getListagem());
				response.getOutputStream().write(outputStream.toByteArray());
				response.getOutputStream().flush();
				response.getOutputStream().close();
				response.flushBuffer();
				FacesContext.getCurrentInstance().responseComplete();
			} catch (IOException e) {
				e.printStackTrace();
				msgError(e, "N�o foi poss�vel realizar a exporta��o");
			}
		}
	}

}
