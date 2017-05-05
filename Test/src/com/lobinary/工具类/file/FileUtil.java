package com.lobinary.工具类.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * <pre>
 * 文件工具类
 * </pre>
 * @author 吕斌：lvb3@chinaunicom.cn
 * @since 2015年3月24日 下午4:14:40
 * @version V1.0.0 描述 : 创建文件FileUtil
 * 
 *         
 *
 */
public class FileUtil {
	
	/**
	 * 将读取的文件放到list中
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLine2List(File file) throws IOException{
		List<String> fileLineContent = new ArrayList<String>();
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		String fileLine ;
		while((fileLine = reader.readLine())!=null){
			fileLineContent.add(fileLine);
		}
		reader.close();
		return fileLineContent;
	}
	
	/**
	 * 复制文件
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(File fromFile,File toFile) throws IOException{
		fileIsExist(fromFile);
		fileIsExist(toFile);
		  // 新建文件输入流并对它进行缓冲   
        FileInputStream input = new FileInputStream(fromFile);  
        BufferedInputStream inBuff=new BufferedInputStream(input);  
  
        // 新建文件输出流并对它进行缓冲   
        FileOutputStream output = new FileOutputStream(toFile);  
        BufferedOutputStream outBuff=new BufferedOutputStream(output);  
          
        // 缓冲数组   
        byte[] b = new byte[1024 * 5];  
        int len;  
        while ((len =inBuff.read(b)) != -1) {  
            outBuff.write(b, 0, len);  
        }  
        // 刷新此缓冲的输出流   
        outBuff.flush();  
          
        //关闭流   
        inBuff.close();  
        outBuff.close();  
        output.close();  
        input.close();  
	}
	
	/**
	 * 判断文件是否存在，如果不存在就创建该文件,存在就直接返回
	 * @param file
	 * @throws IOException
	 */
	public static File createFile(File file) throws IOException{
		if(!file.exists()){
			String filePath = file.getPath();
			try {
				filePath = filePath.substring(0, filePath.lastIndexOf("//"));
			} catch (StringIndexOutOfBoundsException e) {
				try {
					filePath = filePath.substring(0, filePath.lastIndexOf("/"));
				} catch (StringIndexOutOfBoundsException e1) {
					filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
				}
			}
			File filePathDir = new File(filePath);
			if(!filePathDir.exists()){
				filePathDir.mkdirs();
			}
			file.createNewFile();
		}
		return file;
	}
	
	/**
	 * 判断文件是否存在，如果不存在就创建该文件
	 * @param file
	 * @throws IOException
	 */
	public static File fileIsExist(File file) throws IOException{
		if(!file.exists()){
			String filePath = file.getPath();
			try {
				filePath = filePath.substring(0, filePath.lastIndexOf("//"));
			} catch (StringIndexOutOfBoundsException e) {
				try {
					filePath = filePath.substring(0, filePath.lastIndexOf("/"));
				} catch (StringIndexOutOfBoundsException e1) {
					filePath = filePath.substring(0, filePath.lastIndexOf("\\"));
				}
			}
			File filePathDir = new File(filePath);
			if(!filePathDir.exists()){
				filePathDir.mkdirs();
			}
			file.createNewFile();
		}
		return file;
	}
	
	public static void main(String[] args) {
        getCreateTime();   
        getModifiedTime_1();   
        getModifiedTime_2();         
	}
	
	/**
	 * 保存对象到文件(单个,覆盖式)
	 * @param objFile
	 * @param o
	 */
	public static void saveObj(File objFile,Object o){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(objFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(o); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从文件读取对象(单个)
	 * @param objFile
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObj(File objFile,Class<T> c) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		T object = null;
		try {
			fis = new FileInputStream(objFile);
			ois = new ObjectInputStream(fis);
			object = (T) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
		
	}

	/**
	 * 向文件增量增加字符串
	 * @param suggest
	 * @param file
	 * @throws IOException
	 */
	public static void insertStr2File(String suggest, File file) throws IOException {
		FileWriter fileWritter = new FileWriter(file.getAbsolutePath(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(suggest);
		System.out.println(suggest);
		bufferWritter.flush();
		bufferWritter.close();
	}
  
    /**  
     * 读取文件创建时间  
     */  
    public static void getCreateTime(){   
        String filePath = "C://test.txt";   
        String strTime = null;   
        try {   
            Process p = Runtime.getRuntime().exec("cmd /C dir "            
                    + filePath   
                    + "/tc" );   
            InputStream is = p.getInputStream();    
            BufferedReader br = new BufferedReader(new InputStreamReader(is));              
            String line;   
            while((line = br.readLine()) != null){   
                if(line.endsWith(".txt")){   
                    strTime = line.substring(0,17);   
                    break;   
                }                              
             }    
        } catch (IOException e) {   
            e.printStackTrace();   
        }          
        System.out.println("创建时间    " + strTime);      
        //输出：创建时间   2009-08-17  10:21   
    }   
    /**  
     * 读取文件修改时间的方法1  
     */    
    @SuppressWarnings("deprecation")   
    public static void getModifiedTime_1(){   
        File f = new File("C://test.txt");               
        Calendar cal = Calendar.getInstance();   
        long time = f.lastModified();   
        cal.setTimeInMillis(time);     
        //此处toLocalString()方法是不推荐的，但是仍可输出   
        System.out.println("修改时间[1] " + cal.getTime().toLocaleString());    
        //输出：修改时间[1]    2009-8-17 10:32:38   
    }   
       
    /**  
     * 读取修改时间的方法2  
     */  
    public static void getModifiedTime_2(){   
        File f = new File("C://test.txt");               
        Calendar cal = Calendar.getInstance();   
        long time = f.lastModified();   
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");          
        cal.setTimeInMillis(time);     
        System.out.println("修改时间[2] " + formatter.format(cal.getTime()));      
        //输出：修改时间[2]    2009-08-17 10:32:38   
    }

	public static void insertList2File(List<String> list, File file) throws IOException {
		FileWriter fileWritter = new FileWriter(file.getAbsolutePath(),false);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		for (String s : list) {
			bufferWritter.write(s+"\n");
		}
		bufferWritter.flush();
		bufferWritter.close();
	}   
}
