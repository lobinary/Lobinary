/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;


import java.util.*;
import java.awt.*;
import javax.swing.text.*;


/**
 * <P>The AccessibleExtendedText interface contains additional methods
 * not provided by the AccessibleText interface
 *
 * Applications can determine if an object supports the AccessibleExtendedText
 * interface by first obtaining its AccessibleContext (see {@link Accessible})
 * and then calling the {@link AccessibleContext#getAccessibleText} method of
 * AccessibleContext.  If the return value is an instance of
 * AccessibleExtendedText, the object supports this interface.
 *
 * <p>
 *  <P> AccessibleExtendedText接口包含AccessibleText接口不提供的其他方法
 * 
 *  应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用AccessibleContext的{@link AccessibleContext#getAccessibleText}
 * 方法来确定对象是否支持AccessibleExtendedText接口。
 * 如果返回值是AccessibleExtendedText的实例,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleText
 *
 * @author       Peter Korn
 * @author       Lynn Monsanto
 * @since 1.5
 */
public interface AccessibleExtendedText {

    /**
     * Constant used to indicate that the part of the text that should be
     * retrieved is a line of text.
     *
     * <p>
     *  常量用于指示应检索的文本部分是一行文本。
     * 
     * 
     * @see AccessibleText#getAtIndex
     * @see AccessibleText#getAfterIndex
     * @see AccessibleText#getBeforeIndex
     */
    public static final int LINE = 4; // BugID: 4849720

    /**
     * Constant used to indicate that the part of the text that should be
     * retrieved is contiguous text with the same text attributes.
     *
     * <p>
     *  常量用于指示应检索的文本部分是具有相同文本属性的连续文本。
     * 
     * 
     * @see AccessibleText#getAtIndex
     * @see AccessibleText#getAfterIndex
     * @see AccessibleText#getBeforeIndex
     */
    public static final int ATTRIBUTE_RUN = 5; // BugID: 4849720

    /**
     * Returns the text between two indices
     *
     * <p>
     *  返回两个索引之间的文本
     * 
     * 
     * @param startIndex the start index in the text
     * @param endIndex the end index in the text
     * @return the text string if the indices are valid.
     * Otherwise, null is returned.
     */
    public String getTextRange(int startIndex, int endIndex);

    /**
     * Returns the <code>AccessibleTextSequence</code> at a given index.
     *
     * <p>
     *  返回给定索引处的<code> AccessibleTextSequence </code>。
     * 
     * 
     * @param part the <code>CHARACTER</code>, <code>WORD</code>,
     * <code>SENTENCE</code>, <code>LINE</code> or <code>ATTRIBUTE_RUN</code>
     * to retrieve
     * @param index an index within the text
     * @return an <code>AccessibleTextSequence</code> specifying the text
     * if part and index are valid.  Otherwise, null is returned.
     *
     * @see AccessibleText#CHARACTER
     * @see AccessibleText#WORD
     * @see AccessibleText#SENTENCE
     */
    public AccessibleTextSequence getTextSequenceAt(int part, int index);

    /**
     * Returns the <code>AccessibleTextSequence</code> after a given index.
     *
     * <p>
     *  返回给定索引后的<code> AccessibleTextSequence </code>。
     * 
     * 
     * @param part the <code>CHARACTER</code>, <code>WORD</code>,
     * <code>SENTENCE</code>, <code>LINE</code> or <code>ATTRIBUTE_RUN</code>
     * to retrieve
     * @param index an index within the text
     * @return an <code>AccessibleTextSequence</code> specifying the text
     * if part and index are valid.  Otherwise, null is returned.
     *
     * @see AccessibleText#CHARACTER
     * @see AccessibleText#WORD
     * @see AccessibleText#SENTENCE
     */
    public AccessibleTextSequence getTextSequenceAfter(int part, int index);

    /**
     * Returns the <code>AccessibleTextSequence</code> before a given index.
     *
     * <p>
     *  返回给定索引之前的<code> AccessibleTextSequence </code>。
     * 
     * 
     * @param part the <code>CHARACTER</code>, <code>WORD</code>,
     * <code>SENTENCE</code>, <code>LINE</code> or <code>ATTRIBUTE_RUN</code>
     * to retrieve
     * @param index an index within the text
     * @return an <code>AccessibleTextSequence</code> specifying the text
     * if part and index are valid.  Otherwise, null is returned.
     *
     * @see AccessibleText#CHARACTER
     * @see AccessibleText#WORD
     * @see AccessibleText#SENTENCE
     */
    public AccessibleTextSequence getTextSequenceBefore(int part, int index);

    /**
     * Returns the bounding rectangle of the text between two indices.
     *
     * <p>
     *  返回两个索引之间的文本的边界矩形。
     * 
     * @param startIndex the start index in the text
     * @param endIndex the end index in the text
     * @return the bounding rectangle of the text if the indices are valid.
     * Otherwise, null is returned.
     */
    public Rectangle getTextBounds(int startIndex, int endIndex);
}
