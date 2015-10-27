package com.lobinary.android.platform.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lobinary.android.common.util.log.LogUtil;
import com.lobinary.android.platform.R;

public class MainBottomFragment extends Fragment {
	
	private MainContentFragment mainContent;

	private ImageView homeBtnView;
	private ImageView contactBtnView;
	private ImageView featuresBtnView;
	private ImageView settingBtnView;

	private TextView homeBtnText;
	private TextView contactBtnText;
	private TextView featuresBtnText;
	private TextView settingBtnText;
	
	private long activitionBtnId = R.id.homeBtnView;

	private LinearLayout homeBtnBg;

	private LinearLayout contactBtnBg;

	private LinearLayout functionBtnBg;

	private LinearLayout settingBtnBg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_bottom_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialView();
		setBtnListener();
		setClickAnimal(R.id.homeBtnView);
	}
	
	private void initialView() {
		mainContent = (MainContentFragment) getFragmentManager().findFragmentById(R.id.main_content_fragment);
		
		homeBtnView = (ImageView) getActivity().findViewById(R.id.homeBtnView);
		contactBtnView = (ImageView) getActivity().findViewById(R.id.contactBtnView);
		featuresBtnView = (ImageView) getActivity().findViewById(R.id.featuresBtnView);
		settingBtnView = (ImageView) getActivity().findViewById(R.id.settingBtnView);  
		
		homeBtnText = (TextView) getActivity().findViewById(R.id.homeBtnText);
		contactBtnText = (TextView) getActivity().findViewById(R.id.contactBtnText);
		featuresBtnText = (TextView) getActivity().findViewById(R.id.functionBtnText);
		settingBtnText = (TextView) getActivity().findViewById(R.id.settingBtnText);  
		
		homeBtnBg = (LinearLayout) getActivity().findViewById(R.id.homeBtnBg);
		contactBtnBg = (LinearLayout) getActivity().findViewById(R.id.contactBtnBg);
		functionBtnBg = (LinearLayout) getActivity().findViewById(R.id.functionBtnBg);
		settingBtnBg = (LinearLayout) getActivity().findViewById(R.id.settingBtnBg);		
	}

	/**
	 * 设置按钮监听
	 */
	private void setBtnListener() {
		setLisener(homeBtnView);
		setLisener(contactBtnView);
		setLisener(featuresBtnView);
		setLisener(settingBtnView);
		
	}

	public void setClickAnimal(long clickBtnId){
		LogUtil.out("准备切换页面"+clickBtnId);
		mainContent.clickShowContent(clickBtnId,activitionBtnId);
		if(activitionBtnId==R.id.homeBtnView){
			homeBtnView.setImageDrawable(getResources().getDrawable(R.drawable.home));
			homeBtnText.setTextColor(getResources().getColor(R.color.grey));
		}else if(activitionBtnId==R.id.contactBtnView){
			contactBtnView.setImageDrawable(getResources().getDrawable(R.drawable.contact));
			contactBtnText.setTextColor(getResources().getColor(R.color.grey));
		}else if(activitionBtnId==R.id.featuresBtnView){
			featuresBtnView.setImageDrawable(getResources().getDrawable(R.drawable.features));
			featuresBtnText.setTextColor(getResources().getColor(R.color.grey));
		}else if(activitionBtnId==R.id.settingBtnView){
			settingBtnView.setImageDrawable(getResources().getDrawable(R.drawable.setting));
			settingBtnText.setTextColor(getResources().getColor(R.color.grey));
		}

		if(clickBtnId==R.id.homeBtnView){
			homeBtnView.setImageDrawable(getResources().getDrawable(R.drawable.home_activition));
			homeBtnText.setTextColor(getResources().getColor(R.color.orangered));
		}else if(clickBtnId==R.id.contactBtnView){
			contactBtnView.setImageDrawable(getResources().getDrawable(R.drawable.contact_activition));
			contactBtnText.setTextColor(getResources().getColor(R.color.orangered));
		}else if(clickBtnId==R.id.featuresBtnView){
			featuresBtnView.setImageDrawable(getResources().getDrawable(R.drawable.features_activition));
			featuresBtnText.setTextColor(getResources().getColor(R.color.orangered));
		}else if(clickBtnId==R.id.settingBtnView){
			settingBtnView.setImageDrawable(getResources().getDrawable(R.drawable.setting_activition));
			settingBtnText.setTextColor(getResources().getColor(R.color.orangered));
		}
		activitionBtnId = clickBtnId;
	}
	
	/**
	 * 
	 * @param view
	 */
	public void setLisener(final ImageView view){
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainBottomFragment.this.setClickAnimal(view.getId());
			}
		});
	}

}
