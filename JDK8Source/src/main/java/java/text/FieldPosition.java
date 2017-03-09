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
 * (C) Copyright Taligent, Inc. 1996 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996  - 保留所有权利(C)版权所有IBM Corp. 1996  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

/**
 * <code>FieldPosition</code> is a simple class used by <code>Format</code>
 * and its subclasses to identify fields in formatted output. Fields can
 * be identified in two ways:
 * <ul>
 *  <li>By an integer constant, whose names typically end with
 *      <code>_FIELD</code>. The constants are defined in the various
 *      subclasses of <code>Format</code>.
 *  <li>By a <code>Format.Field</code> constant, see <code>ERA_FIELD</code>
 *      and its friends in <code>DateFormat</code> for an example.
 * </ul>
 * <p>
 * <code>FieldPosition</code> keeps track of the position of the
 * field within the formatted output with two indices: the index
 * of the first character of the field and the index of the last
 * character of the field.
 *
 * <p>
 * One version of the <code>format</code> method in the various
 * <code>Format</code> classes requires a <code>FieldPosition</code>
 * object as an argument. You use this <code>format</code> method
 * to perform partial formatting or to get information about the
 * formatted output (such as the position of a field).
 *
 * <p>
 * If you are interested in the positions of all attributes in the
 * formatted string use the <code>Format</code> method
 * <code>formatToCharacterIterator</code>.
 *
 * <p>
 *  <code> FieldPosition </code>是由<code> Format </code>及其子类使用的简单类,用于标识格式化输出中的字段。字段可以通过两种方式识别：
 * <ul>
 *  <li>由整数常数,其名称通常以<code> _FIELD </code>结尾。这些常量在<code> Format </code>的各个子类中定义。
 *  <li>通过<code> Format.Field </code>常量,请参阅<code> ERA_FIELD </code>及其朋友在<code> DateFormat </code>中的示例。
 * </ul>
 * <p>
 *  <code> FieldPosition </code>使用两个索引来跟踪格式化输出中的字段的位置：字段的第一个字符的索引和字段的最后一个字符的索引。
 * 
 * <p>
 * 各种<code> Format </code>类中的<code>格式</code>方法的一个版本需要一个<code> FieldPosition </code>对象作为参数。
 * 您可以使用此<code>格式</code>方法执行部分格式化或获取有关格式化输出(例如字段位置)的信息。
 * 
 * <p>
 *  如果您对格式化字符串中所有属性的位置感兴趣,请使用<code> Format </code>方法<code> formatToCharacterIterator </code>。
 * 
 * 
 * @author      Mark Davis
 * @see         java.text.Format
 */
public class FieldPosition {

    /**
     * Input: Desired field to determine start and end offsets for.
     * The meaning depends on the subclass of Format.
     * <p>
     *  输入：确定的开始和结束偏移的期望字段。其含义取决于Format的子类。
     * 
     */
    int field = 0;

    /**
     * Output: End offset of field in text.
     * If the field does not occur in the text, 0 is returned.
     * <p>
     *  输出：文本中字段的结束偏移。如果字段未出现在文本中,则返回0。
     * 
     */
    int endIndex = 0;

    /**
     * Output: Start offset of field in text.
     * If the field does not occur in the text, 0 is returned.
     * <p>
     *  输出：文本中字段的起始偏移量。如果字段未出现在文本中,则返回0。
     * 
     */
    int beginIndex = 0;

    /**
     * Desired field this FieldPosition is for.
     * <p>
     *  此FieldPosition的所需字段。
     * 
     */
    private Format.Field attribute;

    /**
     * Creates a FieldPosition object for the given field.  Fields are
     * identified by constants, whose names typically end with _FIELD,
     * in the various subclasses of Format.
     *
     * <p>
     *  为给定字段创建FieldPosition对象。字段由格式的各种子类中的常量标识,常量的名称通常以_FIELD结尾。
     * 
     * 
     * @param field the field identifier
     * @see java.text.NumberFormat#INTEGER_FIELD
     * @see java.text.NumberFormat#FRACTION_FIELD
     * @see java.text.DateFormat#YEAR_FIELD
     * @see java.text.DateFormat#MONTH_FIELD
     */
    public FieldPosition(int field) {
        this.field = field;
    }

