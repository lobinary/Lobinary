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

package javax.accessibility;


import java.util.*;
import java.awt.*;
import javax.swing.text.*;


/**
 * <P>The AccessibleText interface should be implemented by all
 * classes that present textual information on the display.  This interface
 * provides the standard mechanism for an assistive technology to access
 * that text via its content, attributes, and spatial location.
 * Applications can determine if an object supports the AccessibleText
 * interface by first obtaining its AccessibleContext (see {@link Accessible})
 * and then calling the {@link AccessibleContext#getAccessibleText} method of
 * AccessibleContext.  If the return value is not null, the object supports this
 * interface.
 *
 * <p>
 *  <P> AccessibleText接口应该由在显示器上呈现文本信息的所有类实现。此接口为辅助技术通过其内容,属性和空间位置访问文本提供了标准机制。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用AccessibleContext的{@link AccessibleContext#getAccessibleText}
 * 方法来确定对象是否支持AccessibleText接口。
 *  <P> AccessibleText接口应该由在显示器上呈现文本信息的所有类实现。此接口为辅助技术通过其内容,属性和空间位置访问文本提供了标准机制。如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleText
 *
 * @author      Peter Korn
 */
public interface AccessibleText {

    /**
     * Constant used to indicate that the part of the text that should be
     * retrieved is a character.
     *
     * <p>
     *  常量用于指示应检索的文本部分是字符。
     * 
     * 
     * @see #getAtIndex
     * @see #getAfterIndex
     * @see #getBeforeIndex
     */
    public static final int CHARACTER = 1;

    /**
     * Constant used to indicate that the part of the text that should be
     * retrieved is a word.
     *
     * <p>
     *  常数用于指示应检索的文本部分是单词。
     * 
     * 
     * @see #getAtIndex
     * @see #getAfterIndex
     * @see #getBeforeIndex
     */
    public static final int WORD = 2;

    /**
     * Constant used to indicate that the part of the text that should be
     * retrieved is a sentence.
     *
     * A sentence is a string of words which expresses an assertion,
     * a question, a command, a wish, an exclamation, or the performance
     * of an action. In English locales, the string usually begins with
     * a capital letter and concludes with appropriate end punctuation;
     * such as a period, question or exclamation mark. Other locales may
     * use different capitalization and/or punctuation.
     *
     * <p>
     *  常量用于指示应该检索的文本部分是一个句子。
     * 
     *  句子是表达断言,问题,命令,愿望,感叹号或动作执行的一串字。在英语语言环境中,字符串通常以大写字母开头,并以适当的结束标点结束;如期间,问题或感叹号。其他区域设置可能使用不同的大小写和/或标点符号。
     * 
     * 
     * @see #getAtIndex
     * @see #getAfterIndex
     * @see #getBeforeIndex
     */
    public static final int SENTENCE = 3;

    /**
     * Given a point in local coordinates, return the zero-based index
     * of the character under that Point.  If the point is invalid,
     * this method returns -1.
     *
     * <p>
     * 给定一个点在局部坐标,返回该点下的字符的从零开始的索引。如果该点无效,则此方法返回-1。
     * 
     * 
     * @param p the Point in local coordinates
     * @return the zero-based index of the character under Point p; if
     * Point is invalid return -1.
     */
    public int getIndexAtPoint(Point p);

    /**
     * Determines the bounding box of the character at the given
     * index into the string.  The bounds are returned in local
     * coordinates.  If the index is invalid an empty rectangle is returned.
     *
     * <p>
     *  确定给定索引处字符的边界框到字符串中。边界在本地坐标中返回。如果索引无效,则返回一个空的矩形。
     * 
     * 
     * @param i the index into the String
     * @return the screen coordinates of the character's bounding box,
     * if index is invalid return an empty rectangle.
     */
    public Rectangle getCharacterBounds(int i);

    /**
     * Returns the number of characters (valid indicies)
     *
     * <p>
     *  返回字符数(有效的标记)
     * 
     * 
     * @return the number of characters
     */
    public int getCharCount();

    /**
     * Returns the zero-based offset of the caret.
     *
     * Note: That to the right of the caret will have the same index
     * value as the offset (the caret is between two characters).
     * <p>
     *  返回插入符号的从零开始的偏移量。
     * 
     *  注意：插入符右侧的索引值与偏移量相同(插入符号在两个字符之间)。
     * 
     * 
     * @return the zero-based offset of the caret.
     */
    public int getCaretPosition();

    /**
     * Returns the String at a given index.
     *
     * <p>
     *  返回给定索引处的String。
     * 
     * 
     * @param part the CHARACTER, WORD, or SENTENCE to retrieve
     * @param index an index within the text
     * @return the letter, word, or sentence
     */
    public String getAtIndex(int part, int index);

    /**
     * Returns the String after a given index.
     *
     * <p>
     *  返回给定索引后的String。
     * 
     * 
     * @param part the CHARACTER, WORD, or SENTENCE to retrieve
     * @param index an index within the text
     * @return the letter, word, or sentence
     */
    public String getAfterIndex(int part, int index);

    /**
     * Returns the String before a given index.
     *
     * <p>
     *  返回给定索引之前的String。
     * 
     * 
     * @param part the CHARACTER, WORD, or SENTENCE to retrieve
     * @param index an index within the text
     * @return the letter, word, or sentence
     */
    public String getBeforeIndex(int part, int index);

    /**
     * Returns the AttributeSet for a given character at a given index
     *
     * <p>
     *  返回给定字符在给定索引的AttributeSet
     * 
     * 
     * @param i the zero-based index into the text
     * @return the AttributeSet of the character
     */
    public AttributeSet getCharacterAttribute(int i);

    /**
     * Returns the start offset within the selected text.
     * If there is no selection, but there is
     * a caret, the start and end offsets will be the same.
     *
     * <p>
     *  返回所选文本内的起始偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。
     * 
     * 
     * @return the index into the text of the start of the selection
     */
    public int getSelectionStart();

    /**
     * Returns the end offset within the selected text.
     * If there is no selection, but there is
     * a caret, the start and end offsets will be the same.
     *
     * <p>
     *  返回所选文本内的结束偏移量。如果没有选择,但有一个插入符号,开始和结束偏移将是相同的。
     * 
     * 
     * @return the index into the text of the end of the selection
     */
    public int getSelectionEnd();

    /**
     * Returns the portion of the text that is selected.
     *
     * <p>
     *  返回所选文本的部分。
     * 
     * @return the String portion of the text that is selected
     */
    public String getSelectedText();
}
