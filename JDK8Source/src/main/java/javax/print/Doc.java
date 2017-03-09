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

package javax.print;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

import javax.print.attribute.DocAttributeSet;


/**
 * Interface Doc specifies the interface for an object that supplies one piece
 * of print data for a Print Job. "Doc" is a short, easy-to-pronounce term
 * that means "a piece of print data." The client passes to the Print Job an
 * object that implements interface Doc, and the Print Job calls methods on
 * that object to obtain the print data. The Doc interface lets a Print Job:
 * <UL>
 * <LI>
 * Determine the format, or "doc flavor" (class {@link DocFlavor DocFlavor}),
 * in which the print data is available. A doc flavor designates the print
 * data format (a MIME type) and the representation class of the object
 * from which the print data comes.
 * <P>
 * <LI>
 * Obtain the print data representation object, which is an instance of the
 * doc flavor's representation class. The Print Job can then obtain the actual
 * print data from the representation object.
 * <P>
 * <LI>
 * Obtain the printing attributes that specify additional characteristics of
 * the doc or that specify processing instructions to be applied to the doc.
 * Printing attributes are defined in package {@link javax.print.attribute
 * javax.print.attribute}. The doc returns its printing attributes stored in
 * an {@link javax.print.attribute.DocAttributeSet javax.print.attribute.DocAttributeSet}.
 * </UL>
 * <P>
 * Each method in an implementation of interface Doc is permitted always to
 * return the same object each time the method is called.
 * This has implications
 * for a Print Job or other caller of a doc object whose print data
 * representation object "consumes" the print data as the caller obtains the
 * print data, such as a print data representation object which is a stream.
 * Once the Print Job has called {@link #getPrintData()
 * getPrintData()} and obtained the stream, any further calls to
 * {@link #getPrintData() getPrintData()} will return the same
 * stream object upon which reading may already be in progress, <I>not</I> a new
 * stream object that will re-read the print data from the beginning. Specifying
 * a doc object to behave this way simplifies the implementation of doc objects,
 * and is justified on the grounds that a particular doc is intended to convey
 * print data only to one Print Job, not to several different Print Jobs. (To
 * convey the same print data to several different Print Jobs, you have to
 * create several different doc objects on top of the same print data source.)
 * <P>
 * Interface Doc affords considerable implementation flexibility. The print data
 * might already be in existence when the doc object is constructed. In this
 * case the objects returned by the doc's methods can be supplied to the doc's
 * constructor, be stored in the doc ahead of time, and simply be returned when
 * called for. Alternatively, the print data might not exist yet when the doc
 * object is constructed. In this case the doc object might provide a "lazy"
 * implementation that generates the print data representation object (and/or
 * the print data) only when the Print Job calls for it (when the Print Job
 * calls the {@link #getPrintData() getPrintData()} method).
 * <P>
 * There is no restriction on the number of client threads that may be
 * simultaneously accessing the same doc. Therefore, all implementations of
 * interface Doc must be designed to be multiple thread safe.
 * <p>
 * However there can only be one consumer of the print data obtained from a
 * Doc.
 * <p>
 * If print data is obtained from the client as a stream, by calling Doc's
 * <code>getReaderForText()</code> or <code>getStreamForBytes()</code>
 * methods, or because the print data source is already an InputStream or
 * Reader, then the print service should always close these streams for the
 * client on all job completion conditions. With the following caveat.
 * If the print data is itself a stream, the service will always close it.
 * If the print data is otherwise something that can be requested as a stream,
 * the service will only close the stream if it has obtained the stream before
 * terminating. That is, just because a print service might request data as
 * a stream does not mean that it will, with the implications that Doc
 * implementors which rely on the service to close them should create such
 * streams only in response to a request from the service.
 * <P>
 * <HR>
 * <p>
 *  接口文档指定为打印作业提供一个打印数据的对象的接口。 "Doc"是一个简短易读的术语,意思是"一条打印数据"。客户端将实现接口Doc的对象传递给打印作业,打印作业调用该对象上的方法以获取打印数据。
 *  Doc接口允许打印作业：。
 * <UL>
 * <LI>
 *  确定打印数据可用的格式或"doc flavor"({@ link DocFlavor DocFlavor}类)。 doc flavor指定打印数据格式(MIME类型)和打印数据来自的对象的表示类。
 * <P>
 * <LI>
 *  获取打印数据表示对象,它是doc flavor的表示类的一个实例。打印作业然后可以从表示对象获得实际打印数据。
 * <P>
 * <LI>
 *  获取指定文档的其他特性或指定要应用于文档的处理指令的打印属性。打印属性在包{@link javax.print.attribute javax.print.attribute}中定义。
 * 文档返回其存储在{@link javax.print.attribute.DocAttributeSet javax.print.attribute.DocAttributeSet}中的打印属性。
 * </UL>
 * <P>
 * 在接口Doc的实现中的每个方法允许总是在每次调用该方法时返回相同的对象。
 * 这对打印作业或打印数据表示对象在消费者获得打印数据时打印数据表示对象"消费"打印数据的其他调用者有影响,例如作为流的打印数据表示对象。
 * 一旦打印作业调用{@link #getPrintData()getPrintData()}并获得流,对{@link #getPrintData()getPrintData()}的任何进一步调用都将返回读
 * 取时可能已经存在的相同流对象进度,<I>不是</I>一个新的流对象,将从头重新读取打印数据。
 * 这对打印作业或打印数据表示对象在消费者获得打印数据时打印数据表示对象"消费"打印数据的其他调用者有影响,例如作为流的打印数据表示对象。
 * 指定文档对象以这种方式来简化doc对象的实现,并且被证明是基于特定的文档旨在仅将打印数据传达给一个打印作业,而不是几个不同的打印作业。
 *  (要将相同的打印数据传送到多个不同的打印作业,必须在同一打印数据源的顶部创建多个不同的doc对象。)。
 * <P>
 * 接口Doc提供了相当大的实现灵活性。构建doc对象时,打印数据可能已经存在。在这种情况下,由doc的方法返回的对象可以提供给doc的构造函数,提前存储在doc中,并且只需在调用时返回。
 * 或者,在构建doc对象时,打印数据可能不存在。
 * 在这种情况下,doc对象可以提供"惰性"实现,其仅在打印作业调用它时(当打印作业调用{@link #getPrintData())时生成打印数据表示对象(和/ getPrintData()}方法)。
 * <P>
 *  对可以同时访问同一个文档的客户端线程数没有限制。因此,接口Doc的所有实现必须设计为多线程安全。
 * <p>
 *  然而,从文档获得的打印数据只能有一个消费者。
 * <p>
 * 如果通过调用Doc的<code> getReaderForText()</code>或<code> getStreamForBytes()</code>方法,或者因为打印数据源已经是InputStrea
 * m或Reader ,则打印服务应该在所有作业完成条件下始终关闭客户端的这些流。
 * 有以下警告。如果打印数据本身是流,则服务将始终关闭它。如果打印数据是可以作为流请求的东西,则该服务将仅在流终止之前已经获得流时才关闭该流。
 */
