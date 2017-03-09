/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 *  版权所有(c)2012,Stephen Colebourne和Michael Nascimento Santos
 * 
 *  版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  *二进制形式的再分发必须在随发行提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或推广衍生自此软件的产品。
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,版权所有者或贡献者对任何直接,间接,偶发,特殊,惩戒性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据或利润损失,或业务中断),无论是由于任何责任推定,无论是在合同,严格责任,或
 * 侵权(包括疏忽或其他)任何方式使用本软件,即使已被告知此类损害的可能性。
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 
 */
package java.time.chrono;

import java.time.DateTimeException;

/**
 * An era in the ISO calendar system.
 * <p>
 * The ISO-8601 standard does not define eras.
 * A definition has therefore been created with two eras - 'Current era' (CE) for
 * years on or after 0001-01-01 (ISO), and 'Before current era' (BCE) for years before that.
 *
 * <table summary="ISO years and eras" cellpadding="2" cellspacing="3" border="0" >
 * <thead>
 * <tr class="tableSubHeadingColor">
 * <th class="colFirst" align="left">year-of-era</th>
 * <th class="colFirst" align="left">era</th>
 * <th class="colLast" align="left">proleptic-year</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="rowColor">
 * <td>2</td><td>CE</td><td>2</td>
 * </tr>
 * <tr class="altColor">
 * <td>1</td><td>CE</td><td>1</td>
 * </tr>
 * <tr class="rowColor">
 * <td>1</td><td>BCE</td><td>0</td>
 * </tr>
 * <tr class="altColor">
 * <td>2</td><td>BCE</td><td>-1</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code IsoEra}.
 * Use {@code getValue()} instead.</b>
 *
 * @implSpec
 * This is an immutable and thread-safe enum.
 *
 * <p>
 *  ISO日历系统中的一个时代。
 * <p>
 *  ISO-8601标准不定义时代。因此,定义已经创建了两个时代 - "当代时代"(CE)在0001-01-01(ISO)或之后的几年,以及"之前的时代"(BCE)之前的几年。
 * 
 * <table summary="ISO years and eras" cellpadding="2" cellspacing="3" border="0" >
 * <thead>
 * <tr class="tableSubHeadingColor">
 *  <th class ="colFirst"align ="left"> year-of-era </th> <th class ="colFirst"align ="left"> era </th> 
 * <th class ="colLast"align = left"> proleptic-year </th>。
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="rowColor">
 *  <td> 2 </td> <td> CE </td> <td> 2 </td>
 * </tr>
 * <tr class="altColor">
 *  <td> 1 </td> <td> CE </td> <td> 1 </td>
 * </tr>
 * <tr class="rowColor">
 *  <td> 1 </td> <td> BCE </td> <td> 0 </td>
 * </tr>
 * <tr class="altColor">
 *  <td> 2 </td> <td> BCE </td> <td> -1 </td>
 * </tr>
 * 
 * @since 1.8
 */
public enum IsoEra implements Era {

    /**
     * The singleton instance for the era before the current one, 'Before Current Era',
     * which has the numeric value 0.
     * <p>
     * </tbody>
     * </table>
     * <p>
     *  <b>不要使用{@code ordinal()}来获取{@code IsoEra}的数字表示形式。请改用{@code getValue()}。</b>
     * 
     *  @implSpec这是一个不可变和线程安全的枚举。
     * 
     */
    BCE,
    /**
     * The singleton instance for the current era, 'Current Era',
     * which has the numeric value 1.
     * <p>
     * 在当前时代之前的时代的单例实例,"当前时代之前",其具有数值0。
     * 
     */
    CE;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code IsoEra} from an {@code int} value.
     * <p>
     * {@code IsoEra} is an enum representing the ISO eras of BCE/CE.
     * This factory allows the enum to be obtained from the {@code int} value.
     *
     * <p>
     *  当前时代的单例实例,"当前时代",其数值为1。
     * 
     * 
     * @param isoEra  the BCE/CE value to represent, from 0 (BCE) to 1 (CE)
     * @return the era singleton, not null
     * @throws DateTimeException if the value is invalid
     */
    public static IsoEra of(int isoEra) {
        switch (isoEra) {
            case 0:
                return BCE;
            case 1:
                return CE;
            default:
                throw new DateTimeException("Invalid era: " + isoEra);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the numeric era {@code int} value.
     * <p>
     * The era BCE has the value 0, while the era CE has the value 1.
     *
     * <p>
     *  从{@code int}值获取{@code IsoEra}的实例。
     * <p>
     *  {@code IsoEra}是一个表示BCE / CE的ISO时代的枚举。此工厂允许从{@code int}值获取枚举。
     * 
     * 
     * @return the era value, from 0 (BCE) to 1 (CE)
     */
    @Override
    public int getValue() {
        return ordinal();
    }

}
