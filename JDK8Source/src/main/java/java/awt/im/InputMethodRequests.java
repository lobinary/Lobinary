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

package java.awt.im;

import java.awt.Rectangle;
import java.awt.font.TextHitInfo;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;

/**
 * InputMethodRequests defines the requests that a text editing component
 * has to handle in order to work with input methods. The component
 * can implement this interface itself or use a separate object that
 * implements it. The object implementing this interface must be returned
 * from the component's getInputMethodRequests method.
 *
 * <p>
 * The text editing component also has to provide an input method event
 * listener.
 *
 * <p>
 * The interface is designed to support one of two input user interfaces:
 * <ul>
 * <li><em>on-the-spot</em> input, where the composed text is displayed as part
 *     of the text component's text body.
 * <li><em>below-the-spot</em> input, where the composed text is displayed in
 *     a separate composition window just below the insertion point where
 *     the text will be inserted when it is committed. Note that, if text is
 *     selected within the component's text body, this text will be replaced by
 *     the committed text upon commitment; therefore it is not considered part
 *     of the context that the text is input into.
 * </ul>
 *
 * <p>
 *  InputMethodRequests定义文本编辑组件为了处理输入方法而必须处理的请求。组件可以实现此接口本身或使用实现它的单独的对象。
 * 实现此接口的对象必须从组件的getInputMethodRequests方法返回。
 * 
 * <p>
 *  文本编辑组件还必须提供一个输入法事件侦听器。
 * 
 * <p>
 *  该接口被设计为支持两个输入用户接口之一：
 * <ul>
 *  <li>现场</em>输入,其中组合文本显示为文本组件文本正文的一部分。
 *  <li> <em> below-the-spot </em>输入,其中组合文本显示在插入点正下方的单独合成窗口中,在插入点提交时将插入文本。
 * 请注意,如果在组件的文本正文中选择了文本,则该文本将在承诺时由已提交的文本替换;因此它不被认为是文本被输入的上下文的一部分。
 * </ul>
 * 
 * 
 * @see java.awt.Component#getInputMethodRequests
 * @see java.awt.event.InputMethodListener
 *
 * @author JavaSoft Asia/Pacific
 * @since 1.2
 */

public interface InputMethodRequests {

    /**
     * Gets the location of a specified offset in the current composed text,
     * or of the selection in committed text.
     * This information is, for example, used to position the candidate window
     * near the composed text, or a composition window near the location
     * where committed text will be inserted.
     *
     * <p>
     * If the component has composed text (because the most recent
     * InputMethodEvent sent to it contained composed text), then the offset is
     * relative to the composed text - offset 0 indicates the first character
     * in the composed text. The location returned should be for this character.
     *
     * <p>
     * If the component doesn't have composed text, the offset should be ignored,
     * and the location returned should reflect the beginning (in line
     * direction) of the highlight in the last line containing selected text.
     * For example, for horizontal left-to-right text (such as English), the
     * location to the left of the left-most character on the last line
     * containing selected text is returned. For vertical top-to-bottom text,
     * with lines proceeding from right to left, the location to the top of the
     * left-most line containing selected text is returned.
     *
     * <p>
     * The location is represented as a 0-thickness caret, that is, it has 0
     * width if the text is drawn horizontally, and 0 height if the text is
     * drawn vertically. Other text orientations need to be mapped to
     * horizontal or vertical orientation. The rectangle uses absolute screen
     * coordinates.
     *
     * <p>
     *  获取当前组合文本中或指定文本中选择的指定偏移的位置。例如,该信息用于将候选窗口放置在组合文本附近,或者在将插入确认文本的位置附近的合成窗口。
     * 
     * <p>
     * 如果组件具有组合文本(因为发送给它的最新InputMethodEvent包含组合文本),则偏移量相对于组合文本 - 偏移量0表示组合文本中的第一个字符。返回的位置应该是此字符。
     * 
     * <p>
     *  如果组件没有组合文本,则应忽略偏移,并且返回的位置应反映包含所选文本的最后一行中高亮的开始(行方向)。例如,对于从左到右的水平文本(例如英语),返回包含所选文本的最后一行上最左边字符左侧的位置。
     * 对于垂直从上到下的文本,从右到左的行,返回包含所选文本的最左行顶部的位置。
     * 
     * <p>
     *  该位置表示为0厚度插入符号,即,如果文本是水平绘制,则其宽度为0,如果垂直绘制文本,则为0高度。其他文本取向需要被映射到水平或垂直取向。矩形使用绝对屏幕坐标。
     * 
     * 
     * @param offset the offset within the composed text, if there is composed
     * text; null otherwise
     * @return a rectangle representing the screen location of the offset
     */
    Rectangle getTextLocation(TextHitInfo offset);

