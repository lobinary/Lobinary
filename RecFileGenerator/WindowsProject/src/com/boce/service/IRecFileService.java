package com.boce.service;

import java.util.List;

import com.boce.model.RecFileModel;

public interface IRecFileService {
	
	/**
	 * 通过list生成对账文件
	 * @param list
	 */
	public void generateRecFile(List<RecFileModel> list);

}
