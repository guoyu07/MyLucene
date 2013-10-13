/**
 * GS
 */
package com.gs.Lib.Annotation;
import static org.junit.Assert.*;

import java.lang.reflect.Method;


import org.apache.log4j.Logger;
import org.junit.Test;
/**
 * @author GaoShen
 * @packageName com.gs.Annotation
 */
public class TestAnnotation {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		Model model = new Model();
		try {
			System.out.println(model.getClass().getMethod("getId").isAnnotationPresent(PK.class));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		/*Method[] m = new Model().getClass().getMethods();
		for(int i=0;i<m.length;i++){
			System.out.println(m[i].isAnnotationPresent(PK.class)+"   "+m[i]);
		}*/
	}
}
