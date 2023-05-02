package com.pxt.loja.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;

import com.pxt.loja.domain.Cliente;

@SuppressWarnings("all")
@Stateless
public class ClienteDAO extends LOJAHibernateDAO<Cliente, Integer> {

	@EJB
	private PersistenceService persistenceService;

	public void salvarCliente(Cliente cliente) throws TransactionException {
		try {
			persistenceService.saveOrUpdate(cliente);
		} catch (Exception e) {
			if (e instanceof SQLException) {
				throw new TransactionException("Cliente já cadastrado.", e);
			}
			throw new TransactionException("Erro ao salvar Cliente", e);
		}
	}

	public List<Cliente> buscar(Cliente cliente) throws PersistenceException {
		try {
			Criteria criteria = getSession().createCriteria(Cliente.class);

			if (cliente != null) {
				if (cliente.getCodigo() != null) {
					criteria.add(Restrictions.eq("codigo", cliente.getCodigo()));
				}
				if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
					criteria.add(Restrictions.like("nome", cliente.getNome(),MatchMode.ANYWHERE).ignoreCase());
				}
				if (cliente.getCpfcnpj() != null && !cliente.getCpfcnpj().isEmpty()) {
					criteria.add(Restrictions.eq("cpfcnpj",cliente.getCpfcnpj()));
				}
				if (cliente.getIndicadorAtivo() || !cliente.getIndicadorAtivo()){
					criteria.add(Restrictions.eq("indicadorAtivo", cliente.getIndicadorAtivo()));
				}
			}
			return criteria.list();
		} catch (Exception e) {
			throw new PersistenceException("Não foi possível buscar Cliente", e);
		}
	}


}
