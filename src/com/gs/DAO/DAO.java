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
	// ������̬ȫ�ֱ���
	static Connection conn;

	static Statement st;

	/* �������ݼ�¼���������������ݼ�¼�� */
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
			// �������ݵ�sql���
			logger.debug(sql);
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����

			int count = st.executeUpdate(sql); // ִ�в��������sql��䣬�����ز������ݵĸ���

			logger.debug("��page���в��� " + count + " ������"); // �����������Ĵ�����

		} catch (SQLException e) {
			logger.error("��������ʧ��" + e.getMessage());
			logger.error(e.getMessage());
		}
	}

	/* ��ѯ���ݿ⣬�������Ҫ��ļ�¼����� */
	public void query() {

		try {
			String sql = "select * from staff"; // ��ѯ���ݵ�sql���
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����st���ֲ�����

			ResultSet rs = st.executeQuery(sql); // ִ��sql��ѯ��䣬���ز�ѯ���ݵĽ����
			System.out.println("���Ĳ�ѯ���Ϊ��");
			while (rs.next()) { // �ж��Ƿ�����һ������

				// �����ֶ�����ȡ��Ӧ��ֵ
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String sex = rs.getString("sex");
				String address = rs.getString("address");
				String depart = rs.getString("depart");
				String worklen = rs.getString("worklen");
				String wage = rs.getString("wage");

				// ����鵽�ļ�¼�ĸ����ֶε�ֵ
				System.out.println(name + " " + age + " " + sex + " " + address
						+ " " + depart + " " + worklen + " " + wage);

			}

		} catch (SQLException e) {
			logger.error("��ѯ����ʧ��");
			logger.error(e.getMessage());
		}
	}

	/* ��ȡ���ݿ����ӵĺ��� */
	public Connection getConnection() {
		Connection con = null; // ���������������ݿ��Connection����
		try {
			Class.forName("com.mysql.jdbc.Driver");// ����Mysql��������

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/page", dbname, dbpass);// ������������

		} catch (Exception e) {
			logger.fatal("���ݿ�����ʧ��" + e.getMessage());
		}
		return con; // ���������������ݿ�����
	}

	public void create() {
		deleteTable();
		try {
			String create = "CREATE  TABLE `page`.`page` ( `id` INT NOT NULL ,  `endoffset` INT NULL ,  `startoffset` INT NULL ,  `path` VARCHAR(50) NULL ,  `url` VARCHAR(200) NULL ,  PRIMARY KEY (`id`) );";
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����

			int count = st.executeUpdate(create); // ִ�в��������sql��䣬�����ز������ݵĸ���

		} catch (SQLException e) {
			logger.fatal("����ʧ��" + e.getMessage());
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
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����

			int count = st.executeUpdate(create); // ִ�в��������sql��䣬�����ز������ݵĸ���

		} catch (SQLException e) {
			logger.fatal("ɾ����ʧ��" + e.getMessage());
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
