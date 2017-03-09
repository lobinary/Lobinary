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
package javax.swing.text;

/**
 * Represents a location within a document.  It is intended to abstract away
 * implementation details of the document and enable specification of
 * positions within the document that are capable of tracking of change as
 * the document is edited.
 * <p>
 * A {@code Position} object points at a location between two characters.
 * As the surrounding content is altered, the {@code Position} object
 * adjusts its offset automatically to reflect the changes. If content is
 * inserted or removed before the {@code Position} object's location, then the
 * {@code Position} increments or decrements its offset, respectively,
 * so as to point to the same location. If a portion of the document is removed
 * that contains a {@code Position}'s offset, then the {@code Position}'s
 * offset becomes that of the beginning of the removed region. For example, if
 * a {@code Position} has an offset of 5 and the region 2-10 is removed, then
 * the {@code Position}'s offset becomes 2.
 * <p>
 * {@code Position} with an offset of 0 is a special case. It never changes its
 * offset while document content is altered.
 *
 * <p>
 *  表示文档中的位置。它旨在抽象出文档的实现细节,并且能够指定文档中能够在文档被编辑时跟踪改变的位置。
 * <p>
 *  {@code Position}对象指向两个字符之间的位置。随着周围内容改变,{@code Position}对象会自动调整其偏移以反映更改。
 * 如果在{@code Position}对象的位置之前插入或删除内容,则{@code Position}分别增加或减少其偏移量,以指向同一位置。
 * 如果删除了包含{@code Position}偏移量的文档部分,则{@code Position}的偏移量将成为删除区域的开始位置。
 * 例如,如果{@code Position}的偏移量为5,而区域2-10被删除,则{@code Position}的偏移量为2。
 * <p>
 *  {@code Position}的偏移量为0是一种特殊情况。它在文档内容改变时从不改变其偏移。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface Position {

    /**
     * Fetches the current offset within the document.
     *
     * <p>
     *  获取文档中的当前偏移量。
     * 
     * 
     * @return the offset &gt;= 0
     */
    public int getOffset();

    /**
     * A typesafe enumeration to indicate bias to a position
     * in the model.  A position indicates a location between
     * two characters.  The bias can be used to indicate an
     * interest toward one of the two sides of the position
     * in boundary conditions where a simple offset is
     * ambiguous.
     * <p>
     * 表示模型中位置偏差的类型安全枚举。位置表示两个字符之间的位置。偏置可以用于指示在简单偏移不明确的边界条件下对位置的两侧中的一侧的兴趣。
     * 
     */
    public static final class Bias {

        /**
         * Indicates to bias toward the next character
         * in the model.
         * <p>
         *  表示偏向模型中的下一个字符。
         * 
         */
        public static final Bias Forward = new Bias("Forward");

        /**
         * Indicates a bias toward the previous character
         * in the model.
         * <p>
         *  表示模型中前一个字符的偏差。
         * 
         */
        public static final Bias Backward = new Bias("Backward");

        /**
         * string representation
         * <p>
         *  字符串表示
         */
        public String toString() {
            return name;
        }

        private Bias(String name) {
            this.name = name;
        }

        private String name;
    }
}
