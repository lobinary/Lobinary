/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XSLOutputAttributes.java,v 1.2.4.1 2005/09/15 08:15:32 suresh_emailid Exp $
 * <p>
 *  $ Id：XSLOutputAttributes.java,v 1.2.4.1 2005/09/15 08:15:32 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.util.Vector;

/**
 * This interface has methods associated with the XSLT xsl:output attribues
 * specified in the stylesheet that effect the format of the document output.
 *
 * In an XSLT stylesheet these attributes appear for example as:
 * <pre>
 * <xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>
 * </pre>
 * The xsl:output attributes covered in this interface are:
 * <pre>
 * version
 * encoding
 * omit-xml-declarations
 * standalone
 * doctype-public
 * doctype-system
 * cdata-section-elements
 * indent
 * media-type
 * </pre>
 *
 * The one attribute not covered in this interface is <code>method</code> as
 * this value is implicitly chosen by the serializer that is created, for
 * example ToXMLStream vs. ToHTMLStream or another one.
 *
 * This interface is only used internally within Xalan.
 *
 * @xsl.usage internal
 * <p>
 *  此接口具有与XSLT xsl：输出属性相关联的方法,这些属性在样式表中指定,以影响文档输出的格式。
 * 
 *  在XSLT样式表中,这些属性例如显示为：
 * <pre>
 * <xsl:output method="xml" omit-xml-declaration="no" indent="yes"/>
 * </pre>
 *  此接口中涉及的xsl：输出属性为：
 * <pre>
 *  版本编码omit-xml-declarations standalone doctype-public doctype-system cdata-section-elements indent med
 * ia-type。
 * </pre>
 * 
 *  此接口中未涵盖的一个属性是<code> method </code>,因为此值由创建的序列化器(例如ToXMLStream与ToHTMLStream或另一个)隐式选择。
 * 
 *  此接口仅在Xalan内部使用。
 * 
 *  @ xsl.usage internal
 * 
 */
interface XSLOutputAttributes
{
    /**
     * Returns the previously set value of the value to be used as the public
     * identifier in the document type declaration (DTD).
     *
     * <p>
     * 返回在文档类型声明(DTD)中用作公共标识符的值的先前设置值。
     * 
     * 
     *@return the public identifier to be used in the DOCTYPE declaration in the
     * output document.
     */
    public String getDoctypePublic();
    /**
     * Returns the previously set value of the value to be used
     * as the system identifier in the document type declaration (DTD).
     * <p>
     *  返回在文档类型声明(DTD)中用作系统标识符的值的先前设置值。
     * 
     * 
     * @return the system identifier to be used in the DOCTYPE declaration in
     * the output document.
     *
     */
    public String getDoctypeSystem();
    /**
    /* <p>
    /* 
     * @return the character encoding to be used in the output document.
     */
    public String getEncoding();
    /**
    /* <p>
    /* 
         * @return true if the output document should be indented to visually
         * indicate its structure.
     */
    public boolean getIndent();

    /**
    /* <p>
    /* 
     * @return the number of spaces to indent for each indentation level.
     */
    public int getIndentAmount();
    /**
    /* <p>
    /* 
     * @return the mediatype the media-type or MIME type associated with the
     * output document.
     */
    public String getMediaType();
    /**
    /* <p>
    /* 
     * @return true if the XML declaration is to be omitted from the output
     * document.
     */
    public boolean getOmitXMLDeclaration();
    /**
    /* <p>
    /* 
      * @return a value of "yes" if the <code>standalone</code> delaration is to
      * be included in the output document.
      */
    public String getStandalone();
    /**
    /* <p>
    /* 
     * @return the version of the output format.
     */
    public String getVersion();






    /**
     * Sets the value coming from the xsl:output cdata-section-elements
     * stylesheet property.
     *
     * This sets the elements whose text elements are to be output as CDATA
     * sections.
     * <p>
     *  设置来自xsl：output cdata-section-elements stylesheet属性的值。
     * 
     *  这将设置其文本元素将作为CDATA节输出的元素。
     * 
     * 
     * @param URI_and_localNames pairs of namespace URI and local names that
     * identify elements whose text elements are to be output as CDATA sections.
     * The namespace of the local element must be the given URI to match. The
     * qName is not given because the prefix does not matter, only the namespace
     * URI to which that prefix would map matters, so the prefix itself is not
     * relevant in specifying which elements have their text to be output as
     * CDATA sections.
     */
    public void setCdataSectionElements(Vector URI_and_localNames);

    /** Set the value coming from the xsl:output doctype-public and doctype-system stylesheet properties
    /* <p>
    /* 
     * @param system the system identifier to be used in the DOCTYPE declaration
     * in the output document.
     * @param pub the public identifier to be used in the DOCTYPE declaration in
     * the output document.
     */
    public void setDoctype(String system, String pub);

    /** Set the value coming from the xsl:output doctype-public stylesheet attribute.
    /* <p>
    /* 
      * @param doctype the public identifier to be used in the DOCTYPE
      * declaration in the output document.
      */
    public void setDoctypePublic(String doctype);
    /** Set the value coming from the xsl:output doctype-system stylesheet attribute.
    /* <p>
    /* 
      * @param doctype the system identifier to be used in the DOCTYPE
      * declaration in the output document.
      */
    public void setDoctypeSystem(String doctype);
    /**
     * Sets the character encoding coming from the xsl:output encoding stylesheet attribute.
     * <p>
     *  设置来自xsl：output编码样式表属性的字符编码。
     * 
     * 
     * @param encoding the character encoding
     */
    public void setEncoding(String encoding);
    /**
     * Sets the value coming from the xsl:output indent stylesheet
     * attribute.
     * <p>
     *  设置来自xsl：output indent stylesheet属性的值。
     * 
     * 
     * @param indent true if the output document should be indented to visually
     * indicate its structure.
     */
    public void setIndent(boolean indent);
    /**
     * Sets the value coming from the xsl:output media-type stylesheet attribute.
     * <p>
     *  设置来自xsl：output media-type stylesheet属性的值。
     * 
     * 
     * @param mediatype the media-type or MIME type associated with the output
     * document.
     */
    public void setMediaType(String mediatype);
    /**
     * Sets the value coming from the xsl:output omit-xml-declaration stylesheet attribute
     * <p>
     *  设置来自xsl：output omit-xml-declaration stylesheet属性的值
     * 
     * 
     * @param b true if the XML declaration is to be omitted from the output
     * document.
     */
    public void setOmitXMLDeclaration(boolean b);
    /**
     * Sets the value coming from the xsl:output standalone stylesheet attribute.
     * <p>
     *  设置来自xsl：output standalone stylesheet属性的值。
     * 
     * 
     * @param standalone a value of "yes" indicates that the
     * <code>standalone</code> delaration is to be included in the output
     * document.
     */
    public void setStandalone(String standalone);
    /**
     * Sets the value coming from the xsl:output version attribute.
     * <p>
     *  设置来自xsl：output version属性的值。
     * 
     * @param version the version of the output format.
     */
    public void setVersion(String version);

}
