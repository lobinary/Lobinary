/*
 * @(#)WindowsCommunicationSocketService.java     V1.0.0      @上午11:32:04
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月30日    	创建文件
 *
 */
package com.lobinary.apc.service.communication;

import java.util.List;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.util.NetUtil;
import com.lobinary.apc.util.initial.InitialUtil;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月30日 上午11:32:04
 * @version V1.0.0 描述 : 创建文件WindowsCommunicationSocketService
 * 
 *         
 * 
 */
public class WindowsCommunicationSocketService extends CommunicationSocketService {
	ConnectionBean conBean;
	
	public void GetConnectionBean(){
		List<String> localIpList = NetUtil.getLocalIpList();//本地ip列表
		for(String localIp : localIpList){
			List<String> lanIpList = NetUtil.getLANIp(localIp);//局域网内其他设备的ip
			for (final String lanIp : lanIpList) {
				conBean = new ConnectionBean();
				conBean.ip = lanIp;
		}
	}
}
	@Override
	public void addConnection(ConnectionBean conBean) {
		super.addConnection(conBean);
	}

	@Override
	public List<ConnectionBean> getAlreadyConnectionBean() {
		return super.getAlreadyConnectionBean();
	}

	@Override
	public boolean refreshConnectableList() {
		return super.refreshConnectableList();
	}
	
	public static void main(String[] args) {
		InitialUtil.initial();
		WindowsCommunicationSocketService test = new WindowsCommunicationSocketService();
		test.refreshConnectableList();
	}
	
	
}
