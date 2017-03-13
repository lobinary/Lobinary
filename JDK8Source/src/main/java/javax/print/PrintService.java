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

import java.util.Locale;

import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.event.PrintServiceAttributeListener;


/**
 * Interface PrintService is the factory for a DocPrintJob. A PrintService
 * describes the capabilities of a Printer and can be queried regarding
 * a printer's supported attributes.
 * <P>
 * Example:
 *   <PRE>{@code
 *   DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
 *   PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
 *   aset.add(MediaSizeName.ISO_A4);
 *   PrintService[] pservices =
 *                 PrintServiceLookup.lookupPrintServices(flavor, aset);
 *   if (pservices.length > 0) {
 *       DocPrintJob pj = pservices[0].createPrintJob();
 *       try {
 *           FileInputStream fis = new FileInputStream("test.ps");
 *           Doc doc = new SimpleDoc(fis, flavor, null);
 *           pj.print(doc, aset);
 *        } catch (FileNotFoundException fe) {
 *        } catch (PrintException e) {
 *        }
 *   }
 *   }</PRE>
 * <p>
 *  Interface PrintService是DocPrintJob的工厂。PrintService描述了打印机的功能,可以查询打印机支持的属性
 * <P>
 * 示例：<PRE> {@ code DocFlavor flavor = DocFlavorINPUT_STREAMPOSTSCRIPT; PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); asetadd(MediaSizeNameISO_A4); PrintService [] pservices = PrintServiceLookuplookupPrintServices(flavor,aset); if(pserviceslength> 0){DocPrintJob pj = pservices [0] createPrintJob(); try {FileInputStream fis = new FileInputStream("testps"); Doc doc = new SimpleDoc(fis,flavor,null); pjprint(doc,aset); }
 *  catch(FileNotFoundException fe){} catch(PrintException e){}}} </PRE>。
 * 
 */
public interface PrintService {

    /** Returns a String name for this print service which may be used
      * by applications to request a particular print service.
      * In a suitable context, such as a name service, this name must be
      * unique.
      * In some environments this unique name may be the same as the user
      * friendly printer name defined as the
      * {@link javax.print.attribute.standard.PrinterName PrinterName}
      * attribute.
      * <p>
      * 通过应用程序请求特定打印服务在合适的上下文(例如名称服务)中,此名称必须是唯一的在某些环境中,此唯一名称可能与定义为{@link javaxprintattributestandardPrinterName PrinterName}
      * 属性的用户友好打印机名称相同。
      * 
      * 
      * @return name of the service.
      */
    public String getName();

    /**
     * Creates and returns a PrintJob capable of handling data from
     * any of the supported document flavors.
     * <p>
     *  创建并返回能够处理来自任何受支持的文档风格的数据的PrintJob
     * 
     * 
     * @return a DocPrintJob object
     */
    public DocPrintJob createPrintJob();

    /**
     * Registers a listener for events on this PrintService.
     * <p>
     *  为此PrintService上的事件注册侦听器
     * 
     * 
     * @param listener  a PrintServiceAttributeListener, which
     *        monitors the status of a print service
     * @see #removePrintServiceAttributeListener
     */
    public void addPrintServiceAttributeListener(
                                       PrintServiceAttributeListener listener);

    /**
     * Removes the print-service listener from this print service.
     * This means the listener is no longer interested in
     * <code>PrintService</code> events.
     * <p>
     *  从此打印服务中删除打印服务侦听器这意味着侦听器不再对<code> PrintService </code>事件感兴趣
     * 
     * 
     * @param listener  a PrintServiceAttributeListener object
     * @see #addPrintServiceAttributeListener
     */
    public void removePrintServiceAttributeListener(
                                       PrintServiceAttributeListener listener);

