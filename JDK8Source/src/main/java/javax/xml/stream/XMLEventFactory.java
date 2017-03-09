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
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.*;
/**
 * This interface defines a utility class for creating instances of
 * XMLEvents
 * <p>
 *  此接口定义了用于创建XMLEvents实例的实用程序类
 * 
 * 
 * @version 1.2
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see javax.xml.stream.events.StartElement
 * @see javax.xml.stream.events.EndElement
 * @see javax.xml.stream.events.ProcessingInstruction
 * @see javax.xml.stream.events.Comment
 * @see javax.xml.stream.events.Characters
 * @see javax.xml.stream.events.StartDocument
 * @see javax.xml.stream.events.EndDocument
 * @see javax.xml.stream.events.DTD
 * @since 1.6
 */
public abstract class XMLEventFactory {
  protected XMLEventFactory(){}

    static final String JAXPFACTORYID = "javax.xml.stream.XMLEventFactory";
    static final String DEFAULIMPL = "com.sun.xml.internal.stream.events.XMLEventFactoryImpl";


  /**
   * Creates a new instance of the factory in exactly the same manner as the
   * {@link #newFactory()} method.
   * <p>
   *  以与{@link #newFactory()}方法完全相同的方式创建工厂的新实例。
   * 
   * 
   * @throws FactoryConfigurationError if an instance of this factory cannot be loaded
   */
  public static XMLEventFactory newInstance()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLEventFactory.class, DEFAULIMPL);
  }

  /**
   * Create a new instance of the factory.
   * <p>
   * This static method creates a new factory instance.
   * This method uses the following ordered lookup procedure to determine
   * the XMLEventFactory implementation class to load:
   * </p>
   * <ul>
   * <li>
   *   Use the javax.xml.stream.XMLEventFactory system property.
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
   * </ul>
   * <p>
   *   Once an application has obtained a reference to a XMLEventFactory it
   *   can use the factory to configure and obtain stream instances.
   * </p>
   * <p>
   *   Note that this is a new method that replaces the deprecated newInstance() method.
   *     No changes in behavior are defined by this replacement method relative to
   *     the deprecated method.
   * </p>
   * <p>
   *  创建一个新的工厂实例。
   * <p>
   *  这个静态方法创建一个新的工厂实例。此方法使用以下有序查找过程来确定要加载的XMLEventFactory实现类：
   * </p>
   * <ul>
   * <li>
   *  使用javax.xml.stream.XMLEventFactory系统属性。
   * </li>
   * <li>
   *  使用JRE目录中的属性文件"lib / stax.properties"。此配置文件采用标准java.util.Properties格式,并包含实现类的完全限定名称,其中键是上面定义的系统属性。
   * </li>
   * <li>
   *  使用由{@link java.util.ServiceLoader}类定义的服务提供程序加载工具,尝试使用{@linkplain java.util.ServiceLoader#load(java.lang。
   * )来定位和加载服务的实现。
   * 类)默认加载机制}：service-provider加载工具将使用{@linkplain java.lang.Thread#getContextClassLoader()当前线程的上下文类加载器}来尝试
   * 加载服务。
   * )来定位和加载服务的实现。如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
   * </li>
   * <li>
   *  否则,返回系统默认实现。
   * </li>
   * </ul>
   * <p>
   * 一旦应用程序获得对XMLEventFactory的引用,它可以使用工厂来配置和获取流实例。
   * </p>
   * <p>
   *  请注意,这是一种替换已弃用的newInstance()方法的新方法。相对于已弃用的方法,此替换方法未定义行为更改。
   * </p>
   * 
   * @throws FactoryConfigurationError in case of {@linkplain
   *   java.util.ServiceConfigurationError service configuration error} or if
   *   the implementation is not available or cannot be instantiated.
   */
  public static XMLEventFactory newFactory()
    throws FactoryConfigurationError
  {
    return FactoryFinder.find(XMLEventFactory.class, DEFAULIMPL);
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
   *              #newFactory(java.lang.String, java.lang.ClassLoader)}
   *              method defines no changes in behavior.
   */
  public static XMLEventFactory newInstance(String factoryId,
          ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLEventFactory.class, factoryId, classLoader, null);
  }

  /**
   * Create a new instance of the factory.
   * If the classLoader argument is null, then the ContextClassLoader is used.
   * <p>
   * This method uses the following ordered lookup procedure to determine
   * the XMLEventFactory implementation class to load:
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
   *   If {@code factoryId} is "javax.xml.stream.XMLEventFactory",
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
   *  创建一个新的工厂实例。如果classLoader参数为null,那么使用ContextClassLoader。
   * <p>
   *  此方法使用以下有序查找过程来确定要加载的XMLEventFactory实现类：
   * </p>
   * <ul>
   * <li>
   *  使用由{@code factoryId}标识的系统属性的值。
   * </li>
   * <li>
   *  使用JRE目录中的属性文件"lib / stax.properties"。
   * 此配置文件采用标准java.util.Properties格式,并包含实现类的完全限定名称,键值为给定的{@code factoryId}。
   * </li>
   * <li>
   * 如果{@code factoryId}是"javax.xml.stream.XMLEventFactory",请使用{@link java.util.ServiceLoader}类定义的服务提供商加载工
   * 具尝试{@linkplain java.util.ServiceLoader #load(java.lang.Class,java.lang.ClassLoader)locate和load}使用指定的{@code ClassLoader}
   * 的服务实现。
   * 如果{@code classLoader}为null,{@linkplain java.util.ServiceLoader#load(java.lang.Class)默认加载机制}将适用：也就是说,s
   * ervice-provider加载工具将使用{@linkplain java .lang.Thread#getContextClassLoader()当前线程的上下文类加载器}尝试加载服务。
   * 如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
   * </li>
   * <li>
   *  否则,会抛出{@link FactoryConfigurationError}。
   * </li>
   * </ul>
   * 
   * <p>
   *  请注意,这是一种替换已弃用的{@link #newInstance(java.lang.String,java.lang.ClassLoader)newInstance(String factoryId,ClassLoader classLoader)}
   * 方法的新方法。
   * 相对于已弃用的方法,此替换方法未定义行为更改。
   * </p>
   * 
   *  @apiNote这里定义的参数factoryId与其他JAXP工厂的参数不一致,其中第一个参数是提供工厂实现的完全限定的工厂类名。
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
  public static XMLEventFactory newFactory(String factoryId,
                                           ClassLoader classLoader)
          throws FactoryConfigurationError {
      //do not fallback if given classloader can't find the class, throw exception
      return FactoryFinder.find(XMLEventFactory.class, factoryId, classLoader, null);
  }

 /**
   * This method allows setting of the Location on each event that
   * is created by this factory.  The values are copied by value into
   * the events created by this factory.  To reset the location
   * information set the location to null.
   * <p>
   * 此方法允许在此工厂创建的每个事件上设置位置。值将按值复制到此工厂创建的事件中。要重置位置信息,请将位置设置为null。
   * 
   * 
   * @param location the location to set on each event created
   */
  public abstract void setLocation(Location location);

  /**
   * Create a new Attribute
   * <p>
   *  创建新属性
   * 
   * 
   * @param prefix the prefix of this attribute, may not be null
   * @param namespaceURI the attribute value is set to this value, may not be null
   * @param localName the local name of the XML name of the attribute, localName cannot be null
   * @param value the attribute value to set, may not be null
   * @return the Attribute with specified values
   */
  public abstract Attribute createAttribute(String prefix, String namespaceURI, String localName, String value);

  /**
   * Create a new Attribute
   * <p>
   *  创建新属性
   * 
   * 
   * @param localName the local name of the XML name of the attribute, localName cannot be null
   * @param value the attribute value to set, may not be null
   * @return the Attribute with specified values
   */
  public abstract Attribute createAttribute(String localName, String value);

  /**
   * Create a new Attribute
   * <p>
   *  创建新属性
   * 
   * 
   * @param name the qualified name of the attribute, may not be null
   * @param value the attribute value to set, may not be null
   * @return the Attribute with specified values
   */
  public abstract Attribute createAttribute(QName name, String value);

  /**
   * Create a new default Namespace
   * <p>
   *  创建新的默认命名空间
   * 
   * 
   * @param namespaceURI the default namespace uri
   * @return the Namespace with the specified value
   */
  public abstract Namespace createNamespace(String namespaceURI);

  /**
   * Create a new Namespace
   * <p>
   *  创建一个新的命名空间
   * 
   * 
   * @param prefix the prefix of this namespace, may not be null
   * @param namespaceUri the attribute value is set to this value, may not be null
   * @return the Namespace with the specified values
   */
  public abstract Namespace createNamespace(String prefix, String namespaceUri);

  /**
   * Create a new StartElement.  Namespaces can be added to this StartElement
   * by passing in an Iterator that walks over a set of Namespace interfaces.
   * Attributes can be added to this StartElement by passing an iterator
   * that walks over a set of Attribute interfaces.
   *
   * <p>
   *  创建一个新的StartElement。通过传递一个遍历一组Namespace接口的Iterator,可以将命名空间添加到此StartElement。
   * 通过传递一个遍历一组属性接口的迭代器,可以将属性添加到此StartElement。
   * 
   * 
   * @param name the qualified name of the attribute, may not be null
   * @param attributes an optional unordered set of objects that
   * implement Attribute to add to the new StartElement, may be null
   * @param namespaces an optional unordered set of objects that
   * implement Namespace to add to the new StartElement, may be null
   * @return an instance of the requested StartElement
   */
  public abstract StartElement createStartElement(QName name,
                                                  Iterator attributes,
                                                  Iterator namespaces);

  /**
   * Create a new StartElement.  This defaults the NamespaceContext to
   * an empty NamespaceContext.  Querying this event for its namespaces or
   * attributes will result in an empty iterator being returned.
   *
   * <p>
   *  创建一个新的StartElement。这会将NamespaceContext默认为一个空的NamespaceContext。为其命名空间或属性查询此事件将导致返回一个空的迭代器。
   * 
   * 
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @return an instance of the requested StartElement
   */
  public abstract StartElement createStartElement(String prefix,
                                                  String namespaceUri,
                                                  String localName);
  /**
   * Create a new StartElement.  Namespaces can be added to this StartElement
   * by passing in an Iterator that walks over a set of Namespace interfaces.
   * Attributes can be added to this StartElement by passing an iterator
   * that walks over a set of Attribute interfaces.
   *
   * <p>
   *  创建一个新的StartElement。通过传递一个遍历一组Namespace接口的Iterator,可以将命名空间添加到此StartElement。
   * 通过传递一个遍历一组属性接口的迭代器,可以将属性添加到此StartElement。
   * 
   * 
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @param attributes an unordered set of objects that implement
   * Attribute to add to the new StartElement
   * @param namespaces an unordered set of objects that implement
   * Namespace to add to the new StartElement
   * @return an instance of the requested StartElement
   */
  public abstract StartElement createStartElement(String prefix,
                                                  String namespaceUri,
                                                  String localName,
                                                  Iterator attributes,
                                                  Iterator namespaces
                                                  );
  /**
   * Create a new StartElement.  Namespaces can be added to this StartElement
   * by passing in an Iterator that walks over a set of Namespace interfaces.
   * Attributes can be added to this StartElement by passing an iterator
   * that walks over a set of Attribute interfaces.
   *
   * <p>
   *  创建一个新的StartElement。通过传递一个遍历一组Namespace接口的Iterator,可以将命名空间添加到此StartElement。
   * 通过传递一个遍历一组属性接口的迭代器,可以将属性添加到此StartElement。
   * 
   * 
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @param attributes an unordered set of objects that implement
   * Attribute to add to the new StartElement, may be null
   * @param namespaces an unordered set of objects that implement
   * Namespace to add to the new StartElement, may be null
   * @param context the namespace context of this element
   * @return an instance of the requested StartElement
   */
  public abstract StartElement createStartElement(String prefix,
                                                  String namespaceUri,
                                                  String localName,
                                                  Iterator attributes,
                                                  Iterator namespaces,
                                                  NamespaceContext context
                                                  );

  /**
   * Create a new EndElement
   * <p>
   *  创建一个新的EndElement
   * 
   * 
   * @param name the qualified name of the EndElement
   * @param namespaces an optional unordered set of objects that
   * implement Namespace that have gone out of scope, may be null
   * @return an instance of the requested EndElement
   */
  public abstract EndElement createEndElement(QName name,
                                              Iterator namespaces);

  /**
   * Create a new EndElement
   * <p>
   *  创建一个新的EndElement
   * 
   * 
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @return an instance of the requested EndElement
   */
  public abstract EndElement createEndElement(String prefix,
                                              String namespaceUri,
                                              String localName);
  /**
   * Create a new EndElement
   * <p>
   *  创建一个新的EndElement
   * 
   * 
   * @param namespaceUri the uri of the QName of the new StartElement
   * @param localName the local name of the QName of the new StartElement
   * @param prefix the prefix of the QName of the new StartElement
   * @param namespaces an unordered set of objects that implement
   * Namespace that have gone out of scope, may be null
   * @return an instance of the requested EndElement
   */
  public abstract EndElement createEndElement(String prefix,
                                              String namespaceUri,
                                              String localName,
                                              Iterator namespaces);

  /**
   * Create a Characters event, this method does not check if the content
   * is all whitespace.  To create a space event use #createSpace(String)
   * <p>
   * 创建一个Characters事件,此方法不检查内容是否全部为空格。要创建空间事件,请使用#createSpace(String)
   * 
   * 
   * @param content the string to create
   * @return a Characters event
   */
  public abstract Characters createCharacters(String content);

  /**
   * Create a Characters event with the CData flag set to true
   * <p>
   *  创建一个CData标志设置为true的Characters事件
   * 
   * 
   * @param content the string to create
   * @return a Characters event
   */
  public abstract Characters createCData(String content);

  /**
   * Create a Characters event with the isSpace flag set to true
   * <p>
   *  创建一个字符事件,其中isSpace标志设置为true
   * 
   * 
   * @param content the content of the space to create
   * @return a Characters event
   */
  public abstract Characters createSpace(String content);
  /**
   * Create an ignorable space
   * <p>
   *  创建一个可忽略的空间
   * 
   * 
   * @param content the space to create
   * @return a Characters event
   */
  public abstract Characters createIgnorableSpace(String content);

  /**
   * Creates a new instance of a StartDocument event
   * <p>
   *  创建一个StartDocument事件的新实例
   * 
   * 
   * @return a StartDocument event
   */
  public abstract StartDocument createStartDocument();

  /**
   * Creates a new instance of a StartDocument event
   *
   * <p>
   *  创建一个StartDocument事件的新实例
   * 
   * 
   * @param encoding the encoding style
   * @param version the XML version
   * @param standalone the status of standalone may be set to "true" or "false"
   * @return a StartDocument event
   */
  public abstract StartDocument createStartDocument(String encoding,
                                                  String version,
                                                  boolean standalone);

  /**
   * Creates a new instance of a StartDocument event
   *
   * <p>
   *  创建一个StartDocument事件的新实例
   * 
   * 
   * @param encoding the encoding style
   * @param version the XML version
   * @return a StartDocument event
   */
  public abstract StartDocument createStartDocument(String encoding,
                                                  String version);

  /**
   * Creates a new instance of a StartDocument event
   *
   * <p>
   *  创建一个StartDocument事件的新实例
   * 
   * 
   * @param encoding the encoding style
   * @return a StartDocument event
   */
  public abstract StartDocument createStartDocument(String encoding);

  /**
   * Creates a new instance of an EndDocument event
   * <p>
   *  创建EndDocument事件的新实例
   * 
   * 
   * @return an EndDocument event
   */
  public abstract EndDocument createEndDocument();

  /** Creates a new instance of a EntityReference event
   *
   * <p>
   * 
   * @param name The name of the reference
   * @param declaration the declaration for the event
   * @return an EntityReference event
   */
  public abstract EntityReference createEntityReference(String name,
                                                        EntityDeclaration declaration);
  /**
   * Create a comment
   * <p>
   *  创建评论
   * 
   * 
   * @param text The text of the comment
   * a Comment event
   */
  public abstract Comment createComment(String text);

  /**
   * Create a processing instruction
   * <p>
   *  创建处理指令
   * 
   * 
   * @param target The target of the processing instruction
   * @param data The text of the processing instruction
   * @return a ProcessingInstruction event
   */
  public abstract ProcessingInstruction createProcessingInstruction(String target,
                                                                   String data);

  /**
   * Create a document type definition event
   * This string contains the entire document type declaration that matches
   * the doctypedecl in the XML 1.0 specification
   * <p>
   *  创建文档类型定义事件此字符串包含与XML 1.0规范中的doctypedecl匹配的整个文档类型声明
   * 
   * @param dtd the text of the document type definition
   * @return a DTD event
   */
  public abstract DTD createDTD(String dtd);
}
