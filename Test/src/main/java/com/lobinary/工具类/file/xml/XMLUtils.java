package com.lobinary.工具类.file.xml;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XMLUtils {

    public static void main(String[] args) throws MalformedURLException, DocumentException {
        File folder = new File("c:/test/xml");
        List<File> fileFromFolder = getFileFromFolder(folder);
        for (File f : fileFromFolder) {
            System.out.println(f.getAbsolutePath());
            String fileName = f.getName();
            String fileType = fileName.substring(fileName.length() - 3);
            if ("xml".equals(fileType)) {// 如果是xml文件
                Document xmlDoc = read(f);
                Element rootElement = xmlDoc.getRootElement();
                List<Element> elements = rootElement.elements();
                for (Element subElement : elements) {
                    String subNodeName = subElement.getName();
                    System.out.println(subNodeName);
                    if ("Body".equals(subNodeName)) {
                        int nodeCount = subElement.nodeCount();
                        System.out.println(nodeCount);
                        if(nodeCount>1){
                            System.out.println(fileName+"的子节点数量大于1,当前为："+nodeCount);
                        }
                    }
                }
            } else {
                System.out.println("不是xml文件：" + fileType);
            }
        }
    }

    public static Document read(File xmlFile) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xmlFile);
        return document;
    }

    public static List<File> getFileFromFolder(File folder) {
        List<File> fileList = new ArrayList<File>();
        if (folder.listFiles() == null || folder.listFiles().length == 0) {
            return fileList;
        }
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                fileList.addAll(getFileFromFolder(f));
            } else {
                fileList.add(f);
            }
        }
        return fileList;
    }

}
