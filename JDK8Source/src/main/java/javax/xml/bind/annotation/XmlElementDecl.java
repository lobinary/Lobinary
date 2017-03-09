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

package javax.xml.bind.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;

/**
 * Maps a factory method to a XML element.
 *
 * <p> <b>Usage</b> </p>
 *
 * The annotation creates a mapping between an XML schema element
 * declaration and a <i> element factory method </i> that returns a
 * JAXBElement instance representing the element
 * declaration. Typically, the element factory method is generated
 * (and annotated) from a schema into the ObjectFactory class in a
 * Java package that represents the binding of the element
 * declaration's target namespace. Thus, while the annotation syntax
 * allows &#64;XmlElementDecl to be used on any method, semantically
 * its use is restricted to annotation of element factory method.
 *
 * The usage is subject to the following constraints:
 *
 * <ul>
 *   <li> The class containing the element factory method annotated
 *        with &#64;XmlElementDecl must be marked with {@link
 *        XmlRegistry}. </li>
 *   <li> The element factory method must take one parameter
 *        assignable to {@link Object}.</li>
 * </ul>
 *
 * <p><b>Example 1: </b>Annotation on a factory method
 * <pre>
 *     // Example: code fragment
 *     &#64;XmlRegistry
 *     class ObjectFactory {
 *         &#64;XmlElementDecl(name="foo")
 *         JAXBElement&lt;String> createFoo(String s) { ... }
 *     }
 * </pre>
 * <pre>
 *     &lt;!-- XML input -->
 *       &lt;foo>string</foo>
 *
 *     // Example: code fragment corresponding to XML input
 *     JAXBElement<String> o =
 *     (JAXBElement<String>)unmarshaller.unmarshal(aboveDocument);
 *     // print JAXBElement instance to show values
 *     System.out.println(o.getName());   // prints  "{}foo"
 *     System.out.println(o.getValue());  // prints  "string"
 *     System.out.println(o.getValue().getClass()); // prints "java.lang.String"
 *
 *     &lt;!-- Example: XML schema definition -->
 *     &lt;xs:element name="foo" type="xs:string"/>
 * </pre>
 *
 * <p><b>Example 2: </b> Element declaration with non local scope
 * <p>
 * The following example illustrates the use of scope annotation
 * parameter in binding of element declaration in schema derived
 * code.
 * <p>
 * The following example may be replaced in a future revision of
 * this javadoc.
 *
 * <pre>
 *     &lt;!-- Example: XML schema definition -->
 *     &lt;xs:schema>
 *       &lt;xs:complexType name="pea">
 *         &lt;xs:choice maxOccurs="unbounded">
 *           &lt;xs:element name="foo" type="xs:string"/>
 *           &lt;xs:element name="bar" type="xs:string"/>
 *         &lt;/xs:choice>
 *       &lt;/xs:complexType>
 *       &lt;xs:element name="foo" type="xs:int"/>
 *     &lt;/xs:schema>
 * </pre>
 * <pre>
 *     // Example: expected default binding
 *     class Pea {
 *         &#64;XmlElementRefs({
 *             &#64;XmlElementRef(name="foo",type=JAXBElement.class)
 *             &#64;XmlElementRef(name="bar",type=JAXBElement.class)
 *         })
 *         List&lt;JAXBElement&lt;String>> fooOrBar;
 *     }
 *
 *     &#64;XmlRegistry
 *     class ObjectFactory {
 *         &#64;XmlElementDecl(scope=Pea.class,name="foo")
 *         JAXBElement<String> createPeaFoo(String s);
 *
 *         &#64;XmlElementDecl(scope=Pea.class,name="bar")
 *         JAXBElement<String> createPeaBar(String s);
 *
 *         &#64;XmlElementDecl(name="foo")
 *         JAXBElement<Integer> createFoo(Integer i);
 *     }
 *
 * </pre>
 * Without scope createFoo and createPeaFoo would become ambiguous
 * since both of them map to a XML schema element with the same local
 * name "foo".
 *
 * <p>
 *  将工厂方法映射到XML元素。
 * 
 *  <p> <b>使用</b> </p>
 * 
 *  注释创建XML模式元素声明和<i>元素工厂方法</i>之间的映射,该方法返回表示元素声明的JAXBElement实例。
 * 通常,元素工厂方法从一个模式生成(和注释)到一个Java包中的ObjectFactory类中,该包表示元素声明的目标命名空间的绑定。
 * 因此,虽然注释语法允许@XmlElementDecl用于任何方法,但在语义上它的使用仅限于元素工厂方法的注释。
 * 
 *  用法受以下约束：
 * 
 * <ul>
 *  <li>包含使用@XmlElementDecl注释的元素工厂方法的类必须标记为{@link XmlRegistry}。
 *  </li> <li>元素工厂方法必须有一个可分配给{@link Object}的参数。</li>。
 * </ul>
 * 
 *  <p> <b>示例1：</b>对工厂方法的注释
 * <pre>
 *  // Example：code fragment @XmlRegistry class ObjectFactory {@XmlElementDecl(name ="foo")JAXBElement&lt; String> createFoo(String s){...}
 * 。
 * </pre>
 * <pre>
 * &lt;!-- XML input -->
 *  &lt; foo> string </foo>
 * 
 * //示例：对应于XML输入的代码片段JAXBElement <String> o =(JAXBElement <String>)unmarshaller.unmarshal(aboveDocument)
 * ; // print JAXBElement instance to show values System.out.println(o.getName()); // prints"{} foo"Syst
 * em.out.println(o.getValue()); // prints"string"System.out.println(o.getValue()。
 * getClass()); // prints"java.lang.String"。
 * 
 * &lt;!-- Example: XML schema definition -->
 * &lt;xs:element name="foo" type="xs:string"/>
 * </pre>
 * 
 *  <p> <b>示例2：</b>具有非本地作用域的元素声明
 * <p>
 *  以下示例说明了在模式派生代码的元素声明的绑定中使用范围注释参数。
 * <p>
 *  以下示例可以在此javadoc的未来版本中替换。
 * 
 * <pre>
 * &lt;!-- Example: XML schema definition -->
 * &lt;xs:schema>
 * &lt;xs:complexType name="pea">
 * &lt;xs:choice maxOccurs="unbounded">
 * &lt;xs:element name="foo" type="xs:string"/>
 * &lt;xs:element name="bar" type="xs:string"/>
 * &lt;/xs:choice>
 * &lt;/xs:complexType>
 * &lt;xs:element name="foo" type="xs:int"/>
 * &lt;/xs:schema>
 * </pre>
 * <pre>
 *  //示例：预期的默认绑定类Pea {@XmlElementRefs({@XmlElementRef(name ="foo",type = JAXBElement.class)@XmlElementRef(name ="bar",type = JAXBElement.class)}
 * )List&lt; JAXBElement&lt; String >> fooOrBar; }}。
 * 
 * @see XmlRegistry
 * @since JAXB 2.0
 */
