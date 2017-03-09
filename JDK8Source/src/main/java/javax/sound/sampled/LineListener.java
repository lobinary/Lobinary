/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.sampled;



/**
 * Instances of classes that implement the <code>LineListener</code> interface can register to
 * receive events when a line's status changes.
 *
 * <p>
 *  实现<code> LineListener </code>接口的类的实例可以注册以在线的状态改变时接收事件。
 * 
 * 
 * @author Kara Kytle
 *
 * @see Line
 * @see Line#addLineListener
 * @see Line#removeLineListener
 * @see LineEvent
 *
 * @since 1.3
 */
/*
 * Instances of classes that implement the <code>LineListener</code> interface can register to
 * receive events when a line's status changes.
 *
 * <p>
 *  实现<code> LineListener </code>接口的类的实例可以注册以在线的状态改变时接收事件。
 * 
 * 
 * @see Line
 * @see Line#addLineListener
 * @see Line#removeLineListener
 * @see LineEvent
 *
 * @author Kara Kytle
 */
public interface LineListener extends java.util.EventListener {

    /**
     * Informs the listener that a line's state has changed.  The listener can then invoke
     * <code>LineEvent</code> methods to obtain information about the event.
     * <p>
     *  通知侦听器线路的状态已更改。然后侦听器可以调用<code> LineEvent </code>方法来获取有关事件的信息。
     * 
     * 
     * @param event a line event that describes the change
     */
    /*
     * Informs the listener that a line's state has changed.  The listener can then invoke
     * <code>LineEvent</code> methods to obtain information about the event.
     * <p>
     *  通知侦听器线路的状态已更改。然后侦听器可以调用<code> LineEvent </code>方法来获取有关事件的信息。
     * 
     * @param event a line event that describes the change
     */
    public void update(LineEvent event);

} // interface LineListener