    /**
     * Obtains this print service's set of printer description attributes
     * giving this Print Service's status. The returned attribute set object
     * is unmodifiable. The returned attribute set object is a "snapshot" of
     * this Print Service's attribute set at the time of the
     * <CODE>getAttributes()</CODE> method call: that is, the returned
     * attribute set's contents will <I>not</I> be updated if this print
     * service's attribute set's contents change in the future. To detect
     * changes in attribute values, call <CODE>getAttributes()</CODE> again
     * and compare the new attribute set to the previous attribute set;
     * alternatively, register a listener for print service events.
     *
     * <p>
     * 获取此打印服务的一组打印机描述属性,以提供此打印服务的状态返回的属性集对象不可修改返回的属性集对象是在<CODE> getAttributes()</span>时设置的此打印服务属性的"快照" CODE
     * >方法调用：也就是说,如果此打印服务的属性集的内容在将来更改,则返回的属性集的内容将不更新</I>要检测属性值的更改,请调用<CODE> getAttributes / CODE>,并将新的属性集与先前
     * 的属性集进行比较;或者,注册打印服务事件的侦听器。
     * 
     * 
     * @return  Unmodifiable snapshot of this Print Service's attribute set.
     *          May be empty, but not null.
     */
    public PrintServiceAttributeSet getAttributes();

    /**
     * Gets the value of the single specified service attribute.
     * This may be useful to clients which only need the value of one
     * attribute and want to minimize overhead.
     * <p>
     * 获取单个指定的服务属性的值这可能对仅需要一个属性的值并希望最小化开销的客户端有用
     * 
     * 
     * @param category the category of a PrintServiceAttribute supported
     * by this service - may not be null.
     * @return the value of the supported attribute or null if the
     * attribute is not supported by this service.
     * @exception NullPointerException if the category is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) if <CODE>category</CODE> is not a
     *     <code>Class</code> that implements interface
     *{@link javax.print.attribute.PrintServiceAttribute PrintServiceAttribute}.
     */
    public <T extends PrintServiceAttribute>
        T getAttribute(Class<T> category);

    /**
     * Determines the print data formats a client can specify when setting
     * up a job for this <code>PrintService</code>. A print data format is
     * designated by a "doc
     * flavor" (class {@link javax.print.DocFlavor DocFlavor})
     * consisting of a MIME type plus a print data representation class.
     * <P>
     * Note that some doc flavors may not be supported in combination
     * with all attributes. Use <code>getUnsupportedAttributes(..)</code>
     * to validate specific combinations.
     *
     * <p>
     *  确定客户端为此<code> PrintService </code>设置作业时可以指定的打印数据格式打印数据格式由"doc flavor"(类为{@link javaxprintDocFlavor DocFlavor}
     * )指定,由MIME类型加上打印数据表示类。
     * <P>
     *  注意,一些doc风格可能不支持与所有属性的组合使用<code> getUnsupportedAttributes()</code>以验证特定的组合
     * 
     * 
     * @return  Array of supported doc flavors, should have at least
     *          one element.
     *
     */
    public DocFlavor[] getSupportedDocFlavors();

    /**
     * Determines if this print service supports a specific
     * <code>DocFlavor</code>.  This is a convenience method to determine
     * if the <code>DocFlavor</code> would be a member of the result of
     * <code>getSupportedDocFlavors()</code>.
     * <p>
     * Note that some doc flavors may not be supported in combination
     * with all attributes. Use <code>getUnsupportedAttributes(..)</code>
     * to validate specific combinations.
     *
     * <p>
     * 确定此打印服务是否支持特定的<code> DocFlavor </code>这是一个方便的方法来确定<code> DocFlavor </code>是否是<code> getSupportedDocFl
     * avors >。
     * <p>
     *  注意,一些doc风格可能不支持与所有属性的组合使用<code> getUnsupportedAttributes()</code>以验证特定的组合
     * 
     * 
     * @param flavor the <code>DocFlavor</code>to query for support.
     * @return  <code>true</code> if this print service supports the
     * specified <code>DocFlavor</code>; <code>false</code> otherwise.
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>flavor</CODE> is null.
     */
    public boolean isDocFlavorSupported(DocFlavor flavor);


