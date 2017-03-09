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

package javax.xml.bind;

import javax.xml.namespace.QName;
import java.io.Serializable;

/**
 * <p>JAXB representation of an Xml Element.</p>
 *
 * <p>This class represents information about an Xml Element from both the element
 * declaration within a schema and the element instance value within an xml document
 * with the following properties
 * <ul>
 *   <li>element's xml tag <b><tt>name</tt></b></li>
 *   <li><b><tt>value</tt></b> represents the element instance's atttribute(s) and content model</li>
 *   <li>element declaration's <b><tt>declaredType</tt></b> (<tt>xs:element @type</tt> attribute)</li>
 *   <li><b><tt>scope</tt></b> of element declaration</li>
 *   <li>boolean <b><tt>nil</tt></b> property. (element instance's <tt><b>xsi:nil</b></tt> attribute)</li>
 * </ul>
 *
 * <p>The <tt>declaredType</tt> and <tt>scope</tt> property are the
 * JAXB class binding for the xml type definition.
 * </p>
 *
 * <p><b><tt>Scope</tt></b> is either {@link GlobalScope} or the Java class representing the
 * complex type definition containing the schema element declaration.
 * </p>
 *
 * <p>There is a property constraint that if <b><tt>value</tt></b> is <tt>null</tt>,
 * then <tt>nil</tt> must be <tt>true</tt>. The converse is not true to enable
 * representing a nil element with attribute(s). If <tt>nil</tt> is true, it is possible
 * that <tt>value</tt> is non-null so it can hold the value of the attributes
 * associated with a nil element.
 * </p>
 *
 * <p>
 *  <p> Xml元素的JAXB表示。</p>
 * 
 *  <p>此类表示关于来自模式中的元素声明和xml文档中的元素实例值的Xml元素的信息,具有以下属性
 * <ul>
 *  <li>元素的xml标记<b> <tt> name </tt> </b> </li> <li> <b> <tt> value </tt> </b>代表元素实例的属性)和内容模型</li> <li>元素
 * 声明<b> <tt> declaredType </tt> </b>(<tt> xs：element @type </tt>元素声明</li> <li>布尔<b> <tt> nil </tt> </b>
 * 属性的<b> <tt>作用域</tt> (元素实例的<tt> <b> xsi：nil </b> </tt>属性)</li>。
 * </ul>
 * 
 *  <p> <tt> declaredType </tt>和<tt> scope </tt>属性是xml类型定义的JAXB类绑定。
 * </p>
 * 
 *  <p> <b> <tt>范围</tt> </b>是{@link GlobalScope}或表示包含模式元素声明的复杂类型定义的Java类。
 * </p>
 * 
 *  <p>有一个属性约束,如果<b> <tt> value </tt> </b>是<tt> null </tt>,则<tt> nil </tt>必须<tt> true </tt>。
 * 相反的是不能够用属性来表示nil元素。如果<tt> nil </tt>为真,则<tt>值</tt>可能是非空的,因此它可以保存与nil元素相关联的属性的值。
 * </p>
 * 
 * 
 * @author Kohsuke Kawaguchi, Joe Fialli
 * @since JAXB 2.0
 */

public class JAXBElement<T> implements Serializable {

    /** xml element tag name */
    final protected QName name;

    /** Java datatype binding for xml element declaration's type. */
    final protected Class<T> declaredType;

    /** Scope of xml element declaration representing this xml element instance.
     *  Can be one of the following values:
     *  - {@link GlobalScope} for global xml element declaration.
     *  - local element declaration has a scope set to the Java class
     *     representation of complex type defintion containing
     *     xml element declaration.
     * <p>
     * 可以是以下值之一： -  {@link GlobalScope}用于全局xml元素声明。 - 局部元素声明的范围设置为包含xml元素声明的复杂类型定义的Java类表示。
     * 
     */
    final protected Class scope;

    /** xml element value.
    /* <p>
    /* 
        Represents content model and attributes of an xml element instance. */
    protected T value;

    /** true iff the xml element instance has xsi:nil="true". */
    protected boolean nil = false;

    /**
     * Designates global scope for an xml element.
     * <p>
     *  指定xml元素的全局范围。
     * 
     */
    public static final class GlobalScope {}

    /**
     * <p>Construct an xml element instance.</p>
     *
     * <p>
     *  <p>构造xml元素实例。</p>
     * 
     * 
     * @param name          Java binding of xml element tag name
     * @param declaredType  Java binding of xml element declaration's type
     * @param scope
     *      Java binding of scope of xml element declaration.
     *      Passing null is the same as passing <tt>GlobalScope.class</tt>
     * @param value
     *      Java instance representing xml element's value.
     * @see #getScope()
     * @see #isTypeSubstituted()
     */
    public JAXBElement(QName name,
                       Class<T> declaredType,
                       Class scope,
                       T value) {
        if(declaredType==null || name==null)
            throw new IllegalArgumentException();
        this.declaredType = declaredType;
        if(scope==null)     scope = GlobalScope.class;
        this.scope = scope;
        this.name = name;
        setValue(value);
    }

