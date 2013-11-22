/**
 * GS
 */
package com.gs.test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
/**
 * @author GaoShen
 * @packageName com.gs.test
 */
public class TestExtractor {
	private Logger logger = Logger.getLogger(this.getClass());

	@Test
	public void test() {
		String s = null;
		try {
			s = FileUtils.readFileToString(new File("D://Test//1.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		//String regex = "<title>(.+?)_新闻_腾讯网</title>";//标题的正则表达式
		String regex = "<div id=\"Cnt-Main-Article-QQ\".*?>(.*?)</P>.?</div>";//正文最后是</p>换行</div>或者</P></div>为结束标志的
		Pattern pt = Pattern.compile(regex,Pattern.DOTALL);
		Matcher mt = pt.matcher(s);//s为网页的html内容
		String re = new String();
		if (mt.find()) {
			re = mt.group(1);
		}
		Pattern pt1 = Pattern.compile("<script>.*?</script>",Pattern.DOTALL);
		Matcher mt1 = pt1.matcher(re);
		re = mt1.replaceAll("");
		Pattern pt2 = Pattern.compile("<style>.*?</style>",Pattern.DOTALL);
		Matcher mt2 = pt2.matcher(re);
		re = mt2.replaceAll("");
/*		Pattern pt3 = Pattern.compile("<!--[if !IE]>.*?<![endif]-->",Pattern.DOTALL);
		Matcher mt3 = pt3.matcher(re);
		re = mt3.replaceAll("");*///FIXME 这个标签不知道为什么抹不掉
		re = re.replaceAll("<.*?>", "");//抹掉所有尖括号的内容
		re = re.replaceAll("\\s", "");//抹掉所有空白
		
		System.out.println(re);
	}
}
