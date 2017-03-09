/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.validation;

import org.w3c.dom.TypeInfo;

/**
 * This class provides access to the type information determined
 * by {@link ValidatorHandler}.
 *
 * <p>
 * Some schema languages, such as W3C XML Schema, encourages a validator
 * to report the "type" it assigns to each attribute/element.
 * Those applications who wish to access this type information can invoke
 * methods defined on this "interface" to access such type information.
 *
 * <p>
 * Implementation of this "interface" can be obtained through the
 * {@link ValidatorHandler#getTypeInfoProvider()} method.
 *
 * <p>
 *  此类提供对由{@link ValidatorHandler}确定的类型信息的访问。
 * 
 * <p>
 *  一些模式语言(例如W3C XML模式)鼓励验证器报告其分配给每个属性/元素的"类型"。希望访问这种类型信息的那些应用程序可以调用在该"接口"上定义的方法来访问这种类型信息。
 * 
 * <p>
 *  这个"接口"的实现可以通过{@link ValidatorHandler#getTypeInfoProvider()}方法获得。
 * 
 * 
 * @author  <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @see org.w3c.dom.TypeInfo
 * @since 1.5
 */
public abstract class TypeInfoProvider {

    /**
     * Constructor for the derived class.
     *
     * <p>
     * The constructor does nothing.
     * <p>
     *  派生类的构造方法。
     * 
     * <p>
     *  构造函数什么也不做。
     * 
     */
    protected TypeInfoProvider() {
    }

    /**
     * <p>Returns the immutable {@link TypeInfo} object for the current
     * element.</p>
     *
     * <p>The method may only be called by the startElement event
     * or the endElement event
     * of the {@link org.xml.sax.ContentHandler} that the application sets to
     * the {@link ValidatorHandler}.</p>
     *
     * <p>When W3C XML Schema validation is being performed, in the
     * case where an element has a union type, the {@link TypeInfo}
     * returned by a call to <code>getElementTypeInfo()</code> from the
     * startElement
     * event will be the union type. The <code>TypeInfo</code>
     * returned by a call
     * from the endElement event will be the actual member type used
     * to validate the element.</p>
     *
     * <p>
     *  <p>返回当前元素的不可变{@link TypeInfo}对象。</p>
     * 
     *  <p>该方法只能由应用程序设置为{@link ValidatorHandler}的{@link org.xml.sax.ContentHandler}的startElement事件或endElemen
     * t事件调用。
     * </p>。
     * 
     *  <p>当执行W3C XML模式验证时,如果元素具有union类型,则从startElement事件调用<code> getElementTypeInfo()</code>返回的{@link TypeInfo}
     * 将为联合类型。
     * 从endElement事件调用返回的<code> TypeInfo </code>将是用于验证元素的实际成员类型。</p>。
     * 
     * 
     * @throws IllegalStateException
     *      If this method is called from other {@link org.xml.sax.ContentHandler}
     *      methods.
     * @return
     *      An immutable {@link TypeInfo} object that represents the
     *      type of the current element.
     *      Note that the caller can keep references to the obtained
     *      {@link TypeInfo} longer than the callback scope.
     *
     *      Otherwise, this method returns
     *      null if the validator is unable to
     *      determine the type of the current element for some reason
     *      (for example, if the validator is recovering from
     *      an earlier error.)
     *
     */
    public abstract TypeInfo getElementTypeInfo();

