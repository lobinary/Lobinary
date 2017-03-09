/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind;

import org.w3c.dom.Node;

import javax.xml.validation.Schema;

/**
 * Enable synchronization between XML infoset nodes and JAXB objects
 * representing same XML document.
 *
 * <p>
 * An instance of this class maintains the association between XML nodes of
 * an infoset preserving view and a JAXB representation of an XML document.
 * Navigation between the two views is provided by the methods
 * {@link #getXMLNode(Object)} and {@link #getJAXBNode(Object)}.
 *
 * <p>
 * Modifications can be made to either the infoset preserving view or the
 * JAXB representation of the document while the other view remains
 * unmodified. The binder is able to synchronize the changes made in the
 * modified view back into the other view using the appropriate
 * Binder update methods, {@link #updateXML(Object, Object)} or
 * {@link #updateJAXB(Object)}.
 *
 * <p>
 * A typical usage scenario is the following:
 * <ul>
 *   <li>load XML document into an XML infoset representation</li>
 *   <li>{@link #unmarshal(Object)} XML infoset view to JAXB view.
 *       (Note to conserve resources, it is possible to only unmarshal a
 *       subtree of the XML infoset view to the JAXB view.)</li>
 *   <li>application access/updates JAXB view of XML document.</li>
 *   <li>{@link #updateXML(Object)} synchronizes modifications to JAXB view
 *       back into the XML infoset view. Update operation preserves as
 *       much of original XML infoset as possible (i.e. comments, PI, ...)</li>
 * </ul>
 *
 * <p>
 * A Binder instance is created using the factory method
 * {@link JAXBContext#createBinder()} or {@link JAXBContext#createBinder(Class)}.
 *
 * <p>
 * The template parameter, <code>XmlNode</code>, is the
 * root interface/class for the XML infoset preserving representation.
 * A Binder implementation is required to minimally support
 * an <code>XmlNode</code> value of <code>org.w3c.dom.Node.class</code>.
 * A Binder implementation can support alternative XML infoset
 * preserving representations.
 *
 * <p>
 *  启用XML信息集节点和表示同一XML文档的JAXB对象之间的同步。
 * 
 * <p>
 *  此类的实例维护信息集保留视图的XML节点与XML文档的JAXB表示之间的关联。
 * 两个视图之间的导航由{@link #getXMLNode(Object)}和{@link #getJAXBNode(Object)}方法提供。
 * 
 * <p>
 *  可以对文档的信息集保留视图或JAXB表示进行修改,而其他视图保持未修改。
 * 绑定器能够使用适当的Binder更新方法{@link #updateXML(Object,Object)}或{@link #updateJAXB(Object)}将修改视图中所做的更改同步到其他视图中。
 *  可以对文档的信息集保留视图或JAXB表示进行修改,而其他视图保持未修改。
 * 
 * <p>
 *  典型的使用场景如下：
 * <ul>
 *  <li>将XML文档加载到XML信息集表示</li> <li> {@ link #unmarshal(Object)} XML信息集视图到JAXB视图。
 *  (请注意,为节省资源,可以仅将JAVA视图中的XML信息集视图的子树解组)。</li> <li>应用程序访问/更新XML文档的JAXB视图。
 * </li> <li> @link #updateXML(Object)}将对JAXB视图的修改同步到XML信息集视图中。更新操作尽可能保留原始XML信息集(即注释,PI ...)</li>。
 * </ul>
 * 
 * <p>
 * 使用工厂方法{@link JAXBContext#createBinder()}或{@link JAXBContext#createBinder(Class)}创建Binder实例。
 * 
 * <p>
 *  模板参数<code> XmlNode </code>是XML信息集保留表示的根接口/类。
 * 需要Binder实现以最小化支持<code> org.w3c.dom.Node.class </code>的<code> XmlNode </code>值。
 *  Binder实现可以支持备选的XML信息集保留表示。
 * 
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 *     Joseph Fialli
 *
 * @since JAXB 2.0
 */
