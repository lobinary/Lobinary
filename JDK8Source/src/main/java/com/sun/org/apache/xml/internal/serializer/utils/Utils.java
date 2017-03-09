/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
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
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Utils.java,v 1.1.4.1 2005/09/08 11:03:21 suresh_emailid Exp $
 * <p>
 *  $ Id：Utils.java,v 1.1.4.1 2005/09/08 11:03:21 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer.utils;

import java.util.Hashtable;

/**
 * This class contains utilities used by the serializer.
 *
 * This class is not a public API, it is only public because it is
 * used by com.sun.org.apache.xml.internal.serializer.
 *
 * @xsl.usage internal
 * <p>
 *  此类包含序列化程序使用的实用程序。
 * 
 *  此类不是公共API,它只是public,因为它由com.sun.org.apache.xml.internal.serializer使用。
 * 
 *  @ xsl.usage internal
 */
public final class Utils
{
    /**
     * A singleton Messages object is used to load the
     * given resource bundle just once, it is
     * used by multiple transformations as long as the JVM stays up.
     * <p>
     * 
     */
    public static final com.sun.org.apache.xml.internal.serializer.utils.Messages messages=
        new com.sun.org.apache.xml.internal.serializer.utils.Messages(
            "com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages");
}
