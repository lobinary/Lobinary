/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * A source data line is a data line to which data may be written.  It acts as
 * a source to its mixer. An application writes audio bytes to a source data line,
 * which handles the buffering of the bytes and delivers them to the mixer.
 * The mixer may mix the samples with those from other sources and then deliver
 * the mix to a target such as an output port (which may represent an audio output
 * device on a sound card).
 * <p>
 * Note that the naming convention for this interface reflects the relationship
 * between the line and its mixer.  From the perspective of an application,
 * a source data line may act as a target for audio data.
 * <p>
 * A source data line can be obtained from a mixer by invoking the
 * <code>{@link Mixer#getLine getLine}</code> method of <code>Mixer</code> with
 * an appropriate <code>{@link DataLine.Info}</code> object.
 * <p>
 * The <code>SourceDataLine</code> interface provides a method for writing
 * audio data to the data line's buffer. Applications that play or mix
 * audio should write data to the source data line quickly enough to keep the
 * buffer from underflowing (emptying), which could cause discontinuities in
 * the audio that are perceived as clicks.  Applications can use the
 * <code>{@link DataLine#available available}</code> method defined in the
 * <code>DataLine</code> interface to determine the amount of data currently
 * queued in the data line's buffer.  The amount of data which can be written
 * to the buffer without blocking is the difference between the buffer size
 * and the amount of queued data.  If the delivery of audio output
 * stops due to underflow, a <code>{@link LineEvent.Type#STOP STOP}</code> event is
 * generated.  A <code>{@link LineEvent.Type#START START}</code> event is generated
 * when the audio output resumes.
 *
 * <p>
 *  源数据线是可以写入数据的数据线。它作为混音器的源。应用程序将音频字节写入源数据线,该源数据线处理字节的缓冲并将其传送到混合器。
 * 混合器可以将样本与来自其他源的样本混合,然后将混合传送到目标,例如输出端口(其可以表示声卡上的音频输出设备)。
 * <p>
 *  请注意,此接口的命名约定反映了行与其混合器之间的关系。从应用的角度来看,源数据线可以用作音频数据的目标。
 * <p>
 *  可以通过用适当的<code>调用<code> Mixer </code>的<code> {@ link Mixer#getLine getLine} </code>方法从混合器获得源数据线{@ link DataLine.Info }
 *  </code>对象。
 * <p>
 * <code> SourceDataLine </code>接口提供了一种将音频数据写入数据线缓冲区的方法。
 * 播放或混合音频的应用程序应将数据写入源数据线的速度足够快,以防止缓冲区下溢(清空),这可能会导致音频中被感知为点击的不连续。
 * 应用程序可以使用<code> DataLine </code>接口中定义的<code> {@ link DataLine#available available} </code>方法来确定当前在数据行缓
 * 冲区中排队的数据量。
 * 播放或混合音频的应用程序应将数据写入源数据线的速度足够快,以防止缓冲区下溢(清空),这可能会导致音频中被感知为点击的不连续。可以写入缓冲区而不阻塞的数据量是缓冲区大小和排队数据量之间的差值。
 * 如果音频输出的传递由于下溢而停止,则会生成<code> {@ link LineEvent.Type#STOP STOP} </code>事件。
 * 当音频输出恢复时,会生成<code> {@ link LineEvent.Type#START START} </code>事件。
 * 
 * 
 * @author Kara Kytle
 * @see Mixer
 * @see DataLine
 * @see TargetDataLine
 * @since 1.3
 */
public interface SourceDataLine extends DataLine {


    /**
     * Opens the line with the specified format and suggested buffer size,
     * causing the line to acquire any required
     * system resources and become operational.
     * <p>
     * The buffer size is specified in bytes, but must represent an integral
     * number of sample frames.  Invoking this method with a requested buffer
     * size that does not meet this requirement may result in an
     * IllegalArgumentException.  The actual buffer size for the open line may
     * differ from the requested buffer size.  The value actually set may be
     * queried by subsequently calling <code>{@link DataLine#getBufferSize}</code>.
     * <p>
     * If this operation succeeds, the line is marked as open, and an
     * <code>{@link LineEvent.Type#OPEN OPEN}</code> event is dispatched to the
     * line's listeners.
     * <p>
     * Invoking this method on a line which is already open is illegal
     * and may result in an <code>IllegalStateException</code>.
     * <p>
     * Note that some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in a
     * <code>LineUnavailableException</code>.
     *
     * <p>
     *  打开具有指定格式和建议缓冲区大小的行,使该行获取任何所需的系统资源并变为可操作。
     * <p>
     *  缓冲区大小以字节为单位指定,但必须表示整数个采样帧。调用具有不满足此要求的请求的缓冲区大小的此方法可能会导致IllegalArgumentException。
     * 开放线路的实际缓冲区大小可能与请求的缓冲区大小不同。实际设置的值可以通过随后调用<code> {@ link DataLine#getBufferSize} </code>来查询。
     * <p>
     * 如果此操作成功,则将该行标记为打开,并将<code> {@ link LineEvent.Type#OPEN OPEN} </code>事件分派到该行的侦听器。
     * <p>
     *  在已经打开的行上调用此方法是非法的,并且可能导致<code> IllegalStateException </code>。
     * <p>
     *  注意,一些行,一旦关闭,不能重新打开。尝试重新打开这样的行将总是导致<code> LineUnavailableException </code>。
     * 
     * 
     * @param format the desired audio format
     * @param bufferSize the desired buffer size
     * @throws LineUnavailableException if the line cannot be
     * opened due to resource restrictions
     * @throws IllegalArgumentException if the buffer size does not represent
     * an integral number of sample frames,
     * or if <code>format</code> is not fully specified or invalid
     * @throws IllegalStateException if the line is already open
     * @throws SecurityException if the line cannot be
     * opened due to security restrictions
     *
     * @see #open(AudioFormat)
     * @see Line#open
     * @see Line#close
     * @see Line#isOpen
     * @see LineEvent
     */
    public void open(AudioFormat format, int bufferSize) throws LineUnavailableException;


    /**
     * Opens the line with the specified format, causing the line to acquire any
     * required system resources and become operational.
     *
     * <p>
     * The implementation chooses a buffer size, which is measured in bytes but
     * which encompasses an integral number of sample frames.  The buffer size
     * that the system has chosen may be queried by subsequently calling
     * <code>{@link DataLine#getBufferSize}</code>.
     * <p>
     * If this operation succeeds, the line is marked as open, and an
     * <code>{@link LineEvent.Type#OPEN OPEN}</code> event is dispatched to the
     * line's listeners.
     * <p>
     * Invoking this method on a line which is already open is illegal
     * and may result in an <code>IllegalStateException</code>.
     * <p>
     * Note that some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in a
     * <code>LineUnavailableException</code>.
     *
     * <p>
     *  打开具有指定格式的行,使该行获取任何所需的系统资源并运行。
     * 
     * <p>
     *  实现选择缓冲器大小,其以字节为单位测量,但包含整数个样本帧。系统选择的缓冲区大小可以通过随后调用<code> {@ link DataLine#getBufferSize} </code>来查询。
     * <p>
     *  如果此操作成功,则将该行标记为打开,并将<code> {@ link LineEvent.Type#OPEN OPEN} </code>事件分派到该行的侦听器。
     * <p>
     *  在已经打开的行上调用此方法是非法的,并且可能导致<code> IllegalStateException </code>。
     * <p>
     *  注意,一些行,一旦关闭,不能重新打开。尝试重新打开这样的行将总是导致<code> LineUnavailableException </code>。
     * 
     * 
     * @param format the desired audio format
     * @throws LineUnavailableException if the line cannot be
     * opened due to resource restrictions
     * @throws IllegalArgumentException if <code>format</code>
     * is not fully specified or invalid
     * @throws IllegalStateException if the line is already open
     * @throws SecurityException if the line cannot be
     * opened due to security restrictions
     *
     * @see #open(AudioFormat, int)
     * @see Line#open
     * @see Line#close
     * @see Line#isOpen
     * @see LineEvent
     */
    public void open(AudioFormat format) throws LineUnavailableException;


    /**
     * Writes audio data to the mixer via this source data line.  The requested
     * number of bytes of data are read from the specified array,
     * starting at the given offset into the array, and written to the data
     * line's buffer.  If the caller attempts to write more data than can
     * currently be written (see <code>{@link DataLine#available available}</code>),
     * this method blocks until the requested amount of data has been written.
     * This applies even if the requested amount of data to write is greater
     * than the data line's buffer size.  However, if the data line is closed,
     * stopped, or flushed before the requested amount has been written,
     * the method no longer blocks, but returns the number of bytes
     * written thus far.
     * <p>
     * The number of bytes that can be written without blocking can be ascertained
     * using the <code>{@link DataLine#available available}</code> method of the
     * <code>DataLine</code> interface.  (While it is guaranteed that
     * this number of bytes can be written without blocking, there is no guarantee
     * that attempts to write additional data will block.)
     * <p>
     * The number of bytes to write must represent an integral number of
     * sample frames, such that:
     * <br>
     * <center><code>[ bytes written ] % [frame size in bytes ] == 0</code></center>
     * <br>
     * The return value will always meet this requirement.  A request to write a
     * number of bytes representing a non-integral number of sample frames cannot
     * be fulfilled and may result in an <code>IllegalArgumentException</code>.
     *
     * <p>
     * 通过此源数据线将音频数据写入混音器。从指定数组读取所请求的数据字节数,从给定的偏移量开始到数组,并写入数据行的缓冲区。
     * 如果调用者尝试写入比当前可写的数据更多的数据(参见<code> {@ link DataLine#available available} </code>),此方法将阻塞,直到写入了所请求的数据量。
     * 即使所请求的要写入的数据量大于数据线的缓冲区大小,也适用。但是,如果在写入请求的数量之前关闭,停止或刷新数据线,则该方法不再阻塞,而是返回到目前为止写入的字节数。
     * <p>
     *  可以使用<code> DataLine </code>接口的<code> {@ link DataLine#available available} </code>方法来确定可以无阻塞地写入的字节数。
     *  (虽然保证这个字节数可以不阻塞地写入,但不能保证写入附加数据的尝试将被阻塞。)。
     * <p>
     *  要写入的字节数必须表示采样帧的整数数量,例如：
     * 
     * @param b a byte array containing data to be written to the data line
     * @param len the length, in bytes, of the valid data in the array
     * (in other words, the requested amount of data to write, in bytes)
     * @param off the offset from the beginning of the array, in bytes
     * @return the number of bytes actually written
     * @throws IllegalArgumentException if the requested number of bytes does
     * not represent an integral number of sample frames,
     * or if <code>len</code> is negative
     * @throws ArrayIndexOutOfBoundsException if <code>off</code> is negative,
     * or <code>off+len</code> is greater than the length of the array
     * <code>b</code>.
     *
     * @see TargetDataLine#read
     * @see DataLine#available
     */
    public int write(byte[] b, int off, int len);

    /**
     * Obtains the number of sample frames of audio data that can be written to
     * the mixer, via this data line, without blocking.  Note that the return
     * value measures sample frames, not bytes.
     * <p>
     * <br>
     *  <center> <code> [bytes written]％[frame size in bytes] == 0 </code> </center>
     * <br>
     *  返回值将始终满足此要求。写入表示非整数个样本帧的多个字节的请求不能被满足,并且可能导致<code> IllegalArgumentException </code>。
     * 
     * @return the number of sample frames currently available for writing
     * @see TargetDataLine#availableRead
     */
    //public int availableWrite();
}
