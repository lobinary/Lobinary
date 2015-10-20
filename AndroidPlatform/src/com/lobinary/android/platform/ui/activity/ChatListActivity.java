package com.lobinary.android.platform.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.ui.listview.ListViewCompat;
import com.lobinary.android.platform.ui.listview.SlideView;
import com.lobinary.android.platform.ui.listview.SlideView.OnSlideListener;

public class ChatListActivity extends Activity implements OnItemClickListener, OnClickListener,
        OnSlideListener {

	private MenuItem menuItem = null;  
	
    private static final String TAG = "MainActivity";

    private ListViewCompat mListView;

    private List<MessageItem> mMessageItems = new ArrayList<ChatListActivity.MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "正在创建主页面");
        setContentView(R.layout.main_content_fragment);
//        setContentView(R.layout.activity_main);
        initView();
//        getFragmentManager().findFragmentById(R.id.chat_list).setHasOptionsMenu(true);
//        savedInstanceState.setHasOptionMenu(true);
    }
    
    

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	Log.d(TAG, "准备初始化菜单栏.");
    	getMenuInflater().inflate(R.menu.main_head_menu, menu);  
        return true; 
//		return super.onCreateOptionsMenu(menu);
	}

    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
        case R.id.action_refresh:  
            Toast.makeText(this, "Menu Item refresh selected",  
                    Toast.LENGTH_SHORT).show();  
            break;  
        case R.id.action_about:  
            Toast.makeText(this, "Menu Item about selected", Toast.LENGTH_SHORT)  
                    .show();  
            break;  
        case R.id.action_edit:  
            Toast.makeText(this, "Menu Item edit selected", Toast.LENGTH_SHORT)  
                    .show();  
            break;  
        case R.id.action_search:  
            Toast.makeText(this, "Menu Item search selected",  
                    Toast.LENGTH_SHORT).show();  
            break;  
        case R.id.action_help:  
            Toast.makeText(this, "Menu Item  settings selected",  
                    Toast.LENGTH_SHORT).show();  
            break;  
        default:  
            break;  
        }  
        return super.onOptionsItemSelected(item);  
    }  

	private void initView() {
        mListView = (ListViewCompat) findViewById(R.id.homeContent);
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
            mInflater = getLayoutInflater();
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

                slideView = new SlideView(ChatListActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(ChatListActivity.this);
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
            holder.deleteHolder.setOnClickListener(ChatListActivity.this);

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
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
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
