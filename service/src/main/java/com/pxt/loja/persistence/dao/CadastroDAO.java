package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.pxt.loja.domain.Fornecedor;
import com.pxt.loja.domain.Marca;

@SuppressWarnings("all")
@Stateless
public class CadastroDAO extends LOJAHibernateDAO {

	public CadastroDAO(){}
	
	@SuppressWarnings("unchecked")
	public List<Marca> buscarMarca(Marca marca) throws PersistenceException{
		Criteria cri = getSession().createCriteria(Marca.class, "mrc");
		if(marca != null && marca.getCodigo() != null){
			cri.add(Restrictions.eq("mrc.codigo", marca.getCodigo()));
		}
		if(marca != null && marca.getDescricao() != null){
			cri.add(Restrictions.like("mrc.descricao", marca.getDescricao(), MatchMode.START));
		}
		
		return cri.list();
	}
	
	
	public List<Fornecedor> buscarFornecedor(Fornecedor fornecedor) throws PersistenceException{
		Criteria cri = getSession().createCriteria(Fornecedor.class, "frn");
		if(fornecedor != null && fornecedor.getCodigo() != null){
			cri.add(Restrictions.eq("frn.codigo", fornecedor.getCodigo()));
		}
		if(fornecedor != null && fornecedor.getNome() != null){
			cri.add(Restrictions.like("frn.nome", fornecedor.getNome(), MatchMode.START));
		}
		if(fornecedor != null && fornecedor.getCnpj() != null){
			cri.add(Restrictions.eq("frn.cnpj", fornecedor.getCnpj()));
		}
		
		return cri.list();
	}
}
