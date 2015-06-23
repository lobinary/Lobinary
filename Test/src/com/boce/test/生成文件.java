package com.boce.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class 生成文件 {
	
	public static void main(String[] args) throws IOException {
		for (int j = 6000; j <= 6000; j = j + 1) {
			long ll = 10000000000000L;
			long total = 0;
			long length = j*1;
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i <= length; i++) {
				total = total + i;
				ll += 1;
				sb.append("LB" + j +  ll+"|" + i + "|110000000000028|1|001|lvbin");
				if(i!=length)sb.append("\n");
			}
			File file = new File("i:/testdatae/" + length + "-" + total + ".txt");
			if(!file.exists()){
				file.createNewFile();
			}
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false)));
			br.append(sb);
			System.out.println(total);
			br.flush();
			br.close();
		}
	}

}
