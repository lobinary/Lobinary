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
 * An era in the Minguo calendar system.
 * <p>
 * The Minguo calendar system has two eras.
 * The current era, for years from 1 onwards, is known as the 'Republic of China' era.
 * All previous years, zero or earlier in the proleptic count or one and greater
 * in the year-of-era count, are part of the 'Before Republic of China' era.
 *
 * <table summary="Minguo years and eras" cellpadding="2" cellspacing="3" border="0" >
 * <thead>
 * <tr class="tableSubHeadingColor">
 * <th class="colFirst" align="left">year-of-era</th>
 * <th class="colFirst" align="left">era</th>
 * <th class="colFirst" align="left">proleptic-year</th>
 * <th class="colLast" align="left">ISO proleptic-year</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="rowColor">
 * <td>2</td><td>ROC</td><td>2</td><td>1913</td>
 * </tr>
 * <tr class="altColor">
 * <td>1</td><td>ROC</td><td>1</td><td>1912</td>
 * </tr>
 * <tr class="rowColor">
 * <td>1</td><td>BEFORE_ROC</td><td>0</td><td>1911</td>
 * </tr>
 * <tr class="altColor">
 * <td>2</td><td>BEFORE_ROC</td><td>-1</td><td>1910</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code MinguoEra}.
 * Use {@code getValue()} instead.</b>
 *
 * @implSpec
 * This is an immutable and thread-safe enum.
 *
 * <p>
 *  Minguo日历系统中的一个时代。
 * <p>
 *  Minguo日历系统有两个时代。当前的时代,从1年起的多年,被称为"中华民国"时代。所有以前的年份,零或更早的探测计数或一个和更大的年龄计数,是"中华民国"时代的一部分。
 * 
 * <table summary="Minguo years and eras" cellpadding="2" cellspacing="3" border="0" >
 * <thead>
 * <tr class="tableSubHeadingColor">
 *  <th class ="colFirst"align ="left"> year-of-era </th> <th class ="colFirst"align ="left"> era </th> 
 * <th class ="colFirst"align = left"> proleptic-year </th> <th class ="colLast"align ="left"> ISO prole
 * ptic-year </th>。
 * </tr>
 * </thead>
 * <tbody>
 * <tr class="rowColor">
 *  <td> 2 </td> <td> ROC </td> <td> 2 </td> <td> 1913 </td>
 * </tr>
 * <tr class="altColor">
 *  <td> 1 </td> <td> ROC </td> <td> 1 </td> <td> 1912 </td>
 * </tr>
 * <tr class="rowColor">
 *  <td> 1 </td> <td> BEFORE_ROC </td> <td> 0 </td> <td> 1911 </td>
 * </tr>
 * <tr class="altColor">
 *  <td> 2 </td> <td> BEFORE_ROC </td> <td> -1 </td> <td> 1910 </td>
 * 
 * @since 1.8
 */
public enum MinguoEra implements Era {

    /**
     * The singleton instance for the era before the current one, 'Before Republic of China Era',
     * which has the numeric value 0.
     * <p>
     * </tr>
     * </tbody>
     * </table>
     * <p>
     * <b>不要使用{@code ordinal()}来获取{@code MinguoEra}的数字表示形式。请改用{@code getValue()}。</b>
     * 
     *  @implSpec这是一个不可变和线程安全的枚举。
     * 
     */
    BEFORE_ROC,
    /**
     * The singleton instance for the current era, 'Republic of China Era',
     * which has the numeric value 1.
     * <p>
     *  在当前一个之前的时代的单例实例,"中华民国时代之前",其具有数值0。
     * 
     */
    ROC;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code MinguoEra} from an {@code int} value.
     * <p>
     * {@code MinguoEra} is an enum representing the Minguo eras of BEFORE_ROC/ROC.
     * This factory allows the enum to be obtained from the {@code int} value.
     *
     * <p>
     *  当前时代的单例实例,"中华民国时代",其数值为1。
     * 
     * 
     * @param minguoEra  the BEFORE_ROC/ROC value to represent, from 0 (BEFORE_ROC) to 1 (ROC)
     * @return the era singleton, not null
     * @throws DateTimeException if the value is invalid
     */
    public static MinguoEra of(int minguoEra) {
        switch (minguoEra) {
            case 0:
                return BEFORE_ROC;
            case 1:
                return ROC;
            default:
                throw new DateTimeException("Invalid era: " + minguoEra);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the numeric era {@code int} value.
     * <p>
     * The era BEFORE_ROC has the value 0, while the era ROC has the value 1.
     *
     * <p>
     *  从{@code int}值获取{@code MinguoEra}的实例。
     * <p>
     *  {@code MinguoEra}是一个表示BEFORE_ROC / ROC的Minguo时代的枚举。此工厂允许从{@code int}值获取枚举。
     * 
     * 
     * @return the era value, from 0 (BEFORE_ROC) to 1 (ROC)
     */
    @Override
    public int getValue() {
        return ordinal();
    }

}