public abstract class Binder<XmlNode> {
    /**
     * Unmarshal XML infoset view to a JAXB object tree.
     *
     * <p>
     * This method is similar to {@link Unmarshaller#unmarshal(Node)}
     * with the addition of maintaining the association between XML nodes
     * and the produced JAXB objects, enabling future update operations,
     * {@link #updateXML(Object, Object)} or {@link #updateJAXB(Object)}.
     *
     * <p>
     * When {@link #getSchema()} is non-null, <code>xmlNode</code>
     * and its descendants is validated during this operation.
     *
     * <p>
     * This method throws {@link UnmarshalException} when the Binder's
     * {@link JAXBContext} does not have a mapping for the XML element name
     * or the type, specifiable via <tt>@xsi:type</tt>, of <tt>xmlNode</tt>
     * to a JAXB mapped class. The method {@link #unmarshal(Object, Class)}
     * enables an application to specify the JAXB mapped class that
     * the <tt>xmlNode</tt> should be mapped to.
     *
     * <p>
     *  将XML信息集视图解组到JAXB对象树。
     * 
     * <p>
     *  此方法类似于{@link Unmarshaller#unmarshal(Node)},另外还保留XML节点与生成的JAXB对象之间的关联,以支持将来的更新操作{@link #updateXML(Object,Object)}
     * 或{ link #updateJAXB(Object)}。
     * 
     * <p>
     *  当{@link #getSchema()}为非null时,<code> xmlNode </code>及其后代在此操作期间生效。
     * 
     * <p>
     *  当Binder的{@link JAXBContext}没有XML元素名称或可通过<tt> @xsi：type </tt>指定的类型的<tt> xmlNode </tt>类型的映射时,此方法会抛出{@link UnmarshalException}
     *  / tt>到JAXB映射类。
     * 方法{@link #unmarshal(Object,Class)}使应用程序能够指定<tt> xmlNode </tt>应映射到的JAXB映射类。
     * 
     * 
     * @param xmlNode
     *      the document/element to unmarshal XML data from.
     *
     * @return
     *      the newly created root object of the JAXB object tree.
     *
     * @throws JAXBException
     *      If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Binder</tt> is unable to perform the XML to Java
     *     binding.
     * @throws IllegalArgumentException
     *      If the node parameter is null
     */
    public abstract Object unmarshal( XmlNode xmlNode ) throws JAXBException;

    /**
     * Unmarshal XML root element by provided <tt>declaredType</tt>
     * to a JAXB object tree.
     *
     * <p>
     * Implements <a href="Unmarshaller.html#unmarshalByDeclaredType">Unmarshal by Declared Type</a>
     *
     * <p>
     * This method is similar to {@link Unmarshaller#unmarshal(Node, Class)}
     * with the addition of maintaining the association between XML nodes
     * and the produced JAXB objects, enabling future update operations,
     * {@link #updateXML(Object, Object)} or {@link #updateJAXB(Object)}.
     *
     * <p>
     * When {@link #getSchema()} is non-null, <code>xmlNode</code>
     * and its descendants is validated during this operation.
     *
     * <p>
     *  通过提供<tt> declaredType </tt>来解析XML根元素到JAXB对象树。
     * 
     * <p>
     *  实现<a href="Unmarshaller.html#unmarshalByDeclaredType">通过声明的类型取消编组</a>
     * 
     * <p>
     * 此方法类似于{@link Unmarshaller#unmarshal(Node,Class)},另外还保留XML节点与生成的JAXB对象之间的关联,从而支持将来的更新操作{@link #updateXML(Object,Object)}
     *  {@link #updateJAXB(Object)}。
     * 
     * <p>
     *  当{@link #getSchema()}为非null时,<code> xmlNode </code>及其后代在此操作期间生效。
     * 
     * 
     * @param xmlNode
     *      the document/element to unmarshal XML data from.
     * @param declaredType
     *      appropriate JAXB mapped class to hold <tt>node</tt>'s XML data.
     *
     * @return
     * <a href="JAXBElement.html">JAXB Element</a> representation
     * of <tt>node</tt>
     *
     * @throws JAXBException
     *      If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Binder</tt> is unable to perform the XML to Java
     *     binding.
     * @throws IllegalArgumentException
     *      If any of the input parameters are null
     * @since JAXB2.0
     */
    public abstract <T> JAXBElement<T>
        unmarshal( XmlNode xmlNode, Class<T> declaredType )
        throws JAXBException;

