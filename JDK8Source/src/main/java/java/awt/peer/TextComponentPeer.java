/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.TextComponent;
import java.awt.im.InputMethodRequests;

/**
 * The peer interface for {@link TextComponent}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link TextComponent}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface TextComponentPeer extends ComponentPeer {

    /**
     * Sets if the text component should be editable or not.
     *
     * <p>
     *  设置文本组件是否应该可编辑。
     * 
     * 
     * @param editable {@code true} for editable text components,
     *        {@code false} for non-editable text components
     *
     * @see TextComponent#setEditable(boolean)
     */
    void setEditable(boolean editable);

    /**
     * Returns the current content of the text component.
     *
     * <p>
     *  返回文本组件的当前内容。
     * 
     * 
     * @return the current content of the text component
     *
     * @see TextComponent#getText()
     */
    String getText();

    /**
     * Sets the content for the text component.
     *
     * <p>
     *  设置文本组件的内容。
     * 
     * 
     * @param text the content to set
     *
     * @see TextComponent#setText(String)
     */
    void setText(String text);

    /**
     * Returns the start index of the current selection.
     *
     * <p>
     *  返回当前选择的开始索引。
     * 
     * 
     * @return the start index of the current selection
     *
     * @see TextComponent#getSelectionStart()
     */
    int getSelectionStart();

    /**
     * Returns the end index of the current selection.
     *
     * <p>
     *  返回当前选择的结束索引。
     * 
     * 
     * @return the end index of the current selection
     *
     * @see TextComponent#getSelectionEnd()
     */
    int getSelectionEnd();

    /**
     * Selects an area of the text component.
     *
     * <p>
     *  选择文本组件的区域。
     * 
     * 
     * @param selStart the start index of the new selection
     * @param selEnd the end index of the new selection
     *
     * @see TextComponent#select(int, int)
     */
    void select(int selStart, int selEnd);

    /**
     * Sets the caret position of the text component.
     *
     * <p>
     *  设置文本组件的插入符号位置。
     * 
     * 
     * @param pos the caret position to set
     *
     * @see TextComponent#setCaretPosition(int)
     */
    void setCaretPosition(int pos);

    /**
     * Returns the current caret position.
     *
     * <p>
     *  返回当前插入符号位置。
     * 
     * 
     * @return the current caret position
     *
     * @see TextComponent#getCaretPosition()
     */
    int getCaretPosition();

    /**
     * Returns the input method requests.
     *
     * <p>
     *  返回输入法请求。
     * 
     * @return the input method requests
     */
    InputMethodRequests getInputMethodRequests();
}