@Retention(RUNTIME)
@Target({METHOD})
public @interface XmlElementDecl {
    /**
     * scope of the mapping.
     *
     * <p>
     * If this is not {@link XmlElementDecl.GLOBAL}, then this element
     * declaration mapping is only active within the specified class.
     * <p>
     * 
     *  @XmlRegistry class ObjectFactory {@XmlElementDecl(scope = Pea.class,name ="foo")JAXBElement <String> createPeaFoo(String s);。
     * 
     *  @XmlElementDecl(scope = Pea.class,name ="bar")JAXBElement <String> createPeaBar(String s);
     * 
     *  @XmlElementDecl(name ="foo")JAXBElement <Integer> createFoo(Integer i); }}
     * 
     * </pre>
     *  没有作用域createFoo和createPeaFoo会变得不明确,因为它们都映射到具有相同本地名称"foo"的XML模式元素。
     * 
     */
    Class scope() default GLOBAL.class;

    /**
     * namespace name of the XML element.
     * <p>
     * If the value is "##default", then the value is the namespace
     * name for the package of the class containing this factory method.
     *
     * <p>
     *  映射的范围。
     * 
     * <p>
     *  如果这不是{@link XmlElementDecl.GLOBAL},那么此元素声明映射仅在指定类中有效。
     * 
     * 
     * @see #name()
     */
    String namespace() default "##default";

