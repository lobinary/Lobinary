/*
 * @(#)WindowsParamAcceptor.java     V1.0.0      @上午12:53:20
 *
 * 项目名称: ControlClientPC
 *
 * 更改 信息:
 *    作者        				   日期        			描述
 *    ============  	================  =======================================
 *    lobinary       	  2015年10月27日    	创建文件
 *
 */
package com.lobinary.apc.windows;

/**
 * <pre>
 * 
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年10月27日 上午12:53:20
 * @version V1.0.0 描述 : 创建文件WindowsParamAcceptor
 * 
 *         
 * 
 */
public class WindowsParamAcceptor {
	
	public static String musicPath;
	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.apc.windows.WindowsParamAcceptor#musicPath
	 * @return the musicPath
	 */
	public String getMusicPath() {
		return musicPath;
	}
	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.apc.windows.WindowsParamAcceptor#musicPath
	 * @param musicPath the musicPath to set
	 */
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.apc.windows.WindowsParamAcceptor#playerPath
	 * @return the playerPath
	 */
	public String getPlayerPath() {
		return playerPath;
	}
	/**
	 * 具体注释请点击Also see
	 * @see com.lobinary.apc.windows.WindowsParamAcceptor#playerPath
	 * @param playerPath the playerPath to set
	 */
	public void setPlayerPath(String playerPath) {
		this.playerPath = playerPath;
	}
	public static String playerPath;
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 */
	public static void main(String[] args) {
		ControlClientPCWindows aa = new ControlClientPCWindows();
//		musicPath = aa.musicPath;
//		playerPath = aa.playerPath;
	}
}
