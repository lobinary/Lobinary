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
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

/**
 * <code>StringCharacterIterator</code> implements the
 * <code>CharacterIterator</code> protocol for a <code>String</code>.
 * The <code>StringCharacterIterator</code> class iterates over the
 * entire <code>String</code>.
 *
 * <p>
 *  <code> StringCharacterIterator </code>为<code> String </code>实现<code> CharacterIterator </code>协议。
 *  <code> StringCharacterIterator </code>类遍历整个<code> String </code>。
 * 
 * 
 * @see CharacterIterator
 */

public final class StringCharacterIterator implements CharacterIterator
{
    private String text;
    private int begin;
    private int end;
    // invariant: begin <= pos <= end
    private int pos;

    /**
     * Constructs an iterator with an initial index of 0.
     *
     * <p>
     *  构造一个初始索引为0的迭代器。
     * 
     * 
     * @param text the {@code String} to be iterated over
     */
    public StringCharacterIterator(String text)
    {
        this(text, 0);
    }

    /**
     * Constructs an iterator with the specified initial index.
     *
     * <p>
     *  构造具有指定的初始索引的迭代器。
     * 
     * 
     * @param  text   The String to be iterated over
     * @param  pos    Initial iterator position
     */
    public StringCharacterIterator(String text, int pos)
    {
    this(text, 0, text.length(), pos);
    }

    /**
     * Constructs an iterator over the given range of the given string, with the
     * index set at the specified position.
     *
     * <p>
     *  在给定字符串的给定范围内构造一个迭代器,索引设置在指定位置。
     * 
     * 
     * @param  text   The String to be iterated over
     * @param  begin  Index of the first character
     * @param  end    Index of the character following the last character
     * @param  pos    Initial iterator position
     */
    public StringCharacterIterator(String text, int begin, int end, int pos) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;

        if (begin < 0 || begin > end || end > text.length())
            throw new IllegalArgumentException("Invalid substring range");

        if (pos < begin || pos > end)
            throw new IllegalArgumentException("Invalid position");

        this.begin = begin;
        this.end = end;
        this.pos = pos;
    }

    /**
     * Reset this iterator to point to a new string.  This package-visible
     * method is used by other java.text classes that want to avoid allocating
     * new StringCharacterIterator objects every time their setText method
     * is called.
     *
     * <p>
     *  重置此迭代器以指向一个新的字符串。
     * 这个package-visible方法被其他java.text类使用,它们每次调用setText方法时都要避免分配新的StringCharacterIterator对象。
     * 
     * 
     * @param  text   The String to be iterated over
     * @since 1.2
     */
    public void setText(String text) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
        this.begin = 0;
        this.end = text.length();
        this.pos = 0;
    }

    /**
     * Implements CharacterIterator.first() for String.
     * <p>
     *  为String实现CharacterIterator.first()。
     * 
     * 
     * @see CharacterIterator#first
     */
    public char first()
    {
        pos = begin;
        return current();
    }

    /**
     * Implements CharacterIterator.last() for String.
     * <p>
     *  为String实现CharacterIterator.last()。
     * 
     * 
     * @see CharacterIterator#last
     */
    public char last()
    {
        if (end != begin) {
            pos = end - 1;
        } else {
            pos = end;
        }
        return current();
     }

    /**
     * Implements CharacterIterator.setIndex() for String.
     * <p>
     *  为String实现CharacterIterator.setIndex()。
     * 
     * 
     * @see CharacterIterator#setIndex
     */
    public char setIndex(int p)
    {
    if (p < begin || p > end)
            throw new IllegalArgumentException("Invalid index");
        pos = p;
        return current();
    }

    /**
     * Implements CharacterIterator.current() for String.
     * <p>
     * 为String实现CharacterIterator.current()。
     * 
     * 
     * @see CharacterIterator#current
     */
    public char current()
    {
        if (pos >= begin && pos < end) {
            return text.charAt(pos);
        }
        else {
            return DONE;
        }
    }

    /**
     * Implements CharacterIterator.next() for String.
     * <p>
     *  为String实现CharacterIterator.next()。
     * 
     * 
     * @see CharacterIterator#next
     */
    public char next()
    {
        if (pos < end - 1) {
            pos++;
            return text.charAt(pos);
        }
        else {
            pos = end;
            return DONE;
        }
    }

    /**
     * Implements CharacterIterator.previous() for String.
     * <p>
     *  为String实现CharacterIterator.previous()。
     * 
     * 
     * @see CharacterIterator#previous
     */
    public char previous()
    {
        if (pos > begin) {
            pos--;
            return text.charAt(pos);
        }
        else {
            return DONE;
        }
    }

    /**
     * Implements CharacterIterator.getBeginIndex() for String.
     * <p>
     *  为String实现CharacterIterator.getBeginIndex()。
     * 
     * 
     * @see CharacterIterator#getBeginIndex
     */
    public int getBeginIndex()
    {
        return begin;
    }

    /**
     * Implements CharacterIterator.getEndIndex() for String.
     * <p>
     *  为String实现CharacterIterator.getEndIndex()。
     * 
     * 
     * @see CharacterIterator#getEndIndex
     */
    public int getEndIndex()
    {
        return end;
    }

    /**
     * Implements CharacterIterator.getIndex() for String.
     * <p>
     *  为String实现CharacterIterator.getIndex()。
     * 
     * 
     * @see CharacterIterator#getIndex
     */
    public int getIndex()
    {
        return pos;
    }

    /**
     * Compares the equality of two StringCharacterIterator objects.
     * <p>
     *  比较两个StringCharacterIterator对象的相等性。
     * 
     * 
     * @param obj the StringCharacterIterator object to be compared with.
     * @return true if the given obj is the same as this
     * StringCharacterIterator object; false otherwise.
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!(obj instanceof StringCharacterIterator))
            return false;

        StringCharacterIterator that = (StringCharacterIterator) obj;

        if (hashCode() != that.hashCode())
            return false;
        if (!text.equals(that.text))
            return false;
        if (pos != that.pos || begin != that.begin || end != that.end)
            return false;
        return true;
    }

    /**
     * Computes a hashcode for this iterator.
     * <p>
     *  计算此迭代器的哈希码。
     * 
     * 
     * @return A hash code
     */
    public int hashCode()
    {
        return text.hashCode() ^ pos ^ begin ^ end;
    }

    /**
     * Creates a copy of this iterator.
     * <p>
     *  创建此迭代器的副本。
     * 
     * @return A copy of this
     */
    public Object clone()
    {
        try {
            StringCharacterIterator other
            = (StringCharacterIterator) super.clone();
            return other;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

}
