/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.dv.dtd;

import com.sun.org.apache.xerces.internal.impl.dv.*;

/**
 * <P>StringValidator validates that XML content is a W3C string type.</P>
 * <P>The string datatype represents character strings in XML. The
 * value space of string is the set of finite-length sequences
 * of characters (as defined in [XML 1.0 Recommendation
 * (Second Edition)]) that match the Char production
 * from [XML 1.0 Recommendation (Second Edition)].
 * A character is an atomic unit of communication; it
 * is not further specified except to note that every
 * character has a corresponding Universal Code Set
 * code point ([ISO 10646],[Unicode] and [Unicode3]),
 * which is an integer.</P>
 *
 * @xerces.internal
 *
 * <p>
 *  <P> StringValidator验证XML内容是否为W3C字符串类型。</P> <P>字符串数据类型表示XML中的字符串。
 * 字符串的值空间是与[XML 1.0建议(第二版)]中的Char生成相匹配的有限长度字符序列集合(如[XML 1.0建议(第二版)中定义的])。
 * 字符是通信的原子单位;除非注意每个字符都具有对应的通用代码集代码点([ISO 10646],[Unicode]和[Unicode3]),它是一个整数,不作进一步说明。</P>。
 * 
 *  @ xerces.internal
 */
public class StringDatatypeValidator implements DatatypeValidator {

    // construct a string datatype validator
    public StringDatatypeValidator() {
    }

    /**
     * Checks that "content" string is valid string value.
     * If invalid a Datatype validation exception is thrown.
     *
     * <p>
     * 
     * 
     * @param content       the string value that needs to be validated
     * @param context       the validation context
     * @throws InvalidDatatypeException if the content is
     *         invalid according to the rules for the validators
     * @see InvalidDatatypeValueException
     */
    public void validate(String content, ValidationContext context) throws InvalidDatatypeValueException {
    }

}
