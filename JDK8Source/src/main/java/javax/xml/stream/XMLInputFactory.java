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
 *  版权所有(c)2009,2013,Oracle Corporation保留所有权利
 * 
 */

package javax.xml.stream;

import javax.xml.stream.util.XMLEventAllocator;
import javax.xml.transform.Source;

/**
 * Defines an abstract implementation of a factory for getting streams.
 *
 * The following table defines the standard properties of this specification.
 * Each property varies in the level of support required by each implementation.
 * The level of support required is described in the 'Required' column.
 *
 *   <table border="2" rules="all" cellpadding="4">
 *    <thead>
 *      <tr>
 *        <th align="center" colspan="5">
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
 *      </tr>
 * <tr><td>javax.xml.stream.isValidating</td><td>Turns on/off implementation specific DTD validation</td><td>Boolean</td><td>False</td><td>No</td></tr>
 * <tr><td>javax.xml.stream.isNamespaceAware</td><td>Turns on/off namespace processing for XML 1.0 support</td><td>Boolean</td><td>True</td><td>True (required) / False (optional)</td></tr>
 * <tr><td>javax.xml.stream.isCoalescing</td><td>Requires the processor to coalesce adjacent character data</td><td>Boolean</td><td>False</td><td>Yes</td></tr>
 * <tr><td>javax.xml.stream.isReplacingEntityReferences</td><td>replace internal entity references with their replacement text and report them as characters</td><td>Boolean</td><td>True</td><td>Yes</td></tr>
 *<tr><td>javax.xml.stream.isSupportingExternalEntities</td><td>Resolve external parsed entities</td><td>Boolean</td><td>Unspecified</td><td>Yes</td></tr>
 *<tr><td>javax.xml.stream.supportDTD</td><td>Use this property to request processors that do not support DTDs</td><td>Boolean</td><td>True</td><td>Yes</td></tr>
 *<tr><td>javax.xml.stream.reporter</td><td>sets/gets the impl of the XMLReporter </td><td>javax.xml.stream.XMLReporter</td><td>Null</td><td>Yes</td></tr>
 *<tr><td>javax.xml.stream.resolver</td><td>sets/gets the impl of the XMLResolver interface</td><td>javax.xml.stream.XMLResolver</td><td>Null</td><td>Yes</td></tr>
 *<tr><td>javax.xml.stream.allocator</td><td>sets/gets the impl of the XMLEventAllocator interface</td><td>javax.xml.stream.util.XMLEventAllocator</td><td>Null</td><td>Yes</td></tr>
 *    </tbody>
 *  </table>
 *
 *
 * <p>
 *  定义用于获取流的工厂的抽象实现
 * 
 *  下表定义了此规范的标准属性每个属性在每个实现所需的支持级别上不同。所需的支持级别在"必需"列中描述
 * 
 * <table border="2" rules="all" cellpadding="4">
 * <thead>
 * <tr>
 * <th align="center" colspan="5">
 *  配置参数
 * </th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <th>属性名称</th> <th>行为</th> <th>返回类型</th> <th>默认值</th> <th>必需</th>
 * </tr>
 * <tr> <td> javaxxmlstreamisValidating </td> <td>打开/关闭实施特定的DTD验证</td> <td>布尔</td> <td> False </td> <td>
 *  </tr> <tr> <td> javaxxmlstreamisNamespaceAware </td> <td>打开/关闭XML 10支持的命名空间处理</td> <td> Boolean </td>
 *  <td> True </td> <td > True(必需)/ False(可选)</td> </tr> <tr> <td> javaxxmlstreamisCoalescing </td> <td>
 * 需要处理器合并相邻字符数据</td> <td> Boolean < td> <td> False </td> <td>是</td> </tr> <tr> <td> javaxxmlstreamisRep
 * lacingEntityReferences </td> <td>将内部实体引用替换为替换文本, / td> <td> Boolean </td> <td> True </td> <td>是</td> 
 * </tr> tr> <td> javaxxmlstreamisSupportingExternalEntities </td> <td>解析外部解析的实体</td> <td>布尔</td> <td>未指
 * 定</td> <td>是</td> </tr> tr> <td> javaxxmlstreamsupportDTD </td> <td> True </td> <td>是</td> </td> </td>
 *  tr> <td> javaxxmlstreamreporter </td> <td> set /获取XMLReporter的隐含</td> <td> javaxxmlstreamXMLReporter
 *  </td> <td> Null </td> <td> / tr> tr> <td> javaxxmlstreamresolver </td> <td> set /获取XMLResolver接口的隐含</td>
 *  <td> javaxxmlstreamXMLResolver </td> <td> / td> </td> <td> javaxxmlstreamallocator </td> <td> sets /
 * 获取XMLEventAllocator接口的隐含</td> <td> javaxxmlstreamutilXMLEventAllocator </td> <td> Null </td> td>是</td>
 *  </tr>。
 * </tbody>
 * </table>
 * 
 * 
 * @version 1.2
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see XMLOutputFactory
 * @see XMLEventReader
 * @see XMLStreamReader
 * @see EventFilter
 * @see XMLReporter
 * @see XMLResolver
 * @see javax.xml.stream.util.XMLEventAllocator
 * @since 1.6
 */

