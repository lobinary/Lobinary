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
 * A <code>SysexMessage</code> object represents a MIDI system exclusive message.
 * <p>
 * When a system exclusive message is read from a MIDI file, it always has
 * a defined length.  Data from a system exclusive message from a MIDI file
 * should be stored in the data array of a <code>SysexMessage</code> as
 * follows: the system exclusive message status byte (0xF0 or 0xF7), all
 * message data bytes, and finally the end-of-exclusive flag (0xF7).
 * The length reported by the <code>SysexMessage</code> object is therefore
 * the length of the system exclusive data plus two: one byte for the status
 * byte and one for the end-of-exclusive flag.
 * <p>
 * As dictated by the Standard MIDI Files specification, two status byte values are legal
 * for a <code>SysexMessage</code> read from a MIDI file:
 * <ul>
 * <li>0xF0: System Exclusive message (same as in MIDI wire protocol)</li>
 * <li>0xF7: Special System Exclusive message</li>
 * </ul>
 * <p>
 * When Java Sound is used to handle system exclusive data that is being received
 * using MIDI wire protocol, it should place the data in one or more
 * <code>SysexMessages</code>.  In this case, the length of the system exclusive data
 * is not known in advance; the end of the system exclusive data is marked by an
 * end-of-exclusive flag (0xF7) in the MIDI wire byte stream.
 * <ul>
 * <li>0xF0: System Exclusive message (same as in MIDI wire protocol)</li>
 * <li>0xF7: End of Exclusive (EOX)</li>
 * </ul>
 * The first <code>SysexMessage</code> object containing data for a particular system
 * exclusive message should have the status value 0xF0.  If this message contains all
 * the system exclusive data
 * for the message, it should end with the status byte 0xF7 (EOX).
 * Otherwise, additional system exclusive data should be sent in one or more
 * <code>SysexMessages</code> with a status value of 0xF7.  The <code>SysexMessage</code>
 * containing the last of the data for the system exclusive message should end with the
 * value 0xF7 (EOX) to mark the end of the system exclusive message.
 * <p>
 * If system exclusive data from <code>SysexMessages</code> objects is being transmitted
 * using MIDI wire protocol, only the initial 0xF0 status byte, the system exclusive
 * data itself, and the final 0xF7 (EOX) byte should be propagated; any 0xF7 status
 * bytes used to indicate that a <code>SysexMessage</code> contains continuing system
 * exclusive data should not be propagated via MIDI wire protocol.
 *
 * <p>
 *  <code> SysexMessage </code>对象表示MIDI系统独占消息。
 * <p>
 *  当从MIDI文件读取系统专用消息时,它始终具有定义的长度。
 * 来自MIDI文件的系统独占消息的数据应该存储在<code> SysexMessage </code>的数据数组中,如下所示：系统独占消息状态字节(0xF0或0xF7),所有消息数据字节,最后结束排它标志
 * (0xF7)。
 *  当从MIDI文件读取系统专用消息时,它始终具有定义的长度。
 * 因此,由<code> SysexMessage </code>对象报告的长度是系统独占数据的长度加上两个：状态字节一个字节和独占结束标志一个字节。
 * <p>
 *  如标准MIDI文件规范所规定的,对于从MIDI文件读取的<code> SysexMessage </code>,两个状态字节值是合法的：
 * <ul>
 *  <li> 0xF0：系统专属讯息(与MIDI线协议相同)</li> <li> 0xF7：特殊系统专属讯息</li>
 * </ul>
 * <p>
 *  当Java Sound用于处理使用MIDI线协议接收的系统独占数据时,它应该将数据放在一个或多个<code> SysexMessages </code>中。
 * 在这种情况下,系统排他数据的长度不是预先知道的;系统独占数据的结束由MIDI线字节流中的排它结束标志(0xF7)标记。
 * <ul>
 * <li> 0xF0：系统独占消息(与MIDI线协议相同)</li> <li> 0xF7：独占结束(EOX)</li>
 * </ul>
 *  包含特定系统独占消息的数据的第一个<code> SysexMessage </code>对象应该具有状态值0xF0。如果此消息包含消息的所有系统独占数据,则应以状态字节0xF7(EOX)结束。
 * 否则,应在一个或多个状态值为0xF7的<code> SysexMessages </code>中发送附加系统独占数据。
 * 包含系统独占消息的最后一个数据的<code> SysexMessage </code>应以值0xF7(EOX)结束,以标记系统独占消息的结束。
 * <p>
 *  如果使用MIDI线协议传输来自<code> SysexMessages </code>对象的系统独占数据,则只应传播初始0xF0状态字节,系统独占数据本身和最后的0xF7(EOX)字节;用于指示<code>
 *  SysexMessage </code>包含连续系统独占数据的任何0xF7状态字节不应通过MIDI线协议传播。
 * 
 * 
 * @author David Rivas
 * @author Kara Kytle
 * @author Florian Bomers
 */
