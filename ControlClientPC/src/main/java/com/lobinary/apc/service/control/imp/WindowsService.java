/*
 * @(#)WindowsService.java     V1.0.0      @下午11:20:59
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月22日    	创建文件
 *
 */
package com.lobinary.apc.service.control.imp;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.media.GainControl;
import javax.media.Player;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lobinary.android.common.constants.CodeDescConstants;
import com.lobinary.android.common.exception.APCSysException;
import com.lobinary.android.common.pojo.communication.Message;
import com.lobinary.android.common.pojo.model.Music;
import com.lobinary.android.common.service.control.BaseServiceInterface;
import com.lobinary.apc.windows.ControlClientPCWindows;

/**
 * <pre>
 * windows业务实现类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月22日 下午11:20:59
 * @version V1.0.0 描述 : 创建文件WindowsService
 * 
 *         
 * 
 */
public class WindowsService implements BaseServiceInterface {
	
	private static Logger logger = LoggerFactory.getLogger(ControlClientPCWindows.class);

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#getCurrentTime()
	 */
	@Override
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#shutDown(long)
	 */
	@Override
	public boolean shutDown(long delayTime) {
		try {
			Process pt =Runtime.getRuntime().exec("shutdown -s -f -t "+delayTime);//f强制关机 s关机 t 时间
		} catch (IOException e) {
			logger.info("关闭计算机时发生异常",e);
			return false;
		} 
		return true;
	}
	
	public boolean cancelShutDown(){
		try {
			Process pt =Runtime.getRuntime().exec("shutdown -a");
		} catch (IOException e) {
			logger.info("关闭计算机时发生异常",e);
			return false;
		} 
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#playMusic(com.lobinary.android.common.pojo.communication.Command)
	 */
	@Override
	public boolean playMusic(String player,String musicId) {
		try {
//			CommandParam commandParam = command.getCommandParam();
//			String player = commandParam.player;
//			String musicName = commandParam.musicName;
			String musicPath = "I:\\KuGou\\黑涩会美眉-123木头人.mp3";
			Runtime.getRuntime().exec("cmd /c start "   +   musicPath.replaceAll(" ", "\" \""));
			//调用酷狗播放歌曲
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#getMusicList(com.lobinary.android.common.pojo.communication.Command)
	 */
	@Override
	public List<Music> getMusicList() {
		String musicFolderStr = ControlClientPCWindows.musicFolderText.getText();
		File mf = new File(musicFolderStr);
		File[] files = mf.listFiles();
		for(File f : files){
			logger.info("获取音乐列表业务:找到文件,文件名称:"+f.getName()+",文件大小:"+f.getTotalSpace()+"文件路径:"+f.getPath());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#increaseVoice()
	 */
	@Override
	public boolean increaseVoice() {
		try {
			Process process =Runtime.getRuntime().exec("sndvol.exe");
			keyPress(KeyEvent.VK_UP,6);
			process.destroy();
		} catch (Exception e) {
			logger.info("增加音量异常",e);
			throw new APCSysException(CodeDescConstants.SERVICE_CONTORL_INCREASE_VOICE_EXCEPTION,e);
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		Player player = null;
		GainControl gainControl = null;//player.getGainControl();
		gainControl.setLevel(20);//Volume是音量值
		System.out.println("居然没报错");
//		File file = File.createTempFile("C:/ylkz_vba",".vbs");
//		System.out.println(file.getAbsoluteFile());
////		file.deleteOnExit();
//		FileWriter fw = new FileWriter(file);
//		String vbs ="'以下命令实现音量减（用循环可以实现一直减）：\n" +
//		"Set WshShell = CreateObject(\"WScript.Shell\")\n" + 
//		"Ws.Sendkeys \"爱\"";
////		"'音量增（用循环可以实现一直增）：\n" + 
////		"Set WshShell = CreateObject(\"WScript.Shell\")\n" + 
////		"WshShell.SendKeys(chr(&hAF))";
//		fw.write(vbs);
//		fw.close();
//		Runtime.getRuntime().exec("wscript " + file.getPath()).waitFor();
	}

	/**
	 * 
	 * <pre>
	 * 通过java代码点击键盘按键
	 * </pre>
	 *
	 * @param key KeyEvent.VK_*
	 * @param times
	 * @throws AWTException
	 */
	private void keyPress(int key,int times) throws AWTException {
		Robot robot = new Robot();
		for (int i = 0; i < times; i++) {
			robot.keyPress(key);
			try {
				Thread.sleep(100); //点击按键是否需要睡眠等待具体测试时候调试
				robot.keyRelease(key);
				Thread.sleep(100);//点击按键是否需要睡眠等待具体测试时候调试
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#decreaseVoice()
	 */
	@Override
	public boolean decreaseVoice() {
		try {
			Process process =Runtime.getRuntime().exec("sndvol.exe");
			keyPress(KeyEvent.VK_DOWN,6);
			process.destroy();
		} catch (Exception e) {
			logger.info("增加音量异常",e);
			throw new APCSysException(CodeDescConstants.SERVICE_CONTORL_DECREASE_VOICE_EXCEPTION,e);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lobinary.android.common.service.control.BaseServiceInterface#shutDown()
	 */
	@Override
	public boolean shutDown() {
		try {
			Process pt =Runtime.getRuntime().exec("shutdown -s -f -t 30 ");//f强制关机 s关机 t 时间
		} catch (IOException e) {
			logger.info("关闭计算机时发生异常",e);
			return false;
		} 
		return true;
	}
	
}
