package com.lobinary.工具类.file.xml;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.xpath.DefaultXPath;

public class Dom4jNsTest {
    public static void main(String[] args) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(Dom4jNsTest.class.getResourceAsStream("x.xml"));
        DefaultXPath xpath = new DefaultXPath("//m:arg0");
        xpath.setNamespaceURIs(Collections.singletonMap("m", "http://service.unicom.com"));
        List list = xpath.selectNodes(document);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Element node = (Element) iterator.next();
            System.out.println("node is " + node.getName());
        }
    }
}