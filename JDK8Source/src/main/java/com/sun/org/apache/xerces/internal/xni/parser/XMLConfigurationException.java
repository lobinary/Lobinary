/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xerces.internal.xni.XNIException;

/**
 * An XNI parser configuration exception. This exception class extends
 * <code>XNIException</code> in order to differentiate between general
 * parsing errors and configuration errors.
 *
 * <p>
 *  XNI解析器配置异常。此异常类扩展<code> XNIException </code>,以区分一般解析错误和配置错误。
 * 
 * 
 * @author Andy Clark, IBM
 *
 * @version $Id: XMLConfigurationException.java,v 1.7 2010-11-01 04:40:22 joehw Exp $
 */
public class XMLConfigurationException
    extends XNIException {

    /** Serialization version. */
    static final long serialVersionUID = -5437427404547669188L;

    //
    // Data
    //

    /** Exception type. */
    protected Status fType;

    /** Identifier. */
    protected String fIdentifier;

    //
    // Constructors
    //

    /**
     * Constructs a configuration exception with the specified type
     * and feature/property identifier.
     *
     * <p>
     *  构造具有指定类型和功能/属性标识符的配置异常。
     * 
     * 
     * @param type       The type of the exception.
     * @param identifier The feature or property identifier.
     */
    public XMLConfigurationException(Status type, String identifier) {
        super(identifier);
        fType = type;
        fIdentifier = identifier;
    } // <init>(short,String)

    /**
     * Constructs a configuration exception with the specified type,
     * feature/property identifier, and error message
     *
     * <p>
     *  构造具有指定类型,功能/属性标识符和错误消息的配置异常
     * 
     * 
     * @param type       The type of the exception.
     * @param identifier The feature or property identifier.
     * @param message    The error message.
     */
    public XMLConfigurationException(Status type, String identifier,
                                     String message) {
        super(message);
        fType = type;
        fIdentifier = identifier;
    } // <init>(short,String,String)

    //
    // Public methods
    //

    /**
     * Returns the exception type.
     * <p>
     *  返回异常类型。
     */
    public Status getType() {
        return fType;
    } // getType():short

    /** Returns the feature or property identifier. */
    public String getIdentifier() {
        return fIdentifier;
    } // getIdentifier():String

} // class XMLConfigurationException