    /**
     * Returns the immutable {@link TypeInfo} object for the specified
     * attribute of the current element.
     *
     * <p>
     * The method may only be called by the startElement event of
     * the {@link org.xml.sax.ContentHandler} that the application sets to the
     * {@link ValidatorHandler}.</p>
     *
     * <p>
     *  返回当前元素的指定属性的不可变{@link TypeInfo}对象。
     * 
     * <p>
     * 该方法只能由{@link org.xml.sax.ContentHandler}的应用程序设置为{@link ValidatorHandler}的startElement事件调用。</p>
     * 
     * 
     * @param index
     *      The index of the attribute. The same index for
     *      the {@link org.xml.sax.Attributes} object passed to the
     *      <code>startElement</code> callback.
     *
     * @throws IndexOutOfBoundsException
     *      If the index is invalid.
     * @throws IllegalStateException
     *      If this method is called from other {@link org.xml.sax.ContentHandler}
     *      methods.
     *
     * @return
     *      An immutable {@link TypeInfo} object that represents the
     *      type of the specified attribute.
     *      Note that the caller can keep references to the obtained
     *      {@link TypeInfo} longer than the callback scope.
     *
     *      Otherwise, this method returns
     *      null if the validator is unable to
     *      determine the type.
     */
    public abstract TypeInfo getAttributeTypeInfo(int index);

    /**
     * Returns <code>true</code> if the specified attribute is determined
     * to be ID.
     *
     * <p>
     * Exacly how an attribute is "determined to be ID" is up to the
     * schema language. In case of W3C XML Schema, this means
     * that the actual type of the attribute is the built-in ID type
     * or its derived type.
     *
     * <p>
     * A {@link javax.xml.parsers.DocumentBuilder} uses this information
     * to properly implement {@link org.w3c.dom.Attr#isId()}.
     *
     * <p>
     * The method may only be called by the startElement event of
     * the {@link org.xml.sax.ContentHandler} that the application sets to the
     * {@link ValidatorHandler}.
     *
     * <p>
     *  如果指定的属性被确定为ID,则返回<code> true </code>。
     * 
     * <p>
     *  属性"确定为ID"的属性取决于模式语言。在W3C XML模式的情况下,这意味着属性的实际类型是内置ID类型或其派生类型。
     * 
     * <p>
     *  {@link javax.xml.parsers.DocumentBuilder}使用此信息来正确实现{@link org.w3c.dom.Attr#isId()}。
     * 
     * <p>
     *  该方法只能由应用程序设置为{@link ValidatorHandler}的{@link org.xml.sax.ContentHandler}的startElement事件调用。
     * 
     * 
     * @param index
     *      The index of the attribute. The same index for
     *      the {@link org.xml.sax.Attributes} object passed to the
     *      <code>startElement</code> callback.
     *
     * @throws IndexOutOfBoundsException
     *      If the index is invalid.
     * @throws IllegalStateException
     *      If this method is called from other {@link org.xml.sax.ContentHandler}
     *      methods.
     *
     * @return true
     *      if the type of the specified attribute is ID.
     */
    public abstract boolean isIdAttribute(int index);

    /**
     * Returns <code>false</code> if the attribute was added by the validator.
     *
     * <p>
     * This method provides information necessary for
     * a {@link javax.xml.parsers.DocumentBuilder} to determine what
     * the DOM tree should return from the {@link org.w3c.dom.Attr#getSpecified()} method.
     *
     * <p>
     * The method may only be called by the startElement event of
     * the {@link org.xml.sax.ContentHandler} that the application sets to the
     * {@link ValidatorHandler}.
     *
     * <p>
     * A general guideline for validators is to return true if
     * the attribute was originally present in the pipeline, and
     * false if it was added by the validator.
     *
     * <p>
     *  如果属性由验证程序添加,则返回<code> false </code>。
     * 
     * <p>
     *  此方法提供{@link javax.xml.parsers.DocumentBuilder}确定DOM树应从{@link org.w3c.dom.Attr#getSpecified()}方法返回的信息
     * 。
     * 
     * 
     * @param index
     *      The index of the attribute. The same index for
     *      the {@link org.xml.sax.Attributes} object passed to the
     *      <code>startElement</code> callback.
     *
     * @throws IndexOutOfBoundsException
     *      If the index is invalid.
     * @throws IllegalStateException
     *      If this method is called from other {@link org.xml.sax.ContentHandler}
     *      methods.
     *
     * @return
     *      <code>true</code> if the attribute was present before the validator
     *      processes input. <code>false</code> if the attribute was added
     *      by the validator.
     */
    public abstract boolean isSpecified(int index);
}
