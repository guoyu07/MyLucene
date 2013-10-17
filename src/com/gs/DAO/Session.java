/**
 * GS
 */
package com.gs.DAO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The Session Class that made by myself
 * @author GaoShen
 * @param <E>
 *            the model which want to be save or load
 * @packageName com.gs.DAO
 */
public class Session<T extends Object> {
	/**
	 * Commons Util
	 * 
	 * @author GaoShen
	 * @packageName com.gs.DAO
	 * @param <K>
	 *            key
	 * @param <V>
	 *            value
	 */
	class Item<K extends Object, V extends Object> {
		public K k;

		public V v;

		/**
		 * @param k
		 * @param v
		 */
		public Item(K k, V v) {
			this.k = k;
			this.v = v;
		}

		@Override
		public String toString() {
			return "Item [k=" + k + ", v=" + v + "]";
		}
	}

	private List<String> columnsName;
	private Connection conn = null;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param url
	 * @param username
	 * @param password
	 */
	public Session(String url, String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
			conn = DriverManager.getConnection(url, username, password);// 创建数据连接

		} catch (Exception e) {
			logger.fatal("数据库连接失败" + e.getMessage());
		}

	}

	/**
	 * @param clazz
	 *            the class which will be referenced to create the table
	 * @throws IllegalArgumentException if the model have not be annotated by the @com.gs.DAO.Entity
	 */
	public void createTable(Class<T> clazz) throws IllegalArgumentException {
		T model = null;
		String PKName = null;
		String dbname = clazz.getSimpleName().toLowerCase();
		try {
			model = (T) clazz.newInstance();
			if (!model.getClass().isAnnotationPresent(Entity.class)) {
				throw new IllegalArgumentException(
						"the model must be annotated by the @com.gs.DAO.Entity");
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		Method[] m = model.getClass().getDeclaredMethods();
		Map<String, String> methodMap = new HashMap<String, String>();
		String sql = "CREATE  TABLE `" + dbname + "`.`"
				+ model.getClass().getSimpleName().toLowerCase() + "` ( ";
		for (int j = 0; j < m.length; j++) {

			if (m[j].getName().startsWith("get")) {
				String name = m[j].getName().split("get")[1].toLowerCase();
				if (m[j].isAnnotationPresent(PK.class))
					PKName = name;
				Class<?> retype = m[j].getReturnType();
				String sqltype = null;
				if (retype.isInstance(new String())) {
					sqltype = "VARCHAR(100)";
				} else if (retype.getSimpleName().equals("int")) {
					sqltype = "INT";
				} else if (retype.getSimpleName().equals("long")) {
					sqltype = "INT";
				} else if (retype.getSimpleName().equals("double")) {
					sqltype = "INT";
				} else
					throw new IllegalArgumentException("the item" + name
							+ "can't be resolved");
				methodMap.put(name, sqltype);
				sql += " `" + name + "` " + sqltype + " NOT NULL";
				if (j != (m.length - 1)) {
					sql += ",";
				}
			}
		}
		if (PKName != null) {
			sql += ", PRIMARY KEY (`" + PKName + "`)";
		}

		sql += ");";
		logger.info(sql);

		try {
			Statement st;
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			st.executeUpdate(sql);
			st.close();// 执行插入操作的sql语句，并返回插入数据的个数
		} catch (SQLException e) {
			logger.fatal("建表失败" + e.getMessage());
		}
	}

	/**
	 * @param clazz
	 *            to Declare the table name
	 */
	public void deleteTable(Class<T> clazz) {
		String sql = "drop table " + clazz.getSimpleName().toLowerCase() + ";";
		logger.debug(sql);
		try {
			Statement st;
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * @param tableName
	 */
	public void deleteTable(String tableName) {
		String sql = "drop table " + tableName + ";";
		logger.debug(sql);
		try {
			Statement st;
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * @param t
	 *            the class to be referenced
	 * @return the T list
	 * @throws IllegalArgumentException
	 */
	public List<T> list(Class<T> t) throws IllegalArgumentException {
		String PKName = null;
		Method[] m = t.getMethods();
		columnsName = new LinkedList<String>();
		for (int j = 0; j < m.length; j++) {
			if (m[j].getName().startsWith("get")) {
				String name = m[j].getName().split("get")[1];
				columnsName.add(name);
				if (m[j].isAnnotationPresent(PK.class)) {
					PKName = name.toLowerCase();
				}
			}
		}
		if (PKName == null) {
			throw new IllegalArgumentException(
					"there must have a method which annotated by @com.gs.DAO.PK ");
		}
		String sql = "select * from " + t.getSimpleName().toLowerCase();
		logger.info(sql);
		ResultSet rs = null;
		try {
			Statement st = (Statement) conn.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		List<T> res = new LinkedList<T>();
		try {
			while (rs.next()) {
				T re = null;
				try {
					re = (T) t.newInstance();
					for (int i = 0; i < m.length; i++) {
						if (m[i].getName().startsWith("set")) {
							Class<?>[] ts = m[i].getParameterTypes();
							String type = ts[0].getSimpleName();
							String column = m[i].getName().toLowerCase()
									.substring(3);
							if (type.equals("int")) {
								m[i].invoke(re, rs.getInt(column));
							} else if (type.equals("double")) {
								m[i].invoke(re, rs.getDouble(column));
							} else if (type.equals("long")) {
								m[i].invoke(re, rs.getLong(column));
							} else if (type.equalsIgnoreCase("String")) {
								m[i].invoke(re, rs.getString(column));
							} else {
								throw new IllegalArgumentException(
										"the type can't be resolved");
							}
						}
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				res.add(re);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return res;
	}

	/**
	 * @param t
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 *             if there isn't a Primary key or there is a type that can't
	 *             resolved by the method
	 */
	public <E extends Object> T load(Class<T> t, E id)
			throws IllegalArgumentException {
		String PKName = null;
		Method[] m = t.getMethods();
		columnsName = new LinkedList<String>();
		for (int j = 0; j < m.length; j++) {
			if (m[j].getName().startsWith("get")) {
				String name = m[j].getName().split("get")[1];
				columnsName.add(name);
				if (m[j].isAnnotationPresent(PK.class)) {
					PKName = name.toLowerCase();
				}
			}
		}
		if (PKName == null) {
			throw new IllegalArgumentException(
					"there must have a method which annotated by @com.gs.DAO.PK ");
		}
		String sql = "select * from " + t.getSimpleName().toLowerCase()
				+ " where " + PKName + " = " + id;
		logger.info(sql);
		ResultSet rs = null;
		try {
			Statement st = (Statement) conn.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		T re = null;
		try {
			re = (T) t.newInstance();
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().startsWith("set")) {
					Class<?>[] ts = m[i].getParameterTypes();
					String type = ts[0].getSimpleName();
					String column = m[i].getName().toLowerCase().substring(3);
					if (type.equals("int")) {
						m[i].invoke(re, rs.getInt(column));
					} else if (type.equals("double")) {
						m[i].invoke(re, rs.getDouble(column));
					} else if (type.equals("long")) {
						m[i].invoke(re, rs.getLong(column));
					} else if (type.equalsIgnoreCase("String")) {
						m[i].invoke(re, rs.getString(column));
					} else {
						throw new IllegalArgumentException(
								"the type can't be resolved");
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return re;
	}

	/**
	 * @param model
	 * @return
	 */
	public boolean save(T model) {
		Method[] m = model.getClass().getDeclaredMethods();
		List<Item<String, Object>> items = new LinkedList<Item<String, Object>>();
		String dbname = model.getClass().getSimpleName().toLowerCase();
		for (int j = 0; j < m.length; j++) {
			if (m[j].getName().startsWith("get")) {
				try {
					items.add(new Item<String, Object>(m[j].getName().split(
							"get")[1].toLowerCase(), m[j].invoke(model, null)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
		
		String sql = "INSERT INTO " + dbname + " ( ";
		
		for (int i = 0; i < items.size(); i++) {
			sql += items.get(i).k;
			if (i != (items.size() - 1)) {
				sql += " , ";
			}

		}
		
		sql += " ) VALUES ( ";
		
		for (int i = 0; i < items.size(); i++) {
			boolean stringFlag = false;
			if (items.get(i).v instanceof String) {
				stringFlag = true;
				sql += "'";
			}
			sql += items.get(i).v;
			if (stringFlag) {
				sql += "'";
			}
			if (i != (items.size() - 1)) {
				sql += " , ";
			}

		}
		
		sql += " ) ;";
		int count = 0;
		
		try {
			Statement st;
			st = (Statement) conn.createStatement(); // 创建用于执行静态sql语句的Statement对象
			count = st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		logger.debug("向" + dbname + "表中插入 " + count + " 条数据");
		logger.debug(sql);

		return true;
	}

}
