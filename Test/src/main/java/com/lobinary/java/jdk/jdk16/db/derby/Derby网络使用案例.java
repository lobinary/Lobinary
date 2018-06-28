package com.lobinary.java.jdk.jdk16.db.derby;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

public class Derby网络使用案例 {
	public static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public static String protocol = "jdbc:derby:";


	public static void main(String[] args) {
		try {
			// 之所以把这个注释掉，是因为1.6版本中DriverManager增加了自动装配驱动的功能，让我们可以直接使用，不需要再加上这段丑陋的代码
			 Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();//网络使用org.apache.derby.jdbc.ClientDriver   本地版本使用org.apache.derby.jdbc.EmbeddedDriver
//			String framework = "derbyclient";
			
			Enumeration<Driver> drivers = DriverManager.getDrivers();

			while(drivers.hasMoreElements()) {
			    System.out.println(drivers.nextElement());
			}
			driver = "org.apache.derby.jdbc.ClientDriver";
			System.out.println("Load the embedded driver");
			Connection conn = null;
			Properties props = new Properties();
			props.put("user", "user1");
			props.put("password", "user1");
			// create and connect the database named helloDB
//			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/derbyTest;create=true", props);
			conn = DriverManager.getConnection("jdbc:derby://localhost:1527/derbyTest", props);
			System.out.println("create and connect to derbyTest");
			conn.setAutoCommit(false);

			// create a table and insert two records
			Statement s = conn.createStatement();
			s.execute("create table hellotable(name varchar(40), score int)");
			System.out.println("Created table hellotable");
			s.execute("insert into hellotable values('Ruth Cao', 86)");
			s.execute("insert into hellotable values ('Flora Shi', 92)");
			// list the two records
			ResultSet rs = s.executeQuery("SELECT name, score FROM hellotable ORDER BY score");
			System.out.println("name\t\tscore");
			while (rs.next()) {
				StringBuilder builder = new StringBuilder(rs.getString(1));
				builder.append("\t");
				builder.append(rs.getInt(2));
				System.out.println(builder.toString());
			}
			// delete the table
//			s.execute("drop table hellotable");
//			System.out.println("Dropped table hellotable");

			rs.close();
			s.close();
			System.out.println("Closed result set and statement");
			conn.commit();
			conn.close();
			System.out.println("Committed transaction and closed connection");

			try { // perform a clean shutdown
				DriverManager.getConnection("jdbc:derby:;shutdown=true");
			} catch (SQLException se) {
				System.out.println("Database shut down normally");
			}
		} catch (Throwable e) {
			// handle the exception
			e.printStackTrace();
		}
		System.out.println("SimpleApp finished");
	}
}
