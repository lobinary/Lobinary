package com.lobinary.android.platform.ui.fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lobinary.android.common.util.ClientUtil;
import com.lobinary.android.platform.R;

public class MainContentSettingFragment extends Fragment {

	private static Logger logger = LoggerFactory.getLogger(MainContentSettingFragment.class);
	
	/**
	 * 客户端Id
	 */
	TextView clientIdText;
	/**
	 * 客户端名称
	 */
	TextView clientNameText;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_setting_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialView();
		addListener();
	}

	private void initialView() {
		clientIdText = (TextView) getActivity().findViewById(R.id.clientIdText);
		clientNameText = (TextView) getActivity().findViewById(R.id.clientNameText);
		clientIdText.setText(ClientUtil.clientId);
		clientNameText.setText(ClientUtil.clientName);
	}


	/**
	 * 增加监听
	 */
	private void addListener() {
		clientNameText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View view, boolean isFocued) {
				String clientName = clientNameText.getText().toString();
				ClientUtil.saveClientName(clientName);
			}
		});
	}
	
}