    /**
     * Determines the printing attribute categories a client can specify
     * when setting up a job for this print service.
     * A printing attribute category is
     * designated by a <code>Class</code> that implements interface
     * {@link javax.print.attribute.Attribute Attribute}. This method returns
     * just the attribute <I>categories</I> that are supported; it does not
     * return the particular attribute <I>values</I> that are supported.
     * <P>
     * This method returns all the printing attribute
     * categories this print service supports for any possible job.
     * Some categories may not be supported in a particular context (ie
     * for a particular <code>DocFlavor</code>).
     * Use one of the methods that include a <code>DocFlavor</code> to
     * validate the request before submitting it, such as
     * <code>getSupportedAttributeValues(..)</code>.
     *
     * <p>
     * 确定客户端为此打印服务设置作业时可以指定的打印属性类别打印属性类别由实现接口的<code> Class </code>指定{@link javaxprintattributeAttribute Attribute}
     * 此方法仅返回属性< I>类别</I>;它不返回支持的特定属性<I>值</I>。
     * <P>
     *  此方法返回此打印服务为任何可能的工作支持的所有打印属性类别某些类别在特定上下文中可能不受支持(即对于特定的<code> DocFlavor </code>)使用包含<code > DocFlavor 
     * </code>在提交之前验证请求,例如<code> getSupportedAttributeValues()</code>。
     * 
     * 
     * @return  Array of printing attribute categories that the client can
     *          specify as a doc-level or job-level attribute in a Print
     *          Request. Each element in the array is a {@link java.lang.Class
     *          Class} that implements interface {@link
     *          javax.print.attribute.Attribute Attribute}.
     *          The array is empty if no categories are supported.
     */
    public Class<?>[] getSupportedAttributeCategories();

    /**
     * Determines whether a client can specify the given printing
     * attribute category when setting up a job for this print service. A
     * printing attribute category is designated by a <code>Class</code>
     * that implements interface {@link javax.print.attribute.Attribute
     * Attribute}. This method tells whether the attribute <I>category</I> is
     * supported; it does not tell whether a particular attribute <I>value</I>
     * is supported.
     * <p>
     * Some categories may not be supported in a particular context (ie
     * for a particular <code>DocFlavor</code>).
     * Use one of the methods which include a <code>DocFlavor</code> to
     * validate the request before submitting it, such as
     * <code>getSupportedAttributeValues(..)</code>.
     * <P>
     * This is a convenience method to determine if the category
     * would be a member of the result of
     * <code>getSupportedAttributeCategories()</code>.
     *
     * <p>
     * 确定在为此打印服务设置作业时客户端是否可以指定给定打印属性类别打印属性类别由实现接口的<code> Class </code>指定{@link javaxprintattributeAttribute Attribute}
     * 此方法告诉属性<I>类别</I>;它不告诉是否支持特定属性<I>值</I>。
     * <p>
     *  某些类别在特定上下文中可能不受支持(例如对于特定的<code> DocFlavor </code>)使用包含<code> DocFlavor </code>的方法之一在提交之前验证请求,例如<code>
     *  getSupportedAttributeValues()</code>。
     * <P>
     * 这是一个方便的方法来确定类别是否是<code>结果的成员getSupportedAttributeCategories()</code>
     * 
     * 
     * @param  category    Printing attribute category to test. It must be a
     *                        <code>Class</code> that implements
     *                        interface
     *                {@link javax.print.attribute.Attribute Attribute}.
     *
     * @return  <code>true</code> if this print service supports
     *          specifying a doc-level or
     *          job-level attribute in <CODE>category</CODE> in a Print
     *          Request; <code>false</code> if it doesn't.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>category</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>category</CODE> is not a
     *     <code>Class</code> that implements interface
     *     {@link javax.print.attribute.Attribute Attribute}.
     */
    public boolean
        isAttributeCategorySupported(Class<? extends Attribute> category);

