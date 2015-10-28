package com.lobinary.android.platform.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lobinary.android.platform.R;

public class FeaturesRightFragment extends Fragment{

	private static final String TAG = "FeaturesRightFragment";

	private View homeContent;

	private View contactContent;

	private View featuresContent;

	/**
	 * 点击切换主内容
	 * 
	 * @param clickBtnId
	 *            本次点击
	 * @param lastClick
	 *            上次点击
	 */
	public void clickShowContent(long clickBtnId, long lastClick) {

		if (lastClick == R.id.homeBtnView) {
			homeContent.setVisibility(View.GONE);
		} else if (lastClick == R.id.contactBtnView) {
			contactContent.setVisibility(View.GONE);
		} else if (lastClick == R.id.featuresBtnView) {
			featuresContent.setVisibility(View.GONE);
		} else if (lastClick == R.id.settingBtnView) {

		}

		if (clickBtnId == R.id.homeBtnView) {
			homeContent.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.contactBtnView) {
			contactContent.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.featuresBtnView) {
			featuresContent.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.settingBtnView) {

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		return inflater.inflate(R.layout.features_right, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


}
