package com.gs.Lib.NaviveBayesClassify;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
* ͣ�ôʴ�����
* @author phinecos 
* 
*/
public class StopWordsHandler 
{
	//private static String stopWordsList[] ={"��", "����","Ҫ","�Լ�","֮","��","��","��","��","��","��","��","Ӧ","��","ĳ","��","��","��","λ","��","һ","��","��","��","��","��","��","��",""};//����ͣ�ô�
	public static boolean IsStopWord(String word)
	{
		/*for(int i=0;i<stopWordsList.length;++i)
		{
			if(word.equalsIgnoreCase(stopWordsList[i]))
				return true;
		}*/
		Set<String> set = new HashSet<String>();
		try {
			for (String e : FileUtils.readLines(new File("D://Test//ChineseStopwords.txt"))) {
				set.add(e);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(set.contains(word)) return true;
		return false;
	}
}
