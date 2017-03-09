/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.text;

/**
* An Annotation object is used as a wrapper for a text attribute value if
* the attribute has annotation characteristics. These characteristics are:
* <ul>
* <li>The text range that the attribute is applied to is critical to the
* semantics of the range. That means, the attribute cannot be applied to subranges
* of the text range that it applies to, and, if two adjacent text ranges have
* the same value for this attribute, the attribute still cannot be applied to
* the combined range as a whole with this value.
* <li>The attribute or its value usually do no longer apply if the underlying text is
* changed.
* </ul>
*
* An example is grammatical information attached to a sentence:
* For the previous sentence, you can say that "an example"
* is the subject, but you cannot say the same about "an", "example", or "exam".
* When the text is changed, the grammatical information typically becomes invalid.
* Another example is Japanese reading information (yomi).
*
* <p>
* Wrapping the attribute value into an Annotation object guarantees that
* adjacent text runs don't get merged even if the attribute values are equal,
* and indicates to text containers that the attribute should be discarded if
* the underlying text is modified.
*
* <p>
*  如果属性具有注释特性,则Annotation对象用作文本属性值的包装器。这些特点是：
* <ul>
*  <li>应用属性的文本范围对于范围的语义至关重要。
* 这意味着,该属性不能应用于其应用于的文本范围的子范围,并且如果两个相邻的文本范围对于该属性具有相同的值,则该属性仍然不能作为整体与该值一起应用于组合的范围。
*  <li>如果底层文字已更改,则属性或其值通常不再适用。
* </ul>
* 
*  一个例子是附加到句子的语法信息：对于上一句,你可以说"一个例子"是主题,但你不能对"一个","示例"或"考试"说同样的。当文本改变时,语法信息通常变得无效。另一个例子是日语阅读信息(yomi)。
* 
* <p>
*  将属性值包装到注释对象中保证即使属性值相等,相邻文本运行也不会合并,并且如果基础文本被修改,则向文本容器指示该属性应该被丢弃。
* 
* @see AttributedCharacterIterator
* @since 1.2
*/

public class Annotation {

    /**
     * Constructs an annotation record with the given value, which
     * may be null.
     *
     * <p>
     * 
     * 
     * @param value the value of the attribute
     */
    public Annotation(Object value) {
        this.value = value;
    }

    /**
     * Returns the value of the attribute, which may be null.
     *
     * <p>
     *  构造具有给定值的注释记录,可以为null。
     * 
     * 
     * @return the value of the attribute
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns the String representation of this Annotation.
     *
     * <p>
     *  返回属性的值,可以为null。
     * 
     * 
     * @return the {@code String} representation of this {@code Annotation}
     */
    public String toString() {
        return getClass().getName() + "[value=" + value + "]";
    }

    private Object value;

};
