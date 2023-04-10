package com.pxt.loja.business.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

import com.pxt.loja.domain.Estoque;
import com.pxt.loja.domain.MovimentacaoEstoque;
import com.pxt.loja.domain.Produto;
import com.pxt.loja.domain.TipoOperacao;
import com.pxt.loja.persistence.dao.MovimentacaoDAO;

@SuppressWarnings("all")
@Stateless
public class MovimentacaoBO {

	@EJB
	public MovimentacaoDAO movimentacaoDAO;
	
	@EJB
	public EstoqueBO estoqueBO;
	

	public List<MovimentacaoEstoque> buscarRelatorioMovimentacao(Date dataInicial, Date dataFinal, TipoOperacao tipoOperacao, Produto produto) throws PersistenceException{
		return movimentacaoDAO.buscarRelatorioMovimentacao(dataInicial, dataFinal, tipoOperacao, produto);
	}
	
	public void salvarMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) throws ValidationException, PersistenceException, TransactionException {

		try {
		
		Date dataMovimentacao = new Date();
		Estoque estoque;
		
		estoque = estoqueBO.buscarProdutoCodigo(movimentacaoEstoque.getProduto().getCodigoProduto());

		estoque = criaEstoqueNulo(movimentacaoEstoque, estoque);
				
		realizaRecebimento(movimentacaoEstoque, estoque);

		realizaEntrada(movimentacaoEstoque, estoque);
		
		movimentacaoEstoque.setDataMovimentacao(dataMovimentacao);
		
		estoqueBO.salvarEstoque(estoque);
		movimentacaoDAO.saveOrUpdate(movimentacaoEstoque);

		} catch (Exception e) {
			if (e instanceof ValidationException) {
				throw new ValidationException(e.getMessage());
			}
			throw new TransactionException("Erro ao salvar movimentação", e);
		}

	}
	
	private Estoque criaEstoqueNulo(MovimentacaoEstoque movimentacaoEstoque,
			Estoque estoque) throws ValidationException {
		if (estoque == null	&& movimentacaoEstoque.getTipoOperacao() == movimentacaoEstoque.getTipoOperacao().RECEBER) {
			estoque = new Estoque();
			estoque.setProduto(movimentacaoEstoque.getProduto());
			estoque.setQuantidadeProduto(0);
			estoque.setQuantidadeRecebimento(0);

		} else if (estoque == null && movimentacaoEstoque.getTipoOperacao() == movimentacaoEstoque.getTipoOperacao().ENTRADA) {
			throw new ValidationException("Não é possível realizar entrada de Mercadoria sem valor de Recebimento");
		}
		return estoque;
	}

	private void realizaRecebimento(MovimentacaoEstoque movimentacaoEstoque, Estoque estoque) throws ValidationException {
		if(movimentacaoEstoque.getTipoOperacao() == movimentacaoEstoque.getTipoOperacao().RECEBER){
			if(movimentacaoEstoque.getQuantidadeMovimentacao() == 0){
				throw new ValidationException("Não é possível inserir valor zerado para Recebimento");
			}
			estoque.setQuantidadeRecebimento(estoque.getQuantidadeRecebimento()	+ movimentacaoEstoque.getQuantidadeMovimentacao());
		}
	}

	private void realizaEntrada(MovimentacaoEstoque movimentacaoEstoque,Estoque estoque) throws ValidationException {
		if (movimentacaoEstoque.getTipoOperacao() == movimentacaoEstoque.getTipoOperacao().ENTRADA) {
			if (movimentacaoEstoque.getQuantidadeMovimentacao() == 0) {
				throw new ValidationException("Não é possível inserir valor zerado para Entrada");
			}
			if (movimentacaoEstoque.getQuantidadeMovimentacao() > estoque.getQuantidadeRecebimento()) {
				throw new ValidationException("Não é possível inserir valor superior a quantidade em Recebimento: "	+ estoque.getQuantidadeRecebimento());
			}
			estoque.setQuantidadeProduto(estoque.getQuantidadeProduto()	+ movimentacaoEstoque.getQuantidadeMovimentacao());
			estoque.setQuantidadeRecebimento(estoque.getQuantidadeRecebimento() - movimentacaoEstoque.getQuantidadeMovimentacao());
		}
	}
}
