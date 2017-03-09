/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002, 2003,2004 The Apache Software Foundation.
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
 *  版权所有2002,2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs;

import com.sun.org.apache.xerces.internal.util.XMLResourceIdentifierImpl;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLSchemaDescription;

/**
 * All information specific to XML Schema grammars.
 *
 * @xerces.internal
 *
 * <p>
 *  所有特定于XML模式语法的信息。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neil Graham, IBM
 * @author Neeraj Bajaj, SUN Microsystems.
 *
 * @version $Id: XSDDescription.java,v 1.6 2010-11-01 04:39:55 joehw Exp $
 */
public class XSDDescription extends XMLResourceIdentifierImpl
                implements XMLSchemaDescription {
    // used to indicate what triggered the call
    /**
     * Indicate that this description was just initialized.
     * <p>
     *  指示此描述刚刚初始化。
     * 
     */
    public final static short CONTEXT_INITIALIZE = -1;
    /**
     * Indicate that the current schema document is <include>d by another
     * schema document.
     * <p>
     *  指示当前模式文档是由另一个模式文档<include> d。
     * 
     */
    public final static short CONTEXT_INCLUDE   = 0;
    /**
     * Indicate that the current schema document is <redefine>d by another
     * schema document.
     * <p>
     *  指示当前模式文档是由另一个模式文档<redefine> d。
     * 
     */
    public final static short CONTEXT_REDEFINE  = 1;
    /**
     * Indicate that the current schema document is <import>ed by another
     * schema document.
     * <p>
     *  指示当前模式文档由另一个模式文档进行<import> ed。
     * 
     */
    public final static short CONTEXT_IMPORT    = 2;
    /**
     * Indicate that the current schema document is being preparsed.
     * <p>
     *  指示正在初始化当前模式文档。
     * 
     */
    public final static short CONTEXT_PREPARSE  = 3;
    /**
     * Indicate that the parse of the current schema document is triggered
     * by xsi:schemaLocation/noNamespaceSchemaLocation attribute(s) in the
     * instance document. This value is only used if we don't defer the loading
     * of schema documents.
     * <p>
     *  指示当前模式文档的解析是由实例文档中的xsi：schemaLocation / noNamespaceSchemaLocation属性触发的。仅当我们不推迟加载模式文档时,才使用此值。
     * 
     */
    public final static short CONTEXT_INSTANCE  = 4;
    /**
     * Indicate that the parse of the current schema document is triggered by
     * the occurrence of an element whose namespace is the target namespace
     * of this schema document. This value is only used if we do defer the
     * loading of schema documents until a component from that namespace is
     * referenced from the instance.
     * <p>
     * 指示当前模式文档的解析是由其命名空间是此模式文档的目标命名空间的元素的发生触发的。仅当我们延迟加载模式文档,直到从该实例引用来自该命名空间的组件时,才使用此值。
     * 
     */
    public final static short CONTEXT_ELEMENT   = 5;
    /**
     * Indicate that the parse of the current schema document is triggered by
     * the occurrence of an attribute whose namespace is the target namespace
     * of this schema document. This value is only used if we do defer the
     * loading of schema documents until a component from that namespace is
     * referenced from the instance.
     * <p>
     *  指示当前模式文档的解析是由其命名空间是此模式文档的目标命名空间的属性的出现触发的。仅当我们延迟加载模式文档,直到从该实例引用来自该命名空间的组件时,才使用此值。
     * 
     */
    public final static short CONTEXT_ATTRIBUTE = 6;
    /**
     * Indicate that the parse of the current schema document is triggered by
     * the occurrence of an "xsi:type" attribute, whose value (a QName) has
     * the target namespace of this schema document as its namespace.
     * This value is only used if we do defer the loading of schema documents
     * until a component from that namespace is referenced from the instance.
     * <p>
     *  指示当前模式文档的解析是由"xsi：type"属性(其值(QName)具有此模式文档的目标命名空间作为其命名空间)的出现触发的。
     * 仅当我们延迟加载模式文档,直到从该实例引用来自该命名空间的组件时,才使用此值。
     * 
     */
    public final static short CONTEXT_XSITYPE   = 7;

    // REVISIT: write description of these fields
    protected short fContextType;
    protected String [] fLocationHints ;
    protected QName fTriggeringComponent;
    protected QName fEnclosedElementName;
    protected XMLAttributes  fAttributes;

    /**
     * the type of the grammar (e.g., DTD or XSD);
     *
     * <p>
     *  语法的类型(例如,DTD或XSD);
     * 
     * 
     * @see com.sun.org.apache.xerces.internal.xni.grammars.Grammar
     */
    public String getGrammarType() {
        return XMLGrammarDescription.XML_SCHEMA;
    }

    /**
     * Get the context. The returned value is one of the pre-defined
     * CONTEXT_xxx constants.
     *
     * <p>
     *  获取上下文。返回的值是预定义的CONTEXT_xxx常量之一。
     * 
     * 
     * @return  the value indicating the context
     */
    public short getContextType() {
        return fContextType ;
    }

    /**
     * If the context is "include" or "redefine", then return the target
     * namespace of the enclosing schema document; otherwise, the expected
     * target namespace of this document.
     *
     * <p>
     *  如果上下文是"include"或"redefine",则返回包含模式文档的目标命名空间;否则,此文档的预期目标命名空间。
     * 
     * 
     * @return  the expected/enclosing target namespace
     */
    public String getTargetNamespace() {
        return fNamespace;
    }

    /**
     * For import and references from the instance document, it's possible to
     * have multiple hints for one namespace. So this method returns an array,
     * which contains all location hints.
     *
     * <p>
     *  对于从实例文档的导入和引用,可能有一个命名空间的多个提示。因此,此方法返回一个数组,其中包含所有位置提示。
     * 
     * 
     * @return  an array of all location hints associated to the expected
     *          target namespace
     */
    public String[] getLocationHints() {
        return fLocationHints ;
    }

    /**
     * If a call is triggered by an element/attribute/xsi:type in the instance,
     * this call returns the name of such triggering component: the name of
     * the element/attribute, or the value of the xsi:type.
     *
     * <p>
     * 如果调用由实例中的元素/属性/ xsi：type触发,则此调用将返回此触发组件的名称：元素/属性的名称或xsi：type的值。
     * 
     * 
     * @return  the name of the triggering component
     */
    public QName getTriggeringComponent() {
        return fTriggeringComponent ;
    }

    /**
     * If a call is triggered by an attribute or xsi:type, then this mehtod
     * returns the enclosing element of such element.
     *
     * <p>
     *  如果调用由属性或xsi：type触发,则此mehtod返回此元素的包围元素。
     * 
     * 
     * @return  the name of the enclosing element
     */
    public QName getEnclosingElementName() {
        return fEnclosedElementName ;
    }

    /**
     * If a call is triggered by an element/attribute/xsi:type in the instance,
     * this call returns all attribute of such element (or enclosing element).
     *
     * <p>
     *  如果调用由实例中的元素/属性/ xsi：type触发,则此调用返回此元素(或包含元素)的所有属性。
     * 
     * 
     * @return  all attributes of the tiggering/enclosing element
     */
    public XMLAttributes getAttributes() {
        return fAttributes;
    }

    public boolean fromInstance() {
        return fContextType == CONTEXT_ATTRIBUTE ||
               fContextType == CONTEXT_ELEMENT ||
               fContextType == CONTEXT_INSTANCE ||
               fContextType == CONTEXT_XSITYPE;
    }

    /**
    /* <p>
    /* 
     * @return true is the schema is external
     */
    public boolean isExternal() {
        return fContextType == CONTEXT_INCLUDE ||
               fContextType == CONTEXT_REDEFINE ||
               fContextType == CONTEXT_IMPORT ||
               fContextType == CONTEXT_ELEMENT ||
               fContextType == CONTEXT_ATTRIBUTE ||
               fContextType == CONTEXT_XSITYPE;
    }
    /**
     * Compares this grammar with the given grammar. Currently, we compare
     * the target namespaces.
     *
     * <p>
     *  将这种语法与给定的语法相比较。目前,我们比较目标命名空间。
     * 
     * 
     * @param descObj The description of the grammar to be compared with
     * @return        True if they are equal, else false
     */
    public boolean equals(Object descObj) {
        if(!(descObj instanceof XMLSchemaDescription)) return false;
        XMLSchemaDescription desc = (XMLSchemaDescription)descObj;
        if (fNamespace != null)
            return fNamespace.equals(desc.getTargetNamespace());
        else // fNamespace == null
            return desc.getTargetNamespace() == null;
    }

    /**
     * Returns the hash code of this grammar
     *
     * <p>
     *  返回此语法的哈希码
     * 
     * 
     * @return The hash code
     */
    public int hashCode() {
         return (fNamespace == null) ? 0 : fNamespace.hashCode();
    }

    public void setContextType(short contextType){
        fContextType = contextType ;
    }

    public void setTargetNamespace(String targetNamespace){
        fNamespace = targetNamespace ;
    }

    public void setLocationHints(String [] locationHints){
        int length = locationHints.length ;
        fLocationHints  = new String[length];
        System.arraycopy(locationHints, 0, fLocationHints, 0, length);
        //fLocationHints = locationHints ;
    }

    public void setTriggeringComponent(QName triggeringComponent){
        fTriggeringComponent = triggeringComponent ;
    }

    public void setEnclosingElementName(QName enclosedElementName){
        fEnclosedElementName = enclosedElementName ;
    }

    public void setAttributes(XMLAttributes attributes){
        fAttributes = attributes ;
    }

    /**
     *  resets all the fields
     * <p>
     *  重置所有字段
     */
    public void reset(){
        super.clear();
        fContextType = CONTEXT_INITIALIZE;
        fLocationHints  = null ;
        fTriggeringComponent = null ;
        fEnclosedElementName = null ;
        fAttributes = null ;
    }

    public XSDDescription makeClone() {
        XSDDescription desc = new XSDDescription();
        desc.fAttributes = this.fAttributes;
        desc.fBaseSystemId = this.fBaseSystemId;
        desc.fContextType = this.fContextType;
        desc.fEnclosedElementName = this.fEnclosedElementName;
        desc.fExpandedSystemId = this.fExpandedSystemId;
        desc.fLiteralSystemId = this.fLiteralSystemId;
        desc.fLocationHints = this.fLocationHints;
        desc.fPublicId = this.fPublicId;
        desc.fNamespace = this.fNamespace;
        desc.fTriggeringComponent = this.fTriggeringComponent;
        return desc;
    }

} // XSDDescription
