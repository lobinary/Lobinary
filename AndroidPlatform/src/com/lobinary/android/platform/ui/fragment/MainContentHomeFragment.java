package com.lobinary.android.platform.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.util.NetUtil;
import com.lobinary.android.common.util.PropertiesUtil;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.platform.R;
import com.lobinary.android.platform.pojo.bean.ButtonListenerBean;
import com.lobinary.android.platform.util.AndroidNetUtil;

@SuppressLint("UseSparseArrays")
public class MainContentHomeFragment extends Fragment {
	
	Logger logger = LoggerFactory.getLogger(MainContentHomeFragment.class);
	
	View maintContentHomeBtn1;
	View maintContentHomeBtn2;
	View maintContentHomeBtn3;
	View maintContentHomeBtn4;
	
	public static List<View> btnList;
	public static Map<Integer,ButtonListenerBean> mainHomeBtnListenerMap;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_home_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialView();
		addListener();
	}

	@SuppressWarnings("unchecked")
	private void initialView() {
		maintContentHomeBtn1 = getActivity().findViewById(R.id.maintContentHomeBtn1);
		maintContentHomeBtn2 = getActivity().findViewById(R.id.maintContentHomeBtn2);
		maintContentHomeBtn3 = getActivity().findViewById(R.id.maintContentHomeBtn3);
		maintContentHomeBtn4 = getActivity().findViewById(R.id.maintContentHomeBtn4);

		btnList = new ArrayList<View>();
		btnList.add(maintContentHomeBtn1);
		btnList.add(maintContentHomeBtn2);
		btnList.add(maintContentHomeBtn3);
		btnList.add(maintContentHomeBtn4);
		
		mainHomeBtnListenerMap = (Map<Integer, ButtonListenerBean>) PropertiesUtil.getFileValue("mainHomeBtnListenerMap");
		if(mainHomeBtnListenerMap==null){
			mainHomeBtnListenerMap = new HashMap<Integer,ButtonListenerBean>();
			for(View view :btnList){
				mainHomeBtnListenerMap.put(view.getId(), new ButtonListenerBean("日志","","log"));
			}
			PropertiesUtil.saveFileValue("mainHomeBtnListenerMap", mainHomeBtnListenerMap);
		}
	}

	private void addListener() {
		setLisener(maintContentHomeBtn1);
		setLisener(maintContentHomeBtn2);
		setLisener(maintContentHomeBtn3);
		setLisener(maintContentHomeBtn4);
	}
	

	/**
	 * 
	 * @param view
	 */
	public void setLisener(final View view){
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ButtonListenerBean buttonListenerBean = mainHomeBtnListenerMap.get(v.getId());
				String methodName = buttonListenerBean.methodName;
				logger.info("点击了主页按钮"+v.getId()+"执行方法："+methodName);
				CommunicationServiceInterface communicationService = CommonFactory.getCommunicationService();
				communicationService.refreshConnectableList();
				
				List<String> s = AndroidNetUtil.getLocalIpAddress();
				for (String ss : s) {
					logger.info("安卓网络工具类捕获到ip：" + ss);
				}
			}
		});
	}
	
}
