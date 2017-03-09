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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: NodeSortRecord.java,v 1.5 2005/09/28 13:48:36 pvedula Exp $
 * <p>
 *  $ Id：NodeSortRecord.java,v 1.5 2005/09/28 13:48:36 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Locale;

import com.sun.org.apache.xalan.internal.xsltc.CollatorFactory;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.utils.StringComparable;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;

/**
 * Base class for sort records containing application specific sort keys
 * <p>
 *  包含应用程序特定排序键的排序记录的基类
 * 
 */
public abstract class NodeSortRecord {
    public static final int COMPARE_STRING     = 0;
    public static final int COMPARE_NUMERIC    = 1;

    public static final int COMPARE_ASCENDING  = 0;
    public static final int COMPARE_DESCENDING = 1;

    /**
     * A reference to a collator. May be updated by subclass if the stylesheet
     * specifies a different language (will be updated iff _locale is updated).
     * <p>
     *  对整理程序的引用。如果样式表指定了不同的语言(如果_locale被更新,将被更新),可以通过子类更新。
     * 
     * 
     * @deprecated This field continues to exist for binary compatibility.
     *             New code should not refer to it.
     */
    private static final Collator DEFAULT_COLLATOR = Collator.getInstance();

    /**
     * A reference to the first Collator
     * <p>
     *  对第一个Collat​​or的引用
     * 
     * 
     * @deprecated This field continues to exist for binary compatibility.
     *             New code should not refer to it.
     */
    protected Collator _collator = DEFAULT_COLLATOR;
    protected Collator[] _collators;

    /**
     * A locale field that might be set by an instance of a subclass.
     * <p>
     *  可以由子类的实例设置的语言环境字段。
     * 
     * 
     * @deprecated This field continues to exist for binary compatibility.
     *             New code should not refer to it.
     */
    protected Locale _locale;

    protected CollatorFactory _collatorFactory;

    protected SortSettings _settings;

    private DOM    _dom = null;
    private int    _node;           // The position in the current iterator
    private int    _last = 0;       // Number of nodes in the current iterator
    private int    _scanned = 0;    // Number of key levels extracted from DOM

    private Object[] _values; // Contains Comparable  objects

    /**
     * This constructor is run by a call to ClassLoader in the
     * makeNodeSortRecord method in the NodeSortRecordFactory class. Since we
     * cannot pass any parameters to the constructor in that case we just set
     * the default values here and wait for new values through initialize().
     * <p>
     *  此构造函数通过调用NodeSortRecordFactory类中的makeNodeSortRecord方法中的ClassLoader运行。
     * 因为在这种情况下我们不能传递任何参数到构造函数,我们只是在这里设置默认值,并通过initialize()等待新的值。
     * 
     */
    public NodeSortRecord(int node) {
        _node = node;
    }

    public NodeSortRecord() {
        this(0);
    }

    /**
     * This method allows the caller to set the values that could not be passed
     * to the default constructor.
     * <p>
     *  此方法允许调用者设置不能传递到默认构造函数的值。
     * 
     */
    public final void initialize(int node, int last, DOM dom,
         SortSettings settings)
        throws TransletException
    {
        _dom = dom;
        _node = node;
        _last = last;
        _settings = settings;

        int levels = settings.getSortOrders().length;
        _values = new Object[levels];

        String colFactClassname = null;
        try {
            // -- W. Eliot Kimber (eliot@isogen.com)
            colFactClassname =
                SecuritySupport.getSystemProperty("com.sun.org.apache.xalan.internal.xsltc.COLLATOR_FACTORY");
        }
        catch (SecurityException e) {
            // If we can't read the propery, just use default collator
        }

        if (colFactClassname != null) {
            try {
                Object candObj = ObjectFactory.findProviderClass(colFactClassname, true);
                _collatorFactory = (CollatorFactory)candObj;
            } catch (ClassNotFoundException e) {
                throw new TransletException(e);
            }
            Locale[] locales = settings.getLocales();
            _collators = new Collator[levels];
            for (int i = 0; i < levels; i++){
                _collators[i] = _collatorFactory.getCollator(locales[i]);
            }
            _collator = _collators[0];
        } else {
            _collators = settings.getCollators();
            _collator = _collators[0];
        }
    }

