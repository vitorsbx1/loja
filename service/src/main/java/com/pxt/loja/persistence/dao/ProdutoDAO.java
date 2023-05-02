package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;

@SuppressWarnings("all")
@Stateless
public class ProdutoDAO extends LOJAHibernateDAO<Produto, Integer>{

	@EJB
	private PersistenceService persistenceService;

	public void salvarProduto(Produto produto) throws TransactionException {
		try{
			persistenceService.saveOrUpdate(produto);
		}catch(Exception e){
			throw new TransactionException("Erro ao salvar Produto",e);
		}
	}

	public List<Produto> buscar(Produto produto) throws PersistenceException{
		try{
			Criteria criteria = getSession().createCriteria(Produto.class);
			
			if(produto != null){
				if(produto.getCodigo() != null){
					criteria.add(Restrictions.eq("codigo", produto.getCodigo()));
				}
				if(produto.getDescricao() != null && !produto.getDescricao().isEmpty()){
					criteria.add(Restrictions.like("descricao", produto.getDescricao(), MatchMode.ANYWHERE).ignoreCase());
				}
				if(produto.getFornecedor() != null && produto.getFornecedor().getCodigo() != null){
					criteria.add(Restrictions.eq("fornecedor.codigo", produto.getFornecedor().getCodigo()));
				}
				if(produto.getMarca() != null && produto.getMarca().getCodigo() != null){
					criteria.add(Restrictions.eq("marca.codigo", produto.getMarca().getCodigo()));
				}
				if(produto.getCategoria() != null && !produto.getCategoria().isEmpty()){
					criteria.add(Restrictions.like("categoria", produto.getCategoria()).ignoreCase());
				}
				if(produto.getModelo() != null && !produto.getModelo().isEmpty()){
					criteria.add(Restrictions.like("modelo", produto.getModelo()).ignoreCase());
				}
				if(produto.getCor() != null && !produto.getCor().isEmpty()){
					criteria.add(Restrictions.like("cor", produto.getCor()).ignoreCase());
				}
				if(produto.getTamanho()!= null){
					criteria.add(Restrictions.eq("tamanho", produto.getTamanho()));
				}
				if(produto.getValor() != null){
					criteria.add(Restrictions.eq("valor", produto.getValor()));
				}
				if(produto.getIndicadorAtivo() || !produto.getIndicadorAtivo()){
					criteria.add(Restrictions.eq("indicadorAtivo", produto.getIndicadorAtivo()));
				}
			}			
			return criteria.list();
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar o Produto", e);
		}
	}
	
	public Boolean produtoDuplicadoExists(String descricao, Marca marca, Fornecedor fornecedor, Float tamanho) throws PersistenceException{
		try{
			Criteria criteria = getSession().createCriteria(Produto.class);
			if(descricao != null){
				criteria.add(Restrictions.like("descricao", descricao));
			}
			if(marca != null && marca.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", marca.getCodigo()));
			}
			if(fornecedor != null && fornecedor.getCodigo() != null){
				criteria.add(Restrictions.eq("codigo", fornecedor.getCodigo()));
			}
			return !criteria.list().isEmpty();
			
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar o Produto" , e);
		}
	}
}
