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
package com.sun.org.apache.xml.internal.security.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Internationalization (I18N) pack.
 *
 * <p>
 *  国际化(I18N)包。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class I18n {

    /** Field NOT_INITIALIZED_MSG */
    public static final String NOT_INITIALIZED_MSG =
        "You must initialize the xml-security library correctly before you use it. "
        + "Call the static method \"com.sun.org.apache.xml.internal.security.Init.init();\" to do that "
        + "before you use any functionality from that library.";

    /** Field resourceBundle */
    private static ResourceBundle resourceBundle;

    /** Field alreadyInitialized */
    private static boolean alreadyInitialized = false;

    /**
     * Constructor I18n
     *
     * <p>
     *  构造函数I18n
     * 
     */
    private I18n() {
        // we don't allow instantiation
    }

    /**
     * Method translate
     *
     * translates a message ID into an internationalized String, see alse
     * <CODE>XMLSecurityException.getExceptionMEssage()</CODE>. The strings are
     * stored in the <CODE>ResourceBundle</CODE>, which is identified in
     * <CODE>exceptionMessagesResourceBundleBase</CODE>
     *
     * <p>
     *  方法翻译
     * 
     *  将消息ID转换为国际化字符串,请参见<CODE> XMLSecurityException.getExceptionMEssage()</CODE>。
     * 字符串存储在<CODE> ResourceBundle </CODE>中,它在<CODE> exceptionMessagesResourceBundleBase </CODE>。
     * 
     * 
     * @param message
     * @param args is an <CODE>Object[]</CODE> array of strings which are inserted into
     * the String which is retrieved from the <CODE>ResouceBundle</CODE>
     * @return message translated
     */
    public static String translate(String message, Object[] args) {
        return getExceptionMessage(message, args);
    }

    /**
     * Method translate
     *
     * translates a message ID into an internationalized String, see also
     * <CODE>XMLSecurityException.getExceptionMessage()</CODE>
     *
     * <p>
     *  方法翻译
     * 
     *  将消息ID转换为国际化字符串,另请参见<CODE> XMLSecurityException.getExceptionMessage()</CODE>
     * 
     * 
     * @param message
     * @return message translated
     */
    public static String translate(String message) {
        return getExceptionMessage(message);
    }

    /**
     * Method getExceptionMessage
     *
     * <p>
     *  方法getExceptionMessage
     * 
     * 
     * @param msgID
     * @return message translated
     *
     */
    public static String getExceptionMessage(String msgID) {
        try {
            return resourceBundle.getString(msgID);
        } catch (Throwable t) {
            if (com.sun.org.apache.xml.internal.security.Init.isInitialized()) {
                return "No message with ID \"" + msgID
                + "\" found in resource bundle \""
                + Constants.exceptionMessagesResourceBundleBase + "\"";
            }
            return I18n.NOT_INITIALIZED_MSG;
        }
    }

    /**
     * Method getExceptionMessage
     *
     * <p>
     *  方法getExceptionMessage
     * 
     * 
     * @param msgID
     * @param originalException
     * @return message translated
     */
    public static String getExceptionMessage(String msgID, Exception originalException) {
        try {
            Object exArgs[] = { originalException.getMessage() };
            return MessageFormat.format(resourceBundle.getString(msgID), exArgs);
        } catch (Throwable t) {
            if (com.sun.org.apache.xml.internal.security.Init.isInitialized()) {
                return "No message with ID \"" + msgID
                + "\" found in resource bundle \""
                + Constants.exceptionMessagesResourceBundleBase
                + "\". Original Exception was a "
                + originalException.getClass().getName() + " and message "
                + originalException.getMessage();
            }
            return I18n.NOT_INITIALIZED_MSG;
        }
    }

    /**
     * Method getExceptionMessage
     *
     * <p>
     *  方法getExceptionMessage
     * 
     * 
     * @param msgID
     * @param exArgs
     * @return message translated
     */
    public static String getExceptionMessage(String msgID, Object exArgs[]) {
        try {
            return MessageFormat.format(resourceBundle.getString(msgID), exArgs);
        } catch (Throwable t) {
            if (com.sun.org.apache.xml.internal.security.Init.isInitialized()) {
                return "No message with ID \"" + msgID
                + "\" found in resource bundle \""
                + Constants.exceptionMessagesResourceBundleBase + "\"";
            }
            return I18n.NOT_INITIALIZED_MSG;
        }
    }

    /**
     * Method init
     *
     * <p>
     *  方法init
     * 
     * @param languageCode
     * @param countryCode
     */
    public synchronized static void init(String languageCode, String countryCode) {
        if (alreadyInitialized) {
            return;
        }

        I18n.resourceBundle =
            ResourceBundle.getBundle(
                Constants.exceptionMessagesResourceBundleBase,
                new Locale(languageCode, countryCode)
            );
        alreadyInitialized = true;
    }
}
