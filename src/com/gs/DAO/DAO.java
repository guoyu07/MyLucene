package com.gs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gs.model.Page;

public class DAO {
	// 创建静态全局变量
	static Connection conn;//TODO 复用commect

	static Statement st;

	/* 插入数据记录，并输出插入的数据记录数 */
	public void save(Page p) {

		conn = getConnection(); // 首先要获取连接，即连接到数据库
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
			System.out.println(sql);
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象

			int count = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数

			System.out.println("向staff表中插入 " + count + " 条数据"); // 输出插入操作的处理结果

			conn.close(); // 关闭数据库连接

		} catch (SQLException e) {
			System.out.println("插入数据失败" + e.getMessage());
		}
	}

	/* 查询数据库，输出符合要求的记录的情况 */
	public static void query() {

		conn = getConnection(); // 同样先要获取连接，即连接到数据库
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
			conn.close(); // 关闭数据库连接

		} catch (SQLException e) {
			System.out.println("查询数据失败");
		}
	}

	/* 获取数据库连接的函数 */
	public static Connection getConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/page", "root", "940409");// 创建数据连接

		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

	public void create() {
		conn = getConnection(); // 首先要获取连接，即连接到数据库
		try {
			String create = "CREATE  TABLE `page`.`page` ( `id` INT NOT NULL ,  `endoffset` INT NULL ,  `startoffset` INT NULL ,  `path` VARCHAR(50) NULL ,  `url` VARCHAR(200) NULL ,  PRIMARY KEY (`id`) );";
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象

			int count = st.executeUpdate(create); // 执行插入操作的sql语句，并返回插入数据的个数

			conn.close(); // 关闭数据库连接

		} catch (SQLException e) {
			System.out.println("建表失败" + e.getMessage());
		}
	}
}
