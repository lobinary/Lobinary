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

package com.sun.org.apache.xerces.internal.impl.dv;

import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.MissingResourceException;

/**
 * Base class for datatype exceptions. For DTD types, the exception can be
 * created from an error message. For Schema types, it needs an error code
 * (as defined in Appendix C of the structure spec), plus an array of arguents,
 * for error message substitution.
 *
 * @xerces.internal
 *
 * <p>
 *  数据类型异常的基类。对于DTD类型,可以从错误消息中创建异常。对于模式类型,它需要一个错误代码(如结构规范的附录C中定义),以及一个用于错误消息替换的参数数组。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 * @version $Id: DatatypeException.java,v 1.6 2010-11-01 04:39:43 joehw Exp $
 */
public class DatatypeException extends Exception {

    /** Serialization version. */
    static final long serialVersionUID = 1940805832730465578L;

    // used to store error code and error substitution arguments
    protected String key;
    protected Object[] args;

    /**
     * Create a new datatype exception by providing an error code and a list
     * of error message substitution arguments.
     *
     * <p>
     *  通过提供错误代码和错误消息替换参数列表来创建新的数据类型异常。
     * 
     * 
     * @param key  error code
     * @param args error arguments
     */
    public DatatypeException(String key, Object[] args) {
        super(key);
        this.key = key;
        this.args = args;
    }

    /**
     * Return the error code
     *
     * <p>
     *  返回错误代码
     * 
     * 
     * @return  error code
     */
    public String getKey() {
        return key;
    }

    /**
     * Return the list of error arguments
     *
     * <p>
     *  返回错误参数列表
     * 
     * 
     * @return  error arguments
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Overrides this method to get the formatted&localized error message.
     *
     * REVISIT: the system locale is used to load the property file.
     *          do we want to allow the appilcation to specify a
     *          different locale?
     * <p>
     *  覆盖此方法以获取格式化和本地化的错误消息。
     * 
     */
    public String getMessage() {
        ResourceBundle resourceBundle = null;
        resourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.XMLSchemaMessages");
        if (resourceBundle == null)
            throw new MissingResourceException("Property file not found!", "com.sun.org.apache.xerces.internal.impl.msg.XMLSchemaMessages", key);

        String msg = resourceBundle.getString(key);
        if (msg == null) {
            msg = resourceBundle.getString("BadMessageKey");
            throw new MissingResourceException(msg, "com.sun.org.apache.xerces.internal.impl.msg.XMLSchemaMessages", key);
        }

        if (args != null) {
            try {
                msg = java.text.MessageFormat.format(msg, args);
            } catch (Exception e) {
                msg = resourceBundle.getString("FormatFailed");
                msg += " " + resourceBundle.getString(key);
            }
        }

        return msg;
    }
}
