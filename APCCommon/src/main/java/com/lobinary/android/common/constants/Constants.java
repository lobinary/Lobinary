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

}
