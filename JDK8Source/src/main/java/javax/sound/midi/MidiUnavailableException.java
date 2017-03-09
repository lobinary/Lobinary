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


/**
 * A <code>MidiUnavailableException</code> is thrown when a requested MIDI
 * component cannot be opened or created because it is unavailable.  This often
 * occurs when a device is in use by another application.  More generally, it
 * can occur when there is a finite number of a certain kind of resource that can
 * be used for some purpose, and all of them are already in use (perhaps all by
 * this application).  For an example of the latter case, see the
 * {@link Transmitter#setReceiver(Receiver) setReceiver} method of
 * <code>Transmitter</code>.
 *
 * <p>
 *  当所请求的MIDI组件因为不可用而无法打开或创建时,会抛出<code> MidiUnavailableException </code>。这通常发生在设备被另一个应用程序使用时。
 * 更一般地,当存在可用于某种目的的某一类型的资源的有限数量,并且它们都已经在使用(可能全部由该应用)时,可以发生。
 * 关于后一种情况的示例,参见<code> Transmitter </code>的{@link Transmitter#setReceiver(receiver)setReceiver}方法。
 * 
 * 
 * @author Kara Kytle
 */
public class MidiUnavailableException extends Exception {

    /**
     * Constructs a <code>MidiUnavailableException</code> that has
     * <code>null</code> as its error detail message.
     * <p>
     *  构造一个具有<code> null </code>作为其错误详细信息的<code> MidiUnavailableException </code>。
     * 
     */
    public MidiUnavailableException() {

        super();
    }


    /**
     *  Constructs a <code>MidiUnavailableException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> MidiUnavailableException </code>。
     * 
     * @param message the string to display as an error detail message
     */
    public MidiUnavailableException(String message) {

        super(message);
    }
}
