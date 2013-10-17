package com.gs.Lib.NaviveBayesClassify;

import java.io.IOException;  	
import java.io.StringReader;
//import jeasy.analysis.MMAnalyzer;

import org.wltea.analyzer.core.IKSegmenter;

/**
* ���ķִ���
*/
public class ChineseSpliter 
{
	/**
	* �Ը������ı��������ķִ�
	* @param text �������ı�
	* @param splitToken ���ڷָ�ı��,��"|"
	* @return �ִ���ϵ��ı�
	*/
	public static String split(String text,String splitToken)
	{
		String result = "";
		/*MMAnalyzer analyzer = new MMAnalyzer();  	
		try  	
        {
			result = analyzer.segment(text, splitToken);	
		}  	
        catch (IOException e)  	
        { 	
        	e.printStackTrace(); 	
        } 	*/
		
		IKSegmenter ik = new IKSegmenter(new StringReader(text), true);
		while (true) {
			try {
				result += ik.next().getLexemeText() + splitToken;
			} catch (NullPointerException e) {
				break;
			} catch(ArrayIndexOutOfBoundsException e){
				System.out.println("he%%%%%%%%%%%%%%%%%%%%");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return result;
	}
}
