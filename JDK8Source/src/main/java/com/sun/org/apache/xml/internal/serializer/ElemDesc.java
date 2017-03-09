/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ElemDesc.java,v 1.2.4.1 2005/09/15 08:15:15 suresh_emailid Exp $
 * <p>
 *  $ Id：ElemDesc.java,v 1.2.4.1 2005/09/15 08:15:15 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

/**
 * This class has a series of flags (bit values) that describe an HTML element
 *
 * This class is public because XSLTC uses it, it is not a public API.
 *
 * @xsl.usage internal
 * <p>
 *  这个类有一系列描述HTML元素的标志(位值)
 * 
 *  这个类是public的,因为XSLTC使用它,它不是一个公共API。
 * 
 *  @ xsl.usage internal
 * 
 */
public final class ElemDesc
{
    /** Bit flags to tell about this element type. */
    private int m_flags;

    /**
     * Table of attribute names to integers, which contain bit flags telling about
     *  the attributes.
     * <p>
     *  属性名称到整数的表,其包含告知属性的位标志。
     * 
     */
    private StringToIntTable m_attrs = null;

    /** Bit position if this element type is empty. */
    static final int EMPTY = (1 << 1);

    /** Bit position if this element type is a flow. */
    private static final int FLOW = (1 << 2);

    /** Bit position if this element type is a block. */
    static final int BLOCK = (1 << 3);

    /** Bit position if this element type is a block form. */
    static final int BLOCKFORM = (1 << 4);

    /** Bit position if this element type is a block form field set. */
    static final int BLOCKFORMFIELDSET = (1 << 5);

    /** Bit position if this element type is CDATA. */
    private static final int CDATA = (1 << 6);

    /** Bit position if this element type is PCDATA. */
    private static final int PCDATA = (1 << 7);

    /** Bit position if this element type is should be raw characters. */
    static final int RAW = (1 << 8);

    /** Bit position if this element type should be inlined. */
    private static final int INLINE = (1 << 9);

    /** Bit position if this element type is INLINEA. */
    private static final int INLINEA = (1 << 10);

    /** Bit position if this element type is an inline label. */
    static final int INLINELABEL = (1 << 11);

    /** Bit position if this element type is a font style. */
    static final int FONTSTYLE = (1 << 12);

    /** Bit position if this element type is a phrase. */
    static final int PHRASE = (1 << 13);

    /** Bit position if this element type is a form control. */
    static final int FORMCTRL = (1 << 14);

    /** Bit position if this element type is ???. */
    static final int SPECIAL = (1 << 15);

    /** Bit position if this element type is ???. */
    static final int ASPECIAL = (1 << 16);

    /** Bit position if this element type is an odd header element. */
    static final int HEADMISC = (1 << 17);

    /** Bit position if this element type is a head element (i.e. H1, H2, etc.) */
    static final int HEAD = (1 << 18);

    /** Bit position if this element type is a list. */
    static final int LIST = (1 << 19);

    /** Bit position if this element type is a preformatted type. */
    static final int PREFORMATTED = (1 << 20);

    /** Bit position if this element type is whitespace sensitive. */
    static final int WHITESPACESENSITIVE = (1 << 21);

    /** Bit position if this element type is a header element (i.e. HEAD). */
    static final int HEADELEM = (1 << 22);

    /** Bit position if this element is the "HTML" element */
    private static final int HTMLELEM = (1 << 23);

    /** Bit position if this attribute type is a URL. */
    public static final int ATTRURL = (1 << 1);

    /** Bit position if this attribute type is an empty type. */
    public static final int ATTREMPTY = (1 << 2);

    /**
     * Construct an ElemDesc from a set of bit flags.
     *
     *
     * <p>
     *  从一组位标志构造ElemDesc。
     * 
     * 
     * @param flags Bit flags that describe the basic properties of this element type.
     */
    ElemDesc(int flags)
    {
        m_flags = flags;
    }

    /**
     * Tell if this element type has the basic bit properties that are passed
     * as an argument.
     *
     * <p>
     *  告诉这个元素类型是否有作为参数传递的基本位属性。
     * 
     * 
     * @param flags Bit flags that describe the basic properties of interest.
     *
     * @return true if any of the flag bits are true.
     */
    private boolean is(int flags)
    {

        // int which = (m_flags & flags);
        return (m_flags & flags) != 0;
    }

    int getFlags() {
        return m_flags;
    }

    /**
     * Set an attribute name and it's bit properties.
     *
     *
     * <p>
     *  设置属性名称及其位属性。
     * 
     * 
     * @param name non-null name of attribute, in upper case.
     * @param flags flag bits.
     */
    void setAttr(String name, int flags)
    {

        if (null == m_attrs)
            m_attrs = new StringToIntTable();

        m_attrs.put(name, flags);
    }

    /**
     * Tell if any of the bits of interest are set for a named attribute type.
     *
     * <p>
     *  告诉是否为命名的属性类型设置了任何感兴趣的位。
     * 
     * @param name non-null reference to attribute name, in any case.
     * @param flags flag mask.
     *
     * @return true if any of the flags are set for the named attribute.
     */
    public boolean isAttrFlagSet(String name, int flags)
    {
        return (null != m_attrs)
            ? ((m_attrs.getIgnoreCase(name) & flags) != 0)
            : false;
    }
}
