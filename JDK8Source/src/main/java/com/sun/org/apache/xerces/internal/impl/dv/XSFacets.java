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

import java.util.Vector;

import com.sun.org.apache.xerces.internal.xs.XSAnnotation;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;

/**
 * The class used to pass all facets to {@link XSSimpleType#applyFacets}.
 *
 * @xerces.internal
 *
 * <p>
 *  该类用于将所有构面传递到{@link XSSimpleType#applyFacets}。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 */
public class XSFacets {

    /**
     * value of length facet.
     * <p>
     *  长度面的值。
     * 
     */
    public int length;

    /**
     * value of minLength facet.
     * <p>
     *  minLength面的值。
     * 
     */
    public int minLength;

    /**
     * value of maxLength facet.
     * <p>
     *  maxLength facet的值。
     * 
     */
    public int maxLength;

    /**
     * value of whiteSpace facet.
     * <p>
     *  whiteSpace facet的值。
     * 
     */
    public short whiteSpace;

    /**
     * value of totalDigits facet.
     * <p>
     *  totalDigits构面的值。
     * 
     */
    public int totalDigits;

    /**
     * value of fractionDigits facet.
     * <p>
     *  fractionDigits facet的值。
     * 
     */
    public int fractionDigits;

    /**
     * string containing value of pattern facet, for multiple patterns values
     * are ORed together.
     * <p>
     *  包含pattern facet的值的字符串,用于多个模式值的OR运算。
     * 
     */
    public String pattern;

    /**
     * Vector containing values of Enumeration facet, as String's.
     * <p>
     *  包含枚举构面值的矢量,作为字符串。
     * 
     */
    public Vector enumeration;

    /**
     * An array parallel to "Vector enumeration". It contains namespace context
     * of each enumeration value. Elements of this vector are NamespaceContext
     * objects.
     * <p>
     *  与"向量枚举"并行的数组。它包含每个枚举值的命名空间上下文。此向量的元素是NamespaceContext对象。
     * 
     */
    public Vector enumNSDecls;

    /**
     * value of maxInclusive facet.
     * <p>
     *  maxInclusive facet的值。
     * 
     */
    public String maxInclusive;

    /**
     * value of maxExclusive facet.
     * <p>
     *  maxExclusive facet的值。
     * 
     */
    public String maxExclusive;

    /**
     * value of minInclusive facet.
     * <p>
     *  minInclusive面的值。
     * 
     */
    public String minInclusive;

    /**
     * value of minExclusive facet.
     * <p>
     *  minExclusive方面的值。
     */
    public String minExclusive;



    public XSAnnotation lengthAnnotation;
    public XSAnnotation minLengthAnnotation;
    public XSAnnotation maxLengthAnnotation;
    public XSAnnotation whiteSpaceAnnotation;
    public XSAnnotation totalDigitsAnnotation;
    public XSAnnotation fractionDigitsAnnotation;
    public XSObjectListImpl patternAnnotations;
    public XSObjectList enumAnnotations;
    public XSAnnotation maxInclusiveAnnotation;
    public XSAnnotation maxExclusiveAnnotation;
    public XSAnnotation minInclusiveAnnotation;
    public XSAnnotation minExclusiveAnnotation;

    public void reset(){
        lengthAnnotation = null;
        minLengthAnnotation = null;
        maxLengthAnnotation = null;
        whiteSpaceAnnotation = null;
        totalDigitsAnnotation = null;
        fractionDigitsAnnotation = null;
        patternAnnotations = null;
        enumAnnotations = null;
        maxInclusiveAnnotation = null;
        maxExclusiveAnnotation = null;
        minInclusiveAnnotation = null;
        minExclusiveAnnotation = null;
    }
}
