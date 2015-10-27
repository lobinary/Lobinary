package com.lobinary.android.platform.ui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.dao.InteractionMessageDAO;
import com.lobinary.android.platform.pojo.bean.InteractionMessage;
import com.lobinary.android.platform.util.NetworkUtils;
import com.lobinary.android.platform.util.SystemUiHider;

import de.mindpipe.android.logging.log4j.LogConfigurator;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WelcomeActivity extends Activity {
	
	private boolean connectionStatus = false;
	
	  private Logger gLogger;
	     
    public void configLog()
    {
        final LogConfigurator logConfigurator = new LogConfigurator();
         
        logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "apc_log4j.log");
        // Set the root log level
        logConfigurator.setRootLevel(Level.DEBUG);
        // Set log level of a specific logger
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.configure();
 
        //gLogger = Logger.getLogger(this.getClass());
        gLogger = Logger.getLogger("CrifanLiLog4jTest");
    }

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	configLog();
    	gLogger.info("日志系统初始化完成");
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_welcome);

        final View contentView = findViewById(R.id.fullscreen_content);
        
        //透明度动画
        AlphaAnimation animation = new AlphaAnimation(0.1f,0.9f);
        animation.setDuration(1000);//动画间隔时间
		contentView.setAnimation(animation);
		
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				connectionStatus = NetworkUtils.networkExist(WelcomeActivity.this);
				if(connectionStatus){
					Toast.makeText(WelcomeActivity.this, "欢迎使用Lobinary Platform系统", Toast.LENGTH_SHORT).show();
					Intent mainIntent = new Intent();
//					mainIntent.setClass(WelcomeActivity.this, ChatListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mainIntent.setClass(WelcomeActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(mainIntent);
					WelcomeActivity.this.finish();
					//Toast.makeText(WelcomeActivity.this, "程序加载完成", Toast.LENGTH_SHORT).show();
				}else {
					NetworkUtils.setNetworkDialogExit(WelcomeActivity.this);
					Toast.makeText(WelcomeActivity.this, "请您连接wifi使用,谢谢!", Toast.LENGTH_SHORT).show();
				}
			}
		});

    }
    
    public void init(){
    	InteractionMessageDAO interactionMessageDAO = new InteractionMessageDAO(WelcomeActivity.this);
    	List<InteractionMessage> interactionMessages = new ArrayList<InteractionMessage>(2);
    	interactionMessages.add(new InteractionMessage());
    	interactionMessageDAO.add(interactionMessages);
    	List<InteractionMessage> list = interactionMessageDAO.query();
//    	Log.d(Constants.LOG.LOG_TAG, "获取到InteractionMessageList ，长度为：	" + list.size());
    	Toast.makeText(WelcomeActivity.this, "本次为第" + list.size() + "次访问本系统！", Toast.LENGTH_SHORT).show();
    }

}
