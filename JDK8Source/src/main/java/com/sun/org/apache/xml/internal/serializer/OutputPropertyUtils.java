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
 * $Id: OutputPropertyUtils.java,v 1.2.4.1 2005/09/15 08:15:21 suresh_emailid Exp $
 * <p>
 *  $ Id：OutputPropertyUtils.java,v 1.2.4.1 2005/09/15 08:15:21 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.util.Properties;

/**
 * This class contains some static methods that act as helpers when parsing a
 * Java Property object.
 *
 * This class is not a public API.
 * It is only public because it is used outside of this package.
 *
 * <p>
 *  此类包含一些静态方法,在解析Java Property对象时充当辅助函数。
 * 
 *  此类不是公共API。它只是公共的,因为它在这个包之外使用。
 * 
 * 
 * @see java.util.Properties
 * @xsl.usage internal
 */
public final class OutputPropertyUtils
{
    /**
      * Searches for the boolean property with the specified key in the property list.
      * If the key is not found in this property list, the default property list,
      * and its defaults, recursively, are then checked. The method returns
      * <code>false</code> if the property is not found, or if the value is other
      * than "yes".
      *
      * <p>
      *  在属性列表中使用指定的键搜索布尔属性。如果在此属性列表中找不到键,则递归检查默认属性列表及其默认值。如果未找到属性,或者值不是"yes",则方法返回<code> false </code>。
      * 
      * 
      * @param   key   the property key.
      * @param   props   the list of properties that will be searched.
      * @return  the value in this property list as a boolean value, or false
      * if null or not "yes".
      */
    public static boolean getBooleanProperty(String key, Properties props)
    {

        String s = props.getProperty(key);

        if (null == s || !s.equals("yes"))
            return false;
        else
            return true;
    }

    /**
     * Searches for the int property with the specified key in the property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>false</code> if the property is not found, or if the value is other
     * than "yes".
     *
     * <p>
     * 在属性列表中使用指定的键搜索int属性。如果在此属性列表中找不到键,则递归检查默认属性列表及其默认值。如果未找到属性,或者值不是"yes",则方法返回<code> false </code>。
     * 
     * @param   key   the property key.
     * @param   props   the list of properties that will be searched.
     * @return  the value in this property list as a int value, or 0
     * if null or not a number.
     */
    public static int getIntProperty(String key, Properties props)
    {

        String s = props.getProperty(key);

        if (null == s)
            return 0;
        else
            return Integer.parseInt(s);
    }

}
