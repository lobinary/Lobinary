package com.lobinary.android.common.util.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <pre>
 * 自定义日志输出器
 * </pre>
 * 
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午2:12:51
 * @version V1.0.0 描述 : 创建文件LogAppender
 * 
 * 
 * 
 */
public class LogAppender extends AppenderSkeleton {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.Appender#requiresLayout()
	 */
	@Override
	public boolean requiresLayout() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent
	 * )
	 */
	@Override
	protected void append(LoggingEvent event) {
//		LogUtil.out(this.layout.format(event));
		LogUtil.out(event.getMessage().toString());
		if (layout.ignoresThrowable()) {
			String[] s = event.getThrowableStrRep();
			if (s != null) {
				int len = s.length;
				for (int i = 0; i < len; i++) {
					LogUtil.out(s[i]);
//					LogUtil.out(Layout.LINE_SEP);
				}
			}
		}
	}

}
