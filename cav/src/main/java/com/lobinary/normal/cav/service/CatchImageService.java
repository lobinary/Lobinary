package com.lobinary.normal.cav.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.lobinary.normal.cav.dao.CAVDao;
import com.lobinary.normal.cav.dto.CAVDto;
import com.lobinary.normal.cav.util.FileUtil;

public class CatchImageService {

	public static CAVDao cavDao = new CAVDao();
	public static int threadNum = 1;
	public static List<CAVDto> cavList = new ArrayList<CAVDto>();

	public static void main(String[] args) {

		for (int thread = 1; thread <= threadNum; thread++) {
			new Thread() {
				@Override
				public void run() {
					while (true) {
						CAVDto cav = getUnCatchImgCav();
						boolean isSuccess = false;
						int times = 1;
						while (!isSuccess) {
							long startTime = System.currentTimeMillis();
							isSuccess = go(cav);
							long endTime = System.currentTimeMillis();
							System.out.println("第" + cav.getId() + "条获取图片,第" + times + "次执行" + (endTime - startTime) + "毫秒,结果为：" + isSuccess);
							times++;
						}
					}
				}

			}.start();
		}
	}
	
	private static CAVDto getUnCatchImgCav() {
		if(cavList.size()>0){
			return cavList.remove(0);
		}else{
			cavList = cavDao.queryUnCatchImgCav(20);
			return cavList.remove(0);
		}
	}

	public static boolean go(CAVDto cav) {
		// cav.setId(123);
		// cav.setImageurl("http://www2.22.cn/UserFiles2014/image/zixun/w150521104327283.png");
		// cav.setType("C类;D类;");
		try {
			return assembleImg(cav);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean assembleImg(CAVDto cav) {
		String path = "D:/Lobinary项目/data/img/";
		String path2 = "";
		String imgUrl = cav.getImageurl();
		String fileName = "" + cav.getId();
		String type = cav.getType();
		if (type != null && type.length() > 0) {
			String[] types = type.split(";");
			boolean isMatch = false;
			for (String t : types) {
				if (t.equals("A类")) {
					isMatch = true;
					path2 = "A类";
					break;
				} else if (t.equals("B类")) {
					isMatch = true;
					path2 = "B类";
					break;
				}
			}
			if (!isMatch && types.length > 0) {
				path2 = types[0];
			} else {
				path2 = "其他";
			}
		} else {
			path2 = "其他";
		}
		String picType = getImgFromUrl(imgUrl, path+path2,fileName);
		String picLocalPath =  path2 + "/" + fileName + "." + picType;
//		System.out.println("图片保存的相对路径为：" + picLocalPath);
		return cavDao.updateLocalImgPath(cav.getId(), picLocalPath);
	}

	public static String getImgFromUrl(String urlstr, String savepath, String fileName) {
		int num = urlstr.indexOf('/', 8);
		int extnum = urlstr.lastIndexOf('.');
		String u = urlstr.substring(0, num);
		String ext = urlstr.substring(extnum + 1, urlstr.length());
		String imgPath = "";
		try {
			URL url = new URL(urlstr);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("referer", u); // 通过这个http头的伪装来反盗链
			BufferedImage image = ImageIO.read(connection.getInputStream());
			File path = new File(savepath);
			FileUtil.folderIsExist(path);
			FileOutputStream fout = new FileOutputStream(savepath + "/" + fileName + "." + ext);
			if ("gif".equals(ext) || "png".equals("png")) {
				ImageIO.write(image, ext, fout);
			}
			ImageIO.write(image, "jpg", fout);
			fout.flush();
			fout.close();
			imgPath = savepath + "/" + fileName + "." + ext;
//			System.out.println("图片保存完毕，路径为：" + imgPath);
			return ext;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ext;
	}

}
