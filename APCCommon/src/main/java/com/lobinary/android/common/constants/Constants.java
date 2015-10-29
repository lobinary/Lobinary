package com.lobinary.android.common.constants;

/**
 * <pre>
 * 常量类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月21日 下午5:17:59
 * @version V1.0.0 描述 : 创建文件Constants
 * 
 *         
 * 
 */
public class Constants {
	
	public class CONNECTION{
		
		/**
		 * 连接状态 - 未连接
		 */
		public final static int STATUS_UNCONNECTION = 0;
		
		/**
		 * 连接状态 - 已经连接
		 */
		public final static int STATUS_CONNECTION = 1;
		
		/**
		 * 连接状态 - 连接异常
		 */
		public final static int STATUS_EXCEPTION = 4;
		
		public class PARAM{
			
			/**
			 * SOCKET端口
			 */
			public final static int SOCKET_PORT = 6666;
			
			/**
			 * SOCKET编码
			 */
			public static final String SOCKET_ENCODING = "UTF-8";
			
		}
		
	}
	
	/**
	 * 
	 * <pre>
	 * 报文型常量
	 * </pre>
	 * @author 吕斌：lvb3@chinaunicom.cn
	 * @since 2015年10月21日 下午5:26:14
	 * @version V1.0.0 描述 : 创建文件MESSAGE
	 * 
	 *         
	 *
	 */
	public class MESSAGE{
		
		/**
		 * 
		 * <pre>
		 * 命令常量
		 * </pre>
		 * @author 吕斌：lvb3@chinaunicom.cn
		 * @since 2015年10月22日 下午9:36:03
		 * @version V1.0.0 描述 : 创建文件COMMAND
		 * 
		 *         
		 *
		 */
		public class COMMAND{

			public class CODE{

				/**
				 * 远程方法
				 */
				public final static String REMOTE_METHOD = "REMOTE_METHOD";
			} 
			
		}
		
		/**
		 * 
		 * <pre>
		 * 报文类型 常量
		 * </pre>
		 * @author 吕斌：lvb3@chinaunicom.cn
		 * @since 2015年10月21日 下午5:26:26
		 * @version V1.0.0 描述 : 创建文件TYPE
		 * 
		 *         
		 *
		 */
		public class TYPE{
			
			/**
			 * 异常报文
			 */
			public final static String EXCEPTION = "EXCEPTION";
			
			/**
			 * 请求 ping  一般用于查看服务器是否开启 辅助 可连接列表使用
			 */
			public final static String REQUEST_PING = "REQUEST_PING";
			
			/**
			 * 请求ping成功
			 */
			public final static String REQUEST_PING_SUCCESS = "REQUEST_PING_SUCCESS";

			/**
			 * 请求连接
			 */
			public final static String REQUEST_CONNECT = "REQUEST_CONNECT";

			/**
			 * 同意连接
			 */
			public final static String ACCEPT_CONNECT = "ACCEPT_CONNECT";
			/**
			 * 断开连接
			 */
			public final static String DISCONNECT = "DISCONNECT";
			/**
			 * 拒绝连接
			 */
			public final static String REJECT_CONNECT = "REJECT_CONNECT";

			/**
			 * 请求时间报文
			 */
			public final static String REQ_TIME = "REQ_TIME";
			
			/**
			 * 请求命令
			 */
			public final static String COMMAND = "COMMAND";
		}
	}

	/**
	 * 系统编码
	 */
	public static final String SYSTEM_CODE_WINDOWS = "Windows";
	/**
	 * 系统编码
	 */
	public static final String SYSTEM_CODE_ANDROID = "Android";

}
