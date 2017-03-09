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
 * $Id: AttributesImplSerializer.java,v 1.2.4.1 2005/09/15 08:15:14 suresh_emailid Exp $
 * <p>
 *  $ Id：AttributesImplSerializer.java,v 1.2.4.1 2005/09/15 08:15:14 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.serializer;

import java.util.Hashtable;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * This class extends org.xml.sax.helpers.AttributesImpl which implements org.
 * xml.sax.Attributes. But for optimization this class adds a Hashtable for
 * faster lookup of an index by qName, which is commonly done in the stream
 * serializer.
 *
 * <p>
 *  这个类扩展了实现org的org.xml.sax.helpers.AttributesImpl。 xml.sax.Attributes。
 * 但是对于优化,这个类添加了一个Hashtable,用于通过qName更快地查找索引,这通常在流串行器中完成。
 * 
 * 
 * @see org.xml.sax.Attributes
 *
 * @xsl.usage internal
 */
public final class AttributesImplSerializer extends AttributesImpl
{
    /**
     * Hash table of qName/index values to quickly lookup the index
     * of an attributes qName.  qNames are in uppercase in the hash table
     * to make the search case insensitive.
     *
     * The keys to the hashtable to find the index are either
     * "prefix:localName"  or "{uri}localName".
     * <p>
     *  qName / index值的哈希表,以快速查找属性qName的索引。 qNames在哈希表中大写,以使搜索不区分大小写。
     * 
     *  哈希表找到索引的键是"prefix：localName"或"{uri} localName"。
     * 
     */
    private final Hashtable m_indexFromQName = new Hashtable();

    private final StringBuffer m_buff = new StringBuffer();

    /**
     * This is the number of attributes before switching to the hash table,
     * and can be tuned, but 12 seems good for now - Brian M.
     * <p>
     *  这是切换到散列表之前的属性数量,可以调整,但现在12似乎很好 -  Brian M.
     * 
     */
    private static final int MAX = 12;

    /**
     * One less than the number of attributes before switching to
     * the Hashtable.
     * <p>
     * 一个小于切换到Hashtable之前的属性数。
     * 
     */
    private static final int MAXMinus1 = MAX - 1;

    /**
     * This method gets the index of an attribute given its qName.
     * <p>
     *  此方法获取属性的索引给定其qName。
     * 
     * 
     * @param qname the qualified name of the attribute, e.g. "prefix1:locName1"
     * @return the integer index of the attribute.
     * @see org.xml.sax.Attributes#getIndex(String)
     */
    public final int getIndex(String qname)
    {
        int index;

        if (super.getLength() < MAX)
        {
            // if we haven't got too many attributes let the
            // super class look it up
            index = super.getIndex(qname);
            return index;
        }
        // we have too many attributes and the super class is slow
        // so find it quickly using our Hashtable.
        Integer i = (Integer)m_indexFromQName.get(qname);
        if (i == null)
            index = -1;
        else
            index = i.intValue();
        return index;
    }
    /**
     * This method adds the attribute, but also records its qName/index pair in
     * the hashtable for fast lookup by getIndex(qName).
     * <p>
     *  此方法添加属性,但也将其qName /索引对记录在散列表中,以便通过getIndex(qName)快速查找。
     * 
     * 
     * @param uri the URI of the attribute
     * @param local the local name of the attribute
     * @param qname the qualified name of the attribute
     * @param type the type of the attribute
     * @param val the value of the attribute
     *
     * @see org.xml.sax.helpers.AttributesImpl#addAttribute(String, String, String, String, String)
     * @see #getIndex(String)
     */
    public final void addAttribute(
        String uri,
        String local,
        String qname,
        String type,
        String val)
    {
        int index = super.getLength();
        super.addAttribute(uri, local, qname, type, val);
        // (index + 1) is now the number of attributes
        // so either compare (index+1) to MAX, or compare index to (MAX-1)

        if (index < MAXMinus1)
        {
            return;
        }
        else if (index == MAXMinus1)
        {
            switchOverToHash(MAX);
        }
        else
        {
            /* add the key with the format of "prefix:localName" */
            /* we have just added the attibute, its index is the old length */
            Integer i = new Integer(index);
            m_indexFromQName.put(qname, i);

            /* now add with key of the format "{uri}localName" */
            m_buff.setLength(0);
            m_buff.append('{').append(uri).append('}').append(local);
            String key = m_buff.toString();
            m_indexFromQName.put(key, i);
        }
        return;
    }

