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

package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

/**
 * The XMLResourceIdentifierImpl class is an implementation of the
 * XMLResourceIdentifier interface which defines the location identity
 * of a resource.
 *
 * <p>
 *  XMLResourceIdentifierImpl类是定义资源的位置标识的XMLResourceIdentifier接口的实现。
 * 
 * 
 * @author Andy Clark
 *
 */
public class XMLResourceIdentifierImpl
    implements XMLResourceIdentifier {

    //
    // Data
    //

    /** The public identifier. */
    protected String fPublicId;

    /** The literal system identifier. */
    protected String fLiteralSystemId;

    /** The base system identifier. */
    protected String fBaseSystemId;

    /** The expanded system identifier. */
    protected String fExpandedSystemId;

    /** The namespace of the resource. */
    protected String fNamespace;

    //
    // Constructors
    //

    /** Constructs an empty resource identifier. */
    public XMLResourceIdentifierImpl() {} // <init>()

    /**
     * Constructs a resource identifier.
     *
     * <p>
     *  构造资源标识符。
     * 
     * 
     * @param publicId The public identifier.
     * @param literalSystemId The literal system identifier.
     * @param baseSystemId The base system identifier.
     * @param expandedSystemId The expanded system identifier.
     */
    public XMLResourceIdentifierImpl(String publicId,
                                     String literalSystemId, String baseSystemId,
                                     String expandedSystemId) {
        setValues(publicId, literalSystemId, baseSystemId,
                  expandedSystemId, null);
    } // <init>(String,String,String,String)

    /**
     * Constructs a resource identifier.
     *
     * <p>
     *  构造资源标识符。
     * 
     * 
     * @param publicId The public identifier.
     * @param literalSystemId The literal system identifier.
     * @param baseSystemId The base system identifier.
     * @param expandedSystemId The expanded system identifier.
     * @param namespace The namespace.
     */
    public XMLResourceIdentifierImpl(String publicId, String literalSystemId,
                                     String baseSystemId, String expandedSystemId,
                                     String namespace) {
        setValues(publicId, literalSystemId, baseSystemId,
                  expandedSystemId, namespace);
    } // <init>(String,String,String,String,String)

    //
    // Public methods
    //

    /** Sets the values of the resource identifier. */
    public void setValues(String publicId, String literalSystemId,
                          String baseSystemId, String expandedSystemId) {
        setValues(publicId, literalSystemId, baseSystemId,
                  expandedSystemId, null);
    } // setValues(String,String,String,String)

    /** Sets the values of the resource identifier. */
    public void setValues(String publicId, String literalSystemId,
                          String baseSystemId, String expandedSystemId,
                          String namespace) {
        fPublicId = publicId;
        fLiteralSystemId = literalSystemId;
        fBaseSystemId = baseSystemId;
        fExpandedSystemId = expandedSystemId;
        fNamespace = namespace;
    } // setValues(String,String,String,String,String)

    /** Clears the values. */
    public void clear() {
        fPublicId = null;
        fLiteralSystemId = null;
        fBaseSystemId = null;
        fExpandedSystemId = null;
        fNamespace = null;
    } // clear()

    /** Sets the public identifier. */
    public void setPublicId(String publicId) {
        fPublicId = publicId;
    } // setPublicId(String)

    /** Sets the literal system identifier. */
    public void setLiteralSystemId(String literalSystemId) {
        fLiteralSystemId = literalSystemId;
    } // setLiteralSystemId(String)

    /** Sets the base system identifier. */
    public void setBaseSystemId(String baseSystemId) {
        fBaseSystemId = baseSystemId;
    } // setBaseSystemId(String)

    /** Sets the expanded system identifier. */
    public void setExpandedSystemId(String expandedSystemId) {
        fExpandedSystemId = expandedSystemId;
    } // setExpandedSystemId(String)

    /** Sets the namespace of the resource. */
    public void setNamespace(String namespace) {
        fNamespace = namespace;
    } // setNamespace(String)

    //
    // XMLResourceIdentifier methods
    //

    /** Returns the public identifier. */
    public String getPublicId() {
        return fPublicId;
    } // getPublicId():String

    /** Returns the literal system identifier. */
    public String getLiteralSystemId() {
        return fLiteralSystemId;
    } // getLiteralSystemId():String

    /**
     * Returns the base URI against which the literal SystemId is to be resolved.
     * <p>
     *  返回要解析文字SystemId的基本URI。
     */
    public String getBaseSystemId() {
        return fBaseSystemId;
    } // getBaseSystemId():String

    /** Returns the expanded system identifier. */
    public String getExpandedSystemId() {
        return fExpandedSystemId;
    } // getExpandedSystemId():String

    /** Returns the namespace of the resource. */
    public String getNamespace() {
        return fNamespace;
    } // getNamespace():String

    //
    // Object methods
    //

    /** Returns a hash code for this object. */
    public int hashCode() {
        int code = 0;
        if (fPublicId != null) {
            code += fPublicId.hashCode();
        }
        if (fLiteralSystemId != null) {
            code += fLiteralSystemId.hashCode();
        }
        if (fBaseSystemId != null) {
            code += fBaseSystemId.hashCode();
        }
        if (fExpandedSystemId != null) {
            code += fExpandedSystemId.hashCode();
        }
        if (fNamespace != null) {
            code += fNamespace.hashCode();
        }
        return code;
    } // hashCode():int

    /** Returns a string representation of this object. */
    public String toString() {
        StringBuffer str = new StringBuffer();
        if (fPublicId != null) {
            str.append(fPublicId);
        }
        str.append(':');
        if (fLiteralSystemId != null) {
            str.append(fLiteralSystemId);
        }
        str.append(':');
        if (fBaseSystemId != null) {
            str.append(fBaseSystemId);
        }
        str.append(':');
        if (fExpandedSystemId != null) {
            str.append(fExpandedSystemId);
        }
        str.append(':');
        if (fNamespace != null) {
            str.append(fNamespace);
        }
        return str.toString();
    } // toString():String

} // class XMLResourceIdentifierImpl
