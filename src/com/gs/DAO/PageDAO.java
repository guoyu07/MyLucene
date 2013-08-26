package com.gs.DAO;

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

	public void delete(Page p) {
		hibernateTemplate.delete(p);
	}

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


	@Resource(name="hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public List<Page> search(String url) {
		return (List<Page>) this.hibernateTemplate.find(
				"from Page p where p.url=?", url);
	}


	/*public boolean checkElecWithDate(int date) {
		Session s = hibernateTemplate.getSessionFactory().openSession();
		s.beginTransaction();
		long count = (Long) s
				.createQuery(
						"select count(*) from Elec elec where date = :date")
				.setInteger("date", date).uniqueResult();
		s.getTransaction().commit();
		if (count > 0) {
			return true;
		} else
			return false;
	}*/
	
	/**
	 * <strong>Very Dangerous!<strong>
	 */
	public void clear(){
		hibernateTemplate.getSessionFactory().openSession().createSQLQuery("drop table page").executeUpdate();
	}

}
