package com.lobinary.android.platform.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lobinary.android.platform.R;

public class FeaturesLeftFragment extends Fragment {
	
	private FeaturesRightFragment rigthContent;

	private TextView features_query_btn_text;
	private TextView features_control_btn_text;
	private TextView features_communication_btn_text;
	
	private long activitionBtnId = R.id.homeBtnView;

	private RelativeLayout features_query_btn;
	private RelativeLayout features_control_btn;
	private RelativeLayout features_communication_btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.features_left, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialView();
		setBtnListener();
		setClickAnimal(R.id.homeBtnView);
	}
	
	private void initialView() {
		rigthContent = (FeaturesRightFragment) getFragmentManager().findFragmentById(R.id.features_right_content);
		
		features_query_btn_text = (TextView) getActivity().findViewById(R.id.features_query_btn_text);
		features_control_btn_text = (TextView) getActivity().findViewById(R.id.features_control_btn_text);
		features_communication_btn_text = (TextView) getActivity().findViewById(R.id.features_communication_btn_text);
		
		features_query_btn = (RelativeLayout) getActivity().findViewById(R.id.features_query_btn);
		features_control_btn = (RelativeLayout) getActivity().findViewById(R.id.features_control_btn);
		features_communication_btn = (RelativeLayout) getActivity().findViewById(R.id.features_communication_btn);
	}

	/**
	 * 设置按钮监听
	 */
	private void setBtnListener() {
		setLisener(features_query_btn);
		setLisener(features_control_btn);
		setLisener(features_communication_btn);
		
	}

	public void setClickAnimal(long clickBtnId){
//		rigthContent.clickShowContent(clickBtnId,activitionBtnId);
		if(activitionBtnId==R.id.homeBtnView){
			features_query_btn.setBackgroundColor(getResources().getColor(R.color.features_left));
			features_query_btn_text.setTextColor(getResources().getColor(R.color.black));
		}else if(activitionBtnId==R.id.contactBtnView){
			features_control_btn.setBackgroundColor(getResources().getColor(R.color.features_left));
			features_control_btn_text.setTextColor(getResources().getColor(R.color.black));
		}else if(activitionBtnId==R.id.featuresBtnView){
			features_communication_btn.setBackgroundColor(getResources().getColor(R.color.features_left));
			features_communication_btn_text.setTextColor(getResources().getColor(R.color.black));
		}

		if(activitionBtnId==R.id.homeBtnView){
			features_query_btn.setBackgroundColor(getResources().getColor(R.color.features_left_activition));
			features_query_btn_text.setTextColor(getResources().getColor(R.color.white));
		}else if(activitionBtnId==R.id.contactBtnView){
			features_control_btn.setBackgroundColor(getResources().getColor(R.color.features_left_activition));
			features_control_btn_text.setTextColor(getResources().getColor(R.color.white));
		}else if(activitionBtnId==R.id.featuresBtnView){
			features_communication_btn.setBackgroundColor(getResources().getColor(R.color.features_left_activition));
			features_communication_btn_text.setTextColor(getResources().getColor(R.color.white));
		}
		activitionBtnId = clickBtnId;
	}
	
	/**
	 * 
	 * @param view
	 */
	public void setLisener(final RelativeLayout view){
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FeaturesLeftFragment.this.setClickAnimal(view.getId());
			}
		});
	}

}
