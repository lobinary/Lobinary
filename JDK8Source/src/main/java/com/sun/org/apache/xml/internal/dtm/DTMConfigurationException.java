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
 * $Id: DTMConfigurationException.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMConfigurationException.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

import javax.xml.transform.SourceLocator;

/**
 * Indicates a serious configuration error.
 * <p>
 *  表示严重的配置错误。
 * 
 */
public class DTMConfigurationException extends DTMException {
    static final long serialVersionUID = -4607874078818418046L;

    /**
     * Create a new <code>DTMConfigurationException</code> with no
     * detail mesage.
     * <p>
     *  创建新的没有详细信息的<code> DTMConfigurationException </code>。
     * 
     */
    public DTMConfigurationException() {
        super("Configuration Error");
    }

    /**
     * Create a new <code>DTMConfigurationException</code> with
     * the <code>String </code> specified as an error message.
     *
     * <p>
     *  使用指定为错误消息的<code> String </code>创建新的<code> DTMConfigurationException </code>。
     * 
     * 
     * @param msg The error message for the exception.
     */
    public DTMConfigurationException(String msg) {
        super(msg);
    }

    /**
     * Create a new <code>DTMConfigurationException</code> with a
     * given <code>Exception</code> base cause of the error.
     *
     * <p>
     *  使用给定的<code>异常</code>原因创建一个新的<code> DTMConfigurationException </code>错误。
     * 
     * 
     * @param e The exception to be encapsulated in a
     * DTMConfigurationException.
     */
    public DTMConfigurationException(Throwable e) {
        super(e);
    }

    /**
     * Create a new <code>DTMConfigurationException</code> with the
     * given <code>Exception</code> base cause and detail message.
     *
     * <p>
     *  使用给定的<code>异常</code>基本原因和详细信息消息创建新的<code> DTMConfigurationException </code>。
     * 
     * 
     * @param msg The detail message.
     * @param e The exception to be wrapped in a DTMConfigurationException
     */
    public DTMConfigurationException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * Create a new DTMConfigurationException from a message and a Locator.
     *
     * <p>This constructor is especially useful when an application is
     * creating its own exception from within a DocumentHandler
     * callback.</p>
     *
     * <p>
     *  从消息和定位器创建新的DTMConfigurationException。
     * 
     *  <p>当应用程序从DocumentHandler回调中创建自己的异常时,此构造函数尤其有用。</p>
     * 
     * 
     * @param message The error or warning message.
     * @param locator The locator object for the error or warning.
     */
    public DTMConfigurationException(String message,
                                             SourceLocator locator) {
        super(message, locator);
    }

    /**
     * Wrap an existing exception in a DTMConfigurationException.
     *
     * <p>
     * 
     * @param message The error or warning message, or null to
     *                use the message from the embedded exception.
     * @param locator The locator object for the error or warning.
     * @param e Any exception.
     */
    public DTMConfigurationException(String message,
                                             SourceLocator locator,
                                             Throwable e) {
        super(message, locator, e);
    }
}
