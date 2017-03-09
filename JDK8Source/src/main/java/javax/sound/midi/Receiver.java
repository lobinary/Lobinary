/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>Receiver</code> receives <code>{@link MidiEvent}</code> objects and
 * typically does something useful in response, such as interpreting them to
 * generate sound or raw MIDI output.  Common MIDI receivers include
 * synthesizers and MIDI Out ports.
 *
 * <p>
 *  <code> Receiver </code>接收<code> {@ link MidiEvent} </code>对象,通常做出一些有用的响应,例如解释它们生成声音或原始MIDI输出。
 * 常见的MIDI接收器包括合成器和MIDI输出端口。
 * 
 * 
 * @see MidiDevice
 * @see Synthesizer
 * @see Transmitter
 *
 * @author Kara Kytle
 */
public interface Receiver extends AutoCloseable {


    //$$fb 2002-04-12: fix for 4662090: Contradiction in Receiver specification
    /**
     * Sends a MIDI message and time-stamp to this receiver.
     * If time-stamping is not supported by this receiver, the time-stamp
     * value should be -1.
     * <p>
     *  向此接收器发送MIDI消息和时间戳。如果此接收器不支持时间戳,则时间戳值应为-1。
     * 
     * 
     * @param message the MIDI message to send
     * @param timeStamp the time-stamp for the message, in microseconds.
     * @throws IllegalStateException if the receiver is closed
     */
    public void send(MidiMessage message, long timeStamp);

    /**
     * Indicates that the application has finished using the receiver, and
     * that limited resources it requires may be released or made available.
     *
     * <p>If the creation of this <code>Receiver</code> resulted in
     * implicitly opening the underlying device, the device is
     * implicitly closed by this method. This is true unless the device is
     * kept open by other <code>Receiver</code> or <code>Transmitter</code>
     * instances that opened the device implicitly, and unless the device
     * has been opened explicitly. If the device this
     * <code>Receiver</code> is retrieved from is closed explicitly by
     * calling {@link MidiDevice#close MidiDevice.close}, the
     * <code>Receiver</code> is closed, too.  For a detailed
     * description of open/close behaviour see the class description
     * of {@link javax.sound.midi.MidiDevice MidiDevice}.
     *
     * <p>
     *  表示应用程序已完成使用接收器,并且需要的有限资源可能会被释放或提供。
     * 
     *  <p>如果创建此<code> Receiver </code>导致隐式打开底层设备,则此方法会隐式关闭设备。
     * 这是真的,除非设备由其他隐含打开设备的<code> Receiver </code>或<code> Transmitter </code>实例保持打开,除非设备已被明确打开。
     * 
     * @see javax.sound.midi.MidiSystem#getReceiver
     */
    public void close();
}
