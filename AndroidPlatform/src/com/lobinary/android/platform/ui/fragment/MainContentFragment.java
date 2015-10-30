package com.lobinary.android.platform.ui.fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lobinary.android.platform.R;

public class MainContentFragment extends Fragment {

	private static Logger logger = LoggerFactory.getLogger(MainContentFragment.class);
	
	private static final String TAG = "MainContentFragment";

	private View homeContent;
	private View contactContent;
	private View featuresContent;
	private View settingContent;


	/**
	 * 点击切换主内容
	 * 
	 * @param clickBtnId
	 *            本次点击
	 * @param lastClick
	 *            上次点击
	 */
	public void clickShowContent(long clickBtnId, long lastClick) {
		if (lastClick == R.id.homeBtnBg) {
			homeContent.setVisibility(View.GONE);
		} else if (lastClick == R.id.contactBtnBg) {
			contactContent.setVisibility(View.GONE);
		} else if (lastClick == R.id.featuresBtnBg) {
			featuresContent.setVisibility(View.GONE);
		} else if (lastClick == R.id.settingBtnBg) {
			settingContent.setVisibility(View.GONE);
		}

		if (clickBtnId == R.id.homeBtnBg) {
			homeContent.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.contactBtnBg) {
			contactContent.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.featuresBtnBg) {
			featuresContent.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.settingBtnBg) {
			settingContent.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_fragment, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {

		homeContent = getActivity().findViewById(R.id.main_content_home_fragment);
		contactContent = getActivity().findViewById(R.id.main_content_contact_fragment);
		featuresContent = getActivity().findViewById(R.id.main_content_features_fragment);
		settingContent = getActivity().findViewById(R.id.main_content_setting_fragment);

//		homeContent.setVisibility(View.GONE);
		contactContent.setVisibility(View.GONE);
		featuresContent.setVisibility(View.GONE);
		settingContent.setVisibility(View.GONE);

	}


}
