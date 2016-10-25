package com.lobinary.java.jdk.jdk16.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * 
 * <pre>
 * 
 * 	http://www.ibm.com/developerworks/cn/java/j-lo-jse67/index.html
 * </pre>
 *
 * @ClassName: Xml应用
 * @author 919515134@qq.com
 * @date 2016年10月10日 下午2:54:02
 * @version V1.0.0
 */
public class Xml应用 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		 SAXParserFactory factory = SAXParserFactory.newInstance(); 
		 System.out.println(factory.getClass()); 

		 // Parse the input 
		 SAXParser saxParser = factory.newSAXParser(); 
		 System.out.println(saxParser.getClass()); 
		 try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
