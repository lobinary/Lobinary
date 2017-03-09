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
 * Provides classes to print and parse dates and times.
 * </p>
 * <p>
 * Printing and parsing is based around the
 * {@link java.time.format.DateTimeFormatter DateTimeFormatter} class.
 * Instances are generally obtained from
 * {@link java.time.format.DateTimeFormatter DateTimeFormatter}, however
 * {@link java.time.format.DateTimeFormatterBuilder DateTimeFormatterBuilder}
 * can be used if more power is needed.
 * </p>
 * <p>
 * Localization occurs by calling
 * {@link java.time.format.DateTimeFormatter#withLocale(java.util.Locale) withLocale(Locale)}
 * on the formatter. Further customization is possible using
 * {@link java.time.format.DecimalStyle DecimalStyle}.
 * </p>
 *
 * <h3>Package specification</h3>
 * <p>
 * Unless otherwise noted, passing a null argument to a constructor or method in any class or interface
 * in this package will cause a {@link java.lang.NullPointerException NullPointerException} to be thrown.
 * <p>
 * <p>
 *  提供打印和解析日期和时间的类。
 * </p>
 * <p>
 *  打印和解析基于{@link java.time.format.DateTimeFormatter DateTimeFormatter}类。
 * 实例通常从{@link java.time.format.DateTimeFormatter DateTimeFormatter}获取,但是如果需要更多的权力,则可以使用{@link java.time.format.DateTimeFormatterBuilder DateTimeFormatterBuilder}
 * 。
 *  打印和解析基于{@link java.time.format.DateTimeFormatter DateTimeFormatter}类。
 * </p>
 * <p>
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
package java.time.format;
