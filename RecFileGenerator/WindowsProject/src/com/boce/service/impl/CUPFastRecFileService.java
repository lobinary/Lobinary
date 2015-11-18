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
import com.boce.util.RecFileUtil;

public class CUPFastRecFileService implements IRecFileService {
	
	private long payTotalNum = 0l;
	private long payTotalAmount = 0l;
	private long refundTotalNum = 0l;
	private long refundTotalAmount = 0l;

	@Override
	public void generateRecFile(List<RecFileModel> list) {
		RecFileUtil.outLog("银联快捷对账文件开始生成！");
		File outFile = RecFileUtil.getOutFile();
		try {
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile,false)));
			StringBuffer sb = new StringBuffer();
			for(RecFileModel rfm: list){
				if(rfm.isValid()){
					if(rfm.getType().equals("pay")){
						payTotalNum ++;
						payTotalAmount += rfm.getAmount();
						sb.append("0302520160120101030252016012010103025201601201010200" + RecFileUtil.behindFillString(" ", rfm.getOrderNo(), 32) + "2015010309565105201501030956516216613600016987853  02c6839c3febb744909b9f7cf63e47e63300" + RecFileUtil.frontFillString("0", ""+rfm.getAmount(), 10) + "20150103d81949b8bf6b0a0b0cc5da0f52547fff\n");
					}else{
						refundTotalNum++;
						refundTotalAmount += rfm.getAmount();
						sb.append("1000000000000901100000000000090110000000000009010900" + RecFileUtil.behindFillString(" ", rfm.getOrderNo(), 32) + "2014080419051865201408041905186225682141000002950  028a80808c47a06d920147a0b16739000c00" + RecFileUtil.frontFillString("0", ""+rfm.getAmount(), 10) + "20140804aac6ade9e03107258c616fd81bbab928\n");
					}
				}
			}
			br.append("30088107736315130200000000000000" + RecFileUtil.frontFillString("0", ""+payTotalNum, 4) + "0000000000000000000000" + RecFileUtil.frontFillString("0", ""+payTotalAmount, 10) + "0900000000000000" + RecFileUtil.frontFillString("0", ""+refundTotalNum, 4) + "0000000000000000000000" + RecFileUtil.frontFillString("0", ""+refundTotalAmount, 10) + "0970000000000000000000000000000000000000000000000000" + "\n");
			RecFileUtil.outLog("10000000000009010200000000000000" + RecFileUtil.frontFillString("0", ""+payTotalNum, 4) + "0000000000000000000000" + RecFileUtil.frontFillString("0", ""+payTotalAmount, 10) + "0900000000000000" + RecFileUtil.frontFillString("0", ""+refundTotalNum, 4) + "0000000000000000000000" + RecFileUtil.frontFillString("0", ""+refundTotalAmount, 10) + "\n" + sb.toString());
			br.append(sb);
			br.flush();
			br.close();
		} catch (FileNotFoundException e) {
			RecFileUtil.outLog("#####运行出现问题：#####" + e.getMessage());
		} catch (IOException e) {
			RecFileUtil.outLog("#####运行出现问题：#####" + e.getMessage());
		}
		RecFileUtil.outLog("银联快捷对账文件生成结束！");
	}

}
