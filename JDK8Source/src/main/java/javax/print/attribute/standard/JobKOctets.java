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
import javax.print.attribute.IntegerSyntax;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobKOctets is an integer valued printing attribute class that specifies
 * the total size of the document(s) in K octets, i.e., in units of 1024 octets
 * requested to be processed in the job. The value must be rounded up, so that a
 * job between 1 and 1024 octets must be indicated as being 1K octets, 1025 to
 * 2048 must be 2K octets, etc. For a multidoc print job (a job with multiple
 * documents), the JobKOctets value is computed by adding up the individual
 * documents' sizes in octets, then rounding up to the next K octets value.
 * <P>
 * The JobKOctets attribute describes the size of the job. This attribute is not
 * intended to be a counter; it is intended to be useful routing and scheduling
 * information if known. The printer may try to compute the JobKOctets
 * attribute's value if it is not supplied in the Print Request. Even if the
 * client does supply a value for the JobKOctets attribute in the Print Request,
 * the printer may choose to change the value if the printer is able to compute
 * a value which is more accurate than the client supplied value. The printer
 * may be able to determine the correct value for the JobKOctets attribute
 * either right at job submission time or at any later point in time.
 * <P>
 * The JobKOctets value must not include the multiplicative factors contributed
 * by the number of copies specified by the {@link Copies Copies} attribute,
 * independent of whether the device can process multiple copies without making
 * multiple passes over the job or document data and independent of whether the
 * output is collated or not. Thus the value is independent of the
 * implementation and indicates the size of the document(s) measured in K octets
 * independent of the number of copies.
 * <P>
 * The JobKOctets value must also not include the multiplicative factor due to a
 * copies instruction embedded in the document data. If the document data
 * actually includes replications of the document data, this value will include
 * such replication. In other words, this value is always the size of the source
 * document data, rather than a measure of the hardcopy output to be produced.
 * <P>
 * The size of a doc is computed based on the print data representation class as
 * specified by the doc's {@link javax.print.DocFlavor DocFlavor}, as
 * shown in the table below.
 * <P>
 * <TABLE BORDER=1 CELLPADDING=2 CELLSPACING=1 SUMMARY="Table showing computation of doc sizes">
 * <TR>
 * <TH>Representation Class</TH>
 * <TH>Document Size</TH>
 * </TR>
 * <TR>
 * <TD>byte[]</TD>
 * <TD>Length of the byte array</TD>
 * </TR>
 * <TR>
 * <TD>java.io.InputStream</TD>
 * <TD>Number of bytes read from the stream</TD>
 * </TR>
 * <TR>
 * <TD>char[]</TD>
 * <TD>Length of the character array x 2</TD>
 * </TR>
 * <TR>
 * <TD>java.lang.String</TD>
 * <TD>Length of the string x 2</TD>
 * </TR>
 * <TR>
 * <TD>java.io.Reader</TD>
 * <TD>Number of characters read from the stream x 2</TD>
 * </TR>
 * <TR>
 * <TD>java.net.URL</TD>
 * <TD>Number of bytes read from the file at the given URL address</TD>
 * </TR>
 * <TR>
 * <TD>java.awt.image.renderable.RenderableImage</TD>
 * <TD>Implementation dependent&#42;</TD>
 * </TR>
 * <TR>
 * <TD>java.awt.print.Printable</TD>
 * <TD>Implementation dependent&#42;</TD>
 * </TR>
 * <TR>
 * <TD>java.awt.print.Pageable</TD>
 * <TD>Implementation dependent&#42;</TD>
 * </TR>
 * </TABLE>
 * <P>
 * &#42; In these cases the Print Service itself generates the print data sent
 * to the printer. If the Print Service supports the JobKOctets attribute, for
 * these cases the Print Service itself must calculate the size of the print
 * data, replacing any JobKOctets value the client specified.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  JobKOctets类是一个整数值打印属性类,它指定以K个字节为单位的文档的总大小,即以作业中请求处理的1024个八位位组为单位。
 * 该值必须向上取整,以便1至1024个八位字节之间的作业必须指示为1K个八位字节,1025至2048个字节必须为2K个八位字节等。
 * 对于多标题打印作业(具有多个文档的作业),JobKOctets值是通过以八位字节为单位累加单个文档的大小来计算的,然后向上取整到下一个K字节值。
 * <P>
 *  JobKOctets属性描述作业的大小。此属性不是计数器;如果已知,则意图是有用的路由和调度信息。如果打印请求中没有提供JobKOctets属性的值,打印机可能会尝试计算。
 * 即使客户端为打印请求中的JobKOctets属性提供了值,如果打印机能够计算比客户端提供的值更精确的值,打印机可以选择更改值。
 * 打印机可以能够在作业提交时或在任何稍后的时间点确定JobKOctets属性的正确值。
 * <P>
 * JobKOctets值不能包括由{@link Copies Copies}属性指定的副本数所贡献的乘法因子,而与设备是否可以处理多个副本而不对作业或文档数据进行多次传递无关,而与是否输出是否整理。
 * 因此,该值与实现无关,并且指示以K个字节测量的文档的大小,而与复制的数量无关。
 * <P>
 *  由于嵌入在文档数据中的副本指令,JobKOctets值还必须不包括乘法因子。如果文档数据实际上包括文档数据的复制,则该值将包括这样的复制。
 * 换句话说,该值始终是源文档数据的大小,而不是要生成的硬拷贝输出的度量。
 * <P>
 *  文档的大小是根据文档的{@link javax.print.DocFlavor DocFlavor}指定的打印数据表示类计算的,如下表所示。
 * <P>
 * <TABLE BORDER=1 CELLPADDING=2 CELLSPACING=1 SUMMARY="Table showing computation of doc sizes">
 * <TR>
 *  <TH>表示类</TH> <TH>文档大小</TH>
 * </TR>
 * <TR>
 *  <TD> byte [] </TD> <TD>字节数组的长度</TD>
 * </TR>
 * <TR>
 *  <TD> java.io.InputStream </TD> <TD>从流读取的字节数</TD>
 * </TR>
 * <TR>
 *  <TD> char [] </TD> <TD>字符数组x 2的长度</TD>
 * </TR>
 * <TR>
 *  <TD> java.lang.String </TD> <TD>字符串的长度x 2 </TD>
 * </TR>
 * <TR>
 *  <TD> java.io.Reader </TD> <TD>从流读取的字符数x 2 </TD>
 * </TR>
 * <TR>
 * <TD> java.net.URL </TD> <TD>从给定URL地址处的文件中读取的字节数</TD>
 * </TR>
 * <TR>
 * 
 * @see JobKOctetsSupported
 * @see JobKOctetsProcessed
 * @see JobImpressions
 * @see JobMediaSheets
 *
 * @author  Alan Kaminsky
 */
