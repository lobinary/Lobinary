package com.lobinary.android.platform.db;

public class DBInfo {

	/**
	 * 数据库配置
	 * @author Lobinary
	 *
	 */
	public static class DB {

		/**
		 * 数据库名称
		 */
		public static final String DATEBASE_NAME = "LOBINARY_PLATFORM";
		
		/**
		 * 数据库版本
		 */
		public static final int DATEBASE_VERSION = 1;

	}

	/**
	 * 表信息
	 * @author Lobinary
	 *
	 */
	public static class TABLE {
		
		/**
		 * 交互信息类
		 * @author Lobinary
		 *
		 */
		public static class INTERACTION_MESSAGE{
		
			/**
			 * 交互信息表-表名称
			 */
			public static final String TABLE_INTERACTION_MESSAGE = "INTERACTION_MESSAGE";
			/**
			 * 交互信息表-ID
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_ID = "ID";
			/**
			 * 交互信息表-发送方ID
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_SENDER_ID = "SENDER_ID";
			/**
			 * 交互信息表-接收方ID
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_RECEIVER_ID = "RECEIVER_ID";
			/**
			 * 交互信息表-信息类型
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_MESSAGE_TYPE = "MESSAGE_TYPE";
			/**
			 * 交互信息表-信息内容
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_MESSAGE_INFO = "MESSAGE_INFO";
			/**
			 * 交互信息表-发送日期
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_SEND_DATE = "SEND_DATE";
			/**
			 * 交互信息表-接收日期
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_RECEIVER_DATE = "RECEIVER_DATE";
			/**
			 * 交互信息表-发送状态
			 */
			public static final String COLUMN_INTERACIONT_MESSAGE_SEND_STATUS = "SEND_STATUS";
			
			/**
			 * 创建表-交互信息表// 构建创建表的SQL语句（可以从SQLite Expert工具的DDL粘贴过来加进StringBuffer中）
			 */
			public static final String CREATE_TABLE_INTERACTION_MESSAGE =  new StringBuilder()
						.append("CREATE TABLE [" + TABLE_INTERACTION_MESSAGE + "] (")
						.append("[").append(COLUMN_INTERACIONT_MESSAGE_ID).append("] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_SENDER_ID).append("] INTEGER,")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_RECEIVER_ID).append("] INTEGER,")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_MESSAGE_TYPE).append("] INTEGER,")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_MESSAGE_INFO).append("] VARCHAR(255),")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_SEND_DATE).append("] DATETIME,")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_RECEIVER_DATE).append("] DATETIME,")
				        .append("[").append(COLUMN_INTERACIONT_MESSAGE_SEND_STATUS).append("] INTEGER)").toString();
			/**
			 * 删除表-交互信息表
			 */
			public static final String DROP_TABLE_INTERACION_MESSAGE = new StringBuilder("DROP TABLE IF EXISTS ").append(TABLE_INTERACTION_MESSAGE).toString();
		}
	}
}
