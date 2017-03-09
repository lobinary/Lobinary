/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package javax.print.attribute.standard;

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.Attribute;

/**
 * Class ReferenceUriSchemesSupported is a printing attribute class
 * an enumeration, that indicates a "URI scheme," such as "http:" or "ftp:",
 * that a printer can use to retrieve print data stored at a URI location.
 * If a printer supports doc flavors with a print data representation class of
 * <CODE>"java.net.URL"</CODE>, the printer uses instances of class
 * ReferenceUriSchemesSupported to advertise the URI schemes it can accept.
 * The acceptable URI schemes are included as service attributes in the
 * lookup service; this lets clients search the
 * for printers that can get print data using a certain URI scheme. The
 * acceptable URI schemes can also be queried using the capability methods in
 * interface <code>PrintService</code>. However,
 * ReferenceUriSchemesSupported attributes are used solely for determining
 * acceptable URI schemes, they are never included in a doc's,
 * print request's, print job's, or print service's attribute set.
 * <P>
 * The Internet Assigned Numbers Authority maintains the
 * <A HREF="http://www.iana.org/assignments/uri-schemes.html">official
 * list of URI schemes</A>.
 * <p>
 * Class ReferenceUriSchemesSupported defines enumeration values for widely
 * used URI schemes. A printer that supports additional URI schemes
 * can define them in a subclass of class ReferenceUriSchemesSupported.
 * <P>
 * <B>IPP Compatibility:</B>  The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类ReferenceUriSchemesSupported是打印属性类枚举,其指示打印机可以用于检索存储在URI位置处的打印数据的"URI方案",例如"http："或"ftp："。
 * 如果打印机支持具有打印数据表示类<CODE>"java.net.URL"</CODE>的doc风格,则打印机使用ReferenceUriSchemesSupported类的实例来通告其可以接受的URI方
 * 案。
 *  类ReferenceUriSchemesSupported是打印属性类枚举,其指示打印机可以用于检索存储在URI位置处的打印数据的"URI方案",例如"http："或"ftp："。
 * 可接受的URI方案作为服务属性包括在查找服务中;这允许客户端搜索可以使用某个URI方案获取打印数据的打印机。
 * 可接受的URI方案也可以使用接口<code> PrintService </code>中的能力方法查询。
 * 但是,ReferenceUriSchemesSupported属性仅用于确定可接受的URI方案,它们从不包含在文档,打印请求,打印作业或打印服务的属性集中。
 * <P>
 *  互联网号码分配机构维护<A HREF="http://www.iana.org/assignments/uri-schemes.html"> URI计划的官方列表</A>。
 * <p>
 *  类ReferenceUriSchemesSupported定义广泛使用的URI方案的枚举值。
 * 支持附加URI方案的打印机可以在ReferenceUriSchemesSupported类的子类中定义它们。
 * <P>
 * <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class ReferenceUriSchemesSupported
    extends EnumSyntax implements Attribute {

    private static final long serialVersionUID = -8989076942813442805L;

    /**
     * File Transfer Protocol (FTP).
     * <p>
     *  文件传输协议(FTP)。
     * 
     */
    public static final ReferenceUriSchemesSupported FTP =
        new ReferenceUriSchemesSupported(0);

    /**
     * HyperText Transfer Protocol (HTTP).
     * <p>
     *  超文本传输​​协议(HTTP)。
     * 
     */
    public static final ReferenceUriSchemesSupported HTTP = new ReferenceUriSchemesSupported(1);

    /**
     * Secure HyperText Transfer Protocol (HTTPS).
     * <p>
     *  安全超文本传输​​协议(HTTPS)。
     * 
     */
    public static final ReferenceUriSchemesSupported HTTPS = new ReferenceUriSchemesSupported(2);

    /**
     * Gopher Protocol.
     * <p>
     *  Gopher协议。
     * 
     */
    public static final ReferenceUriSchemesSupported GOPHER = new ReferenceUriSchemesSupported(3);

    /**
     * USENET news.
     * <p>
     *  USENET新闻。
     * 
     */
    public static final ReferenceUriSchemesSupported NEWS = new ReferenceUriSchemesSupported(4);

    /**
     * USENET news using Network News Transfer Protocol (NNTP).
     * <p>
     *  USENET新闻使用网络新闻传输协议(NNTP)。
     * 
     */
    public static final ReferenceUriSchemesSupported NNTP = new ReferenceUriSchemesSupported(5);

    /**
     * Wide Area Information Server (WAIS) protocol.
     * <p>
     *  广域信息服务器(WAIS)协议。
     * 
     */
    public static final ReferenceUriSchemesSupported WAIS = new ReferenceUriSchemesSupported(6);

    /**
     * Host-specific file names.
     * <p>
     *  主机特定的文件名。
     * 
     */
    public static final ReferenceUriSchemesSupported FILE = new ReferenceUriSchemesSupported(7);

    /**
     * Construct a new reference URI scheme enumeration value with the given
     * integer value.
     *
     * <p>
     *  使用给定的整数值构造新的引用URI方案枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected ReferenceUriSchemesSupported(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "ftp",
        "http",
        "https",
        "gopher",
        "news",
        "nntp",
        "wais",
        "file",
    };

    private static final ReferenceUriSchemesSupported[] myEnumValueTable = {
        FTP,
        HTTP,
        HTTPS,
        GOPHER,
        NEWS,
        NNTP,
        WAIS,
        FILE,
    };

    /**
     * Returns the string table for class ReferenceUriSchemesSupported.
     * <p>
     *  返回类ReferenceUriSchemesSupported的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class
     * ReferenceUriSchemesSupported.
     * <p>
     *  返回类ReferenceUriSchemesSupported的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class ReferenceUriSchemesSupported and any vendor-defined
     * subclasses, the category is class ReferenceUriSchemesSupported itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于ReferenceUriSchemesSupported类和任何供应商定义的子类,类别是ReferenceUriSchemesSupported类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return ReferenceUriSchemesSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class ReferenceUriSchemesSupported and any vendor-defined
     * subclasses, the category name is
     * <CODE>"reference-uri-schemes-supported"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "reference-uri-schemes-supported";
    }

}
