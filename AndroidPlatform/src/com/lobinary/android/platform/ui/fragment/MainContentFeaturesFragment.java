package com.lobinary.android.platform.ui.fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lobinary.android.platform.R;

public class MainContentFeaturesFragment extends Fragment {

	private static Logger logger = LoggerFactory.getLogger(MainContentFeaturesFragment.class);
	
	private static final String TAG = "MainContentFeaturesFragment";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_features_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
}
