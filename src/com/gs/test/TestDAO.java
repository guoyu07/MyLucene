/**
 * GS
 */
package com.gs.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gs.DAO.DAO;
import com.gs.model.Page;


/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestDAO {

	@Test
	public void test() {
		DAO dao = new DAO();
		Page p = new Page();
		p.setId(2);
		p.setEndoffset(20);
		p.setStartoffset(10);
		p.setPath("D://Test");
		p.setUrl("http://www.com");
		dao.save(p);
	}

}