public final class JobKOctets   extends IntegerSyntax
        implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -8959710146498202869L;

    /**
     * Construct a new job K octets attribute with the given integer value.
     *
     * <p>
     *  <TD> java.awt.image.renderable.RenderableImage </TD> <TD>实现依赖* </TD>
     * </TR>
     * <TR>
     *  <TD> java.awt.print.Printable </TD> <TD>实现相关* </TD>
     * </TR>
     * <TR>
     *  <TD> java.awt.print.Pageable </TD> <TD>实现相关* </TD>
     * </TR>
     * </TABLE>
     * <P>
     *  *在这些情况下,打印服务本身会生成发送到打印机的打印数据。如果打印服务支持JobKOctets属性,对于这些情况,打印服务本身必须计算打印数据的大小,替换客户端指定的任何JobKOctets值。
     * <P>
     *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
     * <P>
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *  (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public JobKOctets(int value) {
        super (value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this job K octets attribute is equivalent to the passed
     * in object. To be equivalent, all of the following conditions must be
     * true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobKOctets.
     * <LI>
     * This job K octets attribute's value and <CODE>object</CODE>'s value
     * are equal.
     * </OL>
     *
     * <p>
     *  使用给定的整数值构造新的作业K个八位位组属性。
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job K
     *          octets attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return super.equals(object) && object instanceof JobKOctets;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobKOctets, the category is class JobKOctets itself.
     *
     * <p>
     *  返回此作业的K个八位位组属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobKOctets类的实例。
     * <LI>
     *  此作业K个八位字节属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobKOctets.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobKOctets, the category name is <CODE>"job-k-octets"</CODE>.
     *
     * <p>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-k-octets";
    }

}
