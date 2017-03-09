/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2004 The Apache Software Foundation.
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
 *  版权所有2000-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 */

package com.sun.org.apache.xerces.internal.xni;

/**
 * <p> This represents the basic physical description of the location of any
 * XML resource (a Schema grammar, a DTD, a general entity etc.) </p>
 *
 * <p>
 * 
 * 
 * @author Neil Graham, IBM
 */

public interface XMLResourceIdentifier {

    /** Sets the public identifier. */
    public void setPublicId(String publicId);

    /** Returns the public identifier. */
    public String getPublicId();

    /** Sets the expanded system identifier. */
    public void setExpandedSystemId(String systemId);

    /** Returns the expanded system identifier. */
    public String getExpandedSystemId();

    /** Sets the literal system identifier. */
    public void setLiteralSystemId(String systemId);

    /** Returns the literal system identifier. */
    public String getLiteralSystemId();

    /** Setsthe base URI against which the literal SystemId is to be
    /* <p>
    /*  <p>这表示任何XML资源(模式语法,DTD,一般实体等)位置的基本物理描述</p>
    /* 
    /* 
        resolved.*/
    public void setBaseSystemId(String systemId);

    /** <p> Returns the base URI against which the literal SystemId is to be
    /* <p>
    /* 
        resolved. </p> */
    public String getBaseSystemId();

    /** Sets the namespace of the resource. */
    public void setNamespace(String namespace);

    /** Returns the namespace of the resource. */
    public String getNamespace();

} // XMLResourceIdentifier
