package com.lobinary.java.jdk.jdk16;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.lobinary.java.jdk.JAXB2Bean;

public class JDK16特性 {

	public static void main(String[] args) {
		DeskTop和SystemTray();
		JAXB();
	}
	
	/**
	 * 2.使用JAXB2来实现对象与XML之间的映射 
		JAXB是Java Architecture for XML Binding的缩写，可以将一个Java对象转变成为XML格式，反之亦然。 
		我 们把对象与关系数据库之间的映射称为ORM, 其实也可以把对象与XML之间的映射称为OXM(Object XML Mapping). 
		原来JAXB是Java EE的一部分，在JDK6中，SUN将其放到了Java SE中，这也是SUN的一贯做法。
		JDK6中自带的这个JAXB版本是2.0, 比起1.0(JSR 31)来，JAXB2(JSR 222)用JDK5的新特性Annotation来标识要作绑定的类和属性等，这就极大简化了开发的工作量。 
		实 际上，在Java EE 5.0中，EJB和Web Services也通过Annotation来简化开发工作。
		另外,JAXB2在底层是用StAX(JSR 173)来处理XML文档。除了JAXB之外，我们还可以通过XMLBeans和Castor等来实现同样的功能。 
			http://suo.iteye.com/blog/1233458
	 */
	private static void JAXB() {
		System.out.println();
		System.out.println("============================使用JAXB2来实现对象与XML之间的映射 ===============================");
		JAXB2Bean customer = new JAXB2Bean();  
        customer.setId(100);  
        customer.setName("suo");  
        customer.setAge(29);  
          
        try {  
            File file = new File("C:/temp/test/JAXB2Bean.xml");  
            JAXBContext jaxbContext = JAXBContext.newInstance(JAXB2Bean.class);  
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
            // output pretty printed  
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            jaxbMarshaller.marshal(customer, file);  
            jaxbMarshaller.marshal(customer, System.out);  
        } catch (JAXBException e) {
            e.printStackTrace();  
        }
		System.out.println("============================使用JAXB2来实现对象与XML之间的映射 ===============================");
	}
	
	/**
	 * 1.Desktop类和SystemTray类 
		在JDK6中 ,AWT新增加了两个类:Desktop和SystemTray。 
		前者可以用来打开系统默认浏览器浏览指定的URL,打开系统默认邮件客户端给指定的邮箱发邮件,
		用默认应用程序打开或编辑文件(比如,用记事本打开以txt为后缀名的文件),用系统默认的打印机打印文档;
		后者可以用来在系统托盘区创建一个托盘程序. 
	 */
	private static void DeskTop和SystemTray() {
		DeskTop使用.main(null);
		SystemTray使用.main(null);
	}
	
}