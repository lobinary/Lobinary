package com.lobinary.android.platform.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.util.AndroidLogUtil;
import com.lobinary.android.platform.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class LogActivity extends Activity {
	
	public static List<String> logList = new ArrayList<String>();

//	Handler logHandler;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
    	TextView logText = (TextView) findViewById(R.id.logText);
        for(String log : logList){
			logText.setText(logText.getText() + "\n" + log);
        }
//        logHandler = new Handler() {
//			public void handleMessage(Message msg) {
//				TextView logText = (TextView) findViewById(R.id.logText);
//				logText.setText(logText.getText() + "\n" + msg.obj);
//			}
//		};
//		AndroidLogUtil.setLogHandler(logHandler);
    }
    
    /**
     * 输入日志内容到list
     * @param log
     */
    public static void outLog(String log){
    	logList.add(log);
    }
    
    /**
     * 清空日志
     * @param log
     */
    public static void clearLog(){
    	logList.clear();
    }

}
