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
 * $Id: XSLMessages.java,v 1.2.4.1 2005/09/09 07:41:10 pvedula Exp $
 * <p>
 *  $ Id：XSLMessages.java,v 1.2.4.1 2005/09/09 07:41:10 pvedula Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.res;

import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import java.util.ListResourceBundle;

import com.sun.org.apache.xpath.internal.res.XPATHMessages;

/**
 * Sets things up for issuing error messages. This class is misnamed, and should
 * be called XalanMessages, or some such.
 *
 * @xsl.usage internal
 * <p>
 *  设置用于发出错误消息的东西。这个类被错误命名,应该被称为XalanMessages,或者一些这样的。
 * 
 *  @ xsl.usage internal
 * 
 */
public class XSLMessages extends XPATHMessages {

    /**
     * The language specific resource object for Xalan messages.
     * <p>
     *  Xalan消息的语言特定资源对象。
     * 
     */
    private static ListResourceBundle XSLTBundle = null;
    /**
     * The class name of the Xalan error message string table.
     * <p>
     *  Xalan错误消息字符串表的类名。
     * 
     */
    private static final String XSLT_ERROR_RESOURCES =
            "com.sun.org.apache.xalan.internal.res.XSLTErrorResources";

    /**
     * Creates a message from the specified key and replacement arguments,
     * localized to the given locale.
     *
     * <p>
     *  从指定的键和替换参数创建消息,本地化到给定的语言环境。
     * 
     * 
     * @param msgKey The key for the message text.
     * @param args The arguments to be used as replacement text in the message
     * created.
     *
     * @return The formatted message string.
     */
    public static String createMessage(String msgKey, Object args[]) //throws Exception
    {
        if (XSLTBundle == null) {
            XSLTBundle = SecuritySupport.getResourceBundle(XSLT_ERROR_RESOURCES);
        }

        if (XSLTBundle != null) {
            return createMsg(XSLTBundle, msgKey, args);
        } else {
            return "Could not load any resource bundles.";
        }
    }

    /**
     * Creates a message from the specified key and replacement arguments,
     * localized to the given locale.
     *
     * <p>
     *  从指定的键和替换参数创建消息,本地化到给定的语言环境。
     * 
     * @param msgKey The key for the message text.
     * @param args The arguments to be used as replacement text in the message
     * created.
     *
     * @return The formatted warning string.
     */
    public static String createWarning(String msgKey, Object args[]) //throws Exception
    {
        if (XSLTBundle == null) {
            XSLTBundle = SecuritySupport.getResourceBundle(XSLT_ERROR_RESOURCES);
        }

        if (XSLTBundle != null) {
            return createMsg(XSLTBundle, msgKey, args);
        } else {
            return "Could not load any resource bundles.";
        }
    }
}
