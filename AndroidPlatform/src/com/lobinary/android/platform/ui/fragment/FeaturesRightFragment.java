package com.lobinary.android.platform.ui.fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.lobinary.android.platform.pojo.bean.FeaturesListBaseBean;
import com.lobinary.android.platform.ui.listview.AdapterListView;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView;

public class FeaturesRightFragment extends Fragment{
	
	private static Logger logger = LoggerFactory.getLogger(FeaturesRightFragment.class);

	private static final String TAG = "FeaturesRightFragment";

	private PinnedSectionListView feature_query_view;

	private PinnedSectionListView feature_control_view;

	private PinnedSectionListView feature_communication_view;
	
	public static Handler featureRightHandler;

	private AdapterListView queryAdapter;
	private AdapterListView controlAdapter;
	private AdapterListView communicationAdapter;
	
	long lastClick = R.id.features_query_btn;//上次点击id

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.features_right, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialView();
		initialHandler();
		initialFeatures();
	}

	private void initialHandler() {
		featureRightHandler = new Handler() {
			public void handleMessage(Message msg) {
				int viewId = (Integer) msg.obj;
				clickShowContent(viewId);
			}
		};
	}

	private void initialView() {
		feature_query_view = (PinnedSectionListView) getActivity().findViewById(R.id.feature_query_view);
		feature_control_view = (PinnedSectionListView) getActivity().findViewById(R.id.feature_control_view);
		feature_communication_view = (PinnedSectionListView) getActivity().findViewById(R.id.feature_communication_view);
		clickShowContent(R.id.features_query_btn_text);
		
		feature_query_view.setVisibility(View.VISIBLE);
	}

	/**
	 * 点击切换主内容
	 * 
	 * @param clickBtnId
	 *            本次点击
	 * @param lastClick
	 *            上次点击
	 */
	public void clickShowContent(int clickBtnId) {
		if(clickBtnId==lastClick)return;
		
		if (lastClick == R.id.features_query_btn) {
			feature_query_view.setVisibility(View.GONE);
		} else if (lastClick == R.id.features_control_btn) {
			feature_control_view.setVisibility(View.GONE);
		} else if (lastClick == R.id.features_communication_btn) {
			feature_communication_view.setVisibility(View.GONE);
		}
		
		if (clickBtnId == R.id.features_query_btn) {
			feature_query_view.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.features_control_btn) {
			feature_control_view.setVisibility(View.VISIBLE);
		} else if (clickBtnId == R.id.features_communication_btn) {
			feature_communication_view.setVisibility(View.VISIBLE);
		}
		
		lastClick = clickBtnId;
	}
	
	/**
	 * 初始化功能页面
	 */
	private void initialFeatures() {

		queryAdapter = new AdapterListView(getActivity(), FeaturesListBaseBean.getQueryData());
		feature_query_view.setAdapter(queryAdapter);
		feature_query_view.setOnItemClickListener(getListenerForListView(queryAdapter));
		
		controlAdapter = new AdapterListView(getActivity(), FeaturesListBaseBean.getControlData());
		feature_control_view.setAdapter(controlAdapter);
		feature_control_view.setOnItemClickListener(getListenerForListView(controlAdapter));

		communicationAdapter = new AdapterListView(getActivity(), FeaturesListBaseBean.getCommunicationData());
		feature_communication_view.setAdapter(communicationAdapter);
		feature_communication_view.setOnItemClickListener(getListenerForListView(communicationAdapter));

		setListViewHeightBasedOnChildren(feature_query_view);
	}



	/**
	 * 功能页面增加监听程序
	 * 
	 * @return
	 */
	private OnItemClickListener getListenerForListView(final AdapterListView adapter) {
		// TODO Auto-generated method stub
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					FeaturesListBaseBean bean = adapter.getItem(position);
					if (bean.type == FeaturesListBaseBean.ITEM) {
						Toast.makeText(getActivity(), bean.text, Toast.LENGTH_SHORT).show();
					}
				}
			}
		};
	}
	

	/**
	 * 该方法暂时无用
	 * @param listView
	 */
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
//		Log.e(TAG, "heightTemp设置成功：" + heightTemp);
		params.height = heightTemp;
		listView.setLayoutParams(params);
	}
	
	
}