    /**
     * Construct an xml element instance.
     *
     * This is just a convenience method for <tt>new JAXBElement(name,declaredType,GlobalScope.class,value)</tt>
     * <p>
     *  构造一个xml元素实例。
     * 
     *  这只是一个方便的方法<tt> new JAXBElement(name,declaredType,GlobalScope.class,value)</tt>
     * 
     */
    public JAXBElement(QName name, Class<T> declaredType, T value ) {
        this(name,declaredType,GlobalScope.class,value);
    }

    /**
     * Returns the Java binding of the xml element declaration's type attribute.
     * <p>
     *  返回xml元素声明的type属性的Java绑定。
     * 
     */
    public Class<T> getDeclaredType() {
        return declaredType;
    }

    /**
     * Returns the xml element tag name.
     * <p>
     *  返回xml元素标记名称。
     * 
     */
    public QName getName() {
        return name;
    }

    /**
     * <p>Set the content model and attributes of this xml element.</p>
     *
     * <p>When this property is set to <tt>null</tt>, <tt>isNil()</tt> must by <tt>true</tt>.
     *    Details of constraint are described at {@link #isNil()}.</p>
     *
     * <p>
     *  <p>设置此xml元素的内容模型和属性。</p>
     * 
     *  <p>当此属性设置为<tt> null </tt>时,<tt> isNil()</tt>必须通过<tt> true </tt>。约束的细节在{@link #isNil()}中描述。</p>
     * 
     * 
     * @see #isTypeSubstituted()
     */
    public void setValue(T t) {
        this.value = t;
    }

    /**
     * <p>Return the content model and attribute values for this element.</p>
     *
     * <p>See {@link #isNil()} for a description of a property constraint when
     * this value is <tt>null</tt></p>
     * <p>
     *  <p>返回此元素的内容模型和属性值。</p>
     * 
     *  <p>当此值为<tt> null </tt> </p>时,请参阅{@link #isNil()}关于属性约束的描述。
     * 
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns scope of xml element declaration.
     *
     * <p>
     *  返回xml元素声明的作用域。
     * 
     * 
     * @see #isGlobalScope()
     * @return <tt>GlobalScope.class</tt> if this element is of global scope.
     */
    public Class getScope() {
        return scope;
    }

    /**
     * <p>Returns <tt>true</tt> iff this element instance content model
     * is nil.</p>
     *
     * <p>This property always returns <tt>true</tt> when {@link #getValue()} is null.
     * Note that the converse is not true, when this property is <tt>true</tt>,
     * {@link #getValue()} can contain a non-null value for attribute(s). It is
     * valid for a nil xml element to have attribute(s).</p>
     * <p>
     *  <p>如果此元素实例内容模型为nil,则返回<tt> true </tt>。</p>
     * 
     *  <p>当{@link #getValue()}为null时,此属性总是返回<tt> true </tt>。
     * 请注意,反之不是true,当此属性为<tt> true </tt>时,{@link #getValue()}可以包含属性的非空值。它对于nil xml元素有效,具有属性。</p>。
     * 
     */
    public boolean isNil() {
        return (value == null) || nil;
    }

    /**
     * <p>Set whether this element has nil content.</p>
     *
     * <p>
     *  <p>设置此元素是否包含nil内容。</p>
     * 
     * 
     * @see #isNil()
     */
    public void setNil(boolean value) {
        this.nil = value;
    }

    /* Convenience methods
     * (Not necessary but they do unambiguously conceptualize
     *  the rationale behind this class' fields.)
     * <p>
     * (没有必要,但他们明确地概念了这个类的领域背后的理论。)
     * 
     */

    /**
     * Returns true iff this xml element declaration is global.
     * <p>
     *  返回true iff此xml元素声明是全局的。
     * 
     */
    public boolean isGlobalScope() {
        return this.scope == GlobalScope.class;
    }

    /**
     * Returns true iff this xml element instance's value has a different
     * type than xml element declaration's declared type.
     * <p>
     *  返回true iff此xml元素实例的值具有与xml元素声明的声明类型不同的类型。
     */
    public boolean isTypeSubstituted() {
        if(value==null)     return false;
        return value.getClass() != declaredType;
    }

    private static final long serialVersionUID = 1L;
}
