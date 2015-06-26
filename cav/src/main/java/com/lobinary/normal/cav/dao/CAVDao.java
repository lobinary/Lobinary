package com.lobinary.normal.cav.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lobinary.normal.cav.dto.CAVDto;

public class CAVDao {
	
	public DateBaseUtil db = new DateBaseUtil();

	// 驱动程序名
	String driver = "com.mysql.jdbc.Driver";

	// URL指向要访问的数据库名scutcs
	String url = "jdbc:mysql://127.0.0.1:3306/lobinary";

	// MySQL配置时的用户名
	String user = "root";

	// MySQL配置时的密码
	String password = "123qwe";

	public Connection getConn() throws ClassNotFoundException, SQLException, UnsupportedEncodingException {
		// 加载驱动程序
		Class.forName(driver);

		// 连续数据库
		Connection conn = DriverManager.getConnection(url, user, password);

		if (!conn.isClosed()) System.out.println("成功连接mysql数据库");

		// statement用来执行SQL语句
		Statement statement = conn.createStatement();

		// 要执行的SQL语句
		String sql = "SELECT * FROM lobinary.cav";

		// 结果集
		ResultSet rs = statement.executeQuery(sql);

		System.out.println("-----------------");
		System.out.println("执行结果如下所示:");
		System.out.println("-----------------");
		System.out.println(" 学号" + "\t" + " 姓名");
		System.out.println("-----------------");
//INSERT INTO `lobinary`.`cav` (`id`, `name`, `number`, `date`, `time`, `director`, `makers`, `publisher`, `series`, `type`, `actor`, `detailurl`, `imageurl`, `imagelocalpath`) VALUES ('1', 'testName', '1235', '日期', '时间', '导演', '制造商', '出版商', '系列号', '类型', '演员', '详细地址', '图片地址', '本地图片位置');

		String name = null;

		while (rs.next()) {

			// 选择sname这列数据
			name = rs.getString("name");

			// 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
			// 然后使用GB2312字符集解码指定的字节数组
			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");

			// 输出结果
			System.out.println(rs.getString("number") + "\t" + name);
		}

		rs.close();
		conn.close();
		return conn;
	}
	
	public boolean insert(CAVDto cav) {
		return db.InsertSql(cav);
//		return true;
	}

}