    /**
     * We are switching over to having a hash table for quick look
     * up of attributes, but up until now we haven't kept any
     * information in the Hashtable, so we now update the Hashtable.
     * Future additional attributes will update the Hashtable as
     * they are added.
     * <p>
     *  我们正在切换到具有快速查找属性的哈希表,但是直到现在我们还没有在Hashtable中保存任何信息,所以我们现在更新Hashtable。未来的附加属性将更新Hashtable,因为它们被添加。
     * 
     * 
     * @param numAtts
     */
    private void switchOverToHash(int numAtts)
    {
        for (int index = 0; index < numAtts; index++)
        {
            String qName = super.getQName(index);
            Integer i = new Integer(index);
            m_indexFromQName.put(qName, i);

            // Add quick look-up to find with uri/local name pair
            String uri = super.getURI(index);
            String local = super.getLocalName(index);
            m_buff.setLength(0);
            m_buff.append('{').append(uri).append('}').append(local);
            String key = m_buff.toString();
            m_indexFromQName.put(key, i);
        }
    }

    /**
     * This method clears the accumulated attributes.
     *
     * <p>
     *  此方法清除累积的属性。
     * 
     * 
     * @see org.xml.sax.helpers.AttributesImpl#clear()
     */
    public final void clear()
    {

        int len = super.getLength();
        super.clear();
        if (MAX <= len)
        {
            // if we have had enough attributes and are
            // using the Hashtable, then clear the Hashtable too.
            m_indexFromQName.clear();
        }

    }

    /**
     * This method sets the attributes, previous attributes are cleared,
     * it also keeps the hashtable up to date for quick lookup via
     * getIndex(qName).
     * <p>
     *  此方法设置属性,以前的属性被清除,它也保持哈希表最新的快速查找通过getIndex(qName)。
     * 
     * 
     * @param atts the attributes to copy into these attributes.
     * @see org.xml.sax.helpers.AttributesImpl#setAttributes(Attributes)
     * @see #getIndex(String)
     */
    public final void setAttributes(Attributes atts)
    {

        super.setAttributes(atts);

        // we've let the super class add the attributes, but
        // we need to keep the hash table up to date ourselves for the
        // potentially new qName/index pairs for quick lookup.
        int numAtts = atts.getLength();
        if (MAX <= numAtts)
            switchOverToHash(numAtts);

    }

    /**
     * This method gets the index of an attribute given its uri and locanName.
     * <p>
     *  此方法获取属性的索引给定其uri和locanName。
     * 
     * @param uri the URI of the attribute name.
     * @param localName the local namer (after the ':' ) of the attribute name.
     * @return the integer index of the attribute.
     * @see org.xml.sax.Attributes#getIndex(String)
     */
    public final int getIndex(String uri, String localName)
    {
        int index;

        if (super.getLength() < MAX)
        {
            // if we haven't got too many attributes let the
            // super class look it up
            index = super.getIndex(uri,localName);
            return index;
        }
        // we have too many attributes and the super class is slow
        // so find it quickly using our Hashtable.
        // Form the key of format "{uri}localName"
        m_buff.setLength(0);
        m_buff.append('{').append(uri).append('}').append(localName);
        String key = m_buff.toString();
        Integer i = (Integer)m_indexFromQName.get(key);
        if (i == null)
            index = -1;
        else
            index = i.intValue();
        return index;
    }
}
