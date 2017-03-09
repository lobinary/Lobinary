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
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.jaxp.datatype;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>Factory that creates new <code>javax.xml.datatype</code> <code>Object</code>s that map XML to/from Java <code>Object</code>s.</p>
 *
 * <p id="DatatypeFactory.newInstance">{@link #newInstance()} is used to create a new <code>DatatypeFactory</code>.
 * The following implementation resolution mechanisms are used in the following order:</p>
 * <ol>
 *    <li>
 *      If the system property specified by {@link #DATATYPEFACTORY_PROPERTY}, "<code>javax.xml.datatype.DatatypeFactory</code>",
 *      exists, a class with the name of the property's value is instantiated.
 *      Any Exception thrown during the instantiation process is wrapped as a {@link DatatypeConfigurationException}.
 *    </li>
 *    <li>
 *      If the file ${JAVA_HOME}/lib/jaxp.properties exists, it is loaded in a {@link java.util.Properties} <code>Object</code>.
 *      The <code>Properties</code> <code>Object </code> is then queried for the property as documented in the prior step
 *      and processed as documented in the prior step.
 *    </li>
 *    <li>
 *      The services resolution mechanism is used, e.g. <code>META-INF/services/java.xml.datatype.DatatypeFactory</code>.
 *      Any Exception thrown during the instantiation process is wrapped as a {@link DatatypeConfigurationException}.
 *    </li>
 *    <li>
 *      The final mechanism is to attempt to instantiate the <code>Class</code> specified by
 *      {@link #DATATYPEFACTORY_IMPLEMENTATION_CLASS}, "<code>javax.xml.datatype.DatatypeFactoryImpl</code>".
 *      Any Exception thrown during the instantiation process is wrapped as a {@link DatatypeConfigurationException}.
 *    </li>
 * </ol>
 *
 * <p>
 *  <p>工厂创建新的<code> javax.xml.datatype </code> <code>对象</code>,将XML映射到/从Java <code> Object </code>
 * 
 *  <p id ="DatatypeFactory.newInstance"> {@ link #newInstance()}用于创建新的<code> DatatypeFactory </code>。
 * 以下执行解决机制按以下顺序使用：</p>。
 * <ol>
 * <li>
 *  如果存在由{@link #DATATYPEFACTORY_PROPERTY},"<code> javax.xml.datatype.DatatypeFactory </code>"指定的系统属性,则实
 * 例化具有属性值的名称的类。
 * 在实例化过程中抛出的任何异常都将作为{@link DatatypeConfigurationException}打包。
 * </li>
 * <li>
 * 如果文件$ {JAVA_HOME} /lib/jaxp.properties存在,则将其加载到{@link java.util.Properties} <code> Object </code>中。
 * 然后查询<code> Properties </code> <code> Object </code>中的属性,如上一步骤中所述,并按照上一步骤中所述进行处理。
 * </li>
 * <li>
 *  使用服务解析机制,例如。 <code> META-INF / services / java.xml.datatype.DatatypeFactory </code>。
 * 在实例化过程中抛出的任何异常都将作为{@link DatatypeConfigurationException}打包。
 * </li>
 * <li>
 *  最终机制是尝试实例化由{@link #DATATYPEFACTORY_IMPLEMENTATION_CLASS},"<code> javax.xml.datatype.DatatypeFactoryI
 * mpl </code>"指定的<code> Class </code>。
 * 在实例化过程中抛出的任何异常都将作为{@link DatatypeConfigurationException}打包。
 * </li>
 * </ol>
 * 
 * 
 * @author <a href="mailto:Joseph.Fialli@Sun.COM">Joseph Fialli</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @version $Id: DatatypeFactoryImpl.java,v 1.6 2010/05/19 05:02:55 joehw Exp $
 */
public class DatatypeFactoryImpl
        extends DatatypeFactory {

        /**
         * <p>Public constructor is empty..</p>
         *
         * <p>Use {@link DatatypeFactory#newInstance()} to create a <code>DatatypeFactory</code>.</p>
         * <p>
         *  <p>公共构造函数为空。</p>
         * 
         *  <p>使用{@link DatatypeFactory#newInstance()}创建<code> DatatypeFactory </code>。</p>
         * 
         */
        public DatatypeFactoryImpl() {
        }

        /**
         * <p>Obtain a new instance of a <code>Duration</code>
         * specifying the <code>Duration</code> as its string representation, "PnYnMnDTnHnMnS",
         * as defined in XML Schema 1.0 section 3.2.6.1.</p>
         *
         * <p>XML Schema Part 2: Datatypes, 3.2.6 duration, defines <code>duration</code> as:</p>
         * <blockquote>
         * duration represents a duration of time.
         * The value space of duration is a six-dimensional space where the coordinates designate the
         * Gregorian year, month, day, hour, minute, and second components defined in Section 5.5.3.2 of [ISO 8601], respectively.
         * These components are ordered in their significance by their order of appearance i.e. as
         * year, month, day, hour, minute, and second.
         * </blockquote>
     * <p>All six values are set and availabe from the created {@link Duration}</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>
     *  <p>获取<code> Duration </code>的一个新实例,指定<code> Duration </code>作为其字符串表示形式"PnYnMnDTnHnMnS",如XML Schema 1
     * .0第3.2.6.1节中所定义。
     * </p >。
     * 
     *  <p> XML模式第2部分：数据类型,3.2.6 duration,将<code> duration </code>定义为</p>
     * <blockquote>
     * 持续时间表示持续时间。持续时间的值空间是一个六维空间,其中坐标分别指定[ISO 8601]第5.5.3.2节中定义的格里高利年,月,日,小时,分和秒成分。
     * 这些组分按它们的重要性按它们的出现顺序排序,即作为年,月,日,小时,分钟和秒。
     * </blockquote>
     *  <p>所有六个值均已设置,并可从创建的{@link Duration} </p>中获得
     * 
     *  <p> XML模式规范说明值可以是任意大小。实现可以选择不支持或不能支持任意大和/或小的值。
     * 如果超出实施容量,将抛出{@link UnsupportedOperationException}并显示实施限制的信息。</p>。
     * 
     * 
         * @param lexicalRepresentation <code>String</code> representation of a <code>Duration</code>.
         *
         * @return New <code>Duration</code> created from parsing the <code>lexicalRepresentation</code>.
         *
         * @throws IllegalArgumentException If <code>lexicalRepresentation</code> is not a valid representation of a <code>Duration</code>.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         * @throws NullPointerException if <code>lexicalRepresentation</code> is <code>null</code>.
         */
        public Duration newDuration(final String lexicalRepresentation) {

                return new DurationImpl(lexicalRepresentation);
        }

        /**
         * <p>Obtain a new instance of a <code>Duration</code>
         * specifying the <code>Duration</code> as milliseconds.</p>
         *
         * <p>XML Schema Part 2: Datatypes, 3.2.6 duration, defines <code>duration</code> as:</p>
         * <blockquote>
         * duration represents a duration of time.
         * The value space of duration is a six-dimensional space where the coordinates designate the
         * Gregorian year, month, day, hour, minute, and second components defined in Section 5.5.3.2 of [ISO 8601], respectively.
         * These components are ordered in their significance by their order of appearance i.e. as
         * year, month, day, hour, minute, and second.
         * </blockquote>
     * <p>All six values are set by computing their values from the specified milliseconds
     * and are availabe using the <code>get</code> methods of  the created {@link Duration}.
     * The values conform to and are defined by:</p>
     * <ul>
     *   <li>ISO 8601:2000(E) Section 5.5.3.2 Alternative format</li>
     *   <li><a href="http://www.w3.org/TR/xmlschema-2/#isoformats">
     *     W3C XML Schema 1.0 Part 2, Appendix D, ISO 8601 Date and Time Formats</a>
     *   </li>
     *   <li>{@link XMLGregorianCalendar}  Date/Time Datatype Field Mapping Between XML Schema 1.0 and Java Representation</li>
     * </ul>
         *
         * <p>The default start instance is defined by {@link GregorianCalendar}'s use of the start of the epoch: i.e.,
         * {@link java.util.Calendar#YEAR} = 1970,
         * {@link java.util.Calendar#MONTH} = {@link java.util.Calendar#JANUARY},
         * {@link java.util.Calendar#DATE} = 1, etc.
         * This is important as there are variations in the Gregorian Calendar,
         * e.g. leap years have different days in the month = {@link java.util.Calendar#FEBRUARY}
         * so the result of {@link Duration#getMonths()} and {@link Duration#getDays()} can be influenced.</p>
         *
         * <p>
         *  <p>获取<code> Duration </code>的新实例,指定<code> Duration </code>为毫秒。</p>
         * 
         *  <p> XML模式第2部分：数据类型,3.2.6 duration,将<code> duration </code>定义为</p>
         * <blockquote>
         *  持续时间表示持续时间。持续时间的值空间是一个六维空间,其中坐标分别指定[ISO 8601]第5.5.3.2节中定义的格里高利年,月,日,小时,分和秒成分。
         * 这些组分按它们的重要性按它们的出现顺序排序,即作为年,月,日,小时,分钟和秒。
         * </blockquote>
         * <p>所有六个值都是通过从指定的毫秒计算它们的值来设置的,并且可以使用所创建的{@link Duration}的<code> get </code>方法。值符合并由以下定义：</p>
         * <ul>
         *  <li> ISO 8601：2000(E)第5.5.3.2节替代格式</li> <li> <a href="http://www.w3.org/TR/xmlschema-2/#isoformats">
         *  W3C XML Schema 1.0第2部分,附录D,ISO 8601日期和时间格式</a>。
         * </li>
         *  <li> {@ link XMLGregorianCalendar}日期/时间XML模式1.0与Java表示之间的数据类型字段映射</li>
         * </ul>
         * 
         *  <p>默认的开始实例由{@link GregorianCalendar}使用时代的开始来定义：例如{@link java.util.Calendar#YEAR} = 1970,{@link java.util.Calendar# MONTH}
         *  = {@link java.util.Calendar#JANUARY},{@link java.util.Calendar#DATE} = 1等。
         * 这很重要,因为公历日历中有变化,例如闰年在当月有不同的日期= {@link java.util.Calendar#FEBRUARY},因此{@link Duration#getMonths()}和{@link Duration#getDays()}
         * 的结果可能会受到影响。
         * </p >。
         * 
         * 
         * @param durationInMilliseconds Duration in milliseconds to create.
         *
         * @return New <code>Duration</code> representing <code>durationInMilliseconds</code>.
         */
        public Duration newDuration(final long durationInMilliseconds) {

                return new DurationImpl(durationInMilliseconds);
        }

        /**
         * <p>Obtain a new instance of a <code>Duration</code>
         * specifying the <code>Duration</code> as isPositive, years, months, days, hours, minutes, seconds.</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>
     *  <p>获取<code> Duration </code>的一个新实例,将<code> Duration </code>指定为isPositive,年,月,日,小时,分钟,秒。
     * 
     * <p> XML模式规范说明值可以是任意大小。实现可以选择不支持或不能支持任意大和/或小的值。
     * 如果超出实施容量,将抛出{@link UnsupportedOperationException}并显示实施限制的消息。</p>。
     * 
     * 
         * @param isPositive Set to <code>false</code> to create a negative duration. When the length
         *   of the duration is zero, this parameter will be ignored.
         * @param years of this <code>Duration</code>
         * @param months of this <code>Duration</code>
         * @param days of this <code>Duration</code>
         * @param hours of this <code>Duration</code>
         * @param minutes of this <code>Duration</code>
         * @param seconds of this <code>Duration</code>
         *
         * @return New <code>Duration</code> created from the specified values.
         *
         * @throws IllegalArgumentException If values are not a valid representation of a <code>Duration</code>.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         * @throws NullPointerException If any values are <code>null</code>.
         *
         * @see #newDuration(boolean isPositive, BigInteger years, BigInteger months, BigInteger days,
         *   BigInteger hours, BigInteger minutes, BigDecimal seconds)
         */
        public Duration newDuration(
                final boolean isPositive,
                final BigInteger years,
                final BigInteger months,
                final BigInteger days,
                final BigInteger hours,
                final BigInteger minutes,
                final BigDecimal seconds) {

                return new DurationImpl(
                                isPositive,
                                years,
                                months,
                                days,
                                hours,
                                minutes,
                                seconds
                        );
                }
        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:yearMonthDuration</code> using the specified
         * <code>year</code> and <code>month</code> as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:yearMonthDuration</a>.</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>A <code>null</code> value indicates that field is not set.</p>
     *
     * <p>
     *  <p>使用<code>年</code>和<code>月</code>定义的<code> xdt：yearMonthDuration </code>类型创建<code> Duration </code>
     * 。
     * <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
     *  XQuery 1.0和XPath 2.0数据模型,xdt：yearMonthDuration </a>。</p>
     * 
     *  <p> XML模式规范说明值可以是任意大小。实现可以选择不支持或不能支持任意大和/或小的值。
     * 如果超出实施容量,将抛出{@link UnsupportedOperationException}并显示实施限制的消息。</p>。
     * 
     *  <p> <code> null </code>值表示未设置字段。</p>
     * 
     * 
     * @param isPositive Set to <code>false</code> to create a negative duration. When the length
     *   of the duration is zero, this parameter will be ignored.
         * @param year Year of <code>Duration</code>.
         * @param month Month of <code>Duration</code>.
         *
         * @return New <code>Duration</code> created using the specified <code>year</code> and <code>month</code>.
         *
         * @throws IllegalArgumentException If the values are not a valid representation of a
         * <code>Duration</code>: if all of the fields (year, month) are null or
         * if any of the fields is negative.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         */
        public Duration newDurationYearMonth(
                final boolean isPositive,
                final BigInteger year,
                final BigInteger month) {

                return new DurationYearMonthImpl(
                         isPositive,
                         year,
                         month
                 );

        }
        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:yearMonthDuration</code> using the specified
         * <code>year</code> and <code>month</code> as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:yearMonthDuration</a>.</p>
         *
     * <p>A {@link DatatypeConstants#FIELD_UNDEFINED} value indicates that field is not set.</p>
     *
     * <p>
     *  <p>使用<code>年</code>和<code>月</code>定义的<code> xdt：yearMonthDuration </code>类型创建<code> Duration </code>
     * 。
     * <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
     *  XQuery 1.0和XPath 2.0数据模型,xdt：yearMonthDuration </a>。</p>
     * 
     *  <p> {@link DatatypeConstants#FIELD_UNDEFINED}值表示未设置字段。</p>
     * 
     * 
     * @param isPositive Set to <code>false</code> to create a negative duration. When the length
     *   of the duration is zero, this parameter will be ignored.
         * @param year Year of <code>Duration</code>.
         * @param month Month of <code>Duration</code>.
         *
         * @return New <code>Duration</code> created using the specified <code>year</code> and <code>month</code>.
         *
         * @throws IllegalArgumentException If the values are not a valid representation of a
         * <code>Duration</code>: if any of the fields (year, month) is negative.
         */
    @Override
        public Duration newDurationYearMonth(
                final boolean isPositive,
                final int year,
                final int month) {

                return new DurationYearMonthImpl(
                        isPositive,
                        year,
                        month);
                }

        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:yearMonthDuration</code> by parsing its <code>String</code> representation,
         * "<em>PnYnM</em>", <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:yearMonthDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:yearMonthDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only year and month components.
         * This datatype resides in the namespace {@link javax.xml.XMLConstants#W3C_XPATH_DATATYPE_NS_URI}.</p>
         *
     * <p>Both values are set and availabe from the created {@link Duration}</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>
     * <p>通过解析<code> String </code>表示形式,"<em> PnYnM </em>",创建<code> xdt：yearMonthDuration </code>类型的<code> D
     * uration </code> a href ="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration"> XQuery 1.0和XPath 2.
     * 0数据模型,xdt：yearMonthDuration </a>。
     * </p>。
     * 
     *  <p>数据类型<code> xdt：yearMonthDuration </code>是<code> xs：duration </code>的子类型,其词法表示仅包含年份和月份组件。
     * 此数据类型位于命名空间{@link javax.xml.XMLConstants#W3C_XPATH_DATATYPE_NS_URI}中。</p>。
     * 
     *  <p>这两个值都已设置,并可从创建的{@link Duration} </p>获取
     * 
     *  <p> XML模式规范说明值可以是任意大小。实现可以选择不支持或不能支持任意大和/或小的值。
     * 如果超出实施容量,将抛出{@link UnsupportedOperationException}并显示实施限制的消息。</p>。
     * 
     * 
         * @param lexicalRepresentation Lexical representation of a duration.
         *
         * @return New <code>Duration</code> created using the specified <code>lexicalRepresentation</code>.
         *
         * @throws IllegalArgumentException If <code>lexicalRepresentation</code> is not a valid representation of a <code>Duration</code> expressed only in terms of years and months.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         * @throws NullPointerException If <code>lexicalRepresentation</code> is <code>null</code>.
         */
        public Duration newDurationYearMonth(
                final String lexicalRepresentation) {

                return new DurationYearMonthImpl(lexicalRepresentation);

        }
        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:yearMonthDuration</code> using the specified milliseconds as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:yearMonthDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:yearMonthDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only year and month components.
         * This datatype resides in the namespace {@link javax.xml.XMLConstants#W3C_XPATH_DATATYPE_NS_URI}.</p>
         *
     * <p>Both values are set by computing their values from the specified milliseconds
     * and are availabe using the <code>get</code> methods of  the created {@link Duration}.
     * The values conform to and are defined by:</p>
     * <ul>
     *   <li>ISO 8601:2000(E) Section 5.5.3.2 Alternative format</li>
     *   <li><a href="http://www.w3.org/TR/xmlschema-2/#isoformats">
     *     W3C XML Schema 1.0 Part 2, Appendix D, ISO 8601 Date and Time Formats</a>
     *   </li>
     *   <li>{@link XMLGregorianCalendar}  Date/Time Datatype Field Mapping Between XML Schema 1.0 and Java Representation</li>
     * </ul>
     *
         * <p>The default start instance is defined by {@link GregorianCalendar}'s use of the start of the epoch: i.e.,
         * {@link java.util.Calendar#YEAR} = 1970,
         * {@link java.util.Calendar#MONTH} = {@link java.util.Calendar#JANUARY},
         * {@link java.util.Calendar#DATE} = 1, etc.
         * This is important as there are variations in the Gregorian Calendar,
         * e.g. leap years have different days in the month = {@link java.util.Calendar#FEBRUARY}
         * so the result of {@link Duration#getMonths()} can be influenced.</p>
         *
     * <p>Any remaining milliseconds after determining the year and month are discarded.</p>
         *
         * <p>
         *  <p>使用<code> xdt：yearMonthDuration </code>类型创建<code> Duration </code>
         * <a href="http://www.w3.org/TR/xpath-datamodel#yearMonthDuration">
         *  XQuery 1.0和XPath 2.0数据模型,xdt：yearMonthDuration </a>。</p>
         * 
         *  <p>数据类型<code> xdt：yearMonthDuration </code>是<code> xs：duration </code>的子类型,其词法表示仅包含年份和月份组件。
         * 此数据类型位于命名空间{@link javax.xml.XMLConstants#W3C_XPATH_DATATYPE_NS_URI}中。</p>。
         * 
         * <p>这两个值都是通过从指定的毫秒计算它们的值来设置的,并且可以使用所创建的{@link Duration}的<code> get </code>方法。值符合并由以下定义：</p>
         * <ul>
         *  <li> ISO 8601：2000(E)第5.5.3.2节替代格式</li> <li> <a href="http://www.w3.org/TR/xmlschema-2/#isoformats">
         *  W3C XML Schema 1.0第2部分,附录D,ISO 8601日期和时间格式</a>。
         * </li>
         *  <li> {@ link XMLGregorianCalendar}日期/时间XML模式1.0与Java表示之间的数据类型字段映射</li>
         * </ul>
         * 
         *  <p>默认的开始实例由{@link GregorianCalendar}使用时代的开始来定义：例如{@link java.util.Calendar#YEAR} = 1970,{@link java.util.Calendar# MONTH}
         *  = {@link java.util.Calendar#JANUARY},{@link java.util.Calendar#DATE} = 1等。
         * 这很重要,因为公历日历中有变化,例如闰年在不同的日期= {@link java.util.Calendar#FEBRUARY},因此{@link Duration#getMonths()}的结果可能会受
         * 到影响。
         * </p>。
         * 
         *  <p>确定年份和月份后的任何剩余毫秒将被舍弃。</p>
         * 
         * 
         * @param durationInMilliseconds Milliseconds of <code>Duration</code> to create.
         *
         * @return New <code>Duration</code> created using the specified <code>durationInMilliseconds</code>.
         */
    public Duration newDurationYearMonth(
            final long durationInMilliseconds) {

        return new DurationYearMonthImpl(durationInMilliseconds);
    }

        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:dayTimeDuration</code> by parsing its <code>String</code> representation,
         * "<em>PnDTnHnMnS</em>", <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:dayTimeDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only day, hour, minute, and second components.
         * This datatype resides in the namespace <code>http://www.w3.org/2003/11/xpath-datatypes</code>.</p>
         *
     * <p>All four values are set and availabe from the created {@link Duration}</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>
     *  <p>通过解析其<code> String </code>表示形式,"<em> PnDTnHnMnS </em>",创建<code> xdt：dayTimeDuration </code>类型的<code>
     *  Duration </code> a href ="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration"> XQuery 1.0和XPath 2.
     * 0数据模型,xdt：dayTimeDuration </a>。
     * </p>。
     * 
     * <P>数据类型<代码> XDT：dayTimeDuration </代码>是<代码> XS的子类型：持续时间</代码>的词汇表示只包含日,小时,分钟和秒组成。
     * 此数据类型位于命名空间<code> http://www.w3.org/2003/11/xpath-datatypes </code>中。</p>。
     * 
     *  <p>所有四个值都已设置,并可从创建的{@link Duration} </p>中获得
     * 
     *  <p> XML模式规范说明值可以是任意大小。实现可以选择不支持或不能支持任意大和/或小的值。
     * 如果超出实施容量,将抛出{@link UnsupportedOperationException}并显示实施限制的消息。</p>。
     * 
     * 
         * @param lexicalRepresentation Lexical representation of a duration.
         *
         * @return New <code>Duration</code> created using the specified <code>lexicalRepresentation</code>.
         *
         * @throws IllegalArgumentException If <code>lexicalRepresentation</code> is not a valid representation of a <code>Duration</code> expressed only in terms of days and time.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         * @throws NullPointerException If <code>lexicalRepresentation</code> is <code>null</code>.
         */
        public Duration newDurationDayTime(final String lexicalRepresentation) {
            // lexicalRepresentation must be non-null
            if (lexicalRepresentation == null) {
                throw new NullPointerException(
                    "Trying to create an xdt:dayTimeDuration with an invalid"
                    + " lexical representation of \"null\"");
            }

            return new DurationDayTimeImpl(lexicalRepresentation);
        }

        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:dayTimeDuration</code> using the specified milliseconds as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:dayTimeDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only day, hour, minute, and second components.
         * This datatype resides in the namespace <code>http://www.w3.org/2003/11/xpath-datatypes</code>.</p>
         *
     * <p>All four values are set by computing their values from the specified milliseconds
     * and are availabe using the <code>get</code> methods of  the created {@link Duration}.
     * The values conform to and are defined by:</p>
     * <ul>
     *   <li>ISO 8601:2000(E) Section 5.5.3.2 Alternative format</li>
     *   <li><a href="http://www.w3.org/TR/xmlschema-2/#isoformats">
     *     W3C XML Schema 1.0 Part 2, Appendix D, ISO 8601 Date and Time Formats</a>
     *   </li>
     *   <li>{@link XMLGregorianCalendar}  Date/Time Datatype Field Mapping Between XML Schema 1.0 and Java Representation</li>
     * </ul>
         *
         * <p>The default start instance is defined by {@link GregorianCalendar}'s use of the start of the epoch: i.e.,
         * {@link java.util.Calendar#YEAR} = 1970,
         * {@link java.util.Calendar#MONTH} = {@link java.util.Calendar#JANUARY},
         * {@link java.util.Calendar#DATE} = 1, etc.
         * This is important as there are variations in the Gregorian Calendar,
         * e.g. leap years have different days in the month = {@link java.util.Calendar#FEBRUARY}
         * so the result of {@link Duration#getDays()} can be influenced.</p>
         *
     * <p>Any remaining milliseconds after determining the day, hour, minute and second are discarded.</p>
     *
     * <p>
     *  <p>使用指定的毫秒数创建<code> xdt：dayTimeDuration </code>类型的<code> Duration </code>
     * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
     *  XQuery 1.0和XPath 2.0数据模型,xdt：dayTimeDuration </a>。</p>
     * 
     *  <P>数据类型<代码> XDT：dayTimeDuration </代码>是<代码> XS的子类型：持续时间</代码>的词汇表示只包含日,小时,分钟和秒组成。
     * 此数据类型位于命名空间<code> http://www.w3.org/2003/11/xpath-datatypes </code>中。</p>。
     * 
     *  <p>通过从指定的毫秒计算它们的值来设置所有四个值,并且可以使用所创建的{@link Duration}的<code> get </code>方法。值符合并由以下定义：</p>
     * <ul>
     * <li> ISO 8601：2000(E)第5.5.3.2节替代格式</li> <li> <a href="http://www.w3.org/TR/xmlschema-2/#isoformats"> 
     * W3C XML Schema 1.0第2部分,附录D,ISO 8601日期和时间格式</a>。
     * </li>
     *  <li> {@ link XMLGregorianCalendar}日期/时间XML模式1.0与Java表示之间的数据类型字段映射</li>
     * </ul>
     * 
     *  <p>默认的开始实例由{@link GregorianCalendar}使用时代的开始来定义：例如{@link java.util.Calendar#YEAR} = 1970,{@link java.util.Calendar# MONTH}
     *  = {@link java.util.Calendar#JANUARY},{@link java.util.Calendar#DATE} = 1等。
     * 这很重要,因为公历日历中有变化,例如闰年在月份中有不同的日期= {@link java.util.Calendar#FEBRUARY},因此{@link Duration#getDays()}的结果可能
     * 会受到影响。
     * </p>。
     * 
     *  <p>确定日,小时,分和秒后的任何剩余毫秒将被舍弃。</p>
     * 
     * 
         * @param durationInMilliseconds Milliseconds of <code>Duration</code> to create.
         *
         * @return New <code>Duration</code> created with the specified <code>durationInMilliseconds</code>.
         *
         * @see <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>
         */
        public Duration newDurationDayTime(final long durationInMilliseconds) {

                return new DurationDayTimeImpl(durationInMilliseconds);
        }

        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:dayTimeDuration</code> using the specified
         * <code>day</code>, <code>hour</code>, <code>minute</code> and <code>second</code> as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:dayTimeDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only day, hour, minute, and second components.
         * This datatype resides in the namespace <code>http://www.w3.org/2003/11/xpath-datatypes</code>.</p>
         *
     * <p>The XML Schema specification states that values can be of an arbitrary size.
     * Implementations may chose not to or be incapable of supporting arbitrarily large and/or small values.
     * An {@link UnsupportedOperationException} will be thrown with a message indicating implementation limits
     * if implementation capacities are exceeded.</p>
     *
     * <p>A <code>null</code> value indicates that field is not set.</p>
     *
     * <p>
     *  <p>使用指定的<code> day </code>,<code>小时</code>,<code>分钟创建<code> xdt：dayTimeDuration </code>类型的<code> Dur
     * ation </code> </code>和<code> second </code>中定义。
     * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
     *  XQuery 1.0和XPath 2.0数据模型,xdt：dayTimeDuration </a>。</p>
     * 
     *  <P>数据类型<代码> XDT：dayTimeDuration </代码>是<代码> XS的子类型：持续时间</代码>的词汇表示只包含日,小时,分钟和秒组成。
     * 此数据类型位于命名空间<code> http://www.w3.org/2003/11/xpath-datatypes </code>中。</p>。
     * 
     * <p> XML模式规范说明值可以是任意大小。实现可以选择不支持或不能支持任意大和/或小的值。
     * 如果超出实施容量,将抛出{@link UnsupportedOperationException}并显示实施限制的消息。</p>。
     * 
     *  <p> <code> null </code>值表示未设置字段。</p>
     * 
     * 
     * @param isPositive Set to <code>false</code> to create a negative duration. When the length
     *   of the duration is zero, this parameter will be ignored.
         * @param day Day of <code>Duration</code>.
         * @param hour Hour of <code>Duration</code>.
         * @param minute Minute of <code>Duration</code>.
         * @param second Second of <code>Duration</code>.
         *
         * @return New <code>Duration</code> created with the specified <code>day</code>, <code>hour</code>, <code>minute</code>
         * and <code>second</code>.
         *
         * @throws IllegalArgumentException If the values are not a valid representation of a
         * <code>Duration</code>: if all the fields (day, hour, ...) are null or
         * if any of the fields is negative.
         * @throws UnsupportedOperationException If implementation cannot support requested values.
         */
        public Duration newDurationDayTime(
                final boolean isPositive,
                final BigInteger day,
                final BigInteger hour,
                final BigInteger minute,
                final BigInteger second) {

                return new DurationDayTimeImpl(
                        isPositive,
                        day,
                        hour,
                        minute,
                        (second != null)? new BigDecimal(second):null
                );
        }

        /**
         * <p>Create a <code>Duration</code> of type <code>xdt:dayTimeDuration</code> using the specified
         * <code>day</code>, <code>hour</code>, <code>minute</code> and <code>second</code> as defined in
         * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
         *   XQuery 1.0 and XPath 2.0 Data Model, xdt:dayTimeDuration</a>.</p>
         *
         * <p>The datatype <code>xdt:dayTimeDuration</code> is a subtype of <code>xs:duration</code>
         * whose lexical representation contains only day, hour, minute, and second components.
         * This datatype resides in the namespace <code>http://www.w3.org/2003/11/xpath-datatypes</code>.</p>
         *
     * <p>A {@link DatatypeConstants#FIELD_UNDEFINED} value indicates that field is not set.</p>
     *
     * <p>
     *  <p>使用指定的<code> day </code>,<code>小时</code>,<code>分钟创建<code> xdt：dayTimeDuration </code>类型的<code> Dur
     * ation </code> </code>和<code> second </code>中定义。
     * <a href="http://www.w3.org/TR/xpath-datamodel#dayTimeDuration">
     *  XQuery 1.0和XPath 2.0数据模型,xdt：dayTimeDuration </a>。</p>
     * 
     *  <P>数据类型<代码> XDT：dayTimeDuration </代码>是<代码> XS的子类型：持续时间</代码>的词汇表示只包含日,小时,分钟和秒组成。
     * 此数据类型位于命名空间<code> http://www.w3.org/2003/11/xpath-datatypes </code>中。</p>。
     * 
     *  <p> {@link DatatypeConstants#FIELD_UNDEFINED}值表示未设置字段。</p>
     * 
     * 
     * @param isPositive Set to <code>false</code> to create a negative duration. When the length
     *   of the duration is zero, this parameter will be ignored.
         * @param day Day of <code>Duration</code>.
         * @param hour Hour of <code>Duration</code>.
         * @param minute Minute of <code>Duration</code>.
         * @param second Second of <code>Duration</code>.
         *
         * @return New <code>Duration</code> created with the specified <code>day</code>, <code>hour</code>, <code>minute</code>
         * and <code>second</code>.
         *
         * @throws IllegalArgumentException If the values are not a valid representation of a
         * <code>Duration</code>: if any of the fields (day, hour, ...) is negative.
         */
        public Duration newDurationDayTime(
                final boolean isPositive,
                final int day,
                final int hour,
                final int minute,
                final int second) {

                        return new DurationDayTimeImpl(
                                isPositive,
                                day,
                                hour,
                                minute,
                                second
                                );
                }

        /**
         * <p>Create a new instance of an <code>XMLGregorianCalendar</code>.</p>
         *
     * <p>All date/time datatype fields set to {@link DatatypeConstants#FIELD_UNDEFINED} or null.</p>
     *
     * <p>
     *  <p>创建<code> XMLGregorianCalendar </code>的新实例。</p>
     * 
     *  <p>所有日期/时间数据类型字段设置为{@link DatatypeConstants#FIELD_UNDEFINED}或null。</p>
     * 
     * 
     * @return New <code>XMLGregorianCalendar</code> with all date/time datatype fields set to
     *   {@link DatatypeConstants#FIELD_UNDEFINED} or null.
         */
        public XMLGregorianCalendar newXMLGregorianCalendar() {

                return new XMLGregorianCalendarImpl();
        }

        /**
         * <p>Create a new XMLGregorianCalendar by parsing the String as a lexical representation.</p>
         *
     * <p>Parsing the lexical string representation is defined in
     * <a href="http://www.w3.org/TR/xmlschema-2/#dateTime-order">XML Schema 1.0 Part 2, Section 3.2.[7-14].1,
     * <em>Lexical Representation</em>.</a></p>
     *
     * <p>The string representation may not have any leading and trailing whitespaces.</p>
     *
     * <p>The parsing is done field by field so that
     * the following holds for any lexically correct String x:</p>
     * <pre>
     * newXMLGregorianCalendar(x).toXMLFormat().equals(x)
     * </pre>
     * <p>Except for the noted lexical/canonical representation mismatches
     * listed in <a href="http://www.w3.org/2001/05/xmlschema-errata#e2-45">
     * XML Schema 1.0 errata, Section 3.2.7.2</a>.</p>
         *
         * <p>
         *  <p>通过将String解析为词法表示来创建新的XMLGregorianCalendar。</p>
         * 
         *  <p>解析词法字符串表示形式的定义在<a href="http://www.w3.org/TR/xmlschema-2/#dateTime-order"> XML模式1.0第2部分,第3.2节。
         * [7- 。
         * 
         * <p>字符串表示可能没有任何前导和尾随空格。</p>
         * 
         *  <p>解析是逐字段完成的,因此对于任何词法正确的字符串x：</p>都适用
         * <pre>
         *  newXMLGregorianCalendar(x).toXMLFormat()。equals(x)
         * </pre>
         *  <p>除了<a href="http://www.w3.org/2001/05/xmlschema-errata#e2-45"> XML Schema 1.0勘误表,第3.2节中列出的注意到的词汇/规
         * 范表示不匹配。
         *  7.2 </a>。</p>。
         * 
         * 
         * @param lexicalRepresentation Lexical representation of one the eight XML Schema date/time datatypes.
         *
         * @return <code>XMLGregorianCalendar</code> created from the <code>lexicalRepresentation</code>.
         *
         * @throws IllegalArgumentException If the <code>lexicalRepresentation</code> is not a valid <code>XMLGregorianCalendar</code>.
         * @throws NullPointerException If <code>lexicalRepresentation</code> is <code>null</code>.
         */
        public XMLGregorianCalendar newXMLGregorianCalendar(final String lexicalRepresentation) {

                return new XMLGregorianCalendarImpl(lexicalRepresentation);
        }

        /**
         * <p>Create an <code>XMLGregorianCalendar</code> from a {@link GregorianCalendar}.</p>
         *
         * <table border="2" rules="all" cellpadding="2">
         *   <thead>
         *     <tr>
         *       <th align="center" colspan="2">
         *          Field by Field Conversion from
         *          {@link GregorianCalendar} to an {@link XMLGregorianCalendar}
         *       </th>
         *     </tr>
         *     <tr>
         *        <th><code>java.util.GregorianCalendar</code> field</th>
         *        <th><code>javax.xml.datatype.XMLGregorianCalendar</code> field</th>
         *     </tr>
         *   </thead>
         *   <tbody>
         *     <tr>
         *       <td><code>ERA == GregorianCalendar.BC ? -YEAR : YEAR</code></td>
         *       <td>{@link XMLGregorianCalendar#setYear(int year)}</td>
         *     </tr>
         *     <tr>
         *       <td><code>MONTH + 1</code></td>
         *       <td>{@link XMLGregorianCalendar#setMonth(int month)}</td>
         *     </tr>
         *     <tr>
         *       <td><code>DAY_OF_MONTH</code></td>
         *       <td>{@link XMLGregorianCalendar#setDay(int day)}</td>
         *     </tr>
         *     <tr>
         *       <td><code>HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND</code></td>
         *       <td>{@link XMLGregorianCalendar#setTime(int hour, int minute, int second, BigDecimal fractional)}</td>
         *     </tr>
         *     <tr>
         *       <td>
         *         <code>(ZONE_OFFSET + DST_OFFSET) / (60*1000)</code><br/>
         *         <em>(in minutes)</em>
         *       </td>
         *       <td>{@link XMLGregorianCalendar#setTimezone(int offset)}<sup><em>*</em></sup>
         *       </td>
         *     </tr>
         *   </tbody>
         * </table>
         * <p><em>*</em>conversion loss of information. It is not possible to represent
         * a <code>java.util.GregorianCalendar</code> daylight savings timezone id in the
         * XML Schema 1.0 date/time datatype representation.</p>
         *
         * <p>To compute the return value's <code>TimeZone</code> field,
         * <ul>
         * <li>when <code>this.getTimezone() != FIELD_UNDEFINED</code>,
         * create a <code>java.util.TimeZone</code> with a custom timezone id
         * using the <code>this.getTimezone()</code>.</li>
         * <li>else use the <code>GregorianCalendar</code> default timezone value
         * for the host is defined as specified by
         * <code>java.util.TimeZone.getDefault()</code>.</li></p>
         *
         * <p>
         *  <p>从{@link GregorianCalendar}创建<code> XMLGregorianCalendar </code>。</p>
         * 
         * <table border="2" rules="all" cellpadding="2">
         * <thead>
         * <tr>
         * <th align="center" colspan="2">
         *  字段从{@link GregorianCalendar}转换为{@link XMLGregorianCalendar}
         * </th>
         * </tr>
         * <tr>
         *  <th> <code> java.util.GregorianCalendar </code>字段</th> <th> <code> javax.xml.datatype.XMLGregorianCa
         * lendar </code>。
         * </tr>
         * </thead>
         * <tbody>
         * <tr>
         *  <td> <code> ERA == GregorianCalendar.BC? -YEAR：YEAR </code> </td> <td> {@ link XMLGregorianCalendar#setYear(int year)}
         *  </td>。
         * </tr>
         * <tr>
         *  <td> <code> MONTH + 1 </code> </td> <td> {@ link XMLGregorianCalendar#setMonth(int month)} </td>
         * </tr>
         * <tr>
         *  <td> <code> DAY_OF_MONTH </code> </td> <td> {@ link XMLGregorianCalendar#setDay(int day)} </td>
         * </tr>
         * <tr>
         * 
         * @param cal <code>java.util.GregorianCalendar</code> used to create <code>XMLGregorianCalendar</code>
         *
         * @return <code>XMLGregorianCalendar</code> created from <code>java.util.GregorianCalendar</code>
         *
         * @throws NullPointerException If <code>cal</code> is <code>null</code>.
         */
        public XMLGregorianCalendar newXMLGregorianCalendar(final GregorianCalendar cal) {

                return new XMLGregorianCalendarImpl(cal);
        }

        /**
         * <p>Constructor allowing for complete value spaces allowed by
         * W3C XML Schema 1.0 recommendation for xsd:dateTime and related
         * builtin datatypes. Note that <code>year</code> parameter supports
         * arbitrarily large numbers and fractionalSecond has infinite
         * precision.</p>
         *
         * <p>
         *  <td> <code> HOUR_OF_DAY,MINUTE,SECOND,MILLISECOND </code> </td> <td> {@ link XMLGregorianCalendar#setTime(int hour,int minute,int second,BigDecimal fractional)}
         *  </td>。
         * </tr>
         * <tr>
         * <td>
         *  <code>(ZONE_OFFSET + DST_OFFSET)/(60 * 1000)</code> <br/> <em>(以分钟为单位)</em>
         * </td>
         *  <td> {@ link XMLGregorianCalendar#setTimezone(int offset)} <sup> <em> * </em> </sup>
         * </td>
         * </tr>
         * </tbody>
         * </table>
         * <p> <em> * </em>信息的转化丢失。
         * 
         * @param year of <code>XMLGregorianCalendar</code> to be created.
         * @param month of <code>XMLGregorianCalendar</code> to be created.
         * @param day of <code>XMLGregorianCalendar</code> to be created.
         * @param hour of <code>XMLGregorianCalendar</code> to be created.
         * @param minute of <code>XMLGregorianCalendar</code> to be created.
         * @param second of <code>XMLGregorianCalendar</code> to be created.
         * @param fractionalSecond of <code>XMLGregorianCalendar</code> to be created.
         * @param timezone of <code>XMLGregorianCalendar</code> to be created.
         *
         * @return <code>XMLGregorianCalendar</code> created from specified values.
         *
         * @throws IllegalArgumentException If any individual parameter's value is outside the maximum value constraint for the field
         *   as determined by the Date/Time Data Mapping table in {@link XMLGregorianCalendar}
         *   or if the composite values constitute an invalid <code>XMLGregorianCalendar</code> instance
         *   as determined by {@link XMLGregorianCalendar#isValid()}.
         * @throws NullPointerException If any parameters are <code>null</code>.
         *
         */
        public XMLGregorianCalendar newXMLGregorianCalendar(
                final BigInteger year,
                final int month,
                final int day,
                final int hour,
                final int minute,
                final int second,
                final BigDecimal fractionalSecond,
                final int timezone) {

                return new XMLGregorianCalendarImpl(
                        year,
                        month,
                        day,
                        hour,
                        minute,
                        second,
                        fractionalSecond,
                        timezone
                );
        }
}
