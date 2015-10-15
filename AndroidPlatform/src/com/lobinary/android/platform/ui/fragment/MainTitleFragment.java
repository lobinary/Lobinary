package com.lobinary.android.platform.ui.fragment;

import com.lobinary.android.platform.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainTitleFragment extends Fragment {

	private ImageButton mLeftMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_head_title, container, false);
		mLeftMenu = (ImageButton) view.findViewById(R.id.main_head_title_logo);
		mLeftMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "点我干啥，点我干啥，我是图标！ ", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}
}
