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

package javax.sound.midi;

/**
 * <code>MidiMessage</code> is the base class for MIDI messages.  They include
 * not only the standard MIDI messages that a synthesizer can respond to, but also
 * "meta-events" that can be used by sequencer programs.  There are meta-events
 * for such information as lyrics, copyrights, tempo indications, time and key
 * signatures, markers, etc.  For more information, see the Standard MIDI Files 1.0
 * specification, which is part of the Complete MIDI 1.0 Detailed Specification
 * published by the MIDI Manufacturer's Association
 * (<a href = http://www.midi.org>http://www.midi.org</a>).
 * <p>
 * The base <code>MidiMessage</code> class provides access to three types of
 * information about a MIDI message:
 * <ul>
 * <li>The messages's status byte</li>
 * <li>The total length of the message in bytes (the status byte plus any data bytes)</li>
 * <li>A byte array containing the complete message</li>
 * </ul>
 *
 * <code>MidiMessage</code> includes methods to get, but not set, these values.
 * Setting them is a subclass responsibility.
 * <p>
 * <a name="integersVsBytes"></a>
 * The MIDI standard expresses MIDI data in bytes.  However, because
 * Java<sup>TM</sup> uses signed bytes, the Java Sound API uses integers
 * instead of bytes when expressing MIDI data.  For example, the
 * {@link #getStatus()} method of
 * <code>MidiMessage</code> returns MIDI status bytes as integers.  If you are
 * processing MIDI data that originated outside Java Sound and now
 * is encoded as signed bytes, the bytes can
 * can be converted to integers using this conversion:
 * <center>{@code int i = (int)(byte & 0xFF)}</center>
 * <p>
 * If you simply need to pass a known MIDI byte value as a method parameter,
 * it can be expressed directly as an integer, using (for example) decimal or
 * hexadecimal notation.  For instance, to pass the "active sensing" status byte
 * as the first argument to ShortMessage's
 * {@link ShortMessage#setMessage(int) setMessage(int)}
 * method, you can express it as 254 or 0xFE.
 *
 * <p>
 *  <code> MidiMessage </code>是MIDI消息的基类。它们不仅包括合成器可以响应的标准MIDI消息,而且包括可由序列器程序使用的"元事件"。
 * 有诸如歌词,版权,速度指示,时间和键签名,标记等信息的元事件。
 * 有关更多信息,请参阅标准MIDI文件1.0规范,它是由完整的MIDI 1.0详细规范的一部分发布的MIDI制造商协会(<a href = http://www.midi.org> http://www.
 * midi.org </a>)。
 * 有诸如歌词,版权,速度指示,时间和键签名,标记等信息的元事件。
 * <p>
 *  基本<code> MidiMessage </code>类提供了有关MIDI消息的三种类型的信息：
 * <ul>
 *  <li>邮件的状态字节</li> <li>邮件的总长度(以字节为单位)(状态字节加上任何数据字节)</li> <li>包含完整邮件的字节数组</li>
 * </ul>
 * 
 *  <code> MidiMessage </code>包括获取这些值但不设置这些值的方法。设置它们是一个子类责任。
 * <p>
 * <a name="integersVsBytes"> </a> MIDI标准以字节表示MIDI数据。
 * 但是,因为Java <sup> TM </sup>使用带符号的字节,所以Java Sound API在表达MIDI数据时使用整数而不是字节。
 * 例如,<code> MidiMessage </code>的{@link #getStatus()}方法返回MIDI状态字节作为整数。
 * 如果你正在处理源自Java Sound之外的MIDI数据,并且现在被编码为有符号字节,那么可以使用这种转换将字节转换为整数：<center> {@ code int i =(int)(byte&0xFF)}
 *  < / center>。
 * 例如,<code> MidiMessage </code>的{@link #getStatus()}方法返回MIDI状态字节作为整数。
 * <p>
 *  如果你只需要传递一个已知的MIDI字节值作为方法参数,它可以直接表示为整数,使用(例如)十进制或十六进制符号。
 * 例如,要将"active sensing"状态字节作为第一个参数传递给ShortMessage的{@link ShortMessage#setMessage(int)setMessage(int)}方法
 * ,可以将其表示为254或0xFE。
 *  如果你只需要传递一个已知的MIDI字节值作为方法参数,它可以直接表示为整数,使用(例如)十进制或十六进制符号。
 * 
 * 
 * @see Track
 * @see Sequence
 * @see Receiver
 *
 * @author David Rivas
 * @author Kara Kytle
 */

public abstract class MidiMessage implements Cloneable {

    // Instance variables

