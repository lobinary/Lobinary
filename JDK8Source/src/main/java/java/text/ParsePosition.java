/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;


/**
 * <code>ParsePosition</code> is a simple class used by <code>Format</code>
 * and its subclasses to keep track of the current position during parsing.
 * The <code>parseObject</code> method in the various <code>Format</code>
 * classes requires a <code>ParsePosition</code> object as an argument.
 *
 * <p>
 * By design, as you parse through a string with different formats,
 * you can use the same <code>ParsePosition</code>, since the index parameter
 * records the current position.
 *
 * <p>
 *  <code> ParsePosition </code>是一个由<code> Format </code>及其子类使用的简单类,用于在解析期间跟踪当前位置。
 * 各种<code> Format </code>类中的<code> parseObject </code>方法需要一个<code> ParsePosition </code>对象作为参数。
 * 
 * <p>
 *  通过设计,当您解析不同格式的字符串时,您可以使用相同的<code> ParsePosition </code>,因为索引参数记录当前位置。
 * 
 * 
 * @author      Mark Davis
 * @see         java.text.Format
 */

public class ParsePosition {

    /**
     * Input: the place you start parsing.
     * <br>Output: position where the parse stopped.
     * This is designed to be used serially,
     * with each call setting index up for the next one.
     * <p>
     *  输入：你开始解析的地方。 <br>输出：解析停止的位置。这是设计为连续使用,每个呼叫设置索引为下一个。
     * 
     */
    int index = 0;
    int errorIndex = -1;

    /**
     * Retrieve the current parse position.  On input to a parse method, this
     * is the index of the character at which parsing will begin; on output, it
     * is the index of the character following the last character parsed.
     *
     * <p>
     * 检索当前的解析位置。在对解析方法的输入时,这是解析将开始的字符的索引;在输出上,它是最后一个字符解析后的字符的索引。
     * 
     * 
     * @return the current parse position
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the current parse position.
     *
     * <p>
     *  设置当前解析位置。
     * 
     * 
     * @param index the current parse position
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Create a new ParsePosition with the given initial index.
     *
     * <p>
     *  使用给定的初始索引创建一个新的ParsePosition。
     * 
     * 
     * @param index initial index
     */
    public ParsePosition(int index) {
        this.index = index;
    }
    /**
     * Set the index at which a parse error occurred.  Formatters
     * should set this before returning an error code from their
     * parseObject method.  The default value is -1 if this is not set.
     *
     * <p>
     *  设置发生解析错误的索引。格式化程序应该在从它们的parseObject方法返回错误代码之前进行设置。如果未设置,默认值为-1。
     * 
     * 
     * @param ei the index at which an error occurred
     * @since 1.2
     */
    public void setErrorIndex(int ei)
    {
        errorIndex = ei;
    }

    /**
     * Retrieve the index at which an error occurred, or -1 if the
     * error index has not been set.
     *
     * <p>
     *  检索发生错误的索引,如果尚未设置错误索引,则返回-1。
     * 
     * 
     * @return the index at which an error occurred
     * @since 1.2
     */
    public int getErrorIndex()
    {
        return errorIndex;
    }

    /**
     * Overrides equals
     * <p>
     *  覆盖equals
     * 
     */
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof ParsePosition))
            return false;
        ParsePosition other = (ParsePosition) obj;
        return (index == other.index && errorIndex == other.errorIndex);
    }

    /**
     * Returns a hash code for this ParsePosition.
     * <p>
     *  返回此ParsePosition的哈希码。
     * 
     * 
     * @return a hash code value for this object
     */
    public int hashCode() {
        return (errorIndex << 16) | index;
    }

    /**
     * Return a string representation of this ParsePosition.
     * <p>
     *  返回此ParsePosition的字符串表示形式。
     * 
     * @return  a string representation of this object
     */
    public String toString() {
        return getClass().getName() +
            "[index=" + index +
            ",errorIndex=" + errorIndex + ']';
    }
}
