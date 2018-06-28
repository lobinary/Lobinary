package com.lobinary.工具类;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lobinary.工具类.file.FileUtil;

public class ExcelUtil {
    
    public static void main(String[] args) throws IOException {
        生成excel格式化后的文档内容();
//        String s1 = "123";
//        String s2 = "1234567890";
//        s1 = s1 + getTab(s1.length(),10)+"|";
//        s2 = s2 + getTab(s2.length(),10)+"|";
//        System.out.println(s1);
//        System.out.println(s2);
    }

    /**
     * 中文哟啊才|啊。啊
     * abc   啊|abc啊
     * 
     * @since add by bin.lv 2017年12月15日 下午5:52:32
     * @throws IOException
     */
    private static void 生成excel格式化后的文档内容() throws IOException {
        List<String> l = FileUtil.readLine2List(new File("C:/test/a.txt"));
        int 列数 = l.get(0).split("\t",-1).length;
        int[] 每列最大长度 = new int[列数];
        List<String[]> lt = new ArrayList<String[]>();
        for(String s:l){
            String[] sa = s.split("\t",-1);
            lt.add(sa);
        }
        for(String[] sa:lt){
            for (int i = 0; i < 列数; i++) {
                if(getChineseLength(sa[i])%2==1){
                    sa[i] = sa[i] + "。";//让中文变偶数
                }
                if(lastIsChinese(sa[i])){
                    sa[i] = sa[i] + ".";//让英文结尾
                }
                int lm = getLength(sa[i]);
                if(lm>每列最大长度[i]){
                    每列最大长度[i] = lm;
                }
            }
        }
        for(int i : 每列最大长度){
            System.out.println(i);
        }
        for(String[] sa:lt){
            for (int i = 0; i < 列数; i++) {
                String 该列内容 = sa[i];
                if(该列内容.length()==0){
                    该列内容 = "-";
                }
                int 该列长度 = getLength(该列内容);
                int 该列最大长度 = 每列最大长度[i];
                System.out.print(该列内容 + getTab(该列长度,该列最大长度));
            }
            System.out.println();
        }
    }

    /**
     * 我是啊
     *    啊
     * 
     * @since add by bin.lv 2017年12月15日 下午6:19:56
     * @param 该列长度
     * @param 该列最大长度
     * @return
     */
    private static String getTab234(int 该列长度, int 该列最大长度) {
        int a = 该列最大长度-该列长度;
        int b = a/8;
//        System.out.print(a+":"+b);
        String r = "";
        for (int i = 0; i < b+1; i++) {
            r += "\t";
        }
        return r;
    }

    /*
     * 
     */
    private static String getTab(int 该列长度, int 该列最大长度) {
        //52    30
        int b = 该列最大长度-该列长度;
        String r ="";
        for (int i = 0; i < b+4; i++) {
            r += " ";
        }
        return r;
    }

    /**
     * 长度   列最终需求长度
     * 4    24
     * 8    24
     * 12   24
     * 16   24
     * @since add by bin.lv 2017年12月15日 下午5:01:20
     * @param 该列长度
     * @param 该列最大长度
     * @return
     * 
     */
    private static String getTab23(int 该列长度, int 该列最大长度) {
        int a = 该列最大长度/8;
        a++;
        a = a*8;//该列需求长度24
        a = a - 该列长度;//需要增加的长度16
        int b = a/8;
        if(a%8!=0)b++;
//        System.out.print(a+":"+b);
        String r = "";
        for (int i = 0; i < b; i++) {
            r += "\t";
        }
        return r;
    }

    private static int getLength(String str){
       int chineseLength = getChineseLength(str);
       int charLength = str.length() - chineseLength;
    int i = charLength+chineseLength/2*3;
//       System.out.print(i);
        return i;      
    }
    
    /**
     * 获取汉字个数
     * 
     * @since add by bin.lv 2017年12月15日 下午6:04:08
     * @param str
     * @return
     */
    private static int getChineseLength(String str){
        str = str.replace("。", "啊");
        str = str.replace("，", "啊");
        int count = 0;      
        String regEx = "[\\u4e00-\\u9fa5]";      
        Pattern p = Pattern.compile(regEx);      
        Matcher m = p.matcher(str);      
       while (m.find()) {      
           for (int i = 0; i <= m.groupCount(); i++) {      
                count = count + 1;      
            }      
        }      
        return count;      
    }
    
    /**
     * 是否中文结尾
     * 
     * @since add by bin.lv 2017年12月15日 下午6:04:14
     * @param str
     * @return
     */
    private static boolean lastIsChinese(String str){
        if(str.length()==0)return false;
        String lastStr = str.substring(str.length()-1);
        if(lastStr.equals("。")){
            return true;
        }
        return getChineseLength(lastStr)==1;
    }

}