    /**
     * Marshal a JAXB object tree to a new XML document.
     *
     * <p>
     * This method is similar to {@link Marshaller#marshal(Object, Node)}
     * with the addition of maintaining the association between JAXB objects
     * and the produced XML nodes,
     * enabling future update operations such as
     * {@link #updateXML(Object, Object)} or {@link #updateJAXB(Object)}.
     *
     * <p>
     * When {@link #getSchema()} is non-null, the marshalled
     * xml content is validated during this operation.
     *
     * <p>
     *  将JAXB对象树编组到新的XML文档。
     * 
     * <p>
     *  此方法类似于{@link Marshaller#marshal(Object,Node)},除了保持JAXB对象和生成的XML节点之间的关联,还支持将来的更新操作,例如{@link #updateXML(Object,Object)}
     * 或{@link #updateJAXB(Object)}。
     * 
     * <p>
     *  当{@link #getSchema()}为非空值时,在此操作期间将验证已编组的xml内容。
     * 
     * 
     * @param jaxbObject
     *      The content tree to be marshalled.
     * @param xmlNode
     *      The parameter must be a Node that accepts children.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Binder</tt> is unable to marshal <tt>jaxbObject</tt> (or any
     *      object reachable from <tt>jaxbObject</tt>).
     *
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public abstract void marshal( Object jaxbObject, XmlNode xmlNode ) throws JAXBException;

    /**
     * Gets the XML element associated with the given JAXB object.
     *
     * <p>
     * Once a JAXB object tree is associated with an XML fragment,
     * this method enables navigation between the two trees.
     *
     * <p>
     * An association between an XML element and a JAXB object is
     * established by the bind methods and the update methods.
     * Note that this association is partial; not all XML elements
     * have associated JAXB objects, and not all JAXB objects have
     * associated XML elements.
     *
     * <p>
     *  获取与给定JAXB对象关联的XML元素。
     * 
     * <p>
     *  一旦JAXB对象树与XML片段相关联,此方法就可以在两个树之间导航。
     * 
     * <p>
     *  XML元素和JAXB对象之间的关联由绑定方法和更新方法建立。注意,这种关联是部分的;并非所有XML元素都具有关联的JAXB对象,并且并非所有JAXB对象都具有关联的XML元素。
     * 
     * 
     * @param jaxbObject An instance that is reachable from a prior
     *                   call to a bind or update method that returned
     *                   a JAXB object tree.
     *
     * @return
     *      null if the specified JAXB object is not known to this
     *      {@link Binder}, or if it is not associated with an
     *      XML element.
     *
     * @throws IllegalArgumentException
     *      If the jaxbObject parameter is null
     */
    public abstract XmlNode getXMLNode( Object jaxbObject );

    /**
     * Gets the JAXB object associated with the given XML element.
     *
     * <p>
     * Once a JAXB object tree is associated with an XML fragment,
     * this method enables navigation between the two trees.
     *
     * <p>
     * An association between an XML element and a JAXB object is
     * established by the unmarshal, marshal and update methods.
     * Note that this association is partial; not all XML elements
     * have associated JAXB objects, and not all JAXB objects have
     * associated XML elements.
     *
     * <p>
     *  获取与给定XML元素关联的JAXB对象。
     * 
     * <p>
     *  一旦JAXB对象树与XML片段相关联,此方法就可以在两个树之间导航。
     * 
     * <p>
     * XML元素和JAXB对象之间的关联由解组,元组和更新方法建立。注意,这种关联是部分的;并非所有XML元素都具有关联的JAXB对象,并且并非所有JAXB对象都具有关联的XML元素。
     * 
     * 
     * @return
     *      null if the specified XML node is not known to this
     *      {@link Binder}, or if it is not associated with a
     *      JAXB object.
     *
     * @throws IllegalArgumentException
     *      If the node parameter is null
     */
    public abstract Object getJAXBNode( XmlNode xmlNode );

