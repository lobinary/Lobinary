/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
 *  版权所有2004,2005 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
package com.sun.org.apache.xerces.internal.xs.datatypes;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>Interface to expose the values for all date-time related types. The following
 * table shows the methods defined for various XML Schema 1.0 built-in types. 'X'
 * marks whether a particular method is defined for a particular type. Accessing undefined
 * methods may return unexpected values.
 *
 * <table border="1">
 * <br/>
 * <tr>
 * <td> XML Schema Datatype </td>
 * <td> getYears() </td>
 * <td> getMonths() </td>
 * <td> getDays() </td>
 * <td> getHours() </td>
 * <td> getMinutes() </td>
 * <td> getSeconds() </td>
 * <td> getTimeZoneHours() </td>
 * <td> getTimeZoneMinutes() </td>
 * <td> getXMLGregorianCalendar() </td>
 * <td> getDuration() </td>
 * <td> hasTimeZone() </td>
 * <td> normalize() </td>
 * <td> isNormalized() </td>
 * <td> getLexicalValue() </td>
 * </tr>
 * <tr>
 * <td> gYear </td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> gMonth </td>
 * <td>-</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> gDay </td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> gYearMonth </td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> gMonthDay </td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> date </td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> time </td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> datetime </td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>-</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * <td>X</td>
 * </tr>
 * <tr>
 * <td> duration </td>
 * <td>-</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * <td>-</td>
 * <td>-</td>
 * <td>-</td>
 * <td>X</td>
 * </tr>
 * </table>
 * </p>
 *
 * <p>
 * <p>公开所有日期时间相关类型的值的接口下表显示为各种XML模式10内置类型"X"标记定义的方法,即是否为特定类型定义了特定方法访问未定义的方法可能返回意外值
 * 
 * <table border="1">
 * <br/>
 * <tr>
 *  <td> XML模式数据类型</td> <td> getYears()</td> <td> getMonths()</td> <td> getDays()</td> <td> getHours()</td>
 *  <td> getMinutes()</td> <td> getSeconds()</td> <td> getTimeZoneHours()</td> <td> getTimeZoneMinutes()
 * </td> <td> getXMLGregorianCalendar <td> getDuration()</td> <td> hasTimeZone()</td> <td> normalize()</td>
 *  <td> isNormalized()</td> <td> getLexicalValue()</td>。
 * </tr>
 * <tr>
 * <TD> gYear的</TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD >  -  </TD>
 *  <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD> <TD> 
 * X </TD>。
 * </tr>
 * <tr>
 *  <TD> gMonth的</TD> <TD>  -  </TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD >  - 
 *  </TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD>
 *  <TD> X </TD>。
 * </tr>
 * <tr>
 *  <TD> g·天</TD> <TD>  -  </TD> <TD>  -  </TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD >  -  </TD>
 *  <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD> <TD> 
 * X </TD>。
 * </tr>
 * <tr>
 *  <TD> gYearMonth的</TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD >  
 * -  </TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD>
 *  <TD> X </TD>。
 * </tr>
 * <tr>
 * <TD> gMonthDay的</TD> <TD>  -  </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD >  - 
 *  </TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD>
 *  <TD> X </TD>。
 * </tr>
 * <tr>
 * 
 * @author Ankit Pasricha, IBM
 *
 */
public interface XSDateTime {

    /**
    /* <p>
    /*  <TD>日期</TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD >  -  </TD> <TD>
    /*  X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD> <TD> X </TD>
    /* 。
    /* </tr>
    /* <tr>
    /*  <TD>时间</TD> <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD> X </TD> <TD> X </TD> <TD > X </TD> <TD>
    /*  X </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD> <TD> X </TD>
    /* 。
    /* </tr>
    /* <tr>
    /*  <TD>日期时间</TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD> X </TD> <TD > X </TD> <TD> X 
    /* </TD> <TD> X </TD> <TD> X </TD> <TD>  -  </TD> <TD> X </TD> <TD系列> X </TD> <TD> X </TD> <TD> X </TD>。
    /* </tr>
    /* <tr>
    /* <TD>持续时间</TD> <TD>  -  </TD> <TD> X </TD> <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD > X </TD> 
    /* <TD>  -  </TD> <TD>  -  </TD> <TD>  -  </TD> <TD> X </TD> <TD>  -  </TD> <TD>  - </TD> <TD>  -  </TD>
    /*  <TD> X </TD>。
    /* </tr>
    /* 
     * @return years - can be negative for date-time related types;
     *
     */
    public int getYears();

    /**
    /* <p>
    /* </table>
    /* </p>
    /* 
    /* 
     * @return months - can be negative only for duration types;
     *                  For duration types, it returns years*12 + months
     */
    public int getMonths();

    /**
    /* <p>
    /* 
     * @return days - cannot be negative;
     *
     */
    public int getDays();

    /**
    /* <p>
    /* 
     * @return hours - cannot be negative;
     *
     */
    public int getHours();

    /**
    /* <p>
    /* 
     * @return minutes - cannot be negative;
     *
     */
    public int getMinutes();

    /**
    /* <p>
    /* 
     * @return seconds - can be negative only for durations;
     *                   For duration types, it returns days*24*3600 + hours*3600
     *                                                  + minutes*60 + seconds
     */
    public double getSeconds();

    /**
    /* <p>
    /* 
     * @return boolean (true when timezone is specified in the original lexical value)
     *
     */
    public boolean hasTimeZone();

    /**
    /* <p>
    /* 
     * @return timezone hours (for GMT-xx:xx this will be negative),
     *
     */
    public int getTimeZoneHours();

    /**
    /* <p>
    /* 
     * @return timezone minutes (for GMT-xx:xx this will be negative),
     *
     */
    public int getTimeZoneMinutes();

    /**
    /* <p>
    /* 
     * @return the original lexical value
     */
    public String getLexicalValue();

    /**
    /* <p>
    /* 
     * @return a new date-time related object with normalized values
     *         (has no effect on objects already
     *          normalized)
     */
    public XSDateTime normalize();

    /**
    /* <p>
    /* 
     * @return whether a date-time related object is normalized or not
     *         (value is not useful for types where timezone is not specified)
     */
    public boolean isNormalized();

    /**
    /* <p>
    /* 
     * @return an un-normalized XMLGregorianCalendar (if applicable otherwise null)
     */
    public XMLGregorianCalendar getXMLGregorianCalendar();

    /**
    /* <p>
    /* 
     * @return a Duration (if applicable otherwise null)
     */
    public Duration getDuration();
}
