/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * The <tt>JAXBContext</tt> class provides the client's entry point to the
 * JAXB API. It provides an abstraction for managing the XML/Java binding
 * information necessary to implement the JAXB binding framework operations:
 * unmarshal, marshal and validate.
 *
 * <p>A client application normally obtains new instances of this class using
 * one of these two styles for newInstance methods, although there are other
 * specialized forms of the method available:
 *
 * <ul>
 *   <li>{@link #newInstance(String,ClassLoader) JAXBContext.newInstance( "com.acme.foo:com.acme.bar" )} <br/>
 *   The JAXBContext instance is initialized from a list of colon
 *   separated Java package names. Each java package contains
 *   JAXB mapped classes, schema-derived classes and/or user annotated
 *   classes. Additionally, the java package may contain JAXB package annotations
 *   that must be processed. (see JLS, Section 7.4.1 "Named Packages").
 *   </li>
 *   <li>{@link #newInstance(Class...) JAXBContext.newInstance( com.acme.foo.Foo.class )} <br/>
 *    The JAXBContext instance is initialized with class(es)
 *    passed as parameter(s) and classes that are statically reachable from
 *    these class(es). See {@link #newInstance(Class...)} for details.
 *   </li>
 * </ul>
 *
 * <p>
 * <i><B>SPEC REQUIREMENT:</B> the provider must supply an implementation
 * class containing the following method signatures:</i>
 *
 * <pre>
 * public static JAXBContext createContext( String contextPath, ClassLoader classLoader, Map&lt;String,Object> properties ) throws JAXBException
 * public static JAXBContext createContext( Class[] classes, Map&lt;String,Object> properties ) throws JAXBException
 * </pre>
 *
 * <p><i>
 * The following JAXB 1.0 requirement is only required for schema to
 * java interface/implementation binding. It does not apply to JAXB annotated
 * classes. JAXB Providers must generate a <tt>jaxb.properties</tt> file in
 * each package containing schema derived classes.  The property file must
 * contain a property named <tt>javax.xml.bind.context.factory</tt> whose
 * value is the name of the class that implements the <tt>createContext</tt>
 * APIs.</i>
 *
 * <p><i>
 * The class supplied by the provider does not have to be assignable to
 * <tt>javax.xml.bind.JAXBContext</tt>, it simply has to provide a class that
 * implements the <tt>createContext</tt> APIs.</i>
 *
 * <p><i>
 * In addition, the provider must call the
 * {@link DatatypeConverter#setDatatypeConverter(DatatypeConverterInterface)
 * DatatypeConverter.setDatatypeConverter} api prior to any client
 * invocations of the marshal and unmarshal methods.  This is necessary to
 * configure the datatype converter that will be used during these operations.</i>
 *
 * <a name="Unmarshalling"></a>
 * <h3>Unmarshalling</h3>
 * <p>
 * The {@link Unmarshaller} class provides the client application the ability
 * to convert XML data into a tree of Java content objects.
 * The unmarshal method allows for
 * any global XML element declared in the schema to be unmarshalled as
 * the root of an instance document.
 * Additionally, the unmarshal method allows for an unrecognized root element that
 * has  an xsi:type attribute's value that references a type definition declared in
 * the schema  to be unmarshalled as the root of an instance document.
 * The <tt>JAXBContext</tt> object
 * allows the merging of global elements and type definitions across a set of schemas (listed
 * in the <tt>contextPath</tt>). Since each schema in the schema set can belong
 * to distinct namespaces, the unification of schemas to an unmarshalling
 * context should be namespace independent.  This means that a client
 * application is able to unmarshal XML documents that are instances of
 * any of the schemas listed in the <tt>contextPath</tt>.  For example:
 *
 * <pre>
 *        JAXBContext jc = JAXBContext.newInstance( "com.acme.foo:com.acme.bar" );
 *        Unmarshaller u = jc.createUnmarshaller();
 *        FooObject fooObj = (FooObject)u.unmarshal( new File( "foo.xml" ) ); // ok
 *        BarObject barObj = (BarObject)u.unmarshal( new File( "bar.xml" ) ); // ok
 *        BazObject bazObj = (BazObject)u.unmarshal( new File( "baz.xml" ) ); // error, "com.acme.baz" not in contextPath
 * </pre>
 *
 * <p>
 * The client application may also generate Java content trees explicitly rather
 * than unmarshalling existing XML data.  For all JAXB-annotated value classes,
 * an application can create content using constructors.
 * For schema-derived interface/implementation classes and for the
 * creation of elements that are not bound to a JAXB-annotated
 * class, an application needs to have access and knowledge about each of
 * the schema derived <tt> ObjectFactory</tt> classes that exist in each of
 * java packages contained in the <tt>contextPath</tt>.  For each schema
 * derived java class, there is a static factory method that produces objects
 * of that type.  For example,
 * assume that after compiling a schema, you have a package <tt>com.acme.foo</tt>
 * that contains a schema derived interface named <tt>PurchaseOrder</tt>.  In
 * order to create objects of that type, the client application would use the
 * factory method like this:
 *
 * <pre>
 *       com.acme.foo.PurchaseOrder po =
 *           com.acme.foo.ObjectFactory.createPurchaseOrder();
 * </pre>
 *
 * <p>
 * Once the client application has an instance of the the schema derived object,
 * it can use the mutator methods to set content on it.
 *
 * <p>
 * For more information on the generated <tt>ObjectFactory</tt> classes, see
 * Section 4.2 <i>Java Package</i> of the specification.
 *
 * <p>
 * <i><B>SPEC REQUIREMENT:</B> the provider must generate a class in each
 * package that contains all of the necessary object factory methods for that
 * package named ObjectFactory as well as the static
 * <tt>newInstance( javaContentInterface )</tt> method</i>
 *
 * <h3>Marshalling</h3>
 * <p>
 * The {@link Marshaller} class provides the client application the ability
 * to convert a Java content tree back into XML data.  There is no difference
 * between marshalling a content tree that is created manually using the factory
 * methods and marshalling a content tree that is the result an <tt>unmarshal
 * </tt> operation.  Clients can marshal a java content tree back to XML data
 * to a <tt>java.io.OutputStream</tt> or a <tt>java.io.Writer</tt>.  The
 * marshalling process can alternatively produce SAX2 event streams to a
 * registered <tt>ContentHandler</tt> or produce a DOM Node object.
 * Client applications have control over the output encoding as well as
 * whether or not to marshal the XML data as a complete document or
 * as a fragment.
 *
 * <p>
 * Here is a simple example that unmarshals an XML document and then marshals
 * it back out:
 *
 * <pre>
 *        JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *
 *        // unmarshal from foo.xml
 *        Unmarshaller u = jc.createUnmarshaller();
 *        FooObject fooObj = (FooObject)u.unmarshal( new File( "foo.xml" ) );
 *
 *        // marshal to System.out
 *        Marshaller m = jc.createMarshaller();
 *        m.marshal( fooObj, System.out );
 * </pre>
 *
 *
 * <h3>Validation</h3>
 * <p>
 * Validation has been changed significantly since JAXB 1.0.  The {@link Validator}
 * class has been deprecated and made optional.  This means that you are advised
 * not to use this class and, in fact, it may not even be available depending on
 * your JAXB provider.  JAXB 1.0 client applications that rely on <tt>Validator</tt>
 * will still work properly when deployed with the JAXB 1.0 runtime system.
 *
 * In JAXB 2.0, the {@link Unmarshaller} has included convenince methods that expose
 * the JAXP 1.3 {@link javax.xml.validation} framework.  Please refer to the
 * {@link Unmarshaller#setSchema(javax.xml.validation.Schema)} API for more
 * information.
 *
 *
 * <h3>JAXB Runtime Binding Framework Compatibility</h3>
 * <p>
 * The following JAXB 1.0 restriction only applies to binding schema to
 * interfaces/implementation classes.
 * Since this binding does not require a common runtime system, a JAXB
 * client application must not attempt to mix runtime objects (<tt>JAXBContext,
 * Marshaller</tt>, etc. ) from different providers.  This does not
 * mean that the client application isn't portable, it simply means that a
 * client has to use a runtime system provided by the same provider that was
 * used to compile the schema.
 *
 *
 * <h3>Discovery of JAXB implementation</h3>
 * <p>
 * When one of the <tt>newInstance</tt> methods is called, a JAXB implementation is discovered
 * by the following steps.
 *
 * <ol>
 * <li>
 * For each package/class explicitly passed in to the {@link #newInstance} method, in the order they are specified,
 * <tt>jaxb.properties</tt> file is looked up in its package, by using the associated classloader &mdash;
 * this is {@link Class#getClassLoader() the owner class loader} for a {@link Class} argument, and for a package
 * the specified {@link ClassLoader}.
 *
 * <p>
 * If such a file is discovered, it is {@link Properties#load(InputStream) loaded} as a property file, and
 * the value of the {@link #JAXB_CONTEXT_FACTORY} key will be assumed to be the provider factory class.
 * This class is then loaded by the associated classloader discussed above.
 *
 * <p>
 * This phase of the look up allows some packages to force the use of a certain JAXB implementation.
 * (For example, perhaps the schema compiler has generated some vendor extension in the code.)
 *
 * <li>
 * If the system property {@link #JAXB_CONTEXT_FACTORY} exists, then its value is assumed to be the provider
 * factory class. This phase of the look up enables per-JVM override of the JAXB implementation.
 *
 * <li>
 * Look for <tt>/META-INF/services/javax.xml.bind.JAXBContext</tt> file in the associated classloader.
 * This file follows the standard service descriptor convention, and if such a file exists, its content
 * is assumed to be the provider factory class. This phase of the look up is for automatic discovery.
 * It allows users to just put a JAXB implementation in a classpath and use it without any furhter configuration.
 *
 * <li>
 * Finally, if all the steps above fail, then the rest of the look up is unspecified. That said,
 * the recommended behavior is to simply look for some hard-coded platform default JAXB implementation.
 * This phase of the look up is so that JavaSE can have its own JAXB implementation as the last resort.
 * </ol>
 *
 * <p>
 * Once the provider factory class is discovered, its
 * <tt>public static JAXBContext createContext(String,ClassLoader,Map)</tt> method
 * (see {@link #newInstance(String, ClassLoader, Map)} for the parameter semantics.)
 * or <tt>public static JAXBContext createContet(Class[],Map)</tt> method
 * (see {@link #newInstance(Class[], Map)} for the parameter semantics) are invoked
 * to create a {@link JAXBContext}.
 *
 * <p>
 * <p>
 *  <tt> JAXBContext </tt>类提供客户端对JAXB API的入口点。
 * 它提供了一个用于管理实现JAXB绑定框架操作所需的XML / Java绑定信息的抽象：unmarshal,marshal和validate。
 * 
 *  <p>客户端应用程序通常使用newInstance方法的这两种样式之一获取此类的新实例,但有可用的其他特殊形式的方法：
 * 
 * <ul>
 *  <li> {@ link #newInstance(String,ClassLoader)JAXBContext.newInstance("com.acme.foo:com.acme.bar")} <br/>
 *  JAXBContext实例从冒号分隔的Java包名列表中初始化。
 * 每个java包都包含JAXB映射类,模式派生类和/或用户注释类。另外,java包可能包含必须处理的JAXB包注释。 (参见JLS,第7.4.1节"命名包")。
 * </li>
 *  <li> {@ link #newInstance(Class ...)JAXBContext.newInstance(com.acme.foo.Foo.class)} <br/>使用作为参数传递的类
 * (es)初始化JAXBContext实例,从这些类静态可达的类。
 * 有关详细信息,请参阅{@link #newInstance(Class ...)}。
 * </li>
 * </ul>
 * 
 * <p>
 *  <i> <B> SPEC REQUIREMENT：</B>提供程序必须提供包含以下方法签名的实现类：</i>
 * 
 * <pre>
 * public static JAXBContext createContext(String contextPath,ClassLoader classLoader,Map&lt; String,Obj
 * ect> properties)throws JAXBException public static JAXBContext createContext(Class [] classes,Map&lt;
 *  String,Object> properties)throws JAXBException。
 * </pre>
 * 
 *  <p> <i>以下JAXB 1.0需求仅适用于模式到java接口/实现绑定。它不适用于JAXB注释类。
 *  JAXB提供程序必须在包含模式派生类的每个包中生成一个<tt> jaxb.properties </tt>文件。
 * 属性文件必须包含名为<tt> javax.xml.bind.context.factory </tt>的属性,其值是实现<tt> createContext </tt> API的类的名称。</i>。
 * 
 *  <p> <i>提供者提供的类不必分配给<tt> javax.xml.bind.JAXBContext </tt>,它只需提供一个实现<tt> createContext </tt> tt> API。
 * </i>。
 * 
 *  <p> <i>此外,提供程序必须在任何客户端调用marshal和unmarshal方法之前调用{@link DatatypeConverter#setDatatypeConverter(DatatypeConverterInterface)DatatypeConverter.setDatatypeConverter}
 *  api。
 * 这是配置将在这些操作期间使用的数据类型转换器所必需的。</i>。
 * 
 *  <a name="Unmarshalling"> </a> <h3>取消编组</h3>
 * <p>
 * {@link Unmarshaller}类为客户端应用程序提供了将XML数据转换为Java内容对象树的功能。解组方法允许将在模式中声明的任何全局XML元素解组为实例文档的根。
 * 此外,解组方法允许具有xsi：type属性值的无法识别的根元素引用在模式中声明的类型定义作为实例文档的根解除组织。
 *  <tt> JAXBContext </tt>对象允许跨一组模式(在<tt> contextPath </tt>中列出)合并全局元素和类型定义。
 * 由于模式集中的每个模式都可以属于不同的命名空间,因此模式与解组上下文的统一应该与命名空间无关。
 * 这意味着客户端应用程序能够解组是<tt> contextPath </tt>中列出的任何模式的实例的XML文档。例如：。
 * 
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo:com.acme.bar"); Unmarshaller u = jc.createUnm
 * arshaller(); FooObject fooObj =(FooObject)u.unmarshal(new File("foo.xml")); // ok BarObject barObj =(
 * BarObject)u.unmarshal(new File("bar.xml")); // ok BazObject bazObj =(BazObject)u.unmarshal(new File("
 * baz.xml")); //错误,"com.acme.baz"不在contextPath中。
 * </pre>
 * 
 * <p>
 * 客户端应用程序还可以显式地生成Java内容树,而不是解组现有XML数据。对于所有JAXB注释的值类,应用程序可以使用构造函数创建内容。
 * 对于模式派生的接口/实现类以及创建未绑定到JAXB注释类的元素,应用程序需要具有对存在的每个模式派生<tt> ObjectFactory </tt>类的访问和知识在<tt> contextPath </tt>
 * 中包含的每个Java包中。
 * 客户端应用程序还可以显式地生成Java内容树,而不是解组现有XML数据。对于所有JAXB注释的值类,应用程序可以使用构造函数创建内容。
 * 对于每个模式派生的java类,都有一个静态工厂方法来生成该类型的对象。
 * 例如,假设在编译模式之后,您有一个包<tt> com.acme.foo </tt>,包含名为<tt> PurchaseOrder </tt>的模式派生接口。
 * 为了创建该类型的对象,客户端应用程序将使用如下的工厂方法：。
 * 
 * <pre>
 *  com.acme.foo.PurchaseOrder po = com.acme.foo.ObjectFactory.createPurchaseOrder();
 * </pre>
 * 
 * <p>
 *  一旦客户端应用程序具有模式派生对象的实例,它就可以使用mutator方法在其上设置内容。
 * 
 * <p>
 *  有关生成的<tt> ObjectFactory </tt>类的详细信息,请参见规范的第4.2节"Java包"。
 * 
 * <p>
 * <i> <B> SPEC REQUIREMENT：</B>提供程序必须在每个包中生成一个类,该包包含名为ObjectFactory的包所有必需的对象工厂方法以及静态<tt> newInstance(ja
 * vaContentInterface)</tt>方法</i>。
 * 
 *  <h3>编组</h3>
 * <p>
 *  {@link Marshaller}类为客户端应用程序提供了将Java内容树转换回XML数据的能力。
 * 编组使用工厂方法手动创建的内容树和编组作为<tt>取消组合</tt>操作的结果的内容树之间没有区别。
 * 客户端可以将java内容树编组回XML数据到<tt> java.io.OutputStream </tt>或<tt> java.io.Writer </tt>。
 * 编组过程可以替代地将SAX2事件流产生到注册的<Tt> ContentHandler </tt>或产生DOM节点对象。客户端应用程序可以控制输出编码以及是否将XML数据编组为完整文档或片段。
 * 
 * <p>
 *  这里是一个简单的例子,解组一个XML文档,然后编组它：
 * 
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo");
 * 
 *  // unmarshal from foo.xml Unmarshaller u = jc.createUnmarshaller(); FooObject fooObj =(FooObject)u.u
 * nmarshal(new File("foo.xml"));。
 * 
 *  // marshal to System.out Marshaller m = jc.createMarshaller(); m.marshal(fooObj,System.out);
 * </pre>
 * 
 *  <h3>验证</h3>
 * <p>
 * 验证自JAXB 1.0以来发生了显着变化。 {@link Validator}类已弃用,并且是可选的。这意味着建议您不要使用此类,实际上,根据您的JAXB提供程序,它甚至可能不可用。
 * 依赖于<tt> Validator </tt>的JAXB 1.0客户端应用程序在与JAXB 1.0运行时系统一起部署时仍然可以正常工作。
 * 
 *  在JAXB 2.0中,{@link Unmarshaller}包括了揭示JAXP 1.3 {@link javax.xml.validation}框架的方法。
 * 有关详细信息,请参阅{@link Unmarshaller#setSchema(javax.xml.validation.Schema)} API。
 * 
 *  <h3> JAXB运行时绑定框架兼容性</h3>
 * <p>
 *  以下JAXB 1.0限制仅适用于将绑定模式应用于接口/实现类。
 * 由于此绑定不需要公共运行时系统,因此JAXB客户端应用程序不得尝试混合来自不同提供程序的运行时对象(<tt> JAXBContext,Marshaller </tt>等)。
 * 这并不意味着客户端应用程序不可移植,它只是意味着客户端必须使用由用于编译模式的同一提供程序提供的运行时系统。
 * 
 *  <h3> JAXB实现的发现</h3>
 * <p>
 *  当调用<tt> newInstance </tt>方法之一时,将通过以下步骤发现JAXB实现。
 * 
 * <ol>
 * <li>
 * 对于明确传递到{@link #newInstance}方法的每个包/类,按照它们的指定顺序,通过使用关联的类装载器在其包中查找<tt> jaxb.properties </tt>文件。
 * 这是{@link Class}参数的{@link Class#getClassLoader()所有者类装载器},对于包指定的{@link ClassLoader}。
 * 
 * <p>
 *  如果发现这样的文件,则将{@link属性#load(InputStream)loaded}作为属性文件,并且{@link #JAXB_CONTEXT_FACTORY}键的值将被假定为提供程序工厂类。
 * 这个类然后由上面讨论的关联的类加载器加载。
 * 
 * <p>
 *  这个阶段的查找允许一些包强制使用某个JAXB实现。 (例如,可能模式编译器在代码中生成了一些供应商扩展。)
 * 
 * <li>
 *  如果系统属性{@link #JAXB_CONTEXT_FACTORY}存在,则其值假定为提供程序工厂类。这个查找阶段使得每个JVM覆盖JAXB实现。
 * 
 * <li>
 *  在关联的类加载器中查找<tt> /META-INF/services/javax.xml.bind.JAXBContext </tt>文件。
 * 此文件遵循标准服务描述符约定,如果此类文件存在,则其内容假定为提供程序工厂类。查找的这个阶段是自动发现。它允许用户只是把一个JAXB实现放在类路径中,并使用它没有任何furhter配置。
 * 
 * <li>
 * 最后,如果上述所有步骤失败,则其余的查找未指定。也就是说,推荐的行为是简单地寻找一些硬编码的平台默认JAXB实现。这个阶段的查找是使JavaSE可以有自己的JAXB实现作为最后的手段。
 * </ol>
 * 
 * <p>
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see Marshaller
 * @see Unmarshaller
 * @see S 7.4.1 "Named Packages" in Java Language Specification</a>
 * @since JAXB1.0
 */
public abstract class JAXBContext {

    /**
     * The name of the property that contains the name of the class capable
     * of creating new <tt>JAXBContext</tt> objects.
     * <p>
     *  一旦发现了提供程序工厂类,它的<tt> public static JAXBContext createContext(String,ClassLoader,Map)</tt>方法(参见参数语义的{@link #newInstance(String,ClassLoader,Map)}
     * 。
     *  <tt> public static JAXBContext createContet(Class [],Map)</tt>方法(参见参数语义的{@link #newInstance(Class [],Map)}
     * 被创建{@link JAXBContext}。
     * 
     */
    public static final String JAXB_CONTEXT_FACTORY =
        "javax.xml.bind.context.factory";


    protected JAXBContext() {
    }


    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * This is a convenience method to invoke the
     * {@link #newInstance(String,ClassLoader)} method with
     * the context class loader of the current thread.
     *
     * <p>
     *  包含能够创建新<tt> JAXBContext </tt>对象的类的名称的属性名称。
     * 
     * 
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>JAXBContext</tt> such as
     * <ol>
     *   <li>failure to locate either ObjectFactory.class or jaxb.index in the packages</li>
     *   <li>an ambiguity among global elements contained in the contextPath</li>
     *   <li>failure to locate a value for the context factory provider property</li>
     *   <li>mixing schema derived packages from different providers on the same contextPath</li>
     * </ol>
     */
    public static JAXBContext newInstance( String contextPath )
        throws JAXBException {

        //return newInstance( contextPath, JAXBContext.class.getClassLoader() );
        return newInstance( contextPath, getContextClassLoader());
    }

    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * The client application must supply a context path which is a list of
     * colon (':', \u005Cu003A) separated java package names that contain
     * schema-derived classes and/or fully qualified JAXB-annotated classes.
     * Schema-derived
     * code is registered with the JAXBContext by the
     * ObjectFactory.class generated per package.
     * Alternatively than being listed in the context path, programmer
     * annotated JAXB mapped classes can be listed in a
     * <tt>jaxb.index</tt> resource file, format described below.
     * Note that a java package can contain both schema-derived classes and
     * user annotated JAXB classes. Additionally, the java package may
     * contain JAXB package annotations  that must be processed. (see JLS,
     * Section 7.4.1 "Named Packages").
     * </p>
     *
     * <p>
     * Every package listed on the contextPath must meet <b>one or both</b> of the
     * following conditions otherwise a <tt>JAXBException</tt> will be thrown:
     * </p>
     * <ol>
     *   <li>it must contain ObjectFactory.class</li>
     *   <li>it must contain jaxb.index</li>
     * </ol>
     *
     * <p>
     * <b>Format for jaxb.index</b>
     * <p>
     * The file contains a newline-separated list of class names.
     * Space and tab characters, as well as blank
     * lines, are ignored. The comment character
     * is '#' (0x23); on each line all characters following the first comment
     * character are ignored. The file must be encoded in UTF-8. Classes that
     * are reachable, as defined in {@link #newInstance(Class...)}, from the
     * listed classes are also registered with JAXBContext.
     * <p>
     * Constraints on class name occuring in a <tt>jaxb.index</tt> file are:
     * <ul>
     *   <li>Must not end with ".class".</li>
     *   <li>Class names are resolved relative to package containing
     *       <tt>jaxb.index</tt> file. Only classes occuring directly in package
     *       containing <tt>jaxb.index</tt> file are allowed.</li>
     *   <li>Fully qualified class names are not allowed.
     *       A qualified class name,relative to current package,
     *       is only allowed to specify a nested or inner class.</li>
     * </ul>
     *
     * <p>
     * To maintain compatibility with JAXB 1.0 schema to java
     * interface/implementation binding, enabled by schema customization
     * <tt>&lt;jaxb:globalBindings valueClass="false"></tt>,
     * the JAXB provider will ensure that each package on the context path
     * has a <tt>jaxb.properties</tt> file which contains a value for the
     * <tt>javax.xml.bind.context.factory</tt> property and that all values
     * resolve to the same provider.  This requirement does not apply to
     * JAXB annotated classes.
     *
     * <p>
     * If there are any global XML element name collisions across the various
     * packages listed on the <tt>contextPath</tt>, a <tt>JAXBException</tt>
     * will be thrown.
     *
     * <p>
     * Mixing generated interface/impl bindings from multiple JAXB Providers
     * in the same context path may result in a <tt>JAXBException</tt>
     * being thrown.
     *
     * <p>
     * The steps involved in discovering the JAXB implementation is discussed in the class javadoc.
     *
     * <p>
     * <p>
     *  获取<tt> JAXBContext </tt>类的新实例。
     * 
     * <p>
     *  这是一个方便的方法,用当前线程的上下文类加载器调用{@link #newInstance(String,ClassLoader)}方法。
     * 
     * 
     * @param contextPath list of java package names that contain schema
     *                    derived class and/or java to schema (JAXB-annotated)
     *                    mapped classes
     * @param classLoader
     *      This class loader will be used to locate the implementation
     *      classes.
     *
     * @return a new instance of a <tt>JAXBContext</tt>
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>JAXBContext</tt> such as
     * <ol>
     *   <li>failure to locate either ObjectFactory.class or jaxb.index in the packages</li>
     *   <li>an ambiguity among global elements contained in the contextPath</li>
     *   <li>failure to locate a value for the context factory provider property</li>
     *   <li>mixing schema derived packages from different providers on the same contextPath</li>
     * </ol>
     */
    public static JAXBContext newInstance( String contextPath, ClassLoader classLoader ) throws JAXBException {

        return newInstance(contextPath,classLoader,Collections.<String,Object>emptyMap());
    }

    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * This is mostly the same as {@link JAXBContext#newInstance(String, ClassLoader)},
     * but this version allows you to pass in provider-specific properties to configure
     * the instantiation of {@link JAXBContext}.
     *
     * <p>
     * The interpretation of properties is up to implementations. Implementations should
     * throw <tt>JAXBException</tt> if it finds properties that it doesn't understand.
     *
     * <p>
     * <p>
     *  获取<tt> JAXBContext </tt>类的新实例。
     * 
     * <p>
     * 客户端应用程序必须提供上下文路径,该列表是包含冒号('：',\ u005Cu003A)分隔的Java包名称的列表,其包含模式派生类和/或完全限定的JAXB注释类。
     * 模式派生的代码通过每个包生成的ObjectFactory.class注册到JAXBContext。
     * 或者,在上下文路径中列出的程序员注释的JAXB映射类可以列在<tt> jaxb.in​​dex </tt>资源文件中,格式如下所述。注意,一个java包可以包含模式派生类和用户注释的JAXB类。
     * 另外,java包可能包含必须处理的JAXB包注释。 (参见JLS,第7.4.1节"命名包")。
     * </p>
     * 
     * <p>
     *  在contextPath上列出的每个包都必须满足以下条件中的<b>一个或两个</b>,否则将抛出<tt> JAXBException </tt>：
     * </p>
     * <ol>
     *  <li>必须包含ObjectFactory.class </li> <li>必须包含jaxb.in​​dex </li>
     * </ol>
     * 
     * <p>
     *  <b> jaxb.in​​dex的格式</b>
     * <p>
     *  该文件包含换行符分隔的类名称列表。将忽略空格和制表符字符以及空行。注释字符为'#'(0x23);在每行上,忽略第一个注释字符后面的所有字符。该文件必须以UTF-8编码。
     * 从{@link #newInstance(Class ...)}定义的类,从列出的类也可以访问的类也被注册JAXBContext。
     * <p>
     *  在<tt> jaxb.in​​dex </tt>文件中出现的类名称的约束是：
     * <ul>
     * <li>不得以".class"结尾。</li> <li>类名称相对于包含<tt> jaxb.in​​dex </tt>文件的包解析。
     * 只允许直接在包含<tt> jaxb.in​​dex </tt>文件的包中发生的类。</li> <li>不允许使用完全限定类名。相对于当前包的限定类名仅允许指定嵌套或内部类。</li>。
     * </ul>
     * 
     * <p>
     *  为了保持与由模式定制<tt>&lt; jaxb：globalBindings valueClass ="false"> </tt>启用的JAXB 1.0模式到Java接口/实现绑定的兼容性,JAXB提
     * 供者将确保上下文路径上的每个包包含<tt> javax.xml.bind.context.factory </tt>属性值并且所有值解析到同一提供程序的<tt> jaxb.properties </tt>
     * 文件。
     * 此要求不适用于JAXB注释类。
     * 
     * <p>
     *  如果在<tt> contextPath </tt>上列出的各个包之间存在任何全局XML元素名称冲突,将抛出<tt> JAXBException </tt>。
     * 
     * <p>
     *  在同一上下文路径中混合来自多个JAXB提供程序的生成的接口/ impl绑定可能导致抛出<tt> JAXBException </tt>。
     * 
     * <p>
     *  在javadoc类中讨论了发现JAXB实现所涉及的步骤。
     * 
     * 
     * @param contextPath list of java package names that contain schema derived classes
     * @param classLoader
     *      This class loader will be used to locate the implementation classes.
     * @param properties
     *      provider-specific properties. Can be null, which means the same thing as passing
     *      in an empty map.
     *
     * @return a new instance of a <tt>JAXBContext</tt>
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>JAXBContext</tt> such as
     * <ol>
     *   <li>failure to locate either ObjectFactory.class or jaxb.index in the packages</li>
     *   <li>an ambiguity among global elements contained in the contextPath</li>
     *   <li>failure to locate a value for the context factory provider property</li>
     *   <li>mixing schema derived packages from different providers on the same contextPath</li>
     * </ol>
     * @since JAXB2.0
     */
    public static JAXBContext newInstance( String contextPath, ClassLoader classLoader, Map<String,?>  properties  )
        throws JAXBException {

        return ContextFinder.find(
                        /* The default property name according to the JAXB spec */
                        JAXB_CONTEXT_FACTORY,

                        /* the context path supplied by the client app */
                        contextPath,

                        /* class loader to be used */
                        classLoader,
                        properties );
    }

// TODO: resurrect this once we introduce external annotations
//    /**
//     * <p>
//     * Obtain a new instance of a <tt>JAXBContext</tt> class.
//     *
//     * <p>
//     * The client application must supply a list of classes that the new
//     * context object needs to recognize.
//     *
//     * Not only the new context will recognize all the classes specified,
//     * but it will also recognize any classes that are directly/indirectly
//     * referenced statically from the specified classes.
//     *
//     * For example, in the following Java code, if you do
//     * <tt>newInstance(Foo.class)</tt>, the newly created {@link JAXBContext}
//     * will recognize both <tt>Foo</tt> and <tt>Bar</tt>, but not <tt>Zot</tt>:
//     * <pre>
//     * class Foo {
//     *      Bar b;
//     * }
//     * class Bar { int x; }
//     * class Zot extends Bar { int y; }
//     * </pre>
//     *
//     * Therefore, a typical client application only needs to specify the
//     * top-level classes, but it needs to be careful.
//     *
//     * TODO: if we are to define other mechanisms, refer to them.
//     *
//     * <p>
//     * <p>
//     *  获取<tt> JAXBContext </tt>类的新实例。
//     * 
//     * <p>
//     * 这大体上与{@link JAXBContext#newInstance(String,ClassLoader)}相同,但是此版本允许您传递提供程序特定的属性来配置{@link JAXBContext}的
//     * 实例化。
//     * 
//     * <p>
//     *  属性的解释由实现决定。如果实现发现其不理解的属性,应抛出<tt> JAXBException </tt>。
//     * 
//     * 
//     * @param externalBindings
//     *      list of external binding files. Can be null or empty if none is used.
//     *      when specified, those files determine how the classes are bound.
//     *
//     * @param classesToBeBound
//     *      list of java classes to be recognized by the new {@link JAXBContext}.
//     *      Can be empty, in which case a {@link JAXBContext} that only knows about
//     *      spec-defined classes will be returned.
//     *
//     * @return
//     *      A new instance of a <tt>JAXBContext</tt>. Always non-null valid object.
//     *
//     * @throws JAXBException
//     *      if an error was encountered while creating the
//     *      <tt>JAXBContext</tt>, such as (but not limited to):
//     * <ol>
//     *  <li>No JAXB implementation was discovered
//     *  <li>Classes use JAXB annotations incorrectly
//     *  <li>Classes have colliding annotations (i.e., two classes with the same type name)
//     *  <li>Specified external bindings are incorrect
//     *  <li>The JAXB implementation was unable to locate
//     *      provider-specific out-of-band information (such as additional
//     *      files generated at the development time.)
//     * </ol>
//     *
//     * @throws IllegalArgumentException
//     *      if the parameter contains {@code null} (i.e., {@code newInstance(null);})
//     *
//     * @since JAXB2.0
//     */
//    public static JAXBContext newInstance( Source[] externalBindings, Class... classesToBeBound )
//        throws JAXBException {
//
//        // empty class list is not an error, because the context will still include
//        // spec-specified classes like String and Integer.
//        // if(classesToBeBound.length==0)
//        //    throw new IllegalArgumentException();
//
//        // but it is an error to have nulls in it.
//        for( int i=classesToBeBound.length-1; i>=0; i-- )
//            if(classesToBeBound[i]==null)
//                throw new IllegalArgumentException();
//
//        return ContextFinder.find(externalBindings,classesToBeBound);
//    }

    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * The client application must supply a list of classes that the new
     * context object needs to recognize.
     *
     * Not only the new context will recognize all the classes specified,
     * but it will also recognize any classes that are directly/indirectly
     * referenced statically from the specified classes. Subclasses of
     * referenced classes nor <tt>&#64;XmlTransient</tt> referenced classes
     * are not registered with JAXBContext.
     *
     * For example, in the following Java code, if you do
     * <tt>newInstance(Foo.class)</tt>, the newly created {@link JAXBContext}
     * will recognize both <tt>Foo</tt> and <tt>Bar</tt>, but not <tt>Zot</tt> or <tt>FooBar</tt>:
     * <pre>
     * class Foo {
     *      &#64;XmlTransient FooBar c;
     *      Bar b;
     * }
     * class Bar { int x; }
     * class Zot extends Bar { int y; }
     * class FooBar { }
     * </pre>
     *
     * Therefore, a typical client application only needs to specify the
     * top-level classes, but it needs to be careful.
     *
     * <p>
     * Note that for each java package registered with JAXBContext,
     * when the optional package annotations exist, they must be processed.
     * (see JLS, Section 7.4.1 "Named Packages").
     *
     * <p>
     * The steps involved in discovering the JAXB implementation is discussed in the class javadoc.
     *
     * <p>
     *  // * <p> // *获取<tt> JAXBContext </tt>类的新实例。 // * // * <p> // *客户端应用程序必须提供新的// *上下文对象需要识别的类的列表。
     *  // * // *不仅新上下文将识别所有指定的类// *,但它也会识别直接/间接从指定类静态引用的任何类。
     *  // * // *例如,在以下Java代码中,如果您执行// * <tt> newInstance(Foo.class)</tt>,则新创建的{@link JAXBContext} tt> Foo </tt>
     * 和<tt> Bar </tt>,而不是<tt> Zot </tt>：// * class Foo {// * Bar b; // *} // * class Bar {int x; } // * cla
     * ss Zot extends Bar {int y; } // * </pre> // * // *因此,典型的客户端应用程序只需要指定// *顶级类,但它需要小心。
     *  // * // *不仅新上下文将识别所有指定的类// *,但它也会识别直接/间接从指定类静态引用的任何类。 // * // * TODO：如果我们要定义其他机制,请参考它们。 // *。
     * 
     * 
     * @param classesToBeBound
     *      list of java classes to be recognized by the new {@link JAXBContext}.
     *      Can be empty, in which case a {@link JAXBContext} that only knows about
     *      spec-defined classes will be returned.
     *
     * @return
     *      A new instance of a <tt>JAXBContext</tt>. Always non-null valid object.
     *
     * @throws JAXBException
     *      if an error was encountered while creating the
     *      <tt>JAXBContext</tt>, such as (but not limited to):
     * <ol>
     *  <li>No JAXB implementation was discovered
     *  <li>Classes use JAXB annotations incorrectly
     *  <li>Classes have colliding annotations (i.e., two classes with the same type name)
     *  <li>The JAXB implementation was unable to locate
     *      provider-specific out-of-band information (such as additional
     *      files generated at the development time.)
     * </ol>
     *
     * @throws IllegalArgumentException
     *      if the parameter contains {@code null} (i.e., {@code newInstance(null);})
     *
     * @since JAXB2.0
     */
    public static JAXBContext newInstance( Class... classesToBeBound )
        throws JAXBException {

        return newInstance(classesToBeBound,Collections.<String,Object>emptyMap());
    }

    /**
     * <p>
     * Obtain a new instance of a <tt>JAXBContext</tt> class.
     *
     * <p>
     * An overloading of {@link JAXBContext#newInstance(Class...)}
     * to configure 'properties' for this instantiation of {@link JAXBContext}.
     *
     * <p>
     * The interpretation of properties is up to implementations. Implementations should
     * throw <tt>JAXBException</tt> if it finds properties that it doesn't understand.
     *
     * <p>
     * <p>
     *  获取<tt> JAXBContext </tt>类的新实例。
     * 
     * <p>
     * 客户端应用程序必须提供新的上下文对象需要识别的类的列表。
     * 
     *  不仅新上下文将识别所有指定的类,而且它还将识别从指定类直接/间接引用的任何类。引用类的子类或<tt> @XmlTransient </tt>引用的类不会使用JAXBContext注册。
     * 
     *  例如,在以下Java代码中,如果您执行<tt> newInstance(Foo.class)</tt>,则新创建的{@link JAXBContext}将会识别<tt> Foo </tt>和<tt> 
     * Bar </tt>,但不是<tt> Zot </tt>或<tt> FooBar </tt>：。
     * <pre>
     *  class Foo {@XmlTransient FooBar c;条b; } class Bar {int x; } class Zot extends Bar {int y; } class Fo
     * oBar {}。
     * </pre>
     * 
     *  因此,典型的客户端应用程序只需要指定顶级类,但它需要小心。
     * 
     * <p>
     *  请注意,对于使用JAXBContext注册的每个Java包,当可选的包注释存在时,必须处理它们。 (参见JLS,第7.4.1节"命名包")。
     * 
     * <p>
     *  在javadoc类中讨论了发现JAXB实现所涉及的步骤。
     * 
     * 
     * @param classesToBeBound
     *      list of java classes to be recognized by the new {@link JAXBContext}.
     *      Can be empty, in which case a {@link JAXBContext} that only knows about
     *      spec-defined classes will be returned.
     * @param properties
     *      provider-specific properties. Can be null, which means the same thing as passing
     *      in an empty map.
     *
     * @return
     *      A new instance of a <tt>JAXBContext</tt>. Always non-null valid object.
     *
     * @throws JAXBException
     *      if an error was encountered while creating the
     *      <tt>JAXBContext</tt>, such as (but not limited to):
     * <ol>
     *  <li>No JAXB implementation was discovered
     *  <li>Classes use JAXB annotations incorrectly
     *  <li>Classes have colliding annotations (i.e., two classes with the same type name)
     *  <li>The JAXB implementation was unable to locate
     *      provider-specific out-of-band information (such as additional
     *      files generated at the development time.)
     * </ol>
     *
     * @throws IllegalArgumentException
     *      if the parameter contains {@code null} (i.e., {@code newInstance(null,someMap);})
     *
     * @since JAXB2.0
     */
    public static JAXBContext newInstance( Class[] classesToBeBound, Map<String,?> properties )
        throws JAXBException {

        if (classesToBeBound == null) {
                throw new IllegalArgumentException();
        }

        // but it is an error to have nulls in it.
        for (int i = classesToBeBound.length - 1; i >= 0; i--) {
            if (classesToBeBound[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        return ContextFinder.find(classesToBeBound,properties);
    }

    /**
     * Create an <tt>Unmarshaller</tt> object that can be used to convert XML
     * data into a java content tree.
     *
     * <p>
     * <p>
     *  获取<tt> JAXBContext </tt>类的新实例。
     * 
     * <p>
     *  重载{@link JAXBContext#newInstance(Class ...)}以为此实例化{@link JAXBContext}配置"属性"。
     * 
     * <p>
     *  属性的解释由实现决定。如果实现发现其不理解的属性,应抛出<tt> JAXBException </tt>。
     * 
     * 
     * @return an <tt>Unmarshaller</tt> object
     *
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>Unmarshaller</tt> object
     */
    public abstract Unmarshaller createUnmarshaller() throws JAXBException;


    /**
     * Create a <tt>Marshaller</tt> object that can be used to convert a
     * java content tree into XML data.
     *
     * <p>
     * 创建可用于将XML数据转换为Java内容树的<tt> Unmarshaller </tt>对象。
     * 
     * 
     * @return a <tt>Marshaller</tt> object
     *
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>Marshaller</tt> object
     */
    public abstract Marshaller createMarshaller() throws JAXBException;


    /**
     * {@link Validator} has been made optional and deprecated in JAXB 2.0.  Please
     * refer to the javadoc for {@link Validator} for more detail.
     * <p>
     * Create a <tt>Validator</tt> object that can be used to validate a
     * java content tree against its source schema.
     *
     * <p>
     *  创建可用于将Java内容树转换为XML数据的<tt> Marshaller </tt>对象。
     * 
     * 
     * @return a <tt>Validator</tt> object
     *
     * @throws JAXBException if an error was encountered while creating the
     *                       <tt>Validator</tt> object
     * @deprecated since JAXB2.0
     */
    public abstract Validator createValidator() throws JAXBException;

    /**
     * Creates a <tt>Binder</tt> object that can be used for
     * associative/in-place unmarshalling/marshalling.
     *
     * <p>
     *  {@link Validator}已成为可选,在JAXB 2.0中已弃用。有关更多详细信息,请参阅{@link Validator}的javadoc。
     * <p>
     *  创建可用于根据其源模式验证java内容树的<tt> Validator </tt>对象。
     * 
     * 
     * @param domType select the DOM API to use by passing in its DOM Node class.
     *
     * @return always a new valid <tt>Binder</tt> object.
     *
     * @throws UnsupportedOperationException
     *      if DOM API corresponding to <tt>domType</tt> is not supported by
     *      the implementation.
     *
     * @since JAXB2.0
     */
    public <T> Binder<T> createBinder(Class<T> domType) {
        // to make JAXB 1.0 implementations work, this method must not be
        // abstract
        throw new UnsupportedOperationException();
    }

    /**
     * Creates a <tt>Binder</tt> for W3C DOM.
     *
     * <p>
     *  创建可用于关联/就地取消编组/编组的<tt>绑定器</tt>对象。
     * 
     * 
     * @return always a new valid <tt>Binder</tt> object.
     *
     * @since JAXB2.0
     */
    public Binder<Node> createBinder() {
        return createBinder(Node.class);
    }

    /**
     * Creates a <tt>JAXBIntrospector</tt> object that can be used to
     * introspect JAXB objects.
     *
     * <p>
     *  为W3C DOM创建<tt>绑定器</tt>。
     * 
     * 
     * @return
     *      always return a non-null valid <tt>JAXBIntrospector</tt> object.
     *
     * @throws UnsupportedOperationException
     *      Calling this method on JAXB 1.0 implementations will throw
     *      an UnsupportedOperationException.
     *
     * @since JAXB2.0
     */
    public JAXBIntrospector createJAXBIntrospector() {
        // to make JAXB 1.0 implementations work, this method must not be
        // abstract
        throw new UnsupportedOperationException();
    }

    /**
     * Generates the schema documents for this context.
     *
     * <p>
     *  创建可用于内省JAXB对象的<tt> JAXBIntrospector </tt>对象。
     * 
     * 
     * @param outputResolver
     *      this object controls the output to which schemas
     *      will be sent.
     *
     * @throws IOException
     *      if {@link SchemaOutputResolver} throws an {@link IOException}.
     *
     * @throws UnsupportedOperationException
     *      Calling this method on JAXB 1.0 implementations will throw
     *      an UnsupportedOperationException.
     *
     * @since JAXB 2.0
     */
    public void generateSchema(SchemaOutputResolver outputResolver) throws IOException  {
        // to make JAXB 1.0 implementations work, this method must not be
        // abstract
        throw new UnsupportedOperationException();
    }

    private static ClassLoader getContextClassLoader() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        } else {
            return (ClassLoader) java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedAction() {
                        public java.lang.Object run() {
                            return Thread.currentThread().getContextClassLoader();
                        }
                    });
        }
    }

}
