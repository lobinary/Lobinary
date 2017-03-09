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
package java.time.temporal;

import java.time.DateTimeException;

/**
 * Strategy for adjusting a temporal object.
 * <p>
 * Adjusters are a key tool for modifying temporal objects.
 * They exist to externalize the process of adjustment, permitting different
 * approaches, as per the strategy design pattern.
 * Examples might be an adjuster that sets the date avoiding weekends, or one that
 * sets the date to the last day of the month.
 * <p>
 * There are two equivalent ways of using a {@code TemporalAdjuster}.
 * The first is to invoke the method on this interface directly.
 * The second is to use {@link Temporal#with(TemporalAdjuster)}:
 * <pre>
 *   // these two lines are equivalent, but the second approach is recommended
 *   temporal = thisAdjuster.adjustInto(temporal);
 *   temporal = temporal.with(thisAdjuster);
 * </pre>
 * It is recommended to use the second approach, {@code with(TemporalAdjuster)},
 * as it is a lot clearer to read in code.
 * <p>
 * The {@link TemporalAdjusters} class contains a standard set of adjusters,
 * available as static methods.
 * These include:
 * <ul>
 * <li>finding the first or last day of the month
 * <li>finding the first day of next month
 * <li>finding the first or last day of the year
 * <li>finding the first day of next year
 * <li>finding the first or last day-of-week within a month, such as "first Wednesday in June"
 * <li>finding the next or previous day-of-week, such as "next Thursday"
 * </ul>
 *
 * @implSpec
 * This interface places no restrictions on the mutability of implementations,
 * however immutability is strongly recommended.
 *
 * <p>
 *  调整时间对象的策略。
 * <p>
 *  调整器是修改时态对象的关键工具。它们存在于外部化调整的过程,允许不同的方法,根据战略设计模式。示例可以是设置避免周末的日期的调整器,或者将日期设置为该月的最后一天的日期。
 * <p>
 *  有两种等效的使用{@code TemporalAdjuster}的方法。第一种是直接在此接口上调用方法。第二个是使用{@link Temporal#with(TemporalAdjuster)}：
 * <pre>
 *  //这两行是等效的,但第二种方法是建议temporal = thisAdjuster.adjustInto(temporal); temporal = temporal.with(thisAdjust
 * er);。
 * </pre>
 * 建议使用第二种方法,{@code with(TemporalAdjuster)},因为它在代码中很容易阅读。
 * <p>
 *  {@link TemporalAdjusters}类包含一组标准调整器,可用作静态方法。这些包括：
 * <ul>
 *  <li>查找每月的第一天或最后一天<li>查找下个月的第一天<li>查找一年中的第一天或最后一天<li>找到明年的第一天<li>找到第一个或在一个月内的最后一天,例如"六月的第一个星期三"<li>找到
 * 下一个或前一个星期,例如"下星期四"。
 * </ul>
 * 
 *  @implSpec此接口对实现的可变性没有限制,但强烈建议使用不可变性。
 * 
 * 
 * @see TemporalAdjusters
 * @since 1.8
 */
@FunctionalInterface
public interface TemporalAdjuster {

    /**
     * Adjusts the specified temporal object.
     * <p>
     * This adjusts the specified temporal object using the logic
     * encapsulated in the implementing class.
     * Examples might be an adjuster that sets the date avoiding weekends, or one that
     * sets the date to the last day of the month.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisAdjuster.adjustInto(temporal);
     *   temporal = temporal.with(thisAdjuster);
     * </pre>
     * It is recommended to use the second approach, {@code with(TemporalAdjuster)},
     * as it is a lot clearer to read in code.
     *
     * @implSpec
     * The implementation must take the input object and adjust it.
     * The implementation defines the logic of the adjustment and is responsible for
     * documenting that logic. It may use any method on {@code Temporal} to
     * query the temporal object and perform the adjustment.
     * The returned object must have the same observable type as the input object
     * <p>
     * The input object must not be altered.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable temporal objects.
     * <p>
     * The input temporal object may be in a calendar system other than ISO.
     * Implementations may choose to document compatibility with other calendar systems,
     * or reject non-ISO temporal objects by {@link TemporalQueries#chronology() querying the chronology}.
     * <p>
     * This method may be called from multiple threads in parallel.
     * It must be thread-safe when invoked.
     *
     * <p>
     *  调整指定的时间对象。
     * <p>
     *  这使用在实现类中封装的逻辑来调整指定的时间对象。示例可以是设置避免周末的日期的调整器,或者将日期设置为该月的最后一天的日期。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link Temporal#with(TemporalAdjuster)}：
     * <pre>
     *  //这两行是等效的,但第二种方法是建议temporal = thisAdjuster.adjustInto(temporal); temporal = temporal.with(thisAdjust
     * er);。
     * </pre>
     *  建议使用第二种方法,{@code with(TemporalAdjuster)},因为它在代码中很容易阅读。
     * 
     * @param temporal  the temporal object to adjust, not null
     * @return an object of the same observable type with the adjustment made, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    Temporal adjustInto(Temporal temporal);

}
