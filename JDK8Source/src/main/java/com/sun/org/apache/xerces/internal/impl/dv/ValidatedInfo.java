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

import com.sun.org.apache.xerces.internal.xs.ShortList;

/**
 * Class to get the information back after content is validated. This info
 * would be filled by validate().
 *
 * @xerces.internal
 *
 * <p>
 *  类在内容验证后获取信息。此信息将由validate()填充。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neeraj Bajaj, Sun Microsystems, inc.
 *
 */
public class ValidatedInfo {

    /**
     * The normalized value of a string value
     * <p>
     *  字符串值的标准化值
     * 
     */
    public String normalizedValue;

    /**
     * The actual value from a string value (QName, Boolean, etc.)
     * An array of Objects if the type is a list.
     * <p>
     *  来自字符串值的实际值(QName,Boolean等)如果类型是列表,则为对象数组。
     * 
     */
    public Object actualValue;

    /**
     * The type of the actual value. It's one of the _DT constants
     * defined in XSConstants.java. The value is used to indicate
     * the most specific built-in type.
     * (i.e. short instead of decimal or integer).
     * <p>
     *  实际值的类型。它是XSConstants.java中定义的_DT常量之一。该值用于指示最具体的内置类型。 (即短而不是十进制或整数)。
     * 
     */
    public short actualValueType;

    /**
     * If the type is a union type, then the member type which
     * actually validated the string value.
     * <p>
     *  如果类型是联合类型,那么实际验证字符串值的成员类型。
     * 
     */
    public XSSimpleType memberType;

    /**
     * If
     * 1. the type is a union type where one of the member types is a list, or
     *    if the type is a list; and
     * 2. the item type of the list is a union type
     * then an array of member types used to validate the values.
     * <p>
     *  如果1.类型是联合类型,其中成员类型之一是列表,或者如果类型是列表;和2.列表的项类型是联合类型,然后是用于验证值的成员类型数组。
     * 
     */
    public XSSimpleType[] memberTypes;

    /**
     * In the case the value is a list or a list of unions, this value
     * indicates the type(s) of the items in the list.
     * For a normal list, the length of the array is 1; for list of unions,
     * the length of the array is the same as the length of the list.
     * <p>
     * 在值是联合的列表或列表的情况下,该值指示列表中的项目的类型。对于正常列表,数组的长度为1;对于联合的列表,数组的长度与列表的长度相同。
     * 
     */
    public ShortList itemValueTypes;

    /**
     * reset the state of this object
     * <p>
     *  重置此对象的状态
     * 
     */
    public void reset() {
        this.normalizedValue = null;
        this.actualValue = null;
        this.memberType = null;
        this.memberTypes = null;
    }

    /**
     * Return a string representation of the value. If there is an actual
     * value, use toString; otherwise, use the normalized value.
     * <p>
     *  返回值的字符串表示形式。如果有一个实际值,使用toString;否则,使用归一化值。
     */
    public String stringValue() {
        if (actualValue == null)
            return normalizedValue;
        else
            return actualValue.toString();
    }
}
