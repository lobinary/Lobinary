/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
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
 *  版权所有2005 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
package com.sun.org.apache.xerces.internal.xs.datatypes;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>Interface to expose the value of 'decimal' and related datatypes.</p>
 *
 * <p>
 *  <p>显示"decimal"和相关数据类型的值的接口</p>
 * 
 * 
 * @author Naela Nissar, IBM
 *
 */
public interface XSDecimal {

    /**
    /* <p>
    /* 
     * @return the <code>BigDecimal</code> representation of this object
     */
    public BigDecimal getBigDecimal();

    /**
    /* <p>
    /* 
     * @return the <code>BigInteger</code> representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>BigInteger</code>
     */
    public BigInteger getBigInteger() throws NumberFormatException;

    /**
    /* <p>
    /* 
     * @return the long value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>long</code>
     */
    public long getLong() throws NumberFormatException;

    /**
    /* <p>
    /* 
     * @return the int value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>int</code>
     */
    public int getInt() throws NumberFormatException;

    /**
    /* <p>
    /* 
     * @return the short value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>short</code>
     */
    public short getShort() throws NumberFormatException;

    /**
    /* <p>
    /* 
     * @return the byte value representation of this object
     * @exception NumberFormatException if the value cannot be represented as a <code>byte</code>
     */
    public byte getByte() throws NumberFormatException;
}
