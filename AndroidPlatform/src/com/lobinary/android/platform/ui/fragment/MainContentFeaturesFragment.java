package com.lobinary.android.platform.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.pojo.bean.FeaturesQueryListBean;
import com.lobinary.android.platform.ui.listview.AdapterListView;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView;

public class MainContentFeaturesFragment extends Fragment {

	private static final String TAG = "MainContentFeaturesFragment";
	

	private PinnedSectionListView listview;

	private AdapterListView adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_features_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialFeatures();
	}
	
	/**
	 * 初始化功能页面
	 */
	private void initialFeatures() {
		listview = (PinnedSectionListView) getActivity().findViewById(R.id.pinnedSectionListView1);
		listview.setAdapter(null);

		adapter = new AdapterListView(getActivity(), FeaturesQueryListBean.getData());
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(getListenerForListView());

		setListViewHeightBasedOnChildren(listview);
	}


	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			Log.e(TAG, "adapter为空");
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		int heightTemp = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		Log.e(TAG, "heightTemp设置成功：" + heightTemp);
		params.height = heightTemp;
		listView.setLayoutParams(params);
	}
	


	/**
	 * 功能页面增加监听程序
	 * 
	 * @return
	 */
	private OnItemClickListener getListenerForListView() {
		// TODO Auto-generated method stub
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					FeaturesQueryListBean bean = adapter.getItem(position);
					if (bean.type == FeaturesQueryListBean.ITEM) {
						Toast.makeText(getActivity(), bean.text, Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
	}

}
