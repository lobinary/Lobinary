package com.lobinary.java.jdk.jdk16.db.jdbc4;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class JDBC4特性 {
	
	public static void main(String[] args) throws SQLException {
		Enumeration<Driver> drivers = DriverManager.getDrivers();

		System.out.println("以下信息为当前加载的所有jar包中，包含的所有jdbc驱动");
		while(drivers.hasMoreElements()) {
		    System.out.println(drivers.nextElement());
		}
		
		//Java SE 6 的 API 规范中，java.sql.RowIdLifetime 规定了 5 种不同的生命周期：ROWID_UNSUPPORTED、ROWID_VALID_FOREVER、ROWID_VALID_OTHER、ROWID_VALID_SESSION 和 ROWID_VALID_TRANSACTION。从字面上不难理解它们表示了不支持 ROWID、ROWID 永远有效等等。具体的信息，还可以参看相关的 JavaDoc。读者可以尝试着连接 Derby 进行试验，会发现运行结果是 ROWID_UNSUPPORTED ，即 Derby 并不支持 ROWID。
		Connection connection = DriverManager.getConnection("jdbc:derby:derbyDBFolder;","user1","password1");
		DatabaseMetaData meta = connection.getMetaData();
		System.out.println("是否支持RowId:"+meta.getRowIdLifetime());
	}

}
