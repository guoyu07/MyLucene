/**
 * GS
 */
package com.gs.test;
import org.apache.log4j.Logger;
/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestJava {
	public static int id = 0;
	public int name;
	/**
	 * @return the id
	 */
	public static int getId() {
		return id++;
	}
	
	static{
		System.out.println("Static block2");
	}

	static{
		System.out.println("Static block1");
	}

	public TestJava() {
		System.out.println("Construct");
	}
	
	/**
	 * @param i
	 */
	public TestJava(int i) {
		this.name = i;
		System.out.println("Construct "+name);
	}

	public void finalize(){
		System.out.println("Finalize"+name);
	}
	
}