    /**
     * Determines this print service's default printing attribute value in
     * the given category. A printing attribute value is an instance of
     * a class that implements interface
     * {@link javax.print.attribute.Attribute Attribute}. If a client sets
     * up a print job and does not specify any attribute value in the
     * given category, this Print Service will use the
     * default attribute value instead.
     * <p>
     * Some attributes may not be supported in a particular context (ie
     * for a particular <code>DocFlavor</code>).
     * Use one of the methods that include a <code>DocFlavor</code> to
     * validate the request before submitting it, such as
     * <code>getSupportedAttributeValues(..)</code>.
     * <P>
     * Not all attributes have a default value. For example the
     * service will not have a defaultvalue for <code>RequestingUser</code>
     * i.e. a null return for a supported category means there is no
     * service default value for that category. Use the
     * <code>isAttributeCategorySupported(Class)</code> method to
     * distinguish these cases.
     *
     * <p>
     *  确定此打印服务在给定类别中的默认打印属性值打印属性值是实现接口{@link javaxprintattributeAttribute Attribute}的类的实例如果客户端设置打印作业并且未在给定类
     * 别中指定任何属性值,此打印服务将使用默认属性值。
     * <p>
     *  某些属性可能在特定上下文中不受支持(即对于特定的<code> DocFlavor </code>)使用包含<code> DocFlavor </code>的方法之一在提交之前验证请求,例如<code>
     *  getSupportedAttributeValues()</code>。
     * <P>
     * 并非所有属性都有默认值例如,服务不会有<code> RequestingUser </code>的默认值,即支持类别的空返回意味着该类别没有服务默认值使用<code> isAttributeCatego
     * rySupported类)</code>方法来区分这些情况。
     * 
     * 
     * @param  category    Printing attribute category for which the default
     *                     attribute value is requested. It must be a {@link
     *                        java.lang.Class Class} that implements interface
     *                        {@link javax.print.attribute.Attribute
     *                        Attribute}.
     *
     * @return  Default attribute value for <CODE>category</CODE>, or null
     *       if this Print Service does not support specifying a doc-level or
     *          job-level attribute in <CODE>category</CODE> in a Print
     *          Request, or the service does not have a default value
     *          for this attribute.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>category</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>category</CODE> is not a
     *     {@link java.lang.Class Class} that implements interface {@link
     *     javax.print.attribute.Attribute Attribute}.
     */
    public Object
        getDefaultAttributeValue(Class<? extends Attribute> category);