    /**
     * The MIDI message data.  The first byte is the status
     * byte for the message; subsequent bytes up to the length
     * of the message are data bytes for this message.
     * <p>
     * 
     * @see #getLength
     */
    protected byte[] data;


    /**
     * The number of bytes in the MIDI message, including the
     * status byte and any data bytes.
     * <p>
     *  MIDI信息数据。第一个字节是消息的状态字节;直到消息长度的后续字节是该消息的数据字节。
     * 
     * 
     * @see #getLength
     */
    protected int length = 0;


    /**
     * Constructs a new <code>MidiMessage</code>.  This protected
     * constructor is called by concrete subclasses, which should
     * ensure that the data array specifies a complete, valid MIDI
     * message.
     *
     * <p>
     *  MIDI消息中的字节数,包括状态字节和任何数据字节。
     * 
     * 
     * @param data an array of bytes containing the complete message.
     * The message data may be changed using the <code>setMessage</code>
     * method.
     *
     * @see #setMessage
     */
    protected MidiMessage(byte[] data) {
        this.data = data;
        if (data != null) {
            this.length = data.length;
        }
    }


    /**
     * Sets the data for the MIDI message.   This protected
     * method is called by concrete subclasses, which should
     * ensure that the data array specifies a complete, valid MIDI
     * message.
     *
     * <p>
     *  构造一个新的<code> MidiMessage </code>。这个受保护的构造函数由具体的子类调用,这应该确保数据数组指定一个完整的,有效的MIDI消息。
     * 
     * 
     * @param data the data bytes in the MIDI message
     * @param length the number of bytes in the data byte array
     * @throws InvalidMidiDataException if the parameter values do not specify a valid MIDI meta message
     */
    protected void setMessage(byte[] data, int length) throws InvalidMidiDataException {
        if (length < 0 || (length > 0 && length > data.length)) {
            throw new IndexOutOfBoundsException("length out of bounds: "+length);
        }
        this.length = length;

        if (this.data == null || this.data.length < this.length) {
            this.data = new byte[this.length];
        }
        System.arraycopy(data, 0, this.data, 0, length);
    }


    /**
     * Obtains the MIDI message data.  The first byte of the returned byte
     * array is the status byte of the message.  Any subsequent bytes up to
     * the length of the message are data bytes.  The byte array may have a
     * length which is greater than that of the actual message; the total
     * length of the message in bytes is reported by the <code>{@link #getLength}</code>
     * method.
     *
     * <p>
     * 设置MIDI信息的数据。这个受保护的方法由具体子类调用,这应该确保数据数组指定一个完整,有效的MIDI消息。
     * 
     * 
     * @return the byte array containing the complete <code>MidiMessage</code> data
     */
    public byte[] getMessage() {
        byte[] returnedArray = new byte[length];
        System.arraycopy(data, 0, returnedArray, 0, length);
        return returnedArray;
    }


    /**
     * Obtains the status byte for the MIDI message.  The status "byte" is
     * represented as an integer; see the
     * <a href="#integersVsBytes">discussion</a> in the
     * <code>MidiMessage</code> class description.
     *
     * <p>
     *  获取MIDI消息数据。返回的字节数组的第一个字节是消息的状态字节。到消息长度的任何后续字节都是数据字节。
     * 字节数组可以具有大于实际消息的长度的长度;消息的总长度(以字节为单位)由<code> {@ link #getLength} </code>方法报告。
     * 
     * 
     * @return the integer representation of this event's status byte
     */
    public int getStatus() {
        if (length > 0) {
            return (data[0] & 0xFF);
        }
        return 0;
    }


    /**
     * Obtains the total length of the MIDI message in bytes.  A
     * MIDI message consists of one status byte and zero or more
     * data bytes.  The return value ranges from 1 for system real-time messages,
     * to 2 or 3 for channel messages, to any value for meta and system
     * exclusive messages.
     *
     * <p>
     *  获取MIDI消息的状态字节。状态"字节"被表示为整数;请参阅<code> MidiMessage </code>类描述中的<a href="#integersVsBytes">讨论</a>。
     * 
     * 
     * @return the length of the message in bytes
     */
    public int getLength() {
        return length;
    }


    /**
     * Creates a new object of the same class and with the same contents
     * as this object.
     * <p>
     *  以字节为单位获取MIDI消息的总长度。 MIDI消息由一个状态字节和零个或多个数据字节组成。返回值的范围从系统实时消息的1到通道消息的2或3,到元和系统独占消息的任何值。
     * 
     * 
     * @return a clone of this instance.
     */
    public abstract Object clone();
}
