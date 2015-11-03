package com.lobinary.android.platform.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.util.InitialUtil;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		MainActivity.this.setContentView(R.layout.activity_main);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_main);

        InitialUtil.initial();//初始化
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
