package com.lobinary.android.platform.ui.fragment;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.service.communication.ConnectionThreadInterface;
import com.lobinary.android.platform.R;
import com.lobinary.android.platform.pojo.bean.PinnerListBean;
import com.lobinary.android.platform.ui.activity.MusicActivity;
import com.lobinary.android.platform.ui.listview.AdapterListView;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView;

public class FeaturesRightFragment extends Fragment {

	private static Logger logger = LoggerFactory.getLogger(FeaturesRightFragment.class);

	private static final String TAG = "FeaturesRightFragment";

	private PinnedSectionListView feature_query_view;

	private PinnedSectionListView feature_control_view;

	private PinnedSectionListView feature_communication_view;

	public static Handler featureRightHandler;

	private AdapterListView queryAdapter;
	private AdapterListView controlAdapter;
	private AdapterListView communicationAdapter;

	long lastClick = 0;// 上次点击id

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

		lastClick = R.id.features_query_btn;
		feature_query_view.setVisibility(View.VISIBLE);
		feature_control_view.setVisibility(View.GONE);
		feature_communication_view.setVisibility(View.GONE);
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
		if (clickBtnId == lastClick)
			return;

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

		queryAdapter = new AdapterListView(getActivity(), PinnerListBean.getQueryData());
		feature_query_view.setAdapter(queryAdapter);
		feature_query_view.setOnItemClickListener(getListenerForListView(queryAdapter));
		feature_query_view.setOnItemLongClickListener(getLongClickListenerForListView(queryAdapter));

		controlAdapter = new AdapterListView(getActivity(), PinnerListBean.getControlData());
		feature_control_view.setAdapter(controlAdapter);
		feature_control_view.setOnItemClickListener(getListenerForListView(controlAdapter));

		communicationAdapter = new AdapterListView(getActivity(), PinnerListBean.getCommunicationData());
		feature_communication_view.setAdapter(communicationAdapter);
		feature_communication_view.setOnItemClickListener(getListenerForListView(communicationAdapter));

		setListViewHeightBasedOnChildren(feature_query_view);
	}

	/**
	 * 功能页面增加监听程序
	 * 
	 * @return
	 */
	private OnItemLongClickListener getLongClickListenerForListView(final AdapterListView adapter) {
		// TODO Auto-generated method stub
		return new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					PinnerListBean bean = adapter.getItem(position);
					if (bean.type == PinnerListBean.ITEM) {
						// Toast.makeText(getActivity(), "按钮"+bean.text+"被长按",
						// Toast.LENGTH_SHORT).show();

						final CharSequence[] items = { "刷新设备", "播放歌曲", "播放视频", "文件管理", "取消" };
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("选择替换主页功能");
						builder.setItems(items, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								Toast.makeText(getActivity().getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
							}
						});
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				return true;
			}

		};
	}

	/**
	 * 调用方法
	 */
	public void invokeMethod(String itemName, int position) {
		ConnectionBean currentOpreateConnection = MainTopFragment.getCurrentOpreateConnection();
		if (currentOpreateConnection == null) {
			Toast.makeText(getActivity(), "调用“" + itemName + "”失败,当前无已连接设备", Toast.LENGTH_SHORT).show();
		} else {
			ConnectionThreadInterface connectionThread = currentOpreateConnection.getConnectionThread();
			String baseMethodName = PinnerListBean.baseMethodName(itemName);
			if (baseMethodName == null) {
				if (itemName.equals("音乐")) {
					Intent mainIntent = new Intent();
					getActivity().overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
					mainIntent.setClass(getActivity(), MusicActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 只存在一个activity
					startActivity(mainIntent);
					// WelcomeActivity.this.finish();
				} else {
					Toast.makeText(getActivity(), "对不起,“" + itemName + "”功能暂未实现", Toast.LENGTH_SHORT).show();
				}
			} else {
				try {
					// 如果是普通 远程方法 只是true false 返回则可以反射调用 如果有参数
					// 需要特殊处理
					Class<?> clazz = connectionThread.getClass();
					Method m1 = clazz.getDeclaredMethod(baseMethodName);
					boolean result = (Boolean) m1.invoke(connectionThread);
					Toast.makeText(getActivity(), "调用“" + itemName + "”" + (result ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					logger.error("调用当前连接设备(" + currentOpreateConnection.name + ")时发生异常", e);
				}
			}
		}

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
					PinnerListBean bean = adapter.getItem(position);
					if (bean.type == PinnerListBean.ITEM) {
						invokeMethod(bean.text, position);
					}
				}
			}
		};
	}

	/**
	 * 该方法暂时无用
	 * 
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
		// Log.e(TAG, "heightTemp设置成功：" + heightTemp);
		params.height = heightTemp;
		listView.setLayoutParams(params);
	}

}
