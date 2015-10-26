package com.lobinary.android.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.exception.APCSysException;

/**
 * <pre>
 * 网络工具类
 * </pre>
 * 
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月26日 上午11:30:36
 * @version V1.0.0 描述 : 创建文件NetUtil
 * 
 * 
 * 
 */
public class NetUtil {

	private static Logger logger = LoggerFactory.getLogger(NetUtil.class);
	
	public static void main(String[] args) {
		for(String s : NetUtil.getLocalIpList()){
			System.out.println(s);
		}
		
		System.out.println("#######################");
		List<String> lanIp = NetUtil.getLANIp("172.16.67.2");
		System.out.println(lanIp.size());
		for(String s : lanIp){
			System.out.println(s);
		}
	}
	
	/**
	 * 
	 * <pre>
	 * Ping 固定ip
	 * </pre>
	 *
	 * @param ip
	 * @param timeOut  单位 毫秒
	 * @return
	 */
	public static boolean ping(String ip,int timeOut){
		try {
			String[] ipArray = ip.split("\\.");
			byte[] bip = new byte[4];
			for (int i = 0; i < bip.length; i++) {
				bip[i] = new Byte(ipArray[i]);
			}
			int retry = 4;
			InetAddress address = InetAddress.getByAddress(bip);
			for (int i = 0; i < retry; i++) {
				if (!address.isReachable(timeOut)) {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("网络工具类:Ping 固定ip异常",e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <pre>
	 * 获取本地Ip列表
	 * 当电脑存在多网卡(有现和无线)时存在ip列表,否则一般情况下 list中只含有一个ipv4性质的IP
	 * </pre>
	 *
	 * @return
	 */
	public static List<String> getLocalIpList() {
		List<String> list = new ArrayList<String>();
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName();// 获得本机名称
			InetAddress[] addrs = InetAddress.getAllByName(hostName);
			if (addrs.length > 0) {
				for (InetAddress addrIndex : addrs) {
					String ip = addrIndex.getHostAddress();
					if (ip.length() <= 15) {// 大于15的大部分为ipv6或者其他隧道参数，非IPV4
						if(!ip.contains("172.16")){
							list.add(ip);
						}
					}
				}
			}
		} catch (UnknownHostException e) {
			logger.error("网络工具类:获取本地ip时发生异常",e);
			throw new APCSysException(CodeDescConstants.NET_GET_LOCAL_IP_EXCEPTION,e);
		}
		return list;
	}
	
	/**
	 * 
	 * <pre>
	 * 获取局域网内其他设备IP
	 * 并排序出最有可能有设备的ip
	 * </pre>
	 *
	 * @param localIp 本地ip
	 * @return
	 */
	public static List<String> getLANIp(String localIp){
		List<String> list = new ArrayList<String>();
		
		try {
			String[] localIpStrArray = localIp.split("\\.");
			String beforeIpStr = localIpStrArray[0] + "." + localIpStrArray[1] + "." + localIpStrArray[2] + ".";
			int lastNum = Integer.parseInt(localIpStrArray[3]);
			int startNum = 0;
			int endNum = 255;
			if(lastNum>99){//按照现在路由器的设计 而 特殊处理的优化方案 不是一个可兼容的优化方案
				startNum = 100;
			}

			for (int i = startNum; i < (startNum + 10); i++) {//默认最多局域网不会大于10个设备
				list.add(beforeIpStr+i);//100~109
			}
			for (int i = 0; i < startNum; i++) {
				list.add(beforeIpStr+i);//0~99
			}
			for (int i = (startNum + 10); i <= endNum; i++) {
				list.add(beforeIpStr+i);//110~255
			}
		} catch (Exception e) {
			logger.error("网络工具类:获取局域网内其他设备IP发生异常",e);
			throw new APCSysException(CodeDescConstants.NET_GET_LOCAL_IP_EXCEPTION,e);
		}
//		list.remove(localIp);
		return list;
	}

}
