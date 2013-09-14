/**
 * GS
 */
package com.gs.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.io.ContentReader;
import com.gs.io.ContentWriter;
import com.gs.model.Page;

/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestIO {

	@Test
	public void test() {
		try {
			File file = new File("D://test.txt");
			
			String s1 = new String("你好");
			byte[] b1 = s1.getBytes();
			FileOutputStream fos1 = new FileOutputStream(file,true);
			fos1.write(b1);
			fos1.close();
			
			String s2 = new String("栗子鸡");
			byte[] b2 = s2.getBytes();
			FileOutputStream fos2 = new FileOutputStream(file,true);
			fos2.write(b2);
			fos2.close();
			
			FileInputStream fis = new FileInputStream(file);
			fis.skip(b1.length);
			byte[] b3 = new byte[b2.length];
			for(int i = 0;i<b2.length;i++){
				b3[i] = (byte) fis.read();
			}
			fis.close();
			String re = new String(b3,"UTF-8");
			System.out.println(re);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test2(){
		File file = new File("D://test.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			byte b;
			byte[] b1 = new byte[99];
			for(int i=0;(b=(byte) fis.read())!=-1;i++){
				b1[i] = b;
			}
			String s = new String(b1,"UTF-8");
			System.out.println(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3(){
		File file = new File("D://test.txt");
		long a = file.length();
		System.out.println(a);
	}
	
	/*@Test
	public void test4(){
		ContentWriter cw = new ContentWriter();
		cw.write("D://test.txt", "了解你呢");
		long st = cw.startoffset;
		long en = cw.endoffset;
		System.out.println(st+"   "+en);
		ContentReader cr = new ContentReader();
		cr.read("D://test.txt", st, en);
	}*/
	
	@Test
	public void test5(){
		String s = "World";
		System.out.println(s.hashCode());
		s="sdfsf";
		System.out.println(s.hashCode());
	}
	
	
	@Test
	public void test7(){
		ContentReader cr = new ContentReader();
		System.out.println(cr.read(0, "D://Test//merge//merge.txt"));
	}
	
}