    /**
     * Determines the printing attribute values a client can specify in
     * the given category when setting up a job for this print service. A
     * printing
     * attribute value is an instance of a class that implements interface
     * {@link javax.print.attribute.Attribute Attribute}.
     * <P>
     * If <CODE>flavor</CODE> is null and <CODE>attributes</CODE> is null
     * or is an empty set, this method returns all the printing attribute
     * values this Print Service supports for any possible job. If
     * <CODE>flavor</CODE> is not null or <CODE>attributes</CODE> is not
     * an empty set, this method returns just the printing attribute values
     * that are compatible with the given doc flavor and/or set of attributes.
     * That is, a null return value may indicate that specifying this attribute
     * is incompatible with the specified DocFlavor.
     * Also if DocFlavor is not null it must be a flavor supported by this
     * PrintService, else IllegalArgumentException will be thrown.
     * <P>
     * If the <code>attributes</code> parameter contains an Attribute whose
     * category is the same as the <code>category</code> parameter, the service
     * must ignore this attribute in the AttributeSet.
     * <p>
     * <code>DocAttribute</code>s which are to be specified on the
     * <code>Doc</code> must be included in this set to accurately
     * represent the context.
     * <p>
     * This method returns an Object because different printing attribute
     * categories indicate the supported attribute values in different ways.
     * The documentation for each printing attribute in package {@link
     * javax.print.attribute.standard javax.print.attribute.standard}
     * describes how each attribute indicates its supported values. Possible
     * ways of indicating support include:
     * <UL>
     * <LI>
     * Return a single instance of the attribute category to indicate that any
     * value is legal -- used, for example, by an attribute whose value is an
     * arbitrary text string. (The value of the returned attribute object is
     * irrelevant.)
     * <LI>
     * Return an array of one or more instances of the attribute category,
     * containing the legal values -- used, for example, by an attribute with
     * a list of enumerated values. The type of the array is an array of the
     * specified attribute category type as returned by its
     * <code>getCategory(Class)</code>.
     * <LI>
     * Return a single object (of some class other than the attribute category)
     * that indicates bounds on the legal values -- used, for example, by an
     * integer-valued attribute that must lie within a certain range.
     * </UL>
     * <P>
     *
     * <p>
     *  确定在为此打印服务设置作业时客户端可以在给定类别中指定的打印属性值打印属性值是实现接口{@link javaxprintattributeAttribute Attribute}的类的实例,
     * <P>
     * 如果<CODE> flavor </CODE>为null且<CODE>属性</CODE>为null或为空集,此方法将返回此Print Service对任何可能的作业支持的所有打印属性值如果<CODE> 
     * / CODE>不为空或<CODE>属性不是空集,此方法仅返回与给定doc风格和/或属性集兼容的打印属性值。
     * 即,返回null值可能指示指定此属性与指定的DocFlavor不兼容如果DocFlavor不为null,它必须是此PrintService支持的flavor,否则将抛出IllegalArgumentEx
     * ception异常。
     * <P>
     * 如果<code> attributes </code>参数包含类别与<code> category </code>参数相同的属性,则服务必须在AttributeSet中忽略此属性
     * <p>
     *  要在<code> Doc </code>上指定的<code> DocAttribute </code>必须包含在此集合中以准确表示上下文
     * <p>
     *  此方法返回一个对象,因为不同的打印属性类别以不同的方式指示支持的属性值包{@link javaxprintattributestandard javaxprintattributestandard}中
     * 每个打印属性的文档描述了每个属性如何指示其支持的值可能的指示支持的方法包括：。
     * <UL>
     * <LI>
     * 返回属性类别的单个实例,以指示任何值是合法的 - 例如,由值为任意文本字符串的属性使用(返回的属性对象的值不相关)
     * <LI>
     *  返回属性类别的一个或多个实例的数组,包含合法值 - 例如,由具有枚举值列表的属性使用数组的类型是由返回的指定属性类别类型的数组它的<code> getCategory(Class)</code>
     * <LI>
     *  返回指示法律值上的边界的单个对象(属于类别之外的某个类的对象) - 例如,使用必须位于某个范围内的整数值属性
     * </UL>
     * <P>
     * 
     * 
     * @param  category    Printing attribute category to test. It must be a
     *                        {@link java.lang.Class Class} that implements
     *                        interface {@link
     *                        javax.print.attribute.Attribute Attribute}.
     * @param  flavor      Doc flavor for a supposed job, or null.
     * @param  attributes  Set of printing attributes for a supposed job
     *                        (both job-level attributes and document-level
     *                        attributes), or null.
     *
     * @return  Object indicating supported values for <CODE>category</CODE>,
     *          or null if this Print Service does not support specifying a
     *          doc-level or job-level attribute in <CODE>category</CODE> in
     *          a Print Request.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>category</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>category</CODE> is not a
     *     {@link java.lang.Class Class} that implements interface {@link
     *     javax.print.attribute.Attribute Attribute}, or
     *     <code>DocFlavor</code> is not supported by this service.
     */
    public Object
        getSupportedAttributeValues(Class<? extends Attribute> category,
                                    DocFlavor flavor,
                                    AttributeSet attributes);

