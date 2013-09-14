package com.gs.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.gs.model.Page;

public class DAO {
	private Logger logger = Logger.getLogger(this.getClass());
	// ������̬ȫ�ֱ���
	static Connection conn;//TODO ����commect

	static Statement st;

	/* �������ݼ�¼���������������ݼ�¼�� */
	public void save(Page p) {

		conn = getConnection(); // ����Ҫ��ȡ���ӣ������ӵ����ݿ�
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
			System.out.println(sql);
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����

			int count = st.executeUpdate(sql); // ִ�в��������sql��䣬�����ز������ݵĸ���

			logger.info("��page���в��� " + count + " ������"); // �����������Ĵ�����

			conn.close(); // �ر����ݿ�����

		} catch (SQLException e) {
			logger.error("��������ʧ��" + e.getMessage());
			logger.error(e.getMessage());
		}
	}

	/* ��ѯ���ݿ⣬�������Ҫ��ļ�¼����� */
	public  void query() {

		conn = getConnection(); // ͬ����Ҫ��ȡ���ӣ������ӵ����ݿ�
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
			conn.close(); // �ر����ݿ�����

		} catch (SQLException e) {
			logger.error("��ѯ����ʧ��");
			logger.error(e.getMessage());
		}
	}

	/* ��ȡ���ݿ����ӵĺ��� */
	public  Connection getConnection() {
		Connection con = null; // ���������������ݿ��Connection����
		try {
			Class.forName("com.mysql.jdbc.Driver");// ����Mysql��������

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/page", "root", "940409");// ������������

		} catch (Exception e) {
			logger.fatal("���ݿ�����ʧ��" + e.getMessage());
		}
		return con; // ���������������ݿ�����
	}

	public void create() {
		conn = getConnection(); // ����Ҫ��ȡ���ӣ������ӵ����ݿ�
		try {
			String create = "CREATE  TABLE `page`.`page` ( `id` INT NOT NULL ,  `endoffset` INT NULL ,  `startoffset` INT NULL ,  `path` VARCHAR(50) NULL ,  `url` VARCHAR(200) NULL ,  PRIMARY KEY (`id`) );";
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����

			int count = st.executeUpdate(create); // ִ�в��������sql��䣬�����ز������ݵĸ���

			conn.close(); // �ر����ݿ�����

		} catch (SQLException e) {
			logger.fatal("����ʧ��" + e.getMessage());
		}
	}
}
