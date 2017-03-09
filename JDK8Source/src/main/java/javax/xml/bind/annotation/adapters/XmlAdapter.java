/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.annotation.adapters;

/**
 * Adapts a Java type for custom marshaling.
 *
 * <p> <b> Usage: </b> </p>
 *
 * <p>
 * Some Java types do not map naturally to a XML representation, for
 * example <tt>HashMap</tt> or other non JavaBean classes. Conversely,
 * a XML repsentation may map to a Java type but an application may
 * choose to accesss the XML representation using another Java
 * type. For example, the schema to Java binding rules bind
 * xs:DateTime by default to XmlGregorianCalendar. But an application
 * may desire to bind xs:DateTime to a custom type,
 * MyXmlGregorianCalendar, for example. In both cases, there is a
 * mismatch between <i> bound type </i>, used by an application to
 * access XML content and the <i> value type</i>, that is mapped to an
 * XML representation.
 *
 * <p>
 * This abstract class defines methods for adapting a bound type to a value
 * type or vice versa. The methods are invoked by the JAXB binding
 * framework during marshaling and unmarshalling:
 *
 * <ul>
 *   <li> <b> XmlAdapter.marshal(...): </b> During marshalling, JAXB
 *        binding framework invokes XmlAdapter.marshal(..) to adapt a
 *        bound type to value type, which is then marshaled to XML
 *        representation. </li>
 *
 *   <li> <b> XmlAdapter.unmarshal(...): </b> During unmarshalling,
 *        JAXB binding framework first unmarshals XML representation
 *        to a value type and then invokes XmlAdapter.unmarshal(..) to
 *        adapt the value type to a bound type. </li>
 * </ul>
 *
 * Writing an adapter therefore involves the following steps:
 *
 * <ul>
 *   <li> Write an adapter that implements this abstract class. </li>
 *   <li> Install the adapter using the annotation {@link
 *        XmlJavaTypeAdapter} </li>
 * </ul>
 *
 * <p><b>Example:</b> Customized mapping of <tt>HashMap</tt></p>
 * <p> The following example illustrates the use of
 * <tt>&#64;XmlAdapter</tt> and <tt>&#64;XmlJavaTypeAdapter</tt> to
 * customize the mapping of a <tt>HashMap</tt>.
 *
 * <p> <b> Step 1: </b> Determine the desired XML representation for HashMap.
 *
 * <pre>
 *     &lt;hashmap>
 *         &lt;entry key="id123">this is a value&lt;/entry>
 *         &lt;entry key="id312">this is another value&lt;/entry>
 *         ...
 *       &lt;/hashmap>
 * </pre>
 *
 * <p> <b> Step 2: </b> Determine the schema definition that the
 * desired XML representation shown above should follow.
 *
 * <pre>
 *
 *     &lt;xs:complexType name="myHashMapType">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="entry" type="myHashMapEntryType"
 *                        minOccurs = "0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 *
 *     &lt;xs:complexType name="myHashMapEntryType">
 *       &lt;xs:simpleContent>
 *         &lt;xs:extension base="xs:string">
 *           &lt;xs:attribute name="key" type="xs:int"/>
 *         &lt;/xs:extension>
 *       &lt;/xs:simpleContent>
 *     &lt;/xs:complexType>
 *
 * </pre>
 *
 * <p> <b> Step 3: </b> Write value types that can generate the above
 * schema definition.
 *
 * <pre>
 *     public class MyHashMapType {
 *         List&lt;MyHashMapEntryType> entry;
 *     }
 *
 *     public class MyHashMapEntryType {
 *         &#64;XmlAttribute
 *         public Integer key;
 *
 *         &#64;XmlValue
 *         public String value;
 *     }
 * </pre>
 *
 * <p> <b> Step 4: </b> Write the adapter that adapts the value type,
 * MyHashMapType to a bound type, HashMap, used by the application.
 *
 * <pre>
 *     public final class MyHashMapAdapter extends
 *                        XmlAdapter&lt;MyHashMapType,HashMap> { ... }
 *
 * </pre>
 *
 * <p> <b> Step 5: </b> Use the adapter.
 *
 * <pre>
 *     public class Foo {
 *         &#64;XmlJavaTypeAdapter(MyHashMapAdapter.class)
 *         HashMap hashmap;
 *         ...
 *     }
 * </pre>
 *
 * The above code fragment will map to the following schema:
 *
 * <pre>
 *     &lt;xs:complexType name="Foo">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="hashmap" type="myHashMapType"
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 * </pre>
 *
 * <p>
 *  适应Java类型的自定义封送。
 * 
 *  <p> <b>用法：</b> </p>
 * 
 * <p>
 *  一些Java类型不会自然映射到XML表示,例如<tt> HashMap </tt>或其他非JavaBean类。
 * 相反,XML表示可以映射到Java类型,但应用可以选择使用另一种Java类型来访问XML表示。例如,模式到Java绑定规则将xs：DateTime默认绑定到XmlGregorianCalendar。
 * 但是应用程序可能希望将xs：DateTime绑定到自定义类型,例如MyXmlGregorianCalendar。
 * 在这两种情况下,应用程序用于访问XML内容的<i>绑定类型</i>与映射到XML表示的<value>类型</i>之间存在不匹配。
 * 
 * <p>
 *  此抽象类定义了用于将绑定类型适配为值类型的方法,反之亦然。在封送和解组期间,JAXB绑定框架调用这些方法：
 * 
 * <ul>
 *  <li> <b> XmlAdapter.marshal(...)：</b>在编组期间,JAXB绑定框架调用XmlAdapter.marshal(..)以将绑定类型适配为值类型,然后将其封送到XML表示
 * 。
 *  </li>。
 * 
 *  <li> <b> XmlAdapter.unmarshal(...)：</b>在解组合期间,JAXB绑定框架首先将XML表示解组为值类型,然后调用XmlAdapter.unmarshal(..)绑定类
 * 型。
 *  </li>。
 * </ul>
 * 
 * 因此,编写适配器涉及以下步骤：
 * 
 * <ul>
 *  <li>编写实现此抽象类的适配器。 </li> <li>使用注释{@link XmlJavaTypeAdapter} </li>安装适配器
 * </ul>
 * 
 *  <p> <b>示例：</b> <tt> HashMap </tt> </p> <p>的自定义映射以下示例说明了<tt> @XmlAdapter </tt>和<tt> @XmlJavaTypeAdapt
 * er </tt>可自定义<tt> HashMap </tt>的映射。
 * 
 *  <p> <b>步骤1：</b>确定HashMap所需的XML表示形式。
 * 
 * <pre>
 * &lt;hashmap>
 * &lt;entry key="id123">this is a value&lt;/entry>
 * &lt;entry key="id312">this is another value&lt;/entry>
 *  ... ...
 * &lt;/hashmap>
 * </pre>
 * 
 *  <p> <b>步骤2：</b>确定上面显示的所需XML表示应遵循的模式定义。
 * 
 * <pre>
 * 
 * &lt;xs:complexType name="myHashMapType">
 * &lt;xs:sequence>
 *  &lt; xs：element name ="entry"type ="myHashMapEntryType"
 * minOccurs = "0" maxOccurs="unbounded"/>
 * &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * 
 * 
 * @param <BoundType>
 *      The type that JAXB doesn't know how to handle. An adapter is written
 *      to allow this type to be used as an in-memory representation through
 *      the <tt>ValueType</tt>.
 * @param <ValueType>
 *      The type that JAXB knows how to handle out of the box.
 *
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems Inc.</li> <li> Kohsuke Kawaguchi, Sun Microsystems Inc.</li></ul>
 * @see XmlJavaTypeAdapter
 * @since JAXB 2.0
 */
