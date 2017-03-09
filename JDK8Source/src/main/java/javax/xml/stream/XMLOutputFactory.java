/***** Lobxxx Translate Finished ******/
/*
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

/*
 * Copyright (c) 2009, 2013, by Oracle Corporation. All Rights Reserved.
 * <p>
 *  版权所有(c)2009,2013,由Oracle Corporation。版权所有。
 * 
 */

package javax.xml.stream;

import javax.xml.transform.Result;

/**
 * Defines an abstract implementation of a factory for
 * getting XMLEventWriters and XMLStreamWriters.
 *
 * The following table defines the standard properties of this specification.
 * Each property varies in the level of support required by each implementation.
 * The level of support required is described in the 'Required' column.
 *
 *     <table border="2" rules="all" cellpadding="4">
 *     <thead>
 *      <tr>
 *        <th align="center" colspan="2">
 *          Configuration parameters
 *        </th>
 *      </tr>
 *    </thead>
 *    <tbody>
 *      <tr>
 *        <th>Property Name</th>
 *        <th>Behavior</th>
 *        <th>Return type</th>
 *        <th>Default Value</th>
 *        <th>Required</th>
 *              </tr>
 *         <tr><td>javax.xml.stream.isRepairingNamespaces</td><td>defaults prefixes on the output side</td><td>Boolean</td><td>False</td><td>Yes</td></tr>
 *      </tbody>
 *   </table>
 *
 * <p>The following paragraphs describe the namespace and prefix repair algorithm:</p>
 *
 * <p>The property can be set with the following code line:
 * <code>setProperty("javax.xml.stream.isRepairingNamespaces",new Boolean(true|false));</code></p>
 *
 * <p>This property specifies that the writer default namespace prefix declarations.
 * The default value is false. </p>
 *
 * <p>If a writer isRepairingNamespaces it will create a namespace declaration
 * on the current StartElement for
 * any attribute that does not
 * currently have a namespace declaration in scope.  If the StartElement
 * has a uri but no prefix specified a prefix will be assigned, if the prefix
 * has not been declared in a parent of the current StartElement it will be declared
 * on the current StartElement.  If the defaultNamespace is bound and in scope
 * and the default namespace matches the URI of the attribute or StartElement
 * QName no prefix will be assigned.</p>
 *
 * <p>If an element or attribute name has a prefix, but is not
 * bound to any namespace URI, then the prefix will be removed
 * during serialization.</p>
 *
 * <p>If element and/or attribute names in the same start or
 * empty-element tag are bound to different namespace URIs and
 * are using the same prefix then the element or the first
 * occurring attribute retains the original prefix and the
 * following attributes have their prefixes replaced with a
 * new prefix that is bound to the namespace URIs of those
 * attributes. </p>
 *
 * <p>If an element or attribute name uses a prefix that is
 * bound to a different URI than that inherited from the
 * namespace context of the parent of that element and there
 * is no namespace declaration in the context of the current
 * element then such a namespace declaration is added. </p>
 *
 * <p>If an element or attribute name is bound to a prefix and
 * there is a namespace declaration that binds that prefix
 * to a different URI then that namespace declaration is
 * either removed if the correct mapping is inherited from
 * the parent context of that element, or changed to the
 * namespace URI of the element or attribute using that prefix.</p>
 *
 * <p>
 *  定义一个工厂的抽象实现,用于获取XMLEventWriters和XMLStreamWriter。
 * 
 *  下表定义了此规范的标准属性。每个属性在每个实现所需的支持级别上不同。所需的支持级别在"必填"列中说明。
 * 
 * <table border="2" rules="all" cellpadding="4">
 * <thead>
 * <tr>
 * <th align="center" colspan="2">
 *  配置参数
 * </th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <th>属性名称</th> <th>行为</th> <th>返回类型</th> <th>默认值</th> <th>必需</th>
 * </tr>
 *  <tr> <td> javax.xml.stream.isRepairingNamespaces </td> <td>输出端上的默认值前缀</td> <td>布尔</td> <td> False </td>
 *  <td>是</td> </tr>。
 * </tbody>
 * </table>
 * 
 *  <p>以下段落描述命名空间和前缀修复算法：</p>
 * 
 *  <p>可以使用以下代码行设置属性：<code> setProperty("javax.xml.stream.isRepairingNamespaces",new Boolean(true | fals
 * e)); </code> </p>。
 * 
 *  <p>此属性指定作者缺省命名空间前缀声明。默认值为false。 </p>
 * 
 * <p>如果一个作者isRepairingNamespaces,它将在当前StartElement上为当前在作用域中没有命名空间声明的任何属性创建一个命名空间声明。
 * 如果StartElement有一个uri但没有指定前缀,则会分配一个前缀,如果该前缀没有在当前StartElement的父级中声明,它将在当前StartElement上声明。
 * 如果defaultNamespace被绑定并且在范围内,并且默认名称空间与属性的URI或StartElement QName匹配,则将不分配任何前缀。</p>。
 * 
 *  <p>如果元素或属性名称有前缀,但未绑定到任何命名空间URI,则序列化期间将删除前缀。</p>
 * 
 *  <p>如果同一起始或空元素标记中的元素和/或属性名称绑定到不同的命名空间URI,并且使用相同的前缀,则元素或第一个出现的属性保留原始前缀,并且以下属性具有其前缀替换为绑定到这些属性的命名空间URI的新
 * 前缀。
 *  </p>。
 * 
 *  <p>如果元素或属性名称使用绑定到不同于继承自该元素的父级的命名空间上下文的URI的前缀,并且在当前元素的上下文中没有命名空间声明,那么这样的命名空间声明被添加。 </p>
 * 
 * <p>如果元素或属性名称绑定到前缀,并且有一个命名空间声明将该前缀绑定到不同的URI,那么如果正确的映射从该元素的父上下文继承,那么该命名空间声明将被删除,或者更改为使用该前缀的元素或属性的名称空间UR
 * I。
 * </p>。
 * 
 * 
 * @version 1.2
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see XMLInputFactory
 * @see XMLEventWriter
 * @see XMLStreamWriter
 * @since 1.6
 */