public abstract class XMLInputFactory {
  /**
   * The property used to turn on/off namespace support,
   * this is to support XML 1.0 documents,
   * only the true setting must be supported
   * <p>
   * 该属性用于打开/关闭命名空间支持,这是为了支持XML 10文档,只有真正的设置必须被支持
   * 
   */
  public static final String IS_NAMESPACE_AWARE=
    "javax.xml.stream.isNamespaceAware";

  /**
   * The property used to turn on/off implementation specific validation
   * <p>
   *  该属性用于打开/关闭实现特定的验证
   * 
   */
  public static final String IS_VALIDATING=
    "javax.xml.stream.isValidating";

  /**
   * The property that requires the parser to coalesce adjacent character data sections
   * <p>
   *  需要解析器来合并相邻字符数据节的属性
   * 
   */
  public static final String IS_COALESCING=
    "javax.xml.stream.isCoalescing";

  /**
   * Requires the parser to replace internal
   * entity references with their replacement
   * text and report them as characters
   * <p>
   *  需要解析器用其替换文本替换内部实体引用,并将其报告为字符
   * 
   */
  public static final String IS_REPLACING_ENTITY_REFERENCES=
    "javax.xml.stream.isReplacingEntityReferences";

  /**
   *  The property that requires the parser to resolve external parsed entities
   * <p>
   *  需要解析器解析外部解析实体的属性
   * 
   */
  public static final String IS_SUPPORTING_EXTERNAL_ENTITIES=
    "javax.xml.stream.isSupportingExternalEntities";

  /**
   *  The property that requires the parser to support DTDs
   * <p>
   *  需要解析器支持DTD的属性
   * 
   */
  public static final String SUPPORT_DTD=
    "javax.xml.stream.supportDTD";

  /**
   * The property used to
   * set/get the implementation of the XMLReporter interface
   * <p>
   *  用于设置/获取XMLReporter接口的实现的属性
   * 
   */
  public static final String REPORTER=
    "javax.xml.stream.reporter";

  /**
   * The property used to set/get the implementation of the XMLResolver
   * <p>
   *  该属性用于设置/获取XMLResolver的实现
   * 
   */
  public static final String RESOLVER=
    "javax.xml.stream.resolver";

  /**
   * The property used to set/get the implementation of the allocator
   * <p>
   *  用于设置/获取分配器的实现的属性
   * 
   */
  public static final String ALLOCATOR=
    "javax.xml.stream.allocator";

  static final String DEFAULIMPL = "com.sun.xml.internal.stream.XMLInputFactoryImpl";

  protected XMLInputFactory(){}

