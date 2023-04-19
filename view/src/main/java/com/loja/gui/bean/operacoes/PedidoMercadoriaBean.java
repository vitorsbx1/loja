package com.loja.gui.bean.operacoes;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.business.impl.EstoqueBO;
import com.pxt.loja.business.impl.ItemPedidoBO;
import com.pxt.loja.business.impl.PedidoBO;
import com.pxt.loja.domain.Cliente;
import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.ItemPedido;
import com.pxt.loja.domain.Pedido;
import com.pxt.loja.domain.Produto;

@ManagedBean
@ViewScoped
public class PedidoMercadoriaBean extends CrudController<Pedido> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PersistenceService persistenceService;
	
	private Pedido domain;
	
	@EJB
	private PedidoBO pedidoBO;
	
	@EJB
	private ItemPedidoBO itemPedidoBO;
	
	@EJB
	private EstoqueBO estoqueBO;
	
	private SearchFieldController<Cliente> searchCliente;
	private SearchFieldController<Produto> searchProduto;
	private Produto produto;
	private Integer quantidade;
	private Boolean isClienteEnabled = false;
	
	@Override
	public Pedido getDomain() {
		if (domain == null) {
			domain = new Pedido();
		}
		return domain;
	}

	@Override
	public void setDomain(Pedido domain) {
		this.domain = domain;
	}
	
	public Produto getProduto() {
		if(produto == null){
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}
	
	public boolean isClienteEnabled(){
		return isClienteEnabled();
	}
	
	public void setClienteEnabled(boolean isClienteEnabled){
		this.isClienteEnabled = isClienteEnabled;
	}
	
	public boolean isClienteDisabled(){
		return !domain.getListaItens().isEmpty() || !isClienteEnabled;
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
	
	public SearchFieldController<Produto> getSearchProduto() {
		if (searchProduto == null) {
			searchProduto = new SearchFieldController<Produto>(getPersistenceService()) {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				
				@Override
				public void setObject(Produto produto) {
					setProduto(produto);
				}
				
				@Override
				public Produto getObject() {
					return getProduto();
				}
				
				@Override
				public void buscar() throws Exception {
					setResultList((List<Produto>) pedidoBO.buscarProdutoEstoqueDiponivelExists());
				}
				
				
			};
		}
		return searchProduto;
	}
	
	public void validarCampos() throws ValidationException {
		
		if(getDomain().getCliente() == null){
			throw new ValidationException("O Cliente é um campo obrigatório");
		}
		if(getProduto().getCodigo() == null){
			throw new ValidationException("Produto é um campo obrigatório");
		}
		if(getQuantidade() == null){
			throw new ValidationException("Quantidade é um campo obrigatório");
		}
		if(getQuantidade() <= 0){
			throw new ValidationException("Quantidade deve ser maior que 0!");
		}
	}
	
	public void adicionarItemPedido(){
		try{
			this.validarCampos();
			
			Estoque estoque = estoqueBO.buscarProdutoCodigo(getProduto().getCodigo());
			if(estoque == null){
				throw new ValidationException("Produto não encontrado no estoque!");
			}
			if(estoque.getQuantidadeProduto() < quantidade){
				throw new ValidationException("Quantidade informada maior que quantidade disponível em estoque: " + quantidade);
			}
			
			for(ItemPedido itemPedido : domain.getListaItens()){
				if (itemPedido.getProdutoNaoNulo().getCodigo().equals(getProduto().getCodigo())){
					throw new ValidationException("Produto já existe na lista de itens do pedido!");
				}
				break;
			}
			
			ItemPedido item = new ItemPedido();
			item.setProduto(getProduto());
			item.setQuantidade(getQuantidade());
			item.setValor(getProduto().getValor());
			
			domain.getListaItens().add(item);
			
			BigDecimal subTotalPedido = produto.getValor().multiply(BigDecimal.valueOf(quantidade));
			
			msgInfo("Item inserido com sucesso!");
			searchProduto.limpar();
			setQuantidade(null);
		}catch(PersistenceException e){
			msgError(e, e.getMessage());
		}catch(ValidationException e){
			e.printStackTrace();
			msgWarn(e.getMessage());
		}
	}
		
	@Override
	public void salvar(ActionEvent arg0) {
		try{
			pedidoBO.salvarPedido(getDomain());
			msgInfo("Pedido efetuado com sucesso");
			this.configuraEstado(CrudState.ST_DEFAULT);
		}catch(PersistenceException e){
			e.printStackTrace();
			msgError(e, e.getMessage());
		}
	}
}
