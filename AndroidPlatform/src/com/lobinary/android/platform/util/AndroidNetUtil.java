package com.lobinary.android.platform.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;

import android.util.Log;

public class AndroidNetUtil {

	private static Logger logger = LoggerFactory.getLogger(AndroidNetUtil.class);
	
	public static List<String> getLocalIpAddress() {
		List<String> ipList = new ArrayList<String>();
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						String ip = inetAddress.getHostAddress().toString();
						if (ip.length() <= 15) {// 大于15的大部分为ipv6或者其他隧道参数，非IPV4
							if(!ip.contains("172.16")){
								logger.debug("添加ip到本地ip列表："+ip);
								ipList.add(ip);
							}
						}
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipList;
	}

}