    /**
     * Gets the offset within the composed text for the specified absolute x
     * and y coordinates on the screen. This information is used, for example
     * to handle mouse clicks and the mouse cursor. The offset is relative to
     * the composed text, so offset 0 indicates the beginning of the composed
     * text.
     *
     * <p>
     * Return null if the location is outside the area occupied by the composed
     * text.
     *
     * <p>
     *  获取屏幕上指定的绝对x和y坐标的组合文本内的偏移量。此信息用于例如处理鼠标点击和鼠标光标。偏移量相对于组合文本,因此偏移量0表示组合文本的开始。
     * 
     * <p>
     *  如果位置在组合文本占据的区域之外,则返回null。
     * 
     * 
     * @param x the absolute x coordinate on screen
     * @param y the absolute y coordinate on screen
     * @return a text hit info describing the offset in the composed text.
     */
    TextHitInfo getLocationOffset(int x, int y);

    /**
     * Gets the offset of the insert position in the committed text contained
     * in the text editing component. This is the offset at which characters
     * entered through an input method are inserted. This information is used
     * by an input method, for example, to examine the text surrounding the
     * insert position.
     *
     * <p>
     * 获取文本编辑组件中包含的已提交文本中插入位置的偏移量。这是插入通过输入法输入的字符的偏移量。此信息由输入法使用,例如,以检查插入位置周围的文本。
     * 
     * 
     * @return the offset of the insert position
     */
    int getInsertPositionOffset();

    /**
     * Gets an iterator providing access to the entire text and attributes
     * contained in the text editing component except for uncommitted
     * text. Uncommitted (composed) text should be ignored for index
     * calculations and should not be made accessible through the iterator.
     *
     * <p>
     * The input method may provide a list of attributes that it is
     * interested in. In that case, information about other attributes that
     * the implementor may have need not be made accessible through the
     * iterator. If the list is null, all available attribute information
     * should be made accessible.
     *
     * <p>
     *  获取迭代器,提供对包含在文本编辑组件中的整个文本和属性的访问,但未提交的文本除外。对于索引计算,应忽略未提交(组合)文本,并且不应通过迭代器访问。
     * 
     * <p>
     *  输入方法可以提供其感兴趣的属性的列表。在这种情况下,关于实现者可能不需要通过迭代器可访问的其他属性的信息。如果列表为空,则应使所有可用的属性信息可访问。
     * 
     * 
     * @param beginIndex the index of the first character
     * @param endIndex the index of the character following the last character
     * @param attributes a list of attributes that the input method is
     * interested in
     * @return an iterator providing access to the text and its attributes
     */
    AttributedCharacterIterator getCommittedText(int beginIndex, int endIndex,
                                                 Attribute[] attributes);

    /**
     * Gets the length of the entire text contained in the text
     * editing component except for uncommitted (composed) text.
     *
     * <p>
     *  获取文本编辑组件中包含的整个文本的长度,但未提交(合成)文本除外。
     * 
     * 
     * @return the length of the text except for uncommitted text
     */
    int getCommittedTextLength();

    /**
     * Gets the latest committed text from the text editing component and
     * removes it from the component's text body.
     * This is used for the "Undo Commit" feature in some input methods, where
     * the committed text reverts to its previous composed state. The composed
     * text will be sent to the component using an InputMethodEvent.
     *
     * <p>
     * Generally, this feature should only be supported immediately after the
     * text was committed, not after the user performed other operations on the
     * text. When the feature is not supported, return null.
     *
     * <p>
     * The input method may provide a list of attributes that it is
     * interested in. In that case, information about other attributes that
     * the implementor may have need not be made accessible through the
     * iterator. If the list is null, all available attribute information
     * should be made accessible.
     *
     * <p>
     *  从文本编辑组件获取最新提交的文本,并将其从组件的文本正文中删除。这用于一些输入方法中的"撤消提交"功能,其中已提交的文本恢复到其先前的组合状态。
     * 组合文本将使用InputMethodEvent发送到组件。
     * 
     * <p>
     *  通常,此功能只应在文本提交后立即支持,而不是在用户对文本执行其他操作后支持。当不支持该功能时,返回null。
     * 
     * <p>
     * 输入方法可以提供其感兴趣的属性的列表。在这种情况下,关于实现者可能不需要通过迭代器可访问的其他属性的信息。如果列表为空,则应使所有可用的属性信息可访问。
     * 
     * @param attributes a list of attributes that the input method is
     * interested in
     * @return the latest committed text, or null when the "Undo Commit"
     * feature is not supported
     */
    AttributedCharacterIterator cancelLatestCommittedText(Attribute[] attributes);

    /**
     * Gets the currently selected text from the text editing component.
     * This may be used for a variety of purposes.
     * One of them is the "Reconvert" feature in some input methods.
     * In this case, the input method will typically send an input method event
     * to replace the selected text with composed text. Depending on the input
     * method's capabilities, this may be the original composed text for the
     * selected text, the latest composed text entered anywhere in the text, or
     * a version of the text that's converted back from the selected text.
     *
     * <p>
     * The input method may provide a list of attributes that it is
     * interested in. In that case, information about other attributes that
     * the implementor may have need not be made accessible through the
     * iterator. If the list is null, all available attribute information
     * should be made accessible.
     *
     * <p>
     * 
     * 
     * @param attributes a list of attributes that the input method is
     * interested in
     * @return the currently selected text
     */
    AttributedCharacterIterator getSelectedText(Attribute[] attributes);
}
