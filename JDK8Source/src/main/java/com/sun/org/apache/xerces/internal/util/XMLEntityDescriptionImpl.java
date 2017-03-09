/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004 The Apache Software Foundation.
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
 *  版权所有2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.impl.XMLEntityDescription;

/**
 * <p>This class is an implementation of the XMLEntityDescription
 * interface which describes the properties of an entity.</p>
 *
 * <p>
 *  <p>此类是描述实体属性的XMLEntityDescription接口的实现。</p>
 * 
 * 
 * @author Michael Glavassevich, IBM
 *
 */
public class XMLEntityDescriptionImpl
    extends XMLResourceIdentifierImpl
    implements XMLEntityDescription {

    //
    // Constructors
    //

    /** Constructs an empty entity description. */
    public XMLEntityDescriptionImpl() {} // <init>()

    /**
     * Constructs an entity description.
     *
     * <p>
     *  构造实体描述。
     * 
     * 
     * @param entityName The name of the entity.
     * @param publicId The public identifier.
     * @param literalSystemId The literal system identifier.
     * @param baseSystemId The base system identifier.
     * @param expandedSystemId The expanded system identifier.
     */
    public XMLEntityDescriptionImpl(String entityName, String publicId, String literalSystemId,
                                    String baseSystemId, String expandedSystemId) {
        setDescription(entityName, publicId, literalSystemId, baseSystemId, expandedSystemId);
    } // <init>(String,String,String,String,String)

    /**
     * Constructs a resource identifier.
     *
     * <p>
     *  构造资源标识符。
     * 
     * 
     * @param entityName The name of the entity.
     * @param publicId The public identifier.
     * @param literalSystemId The literal system identifier.
     * @param baseSystemId The base system identifier.
     * @param expandedSystemId The expanded system identifier.
     * @param namespace The namespace.
     */
    public XMLEntityDescriptionImpl(String entityName, String publicId, String literalSystemId,
                                    String baseSystemId, String expandedSystemId, String namespace) {
        setDescription(entityName, publicId, literalSystemId, baseSystemId, expandedSystemId, namespace);
    } // <init>(String,String,String,String,String,String)

    //
    // Data
    //

    /** The name of the entity. */
    protected String fEntityName;

    //
    // Public methods
    //

    /**
     * Sets the name of the entity.
     *
     * <p>
     *  设置实体的名称。
     * 
     * 
     * @param name the name of the entity
     */
    public void setEntityName(String name) {
        fEntityName = name;
    } // setEntityName(String)

    /**
     * Returns the name of the entity.
     *
     * <p>
     *  返回实体的名称。
     * 
     * 
     * @return the name of the entity
     */
    public String getEntityName() {
        return fEntityName;
    } // getEntityName():String

    /**
     * <p>Sets the values of this entity description.</p>
     *
     * <p>
     *  <p>设置此实体说明的值。</p>
     * 
     * 
     * @param entityName The name of the entity.
     * @param publicId The public identifier.
     * @param literalSystemId The literal system identifier.
     * @param baseSystemId The base system identifier.
     * @param expandedSystemId The expanded system identifier.
     */
    public void setDescription(String entityName, String publicId, String literalSystemId,
                               String baseSystemId, String expandedSystemId) {
        setDescription(entityName, publicId, literalSystemId, baseSystemId, expandedSystemId, null);
    } // setDescription(String,String,String,String,String)

    /**
     * <p>Sets the values of this entity description.</p>
     *
     * <p>
     *  <p>设置此实体说明的值。</p>
     * 
     * 
     * @param entityName The name of the entity.
     * @param publicId The public identifier.
     * @param literalSystemId The literal system identifier.
     * @param baseSystemId The base system identifier.
     * @param expandedSystemId The expanded system identifier.
     * @param namespace The namespace.
     */
    public void setDescription(String entityName, String publicId, String literalSystemId,
                               String baseSystemId, String expandedSystemId, String namespace) {
        fEntityName = entityName;
        setValues(publicId, literalSystemId, baseSystemId, expandedSystemId, namespace);
    } // setDescription(String,String,String,String,String,String)

    /**
     * <p>Clears the values.</p>
     * <p>
     *  <p>清除值。</p>
     */
    public void clear() {
        super.clear();
        fEntityName = null;
    } // clear()

    //
    // Object methods
    //

    /** Returns a hash code for this object. */
    public int hashCode() {
        int code = super.hashCode();
        if (fEntityName != null) {
            code += fEntityName.hashCode();
        }
        return code;
    } // hashCode():int

    /** Returns a string representation of this object. */
    public String toString() {
        StringBuffer str = new StringBuffer();
        if (fEntityName != null) {
            str.append(fEntityName);
        }
        str.append(':');
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

} // XMLEntityDescriptionImpl
