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

package javax.sound.midi;

import java.util.EventListener;


/**
 * The <code>ControllerEventListener</code> interface should be implemented
 * by classes whose instances need to be notified when a <code>Sequencer</code>
 * has processed a requested type of MIDI control-change event.
 * To register a <code>ControllerEventListener</code> object to receive such
 * notifications, invoke the
 * {@link Sequencer#addControllerEventListener(ControllerEventListener, int[])
 * addControllerEventListener} method of <code>Sequencer</code>,
 * specifying the types of MIDI controllers about which you are interested in
 * getting control-change notifications.
 *
 * <p>
 *  <code> ControllerEventListener </code>接口应该由类实现,当<code> Sequencer </code>处理请求类型的MIDI控制变化事件时,需要通知实例。
 * 要注册<code> ControllerEventListener </code>对象以接收此类通知,请调用<code> Sequencer </code>的{@link Sequencer#addControllerEventListener(ControllerEventListener,int [])addControllerEventListener}
 * 方法,控制器,您有兴趣获取控制更改通知。
 *  <code> ControllerEventListener </code>接口应该由类实现,当<code> Sequencer </code>处理请求类型的MIDI控制变化事件时,需要通知实例。
 * 
 * 
 * @see MidiChannel#controlChange(int, int)
 *
 * @author Kara Kytle
 */
public interface ControllerEventListener extends EventListener {

    /**
     * Invoked when a <code>Sequencer</code> has encountered and processed
     * a control-change event of interest to this listener.  The event passed
     * in is a <code>ShortMessage</code> whose first data byte indicates
     * the controller number and whose second data byte is the value to which
     * the controller was set.
     *
     * <p>
     * 
     * @param event the control-change event that the sequencer encountered in
     * the sequence it is processing
     *
     * @see Sequencer#addControllerEventListener(ControllerEventListener, int[])
     * @see MidiChannel#controlChange(int, int)
     * @see ShortMessage#getData1
     * @see ShortMessage#getData2
     */
    public void controlChange(ShortMessage event);
}