    /**
     * Creates a FieldPosition object for the given field constant. Fields are
     * identified by constants defined in the various <code>Format</code>
     * subclasses. This is equivalent to calling
     * <code>new FieldPosition(attribute, -1)</code>.
     *
     * <p>
     *  为给定的字段常量创建FieldPosition对象。字段由在各种<code> Format </code>子类中定义的常量标识。
     * 这相当于调用<code> new FieldPosition(attribute,-1)</code>。
     * 
     * 
     * @param attribute Format.Field constant identifying a field
     * @since 1.4
     */
    public FieldPosition(Format.Field attribute) {
        this(attribute, -1);
    }

    /**
     * Creates a <code>FieldPosition</code> object for the given field.
     * The field is identified by an attribute constant from one of the
     * <code>Field</code> subclasses as well as an integer field ID
     * defined by the <code>Format</code> subclasses. <code>Format</code>
     * subclasses that are aware of <code>Field</code> should give precedence
     * to <code>attribute</code> and ignore <code>fieldID</code> if
     * <code>attribute</code> is not null. However, older <code>Format</code>
     * subclasses may not be aware of <code>Field</code> and rely on
     * <code>fieldID</code>. If the field has no corresponding integer
     * constant, <code>fieldID</code> should be -1.
     *
     * <p>
     * 为给定字段创建<code> FieldPosition </code>对象。
     * 该字段由来自<code> Field </code>子类中的一个的属性常量以及由<code> Format </code>子类定义的整数字段ID来标识。
     * 知道<code>字段</code>的<code>格式</code>子类应优先<code>属性</code>,并忽略<code> fieldID </code>代码>不为空。
     * 然而,旧的<code> Format </code>子类可能不知道<code> Field </code>并依赖<code> fieldID </code>。
     * 如果字段没有对应的整数常量,则<code> fieldID </code>应为-1。
     * 
     * 
     * @param attribute Format.Field constant identifying a field
     * @param fieldID integer constant identifying a field
     * @since 1.4
     */
    public FieldPosition(Format.Field attribute, int fieldID) {
        this.attribute = attribute;
        this.field = fieldID;
    }

    /**
     * Returns the field identifier as an attribute constant
     * from one of the <code>Field</code> subclasses. May return null if
     * the field is specified only by an integer field ID.
     *
     * <p>
     *  从<code> Field </code>子类之一返回字段标识符作为属性常量。如果字段仅由整数字段ID指定,则可以返回null。
     * 
     * 
     * @return Identifier for the field
     * @since 1.4
     */
    public Format.Field getFieldAttribute() {
        return attribute;
    }

    /**
     * Retrieves the field identifier.
     *
     * <p>
     *  检索字段标识符。
     * 
     * 
     * @return the field identifier
     */
    public int getField() {
        return field;
    }

    /**
     * Retrieves the index of the first character in the requested field.
     *
     * <p>
     *  检索请求字段中第一个字符的索引。
     * 
     * 
     * @return the begin index
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Retrieves the index of the character following the last character in the
     * requested field.
     *
     * <p>
     *  检索所请求字段中最后一个字符后面的字符的索引。
     * 
     * 
     * @return the end index
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Sets the begin index.  For use by subclasses of Format.
     *
     * <p>
     *  设置开始索引。供Format的子类使用。
     * 
     * 
     * @param bi the begin index
     * @since 1.2
     */
    public void setBeginIndex(int bi) {
        beginIndex = bi;
    }

