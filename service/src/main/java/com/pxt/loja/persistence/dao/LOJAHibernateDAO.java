package com.pxt.loja.persistence.dao;

import java.io.Serializable;

import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import pxt.framework.persistence.hibernate.HibernateIDAO;

public class LOJAHibernateDAO<T, ID extends Serializable> extends HibernateIDAO<T, T, ID> {

	@PersistenceContext(unitName = "LOJA")
	private Session session;

	public LOJAHibernateDAO() {}

	@Override
	protected Session getSession() {
		return session;
	}

	@Override
	protected void setSession(Session session) {
		this.session = session;
	}
}