public class SysexMessage extends MidiMessage {


    // Status byte defines


    /**
     * Status byte for System Exclusive message (0xF0, or 240).
     * <p>
     *  系统独占消息的状态字节(0xF0或240)。
     * 
     * 
     * @see MidiMessage#getStatus
     */
    public static final int SYSTEM_EXCLUSIVE                    = 0xF0; // 240


    /**
     * Status byte for Special System Exclusive message (0xF7, or 247), which is used
     * in MIDI files.  It has the same value as END_OF_EXCLUSIVE, which
     * is used in the real-time "MIDI wire" protocol.
     * <p>
     *  特殊系统专用消息(0xF7或247)的状态字节,用于MIDI文件。它具有与END_OF_EXCLUSIVE相同的值,其用于实时"MIDI线"协议。
     * 
     * 
     * @see MidiMessage#getStatus
     */
    public static final int SPECIAL_SYSTEM_EXCLUSIVE    = 0xF7; // 247


    // Instance variables


    /*
     * The data bytes for this system exclusive message.  These are
     * initialized to <code>null</code> and are set explicitly
     * by {@link #setMessage(int, byte[], int, long) setMessage}.
     * <p>
     * 此系统专用消息的数据字节。这些被初始化为<code> null </code>,并由{@link #setMessage(int,byte [],int,long)setMessage}显式设置。
     * 
     */
    //protected byte[] data = null;


    /**
     * Constructs a new <code>SysexMessage</code>. The
     * contents of the new message are guaranteed to specify
     * a valid MIDI message.  Subsequently, you may set the
     * contents of the message using one of the <code>setMessage</code>
     * methods.
     * <p>
     *  构造新的<code> SysexMessage </code>。新消息的内容保证指定有效的MIDI消息。随后,您可以使用<code> setMessage </code>方法之一设置消息的内容。
     * 
     * 
     * @see #setMessage
     */
    public SysexMessage() {
        this(new byte[2]);
        // Default sysex message data: SOX followed by EOX
        data[0] = (byte) (SYSTEM_EXCLUSIVE & 0xFF);
        data[1] = (byte) (ShortMessage.END_OF_EXCLUSIVE & 0xFF);
    }

    /**
     * Constructs a new {@code SysexMessage} and sets the data for
     * the message. The first byte of the data array must be a valid system
     * exclusive status byte (0xF0 or 0xF7).
     * The contents of the message can be changed by using one of
     * the {@code setMessage} methods.
     *
     * <p>
     *  构造一个新的{@code SysexMessage}并设置消息的数据。数据数组的第一个字节必须是有效的系统独占状态字节(0xF0或0xF7)。
     * 可以使用{@code setMessage}方法之一更改消息的内容。
     * 
     * 
     * @param data the system exclusive message data including the status byte
     * @param length the length of the valid message data in the array,
     *     including the status byte; it should be non-negative and less than
     *     or equal to {@code data.length}
     * @throws InvalidMidiDataException if the parameter values
     *     do not specify a valid MIDI meta message.
     * @see #setMessage(byte[], int)
     * @see #setMessage(int, byte[], int)
     * @see #getData()
     * @since 1.7
     */
    public SysexMessage(byte[] data, int length)
            throws InvalidMidiDataException {
        super(null);
        setMessage(data, length);
    }