    /**
     * Takes an JAXB object and updates
     * its associated XML node and its descendants.
     *
     * <p>
     * This is a convenience method of:
     * <pre>
     * updateXML( jaxbObject, getXMLNode(jaxbObject));
     * </pre>
     *
     * <p>
     *  获取JAXB对象并更新其关联的XML节点及其后代。
     * 
     * <p>
     *  这是一个方便的方法：
     * <pre>
     *  updateXML(jaxbObject,getXMLNode(jaxbObject));
     * </pre>
     * 
     * 
     * @throws JAXBException
     *      If any unexpected problem occurs updating corresponding XML content.
     * @throws IllegalArgumentException
     *      If the jaxbObject parameter is null
     */
    public abstract XmlNode updateXML( Object jaxbObject ) throws JAXBException;

    /**
     * Changes in JAXB object tree are updated in its associated XML parse tree.
     *
     * <p>
     * This operation can be thought of as an "in-place" marshalling.
     * The difference is that instead of creating a whole new XML tree,
     * this operation updates an existing tree while trying to preserve
     * the XML as much as possible.
     *
     * <p>
     * For example, unknown elements/attributes in XML that were not bound
     * to JAXB will be left untouched (whereas a marshalling operation
     * would create a new tree that doesn't contain any of those.)
     *
     * <p>
     * As a side-effect, this operation updates the association between
     * XML nodes and JAXB objects.
     *
     * <p>
     *  JAXB对象树中的更改在其关联的XML解析树中更新。
     * 
     * <p>
     *  这个操作可以被认为是一个"就地"编组。不同的是,这个操作不是创建一个全新的XML树,而是更新现有的树,同时尽可能地保留XML。
     * 
     * <p>
     *  例如,XML中未绑定到JAXB的未知元素/属性将保持不变(而编组操作将创建一个不包含任何这些的新树)。
     * 
     * <p>
     *  作为副作用,此操作更新XML节点和JAXB对象之间的关联。
     * 
     * 
     * @param jaxbObject root of potentially modified JAXB object tree
     * @param xmlNode    root of update target XML parse tree
     *
     * @return
     *      Returns the updated XML node. Typically, this is the same
     *      node you passed in as <i>xmlNode</i>, but it maybe
     *      a different object, for example when the tag name of the object
     *      has changed.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs updating corresponding XML content.
     * @throws IllegalArgumentException
     *      If any of the input parameters are null
     */
    public abstract XmlNode updateXML( Object jaxbObject, XmlNode xmlNode ) throws JAXBException;

    /**
     * Takes an XML node and updates its associated JAXB object and its descendants.
     *
     * <p>
     * This operation can be thought of as an "in-place" unmarshalling.
     * The difference is that instead of creating a whole new JAXB tree,
     * this operation updates an existing tree, reusing as much JAXB objects
     * as possible.
     *
     * <p>
     * As a side-effect, this operation updates the association between
     * XML nodes and JAXB objects.
     *
     * <p>
     *  获取一个XML节点并更新其相关联的JAXB对象及其后代。
     * 
     * <p>
     *  这个操作可以被认为是"就地"解组。不同的是,这个操作不是创建一个全新的JAXB树,而是更新现有的树,重用尽可能多的JAXB对象。
     * 
     * <p>
     *  作为副作用,此操作更新XML节点和JAXB对象之间的关联。
     * 
     * 
     * @return
     *      Returns the updated JAXB object. Typically, this is the same
     *      object that was returned from earlier
     *      {@link #marshal(Object,Object)} or
     *      {@link #updateJAXB(Object)} method invocation,
     *      but it maybe
     *      a different object, for example when the name of the XML
     *      element has changed.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs updating corresponding JAXB mapped content.
     * @throws IllegalArgumentException
     *      If node parameter is null
     */
    public abstract Object updateJAXB( XmlNode xmlNode ) throws JAXBException;


    /**
     * Specifies whether marshal, unmarshal and update methods
     * performs validation on their XML content.
     *
     * <p>
     *  指定元数,解组和更新方法是否对其XML内容执行验证。
     * 
     * 
     * @param schema set to null to disable validation.
     *
     * @see Unmarshaller#setSchema(Schema)
     */
    public abstract void setSchema( Schema schema );

    /**
     * Gets the last {@link Schema} object (including null) set by the
     * {@link #setSchema(Schema)} method.
     *
     * <p>
     * 获取{@link #setSchema(Schema)}方法设置的最后一个{@link Schema}对象(包括null)。
     * 
     * 
     * @return the Schema object for validation or null if not present
     */
    public abstract Schema getSchema();

