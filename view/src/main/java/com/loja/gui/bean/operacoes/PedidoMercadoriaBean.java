package com.loja.gui.bean.operacoes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.Min;

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
	private BigDecimal totalPedido = BigDecimal.ZERO;
	private Boolean editarCampoQuantidade = false;
	private Boolean carrinhoVazio = true;
	private Integer quantidadeItensCarrinho = 0;
	
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
	
	@Min(value = 0, message = "A quantidade deve ser positiva!")
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

	public BigDecimal getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(BigDecimal totalPedido) {
		this.totalPedido = totalPedido;
	}
	
	public String getTotalPedidoFormatado(){
		DecimalFormat formatador = new DecimalFormat("#,##0.00");
		return formatador.format(getTotalPedido());
	}
	
	public Boolean getEditarCampoQuantidade() {
		return editarCampoQuantidade;
	}

	public void setEditarCampoQuantidade(Boolean editarCampoQuantidade) {
		this.editarCampoQuantidade = editarCampoQuantidade;
	}
	
	
	public Boolean getCarrinhoVazio() {
		return carrinhoVazio;
	}

	public void setCarrinhoVazio(Boolean carrinhoVazio) {
		this.carrinhoVazio = carrinhoVazio;
	}
	
	public void atualizaCarrinhoVazio(){
		if(domain.getListaItens().isEmpty()){
			carrinhoVazio = true;
		}else{
			carrinhoVazio = false;
		}
	}
	
	public String getCarrinhoImagem(){
		return carrinhoVazio ? "carrinho-vazio.png" : "carrinho-cheio.png";
	}

	public Integer getQuantidadeItensCarrinho() {
		return quantidadeItensCarrinho;
	}

	public void setQuantidadeItensCarrinho(Integer quantidadeItensCarrinho) {
		this.quantidadeItensCarrinho = quantidadeItensCarrinho;
	}

	public SearchFieldController<Cliente> getSearchCliente() {
		if (searchCliente == null) {
			searchCliente = new SearchFieldController<Cliente>(getPersistenceService()) {

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

				@Override
				public void limpar() {
					super.limpar();
				}
			};
		}
		return this.searchCliente;
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
					setResultList((List<Produto>) pedidoBO.buscarProdutoEstoqueDiponivelExists((Produto) getSearchObject()));
				}

				@Override
				public void limpar() {
					super.limpar();
				}
			};
		}
		return this.searchProduto;
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
				throw new ValidationException("Quantidade informada maior que quantidade disponível em estoque: " + estoque.getQuantidadeProduto());
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
			item.setSubTotalItem(item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade())));
			BigDecimal subTotalAtualizar = item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
			
			domain.getListaItens().add(item);
			
			totalPedido = totalPedido.add(subTotalAtualizar);
			
			if(domain.getListaItens().isEmpty()){
				setCarrinhoVazio(true);
			}else{
				setCarrinhoVazio(false);
			}
			
			setQuantidadeItensCarrinho(domain.getListaItens().size());
			
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
	
	public void adicionarQuantidade(ItemPedido item) {
		try {
			Estoque estoque;
			estoque = estoqueBO.buscarProdutoCodigo(item.getProdutoNaoNulo().getCodigo());
			Integer novaQuantidade = item.getQuantidade() + 1;
			if (estoque.getQuantidadeProduto() < novaQuantidade) {
				throw new ValidationException("Quantidade não pode ser superior à quantidade disponível: " + estoque.getQuantidadeProduto());
			} else {
				item.setQuantidade(novaQuantidade);
			}
			BigDecimal valorAlterar = item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
			item.setSubTotalItem(valorAlterar);
			totalPedido = getTotalPedido().add(item.getValor());
		} catch (ValidationException e) {
			e.printStackTrace();
			msgWarn(e.getMessage());
		} catch (Exception e) {
			msgError(e, e.getMessage());
		}
	}
	
	
	public void removerQuantidade(ItemPedido item) {
		try {
			Integer novaQuantidade = item.getQuantidade() - 1;
			if (novaQuantidade <= 0) {
				throw new ValidationException("Produto não pode ficar com quantidade igual a 0!");
			} else {
				item.setQuantidade(item.getQuantidade() - 1);
			}
			BigDecimal valorAlterar = item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade()));
			item.setSubTotalItem(valorAlterar);
			totalPedido = getTotalPedido().subtract(item.getValor());
		} catch (ValidationException e) {
			e.printStackTrace();
			msgWarn(e.getMessage());
		} catch (Exception e) {
			msgError(e, e.getMessage());
		}
	}
	
	public void removerItemPedidoBean(ItemPedido item){
		try{
			if(getEditarCampoQuantidade() == true){
				throw new ValidationException("Para remover o item do Pedido, cancelar operação de edição.");
			}
			domain.getListaItens().remove(item);
			
			if(domain.getListaItens().isEmpty()){
				setCarrinhoVazio(true);
			}else{
				setCarrinhoVazio(false);
			}
			
			setQuantidadeItensCarrinho(domain.getListaItens().size());
			
			BigDecimal valorRemover = item.getSubTotalItem();
			totalPedido = getTotalPedido().subtract(valorRemover);
			msgInfo("Item removido com sucesso!");
		}catch(ValidationException e){
			msgWarn(e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			msgError(e, e.getMessage());
		}
		
	}
		
	public void editarItem(ItemPedido item) {
		setEditarCampoQuantidade(true);
	}
	
	
	
	public void editarItemQuantidade(ItemPedido item) throws ValidationException {
		
		if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
			throw new ValidationException("Quantidade não pode ser vazia, e menor ou igual a 0!");
		}
		for(ItemPedido itemPedido : domain.getListaItens()) {
			if(itemPedido.getProdutoNaoNulo().getCodigo() == item.getProdutoNaoNulo().getCodigo()) {
				totalPedido = getTotalPedido().subtract(itemPedido.getSubTotalItem());
				itemPedido.setQuantidade(item.getQuantidade());
				BigDecimal novoValor = itemPedido.getValor().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));
				itemPedido.setSubTotalItem(novoValor);
				totalPedido = getTotalPedido().add(item.getSubTotalItem());
				break;
			}
		}
			
	}
	
	public void salvarEdicaoItem(ItemPedido item) {
		try {
			editarItemQuantidade(item);
			setEditarCampoQuantidade(false);
		} catch (ValidationException e) {
			e.printStackTrace();
			msgWarn(e.getMessage());
		} catch (Exception e) {
			msgError(e, e.getMessage());
		}
	}
	
	@Override
	protected void novo() {
		searchCliente.limpar();
		searchProduto.limpar();
		getDomain().getListaItens().clear();
		totalPedido = BigDecimal.ZERO;
		setClienteEnabled(true);
		setQuantidade(null);
		setCarrinhoVazio(true);
		setQuantidadeItensCarrinho(0);
		super.novo();
	}
	
	@Override
	public void salvar(ActionEvent arg0) {
		try{
			if(getDomain().getListaItens().isEmpty()){
				msgWarn("Não é possível salvar pedido sem itens!");
			}else{
				pedidoBO.salvarPedido(getDomain());
				msgInfo("Pedido efetuado com sucesso");
			}
			this.configuraEstado(CrudState.ST_DEFAULT);
		}catch(PersistenceException e){
			e.printStackTrace();
			msgError(e, e.getMessage());
		}
	}
	
}
