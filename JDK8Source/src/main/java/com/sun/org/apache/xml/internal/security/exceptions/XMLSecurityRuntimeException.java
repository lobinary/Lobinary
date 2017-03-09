/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;

import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.I18n;

/**
 * The mother of all runtime Exceptions in this bundle. It allows exceptions to have
 * their messages translated to the different locales.
 *
 * The <code>xmlsecurity_en.properties</code> file contains this line:
 * <pre>
 * xml.WrongElement = Can't create a {0} from a {1} element
 * </pre>
 *
 * Usage in the Java source is:
 * <pre>
 * {
 *    Object exArgs[] = { Constants._TAG_TRANSFORMS, "BadElement" };
 *
 *    throw new XMLSecurityException("xml.WrongElement", exArgs);
 * }
 * </pre>
 *
 * Additionally, if another Exception has been caught, we can supply it, too>
 * <pre>
 * try {
 *    ...
 * } catch (Exception oldEx) {
 *    Object exArgs[] = { Constants._TAG_TRANSFORMS, "BadElement" };
 *
 *    throw new XMLSecurityException("xml.WrongElement", exArgs, oldEx);
 * }
 * </pre>
 *
 *
 * <p>
 *  这个bundle中所有运行时异常的母亲。它允许异常将其消息转换为不同的区域设置。
 * 
 *  <code> xmlsecurity_en.properties </code>文件包含以下行：
 * <pre>
 *  xml.WrongElement =无法从{1}元素创建{0}
 * </pre>
 * 
 *  Java源中的用法是：
 * <pre>
 *  {Object exArgs [] = {Constants._TAG_TRANSFORMS,"BadElement"};
 * 
 *  throw new XMLSecurityException("xml.WrongElement",exArgs); }}
 * </pre>
 * 
 * Additionally, if another Exception has been caught, we can supply it, too>
 * <pre>
 *  try {...} catch(Exception oldEx){Object exArgs [] = {Constants._TAG_TRANSFORMS,"BadElement"};
 * 
 *  throw new XMLSecurityException("xml.WrongElement",exArgs,oldEx); }}
 * </pre>
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class XMLSecurityRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Field msgID */
    protected String msgID;

    /**
     * Constructor XMLSecurityRuntimeException
     *
     * <p>
     * 构造函数XMLSecurityRuntimeException
     * 
     */
    public XMLSecurityRuntimeException() {
        super("Missing message string");

        this.msgID = null;
    }

    /**
     * Constructor XMLSecurityRuntimeException
     *
     * <p>
     *  构造函数XMLSecurityRuntimeException
     * 
     * 
     * @param msgID
     */
    public XMLSecurityRuntimeException(String msgID) {
        super(I18n.getExceptionMessage(msgID));

        this.msgID = msgID;
    }

    /**
     * Constructor XMLSecurityRuntimeException
     *
     * <p>
     *  构造函数XMLSecurityRuntimeException
     * 
     * 
     * @param msgID
     * @param exArgs
     */
    public XMLSecurityRuntimeException(String msgID, Object exArgs[]) {
        super(MessageFormat.format(I18n.getExceptionMessage(msgID), exArgs));

        this.msgID = msgID;
    }

    /**
     * Constructor XMLSecurityRuntimeException
     *
     * <p>
     *  构造函数XMLSecurityRuntimeException
     * 
     * 
     * @param originalException
     */
    public XMLSecurityRuntimeException(Exception originalException) {
        super("Missing message ID to locate message string in resource bundle \""
              + Constants.exceptionMessagesResourceBundleBase
              + "\". Original Exception was a "
              + originalException.getClass().getName() + " and message "
              + originalException.getMessage(), originalException);
    }

    /**
     * Constructor XMLSecurityRuntimeException
     *
     * <p>
     *  构造函数XMLSecurityRuntimeException
     * 
     * 
     * @param msgID
     * @param originalException
     */
    public XMLSecurityRuntimeException(String msgID, Exception originalException) {
        super(I18n.getExceptionMessage(msgID, originalException), originalException);

        this.msgID = msgID;
    }

    /**
     * Constructor XMLSecurityRuntimeException
     *
     * <p>
     *  构造函数XMLSecurityRuntimeException
     * 
     * 
     * @param msgID
     * @param exArgs
     * @param originalException
     */
    public XMLSecurityRuntimeException(String msgID, Object exArgs[], Exception originalException) {
        super(MessageFormat.format(I18n.getExceptionMessage(msgID), exArgs));

        this.msgID = msgID;
    }

    /**
     * Method getMsgID
     *
     * <p>
     *  方法getMsgID
     * 
     * 
     * @return the messageId
     */
    public String getMsgID() {
        if (msgID == null) {
            return "Missing message ID";
        }
        return msgID;
    }

    /** @inheritDoc */
    public String toString() {
        String s = this.getClass().getName();
        String message = super.getLocalizedMessage();

        if (message != null) {
            message = s + ": " + message;
        } else {
            message = s;
        }

        if (this.getCause() != null) {
            message = message + "\nOriginal Exception was " + this.getCause().toString();
        }

        return message;
    }

    /**
     * Method printStackTrace
     *
     * <p>
     *  方法printStackTrace
     * 
     */
    public void printStackTrace() {
        synchronized (System.err) {
            super.printStackTrace(System.err);
        }
    }

    /**
     * Method printStackTrace
     *
     * <p>
     *  方法printStackTrace
     * 
     * 
     * @param printwriter
     */
    public void printStackTrace(PrintWriter printwriter) {
        super.printStackTrace(printwriter);
    }

    /**
     * Method printStackTrace
     *
     * <p>
     *  方法printStackTrace
     * 
     * 
     * @param printstream
     */
    public void printStackTrace(PrintStream printstream) {
        super.printStackTrace(printstream);
    }

    /**
     * Method getOriginalException
     *
     * <p>
     *  方法getOriginalException
     * 
     * @return the original exception
     */
    public Exception getOriginalException() {
        if (this.getCause() instanceof Exception) {
            return (Exception)this.getCause();
        }
        return null;
    }

}
