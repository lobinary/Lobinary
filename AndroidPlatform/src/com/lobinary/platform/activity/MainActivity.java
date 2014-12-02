package com.lobinary.platform.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lobinary.platform.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivity.this.setContentView(R.layout.activity_main);
	}

}
