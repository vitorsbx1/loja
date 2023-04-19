package com.pxt.loja.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

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
			if(e instanceof SQLException){
				throw new TransactionException("Produto já cadastrado.", e);
			}
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
					criteria.add(Restrictions.like("descricao", produto.getDescricao(), MatchMode.ANYWHERE));
				}
				if(produto.getFornecedor() != null && produto.getFornecedor().getCodigo() != null){
					criteria.add(Restrictions.eq("fornecedor.codigo", produto.getFornecedor().getCodigo()));
				}
				if(produto.getMarca() != null && produto.getMarca().getCodigo() != null){
					criteria.add(Restrictions.eq("marca.codigo", produto.getMarca().getCodigo()));
				}
				if(produto.getCategoria() != null && !produto.getCategoria().isEmpty()){
					criteria.add(Restrictions.like("categoria", produto.getCategoria()));
				}
				if(produto.getModelo() != null && !produto.getModelo().isEmpty()){
					criteria.add(Restrictions.like("modelo", produto.getModelo()));
				}
				if(produto.getCor() != null && !produto.getCor().isEmpty()){
					criteria.add(Restrictions.like("cor", produto.getCor()));
				}
				if(produto.getTamanho()!= null){
					criteria.add(Restrictions.eq("tamanho", produto.getTamanho()));
				}
				if(produto.getValor() != null){
					criteria.add(Restrictions.eq("valor", produto.getValor()));
				}
			}			
			return criteria.list();
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar o Produto", e);
		}
		
	}
	
}
