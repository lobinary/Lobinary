package com.boce.util;

import java.io.File;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import com.boce.service.IRecFileService;
import com.boce.service.impl.ACCOUNTSYSTEMRecFileService;
import com.boce.service.impl.CITICB2CRecFileService;
import com.boce.service.impl.CUPFastRecFileService;

public class RecFileUtil {

	private static String generateTypeStr = "银联快捷|账务系统|中信B2C";
	private static String amountUnitStr = "元|分";
	private static JTextArea textArea = null;
	private static String outFileName = "自动生成对账文件.txt";
	private static String outRecFileDir = "";
	
	static{
		FileSystemView fsv = FileSystemView.getFileSystemView(); 
		outRecFileDir = fsv.getHomeDirectory().toString() + "\\" +  outFileName;
	}

	public static int generateAmount(String amountStr, String amtUnitStr) {
		int result = 0;
		if (amountStr.length() < 1 || amountStr.equals("")) {
			return result;
		}
		if (amtUnitStr.equals("元")) {
			float resultFloat = Float.parseFloat(amountStr);
			result = NumberUtils.yuanToFen(resultFloat);
		} else if (amtUnitStr.equals("分")) {
			result = Integer.parseInt(amountStr);
		}
		return result;
	}
	
	/**
	 * 获取并生成对账文件源文件
	 * @return
	 */
	public static File getOutFile(){
		File outFile = new File(outRecFileDir);
		outLog("生成的对账文件路径：" + outFile.getAbsolutePath());
		if(outFile.exists()&&outFile.isFile()){
			outLog("发现对账文件，准备注入新生成的对账文件数据！");
			return outFile;
		}else if(outFile.exists()&&!outFile.isFile()){
			outLog("发现对账文件同名文件夹，正在对其进行删除并新建文件操作，然后准备注入新生成的对账文件数据！");
			outFile.delete();
			try {
				outFile.createNewFile();
				outLog("对账源文件创建成功！");
			} catch (IOException e) {
				outLog("创建File文件失败,请您联系作者进行问题修复！");
			}
			return outFile;
		}else{
			try {
				outLog("对账文件还未创建，正在创建对账原文件,准备注入生成的对账数据！");
				outFile.createNewFile();
				outLog("对账源文件创建成功！");
			} catch (IOException e) {
				outLog("创建File文件失败,请您联系作者进行问题修复！");
			}
			return outFile;
		}

	}

	public static void setTextArea(JTextArea textArea) {
		RecFileUtil.textArea = textArea;
	}

	public static IRecFileService getRecServiceByStr(String recServiceStr) {
		if (recServiceStr.equals("银联快捷")) {
			return new CUPFastRecFileService();
		} else if (recServiceStr.equals("中信B2C")) {
			return new CITICB2CRecFileService();
		} else if (recServiceStr.equals("账务系统")){
			return new ACCOUNTSYSTEMRecFileService();
		}else {
			return null;
		}
	}

	/**
	 * 为comboBox提供数组
	 * 
	 * @return
	 */
	public static String[] getGenerateTypeArray() {
		return generateTypeStr.split("\\|");
	}

	/**
	 * 为comboBox提供数组
	 * 
	 * @return
	 */
	public static String[] getAmountUnitArray() {
		return amountUnitStr.split("\\|");
	}

	public static void main(String[] args) {
		System.out.println(getGenerateTypeArray()[1]);
		System.out.println(frontFillString("-","123",10));
	}

	/**
	 * 在日志框中输入日志
	 * @param logStr
	 */
	public static void outLog(String logStr) {
		System.out.println(logStr);
		StringBuffer sb = new StringBuffer();
		sb.append(textArea.getText());
		sb.append(logStr + "\n");
		textArea.setText(sb.toString());
	}
	
	/**
	 * 在日志框中输出获取到的对象
	 * @param type
	 * @param isValid
	 * @param orderNo
	 * @param serialNo
	 * @param amount
	 */
	public static void outDataLog(String type, boolean isValid,String orderNo, String serialNo, int amount){
		String typeChineseStr = "支付";
		String isValidChineseStr = "无效";
//		String orderNoChineseStr = "空";
//		String serialNoChineseStr = "空";
		if(type.equals("refund")){
			typeChineseStr = "退款";
		}
		if(isValid){
			isValidChineseStr = "有效";
		}
		outLog("获取到数据：交易类型:" + typeChineseStr + "\t 是否有效: " + isValidChineseStr + "\t 订单号: " + orderNo + "\t 流水号: " + serialNo + "\t 金额: " + amount );
	}
	
	/**
	 * 自动在前方填充任意字符串，一个fillStr默认为一个字符串长度
	 * @param fillStr
	 * @param originalStr
	 * @param length
	 * @return
	 */
	public static String frontFillString(String fillStr,String originalStr,int length){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length - originalStr.length(); i++) {
			sb.append(fillStr);
		}
		sb.append(originalStr);
		return sb.toString();
	}
	
	/**
	 * 自动在后方填充任意字符串，一个fillStr默认为一个字符串长度
	 * @param fillStr
	 * @param originalStr
	 * @param length
	 * @return
	 */
	public static String behindFillString(String fillStr,String originalStr,int length){
		StringBuffer sb = new StringBuffer();
		sb.append(originalStr);
		for (int i = 0; i < length - originalStr.length(); i++) {
			sb.append(fillStr);
		}
		return sb.toString();
	}

	public static String getGenerateTypeStr() {
		return generateTypeStr;
	}

	public static void setGenerateTypeStr(String generateTypeStr) {
		RecFileUtil.generateTypeStr = generateTypeStr;
	}

	public static String getAmountUnitStr() {
		return amountUnitStr;
	}

	public static void setAmountUnitStr(String amountUnitStr) {
		RecFileUtil.amountUnitStr = amountUnitStr;
	}

	public static String getOutFileName() {
		return outFileName;
	}

	public static void setOutFileName(String outFileName) {
		RecFileUtil.outFileName = outFileName;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public static String getOutRecFileDir() {
		return outRecFileDir;
	}

	public static void setOutRecFileDir(String outRecFileDir) {
		RecFileUtil.outRecFileDir = outRecFileDir;
	}

}
