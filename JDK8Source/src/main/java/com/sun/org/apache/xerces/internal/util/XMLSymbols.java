/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002,2004 The Apache Software Foundation.
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
 *  版权所有2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

/**
 * All internalized xml symbols. They can be compared using "==".
 *
 * <p>
 *  所有内部化的xml符号。它们可以使用"=="进行比较。
 * 
 * 
 * @author Sandy Gao, IBM
 */
public class XMLSymbols {

    // public constructor.
    public XMLSymbols(){}

    //==========================
    // Commonly used strings
    //==========================

    /**
     * The empty string.
     * <p>
     *  空字符串。
     * 
     */
    public final static String EMPTY_STRING = "".intern();

    //==========================
    // Namespace prefixes/uris
    //==========================

    /**
     * The internalized "xml" prefix.
     * <p>
     *  内部化的"xml"前缀。
     * 
     */
    public final static String PREFIX_XML = "xml".intern();

    /**
     * The internalized "xmlns" prefix.
     * <p>
     *  内部化的"xmlns"前缀。
     */
    public final static String PREFIX_XMLNS = "xmlns".intern();

    //==========================
    // DTD symbols
    //==========================

    /** Symbol: "ANY". */
    public static final String fANYSymbol = "ANY".intern();

    /** Symbol: "CDATA". */
    public static final String fCDATASymbol = "CDATA".intern();

    /** Symbol: "ID". */
    public static final String fIDSymbol = "ID".intern();

    /** Symbol: "IDREF". */
    public static final String fIDREFSymbol = "IDREF".intern();

    /** Symbol: "IDREFS". */
    public static final String fIDREFSSymbol = "IDREFS".intern();

    /** Symbol: "ENTITY". */
    public static final String fENTITYSymbol = "ENTITY".intern();

    /** Symbol: "ENTITIES". */
    public static final String fENTITIESSymbol = "ENTITIES".intern();

    /** Symbol: "NMTOKEN". */
    public static final String fNMTOKENSymbol = "NMTOKEN".intern();

    /** Symbol: "NMTOKENS". */
    public static final String fNMTOKENSSymbol = "NMTOKENS".intern();

    /** Symbol: "NOTATION". */
    public static final String fNOTATIONSymbol = "NOTATION".intern();

    /** Symbol: "ENUMERATION". */
    public static final String fENUMERATIONSymbol = "ENUMERATION".intern();

    /** Symbol: "#IMPLIED. */
    public static final String fIMPLIEDSymbol = "#IMPLIED".intern();

    /** Symbol: "#REQUIRED". */
    public static final String fREQUIREDSymbol = "#REQUIRED".intern();

    /** Symbol: "#FIXED". */
    public static final String fFIXEDSymbol = "#FIXED".intern();


}
