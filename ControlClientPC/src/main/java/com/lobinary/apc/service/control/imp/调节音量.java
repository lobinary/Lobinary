/*

 * @(#)调节音量.java     V1.0.0      @下午5:05:40
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015-11-2    	创建文件
 *
 */
package com.lobinary.apc.service.control.imp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.io.InputStreamReader;

import javax.swing.JPanel;

public class 调节音量 extends JPanel implements Runnable {
	String welcomeStr = "Welcome to Java Sound";
	Thread pbThread;
	Color background = Color.white;
	// new Color(20, 20, 20);
	Color jfcBlue = Color.blue;
	// new Color(204, 204, 255);
	Color jfcDarkBlue = jfcBlue.darker();
	Font font24 = new Font("serif", Font.BOLD, 24);
	Font font28 = new Font("serif", Font.BOLD, 28);
	Font font42 = new Font("serif", Font.BOLD, 42);
	FontMetrics fm28, fm42;
	String errStr = null;
	String currentName = null;
	double duration = 100.0;
	double seconds = 82.0;
	boolean midiEOM, audioEOM;

	public 调节音量() {
		fm28 = getFontMetrics(font28);
		fm42 = getFontMetrics(font42);
		initVolume();
		start();
	}
	
	public static void main(String[] args) {
		调节音量  t = new 调节音量();
		t.start();
	}

	private void initVolume() {
		try {
			// 这一段小程序实现对VC创建程序的调用
			Runtime rt = Runtime.getRuntime(); // Time and Date.
			// mngPathTool类,提供了一个获取当前路径的方法
			mngPathTool tool = new mngPathTool();
			String sexec = tool.getCurPath() + "\\binex\\VolumeControl.exe 0";
			Process child = rt.exec(sexec);
			// 获取控制台输出的内容,进而获得音量的大小
			InputStreamReader reader = new InputStreamReader(child.getInputStream());
			char[] chr = new char[5];
			reader.read(chr);
			String s = "";
			for (int i = 0; i < 5; i++) {
				if (chr[i] >= '0' && chr[i] <= '9')
					s += chr[i];
			}
			// System.out.println(s);
			Integer nVolume = new Integer(s);
			seconds = nVolume.intValue();
			child.waitFor();
			// 这一段小程序实现对VC创建程序的调用
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		// 画图来实现百分比Tracker
		Graphics2D g2 = (Graphics2D) g;
		Dimension d = getSize();
		g2.setBackground(background);
		g2.clearRect(0, 0, d.width, d.height);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(jfcBlue);
		double tseconds = duration - seconds;
		if (tseconds > 0.0) {
			int num = 20;
			int progress = (int) (tseconds / duration * num);
			double hh = ((double) (d.height - 4) / (double) num);
			double ww = (int) (d.width - 4);
			double x = 0.0;
			for (; x < progress; x += 1.0) {
				g2.fill(new Rectangle2D.Double(d.width - ww - 2, x * hh + 2, ww, hh));
				g2.fill3DRect((int) (d.width - ww - 2), (int) (x * hh + 2), (int) ww, (int) hh, true);
			}
			g2.setColor(jfcDarkBlue);
			for (; x < num; x += 1.0) {
				g2.fill(new Rectangle2D.Double(d.width - ww - 2, x * hh + 2, ww, hh));
				g2.fill3DRect((int) (d.width - ww - 2), (int) (x * hh + 2), (int) ww, (int) hh, true);
			}
		}
	}

	public void start() {
		pbThread = new Thread(this);
		pbThread.setName("PlaybackMonitor");
		pbThread.start();
	}

	public void stop() {
		if (pbThread != null) {
			pbThread.interrupt();
		}
		pbThread = null;
	}

	public void run() {
		while (pbThread != null) {
			try {
				pbThread.sleep(99);
			} catch (Exception e) {
				break;
			}
			repaint();
		}
		pbThread = null;
	}

	public void addVolume() {
		changeVolume(false);
		initVolume();
	}

	public void minusVolume() {
		changeVolume(true);
		initVolume();
	}

	// control sound volume.

	private void changeVolume(boolean bIsMinus) {
		try {
			Runtime rt = Runtime.getRuntime();
			// Sound Control mngPathTool
			mngPathTool tool = new mngPathTool();
			String sexec;
			if (bIsMinus)
				sexec = tool.getCurPath() + "\\binex\\VolumeControl.exe 2";
			else
				sexec = tool.getCurPath() + "\\binex\\VolumeControl.exe 1";
			rt.exec(sexec);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
// End VolumeTracker

