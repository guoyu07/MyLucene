/**
 * GS
 */
package com.gs.DAO;
import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.gs.crawler.Property;
import com.gs.model.Page;
/**
 * @author GaoShen
 * @packageName com.gs.DAO
 */
public class TestDAO {
	private Logger logger = Logger.getLogger(this.getClass());
	@Test
	public void test() {
		Session<Page> s = new Session<Page>("jdbc:mysql://localhost:3306/page", "root", "940409");
		//s.createTable(Page.class);
		s.save(new Page(100,200,"htpp://ds.com",17,"D://dnja//sa.txt"));
	}
	@Test
	public void test6(){
		DAO d = new DAO("root","940409");
		d.save(new Page(100,200,"htpp://ds.com",16,"D://dnja//sa.txt"));
	}
	
	@Test
	public void test1(){
		try {
			 Class type = Page.class.getMethod("getStartoffset",null).getReturnType();
			 System.out.println(type.getSimpleName());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	@Test
	public void test2(){
		Session<Page> s = new Session<Page>("jdbc:mysql://localhost:3306/page", "root", "940409");
		s.createTable(Page.class);
		//s.deleteTable(Page.class);
		//FileUtils.readFileToString(new File(""));
	}
	
	@Test
	public void test3(){
		Session<Page> s = new Session<Page>("jdbc:mysql://localhost:3306/page", "root", "940409");
		System.out.println(s.list(Page.class));
	}
	
	@Test
	public void test4(){
		Field[] fs = Page.class.getDeclaredFields();
		System.out.println(fs.length);
		for(Field f : fs){
			System.out.println(f.getName()+"   "+f.getType());
		}
	}
	
	@Test
	public void test5(){
		System.out.println(Page.class.getMethods()[3].getName()+"   "+Page.class.getMethods()[3].getGenericParameterTypes()[0].getClass());
	}
	
}
