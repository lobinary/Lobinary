package com.lobinary.android.platform.ui.fragment;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lobinary.android.common.constants.Constants;
import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.service.communication.socket.CommunicationSocketService;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.platform.R;
import com.lobinary.android.platform.service.communication.AndroidCommunicationSocketService;
import com.lobinary.android.platform.ui.listview.ListViewCompat;
import com.lobinary.android.platform.ui.listview.SlideView;
import com.lobinary.android.platform.ui.listview.SlideView.OnSlideListener;

public class MainContentContactFragment extends Fragment implements OnItemClickListener, OnClickListener, OnSlideListener  {

	private static final String TAG = "MainContentContactFragment";

	private static Logger logger = LoggerFactory.getLogger(CommunicationSocketService.class);
	
	Handler contactHandler;
	
	/**
	 * 当前Contact列表list
	 */
	private List<ConnectionBean> currentContactList = new ArrayList<ConnectionBean>();
	
	private ListViewCompat mListView;

	private List<MessageItem> mMessageItems = new ArrayList<MainContentContactFragment.MessageItem>();
	
	private SlideView mLastSlideViewWithStatusOn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_contact_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		contactHandler = new Handler() {
			public void handleMessage(Message msg) {
				refreshContact();
			}
		};
		AndroidCommunicationSocketService.contactHandler = this.contactHandler;
	}
	
	/**
	 * 初始化listView
	 */
	private void initView() {
		mListView = (ListViewCompat) getActivity().findViewById(R.id.contactContent);
//		MessageItem item = new MessageItem();
//		item.iconRes = R.drawable.default_qq_avatar;
//		item.title = "局域网PC服务器";
//		item.msg = "IP:127.0.0.1";
//		item.time = "未连接";
//		mMessageItems.add(item);
//		MessageItem item2 = new MessageItem();
//		item2.iconRes = R.drawable.wechat_icon;
//		item2.title = "公网PC服务器";
//		item2.msg = "URL:http://www.lobinary.com";
//		item2.time = "已连接";
//		mMessageItems.add(item2);
//		mListView.setAdapter(new SlideAdapter());
//		mListView.setOnItemClickListener(this);
	}

	/**
	 * 刷新contact列表
	 * @param connectionMap
	 */
	public void refreshContact() {
		Map<String, ConnectionBean> connectionMap = CommonFactory.getCommunicationService().connectionMap;
		currentContactList.clear();
		mMessageItems.clear();
		for (String key : connectionMap.keySet()) {
			ConnectionBean connectionBean = connectionMap.get(key);
			MessageItem item = new MessageItem();
			item.iconRes = R.drawable.sound;
			item.title = connectionBean.name;
			item.msg = connectionBean.ip;
			String statusName = "未连接";
			if(Constants.CONNECTION.STATUS_CONNECTION==connectionBean.status){
				statusName = "已连接";
			}else if(Constants.CONNECTION.STATUS_EXCEPTION==connectionBean.status){
				statusName = "连接异常";
			}
			item.time = statusName;
			mMessageItems.add(item);
			currentContactList.add(connectionBean);
			logger.info("增加新的Contact数据"+connectionBean.name);
			reloadContactListPage();
		}
	}

	
	private class SlideAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		SlideAdapter() {
			super();
			mInflater = getActivity().getLayoutInflater();
		}
		@Override
		public int getCount() {
			return mMessageItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mMessageItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			SlideView slideView = (SlideView) convertView;
			if (slideView == null) {
				View itemView = mInflater.inflate(R.layout.list_item, null);

				slideView = new SlideView(getActivity());
				slideView.setContentView(itemView);

				holder = new ViewHolder(slideView,position);
				slideView.setOnSlideListener(MainContentContactFragment.this);
				slideView.setTag(holder);
			} else {
				holder = (ViewHolder) slideView.getTag();
			}
			MessageItem item = mMessageItems.get(position);
			item.slideView = slideView;
			item.slideView.shrink();

			holder.icon.setImageResource(item.iconRes);
			holder.title.setText(item.title);
			holder.msg.setText(item.msg);
			holder.time.setText(item.time);
			holder.deleteHolder.setOnClickListener(MainContentContactFragment.this);

			return slideView;
		}

	}
	
	/**
	 * 重新根据list数据加载页面
	 */
	public void reloadContactListPage(){
		mListView.setAdapter(new SlideAdapter());
		mListView.setOnItemClickListener(this);
	}
	

	public class MessageItem {
		public int iconRes;
		public String title;
		public String msg;
		public String time;
		public SlideView slideView;
	}

	private static class ViewHolder {
		public ImageView icon;
		public TextView title;
		public TextView msg;
		public TextView time;
		public ViewGroup deleteHolder;

		ViewHolder(View view,int position) {
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
			msg = (TextView) view.findViewById(R.id.msg);
			time = (TextView) view.findViewById(R.id.time);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
			deleteHolder.setTag(position);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ConnectionBean connectionBean = currentContactList.get(position);
		try {
			CommonFactory.getCommunicationService().connect(connectionBean);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}

		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.holder) {
			int position = (Integer) v.getTag();
			ConnectionBean connectionBean = currentContactList.get(position);
			connectionBean.getConnectionThread().shutDownConnection();
		}
	}

}
