package com.pxt.loja.business.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
		
		estoque = estoqueBO.buscarProdutoCodigo(movimentacaoEstoque.getProduto().getCodigo());

		estoque = criaEstoqueNulo(movimentacaoEstoque, estoque);
		
		if(movimentacaoEstoque.getTipoOperacao().equals(TipoOperacao.RECEBER)){
			realizaRecebimento(movimentacaoEstoque, estoque);
		}
		
		if(movimentacaoEstoque.getTipoOperacao().equals(TipoOperacao.ENTRADA)){
			realizaEntrada(movimentacaoEstoque, estoque);
		}
		
		if(movimentacaoEstoque.getTipoOperacao().equals(TipoOperacao.VENDIDO)){
			realizaVenda(movimentacaoEstoque, estoque);
		}
		if(movimentacaoEstoque.getTipoOperacao().equals(TipoOperacao.DESFAZER_RECEBIMENTO)){
			realizaCorrecaoRecebimento(movimentacaoEstoque, estoque);
		}
		if(movimentacaoEstoque.getTipoOperacao().equals(TipoOperacao.DESFAZER_ENTRADA)){
			realizaCorrecaoEntrada(movimentacaoEstoque, estoque);
		}
		
		movimentacaoEstoque.setData(dataMovimentacao);
		
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
			if(movimentacaoEstoque.getQuantidade() == 0){
				throw new ValidationException("Não é possível inserir valor zerado para Recebimento");
			}
			estoque.setQuantidadeRecebimento(estoque.getQuantidadeRecebimento()	+ movimentacaoEstoque.getQuantidade());
	}
	
	private void realizaCorrecaoRecebimento(MovimentacaoEstoque movimentacaoEstoque, Estoque estoque) throws ValidationException {
		if (movimentacaoEstoque.getQuantidade() == 0) {
			throw new ValidationException("Não é possível inserir valor zerado para Correção de Recebimento");
		}
		if(estoque.getQuantidadeRecebimento() < movimentacaoEstoque.getQuantidade()){
			throw new ValidationException("Quantidade para correção informada maior que quantidade em Recebimento: " + estoque.getQuantidadeRecebimento());
		}
		estoque.setQuantidadeRecebimento(estoque.getQuantidadeRecebimento()	- movimentacaoEstoque.getQuantidade());
}

	private void realizaEntrada(MovimentacaoEstoque movimentacaoEstoque,Estoque estoque) throws ValidationException {
			if (movimentacaoEstoque.getQuantidade() == 0) {
				throw new ValidationException("Não é possível inserir valor zerado para Entrada");
			}
			if (movimentacaoEstoque.getQuantidade() > estoque.getQuantidadeRecebimento()) {
				throw new ValidationException("Não é possível inserir valor superior a quantidade em Recebimento: "	+ estoque.getQuantidadeRecebimento());
			}
			estoque.setQuantidadeProduto(estoque.getQuantidadeProduto()	+ movimentacaoEstoque.getQuantidade());
			estoque.setQuantidadeRecebimento(estoque.getQuantidadeRecebimento() - movimentacaoEstoque.getQuantidade());
	}
	
	private void realizaCorrecaoEntrada(MovimentacaoEstoque movimentacaoEstoque,Estoque estoque) throws ValidationException {
		if (movimentacaoEstoque.getQuantidade() == 0) {
			throw new ValidationException("Não é possível inserir valor zerado para Correção de Entrada");
		}
		if (movimentacaoEstoque.getQuantidade() > estoque.getQuantidadeRecebimento()) {
			throw new ValidationException("Não é possível inserir valor superior a quantidade em Recebimento: "	+ estoque.getQuantidadeRecebimento());
		}
		if (movimentacaoEstoque.getQuantidade() > estoque.getQuantidadeProduto()) {
			throw new ValidationException("Não é possível inserir valor superior a quantidade em Estoque: "	+ estoque.getQuantidadeProduto());
		}
		estoque.setQuantidadeProduto(estoque.getQuantidadeProduto()	- movimentacaoEstoque.getQuantidade());
		estoque.setQuantidadeRecebimento(estoque.getQuantidadeRecebimento() + movimentacaoEstoque.getQuantidade());
}
	
	private void realizaVenda(MovimentacaoEstoque movimentacaoEstoque,Estoque estoque) throws ValidationException {
			if (movimentacaoEstoque.getQuantidade() == 0) {
				throw new ValidationException("Não é possível inserir valor zerado para Venda");
			}
			if (movimentacaoEstoque.getQuantidade() > estoque.getQuantidadeProduto()) {
				throw new ValidationException("Não é possível inserir valor superior a quantidade em Estoque: "	+ estoque.getQuantidadeProduto());
			}
			estoque.setQuantidadeProduto(estoque.getQuantidadeProduto()	- movimentacaoEstoque.getQuantidade());
	}
	
	
	public ByteArrayOutputStream gerarCsv(List<MovimentacaoEstoque> listagem) throws IOException {
		StringBuilder arquivo = new StringBuilder();
		arquivo.append("Cód. Movimentação;");
		arquivo.append("Data;");
		arquivo.append("Operação;");
		arquivo.append("Qde Movimentada;");
		arquivo.append("Produto;");
		// arquivo.append("Des. Produto;");
		arquivo.append("\n");
		for (MovimentacaoEstoque lista : listagem) {
			arquivo.append(lista.getCodigo() == null ? "" : lista.getCodigo()).append(";");
			arquivo.append(lista.getData() == null ? "" : lista.getData()).append(";");
			arquivo.append(lista.getTipoOperacao() == null ? "" : lista.getTipoOperacao()).append(";");
			arquivo.append(lista.getQuantidade() == null ? "" : lista.getQuantidade()).append(";");
			// MÉTODO ABAIXO RETORNA O CÓDIGO E A DESCRIÇÃO CONCATENADOS:
			String codigoDescricao = "";
			if (lista.getProduto() != null) {
				String descricao = lista.getProduto().getDescricao() == null ? "" : lista.getProduto().getDescricao();
				String codigo = lista.getProduto().getCodigo() == null ? "" : String.valueOf(lista.getProduto().getCodigo());
				codigoDescricao = codigo + " - " + descricao;
			}
			arquivo.append(codigoDescricao).append(";");
			// GERA A DESCRICAO EM OUTRA COLUNA -
			// arquivo.append(lista.getCodigoProduto().getDescricaoProduto() ==
			// null ? "" :
			// lista.getCodigoProduto().getDescricaoProduto()).append(";");
			arquivo.append("\n");
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(arquivo.toString().getBytes());
		outputStream.write('\n');
		return outputStream;
	}
}