    /**
     * Determines whether a client can specify the given printing
     * attribute
     * value when setting up a job for this Print Service. A printing
     * attribute value is an instance of a class that implements interface
     *  {@link javax.print.attribute.Attribute Attribute}.
     * <P>
     * If <CODE>flavor</CODE> is null and <CODE>attributes</CODE> is null or
     * is an empty set, this method tells whether this Print Service supports
     * the given printing attribute value for some possible combination of doc
     * flavor and set of attributes. If <CODE>flavor</CODE> is not null or
     * <CODE>attributes</CODE> is not an empty set, this method tells whether
     * this Print Service supports the given printing attribute value in
     * combination with the given doc flavor and/or set of attributes.
     * <p>
     * Also if DocFlavor is not null it must be a flavor supported by this
     * PrintService, else IllegalArgumentException will be thrown.
     * <p>
     * <code>DocAttribute</code>s which are to be specified on the
     * <code>Doc</code> must be included in this set to accurately
     * represent the context.
     * <p>
     * This is a convenience method to determine if the value
     * would be a member of the result of
     * <code>getSupportedAttributeValues(...)</code>.
     *
     * <p>
     * 确定在为此打印服务设置作业时客户端是否可以指定给定的打印属性值打印属性值是实现接口{@link javaxprintattributeAttribute Attribute}的类的实例,
     * <P>
     *  如果<CODE> flavor </CODE>为空,并且<CODE>属性</CODE>为null或者是一个空集合,此方法将告诉此Print Service是否支持给定的打印属性值属性如果<CODE> 
     * flavor </CODE>不为空或<CODE>属性</CODE>不是空集,此方法将告诉此Print Service是否支持给定的打印属性值结合给定的doc风格, /或属性集。
     * <p>
     * 此外,如果DocFlavor不为null,它必须是此PrintService支持的flavor,否则将抛出IllegalArgumentException异常
     * <p>
     *  要在<code> Doc </code>上指定的<code> DocAttribute </code>必须包含在此集合中以准确表示上下文
     * <p>
     *  这是一个方便的方法来确定该值是否是<code> getSupportedAttributeValues()</code>结果的成员。
     * 
     * 
     * @param  attrval       Printing attribute value to test.
     * @param  flavor      Doc flavor for a supposed job, or null.
     * @param  attributes  Set of printing attributes for a supposed job
     *                        (both job-level attributes and document-level
     *                        attributes), or null.
     *
     * @return  True if this Print Service supports specifying
     *        <CODE>attrval</CODE> as a doc-level or job-level attribute in a
     *          Print Request, false if it doesn't.
     *
     * @exception  NullPointerException
     *     (unchecked exception)  if <CODE>attrval</CODE> is null.
     * @exception  IllegalArgumentException if flavor is not supported by
     *      this PrintService.
     */
    public boolean isAttributeValueSupported(Attribute attrval,
                                             DocFlavor flavor,
                                             AttributeSet attributes);


