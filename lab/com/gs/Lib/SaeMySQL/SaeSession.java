/**
 * GS
 */
package com.gs.Lib.SaeMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author GaoShen
 * @packageName com.gs.Lib.SaeMySQL
 */
public class SaeSession {
	private Logger logger = Logger.getLogger(this.getClass());
	@Test
	public void getConnect(){
		   try {
			String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_qyappgshblog";//使用从库的域名
			   //通过SaeUserInfo提供的静态方法获取应用的access_key和secret_key
			   String Username="kappkx1ywz";
			   String Password="h5lwwxxh4j2xm02yxj0jw1ykl0ikhyh1j321142l";
			   String Driver="com.mysql.jdbc.Driver";
			   //Class.forName(Driver).newInstance();
			   Class.forName(Driver);
			   Connection con=DriverManager.getConnection(URL,Username,Password);
			   Statement st;
				st = (Statement) con.createStatement(); // 创建用于执行静态sql语句的Statement对象
				st.executeUpdate("create table elec");
				st.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
