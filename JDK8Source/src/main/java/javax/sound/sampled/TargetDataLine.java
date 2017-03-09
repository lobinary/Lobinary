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
 * A target data line is a type of <code>{@link DataLine}</code> from which
 * audio data can be read.  The most common example is a data line that gets
 * its data from an audio capture device.  (The device is implemented as a
 * mixer that writes to the target data line.)
 * <p>
 * Note that the naming convention for this interface reflects the relationship
 * between the line and its mixer.  From the perspective of an application,
 * a target data line may act as a source for audio data.
 * <p>
 * The target data line can be obtained from a mixer by invoking the
 * <code>{@link Mixer#getLine getLine}</code>
 * method of <code>Mixer</code> with an appropriate
 * <code>{@link DataLine.Info}</code> object.
 * <p>
 * The <code>TargetDataLine</code> interface provides a method for reading the
 * captured data from the target data line's buffer.Applications
 * that record audio should read data from the target data line quickly enough
 * to keep the buffer from overflowing, which could cause discontinuities in
 * the captured data that are perceived as clicks.  Applications can use the
 * <code>{@link DataLine#available available}</code> method defined in the
 * <code>DataLine</code> interface to determine the amount of data currently
 * queued in the data line's buffer.  If the buffer does overflow,
 * the oldest queued data is discarded and replaced by new data.
 *
 * <p>
 *  目标数据行是一种<code> {@ link DataLine} </code>,可以从中读取音频数据。最常见的示例是从音频捕获设备获取其数据的数据线。 (该器件实现为向目标数据线写入的混频器。
 * <p>
 *  请注意,此接口的命名约定反映了行与其混合器之间的关系。从应用的角度来看,目标数据线可以用作音频数据的源。
 * <p>
 *  可以通过使用适当的<code> {@ link DataLine.Info)调用<code> Mixer </code>的<code> {@ link Mixer#getLine getLine} </code>
 * 方法从混合器获得目标数据线} </code>对象。
 * <p>
 *  <code> TargetDataLine </code>接口提供了一种从目标数据行的缓冲区读取捕获数据的方法。
 * 记录音频的应用程序应该足够快地从目标数据行读取数据,以防止缓冲区溢出,这可能导致不连续在捕获的数据中被感知为点击。
 * 应用程序可以使用<code> DataLine </code>接口中定义的<code> {@ link DataLine#available available} </code>方法来确定当前在数据行缓
 * 冲区中排队的数据量。
 * 记录音频的应用程序应该足够快地从目标数据行读取数据,以防止缓冲区溢出,这可能导致不连续在捕获的数据中被感知为点击。如果缓冲区没有溢出,则最旧的排队数据被丢弃并被新数据替代。
 * 
 * 
 * @author Kara Kytle
 * @see Mixer
 * @see DataLine
 * @see SourceDataLine
 * @since 1.3
 */
public interface TargetDataLine extends DataLine {


    /**
     * Opens the line with the specified format and requested buffer size,
     * causing the line to acquire any required system resources and become
     * operational.
     * <p>
     * The buffer size is specified in bytes, but must represent an integral
     * number of sample frames.  Invoking this method with a requested buffer
     * size that does not meet this requirement may result in an
     * IllegalArgumentException.  The actual buffer size for the open line may
     * differ from the requested buffer size.  The value actually set may be
     * queried by subsequently calling <code>{@link DataLine#getBufferSize}</code>
     * <p>
     * If this operation succeeds, the line is marked as open, and an
     * <code>{@link LineEvent.Type#OPEN OPEN}</code> event is dispatched to the
     * line's listeners.
     * <p>
     * Invoking this method on a line that is already open is illegal
     * and may result in an <code>IllegalStateException</code>.
     * <p>
     * Some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in a
     * <code>LineUnavailableException</code>.
     *
     * <p>
     * 打开具有指定格式和请求的缓冲区大小的行,使该行获取任何所需的系统资源并运行。
     * <p>
     *  缓冲区大小以字节为单位指定,但必须表示整数个采样帧。调用具有不满足此要求的请求的缓冲区大小的此方法可能会导致IllegalArgumentException。
     * 开放线路的实际缓冲区大小可能与请求的缓冲区大小不同。实际设置的值可以通过随后调用<code> {@ link DataLine#getBufferSize} </code>。
     * <p>
     *  如果此操作成功,则将该行标记为打开,并将<code> {@ link LineEvent.Type#OPEN OPEN} </code>事件分派到该行的侦听器。
     * <p>
     *  在已经打开的行上调用此方法是非法的,可能会导致<code> IllegalStateException </code>。
     * <p>
     *  有些行,一旦关闭,就无法重新打开。尝试重新打开这样的行将总是导致<code> LineUnavailableException </code>。
     * 
     * 
     * @param format the desired audio format
     * @param bufferSize the desired buffer size, in bytes.
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
     * that the system has chosen may be queried by subsequently calling <code>{@link DataLine#getBufferSize}</code>
     * <p>
     * If this operation succeeds, the line is marked as open, and an
     * <code>{@link LineEvent.Type#OPEN OPEN}</code> event is dispatched to the
     * line's listeners.
     * <p>
     * Invoking this method on a line that is already open is illegal
     * and may result in an <code>IllegalStateException</code>.
     * <p>
     * Some lines, once closed, cannot be reopened.  Attempts
     * to reopen such a line will always result in a
     * <code>LineUnavailableException</code>.
     *
     * <p>
     *  打开具有指定格式的行,使该行获取任何所需的系统资源并运行。
     * 
     * <p>
     *  实现选择缓冲器大小,其以字节为单位测量,但包含整数个样本帧。系统选择的缓冲区大小可以通过随后调用<code> {@ link DataLine#getBufferSize} </code>
     * <p>
     * 如果此操作成功,则将该行标记为打开,并将<code> {@ link LineEvent.Type#OPEN OPEN} </code>事件分派到该行的侦听器。
     * <p>
     *  在已经打开的行上调用此方法是非法的,可能会导致<code> IllegalStateException </code>。
     * <p>
     *  有些行,一旦关闭,就无法重新打开。尝试重新打开这样的行将总是导致<code> LineUnavailableException </code>。
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
     * Reads audio data from the data line's input buffer.   The requested
     * number of bytes is read into the specified array, starting at
     * the specified offset into the array in bytes.  This method blocks until
     * the requested amount of data has been read.  However, if the data line
     * is closed, stopped, drained, or flushed before the requested amount has
     * been read, the method no longer blocks, but returns the number of bytes
     * read thus far.
     * <p>
     * The number of bytes that can be read without blocking can be ascertained
     * using the <code>{@link DataLine#available available}</code> method of the
     * <code>DataLine</code> interface.  (While it is guaranteed that
     * this number of bytes can be read without blocking, there is no guarantee
     * that attempts to read additional data will block.)
     * <p>
     * The number of bytes to be read must represent an integral number of
     * sample frames, such that:
     * <br>
     * <center><code>[ bytes read ] % [frame size in bytes ] == 0</code></center>
     * <br>
     * The return value will always meet this requirement.  A request to read a
     * number of bytes representing a non-integral number of sample frames cannot
     * be fulfilled and may result in an IllegalArgumentException.
     *
     * <p>
     *  从数据线的输入缓冲区读取音频数据。将请求的字节数读入指定的数组,从指定的数组偏移量开始,以字节为单位。此方法阻塞,直到已读取所请求的数据量。
     * 但是,如果数据线在读取请求量之前关闭,停止,耗尽或刷新,则该方法不再阻塞,而是返回到目前为止读取的字节数。
     * <p>
     *  可以使用<code> DataLine </code>接口的<code> {@ link DataLine#available available} </code>方法确定可以无阻塞地读取的字节数。
     *  (虽然保证可以读取这个字节数而不阻塞,但不能保证尝试读取其他数据会被阻塞。)。
     * <p>
     *  要读取的字节数必须表示采样帧的整数数量,例如：
     * <br>
     * 
     * @param b a byte array that will contain the requested input data when
     * this method returns
     * @param off the offset from the beginning of the array, in bytes
     * @param len the requested number of bytes to read
     * @return the number of bytes actually read
     * @throws IllegalArgumentException if the requested number of bytes does
     * not represent an integral number of sample frames.
     * or if <code>len</code> is negative.
     * @throws ArrayIndexOutOfBoundsException if <code>off</code> is negative,
     * or <code>off+len</code> is greater than the length of the array
     * <code>b</code>.
     *
     * @see SourceDataLine#write
     * @see DataLine#available
     */
    public int read(byte[] b, int off, int len);

    /**
     * Obtains the number of sample frames of audio data that can be read from
     * the target data line without blocking.  Note that the return value
     * measures sample frames, not bytes.
     * <p>
     *  <center> <code> [bytes read]％[frame size in bytes] == 0 </code> </center>
     * <br>
     * 返回值将始终满足此要求。读取表示非整数个样本帧的多个字节的请求不能被满足,并且可能导致IllegalArgumentException。
     * 
     * 
     * @return the number of sample frames currently available for reading
     * @see SourceDataLine#availableWrite
     */
    //public int availableRead();
}
