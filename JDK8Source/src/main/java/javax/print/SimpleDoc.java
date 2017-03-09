/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.StringReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import javax.print.attribute.AttributeSetUtilities;
import javax.print.attribute.DocAttributeSet;

/**
 * This class is an implementation of interface <code>Doc</code> that can
 * be used in many common printing requests.
 * It can handle all of the presently defined "pre-defined" doc flavors
 * defined as static variables in the DocFlavor class.
 * <p>
 * In particular this class implements certain required semantics of the
 * Doc specification as follows:
 * <ul>
 * <li>constructs a stream for the service if requested and appropriate.
 * <li>ensures the same object is returned for each call on a method.
 * <li>ensures multiple threads can access the Doc
 * <li>performs some validation of that the data matches the doc flavor.
 * </ul>
 * Clients who want to re-use the doc object in other jobs,
 * or need a MultiDoc will not want to use this class.
 * <p>
 * If the print data is a stream, or a print job requests data as a
 * stream, then <code>SimpleDoc</code> does not monitor if the service
 * properly closes the stream after data transfer completion or job
 * termination.
 * Clients may prefer to use provide their own implementation of doc that
 * adds a listener to monitor job completion and to validate that
 * resources such as streams are freed (ie closed).
 * <p>
 *  这个类是接口<code> Doc </code>的实现,可以在许多常见的打印请求中使用。它可以处理定义为DocFlavor类中的静态变量的所有当前定义的"预定义"doc风格。
 * <p>
 *  特别地,该类实现Doc规范的某些必需的语义如下：
 * <ul>
 *  <li>如果请求和适当,为服务构建流。 <li>确保为方法上的每个调用返回相同的对象。 <li>确保多个会话可以访问Doc <li>执行一些数据与文档风格匹配的验证。
 * </ul>
 *  想要在其他作业中重用doc对象的客户端,或需要一个MultiDoc将不想使用此类。
 * <p>
 *  如果打印数据是流,或打印作业将数据作为流请求,则<code> SimpleDoc </code>不监视数据传输完成或作业终止后服务是否正确关闭流。
 * 客户端可能更喜欢使用提供自己的doc实现,它添加了一个监听器来监视作业完成,并验证诸如流之类的资源是否被释放(即关闭)。
 * 
 */

public final class SimpleDoc implements Doc {

    private DocFlavor flavor;
    private DocAttributeSet attributes;
    private Object printData;
    private Reader reader;
    private InputStream inStream;

    /**
     * Constructs a <code>SimpleDoc</code> with the specified
     * print data, doc flavor and doc attribute set.
     * <p>
     *  使用指定的打印数据,doc flavor和doc属性设置构造一个<code> SimpleDoc </code>。
     * 
     * 
     * @param printData the print data object
     * @param flavor the <code>DocFlavor</code> object
     * @param attributes a <code>DocAttributeSet</code>, which can
     *                   be <code>null</code>
     * @throws IllegalArgumentException if <code>flavor</code> or
     *         <code>printData</code> is <code>null</code>, or the
     *         <code>printData</code> does not correspond
     *         to the specified doc flavor--for example, the data is
     *         not of the type specified as the representation in the
     *         <code>DocFlavor</code>.
     */
    public SimpleDoc(Object printData,
                     DocFlavor flavor, DocAttributeSet attributes) {

       if (flavor == null || printData == null) {
           throw new IllegalArgumentException("null argument(s)");
       }

       Class repClass = null;
       try {
            String className = flavor.getRepresentationClassName();
            sun.reflect.misc.ReflectUtil.checkPackageAccess(className);
            repClass = Class.forName(className, false,
                              Thread.currentThread().getContextClassLoader());
       } catch (Throwable e) {
           throw new IllegalArgumentException("unknown representation class");
       }

       if (!repClass.isInstance(printData)) {
           throw new IllegalArgumentException("data is not of declared type");
       }

       this.flavor = flavor;
       if (attributes != null) {
           this.attributes = AttributeSetUtilities.unmodifiableView(attributes);
       }
       this.printData = printData;
    }

   /**
     * Determines the doc flavor in which this doc object will supply its
     * piece of print data.
     *
     * <p>
     *  确定此doc对象将提供其打印数据片段的文档风格。
     * 
     * 
     * @return  Doc flavor.
     */
    public DocFlavor getDocFlavor() {
        return flavor;
    }

    /**
     * Obtains the set of printing attributes for this doc object. If the
     * returned attribute set includes an instance of a particular attribute
     * <I>X,</I> the printer must use that attribute value for this doc,
     * overriding any value of attribute <I>X</I> in the job's attribute set.
     * If the returned attribute set does not include an instance
     * of a particular attribute <I>X</I> or if null is returned, the printer
     * must consult the job's attribute set to obtain the value for
     * attribute <I>X,</I> and if not found there, the printer must use an
     * implementation-dependent default value. The returned attribute set is
     * unmodifiable.
     *
     * <p>
     * 获取此doc对象的打印属性集。如果返回的属性集包括特定属性<I> X的实例,则打印机必须使用该文档的该属性值,覆盖作业属性集中属性<I> X </I>的任何值。
     * 如果返回的属性集不包括特定属性<I> X </I>的实例,或者如果返回了null,则打印机必须查阅作业的属性集以获得属性<I> X的值,</I >如果在那里没有找到,打印机必须使用一个实现相关的默认值。
     * 获取此doc对象的打印属性集。如果返回的属性集包括特定属性<I> X的实例,则打印机必须使用该文档的该属性值,覆盖作业属性集中属性<I> X </I>的任何值。返回的属性集是不可修改的。
     * 
     * 
     * @return  Unmodifiable set of printing attributes for this doc, or null
     *          to obtain all attribute values from the job's attribute
     *          set.
     */
    public DocAttributeSet getAttributes() {
        return attributes;
    }

