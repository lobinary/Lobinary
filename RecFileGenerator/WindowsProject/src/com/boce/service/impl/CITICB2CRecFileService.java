package com.boce.service.impl;

import java.util.List;

import com.boce.model.RecFileModel;
import com.boce.service.IRecFileService;
import com.boce.util.RecFileUtil;

public class CITICB2CRecFileService implements IRecFileService {

	@Override
	public void generateRecFile(List<RecFileModel> list) {
		RecFileUtil.outLog("中信B2C对账文件生成中......");
	}

}
