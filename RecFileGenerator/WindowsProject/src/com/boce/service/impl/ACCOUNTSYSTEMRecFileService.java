package com.boce.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.boce.model.RecFileModel;
import com.boce.service.IRecFileService;
import com.boce.util.NumberUtils;
import com.boce.util.RecFileUtil;

public class ACCOUNTSYSTEMRecFileService implements IRecFileService {
	
	private long payTotalNum = 0l;
	private long payTotalAmount = 0l;

	@Override
	public void generateRecFile(List<RecFileModel> list) {

		RecFileUtil.outLog("账务系统对账文件开始生成！");
		File outFile = RecFileUtil.getOutFile();
		try {
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile,false)));
			StringBuffer sb = new StringBuffer();
			for(RecFileModel rfm: list){
				String date = rfm.getSerialNo();
				if(date==""||date.length()==0){
					date = "20160606";
				}
				if(rfm.isValid()){
					payTotalNum ++;
					payTotalAmount += NumberUtils.fenToYuan(rfm.getAmount());
					sb.append(date + "|" + rfm.getOrderNo() + "|" + NumberUtils.fenToYuan(rfm.getAmount()) + "|201512311003785506|03|001|01|14146388720817172777|N");
					sb.append("\n");
				}
			}
			RecFileUtil.outLog("102000|" + payTotalNum + "|" + payTotalAmount + "\n" +sb);
			br.append("102000|" + payTotalNum + "|" +payTotalAmount + "\n");
			br.append(sb);
			br.flush();
			br.close();
		} catch (FileNotFoundException e) {
			RecFileUtil.outLog("#######运行出现问题：文件未找到#######" + e.getMessage());
		} catch (IOException e) {
			RecFileUtil.outLog("#####运行出现问题：文件IO输出异常#####" + e.getMessage());
		}
		RecFileUtil.outLog("账务系统对账文件生成结束！");
	
	}

}
