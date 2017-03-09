/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni;

import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDContentModelSource;

/**
 * The DTD content model handler interface defines callback methods
 * to report information items in DTD content models of an element
 * declaration. Parser components interested in DTD content model
 * information implement this interface and are registered as the DTD
 * content model handler on the DTD content model source.
 *
 * <p>
 *  DTD内容模型处理程序接口定义回调方法以报告元素声明的DTD内容模型中的信息项。对DTD内容模型信息感兴趣的解析器组件实现此接口,并在DTD内容模型源上注册为DTD内容模型处理程序。
 * 
 * 
 * @see XMLDTDHandler
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLDTDContentModelHandler {

    //
    // Constants
    //

    // separators

    /**
     * A choice separator for children and mixed content models. This
     * separator is used to specify that the allowed child is one of a
     * collection.
     * <p>
     * For example:
     * <pre>
     * &lt;!ELEMENT elem (foo|bar)&gt;
     * &lt;!ELEMENT elem (foo|bar+)&gt;
     * &lt;!ELEMENT elem (foo|bar|baz)&gt;
     * &lt;!ELEMENT elem (#PCDATA|foo|bar)*&gt;
     * </pre>
     *
     * <p>
     *  儿童和混合内容模型的选择分隔符。此分隔符用于指定允许的子项是集合之一。
     * <p>
     *  例如：
     * <pre>
     *  &lt;！ELEMENT elem(foo | bar)&gt; &lt;！ELEMENT elem(foo | bar +)&gt; &lt;！ELEMENT elem(foo | bar | ba
     * z)&gt; &lt;！ELEMENT elem(#PCDATA | foo | bar)*&gt;。
     * </pre>
     * 
     * 
     * @see #SEPARATOR_SEQUENCE
     */
    public static final short SEPARATOR_CHOICE = 0;

    /**
     * A sequence separator for children content models. This separator
     * is used to specify that the allowed children must follow in the
     * specified sequence.
     * <p>
     * <pre>
     * &lt;!ELEMENT elem (foo,bar)&gt;
     * &lt;!ELEMENT elem (foo,bar*)&gt;
     * &lt;!ELEMENT elem (foo,bar,baz)&gt;
     * </pre>
     *
     * <p>
     *  儿童内容模型的序列分隔符。此分隔符用于指定允许的孩子必须遵循指定的顺序。
     * <p>
     * <pre>
     * &lt;！ELEMENT elem(foo,bar)&gt; &lt;！ELEMENT elem(foo,bar *)&gt; &lt;！ELEMENT elem(foo,bar,baz)&gt;
     * </pre>
     * 
     * 
     * @see #SEPARATOR_CHOICE
     */
    public static final short SEPARATOR_SEQUENCE = 1;

    // occurrence counts

    /**
     * This occurrence count limits the element, choice, or sequence in a
     * children content model to zero or one. In other words, the child
     * is optional.
     * <p>
     * For example:
     * <pre>
     * &lt;!ELEMENT elem (foo?)&gt;
     * </pre>
     *
     * <p>
     *  此发生次数将子级内容模型中的元素,选项或序列限制为零或一。换句话说,孩子是可选的。
     * <p>
     *  例如：
     * <pre>
     *  &lt;！ELEMENT elem(foo?)&gt;
     * </pre>
     * 
     * 
     * @see #OCCURS_ZERO_OR_MORE
     * @see #OCCURS_ONE_OR_MORE
     */
    public static final short OCCURS_ZERO_OR_ONE = 2;

    /**
     * This occurrence count limits the element, choice, or sequence in a
     * children content model to zero or more. In other words, the child
     * may appear an arbitrary number of times, or not at all. This
     * occurrence count is also used for mixed content models.
     * <p>
     * For example:
     * <pre>
     * &lt;!ELEMENT elem (foo*)&gt;
     * &lt;!ELEMENT elem (#PCDATA|foo|bar)*&gt;
     * </pre>
     *
     * <p>
     *  此出现次数将儿童内容模型中的元素,选项或序列限制为零或更多。换句话说,孩子可以出现任意次数,或者根本不出现。此事件计数也用于混合内容模型。
     * <p>
     *  例如：
     * <pre>
     *  &lt;！ELEMENT elem(foo *)&gt; &lt;！ELEMENT elem(#PCDATA | foo | bar)*&gt;
     * </pre>
     * 
     * 
     * @see #OCCURS_ZERO_OR_ONE
     * @see #OCCURS_ONE_OR_MORE
     */
    public static final short OCCURS_ZERO_OR_MORE = 3;

    /**
     * This occurrence count limits the element, choice, or sequence in a
     * children content model to one or more. In other words, the child
     * may appear an arbitrary number of times, but must appear at least
     * once.
     * <p>
     * For example:
     * <pre>
     * &lt;!ELEMENT elem (foo+)&gt;
     * </pre>
     *
     * <p>
     *  此出现次数将子级内容模型中的元素,选项或序列限制为一个或多个。换句话说,孩子可以出现任意次数,但必须出现至少一次。
     * <p>
     *  例如：
     * <pre>
     *  &lt;！ELEMENT elem(foo +)&gt;
     * </pre>
     * 
     * 
     * @see #OCCURS_ZERO_OR_ONE
     * @see #OCCURS_ZERO_OR_MORE
     */
    public static final short OCCURS_ONE_OR_MORE = 4;

    //
    // XMLDTDContentModelHandler methods
    //

    /**
     * The start of a content model. Depending on the type of the content
     * model, specific methods may be called between the call to the
     * startContentModel method and the call to the endContentModel method.
     *
     * <p>
     *  内容模型的开始。根据内容模型的类型,可以在调用startContentModel方法和调用endContentModel方法之间调用特定方法。
     * 
     * 
     * @param elementName The name of the element.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startContentModel(String elementName, Augmentations augmentations)
        throws XNIException;

    /**
     * A content model of ANY.
     *
     * <p>
     *  ANY的内容模型。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #empty
     * @see #startGroup
     */
    public void any(Augmentations augmentations) throws XNIException;

    /**
     * A content model of EMPTY.
     *
     * <p>
     *  EMPTY的内容模型。
     * 
     * 
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @see #any
     * @see #startGroup
     */
    public void empty(Augmentations augmentations) throws XNIException;

    /**
     * A start of either a mixed or children content model. A mixed
     * content model will immediately be followed by a call to the
     * <code>pcdata()</code> method. A children content model will
     * contain additional groups and/or elements.
     *
     * <p>
     *  混合或儿童内容模型的开始。混合内容模型将立即调用<code> pcdata()</code>方法。子内容模型将包含其他组和/或元素。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #any
     * @see #empty
     */
    public void startGroup(Augmentations augmentations) throws XNIException;

    /**
     * The appearance of "#PCDATA" within a group signifying a
     * mixed content model. This method will be the first called
     * following the content model's <code>startGroup()</code>.
     *
     * <p>
     * 表示混合内容模型的组中的"#PCDATA"的外观。这个方法将首先被调用遵循内容模型的<code> startGroup()</code>。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #startGroup
     */
    public void pcdata(Augmentations augmentations) throws XNIException;

    /**
     * A referenced element in a mixed or children content model.
     *
     * <p>
     *  混合或子内容模型中引用的元素。
     * 
     * 
     * @param elementName The name of the referenced element.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void element(String elementName, Augmentations augmentations)
        throws XNIException;

    /**
     * The separator between choices or sequences of a mixed or children
     * content model.
     *
     * <p>
     *  混合或子内容模型的选择或序列之间的分隔符。
     * 
     * 
     * @param separator The type of children separator.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #SEPARATOR_CHOICE
     * @see #SEPARATOR_SEQUENCE
     */
    public void separator(short separator, Augmentations augmentations)
        throws XNIException;

    /**
     * The occurrence count for a child in a children content model or
     * for the mixed content model group.
     *
     * <p>
     *  子内容模型中的子级或混合内容模型组的子级的发生计数。
     * 
     * 
     * @param occurrence The occurrence count for the last element
     *                   or group.
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #OCCURS_ZERO_OR_ONE
     * @see #OCCURS_ZERO_OR_MORE
     * @see #OCCURS_ONE_OR_MORE
     */
    public void occurrence(short occurrence, Augmentations augmentations)
        throws XNIException;

    /**
     * The end of a group for mixed or children content models.
     *
     * <p>
     *  一个组的结尾的混合或儿童内容模型。
     * 
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endGroup(Augmentations augmentations) throws XNIException;

    /**
     * The end of a content model.
     *
     * <p>
     *  内容模型的结束。
     * 
     * @param augmentations Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endContentModel(Augmentations augmentations) throws XNIException;

    // set content model source
    public void setDTDContentModelSource(XMLDTDContentModelSource source);

    // get content model source
    public XMLDTDContentModelSource getDTDContentModelSource();

} // interface XMLDTDContentModelHandler
