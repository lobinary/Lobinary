/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.event;

import java.awt.AWTEvent;
import javax.swing.JInternalFrame;

/**
 * An <code>AWTEvent</code> that adds support for
 * <code>JInternalFrame</code> objects as the event source.  This class has the
 * same event types as <code>WindowEvent</code>,
 * although different IDs are used.
 * Help on handling internal frame events
 * is in
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/internalframelistener.html" target="_top">How to Write an Internal Frame Listener</a>,
 * a section in <em>The Java Tutorial</em>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  <code> AWTEvent </code>添加对<code> JInternalFrame </code>对象作为事件源的支持。
 * 这个类有与<code> WindowEvent </code>相同的事件类型,尽管使用不同的ID。
 * 有关处理内部框架事件的帮助,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/internalframelistener.html" target="_top">
 * 如何编写内部框架监听器< a>,Java教程</em>中的一个部分。
 * 这个类有与<code> WindowEvent </code>相同的事件类型,尽管使用不同的ID。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see java.awt.event.WindowEvent
 * @see java.awt.event.WindowListener
 * @see JInternalFrame
 * @see InternalFrameListener
 *
 * @author Thomas Ball
 */
public class InternalFrameEvent extends AWTEvent {

    /**
     * The first number in the range of IDs used for internal frame events.
     * <p>
     *  用于内部帧事件的ID范围内的第一个数字。
     * 
     */
    public static final int INTERNAL_FRAME_FIRST        = 25549;

    /**
     * The last number in the range of IDs used for internal frame events.
     * <p>
     *  用于内部帧事件的ID范围中的最后一个数字。
     * 
     */
    public static final int INTERNAL_FRAME_LAST         = 25555;

    /**
     * The "window opened" event.  This event is delivered only
     * the first time the internal frame is made visible.
     *
     * <p>
     *  "窗口打开"事件。此事件仅在第一次使内部框架可见时传递。
     * 
     * 
     * @see JInternalFrame#show
     */
    public static final int INTERNAL_FRAME_OPENED       = INTERNAL_FRAME_FIRST;

    /**
     * The "window is closing" event. This event is delivered when
     * the user attempts to close the internal frame, such as by
     * clicking the internal frame's close button,
     * or when a program attempts to close the internal frame
     * by invoking the <code>setClosed</code> method.
     *
     * <p>
     * "窗口正在关闭"事件。当用户尝试关闭内部框架时,例如通过单击内部框架的关闭按钮,或当程序尝试通过调用<code> setClosed </code>方法尝试关闭内部框架时,将传递此事件。
     * 
     * 
     * @see JInternalFrame#setDefaultCloseOperation
     * @see JInternalFrame#doDefaultCloseAction
     * @see JInternalFrame#setClosed
     */
    public static final int INTERNAL_FRAME_CLOSING      = 1 + INTERNAL_FRAME_FIRST;

    /**
     * The "window closed" event. This event is delivered after
     * the internal frame has been closed as the result of a call to
     * the <code>setClosed</code> or
     * <code>dispose</code> method.
     *
     * <p>
     *  "窗口关闭"事件。由于调用<code> setClosed </code>或<code> dispose </code>方法而导致内部框架关闭后,将发送此事件。
     * 
     * 
     * @see JInternalFrame#setClosed
     * @see JInternalFrame#dispose
     */
    public static final int INTERNAL_FRAME_CLOSED       = 2 + INTERNAL_FRAME_FIRST;

    /**
     * The "window iconified" event.
     * This event indicates that the internal frame
     * was shrunk down to a small icon.
     *
     * <p>
     *  "窗口图标化"事件。此事件表示内部框架缩小为小图标。
     * 
     * 
     * @see JInternalFrame#setIcon
     */
    public static final int INTERNAL_FRAME_ICONIFIED    = 3 + INTERNAL_FRAME_FIRST;

    /**
     * The "window deiconified" event type. This event indicates that the
     * internal frame has been restored to its normal size.
     *
     * <p>
     *  "窗口deiconified"事件类型。此事件表示内部帧已恢复到其正常大小。
     * 
     * 
     * @see JInternalFrame#setIcon
     */
    public static final int INTERNAL_FRAME_DEICONIFIED  = 4 + INTERNAL_FRAME_FIRST;

    /**
     * The "window activated" event type. This event indicates that keystrokes
     * and mouse clicks are directed towards this internal frame.
     *
     * <p>
     *  "窗口激活"事件类型。此事件表示击键和鼠标点击指向此内部框架。
     * 
     * 
     * @see JInternalFrame#show
     * @see JInternalFrame#setSelected
     */
    public static final int INTERNAL_FRAME_ACTIVATED    = 5 + INTERNAL_FRAME_FIRST;

    /**
     * The "window deactivated" event type. This event indicates that keystrokes
     * and mouse clicks are no longer directed to the internal frame.
     *
     * <p>
     *  "窗口停用"事件类型。此事件表示按键和鼠标单击不再指向内部框架。
     * 
     * 
     * @see JInternalFrame#setSelected
     */
    public static final int INTERNAL_FRAME_DEACTIVATED  = 6 + INTERNAL_FRAME_FIRST;

    /**
     * Constructs an <code>InternalFrameEvent</code> object.
     * <p>
     *  构造一个<code> InternalFrameEvent </code>对象。
     * 
     * 
     * @param source the <code>JInternalFrame</code> object that originated the event
     * @param id     an integer indicating the type of event
     */
    public InternalFrameEvent(JInternalFrame source, int id) {
        super(source, id);
    }

    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event logging and for debugging.
     *
     * <p>
     *  返回标识此事件的参数字符串。此方法对事件记录和调试非常有用。
     * 
     * 
     * @return a string identifying the event and its attributes
     */
    public String paramString() {
        String typeStr;
        switch(id) {
          case INTERNAL_FRAME_OPENED:
              typeStr = "INTERNAL_FRAME_OPENED";
              break;
          case INTERNAL_FRAME_CLOSING:
              typeStr = "INTERNAL_FRAME_CLOSING";
              break;
          case INTERNAL_FRAME_CLOSED:
              typeStr = "INTERNAL_FRAME_CLOSED";
              break;
          case INTERNAL_FRAME_ICONIFIED:
              typeStr = "INTERNAL_FRAME_ICONIFIED";
              break;
          case INTERNAL_FRAME_DEICONIFIED:
              typeStr = "INTERNAL_FRAME_DEICONIFIED";
              break;
          case INTERNAL_FRAME_ACTIVATED:
              typeStr = "INTERNAL_FRAME_ACTIVATED";
              break;
          case INTERNAL_FRAME_DEACTIVATED:
              typeStr = "INTERNAL_FRAME_DEACTIVATED";
              break;
          default:
              typeStr = "unknown type";
        }
        return typeStr;
    }


    /**
     * Returns the originator of the event.
     *
     * <p>
     *  返回事件的发起者。
     * 
     * @return the <code>JInternalFrame</code> object that originated the event
     * @since 1.3
     */

    public JInternalFrame getInternalFrame () {
      return (source instanceof JInternalFrame)? (JInternalFrame)source : null;
    }


}