public interface Doc {

    /**
     * Determines the doc flavor in which this doc object will supply its
     * piece of print data.
     *
     * <p>
     * 也就是说,只是因为打印服务可能请求数据作为流并不意味着它将意味着,依赖于服务的Doc实现者关闭它们应当仅响应于来自服务的请求而创建这样的流。
     * <P>
     * <HR>
     * 
     * @return  Doc flavor.
     */
    public DocFlavor getDocFlavor();

    /**
     * Obtains the print data representation object that contains this doc
     * object's piece of print data in the format corresponding to the
     * supported doc flavor.
     * The <CODE>getPrintData()</CODE> method returns an instance of
     * the representation class whose name is given by <CODE>{@link
     * #getDocFlavor() getDocFlavor()}.{@link
     * DocFlavor#getRepresentationClassName()
     * getRepresentationClassName()}</CODE>, and the return value can be cast
     * from class Object to that representation class.
     *
     * <p>
     *  确定此doc对象将提供其打印数据片段的文档风格。
     * 
     * 
     * @return  Print data representation object.
     *
     * @exception  IOException
     *     Thrown if the representation class is a stream and there was an I/O
     *     error while constructing the stream.
     */
    public Object getPrintData() throws IOException;

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
     *  以支持的doc风格对应的格式获取包含此doc对象的打印数据的打印数据表示对象。
     *  <CODE> getPrintData()</CODE>方法返回一个名称由<CODE> {@ link #getDocFlavor()getDocFlavor()}给出的表示类的实例。
     * {@ link DocFlavor#getRepresentationClassName()getRepresentationClassName } </CODE>,并且返回值可以从类Object转换为
     * 该表示类。
     *  <CODE> getPrintData()</CODE>方法返回一个名称由<CODE> {@ link #getDocFlavor()getDocFlavor()}给出的表示类的实例。
     * 
     * 
     * @return  Unmodifiable set of printing attributes for this doc, or null
     *          to obtain all attribute values from the job's attribute
     *          set.
     */
    public DocAttributeSet getAttributes();

    /**
     * Obtains a reader for extracting character print data from this doc.
     * The Doc implementation is required to support this method if the
     * DocFlavor has one of the following print data representation classes,
     * and return null otherwise:
     * <UL>
     * <LI> char[]
     * <LI> java.lang.String
     * <LI> java.io.Reader
     * </UL>
     * The doc's print data representation object is used to construct and
     * return a Reader for reading the print data as a stream of characters
     * from the print data representation object.
     * However, if the print data representation object is itself a Reader,
     * then the print data representation object is simply returned.
     * <P>
     * <p>
     * 获取此doc对象的打印属性集。如果返回的属性集包括特定属性<I> X的实例,则打印机必须使用该文档的该属性值,覆盖作业属性集中属性<I> X </I>的任何值。
     * 如果返回的属性集不包括特定属性<I> X </I>的实例,或者如果返回了null,则打印机必须查阅作业的属性集以获得属性<I> X的值,</I >如果在那里没有找到,打印机必须使用一个实现相关的默认值。
     * 获取此doc对象的打印属性集。如果返回的属性集包括特定属性<I> X的实例,则打印机必须使用该文档的该属性值,覆盖作业属性集中属性<I> X </I>的任何值。返回的属性集是不可修改的。
     * 
     * 
     * @return  Reader for reading the print data characters from this doc.
     *          If a reader cannot be provided because this doc does not meet
     *          the criteria stated above, null is returned.
     *
     * @exception  IOException
     *     Thrown if there was an I/O error while creating the reader.
     */
    public Reader getReaderForText() throws IOException;

    /**
     * Obtains an input stream for extracting byte print data from this
     * doc.  The Doc implementation is required to support this method if
     * the DocFlavor has one of the following print data representation
     * classes, and return null otherwise:
     * <UL>
     * <LI> byte[]
     * <LI> java.io.InputStream
     * </UL>
     * This doc's print data representation object is obtained, then an input
     * stream for reading the print data from the print data representation
     * object as a stream of bytes is created and returned. However, if the
     * print data representation object is itself an input stream, then the
     * print data representation object is simply returned.
     * <P>
     * <p>
     *  获取从此文档中提取字符打印数据的阅读器。如果DocFlavor具有以下打印数据表示类之一,则Doc实现需要支持此方法,否则返回null：
     * <UL>
     *  <LI> char [] <LI> java.lang.String <LI> java.io.Reader
     * </UL>
     *  文档的打印数据表示对象用于构造和返回读取器,用于从打印数据表示对象读取打印数据作为字符流。然而,如果打印数据表示对象本身是读取器,则简单地返回打印数据表示对象。
     * <P>
     * 
     * @return  Input stream for reading the print data bytes from this doc. If
     *          an input stream cannot be provided because this doc does not
     *          meet the criteria stated above, null is returned.
     *
     * @exception  IOException
     *     Thrown if there was an I/O error while creating the input stream.
     */
    public InputStream getStreamForBytes() throws IOException;

}
