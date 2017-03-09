/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * <P>The AccessibleEditableText interface should be implemented by all
 * classes that present editable textual information on the display.
 * Along with the AccessibleText interface, this interface provides
 * the standard mechanism for an assistive technology to access
 * that text via its content, attributes, and spatial location.
 * Applications can determine if an object supports the AccessibleEditableText
 * interface by first obtaining its AccessibleContext (see {@link Accessible})
 * and then calling the {@link AccessibleContext#getAccessibleEditableText}
 * method of AccessibleContext.  If the return value is not null, the object
 * supports this interface.
 *
 * <p>
 *  <P> AccessibleEditableText接口应该由在显示器上呈现可编辑文本信息的所有类实现。
 * 与AccessibleText接口一起,该接口为辅助技术通过其内容,属性和空间位置来访问该文本提供了标准机制。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用AccessibleContext的{@link AccessibleContext#getAccessibleEditableText}
 * 方法来确定对象是否支持AccessibleEditableText接口。
 * 与AccessibleText接口一起,该接口为辅助技术通过其内容,属性和空间位置来访问该文本提供了标准机制。如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleText
 * @see AccessibleContext#getAccessibleEditableText
 *
 * @author      Lynn Monsanto
 * @since 1.4
 */

public interface AccessibleEditableText extends AccessibleText {

    /**
     * Sets the text contents to the specified string.
     *
     * <p>
     *  将文本内容设置为指定的字符串。
     * 
     * 
     * @param s the string to set the text contents
     */
    public void setTextContents(String s);

    /**
     * Inserts the specified string at the given index/
     *
     * <p>
     *  将指定的字符串插入给定的索引/
     * 
     * 
     * @param index the index in the text where the string will
     * be inserted
     * @param s the string to insert in the text
     */
    public void insertTextAtIndex(int index, String s);

    /**
     * Returns the text string between two indices.
     *
     * <p>
     *  返回两个索引之间的文本字符串。
     * 
     * 
     * @param startIndex the starting index in the text
     * @param endIndex the ending index in the text
     * @return the text string between the indices
     */
    public String getTextRange(int startIndex, int endIndex);

    /**
     * Deletes the text between two indices
     *
     * <p>
     *  删除两个索引之间的文本
     * 
     * 
     * @param startIndex the starting index in the text
     * @param endIndex the ending index in the text
     */
    public void delete(int startIndex, int endIndex);

    /**
     * Cuts the text between two indices into the system clipboard.
     *
     * <p>
     *  将两个索引之间的文本切换到系统剪贴板。
     * 
     * 
     * @param startIndex the starting index in the text
     * @param endIndex the ending index in the text
     */
    public void cut(int startIndex, int endIndex);

    /**
     * Pastes the text from the system clipboard into the text
     * starting at the specified index.
     *
     * <p>
     *  将系统剪贴板中的文本粘贴到从指定索引开始的文本中。
     * 
     * 
     * @param startIndex the starting index in the text
     */
    public void paste(int startIndex);

    /**
     * Replaces the text between two indices with the specified
     * string.
     *
     * <p>
     *  用指定的字符串替换两个索引之间的文本。
     * 
     * 
     * @param startIndex the starting index in the text
     * @param endIndex the ending index in the text
     * @param s the string to replace the text between two indices
     */
    public void replaceText(int startIndex, int endIndex, String s);

    /**
     * Selects the text between two indices.
     *
     * <p>
     *  选择两个索引之间的文本。
     * 
     * 
     * @param startIndex the starting index in the text
     * @param endIndex the ending index in the text
     */
    public void selectText(int startIndex, int endIndex);

    /**
     * Sets attributes for the text between two indices.
     *
     * <p>
     *  在两个索引之间设置文本的属性。
     * 
     * @param startIndex the starting index in the text
     * @param endIndex the ending index in the text
     * @param as the attribute set
     * @see AttributeSet
     */
    public void setAttributes(int startIndex, int endIndex, AttributeSet as);

}