    /**
     * Allow an application to register a <tt>ValidationEventHandler</tt>.
     * <p>
     * The <tt>ValidationEventHandler</tt> will be called by the JAXB Provider
     * if any validation errors are encountered during calls to any of the
     * Binder unmarshal, marshal and update methods.
     *
     * <p>
     * Calling this method with a null parameter will cause the Binder
     * to revert back to the default default event handler.
     *
     * <p>
     *  允许应用程序注册<tt> ValidationEventHandler </tt>。
     * <p>
     *  如果在调用任何Binder取消编组,元组和更新方法期间遇到任何验证错误,则JAXB提供程序将调用<tt> ValidationEventHandler </tt>。
     * 
     * <p>
     *  使用null参数调用此方法将导致Binder恢复为默认的默认事件处理程序。
     * 
     * 
     * @param handler the validation event handler
     * @throws JAXBException if an error was encountered while setting the
     *         event handler
     */
    public abstract void setEventHandler( ValidationEventHandler handler ) throws JAXBException;

    /**
     * Return the current event handler or the default event handler if one
     * hasn't been set.
     *
     * <p>
     *  返回当前事件处理程序或默认事件处理程序(如果尚未设置)。
     * 
     * 
     * @return the current ValidationEventHandler or the default event handler
     *         if it hasn't been set
     * @throws JAXBException if an error was encountered while getting the
     *         current event handler
     */
    public abstract ValidationEventHandler getEventHandler() throws JAXBException;

    /**
     *
     * Set the particular property in the underlying implementation of
     * <tt>Binder</tt>.  This method can only be used to set one of
     * the standard JAXB defined unmarshal/marshal properties
     * or a provider specific property for binder, unmarshal or marshal.
     * Attempting to set an undefined property will result in
     * a PropertyException being thrown.  See
     * <a href="Unmarshaller.html#supportedProps">Supported Unmarshal Properties</a>
     * and
     * <a href="Marshaller.html#supportedProps">Supported Marshal Properties</a>.
     *
     * <p>
     *  在<tt> Binder </tt>的基础实现中设置特定属性。
     * 此方法只能用于设置标准JAXB定义的unmarshal / marshal属性或binder,unmarshal或marshal的特定于提供程序的属性。
     * 尝试设置未定义的属性将导致抛出PropertyException。
     * 请参见<a href="Unmarshaller.html#supportedProps">支持的取消组合属性</a>和<a href="Marshaller.html#supportedProps">
     * 支持的元数属性</a>。
     * 尝试设置未定义的属性将导致抛出PropertyException。
     * 
     * 
     * @param name the name of the property to be set. This value can either
     *              be specified using one of the constant fields or a user
     *              supplied string.
     * @param value the value of the property to be set
     *
     * @throws PropertyException when there is an error processing the given
     *                            property or value
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    abstract public void setProperty( String name, Object value ) throws PropertyException;


    /**
     * Get the particular property in the underlying implementation of
     * <tt>Binder</tt>.  This method can only
     * be used to get one of
     * the standard JAXB defined unmarshal/marshal properties
     * or a provider specific property for binder, unmarshal or marshal.
     * Attempting to get an undefined property will result in
     * a PropertyException being thrown.  See
     * <a href="Unmarshaller.html#supportedProps">Supported Unmarshal Properties</a>
     * and
     * <a href="Marshaller.html#supportedProps">Supported Marshal Properties</a>.
     *
     * <p>
     * 获取<tt> Binder </tt>的基本实现中的特定属性。
     * 此方法只能用于获取标准JAXB定义的unmarshal / marshal属性或binder,unmarshal或marshal的提供程序特定属性之一。
     * 尝试获取未定义的属性将导致PropertyException被抛出。
     * 请参见<a href="Unmarshaller.html#supportedProps">支持的取消组合属性</a>和<a href="Marshaller.html#supportedProps">
     * 
     * @param name the name of the property to retrieve
     * @return the value of the requested property
     *
     * @throws PropertyException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    abstract public Object getProperty( String name ) throws PropertyException;

}