    /**
     * local name of the XML element.
     *
     * <p>
     * <b> Note to reviewers: </b> There is no default name; since
     * the annotation is on a factory method, it is not clear that the
     * method name can be derived from the factory method name.
     * <p>
     *  XML元素的名称空间名称。
     * <p>
     * 如果值为"## default",则该值是包含此工厂方法的类的包的命名空间名称。
     * 
     * 
     * @see #namespace()
     */
    String name();

    /**
     * namespace name of a substitution group's head XML element.
     * <p>
     * This specifies the namespace name of the XML element whose local
     * name is specified by <tt>substitutionHeadName()</tt>.
     * <p>
     * If <tt>susbtitutionHeadName()</tt> is "", then this
     * value can only be "##default". But the value is ignored since
     * since this element is not part of susbtitution group when the
     * value of <tt>susbstitutionHeadName()</tt> is "".
     * <p>
     * If <tt>susbtitutionHeadName()</tt> is not "" and the value is
     * "##default", then the namespace name is the namespace name to
     * which the package of the containing class, marked with {@link
     * XmlRegistry }, is mapped.
     * <p>
     * If <tt>susbtitutionHeadName()</tt> is not "" and the value is
     * not "##default", then the value is the namespace name.
     *
     * <p>
     *  XML元素的本地名称。
     * 
     * <p>
     *  <b>评论者注意：</b>没有默认名称;因为注释是在工厂方法上,所以不清楚方法名可以从工厂方法名派生。
     * 
     * 
     * @see #substitutionHeadName()
     */
    String substitutionHeadNamespace() default "##default";

    /**
     * XML local name of a substitution group's head element.
     * <p>
     * If the value is "", then this element is not part of any
     * substitution group.
     *
     * <p>
     *  替换组的头XML元素的名称空间名称。
     * <p>
     *  这指定了其本地名称由<tt> substitutionHeadName()</tt>指定的XML元素的命名空间名称。
     * <p>
     *  如果<tt> susbtitutionHeadName()</tt>是"",则此值只能为"## default"。
     * 但是值被忽略,因为当<tt> susbstitutionHeadName()</tt>的值为""时,此元素不是susbtitution组的一部分。
     * <p>
     *  如果<tt> susbtitutionHeadName()</tt>不是""且值为"## default",则命名空间名称是包含类的包的标记为{@link XmlRegistry}的命名空间名称,映射
     * 。
     * <p>
     *  如果<tt> susbtitutionHeadName()</tt>不是""且值不是"## default",则该值是命名空间名称。
     * 
     * 
     * @see #substitutionHeadNamespace()
     */
    String substitutionHeadName() default "";

    /**
     * Default value of this element.
     *
     * <p>
     * The <pre>'\u0000'</pre> value specified as a default of this annotation element
     * is used as a poor-man's substitute for null to allow implementations
     * to recognize the 'no default value' state.
     * <p>
     *  替换组头元素的XML本地名称。
     * <p>
     *  如果值为"",则此元素不是任何替换组的一部分。
     * 
     */
    String defaultValue() default "\u0000";

    /**
     * Used in {@link XmlElementDecl#scope()} to
     * signal that the declaration is in the global scope.
     * <p>
     *  此元素的默认值。
     * 
     * <p>
     *  指定为此注记元素的默认值的<pre>'\ u0000'</pre>值用作穷人的null替代,以允许实现识别"无默认值"状态。
     */
    public final class GLOBAL {}
}
