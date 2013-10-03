package com.gs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.gs.crawler.Property;
import com.gs.model.Page;

public class DAO {
	private Logger logger = Logger.getLogger(this.getClass());
	private String dbname;
	private String dbpass;
	// 创建静态全局变量
	static Connection conn;

	static Statement st;

	/* 插入数据记录，并输出插入的数据记录数 */
	public void save(Page p) {

		try {

			String sql = "INSERT INTO page(id, endoffset, startoffset,path, url)"
					+ " VALUES ("
					+ p.getId()
					+ ", "
					+ p.getEndoffset()
					+ ", "
					+ p.getStartoffset()
					+ ", '"
					+ p.getPath()
					+ "','"
					+ p.getUrl() + "')";
			// 插入数据的sql语句
			logger.debug(sql);
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象

			int count = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数

			logger.debug("向page表中插入 " + count + " 条数据"); // 输出插入操作的处理结果

		} catch (SQLException e) {
			logger.error("插入数据失败" + e.getMessage());
			logger.error(e.getMessage());
		}
	}

	/* 查询数据库，输出符合要求的记录的情况 */
	public void query() {

		try {
			String sql = "select * from staff"; // 查询数据的sql语句
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量

			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			System.out.println("最后的查询结果为：");
			while (rs.next()) { // 判断是否还有下一个数据

				// 根据字段名获取相应的值
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String sex = rs.getString("sex");
				String address = rs.getString("address");
				String depart = rs.getString("depart");
				String worklen = rs.getString("worklen");
				String wage = rs.getString("wage");

				// 输出查到的记录的各个字段的值
				System.out.println(name + " " + age + " " + sex + " " + address
						+ " " + depart + " " + worklen + " " + wage);

			}

		} catch (SQLException e) {
			logger.error("查询数据失败");
			logger.error(e.getMessage());
		}
	}

	/* 获取数据库连接的函数 */
	public Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/page", dbname, dbpass);// 创建数据连接

		} catch (Exception e) {
			logger.fatal("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

	public void create() {
		deleteTable();
		try {
			String create = "CREATE  TABLE `page`.`page` ( `id` INT NOT NULL ,  `endoffset` INT NULL ,  `startoffset` INT NULL ,  `path` VARCHAR(50) NULL ,  `url` VARCHAR(200) NULL ,  PRIMARY KEY (`id`) );";
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象

			int count = st.executeUpdate(create); // 执行插入操作的sql语句，并返回插入数据的个数

		} catch (SQLException e) {
			logger.fatal("建表失败" + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public DAO(Property p) {
		this.dbname = p.dbname;
		this.dbpass = p.dbpass;
		conn = getConnection();
	}
	
	public DAO(String dbname,String dbpass){
		this.dbname = dbname;
		this.dbpass = dbpass;
		conn = getConnection();
	}

	public void deleteTable() {
		try {
			String create = "drop table page.page;";
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象

			int count = st.executeUpdate(create); // 执行插入操作的sql语句，并返回插入数据的个数

		} catch (SQLException e) {
			logger.fatal("删除表失败" + e.getMessage());
		}
	}

	public Page loadPage(int id) {
		String sql = "select * from page where id = " + id;
		Page p = null;
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				logger.debug(rs.getString("path"));
			}
			p = new Page();
			p.setPath(rs.getString("path"));
			p.setId(id);
			p.setEndoffset(rs.getLong("endoffset"));
			p.setStartoffset(rs.getLong("startoffset"));
			p.setUrl(rs.getString("url"));
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return p;
	}
	
	public void destory(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
