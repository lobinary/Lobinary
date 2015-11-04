package com.lobinary.android.platform.ui.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.lobinary.android.common.pojo.communication.ConnectionBean;
import com.lobinary.android.common.pojo.model.Music;
import com.lobinary.android.common.service.communication.ConnectionThreadInterface;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.platform.R;
import com.lobinary.android.platform.pojo.bean.PinnerListBean;
import com.lobinary.android.platform.ui.fragment.MainTopFragment;
import com.lobinary.android.platform.ui.listview.AdapterListView;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView;

public class MusicActivity extends Activity {
	
	private static Logger logger = LoggerFactory.getLogger(MusicActivity.class);

	private PinnedSectionListView music_list_view;
	private AdapterListView musicListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.activity_music);
		initialView();
	}

	private void initialView() {
		music_list_view = (PinnedSectionListView) findViewById(R.id.music_list_view);	
		ConnectionBean currentOpreateConnection = MainTopFragment.getCurrentOpreateConnection();
		if(currentOpreateConnection==null){
			Toast.makeText(this, "刷新歌曲列表失败,当前无已连接设备", Toast.LENGTH_SHORT).show();
		}else{
//			List<Music> musicList = currentOpreateConnection.getConnectionThread().getMusicList();
		}
		Map<String, List<String>> dataMap = new HashMap<String, List<String>>();
		List<String> musicList = new ArrayList<String>();
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		musicList.add("123木头人.mp3");
		musicList.add("你是我的女朋友.mp3");
		musicList.add("对,就是你,是我女朋友.mp3");
		musicList.add("今天你要嫁给我.mp3");
		dataMap.put("轻音乐文件夹", musicList);
		dataMap.put("激情音乐文件夹", musicList);
		musicListAdapter = new AdapterListView(this, PinnerListBean.translateData(dataMap));
		music_list_view.setAdapter(musicListAdapter);
		music_list_view.setOnItemClickListener(getListenerForListView(musicListAdapter));
	}

	/**
	 * 功能页面增加监听程序
	 * 
	 * @return
	 */
	private OnItemClickListener getListenerForListView(final AdapterListView adapter) {
		return new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > 0) {
					PinnerListBean bean = adapter.getItem(position);
					if (bean.type == PinnerListBean.ITEM) {//如果是item
						ConnectionBean currentOpreateConnection = MainTopFragment.getCurrentOpreateConnection();
						if(currentOpreateConnection==null){
							Toast.makeText(MusicActivity.this, "调用“"+bean.text+"”失败,当前无已连接设备", Toast.LENGTH_SHORT).show();
						}else{
							
						}
						
					}
				}
			}
		};
	}

}
