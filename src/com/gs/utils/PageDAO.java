package com.gs.utils;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.DAO
 */
@Component("pageDAO")
public class PageDAO {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public Page loadPage(int id) {
		Page p = (Page) this.hibernateTemplate.load(Page.class, id);
		return p;
	}

	public void save(Page p) {
		hibernateTemplate.save(p);
	}

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * <strong>Very Dangerous!<strong>
	 */
	public void clear() {
		hibernateTemplate.getSessionFactory().openSession()
				.createSQLQuery("drop table page").executeUpdate();
	}

}
