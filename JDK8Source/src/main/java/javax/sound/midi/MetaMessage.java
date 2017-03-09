/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>MetaMessage</code> is a <code>{@link MidiMessage}</code> that is not meaningful to synthesizers, but
 * that can be stored in a MIDI file and interpreted by a sequencer program.
 * (See the discussion in the <code>MidiMessage</code>
 * class description.)  The Standard MIDI Files specification defines
 * various types of meta-events, such as sequence number, lyric, cue point,
 * and set tempo.  There are also meta-events
 * for such information as lyrics, copyrights, tempo indications, time and key
 * signatures, markers, etc.  For more information, see the Standard MIDI Files 1.0
 * specification, which is part of the Complete MIDI 1.0 Detailed Specification
 * published by the MIDI Manufacturer's Association
 * (<a href = http://www.midi.org>http://www.midi.org</a>).
 *
 * <p>
 * When data is being transported using MIDI wire protocol,
 * a <code>{@link ShortMessage}</code> with the status value <code>0xFF</code> represents
 * a system reset message.  In MIDI files, this same status value denotes a <code>MetaMessage</code>.
 * The types of meta-message are distinguished from each other by the first byte
 * that follows the status byte <code>0xFF</code>.  The subsequent bytes are data
 * bytes.  As with system exclusive messages, there are an arbitrary number of
 * data bytes, depending on the type of <code>MetaMessage</code>.
 *
 * <p>
 *  <code> MetaMessage </code>是一个<code> {@ link MidiMessage} </code>,对合成器没有意义,但可以存储在MIDI文件中,并由音序器程序解释。
 *  (参见<code> MidiMessage </code>类描述中的讨论。)标准MIDI文件规范定义了各种类型的元事件,如序列号,歌词,提示点和设置速度。
 * 还有用于诸如歌词,版权,速度指示,时间和键签名,标记等的信息的元事件。
 * 关于更多信息,参见标准MIDI文件1.0规范,其是由发布的完整MIDI 1.0详细规范的一部分MIDI制造商协会(<a href = http://www.midi.org> http://www.mi
 * di.org </a>)。
 * 还有用于诸如歌词,版权,速度指示,时间和键签名,标记等的信息的元事件。
 * 
 * <p>
 *  当使用MIDI线协议传输数据时,状态值为<code> 0xFF </code>的<code> {@ link ShortMessage} </code>表示系统复位消息。
 * 在MIDI文件中,相同的状态值表示<code> MetaMessage </code>。元消息的类型通过状态字节<code> 0xFF </code>之后的第一字节来彼此区分。后续字节是数据字节。
 * 与系统独占消息一样,根据<code> MetaMessage </code>的类型,有任意数量的数据字节。
 * 
 * 
 * @see MetaEventListener
 *
 * @author David Rivas
 * @author Kara Kytle
 */

public class MetaMessage extends MidiMessage {


    // Status byte defines

    /**
     * Status byte for <code>MetaMessage</code> (0xFF, or 255), which is used
     * in MIDI files.  It has the same value as SYSTEM_RESET, which
     * is used in the real-time "MIDI wire" protocol.
     * <p>
     * <code> MetaMessage </code>(0xFF或255)的状态字节,用于MIDI文件。它与SYSTEM_RESET具有相同的值,用于实时"MIDI线"协议。
     * 
     * 
     * @see MidiMessage#getStatus
     */
    public static final int META                                                = 0xFF; // 255

    // Instance variables

    /**
     * The length of the actual message in the data array.
     * This is used to determine how many bytes of the data array
     * is the message, and how many are the status byte, the
     * type byte, and the variable-length-int describing the
     * length of the message.
     * <p>
     *  数据数组中实际消息的长度。这用于确定消息的数据数组的字节数,状态字节,类型字节和描述消息长度的variable-length-int有多少。
     * 
     */
    private int dataLength = 0;


    /**
     * Constructs a new <code>MetaMessage</code>. The contents of
     * the message are not set here; use
     * {@link #setMessage(int, byte[], int) setMessage}
     * to set them subsequently.
     * <p>
     *  构造一个新的<code> MetaMessage </code>。消息的内容不在这里设置;使用{@link #setMessage(int,byte [],int)setMessage}来设置它们。
     * 
     */
    public MetaMessage() {
        // Default meta message data: just the META status byte value
        this(new byte[]{(byte) META, 0});
    }

    /**
     * Constructs a new {@code MetaMessage} and sets the message parameters.
     * The contents of the message can be changed by using
     * the {@code setMessage} method.
     *
     * <p>
     *  构造一个新的{@code MetaMessage}并设置消息参数。可以使用{@code setMessage}方法更改消息的内容。
     * 
     * 
     * @param type   meta-message type (must be less than 128)
     * @param data   the data bytes in the MIDI message
     * @param length an amount of bytes in the {@code data} byte array;
     *     it should be non-negative and less than or equal to
     *     {@code data.length}
     * @throws InvalidMidiDataException if the parameter values do not specify
     *     a valid MIDI meta message
     * @see #setMessage(int, byte[], int)
     * @see #getType()
     * @see #getData()
     * @since 1.7
     */
    public MetaMessage(int type, byte[] data, int length)
            throws InvalidMidiDataException {
        super(null);
        setMessage(type, data, length); // can throw InvalidMidiDataException
    }


    /**
     * Constructs a new <code>MetaMessage</code>.
     * <p>
     *  构造一个新的<code> MetaMessage </code>。
     * 
     * 
     * @param data an array of bytes containing the complete message.
     * The message data may be changed using the <code>setMessage</code>
     * method.
     * @see #setMessage
     */
    protected MetaMessage(byte[] data) {
        super(data);
        //$$fb 2001-10-06: need to calculate dataLength. Fix for bug #4511796
        if (data.length>=3) {
            dataLength=data.length-3;
            int pos=2;
            while (pos<data.length && (data[pos] & 0x80)!=0) {
                dataLength--; pos++;
            }
        }
    }


    /**
     * Sets the message parameters for a <code>MetaMessage</code>.
     * Since only one status byte value, <code>0xFF</code>, is allowed for meta-messages,
     * it does not need to be specified here.  Calls to <code>{@link MidiMessage#getStatus getStatus}</code> return
     * <code>0xFF</code> for all meta-messages.
     * <p>
     * The <code>type</code> argument should be a valid value for the byte that
     * follows the status byte in the <code>MetaMessage</code>.  The <code>data</code> argument
     * should contain all the subsequent bytes of the <code>MetaMessage</code>.  In other words,
     * the byte that specifies the type of <code>MetaMessage</code> is not considered a data byte.
     *
     * <p>
     *  设置<code> MetaMessage </code>的消息参数。由于只允许一个状态字节值<code> 0xFF </code>用于元消息,因此不需要在这里指定。
     * 对所有元消息调用<code> {@ link MidiMessage#getStatus getStatus} </code>返回<code> 0xFF </code>。
     * <p>
     *  <code> type </code>参数应该是<code> MetaMessage </code>中的状态字节后的字节的有效值。
     *  <code> data </code>参数应包含<code> MetaMessage </code>的所有后续字节。
     * 换句话说,指定<code> MetaMessage </code>类型的字节不被认为是数据字节。
     * 
     * 
     * @param type              meta-message type (must be less than 128)
     * @param data              the data bytes in the MIDI message
     * @param length    the number of bytes in the <code>data</code>
     * byte array
     * @throws                  InvalidMidiDataException  if the
     * parameter values do not specify a valid MIDI meta message
     */
    public void setMessage(int type, byte[] data, int length) throws InvalidMidiDataException {

        if (type >= 128 || type < 0) {
            throw new InvalidMidiDataException("Invalid meta event with type " + type);
        }
        if ((length > 0 && length > data.length) || length < 0) {
            throw new InvalidMidiDataException("length out of bounds: "+length);
        }

        this.length = 2 + getVarIntLength(length) + length;
        this.dataLength = length;
        this.data = new byte[this.length];
        this.data[0] = (byte) META;        // status value for MetaMessages (meta events)
        this.data[1] = (byte) type;        // MetaMessage type
        writeVarInt(this.data, 2, length); // write the length as a variable int
        if (length > 0) {
            System.arraycopy(data, 0, this.data, this.length - this.dataLength, this.dataLength);
        }
    }


    /**
     * Obtains the type of the <code>MetaMessage</code>.
     * <p>
     *  获取<code> MetaMessage </code>的类型。
     * 
     * 
     * @return an integer representing the <code>MetaMessage</code> type
     */
    public int getType() {
        if (length>=2) {
            return data[1] & 0xFF;
        }
        return 0;
    }



    /**
     * Obtains a copy of the data for the meta message.  The returned
     * array of bytes does not include the status byte or the message
     * length data.  The length of the data for the meta message is
     * the length of the array.  Note that the length of the entire
     * message includes the status byte and the meta message type
     * byte, and therefore may be longer than the returned array.
     * <p>
     * 获取元消息的数据副本。返回的字节数组不包括状态字节或消息长度数据。元消息的数据长度是数组的长度。注意,整个消息的长度包括状态字节和元消息类型字节,因此可能比返回的数组长。
     * 
     * 
     * @return array containing the meta message data.
     * @see MidiMessage#getLength
     */
    public byte[] getData() {
        byte[] returnedArray = new byte[dataLength];
        System.arraycopy(data, (length - dataLength), returnedArray, 0, dataLength);
        return returnedArray;
    }


    /**
     * Creates a new object of the same class and with the same contents
     * as this object.
     * <p>
     *  创建与此对象具有相同类和相同内容的新对象。
     * 
     * @return a clone of this instance
     */
    public Object clone() {
        byte[] newData = new byte[length];
        System.arraycopy(data, 0, newData, 0, newData.length);

        MetaMessage event = new MetaMessage(newData);
        return event;
    }

    // HELPER METHODS

    private int getVarIntLength(long value) {
        int length = 0;
        do {
            value = value >> 7;
            length++;
        } while (value > 0);
        return length;
    }

    private final static long mask = 0x7F;

    private void writeVarInt(byte[] data, int off, long value) {
        int shift=63; // number of bitwise left-shifts of mask
        // first screen out leading zeros
        while ((shift > 0) && ((value & (mask << shift)) == 0)) shift-=7;
        // then write actual values
        while (shift > 0) {
            data[off++]=(byte) (((value & (mask << shift)) >> shift) | 0x80);
            shift-=7;
        }
        data[off] = (byte) (value & mask);
    }

}
