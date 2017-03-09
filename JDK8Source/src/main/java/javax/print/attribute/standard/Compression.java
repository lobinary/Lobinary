/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.print.attribute.Attribute;
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.DocAttribute;

/**
 * Class Compression is a printing attribute class, an enumeration, that
 * specifies how print data is compressed. Compression is an attribute of the
 * print data (the doc), not of the Print Job. If a Compression attribute is not
 * specified for a doc, the printer assumes the doc's print data is uncompressed
 * (i.e., the default Compression value is always {@link #NONE
 * NONE}).
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类压缩是打印属性类,枚举,指定如何压缩打印数据。压缩是打印数据(文档)的属性,而不是打印作业的属性。
 * 如果未为文档指定Compression属性,则打印机假定文档的打印数据未压缩(即,默认压缩值始终为{@link #NONE NONE})。
 * <P>
 *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class Compression extends EnumSyntax implements DocAttribute {

    private static final long serialVersionUID = -5716748913324997674L;

    /**
     * No compression is used.
     * <p>
     *  不使用压缩。
     * 
     */
    public static final Compression NONE = new Compression(0);

    /**
     * ZIP public domain inflate/deflate compression technology.
     * <p>
     *  ZIP公共领域充气/放气压缩技术。
     * 
     */
    public static final Compression DEFLATE = new Compression(1);

    /**
     * GNU zip compression technology described in
     * <A HREF="http://www.ietf.org/rfc/rfc1952.txt">RFC 1952</A>.
     * <p>
     *  GNU zip压缩技术在<A HREF="http://www.ietf.org/rfc/rfc1952.txt"> RFC 1952 </A>中描述。
     * 
     */
    public static final Compression GZIP = new Compression(2);

    /**
     * UNIX compression technology.
     * <p>
     *  UNIX压缩技术。
     * 
     */
    public static final Compression COMPRESS = new Compression(3);

    /**
     * Construct a new compression enumeration value with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的压缩枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected Compression(int value) {
        super(value);
    }


    private static final String[] myStringTable = {"none",
                                                   "deflate",
                                                   "gzip",
                                                   "compress"};

    private static final Compression[] myEnumValueTable = {NONE,
                                                           DEFLATE,
                                                           GZIP,
                                                           COMPRESS};

    /**
     * Returns the string table for class Compression.
     * <p>
     *  返回类Compression的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class Compression.
     * <p>
     *  返回类Compression的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Compression and any vendor-defined subclasses, the category is
     * class Compression itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类Compression和任何供应商定义的子类,类别是类Compression本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Compression.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Compression and any vendor-defined subclasses, the category
     * name is <CODE>"compression"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "compression";
    }

}
