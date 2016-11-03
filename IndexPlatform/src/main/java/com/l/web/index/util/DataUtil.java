package com.l.web.index.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.l.web.index.dto.SubSearchResultDto;

@SuppressWarnings("unchecked")
public class DataUtil {

	private static final String SEARCH_STORE = "SEARCH_STORE";


	private final static Logger logger = LoggerFactory.getLogger(DataUtil.class);
	

	private final static File STORE_FILE = new File("/app/data/IndexPlatform/store.dat");
	private static Map<String,Object> dataStore;
	
	static {
		if(!STORE_FILE.exists()){
			File storeFolder = new File("/app/data/IndexPlatform");
			storeFolder.mkdirs();
			try {
				STORE_FILE.createNewFile();
				dataStore = new Hashtable<>();
				FileUtil.saveObj(STORE_FILE, dataStore);
			} catch (Exception e) {
				logger.error("加载搜索存储文件异常",e);
			}
		}else{
			dataStore = FileUtil.getObj(STORE_FILE, Map.class);
		}
	}

	public static List<SubSearchResultDto> getSearchStore() {
		List<SubSearchResultDto> list = (List<SubSearchResultDto>) dataStore.get(SEARCH_STORE);
		if(list==null){
			list = new ArrayList<>();
			SubSearchResultDto initialSubSearchResultDto = new SubSearchResultDto();
			initialSubSearchResultDto.setDisplayStr("增加数据记录");
			initialSubSearchResultDto.setValueStr("/static/views/search/add.html");
			list.add(initialSubSearchResultDto);
			addData(SEARCH_STORE,list);
		}
		return list;
	}

	private static void addData(String key, Object value) {
		dataStore.put(key, value);
		FileUtil.saveObj(STORE_FILE, dataStore);
	}
	
}