    /**
     * Constructs a new {@code SysexMessage} and sets the data for the message.
     * The contents of the message can be changed by using one of
     * the {@code setMessage} methods.
     *
     * <p>
     *  构造一个新的{@code SysexMessage}并设置消息的数据。可以使用{@code setMessage}方法之一更改消息的内容。
     * 
     * 
     * @param status the status byte for the message; it must be a valid system
     *     exclusive status byte (0xF0 or 0xF7)
     * @param data the system exclusive message data (without the status byte)
     * @param length the length of the valid message data in the array;
     *     it should be non-negative and less than or equal to
     *     {@code data.length}
     * @throws InvalidMidiDataException if the parameter values
     *     do not specify a valid MIDI meta message.
     * @see #setMessage(byte[], int)
     * @see #setMessage(int, byte[], int)
     * @see #getData()
     * @since 1.7
     */
    public SysexMessage(int status, byte[] data, int length)
            throws InvalidMidiDataException {
        super(null);
        setMessage(status, data, length);
    }


    /**
     * Constructs a new <code>SysexMessage</code>.
     * <p>
     *  构造新的<code> SysexMessage </code>。
     * 
     * 
     * @param data an array of bytes containing the complete message.
     * The message data may be changed using the <code>setMessage</code>
     * method.
     * @see #setMessage
     */
    protected SysexMessage(byte[] data) {
        super(data);
    }


    /**
     * Sets the data for the system exclusive message.   The
     * first byte of the data array must be a valid system
     * exclusive status byte (0xF0 or 0xF7).
     * <p>
     *  设置系统独占消息的数据。数据数组的第一个字节必须是有效的系统独占状态字节(0xF0或0xF7)。
     * 
     * 
     * @param data the system exclusive message data
     * @param length the length of the valid message data in
     * the array, including the status byte.
     */
    public void setMessage(byte[] data, int length) throws InvalidMidiDataException {
        int status = (data[0] & 0xFF);
        if ((status != 0xF0) && (status != 0xF7)) {
            throw new InvalidMidiDataException("Invalid status byte for sysex message: 0x" + Integer.toHexString(status));
        }
        super.setMessage(data, length);
    }


    /**
     * Sets the data for the system exclusive message.
     * <p>
     *  设置系统独占消息的数据。
     * 
     * 
     * @param status the status byte for the message (0xF0 or 0xF7)
     * @param data the system exclusive message data
     * @param length the length of the valid message data in
     * the array
     * @throws InvalidMidiDataException if the status byte is invalid for a sysex message
     */
    public void setMessage(int status, byte[] data, int length) throws InvalidMidiDataException {
        if ( (status != 0xF0) && (status != 0xF7) ) {
            throw new InvalidMidiDataException("Invalid status byte for sysex message: 0x" + Integer.toHexString(status));
        }
        if (length < 0 || length > data.length) {
            throw new IndexOutOfBoundsException("length out of bounds: "+length);
        }
        this.length = length + 1;

        if (this.data==null || this.data.length < this.length) {
            this.data = new byte[this.length];
        }

        this.data[0] = (byte) (status & 0xFF);
        if (length > 0) {
            System.arraycopy(data, 0, this.data, 1, length);
        }
    }


    /**
     * Obtains a copy of the data for the system exclusive message.
     * The returned array of bytes does not include the status byte.
     * <p>
     *  获取系统独占消息的数据副本。返回的字节数组不包括状态字节。
     * 
     * 
     * @return array containing the system exclusive message data.
     */
    public byte[] getData() {
        byte[] returnedArray = new byte[length - 1];
        System.arraycopy(data, 1, returnedArray, 0, (length - 1));
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
        SysexMessage event = new SysexMessage(newData);
        return event;
    }
}
