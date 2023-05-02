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

import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;

@SuppressWarnings("all")
@Stateless
public class MarcaDAO extends LOJAHibernateDAO<Marca, Integer>{

	@EJB
	private PersistenceService persistenceService;

	public void salvarMarca(Marca marca) throws TransactionException {
		try{
			persistenceService.saveOrUpdate(marca);
		}catch(Exception e){
			if(e instanceof SQLException){
				throw new TransactionException("Marca já cadastrada.", e);
			}
			throw new TransactionException("Erro ao salvar Marca",e);
		}
	}

	public List<Marca> buscar(Marca marca) throws PersistenceException{
		try{
			Criteria criteria = getSession().createCriteria(Produto.class);
			
			if(marca != null){
				if(marca.getCodigo() != null){
					criteria.add(Restrictions.eq("codigo", marca.getCodigo()));
				}
				if(marca.getDescricao() != null && !marca.getDescricao().isEmpty()){
					criteria.add(Restrictions.like("descricao", marca.getDescricao(), MatchMode.ANYWHERE).ignoreCase());
				}
			}			
			return criteria.list();
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar Marca", e);
		}
		
	}
	
}
