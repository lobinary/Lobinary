package com.lobinary.android.platform.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lobinary.android.common.util.log.LogUtil;
import com.lobinary.android.platform.R;
import com.lobinary.android.platform.util.AndroidLogUtil;
import com.lobinary.android.platform.util.InitialUtil;

public class MainContentSettingFragment extends Fragment {

	Handler logHandler;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_setting_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		logHandler = new Handler() {
			public void handleMessage(Message msg) {
				TextView logText = (TextView) getActivity().findViewById(R.id.logText);
				logText.setText(logText.getText() + "\n" + msg.obj);
			}
		};
		AndroidLogUtil.setLogHandler(logHandler);
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 输出日志
	 * 
	 * @param message
	 */
	public final void outLog(final String message) {
		Message msg = new Message();
		msg.obj = message;
		logHandler.sendMessage(msg);
	}

}