    /*
     * Obtains the print data representation object that contains this doc
     * object's piece of print data in the format corresponding to the
     * supported doc flavor.
     * The <CODE>getPrintData()</CODE> method returns an instance of
     * the representation class whose name is given by
     * {@link DocFlavor#getRepresentationClassName() getRepresentationClassName},
     * and the return value can be cast
     * from class Object to that representation class.
     *
     * <p>
     *  以支持的doc风格对应的格式获取包含此doc对象的打印数据的打印数据表示对象。
     *  <CODE> getPrintData()</CODE>方法返回一个表示类的实例,其名称由{@link DocFlavor#getRepresentationClassName()getRepresentationClassName}
     * 给出,并且返回值可以从类Object转换为该表示类。
     *  以支持的doc风格对应的格式获取包含此doc对象的打印数据的打印数据表示对象。
     * 
     * 
     * @return  Print data representation object.
     *
     * @exception  IOException if the representation class is a stream and
     *     there was an I/O error while constructing the stream.
     */
    public Object getPrintData() throws IOException {
        return printData;
    }

    /**
     * Obtains a reader for extracting character print data from this doc.
     * The <code>Doc</code> implementation is required to support this
     * method if the <code>DocFlavor</code> has one of the following print
     * data representation classes, and return <code>null</code>
     * otherwise:
     * <UL>
     * <LI> <code>char[]</code>
     * <LI> <code>java.lang.String</code>
     * <LI> <code>java.io.Reader</code>
     * </UL>
     * The doc's print data representation object is used to construct and
     * return a <code>Reader</code> for reading the print data as a stream
     * of characters from the print data representation object.
     * However, if the print data representation object is itself a
     * <code>Reader</code> then the print data representation object is
     * simply returned.
     * <P>
     * <p>
     *  获取从此文档中提取字符打印数据的阅读器。
     * 如果<code> DocFlavor </code>具有以下打印数据表示类之一,则需要<code> Doc </code>实现来支持此方法,否则返回<code> null </code>。
     * <UL>
     *  <LI> <code> char [] </code> <li> <code> java.lang.String </code> <li> <code> java.io.Reader </code>。
     * </UL>
     * 文档的打印数据表示对象用于构造并返回用于从打印数据表示对象读取打印数据作为字符流的<code> Reader </code>。
     * 然而,如果打印数据表示对象本身是<code> Reader </code>,则简单地返回打印数据表示对象。
     * <P>
     * 
     * @return  a <code>Reader</code> for reading the print data
     *          characters from this doc.
     *          If a reader cannot be provided because this doc does not meet
     *          the criteria stated above, <code>null</code> is returned.
     *
     * @exception  IOException if there was an I/O error while creating
     *             the reader.
     */
    public Reader getReaderForText() throws IOException {

        if (printData instanceof Reader) {
            return (Reader)printData;
        }

        synchronized (this) {
            if (reader != null) {
                return reader;
            }

            if (printData instanceof char[]) {
               reader = new CharArrayReader((char[])printData);
            }
            else if (printData instanceof String) {
                reader = new StringReader((String)printData);
            }
        }
        return reader;
    }

    /**
     * Obtains an input stream for extracting byte print data from
     * this doc.
     * The <code>Doc</code> implementation is required to support this
     * method if the <code>DocFlavor</code> has one of the following print
     * data representation classes; otherwise this method
     * returns <code>null</code>:
     * <UL>
     * <LI> <code>byte[]</code>
     * <LI> <code>java.io.InputStream</code>
     * </UL>
     * The doc's print data representation object is obtained.  Then, an
     * input stream for reading the print data
     * from the print data representation object as a stream of bytes is
     * created and returned.
     * However, if the print data representation object is itself an
     * input stream then the print data representation object is simply
     * returned.
     * <P>
     * <p>
     * 
     * @return  an <code>InputStream</code> for reading the print data
     *          bytes from this doc.  If an input stream cannot be
     *          provided because this doc does not meet
     *          the criteria stated above, <code>null</code> is returned.
     *
     * @exception  IOException
     *     if there was an I/O error while creating the input stream.
     */
    public InputStream getStreamForBytes() throws IOException {

        if (printData instanceof InputStream) {
            return (InputStream)printData;
        }

        synchronized (this) {
            if (inStream != null) {
                return inStream;
            }

            if (printData instanceof byte[]) {
               inStream = new ByteArrayInputStream((byte[])printData);
            }
        }
        return inStream;
    }

}
