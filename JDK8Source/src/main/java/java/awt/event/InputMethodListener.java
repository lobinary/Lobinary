/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.util.EventListener;

/**
 * The listener interface for receiving input method events. A text editing
 * component has to install an input method event listener in order to work
 * with input methods.
 *
 * <p>
 * The text editing component also has to provide an instance of InputMethodRequests.
 *
 * <p>
 *  用于接收输入法事件的侦听器接口。文本编辑组件必须安装输入法事件侦听器才能使用输入法。
 * 
 * <p>
 *  文本编辑组件还必须提供InputMethodRequests的实例。
 * 
 * 
 * @author JavaSoft Asia/Pacific
 * @see InputMethodEvent
 * @see java.awt.im.InputMethodRequests
 * @since 1.2
 */

public interface InputMethodListener extends EventListener {

    /**
     * Invoked when the text entered through an input method has changed.
     * <p>
     *  通过输入法输入的文本已更改时调用。
     * 
     */
    void inputMethodTextChanged(InputMethodEvent event);

    /**
     * Invoked when the caret within composed text has changed.
     * <p>
     *  在组合文本中的插入符号发生更改时调用。
     */
    void caretPositionChanged(InputMethodEvent event);

}
