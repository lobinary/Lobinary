/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
/*
 * $Id: SortSettings.java,v 1.2.4.1 2005/09/06 10:19:22 pvedula Exp $
 * <p>
 *  $ Id：SortSettingsjava,v 1241 2005/09/06 10:19:22 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import java.text.Collator;
import java.util.Locale;

import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;

/**
 * Class for carrying settings that are to be used for a particular set
 * of <code>xsl:sort</code> elements.
 * <p>
 * 用于承载用于特定的一组<code> xsl：sort </code>元素的设置的类
 * 
 */
final class SortSettings {
    /**
     * A reference to the translet object for the transformation.
     * <p>
     *  对转换的translet对象的引用
     * 
     */
    private AbstractTranslet _translet;

    /**
     * The sort order (ascending or descending) for each level of
     * <code>xsl:sort</code>
     * <p>
     *  <code> xsl：sort </code>的每个级别的排序顺序(升序或降序)
     * 
     */
    private int[] _sortOrders;

    /**
     * The type of comparison (text or number) for each level of
     * <code>xsl:sort</code>
     * <p>
     *  <code> xsl：sort </code>的每个级别的比较类型(文本或数字)
     * 
     */
    private int[] _types;

    /**
     * The Locale for each level of <code>xsl:sort</code>, based on any lang
     * attribute or the default Locale.
     * <p>
     *  每个<code> xsl：sort </code>级别的区域设置,基于任何lang属性或默认语言环境
     * 
     */
    private Locale[] _locales;

    /**
     * The Collator object in effect for each level of <code>xsl:sort</code>
     * <p>
     *  对于<code> xsl：sort </code>的每个级别生效的Collat​​or对象
     * 
     */
    private Collator[] _collators;

    /**
     * Case ordering for each level of <code>xsl:sort</code>.
     * <p>
     *  每个<code> xsl：sort </code>级别的案例排序
     * 
     */
    private String[] _caseOrders;

    /**
     * Create an instance of <code>SortSettings</code>.
     * <p>
     *  创建<code> SortSettings </code>的实例
     * 
     * 
     * @param translet {@link com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet}
     *                 object for the transformation
     * @param sortOrders an array specifying the sort order for each sort level
     * @param types an array specifying the type of comparison for each sort
     *              level (text or number)
     * @param locales an array specifying the Locale for each sort level
     * @param collators an array specifying the Collation in effect for each
     *                  sort level
     * @param caseOrders an array specifying whether upper-case, lower-case
     *                   or neither is to take precedence for each sort level.
     *                   The value of each element is equal to one of
     *                   <code>"upper-first", "lower-first", or ""</code>.
     */
    SortSettings(AbstractTranslet translet, int[] sortOrders, int[] types,
                 Locale[] locales, Collator[] collators, String[] caseOrders) {
        _translet = translet;
        _sortOrders = sortOrders;
        _types = types;
        _locales = locales;
        _collators = collators;
        _caseOrders = caseOrders;
    }

    /**
    /* <p>
    /* 
     * @return A reference to the translet object for the transformation.
     */
    AbstractTranslet getTranslet() {
        return _translet;
    }

    /**
    /* <p>
    /* 
     * @return An array containing the sort order (ascending or descending)
     *         for each level of <code>xsl:sort</code>
     */
    int[] getSortOrders() {
        return _sortOrders;
    }

    /**
    /* <p>
    /* 
     * @return An array containing the type of comparison (text or number)
     *         to perform for each level of <code>xsl:sort</code>
     */
    int[] getTypes() {
        return _types;
    }

    /**
    /* <p>
    /* 
     * @return An array containing the Locale object in effect for each level
     *         of <code>xsl:sort</code>
     */
    Locale[] getLocales() {
        return _locales;
    }

    /**
    /* <p>
    /* 
     * @return An array containing the Collator object in effect for each level
     *         of <code>xsl:sort</code>
     */
    Collator[] getCollators() {
        return _collators;
    }

    /**
    /* <p>
    /* 
     * @return An array specifying the case ordering for each level of
     *         <code>xsl:sort</code>.
     */
    String[] getCaseOrders() {
        return _caseOrders;
    }
}