    /**
     * Sets the end index.  For use by subclasses of Format.
     *
     * <p>
     *  设置结束索引。供Format的子类使用。
     * 
     * 
     * @param ei the end index
     * @since 1.2
     */
    public void setEndIndex(int ei) {
        endIndex = ei;
    }

    /**
     * Returns a <code>Format.FieldDelegate</code> instance that is associated
     * with the FieldPosition. When the delegate is notified of the same
     * field the FieldPosition is associated with, the begin/end will be
     * adjusted.
     * <p>
     *  返回与FieldPosition关联的<code> Format.FieldDelegate </code>实例。当通知委托人FieldPosition所关联的相同字段时,将调整开始/结束。
     * 
     */
    Format.FieldDelegate getFieldDelegate() {
        return new Delegate();
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
        if (!(obj instanceof FieldPosition))
            return false;
        FieldPosition other = (FieldPosition) obj;
        if (attribute == null) {
            if (other.attribute != null) {
                return false;
            }
        }
        else if (!attribute.equals(other.attribute)) {
            return false;
        }
        return (beginIndex == other.beginIndex
            && endIndex == other.endIndex
            && field == other.field);
    }

    /**
     * Returns a hash code for this FieldPosition.
     * <p>
     *  返回此FieldPosition的散列码。
     * 
     * 
     * @return a hash code value for this object
     */
    public int hashCode() {
        return (field << 24) | (beginIndex << 16) | endIndex;
    }

    /**
     * Return a string representation of this FieldPosition.
     * <p>
     *  返回此FieldPosition的字符串表示形式。
     * 
     * 
     * @return  a string representation of this object
     */
    public String toString() {
        return getClass().getName() +
            "[field=" + field + ",attribute=" + attribute +
            ",beginIndex=" + beginIndex +
            ",endIndex=" + endIndex + ']';
    }


    /**
     * Return true if the receiver wants a <code>Format.Field</code> value and
     * <code>attribute</code> is equal to it.
     * <p>
     * 如果接收者想要一个<code> Format.Field </code>值和<code>属性</code>等于它,则返回true。
     * 
     */
    private boolean matchesField(Format.Field attribute) {
        if (this.attribute != null) {
            return this.attribute.equals(attribute);
        }
        return false;
    }

    /**
     * Return true if the receiver wants a <code>Format.Field</code> value and
     * <code>attribute</code> is equal to it, or true if the receiver
     * represents an inteter constant and <code>field</code> equals it.
     * <p>
     *  如果接收方想要一个<code> Format.Field </code>值和<code>属性</code>等于它,则返回true,如果接收方表示一个inteter常量,等于它。
     * 
     */
    private boolean matchesField(Format.Field attribute, int field) {
        if (this.attribute != null) {
            return this.attribute.equals(attribute);
        }
        return (field == this.field);
    }


    /**
     * An implementation of FieldDelegate that will adjust the begin/end
     * of the FieldPosition if the arguments match the field of
     * the FieldPosition.
     * <p>
     *  FieldDelegate的实现,如果参数匹配FieldPosition的字段,它将调整FieldPosition的begin / end。
     * 
     */
    private class Delegate implements Format.FieldDelegate {
        /**
         * Indicates whether the field has been  encountered before. If this
         * is true, and <code>formatted</code> is invoked, the begin/end
         * are not updated.
         * <p>
         *  指示之前是否遇到该字段。如果这是真的,并且调用<code>格式化</code>,则不更新开始/结束。
         */
        private boolean encounteredField;

        public void formatted(Format.Field attr, Object value, int start,
                              int end, StringBuffer buffer) {
            if (!encounteredField && matchesField(attr)) {
                setBeginIndex(start);
                setEndIndex(end);
                encounteredField = (start != end);
            }
        }

        public void formatted(int fieldID, Format.Field attr, Object value,
                              int start, int end, StringBuffer buffer) {
            if (!encounteredField && matchesField(attr, fieldID)) {
                setBeginIndex(start);
                setEndIndex(end);
                encounteredField = (start != end);
            }
        }
    }
}
