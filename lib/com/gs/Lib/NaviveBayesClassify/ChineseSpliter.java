package com.gs.Lib.NaviveBayesClassify;

import java.io.IOException;  	
import java.io.StringReader;
//import jeasy.analysis.MMAnalyzer;

import org.wltea.analyzer.core.IKSegmenter;

/**
* 中文分词器
*/
public class ChineseSpliter 
{
	/**
	* 对给定的文本进行中文分词
	* @param text 给定的文本
	* @param splitToken 用于分割的标记,如"|"
	* @return 分词完毕的文本
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