  /**
   * Creates a new instance of the factory in exactly the same manner as the
   * {@link #newFactory()} method.
   * <p>
   * 以与{@link #newFactory()}方法完全相同的方式创建工厂的新实例
   * 
   * 
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   */
  public static XMLInputFactory newInstance()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLInputFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory.
   * <p>
   * This static method creates a new factory instance.
   * This method uses the following ordered lookup procedure to determine
   * the XMLInputFactory implementation class to load:
   * </p>
   * <ul>
   * <li>
   *   Use the javax.xml.stream.XMLInputFactory system property.
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
   * Otherwise, the system-default implementation is returned.
   * </li>
   * </ul>
   * <p>
   *   Once an application has obtained a reference to a XMLInputFactory it
   *   can use the factory to configure and obtain stream instances.
   * </p>
   * <p>
   *   Note that this is a new method that replaces the deprecated newInstance() method.
   *     No changes in behavior are defined by this replacement method relative to
   *     the deprecated method.
   * </p>
   * <p>
   *  创建一个新的工厂实例
   * <p>
   *  此静态方法创建新的工厂实例此方法使用以下有序查找过程来确定要加载的XMLInputFactory实现类：
   * </p>
   * <ul>
   * <li>
   *  使用javaxxmlstreamXMLInputFactory系统属性
   * </li>
   * <li>
   *  在JRE目录中使用属性文件"lib / staxproperties"此配置文件采用标准javautilProperties格式,并包含实现类的完全限定名,键是上面定义的系统属性
   * </li>
   * <li>
   * 使用由{@link javautilServiceLoader}类定义的服务提供商加载工具,尝试使用{@linkplain javautilServiceLoader#load(javalangClass)默认加载机制)定位和加载服务的实现：服务提供商加载设施将使用{@linkplain javalangThread#getContextClassLoader()当前线程的上下文类加载器}
   * 尝试加载服务如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
   * </li>
   * <li>
   *  否则,返回系统默认实现
   * </li>
   * </ul>
   * <p>
   *  一旦应用程序获得了对XMLInputFactory的引用,它可以使用工厂来配置和获取流实例
   * </p>
   * <p>
   * 请注意,这是一种替换已弃用的newInstance()方法的新方法。相对于已弃用的方法,此替换方法未定义行为更改
   * </p>
   * 
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   */
  public static XMLInputFactory newFactory()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLInputFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory
   *
   * <p>
   *  创建一个新的工厂实例
   * 
   * 
   * @param factoryId             Name of the factory to find, same as
   *                              a property name
   * @param classLoader           classLoader to use
   * @return the factory implementation
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   *
   * @deprecated  This method has been deprecated to maintain API consistency.
   *              All newInstance methods have been replaced with corresponding
   *              newFactory methods. The replacement {@link
   *              #newFactory(java.lang.String, java.lang.ClassLoader)} method
   *              defines no changes in behavior.
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
   * the XMLInputFactory implementation class to load:
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
   *   If {@code factoryId} is "javax.xml.stream.XMLInputFactory",
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
   * <p>
   * Note that this is a new method that replaces the deprecated
   *   {@link #newInstance(java.lang.String, java.lang.ClassLoader)
   *   newInstance(String factoryId, ClassLoader classLoader)} method.
   * No changes in behavior are defined by this replacement method relative
   * to the deprecated method.
   * </p>
   *
   * @apiNote The parameter factoryId defined here is inconsistent with that
   * of other JAXP factories where the first parameter is fully qualified
   * factory class name that provides implementation of the factory.
   *
   * <p>
   *  创建工厂的新实例如果classLoader参数为null,则使用ContextClassLoader
   * <p>
   *  此方法使用以下有序查找过程来确定要加载的XMLInputFactory实现类：
   * </p>
   * <ul>
   * <li>
   *  使用由{@code factoryId}标识的系统属性的值
   * </li>
   * <li>
   *  使用JRE目录中的属性文件"lib / staxproperties"此配置文件采用标准javautilProperties格式,并包含实现类的完全限定名称,键值为给定的{@code factoryId}
   * 。
   * </li>
   * <li>
   * 如果{@code factoryId}是"javaxxmlstreamXMLInputFactory",请使用由{@link javautilServiceLoader}类定义的服务提供商加载工具,尝试
   * {@linkplain javautilServiceLoader#load(javalangClass,javalangClassLoader)locate and load}使用指定{@code ClassLoader}
   * 的服务如果{@code classLoader}为null,则将应用{@linkplain javautilServiceLoader#load(javalangClass)默认加载机制}：即服务提供商
   * 加载工具将使用{ linkplain javalangThread#getContextClassLoader()当前线程的上下文类加载器}尝试加载服务如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}
   * 。
   * </li>
   * <li>
   * 否则,会抛出{@link FactoryConfigurationError}
   * </li>
   * </ul>
   * 
   * <p>
   *  请注意,这是一个新方法,用于替换已弃用的{@link #newInstance(javalangString,javalangClassLoader)newInstance(String factoryId,ClassLoader classLoader)}
   * 方法相对于已弃用的方法,此替换方法未定义行为更改。
   * </p>
   * 
   *  @apiNote这里定义的参数factoryId与其他JAXP工厂的参数不一致,其中第一个参数是完全限定的工厂类名,提供工厂的实现
   * 
   * 
   * @param factoryId             Name of the factory to find, same as
   *                              a property name
   * @param classLoader           classLoader to use
   * @return the factory implementation
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   */
  public static XMLInputFactory newFactory(String factoryId,
          ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLInputFactory.class, factoryId, classLoader, null);
  }

  /**
   * Create a new XMLStreamReader from a reader
   * <p>
   *  从阅读器创建一个新的XMLStreamReader
   * 
   * 
   * @param reader the XML data to read from
   * @throws XMLStreamException
   */
  public abstract XMLStreamReader createXMLStreamReader(java.io.Reader reader)
    throws XMLStreamException;

  /**
   * Create a new XMLStreamReader from a JAXP source.  This method is optional.
   * <p>
   *  从JAXP源创建新的XMLStreamReader此方法是可选的
   * 
   * 
   * @param source the source to read from
   * @throws UnsupportedOperationException if this method is not
   * supported by this XMLInputFactory
   * @throws XMLStreamException
   */
  public abstract XMLStreamReader createXMLStreamReader(Source source)
    throws XMLStreamException;

  /**
   * Create a new XMLStreamReader from a java.io.InputStream
   * <p>
   *  从javaioInputStream创建一个新的XMLStreamReader
   * 
   * 
   * @param stream the InputStream to read from
   * @throws XMLStreamException
   */
  public abstract XMLStreamReader createXMLStreamReader(java.io.InputStream stream)
    throws XMLStreamException;

  /**
   * Create a new XMLStreamReader from a java.io.InputStream
   * <p>
   *  从javaioInputStream创建一个新的XMLStreamReader
   * 
   * 
   * @param stream the InputStream to read from
   * @param encoding the character encoding of the stream
   * @throws XMLStreamException
   */
  public abstract XMLStreamReader createXMLStreamReader(java.io.InputStream stream, String encoding)
    throws XMLStreamException;

  /**
   * Create a new XMLStreamReader from a java.io.InputStream
   * <p>
   * 从javaioInputStream创建一个新的XMLStreamReader
   * 
   * 
   * @param systemId the system ID of the stream
   * @param stream the InputStream to read from
   */
  public abstract XMLStreamReader createXMLStreamReader(String systemId, java.io.InputStream stream)
    throws XMLStreamException;

  /**
   * Create a new XMLStreamReader from a java.io.InputStream
   * <p>
   *  从javaioInputStream创建一个新的XMLStreamReader
   * 
   * 
   * @param systemId the system ID of the stream
   * @param reader the InputStream to read from
   */
  public abstract XMLStreamReader createXMLStreamReader(String systemId, java.io.Reader reader)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from a reader
   * <p>
   *  从阅读器创建一个新的XMLEventReader
   * 
   * 
   * @param reader the XML data to read from
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createXMLEventReader(java.io.Reader reader)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from a reader
   * <p>
   *  从阅读器创建一个新的XMLEventReader
   * 
   * 
   * @param systemId the system ID of the input
   * @param reader the XML data to read from
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createXMLEventReader(String systemId, java.io.Reader reader)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from an XMLStreamReader.  After being used
   * to construct the XMLEventReader instance returned from this method
   * the XMLStreamReader must not be used.
   * <p>
   *  从XMLStreamReader创建新的XMLEventReader在用于构造从此方法返回的XMLEventReader实例之后,不能使用XMLStreamReader
   * 
   * 
   * @param reader the XMLStreamReader to read from (may not be modified)
   * @return a new XMLEventReader
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createXMLEventReader(XMLStreamReader reader)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from a JAXP source.
   * Support of this method is optional.
   * <p>
   *  从JAXP源创建新的XMLEventReader支持此方法是可选的
   * 
   * 
   * @param source the source to read from
   * @throws UnsupportedOperationException if this method is not
   * supported by this XMLInputFactory
   */
  public abstract XMLEventReader createXMLEventReader(Source source)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from a java.io.InputStream
   * <p>
   *  从javaioInputStream创建一个新的XMLEventReader
   * 
   * 
   * @param stream the InputStream to read from
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createXMLEventReader(java.io.InputStream stream)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from a java.io.InputStream
   * <p>
   *  从javaioInputStream创建一个新的XMLEventReader
   * 
   * 
   * @param stream the InputStream to read from
   * @param encoding the character encoding of the stream
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createXMLEventReader(java.io.InputStream stream, String encoding)
    throws XMLStreamException;

  /**
   * Create a new XMLEventReader from a java.io.InputStream
   * <p>
   *  从javaioInputStream创建一个新的XMLEventReader
   * 
   * 
   * @param systemId the system ID of the stream
   * @param stream the InputStream to read from
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createXMLEventReader(String systemId, java.io.InputStream stream)
    throws XMLStreamException;

  /**
   * Create a filtered reader that wraps the filter around the reader
   * <p>
   *  创建一个过滤阅读器,将过滤器包裹在阅读器周围
   * 
   * 
   * @param reader the reader to filter
   * @param filter the filter to apply to the reader
   * @throws XMLStreamException
   */
  public abstract XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter)
    throws XMLStreamException;

  /**
   * Create a filtered event reader that wraps the filter around the event reader
   * <p>
   *  创建一个过滤的事件阅读器,将事件阅读器包围过滤器
   * 
   * 
   * @param reader the event reader to wrap
   * @param filter the filter to apply to the event reader
   * @throws XMLStreamException
   */
  public abstract XMLEventReader createFilteredReader(XMLEventReader reader, EventFilter filter)
    throws XMLStreamException;

  /**
   * The resolver that will be set on any XMLStreamReader or XMLEventReader created
   * by this factory instance.
   * <p>
   * 将在此工厂实例创建的任何XMLStreamReader或XMLEventReader上设置的解析器
   * 
   */
  public abstract XMLResolver getXMLResolver();

  /**
   * The resolver that will be set on any XMLStreamReader or XMLEventReader created
   * by this factory instance.
   * <p>
   *  将在此工厂实例创建的任何XMLStreamReader或XMLEventReader上设置的解析器
   * 
   * 
   * @param resolver the resolver to use to resolve references
   */
  public abstract void  setXMLResolver(XMLResolver resolver);

  /**
   * The reporter that will be set on any XMLStreamReader or XMLEventReader created
   * by this factory instance.
   * <p>
   *  将对由此工厂实例创建的任何XMLStreamReader或XMLEventReader设置的记录器
   * 
   */
  public abstract XMLReporter getXMLReporter();

  /**
   * The reporter that will be set on any XMLStreamReader or XMLEventReader created
   * by this factory instance.
   * <p>
   *  将对由此工厂实例创建的任何XMLStreamReader或XMLEventReader设置的记录器
   * 
   * 
   * @param reporter the resolver to use to report non fatal errors
   */
  public abstract void setXMLReporter(XMLReporter reporter);

  /**
   * Allows the user to set specific feature/property on the underlying
   * implementation. The underlying implementation is not required to support
   * every setting of every property in the specification and may use
   * IllegalArgumentException to signal that an unsupported property may not be
   * set with the specified value.
   * <p>
   * All implementations that implement JAXP 1.5 or newer are required to
   * support the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property.
   * </p>
   * <ul>
   *   <li>
   *        <p>
   *        Access to external DTDs, external Entity References is restricted to the
   *        protocols specified by the property. If access is denied during parsing
   *        due to the restriction of this property, {@link javax.xml.stream.XMLStreamException}
   *        will be thrown by the {@link javax.xml.stream.XMLStreamReader#next()} or
   *        {@link javax.xml.stream.XMLEventReader#nextEvent()} method.
   *        </p>
   *   </li>
   * </ul>
   * <p>
   *  允许用户设置底层实现的特定功能/属性底层实现不需要支持规范中每个属性的每个设置,并且可能使用IllegalArgumentException表示可能不使用指定值设置不受支持的属性
   * <p>
   * 实现JAXP 15或更高版本的所有实现都需要支持{@link javaxxmlXMLConstants#ACCESS_EXTERNAL_DTD}属性
   * </p>
   * <ul>
   * <li>
   * <p>
   *  访问外部DTD,外部实体引用仅限于属性指定的协议如果由于此属性的限制而在解析期间拒绝访问,{@link javaxxmlstreamXMLStreamException}将由{@link javaxxmlstreamXMLStreamReader#next()}
   * 抛出,或{@link javaxxmlstreamXMLEventReader#nextEvent()}方法。
   * </p>
   * </li>
   * </ul>
   * 
   * @param name The name of the property (may not be null)
   * @param value The value of the property
   * @throws java.lang.IllegalArgumentException if the property is not supported
   */
  public abstract void setProperty(java.lang.String name, Object value)
    throws java.lang.IllegalArgumentException;

  /**
   * Get the value of a feature/property from the underlying implementation
   * <p>
   * 
   * @param name The name of the property (may not be null)
   * @return The value of the property
   * @throws IllegalArgumentException if the property is not supported
   */
  public abstract Object getProperty(java.lang.String name)
    throws java.lang.IllegalArgumentException;


  /**
   * Query the set of properties that this factory supports.
   *
   * <p>
   *  从底层实现获取要素/属性的值
   * 
   * 
   * @param name The name of the property (may not be null)
   * @return true if the property is supported and false otherwise
   */
  public abstract boolean isPropertySupported(String name);

  /**
   * Set a user defined event allocator for events
   * <p>
   *  查询此工厂支持的属性集
   * 
   * 
   * @param allocator the user defined allocator
   */
  public abstract void setEventAllocator(XMLEventAllocator allocator);

  /**
   * Gets the allocator used by streams created with this factory
   * <p>
   *  为事件设置用户定义的事件分配器
   * 
   */
  public abstract XMLEventAllocator getEventAllocator();

}