    /**
     * Identifies the attributes that are unsupported for a print request
     * in the context of a particular DocFlavor.
     * This method is useful for validating a potential print job and
     * identifying the specific attributes which cannot be supported.
     * It is important to supply only a supported DocFlavor or an
     * IllegalArgumentException will be thrown. If the
     * return value from this method is null, all attributes are supported.
     * <p>
     * <code>DocAttribute</code>s which are to be specified on the
     * <code>Doc</code> must be included in this set to accurately
     * represent the context.
     * <p>
     * If the return value is non-null, all attributes in the returned
     * set are unsupported with this DocFlavor. The returned set does not
     * distinguish attribute categories that are unsupported from
     * unsupported attribute values.
     * <p>
     * A supported print request can then be created by removing
     * all unsupported attributes from the original attribute set,
     * except in the case that the DocFlavor is unsupported.
     * <p>
     * If any attributes are unsupported only because they are in conflict
     * with other attributes then it is at the discretion of the service
     * to select the attribute(s) to be identified as the cause of the
     * conflict.
     * <p>
     * Use <code>isDocFlavorSupported()</code> to verify that a DocFlavor
     * is supported before calling this method.
     *
     * <p>
     * 标识在特定DocFlavor的上下文中不支持打印请求的属性此方法对于验证潜在的打印作业和识别不能支持的特定属性非常有用只提供支持的DocFlavor或IllegalArgumentException非常
     * 重要thrown如果此方法的返回值为null,则支持所有属性。
     * <p>
     *  要在<code> Doc </code>上指定的<code> DocAttribute </code>必须包含在此集合中以准确表示上下文
     * <p>
     *  如果返回值不为null,则返回集中的所有属性都不受此DocFlavor支持返回的集不区分不受支持的属性类别与不支持的属性值
     * <p>
     * 然后可以通过从原始属性集中除去所有不受支持的属性来创建受支持的打印请求,但在不支持DocFlavor的情况下
     * <p>
     *  如果任何属性仅因为它们与其他属性冲突而不受支持,则服务自行决定选择要标识为冲突原因的属性
     * <p>
     *  在调用此方法之前,请使用<code> isDocFlavorSupported()</code>验证是否支持Do​​cFlavor
     * 
     * 
     * @param  flavor      Doc flavor to test, or null
     * @param  attributes  Set of printing attributes for a supposed job
     *                        (both job-level attributes and document-level
     *                        attributes), or null.
     *
     * @return  null if this Print Service supports the print request
     * specification, else the unsupported attributes.
     *
     * @exception IllegalArgumentException if<CODE>flavor</CODE> is
     *             not supported by this PrintService.
     */
    public AttributeSet getUnsupportedAttributes(DocFlavor flavor,
                                           AttributeSet attributes);

    /**
     * Returns a factory for UI components which allow users to interact
     * with the service in various roles.
     * Services which do not provide any UI should return null.
     * Print Services which do provide UI but want to be supported in
     * an environment with no UI support should ensure that the factory
     * is not initialised unless the application calls this method to
     * obtain the factory.
     * See <code>ServiceUIFactory</code> for more information.
     * <p>
     * 返回UI组件的工厂,允许用户与各种角色中的服务进行交互不提供任何UI的服务应返回空打印服务,它提供UI但希望在没有UI支持的环境中支持,应确保工厂除非应用程序调用此方法来获取工厂,否则不会初始化有关详细
     * 信息,请参阅<code> ServiceUIFactory </code>。
     * 
     * 
     * @return null or a factory for UI components.
     */
    public ServiceUIFactory getServiceUIFactory();

    /**
     * Determines if two services are referring to the same underlying
     * service.  Objects encapsulating a print service may not exhibit
     * equality of reference even though they refer to the same underlying
     * service.
     * <p>
     * Clients should call this method to determine if two services are
     * referring to the same underlying service.
     * <p>
     * Services must implement this method and return true only if the
     * service objects being compared may be used interchangeably by the
     * client.
     * Services are free to return the same object reference to an underlying
     * service if that, but clients must not depend on equality of reference.
     * <p>
     *  确定两个服务是否指向相同的底层服务封装打印服务的对象可能不会显示相等的引用,即使它们引用相同的底层服务
     * <p>
     *  客户端应调用此方法以确定两个服务是否指向相同的底层服务
     * <p>
     * 服务必须实现此方法,并且只有当被比较的服务对象可以被客户端互换使用时,服务才返回true服务可以自由地返回相同的对象引用到底层服务,但是客户端不能依赖于引用的相等性
     * 
     * @param obj the reference object with which to compare.
     * @return true if this service is the same as the obj argument,
     * false otherwise.
     */
    public boolean equals(Object obj);

    /**
     * This method should be implemented consistently with
     * <code>equals(Object)</code>.
     * <p>
     * 
     * 
     * @return hash code of this object.
     */
    public int hashCode();

}
