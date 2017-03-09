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
 * A <code>Transmitter</code> sends <code>{@link MidiEvent}</code> objects to one or more
 * <code>{@link Receiver Receivers}</code>. Common MIDI transmitters include sequencers
 * and MIDI input ports.
 *
 * <p>
 *  <code>发送器</code>将<code> {@ link MidiEvent} </code>对象发送到一个或多个<code> {@ link Receiver Receivers} </code>
 * 。
 * 常见的MIDI发射器包括定序器和MIDI输入端口。
 * 
 * 
 * @see Receiver
 *
 * @author Kara Kytle
 */
public interface Transmitter extends AutoCloseable {


    /**
     * Sets the receiver to which this transmitter will deliver MIDI messages.
     * If a receiver is currently set, it is replaced with this one.
     * <p>
     *  设置此发射器将传送MIDI消息的接收器。如果当前设置了接收器,则用该接收器替换。
     * 
     * 
     * @param receiver the desired receiver.
     */
    public void setReceiver(Receiver receiver);


    /**
     * Obtains the current receiver to which this transmitter will deliver MIDI messages.
     * <p>
     *  获取当前接收器,该发射器将向其发送MIDI消息。
     * 
     * 
     * @return the current receiver.  If no receiver is currently set,
     * returns <code>null</code>
     */
    public Receiver getReceiver();


    /**
     * Indicates that the application has finished using the transmitter, and
     * that limited resources it requires may be released or made available.
     *
     * <p>If the creation of this <code>Transmitter</code> resulted in
     * implicitly opening the underlying device, the device is
     * implicitly closed by this method. This is true unless the device is
     * kept open by other <code>Receiver</code> or <code>Transmitter</code>
     * instances that opened the device implicitly, and unless the device
     * has been opened explicitly. If the device this
     * <code>Transmitter</code> is retrieved from is closed explicitly
     * by calling {@link MidiDevice#close MidiDevice.close}, the
     * <code>Transmitter</code> is closed, too.  For a detailed
     * description of open/close behaviour see the class description
     * of {@link javax.sound.midi.MidiDevice MidiDevice}.
     *
     * <p>
     *  表示应用程序已使用发射机,并且需要的有限资源可能会被释放或提供。
     * 
     *  <p>如果创建此<code> Transmitter </code>导致隐式打开底层设备,则此方法会隐式关闭设备。
     * 这是真的,除非设备由其他隐含打开设备的<code> Receiver </code>或<code> Transmitter </code>实例保持打开,除非设备已被明确打开。
     * 
     * @see javax.sound.midi.MidiSystem#getTransmitter
     */
    public void close();
}
