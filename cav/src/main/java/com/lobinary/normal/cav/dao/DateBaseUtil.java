package com.lobinary.normal.cav.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lobinary.normal.cav.dto.CAVDto;

public class DateBaseUtil {
	String drivename = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://127.0.0.1:3306/lobinary";
	String user = "root";
	String password = "123qwe";
	String insql;
	String upsql;
	String delsql;
	String sql = "select * from cav";
	String name;
	Connection conn = null;
	ResultSet rs = null;
	
	public Connection ConnectMysql() {
		try {
			if(conn==null||conn.isClosed()){
				Class.forName(drivename);
				conn = (Connection) DriverManager.getConnection(url, user, password);
				if (!conn.isClosed()) {
					System.out.println("cav mysql数据库连接成功");
				} else {
					System.out.println("cav mysql数据库连接失败");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}

	public void CutConnection(Connection conn) throws SQLException {
		try {
			if (rs != null)
				;
			if (conn != null)
				;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			conn.close();
		}
	}

	class user {// 内部类，其字段对应用来存放、提取数据库中的数据
		int userid;
		String username = "";
		String password = "";
		String email = "";

		// 通过set方法，往类的实例里“存放”数据
		// 通过get方法，从类的实例里“获得”数据，然后再通过插入数据库
		public void setId(int userid) {
			this.userid = userid;
		}

		public void setName(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Integer getId() {
			return userid;
		}

		public String getName() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getEmail() {
			return email;
		}

	}
	
	public static void main(String[] args) {
		DateBaseUtil db = new DateBaseUtil();
		CAVDto cav = new CAVDto();
		cav.setName("测试名称");
		cav.setImagelocalpath("暂无");
		System.out.println(db.InsertSql(cav));
	}

	// 插入、删除、更新的方法是一样的，不一样的是数据库参数
	public boolean InsertSql(CAVDto cav) {
		ConnectMysql();
		try {
			//INSERT INTO `lobinary`.`cav` (`id`, `name`, `number`, `date`, `time`, `director`, `makers`, `publisher`, `series`, `type`, `actor`, `detailurl`, `imageurl`, `imagelocalpath`) VALUES ('1', 'testName', '1235', '日期', '时间', '导演', '制造商', '出版商', '系列号', '类型', '演员', '详细地址', '图片地址', '本地图片位置');
			insql = "insert into cav(name, number, date, time, director, makers, publisher, series, type, actor, detailurl, imageurl, imagelocalpath)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// 上面的方法比下面的方法有优势，一方面是安全性，另一方面我忘记了……
			// insql="insert into user(userid,username,password,email) values(user.getId,user.getName,user.getPassword,user.getEmail)";
			PreparedStatement ps = conn.prepareStatement(insql);
			// .preparedStatement(insql);
			// PreparedStatement ps=(PreparedStatement)
			// conn.prepareStatement(insql);
			ps.setString(1, cav.getName());
			ps.setString(2, cav.getNumber());
			ps.setString(3, cav.getDate());
			ps.setString(4, cav.getTime());
			ps.setString(5, cav.getDirector());
			ps.setString(6, cav.getMakers());
			ps.setString(7, cav.getPublisher());
			ps.setString(8, cav.getSeries());
			ps.setString(9, cav.getType());
			ps.setString(10, cav.getActor());
			ps.setString(11, cav.getDetailurl());
			ps.setString(12, cav.getImageurl());
			ps.setString(13, cav.getImagelocalpath());
			int result = ps.executeUpdate();
			// ps.executeUpdate();无法判断是否已经插入
			if (result > 0){
//				System.out.println(cav.getName() + "插入结果为：" + result);
				return true;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 与其他操作相比较，查询语句在查询后需要一个查询结果集（ResultSet）来保存查询结果
	public void SelectSql(String sql) {
		try {
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				name = rs.getString("username");
				System.out.println(rs.getString("userid") + name + rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean UpdateSql(String upsql) {
		try {
			PreparedStatement ps = conn.prepareStatement(upsql);
			int result = ps.executeUpdate();// 返回行数或者0
			if (result > 0)
				return true;
		} catch (SQLException ex) {
//			Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	public boolean DeletSql(String delsql) {

		try {
			PreparedStatement ps = conn.prepareStatement(upsql);
			int result = ps.executeUpdate(delsql);
			if (result > 0)
				return true;
		} catch (SQLException ex) {
//			Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

}