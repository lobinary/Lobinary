package com.lobinary.android.platform.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.platform.R;

public class MainTopFragment extends Fragment {

	private static Logger logger = LoggerFactory.getLogger(MainTopFragment.class);

	public static Handler topContactHandler;
	String noClientInfo = "暂无已连接设备";


	private ImageButton mLeftMenu;
	Spinner contactSpinner;
	

	ArrayAdapter<String> contactSpinnerAdapter;
	ArrayList<String> contactSpinnerDataList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_top_fragment, container, false);
		initialView(view);
		setListener();
		topContactHandler = new Handler() {
			public void handleMessage(Message msg) {
				contactSpinnerDataList.clear();
				List<ConnectionBean> alreadyConnectionBean = CommonFactory.getCommunicationService().getAlreadyConnectionBean();
				for (ConnectionBean connectionBean : alreadyConnectionBean) {
					contactSpinnerDataList.add(connectionBean.getName());
				}
				if(contactSpinnerDataList.size()==0){
					contactSpinnerDataList.add(noClientInfo);
				}
				refreshContactSpinnerView();
			}
		};
		
		return view;
	}
	
	

	private void initialView(View view) {
		mLeftMenu = (ImageButton) view.findViewById(R.id.main_head_title_logo);
		contactSpinner = (Spinner) view.findViewById(R.id.spinner_contact_view);
		contactSpinnerDataList = new ArrayList<String>();
		contactSpinnerDataList.add(noClientInfo);

//		contactSpinner.setList(contactSpinnerDataList);
		refreshContactSpinnerView();
	}
	
	private void refreshContactSpinnerView(){
		contactSpinnerAdapter=new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_title_layout, contactSpinnerDataList);
		contactSpinnerAdapter.setDropDownViewResource(R.layout.contact_spinner_item);
		contactSpinner.setAdapter(contactSpinnerAdapter);
	}

	private void setListener() {
		mLeftMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "点我干啥，我是图标！，我是图标！ 我是图标！重要的事情说三遍", Toast.LENGTH_SHORT).show();
			}
		});
		
		contactSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { 
			
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long arg3) {
				logger.info("点击了按钮"+position);
			}

			@Override 
            public void onNothingSelected(AdapterView<?> arg0) { 
				logger.info("未选择");
            } 
        });
	}

}