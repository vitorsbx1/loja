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
import pxt.framework.validation.ValidationException;

import com.pxt.loja.domain.Cliente;
import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;
import com.pxt.loja.domain.Produto;

@SuppressWarnings("all")
@Stateless
public class FornecedorDAO extends LOJAHibernateDAO<Fornecedor, Integer>{

	@EJB
	private PersistenceService persistenceService;

	public void salvarFornecedor(Fornecedor fornecedor) throws TransactionException {
		try{
			persistenceService.saveOrUpdate(fornecedor);
		}catch(Exception e){
			if(e instanceof SQLException){
				throw new TransactionException("Fornecedor já cadastrado.", e);
			}
			throw new TransactionException("Erro ao salvar Fornecedor",e);
		}
	}

	public List<Fornecedor> buscar(Fornecedor fornecedor) throws PersistenceException{
		try{
			Criteria criteria = getSession().createCriteria(Fornecedor.class);
			
			if(fornecedor != null){
				if(fornecedor.getCodigo() != null){
					criteria.add(Restrictions.eq("codigo", fornecedor.getCodigo()));
				}
				if(fornecedor.getNome() != null && !fornecedor.getNome().isEmpty()){
					criteria.add(Restrictions.like("nome", fornecedor.getNome(), MatchMode.ANYWHERE).ignoreCase());
				}
				if(fornecedor.getCnpj() != null && !fornecedor.getCnpj().isEmpty()){
					criteria.add(Restrictions.eq("cnpj", fornecedor.getCnpj()));
				}
				if(fornecedor.getIndicadorAtivo() || !fornecedor.getIndicadorAtivo()){
					criteria.add(Restrictions.eq("indicadorAtivo", fornecedor.getIndicadorAtivo()));
				}
			}			
			return criteria.list();
		}catch(Exception e){
			throw new PersistenceException("Não foi possível buscar Fornecedor", e);
		}
	}
	
}
