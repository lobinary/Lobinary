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

package com.sun.org.apache.xerces.internal.xni;

/**
 * This exception is the base exception of all XNI exceptions. It
 * can be constructed with an error message or used to wrap another
 * exception object.
 * <p>
 * <strong>Note:</strong> By extending the Java
 * <code>RuntimeException</code>, XNI handlers and components are
 * not required to catch XNI exceptions but may explicitly catch
 * them, if so desired.
 *
 * <p>
 *  此异常是所有XNI异常的基本异常。它可以构造一个错误消息或用于包装另一个异常对象。
 * <p>
 *  <strong>注意</strong>：通过扩展Java <code> RuntimeException </code>,XNI处理程序和组件不需要捕获XNI异常,但可以显式捕获它们(如果需要)。
 * 
 * 
 * @author Andy Clark, IBM
 *
 * @version $Id: XNIException.java,v 1.6 2010-11-01 04:40:19 joehw Exp $
 */
public class XNIException
    extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = 9019819772686063775L;

    //
    // Data
    //

    /** The wrapped exception. */
    private Exception fException;

    //
    // Constructors
    //

    /**
     * Constructs an XNI exception with a message.
     *
     * <p>
     *  构造带消息的XNI异常。
     * 
     * 
     * @param message The exception message.
     */
    public XNIException(String message) {
        super(message);
    } // <init>(String)

    /**
     * Constructs an XNI exception with a wrapped exception.
     *
     * <p>
     *  构造带有包装异常的XNI异常。
     * 
     * 
     * @param exception The wrapped exception.
     */
    public XNIException(Exception exception) {
        super(exception.getMessage());
        fException = exception;
    } // <init>(Exception)

    /**
     * Constructs an XNI exception with a message and wrapped exception.
     *
     * <p>
     *  构造具有消息和包装异常的XNI异常。
     * 
     * @param message The exception message.
     * @param exception The wrapped exception.
     */
    public XNIException(String message, Exception exception) {
        super(message);
        fException = exception;
    } // <init>(Exception,String)

    //
    // Public methods
    //

    /** Returns the wrapped exception. */
    public Exception getException() {
        return fException;
    } // getException():Exception

    public Throwable getCause() {
       return fException;
    }
} // class QName
