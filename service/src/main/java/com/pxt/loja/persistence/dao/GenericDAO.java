package com.pxt.loja.persistence.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Clustered;

import pxt.framework.persistence.DataAccessObject;

/**
 * Classe de acesso a dados generica utilizada para consultar, excluir, inserir,
 * atualizar dados de qualquer entidade persistente.
 * 
 * @author Anderson Paiva Costa <br>
 *         anderson@newsolutions.inf.br <br>
 *         New Solutions Tecnologia em Inform�tica Ltda <br>
 *         www.newsolutions.inf.br<br>
 *         10/08/2011 14:46:36<br>
 * 
 */
@SuppressWarnings("rawtypes")
@Stateless(name = "loja." + DataAccessObject.SERVICE_NAME)
@Clustered
@Local(DataAccessObject.class)
public class GenericDAO extends LOJAHibernateDAO {}
