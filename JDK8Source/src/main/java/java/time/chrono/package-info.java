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

/**
 * <p>
 * Generic API for calendar systems other than the default ISO.
 * </p>
 * <p>
 * The main API is based around the calendar system defined in ISO-8601.
 * However, there are other calendar systems, and this package provides basic support for them.
 * The alternate calendars are provided in the {@link java.time.chrono} package.
 * </p>
 * <p>
 * A calendar system is defined by the {@link java.time.chrono.Chronology} interface,
 * while a date in a calendar system is defined by the {@link java.time.chrono.ChronoLocalDate} interface.
 * </p>
 * <p>
 * It is intended that applications use the main API whenever possible, including code to read and write
 * from a persistent data store, such as a database, and to send dates and times across a network.
 * The "chrono" classes are then used at the user interface level to deal with localized input/output.
 * See {@link java.time.chrono.ChronoLocalDate ChronoLocalDate}
 * for a full discussion of the issues.
 * </p>
 * <p>
 * Using non-ISO calendar systems in an application introduces significant extra complexity.
 * Ensure that the warnings and recommendations in {@code ChronoLocalDate} have been read before
 * working with the "chrono" interfaces.
 * </p>
 * <p>
 * The supported calendar systems includes:
 * </p>
 * <ul>
 * <li>{@link java.time.chrono.HijrahChronology Hijrah calendar}</li>
 * <li>{@link java.time.chrono.JapaneseChronology Japanese calendar}</li>
 * <li>{@link java.time.chrono.MinguoChronology Minguo calendar}</li>
 * <li>{@link java.time.chrono.ThaiBuddhistChronology Thai Buddhist calendar}</li>
 * </ul>
 *
 * <h3>Example</h3>
 * <p>
 * This example lists todays date for all of the available calendars.
 * </p>
 * <pre>
 *   // Enumerate the list of available calendars and print todays date for each.
 *       Set&lt;Chronology&gt; chronos = Chronology.getAvailableChronologies();
 *       for (Chronology chrono : chronos) {
 *           ChronoLocalDate date = chrono.dateNow();
 *           System.out.printf("   %20s: %s%n", chrono.getId(), date.toString());
 *       }
 * </pre>
 *
 * <p>
 * This example creates and uses a date in a named non-ISO calendar system.
 * </p>
 * <pre>
 *   // Print the Thai Buddhist date
 *       ChronoLocalDate now1 = Chronology.of("ThaiBuddhist").dateNow();
 *       int day = now1.get(ChronoField.DAY_OF_MONTH);
 *       int dow = now1.get(ChronoField.DAY_OF_WEEK);
 *       int month = now1.get(ChronoField.MONTH_OF_YEAR);
 *       int year = now1.get(ChronoField.YEAR);
 *       System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChronology().getId(),
 *                 dow, day, month, year);
 *   // Print today's date and the last day of the year for the Thai Buddhist Calendar.
 *       ChronoLocalDate first = now1
 *                 .with(ChronoField.DAY_OF_MONTH, 1)
 *                 .with(ChronoField.MONTH_OF_YEAR, 1);
 *       ChronoLocalDate last = first
 *                 .plus(1, ChronoUnit.YEARS)
 *                 .minus(1, ChronoUnit.DAYS);
 *       System.out.printf("  %s: 1st of year: %s; end of year: %s%n", last.getChronology().getId(),
 *                 first, last);
 *  </pre>
 *
 * <p>
 * This example creates and uses a date in a specific ThaiBuddhist calendar system.
 * </p>
 * <pre>
 *   // Print the Thai Buddhist date
 *       ThaiBuddhistDate now1 = ThaiBuddhistDate.now();
 *       int day = now1.get(ChronoField.DAY_OF_MONTH);
 *       int dow = now1.get(ChronoField.DAY_OF_WEEK);
 *       int month = now1.get(ChronoField.MONTH_OF_YEAR);
 *       int year = now1.get(ChronoField.YEAR);
 *       System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChronology().getId(),
 *                 dow, day, month, year);
 *
 *   // Print today's date and the last day of the year for the Thai Buddhist Calendar.
 *       ThaiBuddhistDate first = now1
 *                 .with(ChronoField.DAY_OF_MONTH, 1)
 *                 .with(ChronoField.MONTH_OF_YEAR, 1);
 *       ThaiBuddhistDate last = first
 *                 .plus(1, ChronoUnit.YEARS)
 *                 .minus(1, ChronoUnit.DAYS);
 *       System.out.printf("  %s: 1st of year: %s; end of year: %s%n", last.getChronology().getId(),
 *                 first, last);
 *  </pre>
 *
 * <h3>Package specification</h3>
 * <p>
 * Unless otherwise noted, passing a null argument to a constructor or method in any class or interface
 * in this package will cause a {@link java.lang.NullPointerException NullPointerException} to be thrown.
 * <p>
 * <p>
 *  除默认ISO之外的日历系统的通用API。
 * </p>
 * <p>
 *  主要的API基于ISO-8601中定义的日历系统。但是,还有其他日历系统,这个包为他们提供了基本支持。替代日历在{@link java.time.chrono}包中提供。
 * </p>
 * <p>
 *  日历系统由{@link java.time.chrono.Chronology}界面定义,而日历系统中的日期由{@link java.time.chrono.ChronoLocalDate}界面定义。
 * </p>
 * <p>
 * 意图是应用程序尽可能使用主API,包括从持久数据存储(例如数据库)读取和写入的代码,以及跨网络发送日期和时间。然后在用户界面级别使用"计时"类来处理本地化输入/输出。
 * 有关这些问题的完整讨论,请参阅{@link java.time.chrono.ChronoLocalDate ChronoLocalDate}。
 * </p>
 * <p>
 *  在应用中使用非ISO日历系统引入了显着的额外复杂性。确保在使用"chrono"接口之前已阅读{@code ChronoLocalDate}中的警告和建议。
 * </p>
 * <p>
 *  支持的日历系统包括：
 * </p>
 * <ul>
 *  <li> {@ link java.time.chrono.Hijrah Chronology Hijrah calendar} </li> <li> {@ link java.time.chrono.Japanese Chronology Japanese calendar}
 *  </li> <li> {@ link java.time。
 *  chrono.Minguo Chronology Minguo calendar} </li> <li> {@ link java.time.chrono.ThaiBuddhistChronology泰国佛教日历}
 *  </li>。
 * </ul>
 * 
 *  <h3>示例</h3>
 * <p>
 *  此示例列出了所有可用日历的当前日期。
 * </p>
 * <pre>
 *  //枚举可用日历的列表,并为每个日历打印今天的日期。
 * 设置&lt; Chronology&gt; chronos = Chronology.getAvailableChronologies(); for(Chronology chrono：chronos)
 * {ChronoLocalDate date = chrono.dateNow(); System.out.printf("％20s：％s％n",chrono.getId(),date.toString()); }
 * }。
 *  //枚举可用日历的列表,并为每个日历打印今天的日期。
 * </pre>
 * 
 * 
 * The Javadoc "@param" definition is used to summarise the null-behavior.
 * The "@throws {@link java.lang.NullPointerException}" is not explicitly documented in each method.
 * </p>
 * <p>
 * All calculations should check for numeric overflow and throw either an {@link java.lang.ArithmeticException}
 * or a {@link java.time.DateTimeException}.
 * </p>
 * @since JDK1.8
 */
package java.time.chrono;
