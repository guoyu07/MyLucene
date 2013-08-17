package com.gs.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ScanfTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("«Î ‰»ÎA:"); 
		String a;
		try {
			a = br.readLine();
			System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
