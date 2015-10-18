package com.lobinary.android.platform.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.ui.activity.ListViewCompat;
import com.lobinary.android.platform.ui.activity.SlideView;
import com.lobinary.android.platform.ui.activity.SlideView.OnSlideListener;

public class MainContentFragment extends Fragment implements OnItemClickListener, OnClickListener, OnSlideListener {

	private static final String TAG = "MainActivity";

	private ListViewCompat mListView;

	private List<MessageItem> mMessageItems = new ArrayList<MainContentFragment.MessageItem>();

	private SlideView mLastSlideViewWithStatusOn;

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

		return inflater.inflate(R.layout.main_content_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		homeContent = getActivity().findViewById(R.id.homeContent);
		contactContent = getActivity().findViewById(R.id.contactContent);
		featuresContent = getActivity().findViewById(R.id.featuresContent);

		mListView = (ListViewCompat) getActivity().findViewById(R.id.contactContent);
		MessageItem item = new MessageItem();
		item.iconRes = R.drawable.default_qq_avatar;
		item.title = "局域网PC服务器";
		item.msg = "IP:127.0.0.1";
		item.time = "未连接";
		mMessageItems.add(item);
		MessageItem item2 = new MessageItem();
		item2.iconRes = R.drawable.wechat_icon;
		item2.title = "公网PC服务器";
		item2.msg = "URL:http://www.lobinary.com";
		item2.time = "已连接";
		mMessageItems.add(item2);
		mListView.setAdapter(new SlideAdapter());
		mListView.setOnItemClickListener(this);
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

				holder = new ViewHolder(slideView);
				slideView.setOnSlideListener(MainContentFragment.this);
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
			holder.deleteHolder.setOnClickListener(MainContentFragment.this);

			return slideView;
		}

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

		ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.icon);
			title = (TextView) view.findViewById(R.id.title);
			msg = (TextView) view.findViewById(R.id.msg);
			time = (TextView) view.findViewById(R.id.time);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.e(TAG, "点击位置：" + position);

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
			Log.e(TAG, "点击View对象：" + v);
		}
	}

}
