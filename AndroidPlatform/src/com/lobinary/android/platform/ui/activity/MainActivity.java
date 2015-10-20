package com.lobinary.android.platform.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.lobinary.android.platform.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		MainActivity.this.setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_main);
//        Button testIntoListViewButtonView = (Button) findViewById(R.id.testIntoListViewButton);
//        testIntoListViewButtonView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent mainIntent = new Intent();
//				mainIntent.setClass(MainActivity.this, ChatListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(mainIntent);
////				MainActivity.this.finish();
//			}
//		});
        
        
//
//        Button testIntoSlideViewButtonView = (Button) findViewById(R.id.testIntoSlideViewButton);
//        testIntoSlideViewButtonView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent mainIntent = new Intent();
//				mainIntent.setClass(MainActivity.this, TestMessageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(mainIntent);
////				MainActivity.this.finish();
//			}
//		});
	}
	
	/**
	 * 返回键退出系统
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 在这里处理
			System.exit(0);
			return super.onKeyDown(keyCode, event);
		}
		return false;
	}
	
}
