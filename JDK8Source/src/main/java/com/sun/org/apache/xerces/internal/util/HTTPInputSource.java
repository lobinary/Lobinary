/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
 *  版权所有2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xerces.internal.util;

import java.io.InputStream;
import java.io.Reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;

/**
 * This class represents an input source for an XML resource
 * retrievable over HTTP. In addition to the properties
 * provided by an <code>XMLInputSource</code> an HTTP input
 * source also has HTTP request properties and a preference
 * whether HTTP redirects will be followed. Note that these
 * properties will only be used if reading this input source
 * will induce an HTTP connection.
 *
 * <p>
 *  此类表示通过HTTP检索的XML资源的输入源。除了由<code> XMLInputSource </code>提供的属性,HTTP输入源还具有HTTP请求属性和首选项,无论是否遵循HTTP重定向。
 * 请注意,只有读取此输入源才会引发HTTP连接,才会使用这些属性。
 * 
 * 
 * @author Michael Glavassevich, IBM
 *
 */
public final class HTTPInputSource extends XMLInputSource {

    //
    // Data
    //

    /** Preference for whether HTTP redirects should be followed. **/
    protected boolean fFollowRedirects = true;

    /** HTTP request properties. **/
    protected Map fHTTPRequestProperties = new HashMap();

    //
    // Constructors
    //

    /**
     * Constructs an input source from just the public and system
     * identifiers, leaving resolution of the entity and opening of
     * the input stream up to the caller.
     *
     * <p>
     *  从公共和系统标识符构造输入源,留下实体的解析度和打开输入流直到调用者。
     * 
     * 
     * @param publicId     The public identifier, if known.
     * @param systemId     The system identifier. This value should
     *                     always be set, if possible, and can be
     *                     relative or absolute. If the system identifier
     *                     is relative, then the base system identifier
     *                     should be set.
     * @param baseSystemId The base system identifier. This value should
     *                     always be set to the fully expanded URI of the
     *                     base system identifier, if possible.
     */
    public HTTPInputSource(String publicId, String systemId, String baseSystemId) {
        super(publicId, systemId, baseSystemId);
    } // <init>(String,String,String)

    /**
     * Constructs an input source from a XMLResourceIdentifier
     * object, leaving resolution of the entity and opening of
     * the input stream up to the caller.
     *
     * <p>
     *  从XMLResourceIdentifier对象构造输入源,留下实体的解析度和打开输入流直到调用者。
     * 
     * 
     * @param resourceIdentifier the XMLResourceIdentifier containing the information
     */
    public HTTPInputSource(XMLResourceIdentifier resourceIdentifier) {
        super(resourceIdentifier);
    } // <init>(XMLResourceIdentifier)

    /**
     * Constructs an input source from a byte stream.
     *
     * <p>
     *  从字节流构造输入源。
     * 
     * 
     * @param publicId     The public identifier, if known.
     * @param systemId     The system identifier. This value should
     *                     always be set, if possible, and can be
     *                     relative or absolute. If the system identifier
     *                     is relative, then the base system identifier
     *                     should be set.
     * @param baseSystemId The base system identifier. This value should
     *                     always be set to the fully expanded URI of the
     *                     base system identifier, if possible.
     * @param byteStream   The byte stream.
     * @param encoding     The encoding of the byte stream, if known.
     */
    public HTTPInputSource(String publicId, String systemId,
            String baseSystemId, InputStream byteStream, String encoding) {
        super(publicId, systemId, baseSystemId, byteStream, encoding);
    } // <init>(String,String,String,InputStream,String)

    /**
     * Constructs an input source from a character stream.
     *
     * <p>
     *  从字符流构造输入源。
     * 
     * 
     * @param publicId     The public identifier, if known.
     * @param systemId     The system identifier. This value should
     *                     always be set, if possible, and can be
     *                     relative or absolute. If the system identifier
     *                     is relative, then the base system identifier
     *                     should be set.
     * @param baseSystemId The base system identifier. This value should
     *                     always be set to the fully expanded URI of the
     *                     base system identifier, if possible.
     * @param charStream   The character stream.
     * @param encoding     The original encoding of the byte stream
     *                     used by the reader, if known.
     */
    public HTTPInputSource(String publicId, String systemId,
            String baseSystemId, Reader charStream, String encoding) {
        super(publicId, systemId, baseSystemId, charStream, encoding);
    } // <init>(String,String,String,Reader,String)

    //
    // Public methods
    //

    /**
     * Returns the preference whether HTTP redirects should
     * be followed. By default HTTP redirects will be followed.
     * <p>
     * 返回是否应遵循HTTP重定向的首选项。默认情况下将遵循HTTP重定向。
     * 
     */
    public boolean getFollowHTTPRedirects() {
        return fFollowRedirects;
    } // getFollowHTTPRedirects():boolean


    /**
     * Sets the preference whether HTTP redirects should
     * be followed. By default HTTP redirects will be followed.
     * <p>
     *  设置是否应遵循HTTP重定向的首选项。默认情况下将遵循HTTP重定向。
     * 
     */
    public void setFollowHTTPRedirects(boolean followRedirects) {
        fFollowRedirects = followRedirects;
    } // setFollowHTTPRedirects(boolean)

    /**
     * Returns the value of the request property
     * associated with the given property name.
     *
     * <p>
     *  返回与给定属性名称相关联的请求属性的值。
     * 
     * 
     * @param key the name of the request property
     * @return the value of the request property or
     * <code>null</code> if this property has not
     * been set
     */
    public String getHTTPRequestProperty(String key) {
        return (String) fHTTPRequestProperties.get(key);
    } // getHTTPRequestProperty(String):String

    /**
     * Returns an iterator for the request properties this
     * input source contains. Each object returned by the
     * iterator is an instance of <code>java.util.Map.Entry</code>
     * where each key and value are a pair of strings corresponding
     * to the name and value of a request property.
     *
     * <p>
     *  返回此输入源包含的请求属性的迭代器。迭代器返回的每个对象是<code> java.util.Map.Entry </code>的一个实例,其中每个键和值都是一对字符串,对应于请求属性的名称和值。
     * 
     * 
     * @return an iterator for the request properties this
     * input source contains
     */
    public Iterator getHTTPRequestProperties() {
        return fHTTPRequestProperties.entrySet().iterator();
    } // getHTTPRequestProperties():Iterator

    /**
     * Sets the value of the request property
     * associated with the given property name.
     *
     * <p>
     *  设置与给定属性名称关联的请求属性的值。
     * 
     * @param key the name of the request property
     * @param value the value of the request property
     */
    public void setHTTPRequestProperty(String key, String value) {
        if (value != null) {
            fHTTPRequestProperties.put(key, value);
        }
        else {
            fHTTPRequestProperties.remove(key);
        }
    } // setHTTPRequestProperty(String,String)

} // class HTTPInputSource