    /**
     * Returns the node for this sort object
     * <p>
     * 返回此排序对象的节点
     * 
     */
    public final int getNode() {
        return _node;
    }

    /**
     *
     * <p>
     */
    public final int compareDocOrder(NodeSortRecord other) {
        return _node - other._node;
    }

    /**
     * Get the string or numeric value of a specific level key for this sort
     * element. The value is extracted from the DOM if it is not already in
     * our sort key vector.
     * <p>
     *  获取此排序元素的特定级别键的字符串或数值。如果值尚未在排序键向量中,则从DOM中提取值。
     * 
     */
    private final Comparable stringValue(int level) {
        // Get value from our array if possible
        if (_scanned <= level) {
            AbstractTranslet translet = _settings.getTranslet();
            Locale[] locales = _settings.getLocales();
            String[] caseOrder = _settings.getCaseOrders();

            // Get value from DOM if accessed for the first time
            final String str = extractValueFromDOM(_dom, _node, level,
                                                   translet, _last);
            final Comparable key =
                StringComparable.getComparator(str, locales[level],
                                               _collators[level],
                                               caseOrder[level]);
            _values[_scanned++] = key;
            return(key);
        }
        return((Comparable)_values[level]);
  }

    private final Double numericValue(int level) {
        // Get value from our vector if possible
        if (_scanned <= level) {
            AbstractTranslet translet = _settings.getTranslet();

            // Get value from DOM if accessed for the first time
            final String str = extractValueFromDOM(_dom, _node, level,
                                                   translet, _last);
            Double num;
            try {
                num = new Double(str);
            }
            // Treat number as NaN if it cannot be parsed as a double
            catch (NumberFormatException e) {
                num = new Double(Double.NEGATIVE_INFINITY);
            }
            _values[_scanned++] = num;
            return(num);
        }
        return((Double)_values[level]);
    }

    /**
     * Compare this sort element to another. The first level is checked first,
     * and we proceed to the next level only if the first level keys are
     * identical (and so the key values may not even be extracted from the DOM)
     *
     * !!!!MUST OPTIMISE - THIS IS REALLY, REALLY SLOW!!!!
     * <p>
     *  比较这个排序元素到另一个。首先检查第一级别,并且仅当第一级别键相同(因此甚至可能甚至不能从DOM提取键值)时我们才进行下一级别,
     * 
     *  !!!!必须优化 - 这是真的,真的慢！
     * 
     */
    public int compareTo(NodeSortRecord other) {
        int cmp, level;
        int[] sortOrder = _settings.getSortOrders();
        int levels = _settings.getSortOrders().length;
        int[] compareTypes = _settings.getTypes();

        for (level = 0; level < levels; level++) {
            // Compare the two nodes either as numeric or text values
            if (compareTypes[level] == COMPARE_NUMERIC) {
                final Double our = numericValue(level);
                final Double their = other.numericValue(level);
                cmp = our.compareTo(their);
            }
            else {
                final Comparable our = stringValue(level);
                final Comparable their = other.stringValue(level);
                cmp = our.compareTo(their);
            }

            // Return inverse compare value if inverse sort order
            if (cmp != 0) {
                return sortOrder[level] == COMPARE_DESCENDING ? 0 - cmp : cmp;
            }
        }
        // Compare based on document order if all sort keys are equal
        return(_node - other._node);
    }

    /**
     * Returns the array of Collators used for text comparisons in this object.
     * May be overridden by inheriting classes
     * <p>
     *  返回此对象中用于文本比较的Collat​​or数组。可以通过继承类覆盖
     * 
     */
    public Collator[] getCollator() {
        return _collators;
    }

    /**
     * Extract the sort value for a level of this key.
     * <p>
     *  提取此键级别的排序值。
     */
    public abstract String extractValueFromDOM(DOM dom, int current, int level,
                                               AbstractTranslet translet,
                                               int last);

}