public abstract class XmlAdapter<ValueType,BoundType> {

    /**
     * Do-nothing constructor for the derived classes.
     * <p>
     * &lt;xs:complexType name="myHashMapEntryType">
     * &lt;xs:simpleContent>
     * &lt;xs:extension base="xs:string">
     * &lt;xs:attribute name="key" type="xs:int"/>
     * &lt;/xs:extension>
     * &lt;/xs:simpleContent>
     * &lt;/xs:complexType>
     * 
     * </pre>
     * 
     *  <p> <b>步骤3：</b>编写可以生成上述模式定义的值类型。
     * 
     * <pre>
     *  public class MyHashMapType {List&lt; MyHashMapEntryType> entry; }}
     * 
     *  public class MyHashMapEntryType {@XmlAttribute public Integer key;
     * 
     *  @XmlValue public String value; }}
     * </pre>
     * 
     *  <p> <b>步骤4：</b>编写适配器,将值类型MyHashMapType适配为应用程序使用的绑定类型HashMap。
     * 
     * <pre>
     *  public final class MyHashMapAdapter extends XmlAdapter&lt; MyHashMapType,HashMap> {...}
     * 
     * </pre>
     */
    protected XmlAdapter() {}

    /**
     * Convert a value type to a bound type.
     *
     * <p>
     * 
     *  <p> <b>步骤5：</b>使用适配器。
     * 
     * <pre>
     *  public class Foo {@XmlJavaTypeAdapter(MyHashMapAdapter.class)HashMap hashmap; ...}
     * </pre>
     * 
     *  上面的代码片段将映射到以下模式：
     * 
     * <pre>
     * &lt;xs:complexType name="Foo">
     * &lt;xs:sequence>
     *  &lt; xs：element name ="hashmap"type ="myHashMapType"
     * 
     * @param v
     *      The value to be converted. Can be null.
     * @throws Exception
     *      if there's an error during the conversion. The caller is responsible for
     *      reporting the error to the user through {@link javax.xml.bind.ValidationEventHandler}.
     */
    public abstract BoundType unmarshal(ValueType v) throws Exception;

    /**
     * Convert a bound type to a value type.
     *
     * <p>
     * &lt;/xs:sequence>
     * &lt;/xs:complexType>
     * </pre>
     * 
     * 
     * @param v
     *      The value to be convereted. Can be null.
     * @throws Exception
     *      if there's an error during the conversion. The caller is responsible for
     *      reporting the error to the user through {@link javax.xml.bind.ValidationEventHandler}.
     */
    public abstract ValueType marshal(BoundType v) throws Exception;
}