public abstract class XMLOutputFactory {
  /**
   * Property used to set prefix defaulting on the output side
   * <p>
   *  用于在输出端设置前缀默认值的属性
   * 
   */
  public static final String IS_REPAIRING_NAMESPACES=
    "javax.xml.stream.isRepairingNamespaces";

  static final String DEFAULIMPL = "com.sun.xml.internal.stream.XMLOutputFactoryImpl";

  protected XMLOutputFactory(){}

  /**
   * Creates a new instance of the factory in exactly the same manner as the
   * {@link #newFactory()} method.
   * <p>
   *  以与{@link #newFactory()}方法完全相同的方式创建工厂的新实例。
   * 
   * 
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   */
  public static XMLOutputFactory newInstance()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLOutputFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory.
   * <p>
   * This static method creates a new factory instance. This method uses the
   * following ordered lookup procedure to determine the XMLOutputFactory
   * implementation class to load:
   * </p>
   * <ul>
   * <li>
   *   Use the javax.xml.stream.XMLOutputFactory system property.
   * </li>
   * <li>
   *   Use the properties file "lib/stax.properties" in the JRE directory.
   *     This configuration file is in standard java.util.Properties format
   *     and contains the fully qualified name of the implementation class
   *     with the key being the system property defined above.
   * </li>
   * <li>
   *   Use the service-provider loading facilities, defined by the
   *   {@link java.util.ServiceLoader} class, to attempt to locate and load an
   *   implementation of the service using the {@linkplain
   *   java.util.ServiceLoader#load(java.lang.Class) default loading mechanism}:
   *   the service-provider loading facility will use the {@linkplain
   *   java.lang.Thread#getContextClassLoader() current thread's context class loader}
   *   to attempt to load the service. If the context class
   *   loader is null, the {@linkplain
   *   ClassLoader#getSystemClassLoader() system class loader} will be used.
   * </li>
   * <li>
   *   Otherwise, the system-default implementation is returned.
   * </li>
   * <p>
   * Once an application has obtained a reference to a XMLOutputFactory it
   * can use the factory to configure and obtain stream instances.
   * </p>
   * <p>
   * Note that this is a new method that replaces the deprecated newInstance() method.
   *   No changes in behavior are defined by this replacement method relative to the
   *   deprecated method.
   * </p>
   * <p>
   *  创建一个新的工厂实例。
   * <p>
   *  这个静态方法创建一个新的工厂实例。此方法使用以下有序查找过程来确定要加载的XMLOutputFactory实现类：
   * </p>
   * <ul>
   * <li>
   *  使用javax.xml.stream.XMLOutputFactory系统属性。
   * </li>
   * <li>
   *  使用JRE目录中的属性文件"lib / stax.properties"。此配置文件采用标准java.util.Properties格式,并包含实现类的完全限定名称,其中键是上面定义的系统属性。
   * </li>
   * <li>
   * 使用由{@link java.util.ServiceLoader}类定义的服务提供程序加载工具,尝试使用{@linkplain java.util.ServiceLoader#load(java.lang。
   * )来定位和加载服务的实现。
   * 类)默认加载机制}：service-provider加载工具将使用{@linkplain java.lang.Thread#getContextClassLoader()当前线程的上下文类加载器}来尝试
   * 加载服务。
   * )来定位和加载服务的实现。如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
   * </li>
   * <li>
   *  否则,返回系统默认实现。
   * </li>
   * <p>
   *  一旦应用程序获得了对XMLOutputFactory的引用,它可以使用工厂来配置和获取流实例。
   * </p>
   * <p>
   *  请注意,这是一种替换已弃用的newInstance()方法的新方法。相对于已弃用的方法,此替换方法未定义行为更改。
   * </p>
   * 
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   */
  public static XMLOutputFactory newFactory()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLOutputFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory.
   *
   * <p>
   *  创建一个新的工厂实例。
   * 
   * 
   * @param factoryId             Name of the factory to find, same as
   *                              a property name
   * @param classLoader           classLoader to use
   * @return the factory implementation
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   *
   * @deprecated  This method has been deprecated because it returns an
   *              instance of XMLInputFactory, which is of the wrong class.
   *              Use the new method {@link #newFactory(java.lang.String,
   *              java.lang.ClassLoader)} instead.
   */
  public static XMLInputFactory newInstance(String factoryId,
          ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLInputFactory.class, factoryId, classLoader, null);
  }

  /**
   * Create a new instance of the factory.
   * If the classLoader argument is null, then the ContextClassLoader is used.
   * <p>
   * This method uses the following ordered lookup procedure to determine
   * the XMLOutputFactory implementation class to load:
   * </p>
   * <ul>
   * <li>
   *   Use the value of the system property identified by {@code factoryId}.
   * </li>
   * <li>
   *   Use the properties file "lib/stax.properties" in the JRE directory.
   *     This configuration file is in standard java.util.Properties format
   *     and contains the fully qualified name of the implementation class
   *     with the key being the given {@code factoryId}.
   * </li>
   * <li>
   *   If {@code factoryId} is "javax.xml.stream.XMLOutputFactory",
   *   use the service-provider loading facilities, defined by the
   *   {@link java.util.ServiceLoader} class, to attempt to {@linkplain
   *   java.util.ServiceLoader#load(java.lang.Class, java.lang.ClassLoader) locate and load}
   *   an implementation of the service using the specified {@code ClassLoader}.
   *   If {@code classLoader} is null, the {@linkplain
   *   java.util.ServiceLoader#load(java.lang.Class) default loading mechanism} will apply:
   *   That is, the service-provider loading facility will use the {@linkplain
   *   java.lang.Thread#getContextClassLoader() current thread's context class loader}
   *   to attempt to load the service. If the context class
   *   loader is null, the {@linkplain
   *   ClassLoader#getSystemClassLoader() system class loader} will be used.
   * </li>
   * <li>
   *   Otherwise, throws a {@link FactoryConfigurationError}.
   * </li>
   * </ul>
   *
   * @apiNote The parameter factoryId defined here is inconsistent with that
   * of other JAXP factories where the first parameter is fully qualified
   * factory class name that provides implementation of the factory.
   *
   * <p>
   *   Note that this is a new method that replaces the deprecated
   *   {@link #newInstance(java.lang.String, java.lang.ClassLoader)
   *   newInstance(String factoryId, ClassLoader classLoader)} method.
   *   The original method was incorrectly defined to return XMLInputFactory.
   * </p>
   *
   * <p>
   *  创建一个新的工厂实例。如果classLoader参数为null,那么使用ContextClassLoader。
   * <p>
   *  此方法使用以下有序查找过程来确定要加载的XMLOutputFactory实现类：
   * </p>
   * <ul>
   * <li>
   *  使用由{@code factoryId}标识的系统属性的值。
   * </li>
   * <li>
   * 使用JRE目录中的属性文件"lib / stax.properties"。
   * 此配置文件采用标准java.util.Properties格式,并且包含实现类的完全限定名,键为给定的{@code factoryId}。
   * </li>
   * <li>
   *  如果{@code factoryId}是"javax.xml.stream.XMLOutputFactory",请使用{@link java.util.ServiceLoader}类定义的服务提供商加
   * 载工具尝试{@linkplain java.util.ServiceLoader #load(java.lang.Class,java.lang.ClassLoader)locate和load}使用指定
   * 的{@code ClassLoader}的服务实现。
   * 如果{@code classLoader}为null,{@linkplain java.util.ServiceLoader#load(java.lang.Class)默认加载机制}将适用：也就是说,s
   * ervice-provider加载工具将使用{@linkplain java .lang.Thread#getContextClassLoader()当前线程的上下文类加载器}尝试加载服务。
   * 如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
   * </li>
   * <li>
   *  否则,会抛出{@link FactoryConfigurationError}。
   * </li>
   * </ul>
   * 
   *  @apiNote这里定义的参数factoryId与其他JAXP工厂的参数不一致,其中第一个参数是提供工厂实现的完全限定的工厂类名。
   * 
   * <p>
   * 请注意,这是一种替换已弃用的{@link #newInstance(java.lang.String,java.lang.ClassLoader)newInstance(String factoryId,ClassLoader classLoader)}
   * 方法的新方法。
   * 原始方法未正确定义以返回XMLInputFactory。
   * </p>
   * 
   * 
   * @param factoryId             Name of the factory to find, same as
   *                              a property name
   * @param classLoader           classLoader to use
   * @return the factory implementation
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   */
  public static XMLOutputFactory newFactory(String factoryId,
          ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLOutputFactory.class, factoryId, classLoader, null);
  }

  /**
   * Create a new XMLStreamWriter that writes to a writer
   * <p>
   * 
   * @param stream the writer to write to
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(java.io.Writer stream) throws XMLStreamException;

  /**
   * Create a new XMLStreamWriter that writes to a stream
   * <p>
   *  创建写入写入程序的新XMLStreamWriter
   * 
   * 
   * @param stream the stream to write to
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(java.io.OutputStream stream) throws XMLStreamException;

  /**
   * Create a new XMLStreamWriter that writes to a stream
   * <p>
   *  创建写入流的新XMLStreamWriter
   * 
   * 
   * @param stream the stream to write to
   * @param encoding the encoding to use
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(java.io.OutputStream stream,
                                         String encoding) throws XMLStreamException;

  /**
   * Create a new XMLStreamWriter that writes to a JAXP result.  This method is optional.
   * <p>
   *  创建写入流的新XMLStreamWriter
   * 
   * 
   * @param result the result to write to
   * @throws UnsupportedOperationException if this method is not
   * supported by this XMLOutputFactory
   * @throws XMLStreamException
   */
  public abstract XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException;


  /**
   * Create a new XMLEventWriter that writes to a JAXP result.  This method is optional.
   * <p>
   *  创建写入JAXP结果的新XMLStreamWriter。此方法是可选的。
   * 
   * 
   * @param result the result to write to
   * @throws UnsupportedOperationException if this method is not
   * supported by this XMLOutputFactory
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException;

  /**
   * Create a new XMLEventWriter that writes to a stream
   * <p>
   *  创建一个写入JAXP结果的新XMLEventWriter。此方法是可选的。
   * 
   * 
   * @param stream the stream to write to
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(java.io.OutputStream stream) throws XMLStreamException;



  /**
   * Create a new XMLEventWriter that writes to a stream
   * <p>
   *  创建一个写入流的新XMLEventWriter
   * 
   * 
   * @param stream the stream to write to
   * @param encoding the encoding to use
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(java.io.OutputStream stream,
                                                     String encoding) throws XMLStreamException;

  /**
   * Create a new XMLEventWriter that writes to a writer
   * <p>
   *  创建一个写入流的新XMLEventWriter
   * 
   * 
   * @param stream the stream to write to
   * @throws XMLStreamException
   */
  public abstract XMLEventWriter createXMLEventWriter(java.io.Writer stream) throws XMLStreamException;

  /**
   * Allows the user to set specific features/properties on the underlying implementation.
   * <p>
   *  创建一个写入写入程序的新XMLEventWriter
   * 
   * 
   * @param name The name of the property
   * @param value The value of the property
   * @throws java.lang.IllegalArgumentException if the property is not supported
   */
  public abstract void setProperty(java.lang.String name,
                                    Object value)
    throws IllegalArgumentException;

  /**
   * Get a feature/property on the underlying implementation
   * <p>
   *  允许用户在底层实现上设置特定的功能/属性。
   * 
   * 
   * @param name The name of the property
   * @return The value of the property
   * @throws java.lang.IllegalArgumentException if the property is not supported
   */
  public abstract Object getProperty(java.lang.String name)
    throws IllegalArgumentException;

  /**
   * Query the set of properties that this factory supports.
   *
   * <p>
   *  获取基础实现的功能/属性
   * 
   * 
   * @param name The name of the property (may not be null)
   * @return true if the property is supported and false otherwise
   */
  public abstract boolean isPropertySupported(String name);
}
